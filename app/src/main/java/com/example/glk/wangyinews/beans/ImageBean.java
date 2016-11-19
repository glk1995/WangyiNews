package com.example.glk.wangyinews.beans;

import java.io.Serializable;

/**
 * Created by zgqdg on 2016/11/13.
 */

public class ImageBean implements Serializable {
    public static final long serialVersionUID = 11;

    private String title;

    private String thumburl;

    private String sourceurl;

    private int height;

    private int width;



    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getSourceurl() {
        return sourceurl;
    }

    public void setSourceurl(String sourceurl) {
        this.sourceurl = sourceurl;
    }

    public String getThumburl() {
        return thumburl;
    }

    public void setThumburl(String thumburl) {
        this.thumburl = thumburl;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
