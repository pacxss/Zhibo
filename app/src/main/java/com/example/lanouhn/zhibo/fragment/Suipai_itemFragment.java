package com.example.lanouhn.zhibo.fragment;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.lanouhn.zhibo.R;
import com.example.lanouhn.zhibo.activity.VideoLiveActivity;
import com.example.lanouhn.zhibo.adapter.Suipai_RecycleAdapter;
import com.example.lanouhn.zhibo.contants.Contants;
import com.example.lanouhn.zhibo.contants.suipai.Suipai;
import com.example.lanouhn.zhibo.contants.suipai.items;
import com.example.lanouhn.zhibo.interfaces.Rv_ItemClick;
import com.example.lanouhn.zhibo.utils.Suipai_JsonUtil;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 随拍页面动态加载的framgnet
 * Created by lanouhn on 2016/8/18.
 */
public class Suipai_itemFragment extends Fragment implements Rv_ItemClick {

    private int id;    //频道id
    private Suipai suipai;    //实体类
    private Suipai temp;    //暂存数据
    private List<items> items;    //房间的数据集合

    private Suipai_RecycleAdapter recycleAdapter;
    private XRecyclerView suipai_recycle;

    /**
     * 通过动态方法创建Fragment并传入参数
     *
     * @param id
     * @return
     */
    public static Suipai_itemFragment newInstance(int id) {
        Suipai_itemFragment fragment = new Suipai_itemFragment();

        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        //通过setArguments方法将数据保存
        fragment.setArguments(bundle);
        return fragment;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    //访问错误
                    if (null != getActivity()) {
                        Toast.makeText(getActivity(), getResources().getString(R.string.toast), Toast.LENGTH_SHORT).show();
                        suipai_recycle.refreshComplete();    //停止刷新
                    }
                    break;
                case 1:
                    suipai = new Suipai();    //初始化集合
                    suipai = temp;    //赋值
                    items = suipai.getData().getStreams().getItems();
                    recycleAdapter = new Suipai_RecycleAdapter(getActivity(), items);
                    suipai_recycle.setAdapter(recycleAdapter);
                    recycleAdapter.setItemClick(Suipai_itemFragment.this);

                    temp = new Suipai();    //清空集合
                    suipai_recycle.refreshComplete();    //停止刷新
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_suipai_item, null);

        suipai_recycle = (XRecyclerView) view.findViewById(R.id.suipai_recycle);

        getDataFromWeb();

        //设置为表格布局
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        suipai_recycle.setLayoutManager(gridLayoutManager);
        //设置图片距离 首页的最新商品列表
        SpacesItemDecoration decoration = new SpacesItemDecoration(2);
        suipai_recycle.addItemDecoration(decoration);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            id = bundle.getInt("id");
        }

        //刷新和加载
        suipai_recycle.setLoadingListener(new XRecyclerView.LoadingListener() {
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
     * 从网络获取数据
     */
    public void getDataFromWeb() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                //拼接网址
                String url = Contants.SUIPAI + id + "&version=3.6.2&device=4";

                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url(url)
                        .build();

                Call call = client.newCall(request);

                call.enqueue(new Callback() {

                    //连接失败
                    @Override
                    public void onFailure(Call call, IOException e) {
                        handler.sendEmptyMessage(0);
                        e.printStackTrace();
                    }

                    //连接成功
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                        temp = Suipai_JsonUtil.getData(response.body().string());

                        if (!temp.equals(""))
                            handler.sendEmptyMessage(1);
                        else
                            handler.sendEmptyMessage(0);
                    }
                });
            }
        }).start();
    }

    @Override
    public void myItemClick(int position) {

        int id = items.get(position).getChannel().getId();

        Intent intent = new Intent(getActivity(), VideoLiveActivity.class);
        intent.putExtra("url", String.valueOf(id));
        startActivity(intent);
    }

    /**
     * 设置图片距离
     */
    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {

        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.left = space;
            outRect.right = space;
        }
    }

}
