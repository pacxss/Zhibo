package com.example.lanouhn.zhibo.adapter;

import android.content.Context;
import android.content.pm.ProviderInfo;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.lanouhn.zhibo.R;
import com.example.lanouhn.zhibo.contants.game.items;
import com.example.lanouhn.zhibo.interfaces.Rv_ItemClick;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * 游戏页面RecyclerView的适配器
 * Created by lanouhn on 2016/8/17.
 */
public class Game_RecycleAdapter extends RecyclerView.Adapter<Game_RecycleAdapter.ViewHolder> {

    private Context context;
    private List<items> list;
    private Rv_ItemClick itemClick;

    public void setItemClick(Rv_ItemClick itemClick){
        this.itemClick = itemClick;
    }

    public Game_RecycleAdapter(Rv_ItemClick itemClick) {
        this.itemClick = itemClick;
    }

    public Game_RecycleAdapter(Context context, List<items> list) {
        this.context = context;
        this.list = list;
    }

    public List<items> getList() {
        return list;
    }

    public void setList(List<items> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.game_rv_item , null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Picasso.with(context).load(list.get(position).getGame().getLogo()).into(holder.game_recycle_item_image);

        if (itemClick!=null){
            holder.game_recycle_item_image.setOnClickListener(new View.OnClickListener() {
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

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView game_recycle_item_image;

        public ViewHolder(View itemView) {
            super(itemView);

            game_recycle_item_image = (ImageView) itemView.findViewById(R.id.game_recycle_item_image);
        }
    }
}
