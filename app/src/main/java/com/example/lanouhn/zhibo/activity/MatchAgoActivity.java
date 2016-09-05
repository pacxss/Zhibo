package com.example.lanouhn.zhibo.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lanouhn.zhibo.R;
import com.example.lanouhn.zhibo.adapter.Ago_RecycleViewAdapter;
import com.example.lanouhn.zhibo.contants.Contants;
import com.example.lanouhn.zhibo.contants.ago.Ago;
import com.example.lanouhn.zhibo.contants.ago.items;
import com.example.lanouhn.zhibo.contants.agoPlay.AgoPlay;
import com.example.lanouhn.zhibo.interfaces.Rv_ItemClick;
import com.example.lanouhn.zhibo.utils.AgoPlay_JsonUtil;
import com.example.lanouhn.zhibo.utils.Ago_JsonUtil;
import com.example.lanouhn.zhibo.utils.VideoAuthor_JsonUtil;
import com.example.lanouhn.zhibo.view.NoScrollRecyclerView;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 赛事回顾页面
 */
public class MatchAgoActivity extends AppCompatActivity implements Rv_ItemClick {

    private Ago ago;    //赛事的实体类
    private List<com.example.lanouhn.zhibo.contants.ago.items> itemsList;    //赛事的数据集合
    private String roomid;    //用于拼接网址的id

    private AgoPlay agoPlay;    //正在直播的实体类
    private List<com.example.lanouhn.zhibo.contants.agoPlay.items> itemses;    //直播的数据集合

    private NoScrollRecyclerView ago_recycle;
    private ImageView match_iv_back;    //返回按钮
    private RelativeLayout ago_rl_play;    //直播窗口
    private LinearLayout ago_ll_ago;    //赛事title
    private TextView ago_tv_name;    //直播描述
    private TextView ago_tv_total;    //播放人数
    private ImageView ago_iv_backaround;    //直播背景图片
    private ImageView ago_iv_play;    //播放跳转按钮

    private Ago_RecycleViewAdapter recycleViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_ago);

        initView();
        initData();
        getDataFromWeb();
        getImageFromWeb();
        initEvent();

        //设置为表格布局
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        ago_recycle.setLayoutManager(gridLayoutManager);
        //设置图片距离 首页的最新商品列表
        SpacesItemDecoration decoration = new SpacesItemDecoration(10);
        ago_recycle.addItemDecoration(decoration);
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

        /**
         * 播放跳转按钮的点击事件
         */
        ago_iv_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MatchAgoActivity.this, VideoPlayActivity.class);
                intent.putExtra("url", roomid);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        ago_recycle = (NoScrollRecyclerView) findViewById(R.id.ago_recycle);
        match_iv_back = (ImageView) findViewById(R.id.match_iv_back);
        ago_rl_play = (RelativeLayout) findViewById(R.id.ago_rl_play);
        ago_ll_ago = (LinearLayout) findViewById(R.id.ago_ll_ago);
        ago_tv_name = (TextView) findViewById(R.id.ago_tv_name);
        ago_tv_total = (TextView) findViewById(R.id.ago_tv_total);
        ago_iv_backaround = (ImageView) findViewById(R.id.ago_iv_backaround);
        ago_iv_play = (ImageView) findViewById(R.id.ago_iv_play);
    }

    private void initData() {
        Intent intent = getIntent();
        roomid = intent.getStringExtra("roomid");
    }

    /**
     * 从网络获取数据并解析
     */
    public void getDataFromWeb() {    //赛事解析
        new Thread(new Runnable() {
            @Override
            public void run() {
                String data = Contants.MATCH_AGO + roomid + "&tags=&start-index=0&max-results=20&version=3.6.2&device=4";

                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder().url(data).build();

                Call call = client.newCall(request);

                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        handler.sendEmptyMessage(0);
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        ago = Ago_JsonUtil.getData(response.body().string());
                        if (!ago.equals("")) {
                            handler.sendEmptyMessage(1);
                        } else
                            handler.sendEmptyMessage(0);
                    }
                });
            }
        }).start();
    }

    public void getImageFromWeb() {    //直播解析
        new Thread(new Runnable() {
            @Override
            public void run() {
                String data = Contants.MATCH_ING + roomid + "&version=3.6.2&device=4";

                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder().url(data).build();

                Call call = client.newCall(request);

                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        handler.sendEmptyMessage(0);
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        agoPlay = AgoPlay_JsonUtil.getData(response.body().string());
                        if (!agoPlay.equals("")) {
                            handler.sendEmptyMessage(2);
                        } else
                            handler.sendEmptyMessage(0);
                    }
                });
            }
        }).start();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    //访问错误
                    Toast.makeText(MatchAgoActivity.this, getResources().getString(R.string.toast), Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    if (ago.getData().getTotalItems() != 0) {    //如果有赛事，则赋值
                        ago_ll_ago.setVisibility(View.VISIBLE);
                        itemsList = ago.getData().getItemses();
                        recycleViewAdapter = new Ago_RecycleViewAdapter(itemsList, MatchAgoActivity.this);
                        ago_recycle.setAdapter(recycleViewAdapter);
                    } else {    //没有则隐藏
                        ago_ll_ago.setVisibility(View.GONE);
                    }
                    break;
                case 2:
                    if (agoPlay.getData().getTotalItems() != 0) {
                        ago_rl_play.setVisibility(View.VISIBLE);
                        itemses = agoPlay.getData().getItemses();
                        ago_tv_name.setText(agoPlay.getData().getItemses().get(0).getChannel().getStatus());
                        ago_tv_total.setText(agoPlay.getData().getItemses().get(0).getViewers());
                        Picasso.with(MatchAgoActivity.this).load(agoPlay.getData().getItemses().get(0).getPreview()).into(ago_iv_backaround);
                    } else {
                        ago_rl_play.setVisibility(View.GONE);
                    }
                    break;
            }
        }
    };

    /**
     * item的点击事件
     *
     * @param position
     */
    @Override
    public void myItemClick(int position) {

//        Intent intent = new Intent(MatchActivity.this, MatchAgoActivity.class);
//        intent.putExtra("roomid", roomid);
//        startActivity(intent);
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
            outRect.bottom = space + 5;
        }
    }
}
