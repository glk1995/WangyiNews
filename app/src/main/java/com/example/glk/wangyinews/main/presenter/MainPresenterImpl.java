package com.example.glk.wangyinews.main.presenter;

import com.example.glk.wangyinews.R;
import com.example.glk.wangyinews.main.view.MainView;

/**根据不同的Drawer Item切换到不同的界面
 * Created by zgqdg on 2016/11/13.
 */

public class MainPresenterImpl implements MainPresenter {

    private MainView mMainView;

    public MainPresenterImpl(MainView mainView){
        this.mMainView = mainView;
    }

    @Override
    public void switchNavigation(int id) {
        switch (id){
            case R.id.navigation_item_news:
                mMainView.switch2News();
                break;
            case R.id.navigation_item_images:
                mMainView.switch2Images();
                break;
            case R.id.navigation_item_weather:
                mMainView.switch2Weather();
                break;
            case R.id.navigation_item_about:
                mMainView.switch2About();
                break;
        }
    }
}
