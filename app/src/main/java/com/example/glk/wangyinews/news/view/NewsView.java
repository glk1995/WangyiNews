package com.example.glk.wangyinews.news.view;

import com.example.glk.wangyinews.beans.NewsBean;

import java.util.List;

/**
 * Created by zgqdg on 2016/11/16.
 */

public interface NewsView {

    void showProgress();

    void addNews(List<NewsBean> newsList);

    void hideProgress();

    void showLoadFailMsg();

}
