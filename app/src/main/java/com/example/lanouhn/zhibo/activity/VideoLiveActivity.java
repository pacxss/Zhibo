package com.example.lanouhn.zhibo.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lanouhn.zhibo.LiveKit;
import com.example.lanouhn.zhibo.R;
import com.example.lanouhn.zhibo.adapter.ChatListAdapter;
import com.example.lanouhn.zhibo.adapter.Live_ListViewAdapter;
import com.example.lanouhn.zhibo.animation.HeartLayout;
import com.example.lanouhn.zhibo.contants.Contants;
import com.example.lanouhn.zhibo.contants.LiveAuthor;
import com.example.lanouhn.zhibo.contants.Rank;
import com.example.lanouhn.zhibo.contants.playVideo.PlayVideo;
import com.example.lanouhn.zhibo.contants.playVideo.playLines;
import com.example.lanouhn.zhibo.contants.playVideo.urls;
import com.example.lanouhn.zhibo.contants.videoAuthor.VideoAuthor;
import com.example.lanouhn.zhibo.fragment.LiveBottomFragment;
import com.example.lanouhn.zhibo.fragment.PlayBottomFragment;
import com.example.lanouhn.zhibo.message.GiftMessage;
import com.example.lanouhn.zhibo.utils.ExceptUtil;
import com.example.lanouhn.zhibo.utils.LiveAuthor_JsonUtil;
import com.example.lanouhn.zhibo.utils.PlayVideo_JsonUtil;
import com.example.lanouhn.zhibo.utils.Rank_JsonUtil;
import com.example.lanouhn.zhibo.utils.VideoAuthor_JsonUtil;
import com.example.lanouhn.zhibo.view.App;
import com.example.lanouhn.zhibo.view.NoScrollListView;
import com.example.lanouhn.zhibo.widget.ChatListView;
import com.example.lanouhn.zhibo.widget.InputPanel;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
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
 * 随拍视频播放页面
 */
