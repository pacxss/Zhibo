package com.example.lanouhn.zhibo.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lanouhn.zhibo.LiveKit;
import com.example.lanouhn.zhibo.R;
import com.example.lanouhn.zhibo.adapter.ChatListAdapter;
import com.example.lanouhn.zhibo.contants.Contants;
import com.example.lanouhn.zhibo.contants.playVideo.PlayVideo;
import com.example.lanouhn.zhibo.contants.playVideo.playLines;
import com.example.lanouhn.zhibo.contants.playVideo.urls;
import com.example.lanouhn.zhibo.contants.videoAuthor.VideoAuthor;
import com.example.lanouhn.zhibo.fragment.PlayBottomFragment;
import com.example.lanouhn.zhibo.utils.ExceptUtil;
import com.example.lanouhn.zhibo.utils.PlayVideo_JsonUtil;
import com.example.lanouhn.zhibo.utils.VideoAuthor_JsonUtil;
import com.example.lanouhn.zhibo.view.MyMediaController_wrap;
import com.example.lanouhn.zhibo.widget.ChatListView;
import com.example.lanouhn.zhibo.widget.InputPanel;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.MessageContent;
import io.rong.imlib.model.UserInfo;
import io.rong.message.InformationNotificationMessage;
import io.rong.message.TextMessage;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 视频播放页面
 */
public class VideoPlayActivity extends AppCompatActivity {

    private ChatListView chatListView;    //聊天窗口
    private PlayBottomFragment bottomPanel;    //输入窗口
    private ChatListAdapter chatListAdapter;
    private String roomId;

    private VideoView mVideoView;
    private MediaController mMediaController;    //视频控制器
    private MyMediaController_wrap myMediaController;    //自定义的视频控制器

    private TextView play_tv_title;    //房间标题
    private TextView play_tv_des;    //房间描述
    private ImageView play_iv_head;    //头像
    private TextView play_tv_total;    //订阅人数
    private RelativeLayout play_rl_book;    //订阅按钮
    private ImageView play_iv_add;    //订阅图标
    private TextView play_tv_book;    //订阅文字
    private RelativeLayout view_play;

    private boolean isBook;    //是否订阅

    //广播接收器
    public static final String REFRESH_BROADCAST_ACTION = "com.example.lanouhn.zhibo.broadcast";

    private int id;    //接收到的用于拼接视频接口的id
    private String url;    //接收到的用于拼接主播接口的url
    private String securityUrl;    //视频播放网址
    private String ext;    //视频格式

    private PlayVideo playVideo;    //视频播放的实体类
    private List<playLines> playLines = new ArrayList<>();
    private List<urls> urlses = new ArrayList<>();

    private VideoAuthor videoAuthor;    //主播信息的实体类

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Vitamio.isInitialized(this);
//        //设置全屏，无状态栏
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if (!io.vov.vitamio.LibsChecker.checkVitamioLibs(this))
            return;
        setContentView(R.layout.activity_video_play);
        LiveKit.addEventHandler(handler);

        initView();
        initData();
        initEvent();
        startLiveShow();

