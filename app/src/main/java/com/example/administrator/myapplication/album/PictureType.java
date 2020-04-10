package com.example.administrator.myapplication.album;

public class PictureType {

    private int num;
    private String type;
    private String path;
    private boolean chose = false;

    public void setChose(boolean chose) {
        this.chose = chose;
    }


    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void addNum(){
        num += 1;
    }

    public boolean getChose() {
        return chose;
    }
}
