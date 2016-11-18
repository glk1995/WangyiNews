package com.example.glk.wangyinews.news.presenter;

import android.content.Context;
import android.util.Log;

import com.example.glk.wangyinews.beans.NewsDetailBean;
import com.example.glk.wangyinews.news.model.NewsModel;
import com.example.glk.wangyinews.news.model.NewsModelImpl;
import com.example.glk.wangyinews.news.model.OnLoadNewsDetailListener;
import com.example.glk.wangyinews.news.view.NewsDetailView;
import com.example.glk.wangyinews.utils.LogUtils;

/**
 * Created by zgqdg on 2016/11/17.
 */

public class NewsDetailPresenterImpl implements NewsDetailPresenter,OnLoadNewsDetailListener{

    private Context mContext;
    private NewsDetailView newsDetailView;
    private NewsModel newsModel;

    public NewsDetailPresenterImpl(Context mContextm,NewsDetailView newsDetailView){
        this.mContext = mContextm;
        this.newsDetailView = newsDetailView;
        newsModel = new NewsModelImpl();
    }

    @Override
    public void loadNewsDetail(String docid) {
        newsDetailView.showProgress();
        newsModel.loadNewsDetail(docid,this);
    }

    //获取数据成功后，若内容不为空，则显示内容，隐藏进度条
    @Override
    public void onSuccess(NewsDetailBean newsDetailBean) {
        if(newsDetailBean != null){
            Log.e("FFFFF",newsDetailBean.getDocid());
            newsDetailView.showNewsDetailContent(newsDetailBean.getBody());
        }
        newsDetailView.hideProgress();
    }

    @Override
    public void onFailure(String msg, Exception e) {
        newsDetailView.hideProgress();
    }

}
