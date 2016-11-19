package com.example.glk.wangyinews.news.widget;


import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.example.glk.wangyinews.R;
import com.example.glk.wangyinews.beans.NewsBean;
import com.example.glk.wangyinews.news.presenter.NewsDetailPresenter;
import com.example.glk.wangyinews.news.presenter.NewsDetailPresenterImpl;
import com.example.glk.wangyinews.news.view.NewsDetailView;
import com.example.glk.wangyinews.utils.ToolsUtil;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * 新闻详情界面
 * Created by zgqdg on 2016/11/17.
 */

public class NewsDetailActivity extends SwipeBackActivity implements NewsDetailView {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.webcontent)
    WebView webcontent;
    private NewsBean news;
    private NewsDetailPresenter newsDetailPresenter;
    private SwipeBackLayout swipeBackLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        ButterKnife.bind(this);

        //设置Toolbar
        setSupportActionBar(toolbar);
        //给左上角设置一个返回的图标
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //设置返回的监听器
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //设置右滑关闭
        swipeBackLayout = getSwipeBackLayout();
        swipeBackLayout.setEdgeSize(ToolsUtil.getWidthInPx(this));
        swipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);

        //获取activitList传递过来的内容
        news = (NewsBean) getIntent().getSerializableExtra("news");
        //设置toolBar标题
        //collapsingToolbar.setTitle(news.getTitle());
        newsDetailPresenter = new NewsDetailPresenterImpl(getApplication(), this);
        //WebView视频避免闪屏和透明问题
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        //WebView避免输入法界面弹出后遮挡住输入光标的问题
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        //加载内容
        newsDetailPresenter.loadNewsDetail(news.getDocid());
    }


    @Override
    public void showNewsDetailContent(final String newsDetailContent) {
        //AssetManager manager = getAssets();
        //Typeface tf = Typeface.createFromAsset(manager,"KT_GB2312.ttf");
        //htNewsContent.setTypeface(tf);
        //Log.e("Content",newsDetailContent);
        //htNewsContent.setHtml(newsDetailContent, new HtmlHttpImageGetter(htNewsContent));
        // webcontent.loadUrl(url3);
        WebSettings webSettings = webcontent.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webcontent.loadData(newsDetailContent, "text/html;charset=UTF-8", null);
        webcontent.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s) {
                webView.loadData(newsDetailContent, "text/html;charset=UTF-8", null);
                //webView.loadUrl(newsDetailContent);
                return true;
            }
        });

    }


    @Override
    public void showProgress() {
    }

    @Override
    public void hideProgress() {
    }
}
