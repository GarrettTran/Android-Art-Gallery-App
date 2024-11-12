package rmit.ad.assignment1_s3929513.model;

import java.util.Date;

public class Artwork {
    private String id;
    private String title;
    private String artistId;
    private String description;
    private String medium;
    private String imageUrl;
    private double price;
    private boolean isFeatured;
    private Date dateCreated;

    public Artwork() {}

    public Artwork(String id, String title, String artistId, String description,
                   String medium, String imageUrl, double price, boolean isFeatured,
                   Date dateCreated) {
        this.id = id;
        this.title = title;
        this.artistId = artistId;
        this.description = description;
        this.medium = medium;
        this.imageUrl = imageUrl;
        this.price = price;
        this.isFeatured = isFeatured;
        this.dateCreated = dateCreated;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtistId() {
        return artistId;
    }

    public void setArtistId(String artistId) {
        this.artistId = artistId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isFeatured() {
        return isFeatured;
    }

    public void setFeatured(boolean featured) {
        isFeatured = featured;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public String toString() {
        return "Artwork{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", artistId='" + artistId + '\'' +
                ", description='" + description + '\'' +
                ", medium='" + medium + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", price=" + price +
                ", isFeatured=" + isFeatured +
                ", dateCreated=" + dateCreated +
                '}';
    }
}

