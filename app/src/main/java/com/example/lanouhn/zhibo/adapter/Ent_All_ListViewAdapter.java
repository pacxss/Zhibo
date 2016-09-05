package com.example.lanouhn.zhibo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lanouhn.zhibo.R;
import com.example.lanouhn.zhibo.contants.ent.columns;
import com.example.lanouhn.zhibo.contants.ent.rooms;
import com.example.lanouhn.zhibo.view.NoScrollGridView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * 娱乐页面中全部的ListView的适配器
 * Created by lanouhn on 2016/8/19.
 */
public class Ent_All_ListViewAdapter extends BaseAdapter {

    private Context context;
    private List<columns> list;

    public Ent_All_ListViewAdapter(Context context, List<columns> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.ent_all_lv_item, null);
            viewHolder = new ViewHolder();

            viewHolder.ent_item_iv_zhibo = (ImageView) view.findViewById(R.id.ent_item_iv_zhibo);
            viewHolder.ent_item_tv_title = (TextView) view.findViewById(R.id.ent_item_tv_title);
            viewHolder.ent_item_tv_total = (TextView) view.findViewById(R.id.ent_item_tv_total);
            viewHolder.ent_item_gv = (NoScrollGridView) view.findViewById(R.id.ent_item_gv);

            view.setTag(viewHolder);

        } else
            viewHolder = (ViewHolder) view.getTag();

        //赋值
        columns data = list.get(i);
        Picasso.with(context).load(data.getGame().getIcon()).into(viewHolder.ent_item_iv_zhibo);
        viewHolder.ent_item_tv_title.setText(data.getGame().getTitle());
        viewHolder.ent_item_tv_total.setText(data.getChannelsText());

        List<rooms> roomsList = data.getRooms();
        Ent_All_Head_GridViewAdapter gridViewAdapter = new Ent_All_Head_GridViewAdapter(context , roomsList);
        viewHolder.ent_item_gv.setAdapter(gridViewAdapter);

        return view;
    }

    class ViewHolder {
        private ImageView ent_item_iv_zhibo;    //标题图片
        private TextView ent_item_tv_title;    //标题
        private TextView ent_item_tv_total;    //人数
        private NoScrollGridView ent_item_gv;    //房间布局
        private Ent_All_Head_GridViewAdapter gridViewAdapter;
    }
}
