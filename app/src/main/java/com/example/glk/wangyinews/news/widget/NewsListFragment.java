package com.example.glk.wangyinews.news.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.glk.wangyinews.R;
import com.example.glk.wangyinews.beans.NewsBean;
import com.example.glk.wangyinews.news.presenter.NewsPresenter;
import com.example.glk.wangyinews.news.view.NewsView;

import java.util.List;

import butterknife.BindView;

/**
 * 新闻Fragment
 * Created by zgqdg on 2016/11/16.
 */

public class NewsListFragment extends Fragment implements NewsView, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "NewsListFragment";

    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    @BindView(R.id.swipe_refresh_widget)
    SwipeRefreshLayout swipeRefreshWidget;

    private LinearLayoutManager layoutManager;
    private List<NewsBean> mData;
    private NewsPresenter newsPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_newslist, null);

        return view;
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void addNews(List<NewsBean> newsList) {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showLoadFailMsg() {

    }
}
