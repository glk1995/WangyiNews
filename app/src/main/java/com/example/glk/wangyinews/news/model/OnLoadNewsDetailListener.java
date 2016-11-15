package com.example.glk.wangyinews.news.model;

import com.example.glk.wangyinews.beans.NewsDetailBean;

/**新闻详情加载回调
 * Created by zgqdg on 2016/11/14.
 */

public interface OnLoadNewsDetailListener {

    void onSuccess(NewsDetailBean newsDetailBean);

    void onFailure(String msg ,Exception e);

}
