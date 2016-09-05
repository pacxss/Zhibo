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
import com.example.lanouhn.zhibo.adapter.Ent_ViewpagerAdapter;
import com.example.lanouhn.zhibo.contants.Contants;
import com.example.lanouhn.zhibo.contants.ent.Ent;
import com.example.lanouhn.zhibo.contants.ent.tabs;
import com.example.lanouhn.zhibo.utils.Ent_All_JsonUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 娱乐页面
 * Created by lanouhn on 2016/8/16.
 */
public class EntFragment extends Fragment {

    private TabLayout ent_tab;
    private ViewPager ent_vp;

    private Ent_ViewpagerAdapter pagerAdapter;

    private List<Fragment> fragments;    //Fragment集合
    private List<tabs> tabs;    //标题的数据集合
    private Ent ent;    //实体类
    private Ent temp;    //暂存数据


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {

                tabs = temp.getData().getTabs();    //获取标题的数据

                //动态添加：给Fragment集合添加元素
                fragments = new ArrayList<>();
                fragments.add(new Ent_AllFragment());
                //循环添加Fragment
                for (int i = 0; i < tabs.size() ; i++) {
                    fragments.add(Ent_ItemFragment.newInstance(tabs.get(i).getId() , tabs.get(i).getSortby()));
                }

                //绑定适配器
                pagerAdapter = new Ent_ViewpagerAdapter(getChildFragmentManager(), tabs, fragments, getActivity());
                ent_vp.setAdapter(pagerAdapter);
                //把tablayout和viewpager绑定起来
                ent_tab.setupWithViewPager(ent_vp);
                //设置默认显示页面
                ent_vp.setCurrentItem(0);

                //循环给tablayout添加标签
                for (int y = 0; y < ent_tab.getTabCount(); y++) {
                    TabLayout.Tab tab = ent_tab.getTabAt(y);
                    tab.setCustomView(pagerAdapter.getTabView(y));
                }
            } else
                return;
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ent, null);

        ent_tab = (TabLayout) view.findViewById(R.id.ent_tab);
        ent_vp = (ViewPager) view.findViewById(R.id.ent_vp);

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
                String url = Contants.ENT_ALL;

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
                        temp = Ent_All_JsonUtil.getData(response.body().string());
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
