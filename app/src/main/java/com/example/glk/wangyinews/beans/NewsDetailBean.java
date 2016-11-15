package com.example.glk.wangyinews.beans;

import java.io.Serializable;
import java.util.List;

/**新闻详情实体类
 * Created by zgqdg on 2016/11/13.
 */

public class NewsDetailBean implements Serializable {

    //docid
    private String docid;

    //title
    private String title;

    //source
    private String source;

    //body
    private String body;

    //ptime
    private String ptime;

    //cover
    private String cover;

    //图片列表
    private List<String> imgList;

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getBody() {

        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPtime() {
        return ptime;
    }

    public void setPtime(String ptime) {
        this.ptime = ptime;
    }

    public List<String> getImgList() {
        return imgList;
    }

    public void setImgList(List<String> imgList) {
        this.imgList = imgList;
    }
}
