package com.example.narrator.classes;

public class TextFavourites {

    private int TFID;
    private int TID;
    private int UID;

    public TextFavourites(int TID, int UID) {
        this.TID = TID;
        this.UID = UID;
    }

    public int getTFID() {
        return TFID;
    }

    public void setTFID(int TFID) {
        this.TFID = TFID;
    }

    public int getTID() {
        return TID;
    }

    public void setTID(int TID) {
        this.TID = TID;
    }

    public int getUID() {
        return UID;
    }

    public void setUID(int UID) {
        this.UID = UID;
    }
}
