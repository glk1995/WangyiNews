package com.example.glk.wangyinews.news.widget;


import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.example.glk.wangyinews.R;
import com.example.glk.wangyinews.beans.NewsBean;
import com.example.glk.wangyinews.news.presenter.NewsDetailPresenter;
import com.example.glk.wangyinews.news.presenter.NewsDetailPresenterImpl;
import com.example.glk.wangyinews.news.view.NewsDetailView;
import com.example.glk.wangyinews.utils.ToolsUtil;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.text.SimpleDateFormat;
import java.util.Date;

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
    @BindView(R.id.progress)
    ProgressBar progress;

    private NewsBean news;
    private NewsDetailPresenter newsDetailPresenter;
    private SwipeBackLayout swipeBackLayout;
    private String shareLink= null;
    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void onCreate(Bundle savedInstanceState) {

        Log.e("TAG0", sf.format(new Date()));
        super.onCreate(savedInstanceState);
        Log.e("TAG0.1", sf.format(new Date()));
        setContentView(R.layout.activity_news_detail);
        Log.e("TAG0.2", sf.format(new Date()));
        ButterKnife.bind(this);

        Log.e("TAG1", sf.format(new Date()));
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

        Log.e("TAG2", "time");
        //获取activitList传递过来的内容
        news = (NewsBean) getIntent().getSerializableExtra("news");

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detailactivity,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_share){
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,shareLink);
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
            return  true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showNewsDetailContent(final String newsDetailContent,String url) {
        shareLink = url;
        WebSettings webSettings = webcontent.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webcontent.setWebChromeClient(new WebChromeClient() {
            //监听网页加载
            @Override
            public void onProgressChanged(WebView webView, int i) {
                if(i >= 100){
                    progress.setVisibility(View.GONE);
                }else {
                    progress.setProgress(i);
                }
                super.onProgressChanged(webView, i);
            }
        });
        webcontent.loadData(newsDetailContent, "text/html;charset=UTF-8", null);
        webcontent.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s) {
                webView.loadData(newsDetailContent, "text/html;charset=UTF-8", null);
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
