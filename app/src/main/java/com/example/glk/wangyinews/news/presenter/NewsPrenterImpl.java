package com.example.glk.wangyinews.news.presenter;

import com.example.glk.wangyinews.beans.NewsBean;
import com.example.glk.wangyinews.common.Urls;
import com.example.glk.wangyinews.news.model.NewsModel;
import com.example.glk.wangyinews.news.model.NewsModelImpl;
import com.example.glk.wangyinews.news.model.OnLoadNewsListener;
import com.example.glk.wangyinews.news.view.NewsView;
import com.example.glk.wangyinews.news.widget.NewsFragment;
import com.example.glk.wangyinews.utils.LogUtils;

import java.util.List;

/**实现NewsPresenter接口,实现News列表展示(Presenter)
 * 实现onLoadNewsLister接口,实现请求数据的处理(Model)
 * 构造函数传入一个newsView,实现界面的显示(View)
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


    /**
     * 根据type和page获取URL
     * @param type
     * @param page
     */
    @Override
    public void loadNews(int type, int page) {
        String url = getUrl(type, page);
        LogUtils.d(TAG, url);
        //只有第一页的或者刷新的时候才显示刷新进度条
        if (page == 0) {
            newsView.showProgress();
        }
        //使用mode处理数据
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
            case NewsFragment.NEWS_TYPE_TOP:
                sb.append(Urls.TOP_URL).append(Urls.TOP_ID);
                break;
            case NewsFragment.NEWS_TYPE_NBA:
                sb.append(Urls.COMMON_URL).append(Urls.NBA_ID);
                break;
            case NewsFragment.NEWS_TYPE_CARS:
                sb.append(Urls.COMMON_URL).append(Urls.CAR_ID);
                break;
            case NewsFragment.NEWS_TYPE_JOKES:
                sb.append(Urls.COMMON_URL).append(Urls.JOKE_ID);
                break;
            default:
                sb.append(Urls.TOP_URL).append(Urls.TOP_ID);
                break;
        }
        sb.append("/").append(page).append(Urls.END_URL);
        return sb.toString();
    }

    //两个回调方法
    @Override
    public void onSuccess(List<NewsBean> list) {
        newsView.hideProgress();
        newsView.addNews(list);
    }

    @Override
    public void onFailure(String msg, Exception e) {
        newsView.hideProgress();
        newsView.showLoadFailMsg();
    }
}
