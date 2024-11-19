package rmit.ad.assignment1_s3929513.activity;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import rmit.ad.assignment1_s3929513.R;
import rmit.ad.assignment1_s3929513.adapter.ArtistAdapter;
import rmit.ad.assignment1_s3929513.database.FireStoreDatabaseHandler;
import rmit.ad.assignment1_s3929513.model.Artist;

public class ArtistActivity extends AppCompatActivity {

    private FireStoreDatabaseHandler dbHandler;
    private RecyclerView recyclerView;
    private ArtistAdapter adapter;
    private List<Artist> allArtists = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist);

        // Initialize Firestore handler
        dbHandler = new FireStoreDatabaseHandler();

        // Set up RecyclerView
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Fetch artists from Firestore
        fetchAndDisplayArtists();

        // Set up Search Bar
        SearchView searchView = findViewById(R.id.search_bar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterArtists(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    // Reset the adapter with the full list of artists when the search bar is cleared
                    adapter.updateList(allArtists);
                } else {
                    // Filter artists based on the search query
                    filterArtists(newText);
                }
                return false;
            }
        });


        // Set up Bottom Navigation Bar
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        rmit.ad.assignment1_s3929513.helper.BottomNavigationHelper.setupBottomNavigation(bottomNavigationView, this);

    }

    private void fetchAndDisplayArtists() {
        dbHandler.fetchAllArtists(new FireStoreDatabaseHandler.DataCallback<List<Artist>>() {
            @Override
            public void onSuccess(List<Artist> artists) {
                Log.d("Firestore", "Fetched artists: " + artists.toString());
                allArtists = artists; // Store all artists
                adapter = new ArtistAdapter(ArtistActivity.this, allArtists);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Exception e) {
                Log.e("Firestore", "Error fetching artists", e);
            }
        });
    }

    private void filterArtists(String query) {
        List<Artist> filteredList = new ArrayList<>();
        for (Artist artist : allArtists) {
            if (artist.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(artist);
            }
        }
        adapter.updateList(filteredList);
    }

}
