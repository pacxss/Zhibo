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
import android.widget.TextView;
import android.widget.Toast;

import com.example.lanouhn.zhibo.R;
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
 * 所有房间的页面
 */
public class AllRoomsActivity extends AppCompatActivity implements Rv_ItemClick {

    private int id;    //频道id
    private String sortby;    //拼接网址用的sortby
    private int pos;    //item的位置
    private String name;    //接收到的name

    private Others others;    //实体类
    private Others temp;    //暂存集合
    private List<items> itemsList;    //房间的数据集合

    private ImageView all_iv_head;    //返回按钮
    private TextView all_tv_title;    //标题
    private XRecyclerView all_recycle;
    private Ent_Others_RecycleAdapter recycleAdapter;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    //访问错误
                    if (null != this) {
                        Toast.makeText(AllRoomsActivity.this, getResources().getString(R.string.toast), Toast.LENGTH_SHORT).show();
                        all_recycle.refreshComplete();    //停止刷新
                    }
                    break;
                case 1:
                    //下拉刷新
                    //先清空数据，保证没有加载新数据
                    others = new Others();
                    others = temp;
                    itemsList = others.getData().getItems();

                    recycleAdapter = new Ent_Others_RecycleAdapter(AllRoomsActivity.this, itemsList);
                    all_recycle.setAdapter(recycleAdapter);

                    recycleAdapter.setItemClick(AllRoomsActivity.this);

                    //每次结束清空暂存集合
                    temp = new Others();
                    all_recycle.refreshComplete();    //停止刷新

                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_rooms);

        initView();
        initData();
        initEvent();
        getDataFromWeb();

        //设置为表格布局
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        all_recycle.setLayoutManager(gridLayoutManager);
        //设置图片距离 首页的最新商品列表
        SpacesItemDecoration decoration = new SpacesItemDecoration(10);
        all_recycle.addItemDecoration(decoration);

        //刷新和加载
        all_recycle.setLoadingListener(new XRecyclerView.LoadingListener() {
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

    private void initData() {
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        sortby = intent.getStringExtra("sortby");
        name = intent.getStringExtra("name");

        all_tv_title.setText(name);
    }

    private void initEvent() {
        /**
         * 返回按钮的点击事件
         */
        all_iv_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void initView() {
        all_recycle = (XRecyclerView) findViewById(R.id.all_recycle);
        all_iv_head = (ImageView) findViewById(R.id.all_iv_head);
        all_tv_title = (TextView) findViewById(R.id.all_tv_title);

    }

    /**
     * 从网络获取数据并解析
     */
    public void getDataFromWeb() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = Contants.ALL_ROOMS + id + "&sort-by=" + sortby + "&version=3.6.2&device=4";

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

    @Override
    public void myItemClick(int position) {
        Intent intent = new Intent(AllRoomsActivity.this, VideoPlayActivity.class);
        //获取数据
        String url = others.getData().getItems().get(position).getChannel().getUrl();
        intent.putExtra("url", url);
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
