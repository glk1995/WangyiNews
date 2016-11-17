package com.example.glk.wangyinews.news.presenter;

import com.example.glk.wangyinews.beans.NewsBean;
import com.example.glk.wangyinews.news.model.NewsModel;
import com.example.glk.wangyinews.news.model.NewsModelImpl;
import com.example.glk.wangyinews.news.model.OnLoadNewsListener;
import com.example.glk.wangyinews.news.view.NewsView;
import com.example.glk.wangyinews.utils.LogUtils;

import java.util.List;

/**
 * Created by zgqdg on 2016/11/16.
 */

public class NewsPrenterImpl implements NewsPresenter,OnLoadNewsListener {


    private static final String TAG = "NewsPresenterImpl";

    private NewsView newsView;
    private NewsModel newsModel;

    public NewsPrenterImpl(NewsView newsView){
        this.newsView = newsView;
        this.newsModel = new NewsModelImpl();
    }

    @Override
    public void loadNews(int type, int page) {
        String url = getUrl(type, page);
        LogUtils.d(TAG, url);
        //只有第一页的或者刷新的时候才显示刷新进度条
        if (page == 0) {
            newsView.showProgress();
        }
        newsModel.loadNews(url,type,this);
    }

    /**
     * 根据类别和页面索引创建url
     * @param   type
     * @param   page
     * @return
     */
    private String getUrl(int type, int page) {
        StringBuffer sb = new StringBuffer();
        switch (type){

        }
        return null;
    }

    @Override
    public void onSuccess(List<NewsBean> list) {

    }

    @Override
    public void onFailure(String msg, Exception e) {

    }
}
