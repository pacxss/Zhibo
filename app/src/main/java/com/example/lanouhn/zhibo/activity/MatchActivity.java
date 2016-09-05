package com.example.lanouhn.zhibo.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lanouhn.zhibo.R;
import com.example.lanouhn.zhibo.adapter.Match_RecycleAdapter;
import com.example.lanouhn.zhibo.contants.Contants;
import com.example.lanouhn.zhibo.contants.match.Match;
import com.example.lanouhn.zhibo.contants.match.items;
import com.example.lanouhn.zhibo.interfaces.Rv_ItemClick;
import com.example.lanouhn.zhibo.utils.Match_JsonUtil;
import com.example.lanouhn.zhibo.utils.Zhibo_JsonUtil;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 全部赛事的页面
 */
public class MatchActivity extends AppCompatActivity implements Rv_ItemClick {

    private XRecyclerView match_recycle;
    private ImageView match_iv_back;    //返回按钮

    private Match_RecycleAdapter recycleAdapter;

    private Match match;    //实体类
    private Match temp;    //暂存集合
    private List<items> itemsList;    //item的集合

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    //访问错误
                    if (null != MatchActivity.this) {
                        Toast.makeText(MatchActivity.this, getResources().getString(R.string.toast), Toast.LENGTH_SHORT).show();
                        match_recycle.refreshComplete();    //停止刷新
                    }
                    break;
                case 1:
                    match = new Match();
                    match = temp;
                    itemsList = match.getData().getItems();

                    recycleAdapter = new Match_RecycleAdapter(itemsList, MatchActivity.this);
                    match_recycle.setAdapter(recycleAdapter);
                    recycleAdapter.setItemClick(MatchActivity.this);

                    temp = new Match();

                    match_recycle.refreshComplete();    //停止刷新
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        initView();
        initEvent();

        getDataFromWeb();

        //给整体的RecyclerView设置布局管理器
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
        linearLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        match_recycle.setLayoutManager(linearLayoutManager2);
        //设置图片距离 首页的最新商品列表
        SpacesItemDecoration decoration = new SpacesItemDecoration(10);
        match_recycle.addItemDecoration(decoration);

        //刷新和加载
        match_recycle.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                getDataFromWeb();
            }

            @Override
            public void onLoadMore() {
                getDataFromWeb();
            }
        });
    }

    private void initEvent() {
        /**
         * 返回按钮的点击事件
         */
        match_iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        match_iv_back = (ImageView) findViewById(R.id.match_iv_back);
        match_recycle = (XRecyclerView) findViewById(R.id.match_recycle);
    }

    /**
     * 从网络获取数据并解析
     */
    public void getDataFromWeb() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = Contants.ALL_MATCHES;

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
                        temp = Match_JsonUtil.getData(response.body().string());
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
     * item的点击事件
     *
     * @param position
     */
    @Override
    public void myItemClick(int position) {
        String roomid = match.getData().getItems().get(position).getRoomid();

        Intent intent = new Intent(MatchActivity.this, MatchAgoActivity.class);
        intent.putExtra("roomid", roomid);
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
            outRect.bottom = space;
            outRect.top = space;
        }
    }
}













