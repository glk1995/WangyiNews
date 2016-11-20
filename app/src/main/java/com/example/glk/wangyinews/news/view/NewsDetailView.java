package com.example.glk.wangyinews.news.view;

/**
 * Created by zgqdg on 2016/11/16.
 */

public interface NewsDetailView {

    void showNewsDetailContent(String newsDetailContent,String url);

    void showProgress();

    void hideProgress();

}