        //动态注册广播
        IntentFilter filter = new IntentFilter();
        filter.addAction(REFRESH_BROADCAST_ACTION);
        registerReceiver(refreshReceiver, filter);
    }

    private void initEvent() {
        /**
         * 订阅的点击事件
         */
        play_rl_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isBook == false) {
                    play_tv_book.setText(R.string.play_tv_booked);
                    play_tv_book.setTextColor(getResources().getColor(R.color.gray));
                    play_iv_add.setImageResource(R.mipmap.ic_room_subscribed);
                    //更新ui喜欢的个数
                    changeNiceCount(1);
                    Toast.makeText(VideoPlayActivity.this, getResources().getString(R.string.play_tv_toasted), Toast.LENGTH_SHORT).show();
                } else {
                    play_tv_book.setText(R.string.play_tv_book);
                    play_tv_book.setTextColor(getResources().getColor(R.color.white));
                    play_iv_add.setImageResource(R.mipmap.ic_subscribe);
                    //更新ui喜欢的个数
                    changeNiceCount(0);
                    Toast.makeText(VideoPlayActivity.this, getResources().getString(R.string.play_tv_toasted), Toast.LENGTH_SHORT).show();
                }
                isBook = !isBook;
            }
        });
    }

    private void initData() {
        //获取传递过来的url
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDataFromWeb();
    }

    private void initView() {
        mVideoView = (VideoView) findViewById(R.id.surface_view);
        play_tv_des = (TextView) findViewById(R.id.play_tv_des);
        play_iv_head = (ImageView) findViewById(R.id.play_iv_head);
        play_tv_title = (TextView) findViewById(R.id.play_tv_title);
        play_tv_total = (TextView) findViewById(R.id.play_tv_total);
        view_play = (RelativeLayout) findViewById(R.id.view_play);
        play_rl_book = (RelativeLayout) findViewById(R.id.play_rl_book);
        play_iv_add = (ImageView) findViewById(R.id.play_iv_add);
        play_tv_book = (TextView) findViewById(R.id.play_tv_book);

        chatListView = (ChatListView) findViewById(R.id.chat_listview);
        bottomPanel = (PlayBottomFragment) getSupportFragmentManager().findFragmentById(R.id.bottom_bar);

        chatListAdapter = new ChatListAdapter();
        chatListView.setAdapter(chatListAdapter);
        bottomPanel.setInputPanelListener(new InputPanel.InputPanelListener() {
            @Override
            public void onSendClick(String text) {
                final TextMessage content = TextMessage.obtain(text);
                LiveKit.sendMessage(content);
            }
        });
    }

    /**
     * 从网络获取数据并解析
     */
    public void getVIDEOFromWeb() {    //视频解析
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = Contants.VIDEOPLAY + id + "&appId=4001&version=3.6.2&device=4";

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
                        playVideo = PlayVideo_JsonUtil.getData(response.body().string());
                        if (!playVideo.equals("")) {
                            handler.sendEmptyMessage(1);
                        } else
                            handler.sendEmptyMessage(0);
                    }
                });
            }
        }).start();
    }

    public void getDataFromWeb() {    //主播解析
        new Thread(new Runnable() {
            @Override
            public void run() {
                String data = Contants.VIDEOAUTHOR + url + "&version=3.6.2&device=4";

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
                        videoAuthor = VideoAuthor_JsonUtil.getData(response.body().string());
                        if (!videoAuthor.equals("")) {
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
                    Toast.makeText(VideoPlayActivity.this, getResources().getString(R.string.toast), Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    //赋值，循环遍历数据
                    playLines = playVideo.getPlayLines();
                    for (int x = 0; x < playLines.size(); x++) {
                        urlses = playLines.get(x).getUrls();
                        for (int y = 0; y < urlses.size(); y++) {
                            ext = urlses.get(y).getExt();
                            //获取格式为m3u8的视频的接口
                            if (ext.equals("m3u8")) {

                                Log.e("aaaaa", "y" + "========" + y);

                                securityUrl = urlses.get(y).getSecurityUrl();
                                mVideoView.setVideoPath(securityUrl);

                                Log.e("aaaaa", "securityUrl" + "==========" + securityUrl);

                                mMediaController = new MediaController(VideoPlayActivity.this);
                                myMediaController = new MyMediaController_wrap(VideoPlayActivity.this, mVideoView, VideoPlayActivity.this, videoAuthor, securityUrl);
                                mMediaController.show(5000);//控制器显示5s后自动隐藏
//                                        mVideoView.setMediaController(mMediaController);
                                mVideoView.setMediaController(myMediaController);    //绑定自定义的控制器

                                mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                    @Override
                                    public void onPrepared(MediaPlayer mediaPlayer) {
                                        //此处设置播放速度为正常速度1
                                        mediaPlayer.setPlaybackSpeed(1.0f);
                                    }
                                });
                                mVideoView.setVideoQuality(MediaPlayer.VIDEOQUALITY_HIGH);    //设置播放画质 高画质
                                mVideoView.requestFocus();    //取得焦点
                            }
                        }
                    }
                    break;
                case 2:
                    Picasso.with(VideoPlayActivity.this).load(videoAuthor.getBaseRoomInfo().getAvatar()).into(play_iv_head);
                    play_tv_title.setText(videoAuthor.getBaseRoomInfo().getName());
                    play_tv_des.setText(videoAuthor.getBaseRoomInfo().getDesc());
                    //小数处理
                    String result = ExceptUtil.getDate(videoAuthor.getBaseRoomInfo().getSubscribeCount());
                    play_tv_total.setText(result);

                    //先从主播信息中解析出id,然后拼接视频接口，解析视频
                    id = videoAuthor.getBaseRoomInfo().getId();
                    getVIDEOFromWeb();

                    break;

                case LiveKit.MESSAGE_ARRIVED: {
                    MessageContent content = (MessageContent) msg.obj;
                    chatListAdapter.addMessage(content);
                    break;
                }
                case LiveKit.MESSAGE_SENT: {
                    MessageContent content = (MessageContent) msg.obj;
                    chatListAdapter.addMessage(content);
                    break;
                }
                case LiveKit.MESSAGE_SEND_ERROR: {
                    break;
                }
                default:
            }
            chatListAdapter.notifyDataSetChanged();
        }
    };

    /**
     * 更改订阅的数量
     *
     * @param type 订阅是1，不订阅是0
     */
    private void changeNiceCount(int type) {

        int likeCount = videoAuthor.getBaseRoomInfo().getSubscribeCount();
        switch (type) {
            case 0:
                likeCount--;
                break;
            case 1:
                likeCount++;
                break;
        }
        videoAuthor.getBaseRoomInfo().setSubscribeCount(likeCount);
        videoAuthor.setLike(!videoAuthor.isLike());

        play_tv_total.setText(String.valueOf(likeCount));
    }

    /**
     * 广播接收器
     */
    private BroadcastReceiver refreshReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            //刷新，重新解析视频
            getVIDEOFromWeb();
            Toast.makeText(VideoPlayActivity.this, "refresh", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销广播接收器，适用于动态注册的广播接收器
        unregisterReceiver(refreshReceiver);
    }

    private void startLiveShow() {

        LiveKit.setCurrentUser(new UserInfo("哈哈", "绝迹", Uri.parse("http://7xs9j5.com1.z0.glb.clouddn.com/liveapp/zhenhuan.jpg")));

        joinChatRoom(String.valueOf(id));
    }

    private void joinChatRoom(final String roomId) {

        LiveKit.joinChatRoom(roomId, 2, new RongIMClient.OperationCallback() {
            @Override
            public void onSuccess() {
                final InformationNotificationMessage content = InformationNotificationMessage.obtain("进入房间");
                LiveKit.sendMessage(content);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Toast.makeText(VideoPlayActivity.this, "房间加入失败! errorCode = " + errorCode, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