public class VideoLiveActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewGroup background;
    private ChatListView chatListView;    //聊天窗口
    private LiveBottomFragment bottomPanel;    //输入窗口
    private ChatListAdapter chatListAdapter;
    private String roomId;
    private ImageView btnGift;    //送礼物
    private ImageView btnHeart;    //点赞

    private HeartLayout heartLayout;
    private Random random = new Random();

    private VideoView mVideoView;

    private int id;    //接收到的用于拼接视频接口的id
    private String url;    //接收到的用于拼接主播接口的url
    private String securityUrl;    //视频播放网址
    private String ext;    //视频格式

    private RelativeLayout live_rl_head;    //头像布局
    private RelativeLayout live_rl_top;    //顶部的整体布局
    private LinearLayout live_ll_change;    //点击切换的布局
    private View live_view;    //覆盖的view

    private ImageView live_change_iv_head;    //切换布局中的头像
    private TextView live_change_tv_name;    //切换布局中的名字
    private TextView live_change_tv_desc;    //切换布局中的签名
    private TextView live_change_tv_total;    //切换布局中的订阅人数
    private TextView live_change_tv_des;    //切换布局中的房间描述
    private LinearLayout live_change_ll_book;    //切换布局中的订阅按钮的布局
    private TextView live_change_tv_book;    //切换布局中的订阅按钮
    private RelativeLayout live_change_rl_book;    //切换布局中的订阅背景框
    private ImageView live_change_iv_grade;    //主播等级
    private TextView live_change_tv_kind;    //手机型号
    private TextView live_change_tv_location;    //地址
    private ImageView live_change_iv_location;    //地址图标
    private TextView live_change_tv_time;    //开始时间

    private boolean isChange;    //是否切换的标识
    private boolean isRank;    //是否打开贡献榜
    private boolean isBook;    //是否订阅

    private RelativeLayout live_rl_title;    //标题的整体布局
    private ImageView live_iv_head;    //主播头像
    private TextView live_tv_title;    //主播名字
    private TextView live_tv_total;    //观看人数
    private ImageButton live_ib_close;    //关闭按钮
    private TextView live_tv_book;    //订阅按钮
    private TextView live_tv_line;    //主线按钮
    private LinearLayout live_ll_rank;    //贡献排名
    private ImageView live_iv_one;    //第一名
    private ImageView live_iv_two;    //第二名
    private ImageView live_iv_three;    //第三名
    private ListView live_lv;    //贡献榜
    private LinearLayout live_ll_ranklist;    //贡献窗口

    private PlayVideo playVideo;    //视频播放的实体类
    private List<playLines> playLines = new ArrayList<>();
    private List<urls> urlses = new ArrayList<>();

    private LiveAuthor liveAuthor;    //主播信息的实体类
    private List<Rank.Ranks> ranksList;    //贡献榜的实体类
    private Live_ListViewAdapter listViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Vitamio.isInitialized(this);
        if (!io.vov.vitamio.LibsChecker.checkVitamioLibs(this))
            return;
        setContentView(R.layout.activity_video_live);

        LiveKit.addEventHandler(handler);

        initView();
        initData();
        initEvent();
        getDataFromWeb();
        getRankFromWeb();
    }

    private void initEvent() {
        /**
         * 退出的点击事件
         */
        live_ib_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                mVideoView.stopPlayback();
            }
        });

        /**
         * 头部点击切换事件
         */
        live_rl_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isChange == false) {    //如果没有切换，则切换
                    live_rl_top.setVisibility(View.GONE);
                    live_ll_change.setVisibility(View.VISIBLE);
                    live_view.setVisibility(View.VISIBLE);
                    isChange = true;
                }
            }
        });

        /**
         * 布局的点击切回事件
         */
        live_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isChange == true) {    //如果切换了，则切回
                    live_rl_top.setVisibility(View.VISIBLE);
                    live_ll_change.setVisibility(View.GONE);
                    live_view.setVisibility(View.GONE);
                    isChange = false;
                }
                if (isRank == true) {
                    Animation scale = AnimationUtils.loadAnimation(VideoLiveActivity.this, R.anim.scale_in);
                    live_ll_ranklist.setVisibility(View.INVISIBLE);
                    live_ll_ranklist.startAnimation(scale);
                    live_view.setVisibility(View.GONE);
                    isRank = false;
                }
            }
        });

        /**
         * 订阅按钮的点击事件
         */

        //切换前
        live_tv_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isBook == false) {
                    live_tv_book.setVisibility(View.GONE);
                    live_change_tv_book.setText(R.string.live_tv_booked);
                    live_change_rl_book.setBackgroundResource(R.drawable.shape_gray);
                    //更新ui喜欢的个数
                    changeNiceCount(1);
                    Toast.makeText(VideoLiveActivity.this, getResources().getString(R.string.play_tv_toasted), Toast.LENGTH_SHORT).show();
                    isBook = true;
                } else {
                    live_tv_book.setVisibility(View.VISIBLE);
                    live_change_tv_book.setText(R.string.live_tv_book);
                    live_change_rl_book.setBackgroundResource(R.drawable.shape_blue);
                    //更新ui喜欢的个数
                    changeNiceCount(0);
                    Toast.makeText(VideoLiveActivity.this, getResources().getString(R.string.play_tv_toast), Toast.LENGTH_SHORT).show();
                    isBook = false;
                }
            }
        });

        //切换后
        live_change_rl_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isBook == false) {
                    live_tv_book.setVisibility(View.GONE);
                    live_change_tv_book.setText(R.string.live_tv_booked);
                    live_change_rl_book.setBackgroundResource(R.drawable.shape_gray);
                    //更新ui喜欢的个数
                    changeNiceCount(1);
                    Toast.makeText(VideoLiveActivity.this, getResources().getString(R.string.play_tv_toasted), Toast.LENGTH_SHORT).show();
                    isBook = true;

                } else {
                    live_tv_book.setVisibility(View.VISIBLE);
                    live_change_tv_book.setText(R.string.live_tv_bookTa);
                    live_change_rl_book.setBackgroundResource(R.drawable.shape_blue);
                    //更新ui喜欢的个数
                    changeNiceCount(0);
                    Toast.makeText(VideoLiveActivity.this, getResources().getString(R.string.play_tv_toast), Toast.LENGTH_SHORT).show();
                    isBook = false;
                }
            }
        });

        /**
         * 贡献榜的点击事件
         */
        live_ll_rank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRank == false) {
                    Animation scale = AnimationUtils.loadAnimation(VideoLiveActivity.this, R.anim.scale_out);
                    live_ll_ranklist.setVisibility(View.VISIBLE);
                    live_ll_ranklist.startAnimation(scale);
                    live_view.setVisibility(View.VISIBLE);
                    isRank = true;
                } else {
                    Animation scale = AnimationUtils.loadAnimation(VideoLiveActivity.this, R.anim.scale_in);
                    live_ll_ranklist.setVisibility(View.INVISIBLE);
                    live_ll_ranklist.startAnimation(scale);
                    live_view.setVisibility(View.INVISIBLE);
                    isRank = false;
                }
            }
        });
    }

    private void initView() {
        mVideoView = (VideoView) findViewById(R.id.surface_view);
        live_rl_title = (RelativeLayout) findViewById(R.id.live_rl_title);
        live_iv_head = (ImageView) findViewById(R.id.live_iv_head);
        live_tv_total = (TextView) findViewById(R.id.live_tv_total);
        live_tv_title = (TextView) findViewById(R.id.live_tv_title);
        live_ib_close = (ImageButton) findViewById(R.id.live_ib_close);
        live_tv_book = (TextView) findViewById(R.id.live_tv_book);
        live_tv_line = (TextView) findViewById(R.id.live_tv_line);
        live_ll_rank = (LinearLayout) findViewById(R.id.live_ll_rank);
        live_iv_one = (ImageView) findViewById(R.id.live_iv_one);
        live_iv_two = (ImageView) findViewById(R.id.live_iv_two);
        live_iv_three = (ImageView) findViewById(R.id.live_iv_three);
        live_lv = (ListView) findViewById(R.id.live_lv);
        live_ll_ranklist = (LinearLayout) findViewById(R.id.live_ll_ranklist);
        live_rl_head = (RelativeLayout) findViewById(R.id.live_rl_head);
        live_rl_top = (RelativeLayout) findViewById(R.id.live_rl_top);
        live_ll_change = (LinearLayout) findViewById(R.id.live_ll_change);
        live_view = findViewById(R.id.live_view);

        //切换后的布局中的控件
        live_change_iv_head = (ImageView) findViewById(R.id.live_change_iv_head);
        live_change_tv_name = (TextView) findViewById(R.id.live_change_tv_name);
        live_change_tv_desc = (TextView) findViewById(R.id.live_change_tv_desc);
        live_change_tv_total = (TextView) findViewById(R.id.live_change_tv_total);
        live_change_tv_des = (TextView) findViewById(R.id.live_change_tv_des);
        live_change_tv_kind = (TextView) findViewById(R.id.live_change_tv_kind);
        live_change_tv_location = (TextView) findViewById(R.id.live_change_tv_location);
        live_change_iv_location = (ImageView) findViewById(R.id.live_change_iv_location);
        live_change_tv_time = (TextView) findViewById(R.id.live_change_tv_time);
        live_change_iv_grade = (ImageView) findViewById(R.id.live_change_iv_grade);
        live_change_ll_book = (LinearLayout) findViewById(R.id.live_change_ll_book);
        live_change_tv_book = (TextView) findViewById(R.id.live_change_tv_book);
        live_change_rl_book = (RelativeLayout) findViewById(R.id.live_change_rl_book);

        //聊天窗口
        background = (ViewGroup) findViewById(R.id.background);
        chatListView = (ChatListView) findViewById(R.id.chat_listview);
        bottomPanel = (LiveBottomFragment) getSupportFragmentManager().findFragmentById(R.id.bottom_bar);
        btnGift = (ImageView) bottomPanel.getView().findViewById(R.id.btn_gift);
        btnHeart = (ImageView) bottomPanel.getView().findViewById(R.id.btn_heart);
        heartLayout = (HeartLayout) findViewById(R.id.heart_layout);

        chatListAdapter = new ChatListAdapter();
        chatListView.setAdapter(chatListAdapter);
        background.setOnClickListener(this);
        btnGift.setOnClickListener(this);
        btnHeart.setOnClickListener(this);
        bottomPanel.setInputPanelListener(new InputPanel.InputPanelListener() {
            @Override
            public void onSendClick(String text) {
                final TextMessage content = TextMessage.obtain(text);
                LiveKit.sendMessage(content);
            }
        });
    }

    private void initData() {
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
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
                String data = Contants.LIVEAUTHOR + url + "&version=3.6.2&device=4";

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
                        liveAuthor = LiveAuthor_JsonUtil.getData(response.body().string());
                        if (!liveAuthor.equals("")) {
                            handler.sendEmptyMessage(2);
                        } else
                            handler.sendEmptyMessage(0);
                    }
                });
            }
        }).start();
    }

    public void getRankFromWeb() {    //贡献榜解析
        new Thread(new Runnable() {
            @Override
            public void run() {
                String data = Contants.RANK + url + "&version=3.6.2&device=4";

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
                        ranksList = Rank_JsonUtil.getData(response.body().string());
                        if (ranksList.size() > 0) {
                            handler.sendEmptyMessage(3);
                        } else
                            handler.sendEmptyMessage(10);
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
                    Toast.makeText(VideoLiveActivity.this, getResources().getString(R.string.toast), Toast.LENGTH_SHORT).show();
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

//                                mVideoView.setMediaController(new MediaController(VideoLiveActivity.this));    //绑定控制器
                                mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                    @Override
                                    public void onPrepared(MediaPlayer mediaPlayer) {
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
                    Picasso.with(VideoLiveActivity.this).load(liveAuthor.getLogo()).into(live_iv_head);
                    live_tv_title.setText(liveAuthor.getName());
                    String total = ExceptUtil.getDate(liveAuthor.getOnlineCount());
                    live_tv_total.setText(total);

                    //给切换后的布局中的控件赋值
                    Picasso.with(VideoLiveActivity.this).load(liveAuthor.getLogo()).into(live_change_iv_head);
                    live_change_tv_name.setText(liveAuthor.getName());
                    live_change_tv_desc.setText(liveAuthor.getDesc());
                    live_change_tv_total.setText(String.valueOf(liveAuthor.getSubscribeCount()));
                    live_change_tv_des.setText(liveAuthor.getTitle());
                    live_change_tv_kind.setText(liveAuthor.getModel());

//                    //判断是否已经订阅
//                    if (liveAuthor.isLike()) {
//                        live_tv_book.setVisibility(View.GONE);
//                        live_change_tv_book.setText(R.string.live_tv_booked);
//                        live_change_rl_book.setBackgroundResource(R.drawable.shape_gray);
//                        isBook = true;
//                    } else {
//                        live_tv_book.setVisibility(View.VISIBLE);
//                        live_change_tv_book.setText(R.string.live_tv_bookTa);
//                        live_change_rl_book.setBackgroundResource(R.drawable.shape_blue);
//                        isBook = false;
//                    }

                    if (liveAuthor.getAddress() != null)    //如果有地址，则赋值
                        live_change_tv_location.setText(liveAuthor.getAddress());
                    else {    //如果没有地址，则隐藏
                        live_change_iv_location.setVisibility(View.GONE);
                        live_change_tv_location.setVisibility(View.GONE);
                    }

                    //将GMT+0800类型的时间转换为标准格式
                    String begin = liveAuthor.getBeginTime();
                    StringBuffer buffer = new StringBuffer(begin);
                    int left = buffer.indexOf("(");
                    int right = buffer.indexOf(")");
                    String strbuffer = buffer.substring(left + 1, right);
                    StringBuffer newBuffer = new StringBuffer(strbuffer);
                    int add = newBuffer.indexOf("+");
                    long op = Long.parseLong(newBuffer.substring(0, add));

                    SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
                    String dateStr = format.format(op);
                    live_change_tv_time.setText(dateStr);

                    int grade = liveAuthor.getGrade();
                    int resID = getResources().getIdentifier("icon_grade_" + grade, "mipmap", "com.example.lanouhn.zhibo");
                    live_change_iv_grade.setImageResource(resID);

                    //解析视频
                    id = Integer.parseInt(url);
                    startLiveShow();
                    getVIDEOFromWeb();

                    break;
                case 3:
                    //给贡献榜的前三名赋值，没有值就用假数据
                    if (ranksList.size() > 0 && ranksList.size() < 2)
                        Picasso.with(VideoLiveActivity.this).load(ranksList.get(0).getAvatar()).into(live_iv_one);
                    else if (ranksList.size() < 3) {
                        Picasso.with(VideoLiveActivity.this).load(ranksList.get(0).getAvatar()).into(live_iv_one);
                        Picasso.with(VideoLiveActivity.this).load(ranksList.get(1).getAvatar()).into(live_iv_two);
                    } else {
                        Picasso.with(VideoLiveActivity.this).load(ranksList.get(0).getAvatar()).into(live_iv_one);
                        Picasso.with(VideoLiveActivity.this).load(ranksList.get(1).getAvatar()).into(live_iv_two);
                        Picasso.with(VideoLiveActivity.this).load(ranksList.get(2).getAvatar()).into(live_iv_three);
                    }

                    listViewAdapter = new Live_ListViewAdapter(VideoLiveActivity.this, ranksList);
                    live_lv.setAdapter(listViewAdapter);

                    break;
                case 10:
                    listViewAdapter = new Live_ListViewAdapter(VideoLiveActivity.this, ranksList);
                    live_lv.setAdapter(listViewAdapter);
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

    @Override
    public void onBackPressed() {
        if (!bottomPanel.onBackAction()) {
            finish();
            return;
        }
    }

    @Override
    public void onClick(View v) {
        if (v.equals(background)) {
            bottomPanel.onBackAction();
        } else if (v.equals(btnGift)) {
//            GiftMessage msg = new GiftMessage("2", "送您一个礼物");
//            LiveKit.sendMessage(msg);
            showShare();
        } else if (v.equals(btnHeart)) {
            heartLayout.post(new Runnable() {
                @Override
                public void run() {
                    int rgb = Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255));
                    heartLayout.addHeart(rgb);
                }
            });
            GiftMessage msg = new GiftMessage("1", "为您点赞");
            LiveKit.sendMessage(msg);
        }
    }

    private void startLiveShow() {

        LiveKit.setCurrentUser(new UserInfo("1000", "绝迹", Uri.parse("http://7xs9j5.com1.z0.glb.clouddn.com/liveapp/zhenhuan.jpg")));

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
                Toast.makeText(VideoLiveActivity.this, "房间加入失败! errorCode = " + errorCode, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 更改订阅的数量
     *
     * @param type 订阅是1，不订阅是0
     */
    private void changeNiceCount(int type) {

        int likeCount = liveAuthor.getSubscribeCount();
        switch (type) {
            case 0:
                likeCount--;
                break;
            case 1:
                likeCount++;
                break;
        }
        liveAuthor.setSubscribeCount(likeCount);
        liveAuthor.setLike(!liveAuthor.isLike());

        live_change_tv_total.setText(String.valueOf(likeCount));
    }

    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
//        oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("听说最近不能直播造宝宝了？" + liveAuthor.getName());
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(securityUrl);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(liveAuthor.getTitle());
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl(liveAuthor.getLogo());
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//        oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
//        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
//        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
//        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(securityUrl);

// 启动分享GUI
        oks.show(this);
    }
}
