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
import com.example.lanouhn.zhibo.activity.AllRoomsActivity;
import com.example.lanouhn.zhibo.activity.MatchActivity;
import com.example.lanouhn.zhibo.adapter.Game_RecycleAdapter;
import com.example.lanouhn.zhibo.contants.Contants;
import com.example.lanouhn.zhibo.contants.game.data;
import com.example.lanouhn.zhibo.contants.game.items;
import com.example.lanouhn.zhibo.interfaces.Rv_ItemClick;
import com.example.lanouhn.zhibo.utils.Game_JsonUtil;
import com.example.lanouhn.zhibo.utils.Zhibo_JsonUtil;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 游戏页面
 * Created by lanouhn on 2016/8/16.
 */
public class GameFragment extends Fragment implements Rv_ItemClick {

    private XRecyclerView game_recycle;
    private Game_RecycleAdapter recycleAdapter;

    private int id;    //点击跳转页面时传送的id
    private String sortby;    //点击跳转页面时传送的sortby
    private String name;    //点击跳转页面时传送的name
    private String tag;    //判断标识

    private data temp;    //实体类
    private List<items> itemsList;    //游戏图片的数据集合


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    //访问错误
                    if (null != getActivity()) {
                        Toast.makeText(getActivity(), getResources().getString(R.string.toast), Toast.LENGTH_SHORT).show();
                        game_recycle.refreshComplete();    //停止刷新
                    }
                    break;
                case 1:
                    //下拉刷新

                    itemsList = new ArrayList<>();
                    itemsList = temp.getItems();

                    Game_RecycleAdapter recycleAdapter = new Game_RecycleAdapter(getActivity(), itemsList);
                    game_recycle.setAdapter(recycleAdapter);

                    recycleAdapter.setItemClick(GameFragment.this);    //接口的点击事件

                    temp = new data();

                    game_recycle.refreshComplete();    //停止刷新

            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, null);
        game_recycle = (XRecyclerView) view.findViewById(R.id.game_recycle);

        //设置为表格布局
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        game_recycle.setLayoutManager(gridLayoutManager);
        //设置图片距离 首页的最新商品列表
        SpacesItemDecoration decoration = new SpacesItemDecoration(5);
        game_recycle.addItemDecoration(decoration);

        //刷新和加载
        game_recycle.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                getDataFromWeb();
            }

            @Override
            public void onLoadMore() {
                getDataFromWeb();
            }
        });

        getDataFromWeb();

        return view;
    }

    @Override
    public void myItemClick(int position) {
        //获取数据
        id = itemsList.get(position).getGame().getId();
        name = itemsList.get(position).getGame().getName();
        tag = itemsList.get(position).getGame().getTag();

        if (tag.equals("__matches")) {    //如果相等，跳转到赛事页面
            Intent intent = new Intent(getActivity(), MatchActivity.class);
            startActivity(intent);
        } else {    //否则跳转到更多页面
            Intent intent = new Intent(getActivity(), AllRoomsActivity.class);
            intent.putExtra("id", id);
            intent.putExtra("sortby", "view");
            intent.putExtra("name", name);
            startActivity(intent);
        }
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
            outRect.bottom = space;
            outRect.top = space;
        }
    }

    /**
     * 从网络获取数据并解析
     */
    public void getDataFromWeb() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = Contants.GAME;

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
                        temp = Game_JsonUtil.getData(response.body().string()).getData();
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
