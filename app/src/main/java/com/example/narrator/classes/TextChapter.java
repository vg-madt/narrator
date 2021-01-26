package com.example.narrator.classes;

public class TextChapter {
    private int TCID;
    private int TID;
    private String title;

    public TextChapter(String title) {
        this.title = title;
    }

    public int getTCID() {
        return TCID;
    }

    public void setTCID(int TCID) {
        this.TCID = TCID;
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
}
