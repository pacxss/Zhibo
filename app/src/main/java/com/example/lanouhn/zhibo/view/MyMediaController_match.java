package com.example.lanouhn.zhibo.view;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.lanouhn.zhibo.R;
import com.example.lanouhn.zhibo.activity.ParentActivity;
import com.example.lanouhn.zhibo.contants.videoAuthor.VideoAuthor;
import com.example.lanouhn.zhibo.utils.ExceptUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import io.vov.vitamio.utils.Log;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

/**
 * Created by lanouhn on 16/8/30.
 */
public class MyMediaController_match extends MediaController {

    private static final int HIDEFRAM = 0;
    private static final int SHOW_PROGRESS = 2;

    private GestureDetector GestureDetector;

    private VideoAuthor videoAuthor;    //主播信息的实体类

    private ImageButton img_back;    //返回键
    private RelativeLayout match_rl_title;    //播放窗口的标题栏
    private TextView match_tv_desc;    //播放窗口的房间标题
    private TextView match_tv_viwers;    //播放窗口的观看人数
    private ImageView match_iv_reflash;    //刷新按钮
    private ImageView match_iv_share;    //分享按钮
    private ImageView match_iv_change;    //暂定按钮
    private ImageView match_iv_gift;    //礼物按钮
    private TextView match_tv_send;    //发送窗口切换按钮
    private ImageView match_iv_danmu;    //弹幕按钮
    private TextView match_tv_hot;    //热词
    private ListView match_lv;    //热词的listView
    private LinearLayout match_ll_send;    //发送消息窗口
    private LinearLayout match_rl_tail;    //底部窗口

    private RelativeLayout containerView;    //弹幕

    private boolean isDanmu;    //是否显示弹幕
    private boolean isHot;    //是否显示热词

    private String securityUrl;    //播放网址

    private VideoView videoView;
    private Activity activity;
    private Context context;
    private int controllerWidth = 0;//设置mediaController高度为了使横屏时top显示在屏幕顶端

    private View mVolumeBrightnessLayout;
    private ImageView mOperationBg;
    private TextView mOperationTv;
    private AudioManager mAudioManager;

    private MediaPlayerControl player;
    //最大声音
    private int mMaxVolume;
    // 当前声音
    private int mVolume = -1;
    //当前亮度
    private float mBrightness = -1f;

    /**
     * 返回监听
     */
    private OnClickListener backListener = new OnClickListener() {
        public void onClick(View v) {
            if (activity != null) {
                activity.finish();
                videoView.stopPlayback();
            }
        }
    };

