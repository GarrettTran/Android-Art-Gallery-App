package rmit.ad.assignment1_s3929513.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import rmit.ad.assignment1_s3929513.R;
import rmit.ad.assignment1_s3929513.model.Artist;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ArtistViewHolder> {

    private final Context context;
    private List<Artist> artistList;

    // Constructor to initialize the context and dataset
    public ArtistAdapter(Context context, List<Artist> artistList) {
        this.context = context;
        this.artistList = artistList;
    }

    @NonNull
    @Override
    public ArtistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each artist item
        View view = LayoutInflater.from(context).inflate(R.layout.item_artist, parent, false);
        return new ArtistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistViewHolder holder, int position) {
        // Get the artist at the current position
        Artist artist = artistList.get(position);

        // Bind artist data to views
        holder.artistName.setText(artist.getName());
        holder.artistNationality.setText(artist.getNationality());
        holder.artistBio.setText(artist.getBio());

        // Load artist profile image using Glide
        Glide.with(context)
                .load(artist.getProfileImageUrl())
                .placeholder(R.drawable.painter) // Placeholder image
                .into(holder.artistProfileImage);
    }

    @Override
    public int getItemCount() {
        return artistList.size(); // Return the size of the dataset
    }

    // Method to update the dataset and notify the adapter
    public void updateList(List<Artist> newArtists) {
        this.artistList = newArtists; // Update the dataset
        notifyDataSetChanged(); // Notify the RecyclerView to refresh
    }

    // ViewHolder class to hold the views for each artist item
    public static class ArtistViewHolder extends RecyclerView.ViewHolder {
        ImageView artistProfileImage;
        TextView artistName, artistNationality, artistBio;

        public ArtistViewHolder(@NonNull View itemView) {
            super(itemView);

            // Initialize views
            artistProfileImage = itemView.findViewById(R.id.artist_profile_image);
            artistName = itemView.findViewById(R.id.artist_name);
            artistNationality = itemView.findViewById(R.id.artist_nationality);
            artistBio = itemView.findViewById(R.id.artist_bio);
        }
    }
}
