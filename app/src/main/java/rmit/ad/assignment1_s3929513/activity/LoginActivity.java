package rmit.ad.assignment1_s3929513.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.app.AlertDialog;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import rmit.ad.assignment1_s3929513.helper.CustomToast;
import rmit.ad.assignment1_s3929513.R;
import rmit.ad.assignment1_s3929513.model.User;
import rmit.ad.assignment1_s3929513.database.FirestoreUserManager;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText emailField, passwordField;
    private ProgressBar progressBar;
    private FirestoreUserManager userManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        userManager = new FirestoreUserManager();

        emailField = findViewById(R.id.emailField);
        passwordField = findViewById(R.id.passwordField);
        progressBar = findViewById(R.id.progressBar);

        findViewById(R.id.registerButton).setOnClickListener(v -> {
            String email = emailField.getText().toString();
            String password = passwordField.getText().toString();
            showConfirmationDialog(email, password);
        });

        findViewById(R.id.loginButton).setOnClickListener(v -> {
            String email = emailField.getText().toString();
            String password = passwordField.getText().toString();
            loginUser(email, password);
        });
    }

    private void registerUser(String email, String password) {
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    progressBar.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = mAuth.getCurrentUser();
                        if (firebaseUser != null) {
                            // Create new User object
                            String uid = firebaseUser.getUid();
                            User user = new User(uid, "New User", email, "", new ArrayList<>());

                            // Save user to Firestore
                            userManager.saveUser(user, new FirestoreUserManager.FirestoreCallback<Void>() {
                                @Override
                                public void onSuccess(Void data) {
                                    CustomToast.makeText(LoginActivity.this, "Registration Successful", CustomToast.SHORT, CustomToast.SUCCESS, true).show();
                                    navigateToHome();
                                }

                                @Override
                                public void onFailure(Exception e) {
                                    CustomToast.makeText(LoginActivity.this, "Failed to save user data: " + e.getMessage(), CustomToast.SHORT, CustomToast.ERROR, true).show();
                                }
                            });
                        }
                    } else {
                        CustomToast.makeText(LoginActivity.this, "Registration Failed: " + task.getException().getMessage(), CustomToast.SHORT, CustomToast.ERROR, true).show();
                    }
                });
    }

    private void loginUser(String email, String password) {
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    progressBar.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = mAuth.getCurrentUser();
                        if (firebaseUser != null) {
                            // Retrieve user data to ensure Firestore is updated
                            String uid = firebaseUser.getUid();
                            userManager.getUser(uid, new FirestoreUserManager.FirestoreCallback<User>() {
                                @Override
                                public void onSuccess(User user) {
                                    CustomToast.makeText(LoginActivity.this, "Login Successful", CustomToast.SHORT, CustomToast.SUCCESS, true).show();
                                    navigateToHome();
                                }

                                @Override
                                public void onFailure(Exception e) {
                                    CustomToast.makeText(LoginActivity.this, "User data not found, creating default profile.", CustomToast.SHORT, CustomToast.ERROR, true).show();

                                    // If user data does not exist, create a new user document
                                    User newUser = new User(uid, "Existing User", email, "", new ArrayList<>());
                                    userManager.saveUser(newUser, null); // Save user without callback
                                    navigateToHome();
                                }
                            });
                        }
                    } else {
                        CustomToast.makeText(LoginActivity.this, "Login Failed: " + task.getException().getMessage(), CustomToast.SHORT, CustomToast.ERROR, true).show();
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            navigateToHome();
        }
    }

    private void navigateToHome() {
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
        finish(); // Close LoginActivity
    }

    private void showConfirmationDialog(String email, String password) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation");
        builder.setMessage("Are you sure you want to create a new account?");

        builder.setPositiveButton("Yes", (dialog, which) -> registerUser(email, password));
        builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
