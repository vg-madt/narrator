package com.example.narrator.classes;

public class ImageViewClass {
    private int CIID;
    private int CSID;
    private String img;
    private int leftM;
    private int topM;
    private int tag;
    private boolean isSaved;


    public ImageViewClass(String img, int leftM, int topM, int tag) {
        this.img = img;
        this.leftM = leftM;
        this.topM = topM;
        this.tag = tag;
    }

    public int getCIID() {
        return CIID;
    }

    public void setCIID(int CIID) {
        this.CIID = CIID;
    }

    public int getCSID() {
        return CSID;
    }

    public void setCSID(int CSID) {
        this.CSID = CSID;
    }


    public boolean getSaved() {
        return isSaved;
    }

    public void setSaved(boolean saved) {
        isSaved = saved;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
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
}
