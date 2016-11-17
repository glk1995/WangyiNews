package com.example.glk.wangyinews.utils;

import android.content.Context;
import android.widget.ImageView;

import com.example.glk.wangyinews.R;
import com.squareup.picasso.Picasso;

/**
 * Created by zgqdg on 2016/11/16.
 */

public class ImageLoadUtils {

    public static void display(Context context, ImageView imageView,String url,int placeholder,int error){
        if(imageView == null){
            throw new IllegalArgumentException("argument error");
        }
        Picasso.with(context).load(url).placeholder(placeholder)
                .error(error).noFade().into(imageView);
    }

    public static void display(Context context,ImageView imageView,String url){
        if(imageView == null){
            throw new IllegalArgumentException("argument error");
        }
        Picasso.with(context).load(url).placeholder(R.drawable.ic_image_loading)
                .error(R.drawable.ic_image_loadfail).noFade().into(imageView);
    }
}
