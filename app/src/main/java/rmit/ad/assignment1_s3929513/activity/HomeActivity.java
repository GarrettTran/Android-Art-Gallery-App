package rmit.ad.assignment1_s3929513.activity;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.widget.SearchView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import rmit.ad.assignment1_s3929513.R;
import rmit.ad.assignment1_s3929513.adapter.ArtworkAdapter;
import rmit.ad.assignment1_s3929513.FireStoreDatabaseHandler;
import rmit.ad.assignment1_s3929513.model.Artwork;

public class HomeActivity extends AppCompatActivity {

    private FireStoreDatabaseHandler dbHandler;
    private RecyclerView recyclerView;
    private ArtworkAdapter adapter;
    private List<Artwork> allArtworks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize Firestore handler
        dbHandler = new FireStoreDatabaseHandler();

        // Set up RecyclerView
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Fetch artworks from Firestore
        fetchAndDisplayArtworks();

        // Set up Search Bar
        SearchView searchView = findViewById(R.id.search_bar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterArtworks(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterArtworks(newText);
                return false;
            }
        });

        // Set up Bottom Navigation Bar
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                // Current view
                Log.d("Navigation", "Home clicked");
                return true;
            } else if (id == R.id.nav_favorite) {
                // Navigate to FavoriteActivity
                Log.d("Navigation", "Favorite clicked");
                // Intent intent = new Intent(this, FavoriteActivity.class);
                // startActivity(intent);
                return true;
            } else if (id == R.id.nav_user) {
                // Navigate to UserActivity
                Log.d("Navigation", "User clicked");
                // Intent intent = new Intent(this, UserActivity.class);
                // startActivity(intent);
                return true;
            }

            return false;
        });

    }

    private void fetchAndDisplayArtworks() {
        dbHandler.fetchAllArtworks(new FireStoreDatabaseHandler.DataCallback<List<Artwork>>() {
            @Override
            public void onSuccess(List<Artwork> artworks) {
                Log.d("Firestore", "Fetched artworks: " + artworks.toString());
                allArtworks = artworks; // Store all artworks
                adapter = new ArtworkAdapter(allArtworks);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Exception e) {
                Log.e("Firestore", "Error fetching artworks", e);
            }
        });
    }

    private void filterArtworks(String query) {
        List<Artwork> filteredList = new ArrayList<>();
        for (Artwork artwork : allArtworks) {
            if (artwork.getTitle().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(artwork);
            }
        }
        adapter.updateList(filteredList);
    }
}
