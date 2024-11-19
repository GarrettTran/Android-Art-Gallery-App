package rmit.ad.assignment1_s3929513.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import rmit.ad.assignment1_s3929513.R;
import rmit.ad.assignment1_s3929513.model.Artwork;
import rmit.ad.assignment1_s3929513.model.User;
import rmit.ad.assignment1_s3929513.database.FirestoreUserManager;

public class DetailActivity extends AppCompatActivity {

    private FirestoreUserManager userManager;
    private String currentUserId;
    private Artwork artwork;
    private boolean isFavorite;

    private ImageView favoriteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_activty);

        // Set up FirestoreUserManager
        userManager = new FirestoreUserManager();
        currentUserId = getIntent().getStringExtra("userId"); // Pass current user ID via intent

        // Set up the toolbar
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Enable the back button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Get views
        ImageView artworkImage = findViewById(R.id.artwork_image);
        TextView artworkTitle = findViewById(R.id.artwork_title);
        TextView artistName = findViewById(R.id.artist_name);
        TextView artworkDescription = findViewById(R.id.artwork_description);
        TextView artworkMedium = findViewById(R.id.artwork_medium);
        TextView artworkPrice = findViewById(R.id.artwork_price);
        TextView artworkDateCreated = findViewById(R.id.artwork_date_created);
        favoriteButton = findViewById(R.id.favorite_button); // Favorite button

        // Get data from intent
        String title = getIntent().getStringExtra("title");
        String artist = getIntent().getStringExtra("artistName");
        String description = getIntent().getStringExtra("description");
        String imageUrl = getIntent().getStringExtra("imageUrl");
        String medium = getIntent().getStringExtra("medium");
        String price = getIntent().getStringExtra("price");
        String rawDate = getIntent().getStringExtra("dateCreated"); // Passed as a String
        String artworkId = getIntent().getStringExtra("artworkId"); // Artwork ID for favorites

        // Set up Artwork object for toggling favorite
        artwork = new Artwork();
        artwork.setId(artworkId);
        artwork.setTitle(title);
        artwork.setArtistName(artist);
        artwork.setDescription(description);
        artwork.setMedium(medium);
        artwork.setImageUrl(imageUrl);
        artwork.setPrice(price);

        // Format the date
        String formattedDate = rawDate;
        try {
            formattedDate = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
                    .format(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(rawDate));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Set data to views
        artworkTitle.setText(title);
        artistName.setText(artist);
        artworkDescription.setText(description);
        artworkMedium.setText(String.format("Medium: %s", medium));
        artworkPrice.setText(String.format("Price: $%s", price));
        artworkDateCreated.setText(String.format("Created: %s", formattedDate));

        // Load image using Glide
        Glide.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.pillars)
                .into(artworkImage);

        // Check if artwork is a favorite
        checkIfFavorite();

        // Set favorite button click listener
        favoriteButton.setOnClickListener(v -> toggleFavorite());
    }

    private void checkIfFavorite() {
        userManager.getUser(currentUserId, new FirestoreUserManager.FirestoreCallback<User>() {
            @Override
            public void onSuccess(User user) {
                List<String> favoriteArtworks = user.getFavoriteArtworks();
                isFavorite = favoriteArtworks.contains(artwork.getId());
                updateFavoriteButton();
            }

            @Override
            public void onFailure(Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void toggleFavorite() {
        userManager.getUser(currentUserId, new FirestoreUserManager.FirestoreCallback<User>() {
            @Override
            public void onSuccess(User user) {
                List<String> favoriteArtworks = user.getFavoriteArtworks();

                if (isFavorite) {
                    // Remove artwork from favorites
                    favoriteArtworks.remove(artwork.getId());
                } else {
                    // Add artwork to favorites
                    favoriteArtworks.add(artwork.getId());
                }

                // Update user data in Firestore
                userManager.updateUserField(currentUserId, "favoriteArtworks", favoriteArtworks, new FirestoreUserManager.FirestoreCallback<Void>() {
                    @Override
                    public void onSuccess(Void data) {
                        isFavorite = !isFavorite; // Toggle favorite state
                        updateFavoriteButton(); // Update button UI
                    }

                    @Override
                    public void onFailure(Exception e) {
                        e.printStackTrace();
                    }
                });
            }

            @Override
            public void onFailure(Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void updateFavoriteButton() {
        if (isFavorite) {
            favoriteButton.setImageResource(R.drawable.star_filled); // Filled star
        } else {
            favoriteButton.setImageResource(R.drawable.star_empty); // Empty star
        }
    }

    // Handle back button press in action bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed(); // Navigate back to the previous activity
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // Clear the activity from the stack if needed
        finish();
    }
}
