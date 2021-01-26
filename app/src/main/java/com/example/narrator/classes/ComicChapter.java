package com.example.narrator.classes;

public class ComicChapter {

    private int CCID;
    private int CID;
    private String title;

    public ComicChapter(int CID, String title) {
        this.CID = CID;
        this.title = title;
    }

    public int getCCID() {
        return CCID;
    }

    public void setCCID(int CCID) {
        this.CCID = CCID;
    }

    public int getCID() {
        return CID;
    }

    public void setCID(int CID) {
        this.CID = CID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
