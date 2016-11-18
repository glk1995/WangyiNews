package com.example.glk.wangyinews.news.widget;


import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.glk.wangyinews.R;
import com.example.glk.wangyinews.beans.NewsBean;
import com.example.glk.wangyinews.news.presenter.NewsDetailPresenter;
import com.example.glk.wangyinews.news.presenter.NewsDetailPresenterImpl;
import com.example.glk.wangyinews.news.view.NewsDetailView;
import com.example.glk.wangyinews.utils.ImageLoadUtils;
import com.example.glk.wangyinews.utils.ToolsUtil;

import org.sufficientlysecure.htmltextview.HtmlResImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

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
    @BindView(R.id.ivImage)
    ImageView ivImage;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.htNewsContent)
    HtmlTextView htNewsContent;

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
        collapsingToolbar.setTitle(news.getTitle());
        //加载图片
        Log.e("imageSrc",news.getImgsrc());
        ImageLoadUtils.display(getApplicationContext(),
                (ImageView) findViewById(R.id.ivImage), news.getImgsrc());
        newsDetailPresenter = new NewsDetailPresenterImpl(getApplication(), this);
        //加载内容
        newsDetailPresenter.loadNewsDetail(news.getDocid());
    }

    @Override
    public void showNewsDetailContent(String newsDetailContent) {
        //AssetManager manager = getAssets();
        //Typeface tf = Typeface.createFromAsset(manager,"KT_GB2312.ttf");
        //htNewsContent.setTypeface(tf);
        htNewsContent.setHtml(newsDetailContent,new HtmlResImageGetter(htNewsContent));
    }

    @Override
    public void showProgress() {
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progress.setVisibility(View.INVISIBLE);
    }
}
