package com.example.glk.wangyinews.utils;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.internal.$Gson$Types;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * OkHttp网络连接封装工具类
 * Created by zgqdg on 2016/11/15.
 */

public class OkHttpUtils {

    private static final String TAG = "OkHttpUtils";

    private static OkHttpUtils mInstance;

    private OkHttpClient mOkHttpClient;

    private Handler mDelivery;

    //构造方法中,各种初始化操作:设置超时、设置cookie enable、新建Handler对象
    private OkHttpUtils(){
        mOkHttpClient = new OkHttpClient();
        mOkHttpClient.setConnectTimeout(10, TimeUnit.SECONDS);
        mOkHttpClient.setWriteTimeout(10,TimeUnit.SECONDS);
        mOkHttpClient.setReadTimeout(10,TimeUnit.SECONDS);
        //cookie enabled
        mOkHttpClient.setCookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ORIGINAL_SERVER));
        mDelivery = new Handler(Looper.getMainLooper());
    }

    //单例方法
    private synchronized static OkHttpUtils getInstance(){
        if(mInstance == null){
            mInstance = new OkHttpUtils();
        }
        return mInstance;
    }

    /**
     * get操作，外部接口获取数据后传递到这
     * @param url           外部传递的url
     * @param callback      回调接口
     */
    private void getRequest(String url,final ResultCallback callback){
        //创建Request
        final Request request = new Request.Builder().url(url).build();
        //传递给消息处理方法
        deliveryResult(callback,request);
    }

    /**
     * post操作，外部接口获取数据后传递到这
     * @param url           外部传递的url
     * @param callback      回调接口
     * @param params        参数
     */
    private void postRequest(String url, final ResultCallback callback, List<Param> params){
        //创建Request
        Request request = buildPostRequest(url,params);
        //传递给消息处理方法
        deliveryResult(callback,request);
    }


    /**
     * 建立Post操作的Request参数
     * @param url
     * @param params
     * @return
     */
    private Request buildPostRequest(String url,List<Param> params){
        FormEncodingBuilder builder = new FormEncodingBuilder();
        for (Param param : params) {
            builder.add(param.key,param.value);
        }
        RequestBody requestBody = builder.build();

        return new Request.Builder().url(url).post(requestBody).build();
    }


    /**
     * 分发结果
     * @param callback
     * @param request
     */
    private  void deliveryResult(final ResultCallback callback, final Request request){
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                sendFailCallback(callback,e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                try {
                    String str = response.body().string();
                    if(callback.mType == String.class){
                        sendSuccessCallback(callback,str);
                    }else {
                        Object object = JsonUtils.deserialize(str,callback.mType);
                        sendSuccessCallback(callback,object);
                    }
                }catch (final Exception e){
                    LogUtils.e(TAG,"convert json failure",e);
                    sendFailCallback(callback,e);
                }
            }
        });
    }


    /**
     * 在主线程中回调方法
     * @param callback
     * @param e
     */
    private void sendFailCallback(final ResultCallback callback,final Exception e){
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                if(callback != null){
                    callback.onFailure(e);
                }
            }
        });
    }

    /**
     * 在主线程中回调方法
     * @param callback
     * @param obj
     */
    private void sendSuccessCallback(final ResultCallback callback,final Object obj){
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                if(callback != null){
                    callback.onSuccess(obj);
                }
            }
        });
    }




    /****************对外接口***************/
    /**
     * get请求
     * @param url       请求url
     * @param callback  请求回调
     */

    public static void get(String url,ResultCallback callback){
        getInstance().getRequest(url,callback);
    }

    /**
     * post请求
     * @param url       请求url
     * @param callback  请求回调
     * @param params    请求参数
     */

    public static void post(String url,final ResultCallback callback,List<Param> params){
        getInstance().postRequest(url,callback,params);
    }

    /**
     * 请求回调类,回调方法在UI线程中执行
     * @param <T>
     */
    public static abstract class ResultCallback<T> {

        Type mType;

        public ResultCallback(){
            mType = getSuperclassTypeParameter(getClass());
        }

        //用Gson完成获取泛型类型
        static Type getSuperclassTypeParameter(Class<?> subclass){
            Type superclass = subclass.getGenericSuperclass();
            if (superclass instanceof  Class){
                throw new RuntimeException("Missing type parameter.");
            }
            ParameterizedType parameterizedType = (ParameterizedType)superclass;
            return $Gson$Types.canonicalize(parameterizedType.getActualTypeArguments()[0]);
        }

        /**
         * 请求成功回调,已经在主线程中,可以直接处理UI
         * @param response
         */
        public abstract void onSuccess(T response);

        /**
         * 请求失败回调,已经在主线程中,可以直接处理UI
         * @param e
         */
        public abstract void onFailure(Exception e);
    }

    /**
     * post请求参数类
     */
    public static class Param{
        String key;
        String value;

        public Param(){
        }

        public Param(String key,String value){
            this.key = key;
            this.value = value;
        }
    }

}
