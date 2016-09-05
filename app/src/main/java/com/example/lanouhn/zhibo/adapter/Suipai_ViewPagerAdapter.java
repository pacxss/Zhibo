package com.example.lanouhn.zhibo.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.lanouhn.zhibo.R;
import com.example.lanouhn.zhibo.contants.suipai.tabs;

import java.util.List;

/**
 * Created by lanouhn on 2016/8/18.
 */
public class Suipai_ViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<tabs> list;
    private List<Fragment> fragment;
    private Context context;

    public Suipai_ViewPagerAdapter(FragmentManager fm, List<tabs> list, List<Fragment> fragment, Context context) {
        super(fm);
        this.list = list;
        this.fragment = fragment;
        this.context = context;
    }

    public Suipai_ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragment.get(position);
    }

    @Override
    public int getCount() {
        return fragment.size();
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
