package com.example.lanouhn.zhibo.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lanouhn.zhibo.R;
import com.example.lanouhn.zhibo.activity.VideoLiveActivity;
import com.example.lanouhn.zhibo.activity.VideoPlayActivity;
import com.example.lanouhn.zhibo.interfaces.Rv_ItemClick;
import com.example.lanouhn.zhibo.interfaces.Rv_TitleClick;
import com.example.lanouhn.zhibo.view.NoScrollGridView;
import com.example.lanouhn.zhibo.contants.zhibo.columns;
import com.example.lanouhn.zhibo.contants.zhibo.rooms;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * 直播页面ListView的适配器
 * Created by lanouhn on 2016/8/17.
 */
public class Zhibo_RecycleViewAdapter extends RecyclerView.Adapter<Zhibo_RecycleViewAdapter.ViewHolder> {

    private Context context;
    private List<columns> list;
    private Rv_TitleClick titleClick;    //标题的点击事件的接口

    public void setTitleClick(Rv_TitleClick titleClick) {    //标题的点击事件
        this.titleClick = titleClick;
    }


    public Zhibo_RecycleViewAdapter(Rv_TitleClick titleClick) {
        this.titleClick = titleClick;
    }

    public Zhibo_RecycleViewAdapter(Context context, List<com.example.lanouhn.zhibo.contants.zhibo.columns> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.zhibo_lv_item, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        //赋值
        columns data = list.get(position);
        Picasso.with(context).load(data.getGame().getIcon()).into(holder.zhibo_item_iv_zhibo);
        holder.zhibo_item_tv_title.setText(data.getGame().getTitle());
        holder.zhibo_item_tv_total.setText(data.getChannelsText());

        final List<rooms> roomsList = data.getRooms();
        final Zhibo_GridViewAdapter gridViewAdapter = new Zhibo_GridViewAdapter(context, roomsList);
        holder.zhibo_item_gv.setAdapter(gridViewAdapter);

        //如果接口对象不为空，则对标题设置点击事件
        if (titleClick != null) {
            holder.zhibo_item_tv_total.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //当item被点击时执行
                    //getLayoutPosition()方法是用来获取当前item的id
                    int pos = holder.getLayoutPosition() - 2;
                    //调用接口对象的方法来传递参数
                    titleClick.myTitleClick(pos);
                }
            });
        }

        holder.zhibo_item_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                gridViewAdapter.setSelectedPosition(position);

                //获取数据
                int url = list.get(position).getRooms().get(i).getChannel().getUrl();
                int id = list.get(position).getRooms().get(i).getChannel().getId();
                if (url == 0) {
                    Intent intent = new Intent(context, VideoLiveActivity.class);
                    intent.putExtra("url", String.valueOf(id));
                    context.startActivity(intent);
                } else {
                    Intent intent = new Intent(context, VideoPlayActivity.class);
                    intent.putExtra("url", String.valueOf(url));
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView zhibo_item_iv_zhibo;    //标题图片
        private TextView zhibo_item_tv_title;    //标题
        private TextView zhibo_item_tv_total;    //人数
        private NoScrollGridView zhibo_item_gv;    //房间布局
        private Zhibo_GridViewAdapter gridViewAdapter;

        public ViewHolder(View itemView) {
            super(itemView);

            zhibo_item_iv_zhibo = (ImageView) itemView.findViewById(R.id.zhibo_item_iv_zhibo);
            zhibo_item_tv_title = (TextView) itemView.findViewById(R.id.zhibo_item_tv_title);
            zhibo_item_tv_total = (TextView) itemView.findViewById(R.id.zhibo_item_tv_total);
            zhibo_item_gv = (NoScrollGridView) itemView.findViewById(R.id.zhibo_item_gv);
        }
    }
}
