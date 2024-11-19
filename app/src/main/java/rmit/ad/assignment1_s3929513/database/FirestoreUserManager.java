package rmit.ad.assignment1_s3929513.database;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rmit.ad.assignment1_s3929513.model.User;

public class FirestoreUserManager {

    private final FirebaseFirestore db;
    private static final String USERS_COLLECTION = "Users";

    public FirestoreUserManager() {
        this.db = FirebaseFirestore.getInstance();
    }

    // Save or Update User
    public void saveUser(User user, FirestoreCallback<Void> callback) {
        db.collection(USERS_COLLECTION)
                .document(user.getId()) // Use UID as document ID
                .set(user, SetOptions.merge()) // Merge data to avoid overwriting
                .addOnSuccessListener(aVoid -> {
                    if (callback != null) callback.onSuccess(aVoid);
                })
                .addOnFailureListener(e -> {
                    if (callback != null) callback.onFailure(e);
                });
    }

    // Retrieve User by UID
    public void getUser(String uid, FirestoreCallback<User> callback) {
        db.collection(USERS_COLLECTION)
                .document(uid)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        User user = documentSnapshot.toObject(User.class);
                        if (callback != null) callback.onSuccess(user);
                    } else {
                        if (callback != null) callback.onFailure(new Exception("User not found"));
                    }
                })
                .addOnFailureListener(e -> {
                    if (callback != null) callback.onFailure(e);
                });
    }

    // Update Specific Fields (e.g., favorites)
    public void updateUserField(String uid, String field, Object value, FirestoreCallback<Void> callback) {
        Map<String, Object> updates = new HashMap<>();
        updates.put(field, value);

        db.collection(USERS_COLLECTION)
                .document(uid)
                .update(updates)
                .addOnSuccessListener(aVoid -> {
                    if (callback != null) callback.onSuccess(aVoid);
                })
                .addOnFailureListener(e -> {
                    if (callback != null) callback.onFailure(e);
                });
    }

    // Update User's Favorite Artworks
    public void updateUserFavorites(String uid, List<String> favoriteArtworks, FirestoreCallback<Void> callback) {
        updateUserField(uid, "favoriteArtworks", favoriteArtworks, callback);
    }

    // Check if an Artwork is in User's Favorites
    public void isArtworkFavorited(String uid, String artworkId, FirestoreCallback<Boolean> callback) {
        getUser(uid, new FirestoreCallback<User>() {
            @Override
            public void onSuccess(User user) {
                if (user != null && user.getFavoriteArtworks() != null) {
                    callback.onSuccess(user.getFavoriteArtworks().contains(artworkId));
                } else {
                    callback.onSuccess(false);
                }
            }

            @Override
            public void onFailure(Exception e) {
                callback.onFailure(e);
            }
        });
    }

    // Retrieve All Users
    public void getAllUsers(FirestoreCallback<List<User>> callback) {
        db.collection(USERS_COLLECTION)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    List<User> users = querySnapshot.toObjects(User.class);
                    if (callback != null) callback.onSuccess(users);
                })
                .addOnFailureListener(e -> {
                    if (callback != null) callback.onFailure(e);
                });
    }

    // Delete User by UID
    public void deleteUser(String uid, FirestoreCallback<Void> callback) {
        db.collection(USERS_COLLECTION)
                .document(uid)
                .delete()
                .addOnSuccessListener(aVoid -> {
                    if (callback != null) callback.onSuccess(aVoid);
                })
                .addOnFailureListener(e -> {
                    if (callback != null) callback.onFailure(e);
                });
    }

    // Add an Artwork to User's Favorites
    public void addFavoriteArtwork(String uid, String artworkId, FirestoreCallback<Void> callback) {
        getUser(uid, new FirestoreCallback<User>() {
            @Override
            public void onSuccess(User user) {
                if (user != null) {
                    List<String> favorites = user.getFavoriteArtworks();
                    if (!favorites.contains(artworkId)) {
                        favorites.add(artworkId);
                        updateUserFavorites(uid, favorites, callback);
                    } else {
                        callback.onSuccess(null);
                    }
                }
            }

            @Override
            public void onFailure(Exception e) {
                callback.onFailure(e);
            }
        });
    }

    // Remove an Artwork from User's Favorites
    public void removeFavoriteArtwork(String uid, String artworkId, FirestoreCallback<Void> callback) {
        getUser(uid, new FirestoreCallback<User>() {
            @Override
            public void onSuccess(User user) {
                if (user != null) {
                    List<String> favorites = user.getFavoriteArtworks();
                    if (favorites.contains(artworkId)) {
                        favorites.remove(artworkId);
                        updateUserFavorites(uid, favorites, callback);
                    } else {
                        callback.onSuccess(null);
                    }
                }
            }

            @Override
            public void onFailure(Exception e) {
                callback.onFailure(e);
            }
        });
    }

    // Update User's Profile Image URL and Name
    public void updateUserProfile(String uid, String newName, String profileImageUrl, FirestoreCallback<Boolean> callback) {
        Map<String, Object> updates = new HashMap<>();
        updates.put("name", newName);
        updates.put("profileImageUrl", profileImageUrl);

        db.collection(USERS_COLLECTION)
                .document(uid)
                .update(updates)
                .addOnSuccessListener(aVoid -> {
                    if (callback != null) callback.onSuccess(true);
                })
                .addOnFailureListener(e -> {
                    if (callback != null) callback.onFailure(e);
                });
    }


    public void updateUserName(String uid, String newName, FirestoreCallback<Boolean> callback) {
        db.collection(USERS_COLLECTION)
                .document(uid)
                .update("name", newName)
                .addOnSuccessListener(aVoid -> {
                    if (callback != null) callback.onSuccess(true);
                })
                .addOnFailureListener(e -> {
                    if (callback != null) callback.onFailure(e);
                });
    }

    public interface SimpleCallback<T> {
        void onComplete(T result);
    }

    public interface FirestoreCallback<T> {
        void onSuccess(T data);
        void onFailure(Exception e);
    }

}
