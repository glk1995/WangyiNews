package com.example.glk.wangyinews.news.widget;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.glk.wangyinews.R;
import com.example.glk.wangyinews.beans.NewsBean;
import com.example.glk.wangyinews.common.Urls;
import com.example.glk.wangyinews.news.NewsAdapter;
import com.example.glk.wangyinews.news.presenter.NewsPrenterImpl;
import com.example.glk.wangyinews.news.presenter.NewsPresenter;
import com.example.glk.wangyinews.news.view.NewsView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    private NewsAdapter adapter;

    private int mType = NewsFragment.NEWS_TYPE_TOP;
    //页面上的数据
    private int pageIndex = 0;

    public static NewsListFragment newInstance(int type){
        Bundle args = new Bundle();
        NewsListFragment fragment = new NewsListFragment();
        args.putInt("type",type);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        newsPresenter = new NewsPrenterImpl(this);
        mType = getArguments().getInt("type");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_newslist, null);
        ButterKnife.bind(this,view);
        //设置下拉刷新的参数
        swipeRefreshWidget.setColorSchemeColors(R.color.primary,R.color.primary_dark,
                R.color.primary_light,R.color.accent);
        //设置下拉刷新监听器
        swipeRefreshWidget.setOnRefreshListener(this);

        //确保RecyclerView的参数是一个常数，提高效率
        recycleView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recycleView.setLayoutManager(layoutManager);
        //Item添加、删除默认动画效果
        recycleView.setItemAnimator(new DefaultItemAnimator());
        //新建adpter
        adapter = new NewsAdapter(getActivity().getApplicationContext());
        //给adpter设置监听器,实现点击显示详细信息
        adapter.setOnItemClickListener(itemClickListener);
        recycleView.setAdapter(adapter);
        //实现自动加载功能
        recycleView.addOnScrollListener(scrollListener);
        //初始化显示首页数据
        onRefresh();
        return view;
    }

    //adpter监听器
    private NewsAdapter.OnItemClickListener itemClickListener = new NewsAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            if(mData.size() <= 0){
                return;
            }
            NewsBean news = adapter.getItem(position);
            Intent intent = new Intent(getActivity(),NewsDetailActivity.class);
            //Log.e("docid",news.getDocid());
            intent.putExtra("news",news);

            View transitionView = view.findViewById(R.id.ivNews);
            //设置Activity跳转动画，这个是场景动画
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                    transitionView,getString(R.string.transition_news_img));
            ActivityCompat.startActivity(getActivity(),intent,options.toBundle());
            startActivity(intent);
        }
    };

    //实现自动加载
    private RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
        private int lastVisibleItem;

        //一直在滚动中，一直触发
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            //获取可视的最后一个Item的position
            lastVisibleItem = layoutManager.findLastVisibleItemPosition();
        }

        //滚动事件结束后触发
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            //SCROLL_STATE_IDLE整个滚动事件结束
            //SCROLL_STATE_FLING持续滚动开始
            //SCROLL_STATE_TOUCH_SCROLL手指触摸拉动准备滚动
            if(newState == RecyclerView.SCROLL_STATE_IDLE
                    && lastVisibleItem + 1 == adapter.getItemCount()
                    && adapter.isShowFooter()){
                //LogUtils.d(TAG,"loading more data");
                //加载更多
                newsPresenter.loadNews(mType,pageIndex + Urls.PAZE_SIZE);
            }
        }
    };

    //初始化显示数据
    @Override
    public void onRefresh() {
        pageIndex = 0;
        if(mData != null){
            mData.clear();
        }
        newsPresenter.loadNews(mType,pageIndex);
    }

    //显示加载
    @Override
    public void showProgress() {
        swipeRefreshWidget.setRefreshing(true);
    }

    //加载隐藏
    @Override
    public void hideProgress() {
        swipeRefreshWidget.setRefreshing(false);
    }

    //NewsPrenter的onSuccess回调方法
    @Override
    public void addNews(List<NewsBean> newsList) {
        adapter.isShowFooter(true);
        if(mData == null){
            mData = new ArrayList<>();
        }
        mData.addAll(newsList);
        if(pageIndex == 0){
            adapter.setmData(mData);
        }else{
            //如果没有更多数据,则隐藏footer布局
            if(newsList == null || newsList.size() == 0){
                adapter.isShowFooter(false);
                //这边可以加一行,已经加载到底了
            }
            adapter.notifyDataSetChanged();
        }
        pageIndex += Urls.PAZE_SIZE;
    }


    //使用SnakeBar弹出加载数据失败
    @Override
    public void showLoadFailMsg() {
        if(pageIndex == 0){
            adapter.isShowFooter(false);
            adapter.notifyDataSetChanged();
        }
        View view = getActivity() == null ?
                recycleView.getRootView():getActivity().findViewById(R.id.drawer_layout);
        Snackbar.make(view,"加载数据失败",Snackbar.LENGTH_SHORT).show();
    }
}
