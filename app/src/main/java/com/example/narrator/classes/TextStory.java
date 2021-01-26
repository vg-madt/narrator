package com.example.narrator.classes;

public class TextStory implements Type{
    private int TID;
    private int UID;
    private String title;
    private String description;
    private String coverImage;
    private String genre;

    public TextStory(String title, String description, String coverImage, String genre) {
        this.title = title;
        this.description = description;
        this.coverImage = coverImage;
        this.genre = genre;
    }

    public TextStory() {

    }

    public int getUID() {
        return UID;
    }

    public void setUID(int UID) {
        this.UID = UID;
    }

    public int getTID() {
        return TID;
    }

    public void setTID(int TID) {
        this.TID = TID;
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
