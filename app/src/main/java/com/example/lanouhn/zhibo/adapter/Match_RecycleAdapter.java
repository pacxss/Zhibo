package com.example.lanouhn.zhibo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lanouhn.zhibo.R;
import com.example.lanouhn.zhibo.contants.match.items;
import com.example.lanouhn.zhibo.interfaces.Rv_ItemClick;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * 全部赛事的RecyclerView的适配器
 * Created by lanouhn on 16/8/23.
 */
public class Match_RecycleAdapter extends RecyclerView.Adapter<Match_RecycleAdapter.ViewHolder> {

    private List<items> itemsList;    //item的集合
    private Context context;
    private Rv_ItemClick itemClick;

    public void setItemClick(Rv_ItemClick itemClick) {
        this.itemClick = itemClick;
    }

    public Match_RecycleAdapter(Rv_ItemClick itemClick) {
        this.itemClick = itemClick;
    }

    public Match_RecycleAdapter(List<items> itemsList, Context context) {
        this.itemsList = itemsList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.match_rv_item, null);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        items data = itemsList.get(position);

        Picasso.with(context).load(data.getAvtar()).into(holder.match_itme_iv_backaround);

        if (data.getVideos() != null) {
            if (data.getVideos().getTitle() != null) {    //当有更新的时候显示
                holder.match_tv_des.setVisibility(View.VISIBLE);
                holder.match_tv_des.append(data.getVideos().getTitle());
            } else    //没有更新的时候隐藏
                holder.match_tv_des.setVisibility(View.GONE);
        }

        holder.match_tv_title.setText(data.getName());

        if (data.getProcess().equals("已结束"))    //根据赛事状态设置背景
            holder.match_tv_state.setBackgroundResource(R.drawable.shape_over);
        else
            holder.match_tv_state.setBackgroundResource(R.drawable.shape_ing);
        holder.match_tv_state.setText(data.getProcess());

        if (itemClick != null) {
            holder.match_ll_item.setOnClickListener(new View.OnClickListener() {
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
        return itemsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView match_itme_iv_backaround;    //赛事背景图片
        private TextView match_tv_title;    //标题
        private TextView match_tv_des;    //更新信息
        private TextView match_tv_state;    //赛事状况
        private LinearLayout match_ll_item;    //item

        public ViewHolder(View itemView) {
            super(itemView);

            match_tv_state = (TextView) itemView.findViewById(R.id.match_tv_state);
            match_tv_des = (TextView) itemView.findViewById(R.id.match_tv_des);
            match_tv_title = (TextView) itemView.findViewById(R.id.match_tv_title);
            match_itme_iv_backaround = (ImageView) itemView.findViewById(R.id.match_itme_iv_backaround);
            match_ll_item = (LinearLayout) itemView.findViewById(R.id.match_ll_item);

        }
    }
}
