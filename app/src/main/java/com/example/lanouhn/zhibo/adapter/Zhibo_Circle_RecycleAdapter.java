package com.example.lanouhn.zhibo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.lanouhn.zhibo.R;
import com.example.lanouhn.zhibo.contants.zhibo.quickbutton;
import com.example.lanouhn.zhibo.interfaces.Rv_ItemClick;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * 直播页面小轮播图的适配器
 * Created by lanouhn on 2016/8/17.
 */
public class Zhibo_Circle_RecycleAdapter extends RecyclerView.Adapter<Zhibo_Circle_RecycleAdapter.ViewHolder> {

    private Context context;
    private List<quickbutton> list;
    private Rv_ItemClick itemClick;

    public void setItemClick(Rv_ItemClick itemClick) {
        this.itemClick = itemClick;
    }

    public Zhibo_Circle_RecycleAdapter(Rv_ItemClick itemClick) {
        this.itemClick = itemClick;
    }

    public Zhibo_Circle_RecycleAdapter(Context context, List<com.example.lanouhn.zhibo.contants.zhibo.quickbutton> list) {
        this.context = context;
        this.list = list;
    }

    //创建自定义的viewHolder，绑定item布局文件
    @Override
    public Zhibo_Circle_RecycleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viwe = LayoutInflater.from(context).inflate(R.layout.zhibo_rv_item, null);
        ViewHolder viewHolder = new ViewHolder(viwe);
        return viewHolder;
    }

    //将数据绑定给viewHolder中的控件
    @Override
    public void onBindViewHolder(final Zhibo_Circle_RecycleAdapter.ViewHolder holder, int position) {
        quickbutton quickbutton = list.get(position);
        Picasso.with(context).load(quickbutton.getImage()).into(holder.zhibo_recycle_item_image);

        if (itemClick != null) {
            holder.zhibo_recycle_item_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    itemClick.myItemClick(pos);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView zhibo_recycle_item_image;

        public ViewHolder(View itemView) {
            super(itemView);

            zhibo_recycle_item_image = (ImageView) itemView.findViewById(R.id.zhibo_recycle_item_image);
        }
    }
}
