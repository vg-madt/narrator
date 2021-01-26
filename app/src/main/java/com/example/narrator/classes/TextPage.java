package com.example.narrator.classes;

public class TextPage {
    private int TPID;
    private int TCID;
    private String story;


    public TextPage(){

    }
    public TextPage(int TCID, String story) {
        this.TCID = TCID;
        this.story = story;
    }

    public int getTPID() {
        return TPID;
    }

    public void setTPID(int TPID) {
        this.TPID = TPID;
    }

    public int getTCID() {
        return TCID;
    }

    public void setTCID(int TCID) {
        this.TCID = TCID;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }
}

