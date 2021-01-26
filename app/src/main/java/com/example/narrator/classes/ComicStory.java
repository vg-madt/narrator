package com.example.narrator.classes;

public class ComicStory implements Type {
    private int CID;
    private int UID;
    private String title;
    private String description;
    private String coverImage;
    private String genre;

    public ComicStory() {
    }

    public ComicStory(String title, String description, String coverImage, String genre) {
        this.title = title;
        this.description = description;
        this.coverImage = coverImage;
        this.genre = genre;
    }

    public int getCID() {
        return CID;
    }

    public void setCID(int CID) {
        this.CID = CID;
    }

    public int getUID() {
        return UID;
    }

    public void setUID(int UID) {
        this.UID = UID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
