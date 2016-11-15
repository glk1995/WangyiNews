package com.example.glk.wangyinews.news.model;

import com.example.glk.wangyinews.beans.NewsBean;

import java.util.List;

/**新闻列表加载回调
 * Created by zgqdg on 2016/11/14.
 */

public interface OnLoadNewsListener {

    void onSuccess(List<NewsBean> list);

    void onFailure(String msg,Exception e);

}
