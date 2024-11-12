package rmit.ad.assignment1_s3929513.model;

import java.util.Date;

public class Artist {
    private String id;
    private String name;
    private String bio;
    private String nationality;
    private Date dateOfBirth;
    private String profileImageUrl;

    public Artist() {}

    public Artist(String id, String name, String bio, String nationality,
                  Date dateOfBirth, String profileImageUrl) {
        this.id = id;
        this.name = name;
        this.bio = bio;
        this.nationality = nationality;
        this.dateOfBirth = dateOfBirth;
        this.profileImageUrl = profileImageUrl;
    }

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

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    @Override
    public String toString() {
        return "Artist{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", bio='" + bio + '\'' +
                ", nationality='" + nationality + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", profileImageUrl='" + profileImageUrl + '\'' +
                '}';
    }
}

