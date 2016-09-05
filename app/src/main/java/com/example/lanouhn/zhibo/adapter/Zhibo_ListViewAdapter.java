package com.example.lanouhn.zhibo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lanouhn.zhibo.R;
import com.example.lanouhn.zhibo.view.NoScrollGridView;
import com.example.lanouhn.zhibo.contants.zhibo.columns;
import com.example.lanouhn.zhibo.contants.zhibo.rooms;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * 直播页面ListView的适配器
 * Created by lanouhn on 2016/8/17.
 */
public class Zhibo_ListViewAdapter extends BaseAdapter {

    private Context context;
    private List<columns> list;

    public Zhibo_ListViewAdapter(Context context, List<com.example.lanouhn.zhibo.contants.zhibo.columns> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder = null;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.zhibo_lv_item, null);
            viewHolder = new ViewHolder();
            view.setTag(viewHolder);

            viewHolder.zhibo_item_iv_zhibo = (ImageView) view.findViewById(R.id.zhibo_item_iv_zhibo);
            viewHolder.zhibo_item_tv_title = (TextView) view.findViewById(R.id.zhibo_item_tv_title);
            viewHolder.zhibo_item_tv_total = (TextView) view.findViewById(R.id.zhibo_item_tv_total);
            viewHolder.zhibo_item_gv = (NoScrollGridView) view.findViewById(R.id.zhibo_item_gv);
        } else
            viewHolder = (ViewHolder) view.getTag();

        //赋值
        columns data = list.get(i);
        Picasso.with(context).load(data.getGame().getIcon()).into(viewHolder.zhibo_item_iv_zhibo);
        viewHolder.zhibo_item_tv_title.setText(data.getGame().getTitle());
        viewHolder.zhibo_item_tv_total.setText(data.getChannelsText());

        List<rooms> roomsList = data.getRooms();
        Zhibo_GridViewAdapter gridViewAdapter = new Zhibo_GridViewAdapter(context , roomsList);
        viewHolder.zhibo_item_gv.setAdapter(gridViewAdapter);

        return view;
    }

    class ViewHolder {
        private ImageView zhibo_item_iv_zhibo;    //标题图片
        private TextView zhibo_item_tv_title;    //标题
        private TextView zhibo_item_tv_total;    //人数
        private NoScrollGridView zhibo_item_gv;    //房间布局
        private Zhibo_GridViewAdapter gridViewAdapter;

    }
}
