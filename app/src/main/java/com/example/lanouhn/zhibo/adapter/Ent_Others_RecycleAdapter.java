package com.example.lanouhn.zhibo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lanouhn.zhibo.R;
import com.example.lanouhn.zhibo.contants.ent_Others.items;
import com.example.lanouhn.zhibo.interfaces.Rv_ItemClick;
import com.example.lanouhn.zhibo.utils.ExceptUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * 娱乐页面中动态加载的布局中RecyclerView的适配器
 * Created by lanouhn on 2016/8/19.
 */
public class Ent_Others_RecycleAdapter extends RecyclerView.Adapter<Ent_Others_RecycleAdapter.ViewHolder> {

    private Context context;
    private List<items> list;
    private Rv_ItemClick itemClick;

    public void setItemClick(Rv_ItemClick itemClick){
        this.itemClick = itemClick;
    }

    public Ent_Others_RecycleAdapter(Rv_ItemClick itemClick) {
        this.itemClick = itemClick;
    }

    public Ent_Others_RecycleAdapter(Context context, List<items> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public Ent_Others_RecycleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.ent_others_rv_item, null);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final Ent_Others_RecycleAdapter.ViewHolder holder, int position) {

        //赋值
        items data = list.get(position);
        Picasso.with(context).load(data.getPreview()).into(holder.ent_gv_iv_backaround);
        holder.ent_gv_tv_name.setText(data.getChannel().getName());
        holder.ent_gv_tv_des.setText(data.getChannel().getStatus());
        holder.ent_gv_tv_total.setText(String.valueOf(data.getViewers()));

        //小数处理
        String result = ExceptUtil.getDate(data.getViewers());
        holder.ent_gv_tv_total.setText(result);

        if (itemClick!=null){
            holder.ent_gv_iv_backaround.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    itemClick.myItemClick(pos-1);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ent_gv_iv_backaround;    //背景图片
        private TextView ent_gv_tv_name;    //房间标题
        private TextView ent_gv_tv_total;    //观看人数
        private TextView ent_gv_tv_des;    //房间描述

        public ViewHolder(View itemView) {
            super(itemView);

            ent_gv_iv_backaround = (ImageView) itemView.findViewById(R.id.ent_gv_iv_backaround);
            ent_gv_tv_name = (TextView) itemView.findViewById(R.id.ent_gv_tv_name);
            ent_gv_tv_total = (TextView) itemView.findViewById(R.id.ent_gv_tv_total);
            ent_gv_tv_des = (TextView) itemView.findViewById(R.id.ent_gv_tv_des);
        }
    }
}
