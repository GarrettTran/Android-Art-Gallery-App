package rmit.ad.assignment1_s3929513.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import rmit.ad.assignment1_s3929513.R;
import rmit.ad.assignment1_s3929513.helper.CustomToast;

public class GenerateArtActivity extends AppCompatActivity {

    private static final String API_URL = "https://api.stability.ai/v2beta/stable-image/generate/core";
    private static final String API_KEY = "provide_your_API_key"; // Replace with your Stability.AI API Key

    private EditText promptInput;
    private ImageView generatedImage;
    private Button generateButton, downloadButton;
    private ProgressBar progressBar;

    private byte[] currentImageData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_art);

        promptInput = findViewById(R.id.prompt_input);
        generatedImage = findViewById(R.id.generated_image);
        generateButton = findViewById(R.id.generate_button);
        downloadButton = findViewById(R.id.download_button);
        progressBar = findViewById(R.id.progress_bar);

        // Set up Bottom Navigation Bar
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        rmit.ad.assignment1_s3929513.helper.BottomNavigationHelper.setupBottomNavigation(bottomNavigationView, this);

        generateButton.setOnClickListener(v -> generateArt());
        downloadButton.setOnClickListener(v -> downloadImage());

        // Request storage permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1
            );
        }
    }

    private void generateArt() {
        String prompt = promptInput.getText().toString().trim();
        if (prompt.isEmpty()) {
            CustomToast.makeText(GenerateArtActivity.this,"Please enter a prompt",CustomToast.SHORT,CustomToast.ERROR,true).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        OkHttpClient client = new OkHttpClient();

        // Create the multipart body
        MultipartBody.Builder bodyBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        bodyBuilder.addFormDataPart("prompt", prompt); // Required
        bodyBuilder.addFormDataPart("output_format", "webp"); // Example optional parameter
        bodyBuilder.addFormDataPart("style_preset", "anime"); // Optional style preset

        RequestBody body = bodyBuilder.build();

        // Build the request
        Request request = new Request.Builder()
                .url(API_URL)
                .addHeader("Authorization", "Bearer " + API_KEY) // Required
                .addHeader("Accept", "image/*") // For raw image bytes
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> {
                    progressBar.setVisibility(View.GONE);
                    CustomToast.makeText(GenerateArtActivity.this,"Failed to generate art",CustomToast.SHORT,CustomToast.ERROR,true).show();

                    Log.e("GenerateArt", "Request Failed: ", e);
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    currentImageData = response.body().bytes(); // Save the image data as raw bytes

                    runOnUiThread(() -> {
                        // Load the generated image into the ImageView
                        Glide.with(GenerateArtActivity.this)
                                .load(currentImageData)
                                .into(generatedImage);
                        downloadButton.setVisibility(View.VISIBLE); // Show the download button
                        progressBar.setVisibility(View.GONE); // Hide progress bar
                    });
                } else {
                    String errorResponse = response.body().string();
                    Log.e("GenerateArt", "Error Response Code: " + response.code());
                    Log.e("GenerateArt", "Error Response Body: " + errorResponse);
                    runOnUiThread(() -> {
                        progressBar.setVisibility(View.GONE);
                        CustomToast.makeText(GenerateArtActivity.this,"Error generating art: " + response.message(),CustomToast.SHORT,CustomToast.ERROR,true).show();
                    });
                }
            }
        });
    }

    private void downloadImage() {
        if (currentImageData == null || currentImageData.length == 0) {
            CustomToast.makeText(GenerateArtActivity.this,"No image to download",CustomToast.SHORT,CustomToast.ERROR,true).show();
            return;
        }

        new Thread(() -> {
            try {
                // Create the directory for saving images
                File directory = new File(
                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                        "AIArt"
                );
                if (!directory.exists()) {
                    directory.mkdirs();
                }

                // Save the image file
                File file = new File(directory, "art_" + System.currentTimeMillis() + ".webp");
                FileOutputStream outputStream = new FileOutputStream(file);
                outputStream.write(currentImageData);
                outputStream.flush();
                outputStream.close();

                // Notify the media scanner about the new file
                runOnUiThread(() -> {
                    CustomToast.makeText(
                            this,
                            "Image saved: " + file.getAbsolutePath(), CustomToast.SHORT, CustomToast.LENGTH_SHORT,true
                    ).show();

                    // Notify the gallery about the new image
                    Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    intent.setData(android.net.Uri.fromFile(file));
                    sendBroadcast(intent);
                });
            } catch (Exception e) {
                runOnUiThread(() -> {
                    CustomToast.makeText(GenerateArtActivity.this,"Error saving image",CustomToast.SHORT,CustomToast.ERROR,true).show();

                    Log.e("DownloadImage", "Error: ", e);
                });
            }
        }).start();
    }

}
