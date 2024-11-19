package rmit.ad.assignment1_s3929513.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

// Include Glide for image loading
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import rmit.ad.assignment1_s3929513.R;
import rmit.ad.assignment1_s3929513.activity.DetailActivity;
import rmit.ad.assignment1_s3929513.model.Artwork;

public class ArtworkAdapter extends RecyclerView.Adapter<ArtworkAdapter.ArtworkViewHolder> {

    private List<Artwork> artworks;
    private Context context;

    public ArtworkAdapter(Context context, List<Artwork> artworks) {
        this.context = context;
        this.artworks = artworks;
    }

    @NonNull
    @Override
    public ArtworkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_artwork, parent, false);
        return new ArtworkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtworkViewHolder holder, int position) {
        Artwork artwork = artworks.get(position);

        holder.title.setText(artwork.getTitle());
        holder.artistName.setText(artwork.getArtistName());
        holder.description.setText(artwork.getDescription());

        Glide.with(holder.imageView.getContext())
                .load(artwork.getImageUrl())
                .placeholder(R.drawable.pillars)
                .into(holder.imageView);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), DetailActivity.class);
            intent.putExtra("userId", FirebaseAuth.getInstance().getCurrentUser().getUid()); // Pass userId
            intent.putExtra("artworkId", artwork.getId()); // Pass artwork ID
            intent.putExtra("title", artwork.getTitle());
            intent.putExtra("artistName", artwork.getArtistName());
            intent.putExtra("description", artwork.getDescription());
            intent.putExtra("imageUrl", artwork.getImageUrl());
            intent.putExtra("medium", artwork.getMedium());
            intent.putExtra("price", artwork.getPrice());
            intent.putExtra("dateCreated", artwork.getDateCreated().toString());
            holder.itemView.getContext().startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return artworks.size();
    }

    // Method to update the list of artworks
    public void updateList(List<Artwork> newArtworks) {
        this.artworks = newArtworks;
        notifyDataSetChanged();
    }

    // ViewHolder class
    public static class ArtworkViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView artistName;
        TextView description;
        ImageView imageView;

        public ArtworkViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.artwork_title);
            artistName = itemView.findViewById(R.id.artwork_name); // Ensure this ID matches your layout
            description = itemView.findViewById(R.id.artwork_description);
            imageView = itemView.findViewById(R.id.artwork_image);
        }
    }
}
