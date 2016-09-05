package com.example.lanouhn.zhibo.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lanouhn.zhibo.R;
import com.example.lanouhn.zhibo.activity.VideoPlayActivity;
import com.example.lanouhn.zhibo.contants.ent.columns;
import com.example.lanouhn.zhibo.contants.ent.rooms;
import com.example.lanouhn.zhibo.interfaces.Rv_TitleClick;
import com.example.lanouhn.zhibo.view.NoScrollGridView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * 娱乐页面中全部的头部RecyclerView的适配器
 * Created by lanouhn on 2016/8/19.
 */
public class Ent_All_RecycleViewAdapter extends RecyclerView.Adapter<Ent_All_RecycleViewAdapter.ViewHolder> {

    private Context context;
    private List<columns> list;
    private Rv_TitleClick titleClick;    //标题的点击事件的接口

    public void setTitleClick(Rv_TitleClick titleClick) {    //标题的点击事件
        this.titleClick = titleClick;
    }


    public Ent_All_RecycleViewAdapter(Context context, List<columns> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ent_all_lv_item, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        //赋值
        columns data = list.get(position);
        Picasso.with(context).load(data.getGame().getIcon()).into(holder.ent_item_iv_zhibo);
        holder.ent_item_tv_title.setText(data.getGame().getName());
        holder.ent_item_tv_total.setText(data.getChannelsText());

        String tag = data.getGame().getTag();
        if (tag.equals("all")) {
            holder.ent_item_ll_go.setVisibility(View.GONE);
        } else
            holder.ent_item_ll_go.setVisibility(View.VISIBLE);

        List<rooms> roomsList = data.getRooms();
        final Ent_All_Head_GridViewAdapter gridViewAdapter = new Ent_All_Head_GridViewAdapter(context, roomsList);
        holder.ent_item_gv.setAdapter(gridViewAdapter);

        holder.ent_item_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                gridViewAdapter.setSelectedPosition(position);

                Intent intent = new Intent(context, VideoPlayActivity.class);
                //获取数据
                int url = list.get(position).getRooms().get(i).getChannel().getUrl();
                intent.putExtra("url", String.valueOf(url));
                context.startActivity(intent);
            }
        });

        //如果接口对象不为空，则对标题设置点击事件
        if (titleClick != null) {
            holder.ent_item_tv_total.setOnClickListener(new View.OnClickListener() {
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
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ent_item_iv_zhibo;    //标题图片
        private TextView ent_item_tv_title;    //标题
        private TextView ent_item_tv_total;    //人数
        private NoScrollGridView ent_item_gv;    //房间布局
        private LinearLayout ent_item_ll_go;
        private Ent_All_Head_GridViewAdapter gridViewAdapter;

        public ViewHolder(View itemView) {
            super(itemView);

            ent_item_iv_zhibo = (ImageView) itemView.findViewById(R.id.ent_item_iv_zhibo);
            ent_item_tv_title = (TextView) itemView.findViewById(R.id.ent_item_tv_title);
            ent_item_tv_total = (TextView) itemView.findViewById(R.id.ent_item_tv_total);
            ent_item_gv = (NoScrollGridView) itemView.findViewById(R.id.ent_item_gv);
            ent_item_ll_go = (LinearLayout) itemView.findViewById(R.id.ent_item_ll_go);
        }
    }
}
