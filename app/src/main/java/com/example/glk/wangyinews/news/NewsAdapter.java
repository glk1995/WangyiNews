package com.example.glk.wangyinews.news;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.glk.wangyinews.R;
import com.example.glk.wangyinews.beans.NewsBean;
import com.example.glk.wangyinews.utils.ImageLoadUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zgqdg on 2016/11/16.
 */

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    //List<NewsBean>的数据
    private List<NewsBean> mData;
    //是否显示加载
    private boolean mShowFoot = true;
    private Context mContext;

    private OnItemClickListener mOnItemClickListener;

    public NewsAdapter(Context context) {
        this.mContext = context;
    }

    public void setmData(List<NewsBean> data) {
        this.mData = data;
        //notifyDataSetChanged方法通过外部如果适配器内容改变
        //强制调用getView来刷新每个Item的内容，可以实现动态刷新列表的功能
        this.notifyDataSetChanged();
    }

    //每个Adapter显示的界面
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //如果viewType是Item,显示item_news界面,否则显示加载界面
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_news, parent,false);
            ItemViewHolder vh = new ItemViewHolder(v);
            return vh;
        }else {
            View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.footer,null);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            return new FooterViewHolder(view);
        }
    }


    //加载每行的新闻信息
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ItemViewHolder){
            NewsBean news = mData.get(position);
            if(news == null){
                return;
            }
            //加载数据
            ((ItemViewHolder)holder).tvTitle.setText(news.getTitle());
            ((ItemViewHolder)holder).tvDesc.setText(news.getDigest());
            //加载图片
            ImageLoadUtils.display(mContext,((ItemViewHolder)holder).ivNews,news.getImgsrc());
        }
    }

    //
    @Override
    public int getItemViewType(int position) {
        //最后一个item设置为footerView
        if(!mShowFoot){
            return TYPE_ITEM;
        }
        if(position + 1 == getItemCount()){
            return TYPE_FOOTER;
        }else {
            return TYPE_ITEM;
        }
    }

    //获取Item的个数
    @Override
    public int getItemCount() {
        //如果有有加载头,那么至少有一个Item
        int begin = mShowFoot ? 1: 0;
        if(mData == null)
            return begin;
        //否则返回数据的个数+加载头
        return mData.size() + begin;
    }

    //一些外部接口
    public NewsBean getItem(int position){
        return mData == null ? null : mData.get(position);
    }

    public void isShowFooter(boolean mShowFoot){
        this.mShowFoot = mShowFoot;
    }

    public boolean isShowFooter(){return this.mShowFoot;}

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.mOnItemClickListener = onItemClickListener;
    }


    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }


    //footet ViewHolder
    public class FooterViewHolder extends RecyclerView.ViewHolder{

        public FooterViewHolder(View View) {
            super(View);
        }
    }



    //Item ViewHolder
    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.ivNews)
        ImageView ivNews;
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvDesc)
        TextView tvDesc;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            if(mOnItemClickListener != null){
                mOnItemClickListener.onItemClick(itemView,this.getPosition());
            }
        }
    }
}