    /**
     * 暂停监听
     */
    private OnClickListener changeListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            playOrPause();
        }
    };

    /**
     * 弹幕监听
     */
    private OnClickListener danmuListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isDanmu == false) {
                match_iv_danmu.setImageResource(R.mipmap.btn_danmu2_n);
                containerView.setVisibility(GONE);
                isDanmu = true;
            } else {
                match_iv_danmu.setImageResource(R.mipmap.btn_danmu_h);
                containerView.setVisibility(VISIBLE);
                isDanmu = false;
            }
        }
    };

    /**
     * 热词监听
     */
    private OnClickListener hotListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isHot == false) {
                match_lv.setVisibility(VISIBLE);
                match_tv_hot.setBackgroundResource(R.mipmap.btn_hot_h);
                isHot = true;
            } else {
                match_lv.setVisibility(GONE);
                match_tv_hot.setBackgroundResource(R.mipmap.btn_hot_n);
                isHot = false;
            }
        }
    };

    /**
     * 分享监听
     */
    private OnClickListener shareListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            showShare();
        }
    };

    /**
     * 输入窗口切换监听
     */
    private OnClickListener sendListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            match_rl_tail.setVisibility(GONE);
            match_ll_send.setVisibility(VISIBLE);
        }
    };

    private SeekBar.OnSeekBarChangeListener seekListener = new SeekBar.OnSeekBarChangeListener() {
        public void onStartTrackingTouch(SeekBar bar) {

        }

        public void onProgressChanged(SeekBar bar, int progress, boolean fromuser) {

        }

        public void onStopTrackingTouch(SeekBar bar) {

        }
    };

    private Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            long pos;
            switch (msg.what) {
                case HIDEFRAM:
                    mVolumeBrightnessLayout.setVisibility(View.GONE);
                    mOperationTv.setVisibility(View.GONE);
                    break;
            }
        }
    };

    //videoview 用于对视频进行控制的等，activity为了退出
    public MyMediaController_match(Context context, VideoView videoView, Activity activity, VideoAuthor videoAuthor, RelativeLayout containerView) {
        super(context);
        this.context = context;
        this.videoView = videoView;
        this.activity = activity;
        this.videoAuthor = videoAuthor;
        this.containerView = containerView;
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        controllerWidth = wm.getDefaultDisplay().getWidth();
        GestureDetector = new GestureDetector(context, new MyGestureListener());
    }

    @Override
    protected View makeControllerView() {
//        View v = LayoutInflater.from(context).inflate(R.layout.mymediacontorller_match, null);
        View v = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(getResources().getIdentifier("mymediacontorller_match", "layout", getContext().getPackageName()), this);
        v.setMinimumHeight(controllerWidth);

        match_tv_desc = (TextView) v.findViewById(R.id.match_tv_desc);
        match_tv_viwers = (TextView) v.findViewById(R.id.match_tv_viwers);
        match_iv_share = (ImageView) v.findViewById(R.id.match_iv_share);
        match_iv_change = (ImageView) v.findViewById(R.id.match_iv_change);
        match_iv_gift = (ImageView) v.findViewById(R.id.match_iv_gift);
        match_iv_danmu = (ImageView) v.findViewById(R.id.match_iv_danmu);
        img_back = (ImageButton) v.findViewById(R.id.mediacontroller_top_back);
        match_tv_hot = (TextView) v.findViewById(R.id.match_tv_hot);
        match_lv = (ListView) v.findViewById(R.id.match_lv);
        match_tv_send = (TextView) v.findViewById(R.id.match_tv_send);
        match_rl_tail = (LinearLayout) v.findViewById(R.id.match_rl_tail);
        match_ll_send = (LinearLayout) v.findViewById(R.id.match_ll_send);

        //声音和亮度的显示控件
        mVolumeBrightnessLayout = (RelativeLayout) v.findViewById(R.id.operation_volume_brightness);
        mOperationBg = (ImageView) v.findViewById(R.id.operation_bg);
        mOperationTv = (TextView) v.findViewById(R.id.operation_tv);
        mOperationTv.setVisibility(View.GONE);
        mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        mMaxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

        initData();

        //热词中的数据
        String[] hot = {"23333333", "666666666666", "我也是醉了", "哈哈哈哈哈", "110", "我要报警了!", "这游戏太难了！", "BGM好评！", "要爆炸了要爆炸了", "不要怂，就是干", "销魂走位", "这是一盘很大的棋"};
        List<Map<String, Object>> hotList = new ArrayList<>();
        for (int i = 0; i < hot.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("text", hot[i]);
            hotList.add(map);
        }

        SimpleAdapter adapter = new SimpleAdapter(context, hotList, R.layout.match_lv_item, new String[]{"text"}, new int[]{R.id.match_lv_item_tv});
        match_lv.setAdapter(adapter);
        return v;

    }

    private void initData() {
        match_tv_desc.setText(videoAuthor.getBroadcast().getTitle());
        String total = ExceptUtil.getDate(videoAuthor.getOnlineCount());
        match_tv_viwers.setText(total);
        //给按钮设置监听事件
        match_iv_change.setOnClickListener(changeListener);
        img_back.setOnClickListener(backListener);
        match_tv_hot.setOnClickListener(hotListener);
        match_tv_send.setOnClickListener(sendListener);
        match_iv_danmu.setOnClickListener(danmuListener);
        match_iv_share.setOnClickListener(shareListener);

    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        System.out.println("MYApp-MyMediaController-dispatchKeyEvent");
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (GestureDetector.onTouchEvent(event)) return true;
        // 处理手势结束
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                endGesture();
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 手势结束
     */
    private void endGesture() {
        mVolume = -1;
        mBrightness = -1f;

        // 隐藏
//        mVolumeBrightnessLayout.setVisibility(View.GONE);
//        mOperationTv.setVisibility(View.GONE);
        myHandler.removeMessages(HIDEFRAM);
//        mHandler.sendEmptyMessageDelayed(0, 500);
        myHandler.sendEmptyMessageDelayed(HIDEFRAM, 1);
    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            //当收拾结束，并且是单击结束时，控制器隐藏/显示
            toggleMediaControlsVisiblity();

            return super.onSingleTapConfirmed(e);
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            float mOldX = e1.getX(), mOldY = e1.getY();
            int y = (int) e2.getRawY();
            int x = (int) e2.getRawX();
            Display disp = activity.getWindowManager().getDefaultDisplay();
            int windowWidth = disp.getWidth();
            int windowHeight = disp.getHeight();
            if (mOldX > windowWidth * 3.0 / 5.0) {// 右边滑动 屏幕3/5
                onVolumeSlide((mOldY - y) / windowHeight);
            } else if (mOldX < windowWidth * 2.0 / 5.0) {// 左边滑动 屏幕2/5
                onBrightnessSlide((mOldY - y) / windowHeight);
            }
            return super.onScroll(e1, e2, distanceX, distanceY);
        }

//        //双击暂停或开始
//        @Override
//        public boolean onDoubleTap(MotionEvent e) {
//            playOrPause();
//            return true;
//        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }

    /**
     * 滑动改变声音大小
     *
     * @param percent
     */
    private void onVolumeSlide(float percent) {
        if (mVolume == -1) {
            mVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            if (mVolume < 0)
                mVolume = 0;

            // 显示
//            mOperationBg.setImageResource(R.drawable.video_volumn_bg);
            mVolumeBrightnessLayout.setVisibility(View.VISIBLE);
            mOperationTv.setVisibility(VISIBLE);
        }

        int index = (int) (percent * mMaxVolume) + mVolume;
        if (index > mMaxVolume)
            index = mMaxVolume;
        else if (index < 0)
            index = 0;
        if (index >= 10) {
            mOperationBg.setImageResource(R.drawable.volmn_100);
        } else if (index >= 5 && index < 10) {
            mOperationBg.setImageResource(R.drawable.volmn_60);
        } else if (index > 0 && index < 5) {
            mOperationBg.setImageResource(R.drawable.volmn_30);
        } else {
            mOperationBg.setImageResource(R.drawable.volmn_no);
        }
        //DecimalFormat    df   = new DecimalFormat("######0.00");
        mOperationTv.setText((int) (((double) index / mMaxVolume) * 100) + "%");
        // 变更声音
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, index, 0);

    }

    /**
     * 滑动改变亮度
     *
     * @param percent
     */
    private void onBrightnessSlide(float percent) {
        if (mBrightness < 0) {
            mBrightness = activity.getWindow().getAttributes().screenBrightness;
            if (mBrightness <= 0.00f)
                mBrightness = 0.50f;
            if (mBrightness < 0.01f)
                mBrightness = 0.01f;

            // 显示
            //mOperationBg.setImageResource(R.drawable.video_brightness_bg);
            mVolumeBrightnessLayout.setVisibility(View.VISIBLE);
            mOperationTv.setVisibility(VISIBLE);

        }

        WindowManager.LayoutParams lpa = activity.getWindow().getAttributes();
        lpa.screenBrightness = mBrightness + percent;
        if (lpa.screenBrightness > 1.0f)
            lpa.screenBrightness = 1.0f;
        else if (lpa.screenBrightness < 0.01f)
            lpa.screenBrightness = 0.01f;
        activity.getWindow().setAttributes(lpa);

        mOperationTv.setText((int) (lpa.screenBrightness * 100) + "%");
        if (lpa.screenBrightness * 100 >= 90) {
            mOperationBg.setImageResource(R.drawable.light_100);
        } else if (lpa.screenBrightness * 100 >= 80 && lpa.screenBrightness * 100 < 90) {
            mOperationBg.setImageResource(R.drawable.light_90);
        } else if (lpa.screenBrightness * 100 >= 70 && lpa.screenBrightness * 100 < 80) {
            mOperationBg.setImageResource(R.drawable.light_80);
        } else if (lpa.screenBrightness * 100 >= 60 && lpa.screenBrightness * 100 < 70) {
            mOperationBg.setImageResource(R.drawable.light_70);
        } else if (lpa.screenBrightness * 100 >= 50 && lpa.screenBrightness * 100 < 60) {
            mOperationBg.setImageResource(R.drawable.light_60);
        } else if (lpa.screenBrightness * 100 >= 40 && lpa.screenBrightness * 100 < 50) {
            mOperationBg.setImageResource(R.drawable.light_50);
        } else if (lpa.screenBrightness * 100 >= 30 && lpa.screenBrightness * 100 < 40) {
            mOperationBg.setImageResource(R.drawable.light_40);
        } else if (lpa.screenBrightness * 100 >= 20 && lpa.screenBrightness * 100 < 20) {
            mOperationBg.setImageResource(R.drawable.light_30);
        } else if (lpa.screenBrightness * 100 >= 10 && lpa.screenBrightness * 100 < 20) {
            mOperationBg.setImageResource(R.drawable.light_20);
        }
    }

    /**
     * 隐藏/显示
     */
    private void toggleMediaControlsVisiblity() {
        if (isShowing()) {
            hide();
        } else {
            show();
        }
    }

    /**
     * 播放与暂停
     */
    private void playOrPause() {
        if (videoView != null)
            if (videoView.isPlaying()) {
                videoView.pause();
                match_iv_change.setImageResource(R.mipmap.ic_player_f_start);
            } else {
                videoView.start();
                match_iv_change.setImageResource(R.mipmap.ic_player_f_pause);
            }
    }

    private void showShare() {
        ShareSDK.initSDK(context);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
//        oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("哦哟,「" + videoAuthor.getBaseRoomInfo().getName() + " 」的直播");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(securityUrl);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(videoAuthor.getBaseRoomInfo().getBoardCastTitle());
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl(videoAuthor.getBaseRoomInfo().getAvatar());
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//        oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
//        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
//        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
//        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
//        oks.setSiteUrl(securityUrl);

// 启动分享GUI
        oks.show(context);
    }
}
