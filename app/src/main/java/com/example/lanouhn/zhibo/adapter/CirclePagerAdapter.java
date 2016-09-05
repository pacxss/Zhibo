package com.example.lanouhn.zhibo.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;


/**
 * Created by lanouhn on 2016/8/17.
 */
public class CirclePagerAdapter extends PagerAdapter {

    private List<ImageView> dataList;

    public CirclePagerAdapter(List<ImageView> dataList) {
        super();
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        int newPosition = position % dataList.size();

        ImageView imageView = dataList.get(newPosition);

        container.addView(imageView);

        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //object要销毁的对象
        container.removeView((View) object);
    }
}

