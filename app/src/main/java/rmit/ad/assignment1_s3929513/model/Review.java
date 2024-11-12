package rmit.ad.assignment1_s3929513.model;

import java.util.Date;

public class Review {
    private String id;
    private String artworkId;
    private String userId;
    private String comment;
    private int rating;
    private Date datePosted;

    public Review() {}

    public Review(String id, String artworkId, String userId, String comment,
                  int rating, Date datePosted) {
        this.id = id;
        this.artworkId = artworkId;
        this.userId = userId;
        this.comment = comment;
        this.rating = rating;
        this.datePosted = datePosted;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArtworkId() {
        return artworkId;
    }

    public void setArtworkId(String artworkId) {
        this.artworkId = artworkId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Date getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(Date datePosted) {
        this.datePosted = datePosted;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id='" + id + '\'' +
                ", artworkId='" + artworkId + '\'' +
                ", userId='" + userId + '\'' +
                ", comment='" + comment + '\'' +
                ", rating=" + rating +
                ", datePosted=" + datePosted +
                '}';
    }
}

