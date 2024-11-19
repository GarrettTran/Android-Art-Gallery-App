package rmit.ad.assignment1_s3929513.model;

import java.util.List;

public class User {
    private String id; // Firebase Auth UID
    private String name;
    private String email;
    private String profileImageUrl;
    private List<String> favoriteArtworks; // List of artwork IDs

    public User() {
        // Required empty constructor for Firestore deserialization
    }

    public User(String id, String name, String email, String profileImageUrl, List<String> favoriteArtworks) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.profileImageUrl = profileImageUrl;
        this.favoriteArtworks = favoriteArtworks;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public List<String> getFavoriteArtworks() {
        return favoriteArtworks;
    }

    public void setFavoriteArtworks(List<String> favoriteArtworks) {
        this.favoriteArtworks = favoriteArtworks;
    }
}
