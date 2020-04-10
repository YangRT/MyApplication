package com.example.administrator.myapplication.view.layout_manager;

public class Bean {

    private String mContent;
    private int mColor;

    public Bean(String content, int color) {
        this.mContent = content;
        this.mColor = color;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        this.mContent = content;
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(int color) {
        this.mColor = color;
    }

}
