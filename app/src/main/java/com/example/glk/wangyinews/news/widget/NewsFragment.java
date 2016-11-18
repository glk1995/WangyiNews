package com.example.glk.wangyinews.news.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.glk.wangyinews.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zgqdg on 2016/11/16.
 */

public class NewsFragment extends Fragment {

    public static final int NEWS_TYPE_TOP = 0;
    public static final int NEWS_TYPE_NBA = 1;
    public static final int NEWS_TYPE_CARS = 2;
    public static final int NEWS_TYPE_JOKES = 3;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, null);
        ButterKnife.bind(this, view);
        //设置预加载页面的个数,左边3个页面,右边3个界面
        viewpager.setOffscreenPageLimit(3);
        setupViewPager(viewpager);
        //加入Tab页卡
        tabLayout.addTab(tabLayout.newTab().setText("头条"));
        tabLayout.addTab(tabLayout.newTab().setText("NBA"));
        tabLayout.addTab(tabLayout.newTab().setText("汽车"));
        tabLayout.addTab(tabLayout.newTab().setText("笑话"));
        //将Tab页卡与viewPager关联起来
        tabLayout.setupWithViewPager(viewpager);
        return view;
    }

    private void setupViewPager(ViewPager viewpager){
        //Fragment中嵌套使用Fragment一定要使用getChildFragmentManager(),否则会有问题
        MyPagerAdpater adpater = new MyPagerAdpater(getChildFragmentManager());
        //viewPager加入Fragment界面，每个Fragment里面加载的数据是不同的
        adpater.addFragment(NewsListFragment.newInstance(NEWS_TYPE_TOP),"头条");
        adpater.addFragment(NewsListFragment.newInstance(NEWS_TYPE_NBA),"NBA");
        adpater.addFragment(NewsListFragment.newInstance(NEWS_TYPE_CARS),"汽车");
        adpater.addFragment(NewsListFragment.newInstance(NEWS_TYPE_JOKES),"笑话");
        viewpager.setAdapter(adpater);
    }

    public static class MyPagerAdpater extends FragmentPagerAdapter{
        private final List<Fragment> fragments = new ArrayList<>();
        private final List<String> fragmentTitles = new ArrayList<>();

        public MyPagerAdpater(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment,String title){
            fragments.add(fragment);
            fragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragmentTitles.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitles.get(position);
        }
    }
}
