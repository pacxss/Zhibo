package com.example.lanouhn.zhibo.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lanouhn.zhibo.R;
import com.example.lanouhn.zhibo.contants.Rank;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by lanouhn on 16/9/1.
 */
public class Live_ListViewAdapter extends BaseAdapter {

    private Context context;
    private List<Rank.Ranks> ranksList;    //贡献榜的实体类

    public Live_ListViewAdapter(Context context, List<Rank.Ranks> ranksList) {
        this.context = context;
        this.ranksList = ranksList;
        Log.e("TAG", "Live_ListViewAdapter: " + (ranksList == null));
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Object getItem(int position) {
        return ranksList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.live_lv_item, null);
            viewHolder = new ViewHolder();

            viewHolder.live_item_iv = (TextView) convertView.findViewById(R.id.live_item_iv);
            viewHolder.live_item_tv_name = (TextView) convertView.findViewById(R.id.live_item_tv_name);
            viewHolder.live_item_iv_head = (ImageView) convertView.findViewById(R.id.live_item_iv_heads);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (position < ranksList.size()) {    //如果有值，则赋值
            viewHolder.live_item_tv_name.setText(ranksList.get(position).getUserName());
            viewHolder.live_item_iv.setText(String.valueOf(position + 1));
            Picasso.with(context).load(ranksList.get(position).getAvatar()).into(viewHolder.live_item_iv_head);
        } else {    //没值就使用假数据
            viewHolder.live_item_tv_name.setText(R.string.live_tv_name);
            viewHolder.live_item_iv.setText(String.valueOf(position + 1));
            viewHolder.live_item_iv_head.setImageResource(R.mipmap.img_user_icon80);
        }

        return convertView;
    }

    class ViewHolder {
        private TextView live_item_tv_name;
        private ImageView live_item_iv_head;
        private TextView live_item_iv;
    }
}
