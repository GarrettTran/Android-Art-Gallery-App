package rmit.ad.assignment1_s3929513.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import rmit.ad.assignment1_s3929513.R;
import rmit.ad.assignment1_s3929513.database.FirestoreUserManager;
import rmit.ad.assignment1_s3929513.helper.CustomToast;
import rmit.ad.assignment1_s3929513.model.User;

public class UserActivity extends AppCompatActivity {

    private ImageView userImageView;
    private EditText usernameField, imageUrlField;
    private TextView userEmailView;
    private Button saveButton, signOutButton;

    private FirebaseAuth mAuth;
    private FirestoreUserManager userManager;
    private String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        // Initialize Firebase
        mAuth = FirebaseAuth.getInstance();
        userManager = new FirestoreUserManager();

        // Get current user
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            redirectToLogin();
            return;
        }

        currentUserId = currentUser.getUid();

        // Bind views
        userImageView = findViewById(R.id.user_image);
        usernameField = findViewById(R.id.username_field);
        imageUrlField = findViewById(R.id.url_field); // New field for inputting image URL
        userEmailView = findViewById(R.id.user_email);
        saveButton = findViewById(R.id.save_button);
        signOutButton = findViewById(R.id.sign_out_button);

        // Set user email
        userEmailView.setText(currentUser.getEmail());

        // Fetch user data
        fetchUserData();

        // Handle save button click
        saveButton.setOnClickListener(v -> saveUserData());

        // Handle sign-out button click
        signOutButton.setOnClickListener(v -> showSignOutConfirmationDialog());

        // Set up bottom navigation bar
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        rmit.ad.assignment1_s3929513.helper.BottomNavigationHelper.setupBottomNavigation(bottomNavigationView, this);
    }

    private void fetchUserData() {
        userManager.getUser(currentUserId, new FirestoreUserManager.FirestoreCallback<User>() {
            @Override
            public void onSuccess(User user) {
                if (user != null) {
                    // Set username or use a default name
                    String username = user.getName() != null ? user.getName() : "Unknown User";
                    usernameField.setText(username);

                    // Set image URL or placeholder
                    String imageUrl = user.getProfileImageUrl();
                    imageUrlField.setText(imageUrl != null ? imageUrl : ""); // Populate URL in the new field
                    Glide.with(UserActivity.this)
                            .load(imageUrl != null ? imageUrl : R.drawable.painter)
                            .placeholder(R.drawable.painter)
                            .circleCrop()
                            .into(userImageView);
                } else {
                    Log.w("fetchUserData", "User data is null");
                }
            }

            @Override
            public void onFailure(Exception e) {
                Log.e("fetchUserData", "Failed to fetch user data", e);
                CustomToast.makeText(UserActivity.this, "Error fetching user data. Please try again.",
                        CustomToast.SHORT, CustomToast.ERROR, true).show();
            }
        });
    }

    private void saveUserData() {
        String newUsername = usernameField.getText().toString().trim();
        String newImageUrl = imageUrlField.getText().toString().trim(); // Get the image URL from the field

        if (newUsername.isEmpty()) {
            usernameField.setError("Username cannot be empty");
            return;
        }

        if (newImageUrl.isEmpty()) {
            imageUrlField.setError("Image URL cannot be empty");
            return;
        }

        // Update Firestore with new username and image URL
        userManager.updateUserProfile(currentUserId, newUsername, newImageUrl, new FirestoreUserManager.FirestoreCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean success) {
                if (success) {
                    Glide.with(UserActivity.this)
                            .load(newImageUrl)
                            .placeholder(R.drawable.painter)
                            .circleCrop()
                            .into(userImageView);
                    CustomToast.makeText(UserActivity.this, "User profile updated successfully.",
                            CustomToast.SHORT, CustomToast.SUCCESS, true).show();
                }
            }

            @Override
            public void onFailure(Exception e) {
                Log.e("UserActivity", "Error updating user profile", e);
                CustomToast.makeText(UserActivity.this, "Failed to update profile. Please try again.",
                        CustomToast.SHORT, CustomToast.ERROR, true).show();
            }
        });
    }

    private void showSignOutConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Sign Out")
                .setMessage("Are you sure you want to sign out?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    mAuth.signOut();
                    redirectToLogin();
                })
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void redirectToLogin() {
        Intent intent = new Intent(UserActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
