package com.example.glk.wangyinews.news.model;

import com.example.glk.wangyinews.beans.NewsBean;
import com.example.glk.wangyinews.utils.OkHttpUtils;

import java.util.List;

/**新闻也业务处理类
 * Created by zgqdg on 2016/11/14.
 */

public class NewsModelImpl implements NewsModel {

    /**
     * 加载新闻列表
     * @param url
     * @param type
     * @param listener
     */
    @Override
    public void loadNews(String url, int type, OnLoadNewsListener listener) {
        OkHttpUtils.ResultCallback<String> loadNewsCallback = new OkHttpUtils.ResultCallback<String>() {
            @Override
            public void onSuccess(String response) {

            }

            @Override
            public void onFailure(Exception e) {

            }
        };
    }

    /**
     * 加载新闻详情
     * @param docid
     * @param listener
     */
    @Override
    public void loadNewsDetail(String docid, OnLoadNewsDetailListener listener) {

    }
}
