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
import com.example.lanouhn.zhibo.activity.VideoPlayActivity;
import com.example.lanouhn.zhibo.adapter.Ent_Others_RecycleAdapter;
import com.example.lanouhn.zhibo.contants.Contants;
import com.example.lanouhn.zhibo.contants.ent_Others.Others;
import com.example.lanouhn.zhibo.contants.ent_Others.items;
import com.example.lanouhn.zhibo.interfaces.Rv_ItemClick;
import com.example.lanouhn.zhibo.utils.Ent_Others_JsonUtil;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 娱乐页面动态加载的fragment
 * Created by lanouhn on 2016/8/19.
 */
public class Ent_ItemFragment extends Fragment implements Rv_ItemClick {

    private int id;    //频道id
    private String sortby;    //拼接网址用的sortby
    private Others others;    //实体类
    private Others temp;    //暂存集合
    private List<items> itemsList;    //房间的数据集合

    private XRecyclerView ent_tiem_recycle;
    private Ent_Others_RecycleAdapter recycleAdapter;

    /**
     * 通过动态方法创建Fragment并传入参数
     *
     * @param id
     * @return
     */
    public static Ent_ItemFragment newInstance(int id, String sortby) {
        Ent_ItemFragment fragment = new Ent_ItemFragment();

        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        bundle.putCharSequence("sortby", sortby);
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
                        ent_tiem_recycle.refreshComplete();    //停止刷新
                    }
                    break;
                case 1:
                    //下拉刷新
                    //先清空数据，保证没有加载新数据
                    others = new Others();
                    others = temp;
                    itemsList = others.getData().getItems();

                    recycleAdapter = new Ent_Others_RecycleAdapter(getActivity(), itemsList);
                    ent_tiem_recycle.setAdapter(recycleAdapter);

                    recycleAdapter.setItemClick(Ent_ItemFragment.this);

                    //每次结束清空暂存集合
                    temp = new Others();
                    ent_tiem_recycle.refreshComplete();    //停止刷新

                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ent_item, null);
        ent_tiem_recycle = (XRecyclerView) view.findViewById(R.id.ent_tiem_recycle);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            id = bundle.getInt("id");
            sortby = bundle.getString("sortby");
        }

        getDataFromWeb();

        //设置为表格布局
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        ent_tiem_recycle.setLayoutManager(gridLayoutManager);
        //设置图片距离 首页的最新商品列表
        SpacesItemDecoration decoration = new SpacesItemDecoration(10);
        ent_tiem_recycle.addItemDecoration(decoration);

        //刷新和加载
        ent_tiem_recycle.setLoadingListener(new XRecyclerView.LoadingListener() {
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

    @Override
    public void myItemClick(int position) {
        Intent intent = new Intent(getActivity(), VideoPlayActivity.class);
        //获取数据
        String url = itemsList.get(position).getChannel().getUrl();
        intent.putExtra("url", String.valueOf(url));
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

    /**
     * 从网络获取数据并解析
     */
    public void getDataFromWeb() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = Contants.ENT + id + "&sort-by=" + sortby + "&version=3.6.2&device=4";

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
                        temp = Ent_Others_JsonUtil.getData(response.body().string());
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
