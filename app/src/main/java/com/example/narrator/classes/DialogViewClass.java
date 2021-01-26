package com.example.narrator.classes;

public class DialogViewClass {

    private int CDID;
    private int CSID;
    private String img;
    private int leftM;
    private int topM;
    private int tag;
    private boolean isSaved;

    public DialogViewClass(int CSID, String img, int leftM, int topM, int tag) {
        this.CSID = CSID;
        this.img = img;
        this.leftM = leftM;
        this.topM = topM;
        this.tag = tag;
    }

    public int getCDID() {
        return CDID;
    }

    public void setCDID(int CDID) {
        this.CDID = CDID;
    }

    public int getCSID() {
        return CSID;
    }

    public void setCSID(int CSID) {
        this.CSID = CSID;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getLeftM() {
        return leftM;
    }

    public void setLeftM(int leftM) {
        this.leftM = leftM;
    }

    public int getTopM() {
        return topM;
    }

    public void setTopM(int topM) {
        this.topM = topM;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public boolean getSaved() {
        return isSaved;
    }

    public void setSaved(boolean saved) {
        isSaved = saved;
    }
}
