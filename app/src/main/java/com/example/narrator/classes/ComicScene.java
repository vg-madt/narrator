package com.example.narrator.classes;

public class ComicScene {
    private int CSID;
    private int CCID;
    private String title;

    public ComicScene(int CCID, String title) {
        this.CCID = CCID;
        this.title = title;
    }

    public int getCSID() {
        return CSID;
    }

    public void setCSID(int CSID) {
        this.CSID = CSID;
    }

    public int getCCID() {
        return CCID;
    }

    public void setCCID(int CCID) {
        this.CCID = CCID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
