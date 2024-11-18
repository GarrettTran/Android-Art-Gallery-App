package rmit.ad.assignment1_s3929513.adapter;

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
import rmit.ad.assignment1_s3929513.model.Artwork;

public class ArtworkAdapter extends RecyclerView.Adapter<ArtworkAdapter.ArtworkViewHolder> {

    private List<Artwork> artworks;

    public ArtworkAdapter(List<Artwork> artworks) {
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
        holder.artistId.setText("Artist: " + artwork.getArtistId());
        holder.description.setText(artwork.getDescription());

        // Load the image using Glide
        Glide.with(holder.imageView.getContext())
                .load(artwork.getImageUrl())
                .placeholder(R.drawable.pillars)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return artworks.size();
    }

    public void updateList(List<Artwork> newArtworks) {
        this.artworks = newArtworks;
        notifyDataSetChanged();
    }

    public static class ArtworkViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView artistId;
        TextView description;
        ImageView imageView;

        public ArtworkViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.artwork_title);
            artistId = itemView.findViewById(R.id.artwork_artist_id);
            description = itemView.findViewById(R.id.artwork_description);
            imageView = itemView.findViewById(R.id.artwork_image);
        }
    }
}
