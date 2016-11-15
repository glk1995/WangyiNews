package com.example.glk.wangyinews.news.model;

/**
 * Created by zgqdg on 2016/11/14.
 */

public interface NewsModel {

    void loadNews(String url,int type,OnLoadNewsListener listener);

    void loadNewsDetail(String docid,OnLoadNewsDetailListener listener);

}
