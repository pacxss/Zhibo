package com.example.lanouhn.zhibo.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.lanouhn.zhibo.R;
import com.example.lanouhn.zhibo.contants.ent.tabs;

import java.util.List;

/**
 * 娱乐页面ViewPager的适配器
 * Created by lanouhn on 2016/8/19.
 */
public class Ent_ViewpagerAdapter extends FragmentStatePagerAdapter {

    private List<tabs> list;
    private List<Fragment> fragments;
    private Context context;

    public Ent_ViewpagerAdapter(FragmentManager fm, List<tabs> list, List<Fragment> fragments, Context context) {
        super(fm);
        this.list = list;
        this.fragments = fragments;
        this.context = context;
    }

    public Ent_ViewpagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    public View getTabView(int positon) {

        if (null != context) {
            View view = LayoutInflater.from(context).inflate(R.layout.suipai_tab, null);

            TextView guide_tab_tv = (TextView) view.findViewById(R.id.suipai_tab_tv);

            guide_tab_tv.setText(list.get(positon).getName());

            return view;
        }
        return null;
    }
}
