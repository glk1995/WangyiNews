package com.example.glk.wangyinews.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by zgqdg on 2016/11/14.
 */

public class ToolsUtil {

    public static  int getHeightInPx(Context context){
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static final int getWidthInPx(Context context){
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static final int getHeightInDp(Context context){
        final float height = context.getResources().getDisplayMetrics().heightPixels;
        int heightInDp = px2dp(context,height);

        return heightInDp;
    }

    public static final int getWidthInDp(Context context){
        final float width = context.getResources().getDisplayMetrics().widthPixels;
        int widthInDp = px2dp(context,width);

        return widthInDp;
    }

    public static int dp2px(Context context,float dp){
        final float scale = context.getResources().getDisplayMetrics().density;

        return (int)(dp * scale + 0.5f);
    }

    public static int px2dp(Context context,float px){
        final float scale = context.getResources().getDisplayMetrics().density;

        return (int)(px / scale + 0.5f);
    }

    /**
     * 获得状态栏的高度
     * @param context
     * @return
     */
    public static int getStatusHeight(Context context){
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return statusHeight;
    }

    /**
     * 判断网络是否可用
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context){
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(
                Context.CONNECTIVITY_SERVICE);
        if(cm != null){
            //如果仅仅是用来判断网络连接
            //则可以使用 cm.getActiveNetworkInfo().isAvailable();
            NetworkInfo[] info = cm.getAllNetworkInfo();
            if(info != null){
                for (int i = 0; i < info.length; i++) {
                    if(info[i].getState() == NetworkInfo.State.CONNECTED){
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
