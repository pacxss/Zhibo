package com.example.lanouhn.zhibo.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.lanouhn.zhibo.R;
import com.example.lanouhn.zhibo.adapter.Suipai_ViewPagerAdapter;
import com.example.lanouhn.zhibo.contants.Contants;
import com.example.lanouhn.zhibo.contants.suipai.Suipai;
import com.example.lanouhn.zhibo.contants.suipai.items;
import com.example.lanouhn.zhibo.contants.suipai.tabs;
import com.example.lanouhn.zhibo.utils.Suipai_JsonUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 随拍页面
 * Created by lanouhn on 2016/8/16.
 */
public class SuipaiFragment extends Fragment{

    private TabLayout suipai_tab;
    private ViewPager suipai_vp;

    private Suipai_ViewPagerAdapter pagerAdapter;

    private List<Fragment> fragments;    //Fragment集合
    private List<tabs> tabs;     //标题的数据集合
    private List<items> items;    //房间的数据集合
    private Suipai suipai;    //实体类
    private Suipai temp;    //暂存数据

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {

                tabs = temp.getData().getTabs();    //获取标题的数据

                //动态添加：给Fragment集合添加元素
                fragments = new ArrayList<>();
                //循环添加Fragment
                for (int i = 0 ; i < tabs.size() ; i++) {
                    fragments.add(Suipai_itemFragment.newInstance(tabs.get(i).getId()));
                }

                //绑定适配器
                pagerAdapter = new Suipai_ViewPagerAdapter(getChildFragmentManager(), tabs, fragments, getActivity());
                suipai_vp.setAdapter(pagerAdapter);
                //把tablayout和viewpager绑定起来
                suipai_tab.setupWithViewPager(suipai_vp);
                //设置默认显示页面
                suipai_vp.setCurrentItem(0);

                //循环给tablayout添加标签
                for (int y = 0; y < suipai_tab.getTabCount(); y++) {
                    TabLayout.Tab tab = suipai_tab.getTabAt(y);
                    tab.setCustomView(pagerAdapter.getTabView(y));
                }
            } else
                return;
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_suipai , null);
        suipai_tab = (TabLayout) view.findViewById(R.id.suipai_tab);
        suipai_vp = (ViewPager) view.findViewById(R.id.suipai_vp);

        getDataFromWeb();

        return view;
    }

    /**
     * 从网络获取数据并解析
     */
    public void getDataFromWeb() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = Contants.SUIPAI_ALL;

                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder().url(url).build();

                Call call = client.newCall(request);

                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        handler.sendEmptyMessage(0);
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        temp = Suipai_JsonUtil.getData(response.body().string());
                        if (!temp.equals("")) {
                            handler.sendEmptyMessage(1);
                        } else
                            handler.sendEmptyMessage(0);
                    }
                });
            }
        }).start();
    }
}
