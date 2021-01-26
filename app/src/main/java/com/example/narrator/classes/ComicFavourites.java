package com.example.narrator.classes;

public class ComicFavourites {

    private int CFID;
    private int CID;
    private int UID;

    public ComicFavourites(int CID, int UID) {
        this.CID = CID;
        this.UID = UID;
    }

    public int getCFID() {
        return CFID;
    }

    public void setCFID(int CFID) {
        this.CFID = CFID;
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
}

