package rmit.ad.assignment1_s3929513.helper;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import rmit.ad.assignment1_s3929513.R;
import rmit.ad.assignment1_s3929513.activity.ArtistActivity;
import rmit.ad.assignment1_s3929513.activity.FavoriteActivity;
import rmit.ad.assignment1_s3929513.activity.GenerateArtActivity;
import rmit.ad.assignment1_s3929513.activity.HomeActivity;
import rmit.ad.assignment1_s3929513.activity.UserActivity;

public class BottomNavigationHelper {

    public static void setupBottomNavigation(BottomNavigationView bottomNavigationView, Context context) {
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            Intent intent = null;
            if (id == R.id.nav_home) {
                intent = new Intent(context, HomeActivity.class);
            } else if (id == R.id.nav_favorite) {
                intent = new Intent(context, FavoriteActivity.class);
            } else if (id == R.id.nav_user) {
                intent = new Intent(context, UserActivity.class);
            } else if (id == R.id.nav_artist) {
                intent = new Intent(context, ArtistActivity.class);
            } else if (id == R.id.nav_AI) {
                intent = new Intent(context, GenerateArtActivity.class);
            }

            if (intent != null) {
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                context.startActivity(intent);
            }
            return true;
        });
    }

}
