package com.example.lanouhn.zhibo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lanouhn.zhibo.R;
import com.example.lanouhn.zhibo.contants.suipai.items;
import com.example.lanouhn.zhibo.interfaces.Rv_ItemClick;
import com.example.lanouhn.zhibo.utils.ExceptUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * 随拍页面XRecyclerView的适配器
 * Created by lanouhn on 2016/8/19.
 */
public class Suipai_RecycleAdapter extends RecyclerView.Adapter<Suipai_RecycleAdapter.ViewHolder> {

    private Context context;
    private List<items> list;
    private Rv_ItemClick itemClick;

    public void setItemClick(Rv_ItemClick itemClick) {
        this.itemClick = itemClick;
    }

    public Suipai_RecycleAdapter(Context context, List<items> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.suipai_rv_item, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        Picasso.with(context).load(list.get(position).getPreview()).into(holder.suipai_item_iv_backaround);
        holder.suipai_item_tv_title.setText(list.get(position).getChannel().getName());
        String result = ExceptUtil.getDate(list.get(position).getViewers());    //小数处理
        holder.suipai_item_tv_total.setText(result);

        if (itemClick != null) {
            holder.suipai_item_iv_backaround.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition() - 1;
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

        private ImageView suipai_item_iv_backaround;    //房间的背景图片
        private TextView suipai_item_tv_title;    //房间标题
        private TextView suipai_item_tv_total;    //观看人数

        public ViewHolder(View itemView) {
            super(itemView);

            suipai_item_tv_total = (TextView) itemView.findViewById(R.id.suipai_item_tv_total);
            suipai_item_tv_title = (TextView) itemView.findViewById(R.id.suipai_item_tv_title);
            suipai_item_iv_backaround = (ImageView) itemView.findViewById(R.id.suipai_item_iv_backaround);
        }
    }
}
