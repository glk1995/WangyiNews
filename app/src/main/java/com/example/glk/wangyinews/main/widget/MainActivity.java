package com.example.glk.wangyinews.main.widget;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.glk.wangyinews.R;
import com.example.glk.wangyinews.main.presenter.MainPresenter;
import com.example.glk.wangyinews.main.presenter.MainPresenterImpl;
import com.example.glk.wangyinews.main.view.MainView;
import com.example.glk.wangyinews.news.widget.NewsFragment;
import com.tencent.smtt.sdk.QbSdk;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainView {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.frame_content)
    FrameLayout frameContent;
    @BindView(R.id.main_content)
    CoordinatorLayout mainContent;
    @BindView(R.id.navigation_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    //DrawerLayout监听器
    private ActionBarDrawerToggle drawerToggle;

    private MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //预加载X5内核环境
        QbSdk.initX5Environment(this,null);
        setSupportActionBar(toolbar);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_close);

        drawerToggle.syncState();
        //setDrawerListener已经depressed
        drawerLayout.addDrawerListener(drawerToggle);
        mainPresenter = new MainPresenterImpl(this);
        setupDrawerContent(navigationView);
        //初始显示新闻界面
        switch2News();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu
        //this adds items to the action bar if it is present
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Handle action bar item clicks here.
        //The action bar will automatically handle clicks on Home/Up button
        //so long as you specify a parent activity int AndroidManifest.xml
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                mainPresenter.switchNavigation(item.getItemId());
                item.setChecked(true);
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    @Override
    public void switch2News() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content,new NewsFragment()).commit();
        toolbar.setTitle("新闻");
    }

    @Override
    public void switch2Images() {

    }

    @Override
    public void switch2Weather() {

    }

    @Override
    public void switch2About() {

    }
}
