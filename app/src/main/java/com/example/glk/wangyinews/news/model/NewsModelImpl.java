package com.example.glk.wangyinews.news.model;

import android.util.Log;

import com.example.glk.wangyinews.beans.NewsBean;
import com.example.glk.wangyinews.beans.NewsDetailBean;
import com.example.glk.wangyinews.common.Urls;
import com.example.glk.wangyinews.news.NewsJsonUtils;
import com.example.glk.wangyinews.news.widget.NewsFragment;
import com.example.glk.wangyinews.utils.OkHttpUtils;

import java.util.List;

/**新闻也业务处理类
 * 主要处理新闻列表的加载和新闻详情的加载
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
    public void loadNews(String url, final int type, final OnLoadNewsListener listener) {
        //网络请求结果回调方法，已在UI线程中
        OkHttpUtils.ResultCallback<String> loadNewsCallback = new OkHttpUtils.ResultCallback<String>() {
            @Override
            public void onSuccess(String response) {
                List<NewsBean> newsBeanList = NewsJsonUtils.readJsonNewsBeans(response,getId(type));
                //OnLoadNewsListener
                listener.onSuccess(newsBeanList);
            }

            @Override
            public void onFailure(Exception e) {
                listener.onFailure("load news list failure",e);
            }
        };
        //向网络请求数据
        OkHttpUtils.get(url,loadNewsCallback);
    }

    /**
     * 加载新闻详情
     * @param docid
     * @param listener
     */
    @Override
    public void loadNewsDetail(final String docid, final OnLoadNewsDetailListener listener) {
        String url = getDetailUrl(docid);
        Log.e("URL",url);
        OkHttpUtils.ResultCallback<String> loadNewsCallback = new OkHttpUtils.ResultCallback<String>() {
            @Override
            public void onSuccess(String response) {
                NewsDetailBean newsDetailBean = NewsJsonUtils.readJsonNewsDetailBeans(response,docid);
                listener.onSuccess(newsDetailBean);
            }

            @Override
            public void onFailure(Exception e) {
                listener.onFailure("load news detail into failure",e);
            }
        };
        //向服务器请求数据
        OkHttpUtils.get(url,loadNewsCallback);
    }

    /**
     * 获取新闻列表ID
     * @param type
     * @return
     */
    private String getId(int type){
        String id;
        switch (type){
            case NewsFragment.NEWS_TYPE_TOP:
                id = Urls.TOP_ID;
                break;
            case NewsFragment.NEWS_TYPE_NBA:
                id = Urls.NBA_ID;
                break;
            case NewsFragment.NEWS_TYPE_CARS:
                id = Urls.CAR_ID;
                break;
            case NewsFragment.NEWS_TYPE_JOKES:
                id = Urls.JOKE_ID;
                break;
            default:
                id = Urls.TOP_ID;
                break;
        }

        return id;
    }

    /**
     * 获取文章对应的完整的Url
     * @param docId
     * @return
     */
    private String getDetailUrl(String docId){
        StringBuffer sb = new StringBuffer(Urls.NEW_DETAIL);
        sb.append(docId).append(Urls.END_DETAIL_URL);

        return sb.toString();
    }
}
