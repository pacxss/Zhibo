package com.example.lanouhn.zhibo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lanouhn.zhibo.R;
import com.example.lanouhn.zhibo.activity.AllRoomsActivity;
import com.example.lanouhn.zhibo.activity.VideoPlayActivity;
import com.example.lanouhn.zhibo.adapter.Ent_All_RecycleViewAdapter;
import com.example.lanouhn.zhibo.adapter.Ent_All_Tail_GridViewAdapter;
import com.example.lanouhn.zhibo.contants.Contants;
import com.example.lanouhn.zhibo.contants.ent.Ent;
import com.example.lanouhn.zhibo.contants.ent.banner;
import com.example.lanouhn.zhibo.contants.ent.columns;
import com.example.lanouhn.zhibo.contants.ent.data;
import com.example.lanouhn.zhibo.contants.ent.roomses;
import com.example.lanouhn.zhibo.interfaces.Rv_TitleClick;
import com.example.lanouhn.zhibo.interfaces.ViewPagerItemListenter;
import com.example.lanouhn.zhibo.utils.Ent_All_JsonUtil;
import com.example.lanouhn.zhibo.view.EntCircleView;
import com.example.lanouhn.zhibo.view.NoScrollGridView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 娱乐页面中的全部页面
 * Created by lanouhn on 2016/8/19.
 */
public class Ent_AllFragment extends Fragment implements ViewPagerItemListenter, Rv_TitleClick {

    private XRecyclerView ent_all_rv;
    private EntCircleView ent_head_circle;    //轮播图控件
    private TextView ent_tail_tv;
    private NoScrollGridView ent_tail_gv;

    private Ent_All_RecycleViewAdapter RecycleviewAdapter;
    private Ent_All_Tail_GridViewAdapter gridViewAdapter;

    private List<banner> banners;    //轮播图的数据集合
    private List<columns> columns;    //房间的数据集合
    private List<roomses> roomses;    //更多房间的数据集合
    private Ent ent;    //实体类
    private Ent temp;    //暂存集合

    private int id;    //点击“全部房间”跳转页面时传送的id
    private String sortby;    //点击“全部房间”跳转页面时传送的sortby
    private String name;    //点击“全部房间”跳转页面时传送的name
    private String hrefTarget;    //点击轮播图跳转时传送的url

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    //访问错误
                    if (null != getActivity()) {
                        Toast.makeText(getActivity(), getResources().getString(R.string.toast), Toast.LENGTH_SHORT).show();
                        ent_all_rv.refreshComplete();    //停止刷新
                    }
                    break;
                case 1:
                    //下拉刷新

                    ent = new Ent();    //先清空数据，保证没有加载新数据
                    ent = temp;    //给实体类赋值

                    //给轮播图赋值
                    banners = ent.getData().getBanner();
                    ent_head_circle.initData(banners);
                    ent_head_circle.setViewPagerItemListenter(Ent_AllFragment.this);

                    //给RecyclerView绑定适配器
                    columns = ent.getData().getColumns();
                    RecycleviewAdapter = new Ent_All_RecycleViewAdapter(getActivity(), columns);
                    ent_all_rv.setAdapter(RecycleviewAdapter);

                    RecycleviewAdapter.setTitleClick(Ent_AllFragment.this);    //绑定点击事件

                    ent_tail_tv.setText(ent.getData().getMore().getChannelsText());

                    //给GridView绑定适配器
                    roomses = ent.getData().getMore().getRoomses();
                    gridViewAdapter = new Ent_All_Tail_GridViewAdapter(getActivity(), roomses);
                    ent_tail_gv.setAdapter(gridViewAdapter);

                    //每次结束清空暂存集合
                    temp = new Ent();

                    ent_all_rv.refreshComplete();    //停止刷新

                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ent_all, null);
        ent_all_rv = (XRecyclerView) view.findViewById(R.id.ent_all_rv);

        //加载头部布局
        View headView = inflater.inflate(R.layout.ent_all_item_head, null);
        ent_head_circle = (EntCircleView) headView.findViewById(R.id.ent_head_circle);
        ent_all_rv.addHeaderView(headView);
        //加载尾部布局
        View footerView = inflater.inflate(R.layout.ent_all_item_tail, null);
        ent_tail_tv = (TextView) footerView.findViewById(R.id.ent_tail_tv);
        ent_tail_gv = (NoScrollGridView) footerView.findViewById(R.id.ent_tail_gv);
        ent_all_rv.addFootView(footerView);

        getDataFromWeb();

        initEvent();

        //给整体的RecyclerView设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        ent_all_rv.setLayoutManager(linearLayoutManager);

        //刷新和加载
        ent_all_rv.setLoadingListener(new XRecyclerView.LoadingListener() {
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

    private void initEvent() {
        /**
         * 尾部的GridView中item的点击事件
         */
        ent_tail_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), VideoPlayActivity.class);
                //获取数据
                int url = roomses.get(position).getChannel().getUrl();
                intent.putExtra("url", String.valueOf(url));
                startActivity(intent);
            }
        });
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

    /**
     * 轮播图的点击事件
     *
     * @param position
     */
    @Override
    public void myItemListenr(int position) {
        hrefTarget = ent.getData().getBanner().get(position).getHrefTarget();

        Intent intent = new Intent(getActivity(), VideoPlayActivity.class);
        intent.putExtra("url", hrefTarget);
        startActivity(intent);
    }

    /**
     * 标题的点击事件
     *
     * @param position
     */
    @Override
    public void myTitleClick(int position) {
        id = ent.getData().getColumns().get(position).getGame().getId();

        Log.i("aaa" , "id" + "======" +id);
        sortby = ent.getData().getColumns().get(position).getGame().getSortby();
        name = ent.getData().getColumns().get(position).getGame().getName();

        Intent intent = new Intent(getActivity(), AllRoomsActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("sortby", sortby);
        intent.putExtra("name", name);
        startActivity(intent);
    }
}
