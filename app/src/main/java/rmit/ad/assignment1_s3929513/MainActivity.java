package rmit.ad.assignment1_s3929513;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.DialogInterface;



import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText emailField, passwordField;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
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
                        // Registration successful, user is logged in
                        FirebaseUser user = mAuth.getCurrentUser();
                        CustomToast.makeText(MainActivity.this,"Registration Successful",CustomToast.SHORT,CustomToast.SUCCESS,true).show();

                    } else {
                        // Registration failed, display a message
                        CustomToast.makeText(MainActivity.this,"Registration Failed: " + task.getException().getMessage(),CustomToast.SHORT,CustomToast.ERROR,true).show();
                    }
                });
    }

    private void loginUser(String email, String password) {
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    progressBar.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        // Login successful, user is logged in
                        FirebaseUser user = mAuth.getCurrentUser();
                        CustomToast.makeText(MainActivity.this,"Login Successful",CustomToast.SHORT,CustomToast.SUCCESS,true).show();
                    } else {
                        // Login failed, display a message
                        CustomToast.makeText(MainActivity.this,"Login Failed: " + task.getException().getMessage(),CustomToast.SHORT,CustomToast.ERROR,true).show();
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // User is already logged in, proceed to the main app
            CustomToast.makeText(this, "Welcome Back " + currentUser.getEmail(), CustomToast.SHORT, CustomToast.SUCCESS, true).show();

        }
    }

    private void signOut() {
        mAuth.signOut();
        CustomToast.makeText(this, "Sign Out Successfully", CustomToast.SHORT, CustomToast.SUCCESS, true).show();

    }

    private void showConfirmationDialog(String email, String password) {
        // Create an AlertDialog Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Set dialog properties
        builder.setTitle("Confirmation");
        builder.setMessage("Are you sure you want to create a new account?");

        // "Yes" button
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Action when user confirms
                registerUser(email, password);
            }
        });

        // "No" button
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Close the dialog
                dialog.dismiss();
            }
        });

        // Show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}

