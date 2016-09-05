package com.example.lanouhn.zhibo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lanouhn.zhibo.R;
import com.example.lanouhn.zhibo.contants.ago.items;
import com.example.lanouhn.zhibo.interfaces.Rv_ItemClick;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * 赛事回顾页面RecyclerView的适配器
 * Created by lanouhn on 16/9/2.
 */
public class Ago_RecycleViewAdapter extends RecyclerView.Adapter<Ago_RecycleViewAdapter.ViewHolder> {

    private List<items> itemsList;    //item的集合
    private Context context;
    private Rv_ItemClick itemClick;

    public void setItemClick(Rv_ItemClick itemClick) {
        this.itemClick = itemClick;
    }

    public Ago_RecycleViewAdapter(Rv_ItemClick itemClick) {
        this.itemClick = itemClick;
    }

    public Ago_RecycleViewAdapter(List<items> itemsList, Context context) {
        this.itemsList = itemsList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.ago_rv_item, null);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        items data = itemsList.get(position);

        Picasso.with(context).load(data.getImages().getThumb()).into(holder.ago_gv_iv_backaround);
        holder.ago_gv_tv_total.setText(String.valueOf(itemsList.get(position).getViews()));
        holder.ago_gv_tv_des.setText(itemsList.get(position).getTitle());

        //处理时间，除以60，取整取模
        int longtime = itemsList.get(position).getDuration();
        int hour = longtime / 60;
        int minite = longtime % 60;
        holder.ago_gv_tv_time.setText(hour + ":" + minite);

    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ago_gv_iv_backaround;    //赛事背景图片
        private TextView ago_gv_tv_time;    //视频时长
        private TextView ago_gv_tv_total;    //播放次数
        private TextView ago_gv_tv_des;    //赛事描述

        public ViewHolder(View itemView) {
            super(itemView);

            ago_gv_tv_total = (TextView) itemView.findViewById(R.id.ago_gv_tv_total);
            ago_gv_tv_time = (TextView) itemView.findViewById(R.id.ago_gv_tv_time);
            ago_gv_iv_backaround = (ImageView) itemView.findViewById(R.id.ago_gv_iv_backaround);
            ago_gv_tv_des = (TextView) itemView.findViewById(R.id.ago_gv_tv_des);
        }
    }
}
