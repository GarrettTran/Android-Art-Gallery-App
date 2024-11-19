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
import java.util.Map;

import rmit.ad.assignment1_s3929513.R;
import rmit.ad.assignment1_s3929513.adapter.ArtworkAdapter;
import rmit.ad.assignment1_s3929513.database.FireStoreDatabaseHandler;
import rmit.ad.assignment1_s3929513.model.Artwork;

public class FavoriteActivity extends AppCompatActivity {

    private FireStoreDatabaseHandler dbHandler;
    private RecyclerView recyclerView;
    private ArtworkAdapter adapter;
    private List<Artwork> favoriteArtworks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home); // Reuse the HomeActivity layout

        // Initialize Firestore handler
        dbHandler = new FireStoreDatabaseHandler();

        // Set up RecyclerView
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Fetch favorite artworks with artist names
        fetchFavoriteArtworksWithArtistNames();

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
        rmit.ad.assignment1_s3929513.helper.BottomNavigationHelper.setupBottomNavigation(bottomNavigationView, this);
    }

    private void fetchFavoriteArtworksWithArtistNames() {
        dbHandler.fetchArtistNameMap(new FireStoreDatabaseHandler.DataCallback<Map<String, String>>() {
            @Override
            public void onSuccess(Map<String, String> artistNameMap) {
                dbHandler.fetchFavoriteArtworks(dbHandler.getCurrentUserId(), new FireStoreDatabaseHandler.DataCallback<List<Artwork>>() {
                    @Override
                    public void onSuccess(List<Artwork> artworks) {
                        for (Artwork artwork : artworks) {
                            String artistName = artistNameMap.get(artwork.getArtistId());
                            artwork.setArtistName(artistName != null ? artistName : "Unknown Artist");
                        }
                        favoriteArtworks = artworks;
                        adapter = new ArtworkAdapter(FavoriteActivity.this, favoriteArtworks);
                        recyclerView.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Log.e("Firestore", "Error fetching favorite artworks", e);
                    }
                });
            }

            @Override
            public void onFailure(Exception e) {
                Log.e("Firestore", "Error fetching artist names", e);
            }
        });
    }

    private void filterArtworks(String query) {
        List<Artwork> filteredList = new ArrayList<>();
        for (Artwork artwork : favoriteArtworks) {
            if (artwork.getTitle().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(artwork);
            }
        }
        adapter.updateList(filteredList);
    }
}
