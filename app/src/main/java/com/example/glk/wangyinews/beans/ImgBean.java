package com.example.glk.wangyinews.beans;

import java.io.Serializable;

/**
 * Created by zgqdg on 2016/11/19.
 */

public class ImgBean implements Serializable {

    private String alt;

    private String pixel;

    private String ref;

    private String src;

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getPixel() {
        return pixel;
    }

    public void setPixel(String pixel) {
        this.pixel = pixel;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }
}
