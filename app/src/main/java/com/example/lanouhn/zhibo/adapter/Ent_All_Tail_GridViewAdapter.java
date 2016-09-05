package com.example.lanouhn.zhibo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lanouhn.zhibo.R;
import com.example.lanouhn.zhibo.contants.ent.roomses;
import com.example.lanouhn.zhibo.utils.ExceptUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * 娱乐页面中全部的GridView的适配器
 * Created by lanouhn on 2016/8/19.
 */
public class Ent_All_Tail_GridViewAdapter extends BaseAdapter {

    private Context context;
    private List<roomses> list;
    private int selectedPos;
    public Ent_All_Tail_GridViewAdapter(Context context, List<roomses> list) {
        this.context = context;
        this.list = list;
    }

    /**
     * 用于获得位置
     * @param pos
     */
    public void setSelectedPosition(int pos) {
        selectedPos = pos;
        // inform the view of this change
        notifyDataSetChanged();
    }

    public int getSelectedPosition() {
        return selectedPos;
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
            view = LayoutInflater.from(context).inflate(R.layout.zhibo_gv_item, null);
            viewHolder = new ViewHolder();
            view.setTag(viewHolder);

            viewHolder.zhibo_gv_iv_backaround = (ImageView) view.findViewById(R.id.zhibo_gv_iv_backaround);
            viewHolder.zhibo_gv_tv_name = (TextView) view.findViewById(R.id.zhibo_gv_tv_name);
            viewHolder.zhibo_gv_tv_total = (TextView) view.findViewById(R.id.zhibo_gv_tv_total);
            viewHolder.zhibo_gv_tv_des = (TextView) view.findViewById(R.id.zhibo_gv_tv_des);

        } else
            viewHolder = (ViewHolder) view.getTag();

        //赋值
        Picasso.with(context).load(list.get(i).getPreview()).into(viewHolder.zhibo_gv_iv_backaround);
        viewHolder.zhibo_gv_tv_name.setText(list.get(i).getChannel().getName());
        viewHolder.zhibo_gv_tv_des.setText(list.get(i).getChannel().getStatus());
        viewHolder.zhibo_gv_tv_total.setText(String.valueOf(list.get(i).getViewers()));

        //小数处理
        String result = ExceptUtil.getDate(list.get(i).getViewers());
        viewHolder.zhibo_gv_tv_total.setText(result);

        return view;
    }

    class ViewHolder {
        private ImageView zhibo_gv_iv_backaround;    //背景图片
        private TextView zhibo_gv_tv_name;    //房间标题
        private TextView zhibo_gv_tv_total;    //观看人数
        private TextView zhibo_gv_tv_des;    //房间描述

    }
}
