package com.example.glk.wangyinews.news.presenter;

import android.content.Context;
import android.util.Log;

import com.example.glk.wangyinews.beans.ImgBean;
import com.example.glk.wangyinews.beans.NewsDetailBean;
import com.example.glk.wangyinews.news.model.NewsModel;
import com.example.glk.wangyinews.news.model.NewsModelImpl;
import com.example.glk.wangyinews.news.model.OnLoadNewsDetailListener;
import com.example.glk.wangyinews.news.view.NewsDetailView;
import com.example.glk.wangyinews.utils.HtmlUtils;
import com.example.glk.wangyinews.utils.LogUtils;

import java.util.List;

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
        //newsDetailView.showProgress();
        newsModel.loadNewsDetail(docid,this);
    }

    //获取数据成功后，若内容不为空，则显示内容，隐藏进度条
    @Override
    public void onSuccess(NewsDetailBean newsDetailBean) {
        if(newsDetailBean != null){
            //Log.e("FFFFF",newsDetailBean.getTitle());
            List<ImgBean> imgArr = newsDetailBean.getImg();
            //新闻内容本身
            String body = Func(newsDetailBean.getBody(),imgArr);
            //对新闻加上html
            String html = getHtml(body,newsDetailBean);
            newsDetailView.showNewsDetailContent(html);
        }
        //newsDetailView.hideProgress();
    }

    public  String Func(String s,List<ImgBean> a){

        for(int i = 0;i < a.size();i++){
            StringBuilder sb = new StringBuilder(a.get(i).getRef());

            StringBuilder sb2= new StringBuilder("<p><img src=");
            sb2.append(a.get(i).getSrc());
            sb2.append("></p>");

            s = s.replaceAll(sb.toString(), sb2.toString());
        }

        return s;
    }

    public String getHtml(String s,NewsDetailBean newsDetailBean){

        String html = HtmlUtils.head1 + newsDetailBean.getTitle() + HtmlUtils.head2 + newsDetailBean.getTitle()
                + HtmlUtils.head3 + newsDetailBean.getSource() + HtmlUtils.tail1 + newsDetailBean.getPtime()
                + HtmlUtils.tail2 + s + HtmlUtils.tail3;


        return html;

    }

    @Override
    public void onFailure(String msg, Exception e) {
        newsDetailView.hideProgress();
    }

}
