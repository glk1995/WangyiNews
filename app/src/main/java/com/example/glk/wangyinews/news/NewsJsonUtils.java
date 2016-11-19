package com.example.glk.wangyinews.news;

import com.example.glk.wangyinews.beans.NewsBean;
import com.example.glk.wangyinews.beans.NewsDetailBean;
import com.example.glk.wangyinews.utils.JsonUtils;
import com.example.glk.wangyinews.utils.LogUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zgqdg on 2016/11/17.
 */

public class NewsJsonUtils {

    private final static String TAG = "NewsJsonUtils";


    /**
     * 将获取到的json转换为新闻列表对象
     * @param res
     * @param value
     * @return
     */
    public static List<NewsBean> readJsonNewsBeans(String res,String value){
        List<NewsBean> beans = new ArrayList<>();
        try{
            JsonParser parser = new JsonParser();
            JsonObject jsonObject = parser.parse(res).getAsJsonObject();
            JsonElement jsonElement = jsonObject.get(value);
            if (jsonElement == null){
                return null;
            }
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            for(int i = 1;i < jsonArray.size();i++){
                JsonObject jo = jsonArray.get(i).getAsJsonObject();
                if(jo.has("skipType") && "special".equals(jo.get("skipType").getAsString())){
                    continue;
                }
                if(jo.has("TAGS") && !jo.has("TAG")){
                    continue;
                }
                if(!jo.has("imgextra")){
                    NewsBean news = JsonUtils.deserialize(jo,NewsBean.class);
                    beans.add(news);
                }
            }
        }catch (Exception e){
            LogUtils.e(TAG,"readJsonNewsBean error",e);
        }

        return beans;
    }

    /**
     * 将获取到的json转换为新闻详情对象
     * @param res
     * @param docId
     * @return
     */
    public static NewsDetailBean readJsonNewsDetailBeans(String res, String docId){
        NewsDetailBean newsDetailBean = null;
        try {
            JsonParser parser = new JsonParser();
            JsonObject jsonObj = parser.parse(res).getAsJsonObject();
            JsonElement jsonElement = jsonObj.get(docId);
            if(jsonElement == null) {
                return null;
            }
            //
            newsDetailBean = JsonUtils.deserialize(jsonElement.getAsJsonObject(), NewsDetailBean.class);
        } catch (Exception e) {
            LogUtils.e(TAG, "readJsonNewsBeans error" , e);
        }
        return newsDetailBean;
    }

}
