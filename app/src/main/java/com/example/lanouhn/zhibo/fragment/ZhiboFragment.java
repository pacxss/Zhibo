package com.example.lanouhn.zhibo.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.lanouhn.zhibo.R;
import com.example.lanouhn.zhibo.activity.AllRoomsActivity;
import com.example.lanouhn.zhibo.activity.VideoPlayActivity;
import com.example.lanouhn.zhibo.adapter.Zhibo_RecycleViewAdapter;
import com.example.lanouhn.zhibo.adapter.Zhibo_Circle_RecycleAdapter;
import com.example.lanouhn.zhibo.contants.Contants;
import com.example.lanouhn.zhibo.interfaces.Circle_ItemClick;
import com.example.lanouhn.zhibo.interfaces.Rv_ItemClick;
import com.example.lanouhn.zhibo.interfaces.Rv_TitleClick;
import com.example.lanouhn.zhibo.interfaces.ViewPagerItemListenter;
import com.example.lanouhn.zhibo.utils.Zhibo_JsonUtil;
import com.example.lanouhn.zhibo.view.ZhiboCircleView;
import com.example.lanouhn.zhibo.contants.zhibo.Zhibo;
import com.example.lanouhn.zhibo.contants.zhibo.banner;
import com.example.lanouhn.zhibo.contants.zhibo.columns;
import com.example.lanouhn.zhibo.contants.zhibo.data;
import com.example.lanouhn.zhibo.contants.zhibo.quickbutton;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 直播页面
 * Created by lanouhn on 2016/8/16.
 */
public class ZhiboFragment extends Fragment implements Rv_TitleClick, Rv_ItemClick, ViewPagerItemListenter {

    private XRecyclerView zhibo_rv;
    private Zhibo_RecycleViewAdapter recycleViewAdapter;
    private ZhiboCircleView zhibo_head_circle;    //轮播图
    private RecyclerView zhibo_head_recycle;    //小轮播图
    private Zhibo_Circle_RecycleAdapter recycleAdapter;

    private Zhibo zhibo;    //实体类
    private data data;    //刷新数据
    private data temp;    //暂存数据
    private List<banner> banner;    //轮播图的数据集合
    private List<quickbutton> quickbutton;    //小轮播图的数据集合
    private List<columns> columns;    //ListView的数据集合

    private int id;    //点击“全部房间”跳转页面时传送的id
    private String sortby;    //点击“全部房间”跳转页面时传送的sortby
    private String name;    //点击“全部房间”跳转页面时传送的name
    private int url;    //点击item跳转页面时传送的拼接url
    private String hrefTarget;    //点击小轮播图跳转时传送的url

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    //访问错误
                    if (null != getActivity()) {
                        Toast.makeText(getActivity(), getResources().getString(R.string.toast), Toast.LENGTH_SHORT).show();
                        zhibo_rv.refreshComplete();    //停止刷新
                    }
                    break;
                case 1:
                    //下拉刷新
                    //先清空数据，保证没有加载新数据
                    data = new data();
                    data = temp;

                    //给轮播图赋值
                    banner = data.getBanner();
                    zhibo_head_circle.initData(banner);
                    zhibo_head_circle.setViewPagerItemListenter(ZhiboFragment.this);    //绑定点击事件

                    //给小轮播图绑定适配器
                    quickbutton = data.getQuickbutton();
                    recycleAdapter = new Zhibo_Circle_RecycleAdapter(getActivity(), quickbutton);
                    zhibo_head_recycle.setAdapter(recycleAdapter);

                    recycleAdapter.setItemClick(ZhiboFragment.this);    //绑定点击事件

                    //给RecycleView绑定适配器
                    columns = data.getColumns();
                    recycleViewAdapter = new Zhibo_RecycleViewAdapter(getActivity(), columns);
                    zhibo_rv.setAdapter(recycleViewAdapter);

                    recycleViewAdapter.setTitleClick(ZhiboFragment.this);    //绑定点击事件

                    //每次结束清空暂存集合
                    temp = new data();

                    zhibo_rv.refreshComplete();    //停止刷新

                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_zhibo, null);
        zhibo_rv = (XRecyclerView) view.findViewById(R.id.zhibo_rv);

        //加载头部的布局
        View headView = inflater.inflate(R.layout.zhibo_lv_item_head, null);
        zhibo_head_circle = (ZhiboCircleView) headView.findViewById(R.id.zhibo_head_circle);
        zhibo_head_recycle = (RecyclerView) headView.findViewById(R.id.zhibo_head_recycle);
        zhibo_rv.addHeaderView(headView);

        getDataFromWeb();

        //给小轮播图设置布局管理器
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getActivity());
        linearLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        zhibo_head_recycle.setLayoutManager(linearLayoutManager1);

        //给整体的RecyclerView设置布局管理器
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getActivity());
        linearLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        zhibo_rv.setLayoutManager(linearLayoutManager2);

        //刷新和加载
        zhibo_rv.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                getDataFromWeb();
            }

            @Override
            public void onLoadMore() {
                getDataFromWeb();
            }
        });

        return view;
    }

    /**
     * 从网络获取数据并解析
     */
    public void getDataFromWeb() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = Contants.ZHIBO;

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
                        temp = Zhibo_JsonUtil.getData(response.body().string()).getData();
                        if (!temp.equals("")) {
                            handler.sendEmptyMessage(1);
                        } else
                            handler.sendEmptyMessage(0);
                    }
                });
            }
        }).start();
    }

    /**
     * recyclerView的标题点击事件
     *
     * @param position
     */
    @Override
    public void myTitleClick(int position) {
        //获取数据
        id = data.getColumns().get(position).getGame().getId();
        sortby = data.getColumns().get(position).getGame().getSortby();
        name = data.getColumns().get(position).getGame().getName();
        String tag = data.getColumns().get(position).getGame().getTag();

        if (tag.equals("suipai")) {    //如果是随拍的频道，则跳到随拍页面
            RadioButton rb_suipai = (RadioButton) getActivity().findViewById(R.id.main_rb_suipai);
            rb_suipai.setChecked(true);
        } else {    //否则跳到“更多”页面
            Intent intent = new Intent(getActivity(), AllRoomsActivity.class);
            intent.putExtra("id", id);
            intent.putExtra("sortby", sortby);
            intent.putExtra("name", name);
            startActivity(intent);
        }
    }

    /**
     * 小轮播图的点击事件
     *
     * @param position
     */
    @Override
    public void myItemClick(int position) {
        hrefTarget = data.getQuickbutton().get(position).getHrefTarget();
        name = data.getQuickbutton().get(position).getTitle();

        Intent intent = new Intent(getActivity(), AllRoomsActivity.class);
        intent.putExtra("id", hrefTarget);
        intent.putExtra("sortby", "views");
        intent.putExtra("name", name);
        startActivity(intent);
    }

    /**
     * 轮播图的点击事件
     * @param position
     */
    @Override
    public void myItemListenr(int position) {
        hrefTarget = data.getBanner().get(position).getHrefTarget();

        Intent intent = new Intent(getActivity(), VideoPlayActivity.class);
        intent.putExtra("url", hrefTarget);
        startActivity(intent);
    }
}
