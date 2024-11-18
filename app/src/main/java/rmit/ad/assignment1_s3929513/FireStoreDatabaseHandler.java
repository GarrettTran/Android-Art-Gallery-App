package rmit.ad.assignment1_s3929513;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import rmit.ad.assignment1_s3929513.model.Artist;
import rmit.ad.assignment1_s3929513.model.Artwork;
import rmit.ad.assignment1_s3929513.model.Review;

public class FireStoreDatabaseHandler {

    private static final String TAG = "FirestoreDBHandler";
    private static final String ARTISTS_COLLECTION = "Artists";
    private static final String ARTWORKS_COLLECTION = "Artworks";
    private static final String REVIEWS_COLLECTION = "Reviews";

    private final FirebaseFirestore db;

    public FireStoreDatabaseHandler() {
        this.db = FirebaseFirestore.getInstance();
    }

    // ===================== ARTIST METHODS =====================

    public void fetchAllArtists(DataCallback<List<Artist>> callback) {
        db.collection(ARTISTS_COLLECTION)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<Artist> artists = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Artist artist = document.toObject(Artist.class);
                            artists.add(artist);
                        }
                        callback.onSuccess(artists);
                    } else {
                        callback.onFailure(task.getException());
                    }
                });
    }

    public void addArtist(Artist artist, DataCallback<Void> callback) {
        db.collection(ARTISTS_COLLECTION)
                .document(artist.getId())
                .set(artist)
                .addOnSuccessListener(unused -> callback.onSuccess(null))
                .addOnFailureListener(callback::onFailure);
    }

    public void deleteArtist(String artistId, DataCallback<Void> callback) {
        db.collection(ARTISTS_COLLECTION)
                .document(artistId)
                .delete()
                .addOnSuccessListener(unused -> callback.onSuccess(null))
                .addOnFailureListener(callback::onFailure);
    }

    // ===================== ARTWORK METHODS =====================

    public void fetchAllArtworks(DataCallback<List<Artwork>> callback) {
        db.collection(ARTWORKS_COLLECTION)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<Artwork> artworks = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Artwork artwork = document.toObject(Artwork.class);
                            artworks.add(artwork);
                        }
                        callback.onSuccess(artworks);
                    } else {
                        callback.onFailure(task.getException());
                    }
                });
    }

    public void fetchArtworksByArtist(String artistId, DataCallback<List<Artwork>> callback) {
        db.collection(ARTWORKS_COLLECTION)
                .whereEqualTo("artistId", artistId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<Artwork> artworks = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Artwork artwork = document.toObject(Artwork.class);
                            artworks.add(artwork);
                        }
                        callback.onSuccess(artworks);
                    } else {
                        callback.onFailure(task.getException());
                    }
                });
    }

    public void addArtwork(Artwork artwork, DataCallback<Void> callback) {
        db.collection(ARTWORKS_COLLECTION)
                .document(artwork.getId())
                .set(artwork)
                .addOnSuccessListener(unused -> callback.onSuccess(null))
                .addOnFailureListener(callback::onFailure);
    }

    public void deleteArtwork(String artworkId, DataCallback<Void> callback) {
        db.collection(ARTWORKS_COLLECTION)
                .document(artworkId)
                .delete()
                .addOnSuccessListener(unused -> callback.onSuccess(null))
                .addOnFailureListener(callback::onFailure);
    }

    // ===================== REVIEW METHODS =====================

    public void fetchReviewsByArtwork(String artworkId, DataCallback<List<Review>> callback) {
        db.collection(REVIEWS_COLLECTION)
                .whereEqualTo("artworkId", artworkId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<Review> reviews = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Review review = document.toObject(Review.class);
                            reviews.add(review);
                        }
                        callback.onSuccess(reviews);
                    } else {
                        callback.onFailure(task.getException());
                    }
                });
    }

    public void addReview(Review review, DataCallback<Void> callback) {
        db.collection(REVIEWS_COLLECTION)
                .document(review.getId())
                .set(review)
                .addOnSuccessListener(unused -> callback.onSuccess(null))
                .addOnFailureListener(callback::onFailure);
    }

    public void deleteReview(String reviewId, DataCallback<Void> callback) {
        db.collection(REVIEWS_COLLECTION)
                .document(reviewId)
                .delete()
                .addOnSuccessListener(unused -> callback.onSuccess(null))
                .addOnFailureListener(callback::onFailure);
    }

    // ===================== CALLBACK INTERFACE =====================

    public interface DataCallback<T> {
        void onSuccess(T result);
        void onFailure(Exception e);
    }
}
