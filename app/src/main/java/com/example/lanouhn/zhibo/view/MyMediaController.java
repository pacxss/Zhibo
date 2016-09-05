package com.example.lanouhn.zhibo.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lanouhn.zhibo.R;

import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

/**
 * 自定义的视频控制器
 * Created by lanouhn on 16/8/25.
 */
public class MyMediaController extends MediaController {

    private GestureDetector mGestureDetector;
    private ImageButton play_iv_back;    //返回键
    private RelativeLayout play_rl_title;    //播放窗口的标题栏
    private TextView play_tv_desc;    //播放窗口的房间标题
    private TextView play_tv_viwers;    //播放窗口的观看人数
    private ImageView play_iv_reflash;    //刷新按钮
    private ImageView play_iv_share;    //分享按钮
    private ImageView play_iv_zoom;    //刷新按钮
    private VideoView videoView;
    private Activity activity;
    private Context context;
    private int controllerWidth = 0;//设置mediaController高度为了使横屏时top显示在屏幕顶端

    /**
     * videoview 用于对视频进行控制的等，activity为了退出
     */
    public MyMediaController(Context context, VideoView videoView, Activity activity) {
        super(context);
        this.context = context;
        this.videoView = videoView;
        this.activity = activity;
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        controllerWidth = wm.getDefaultDisplay().getWidth();
        mGestureDetector = new GestureDetector(context, new MyGestureListener());
    }

    @Override
    protected View makeControllerView() {
        View view = LayoutInflater.from(context).inflate(R.layout.play_controller, null);
        view.setMinimumHeight(controllerWidth);
        play_iv_back = (ImageButton) view.findViewById(getResources().getIdentifier("play_iv_back", "id", context.getPackageName()));
        play_iv_back.setOnClickListener(backListener);

        return view;

    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        System.out.println("MYApp-MyMediaController-dispatchKeyEvent");
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mGestureDetector.onTouchEvent(event)) return true;
        // 处理手势结束
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 返回监听
     */
    private View.OnClickListener backListener = new View.OnClickListener() {
        public void onClick(View v) {
            if (activity != null) {
                activity.finish();
                videoView.stopPlayback();
            }
        }
    };

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
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
            return super.onScroll(e1, e2, distanceX, distanceY);
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
//
//    @Override
//    protected View makeControllerView() {
//        View v = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(getResources().getIdentifier("mymediacontroller", "layout", getContext().getPackageName()), this);
//        v.setMinimumHeight(controllerWidth);
//        img_back = (ImageButton) v.findViewById(getResources().getIdentifier("mediacontroller_top_back", "id", context.getPackageName()));
//        img_Battery = (ImageView) v.findViewById(getResources().getIdentifier("mediacontroller_imgBattery", "id", context.getPackageName()));
//        img_back.setOnClickListener(backListener);
//        textViewBattery = (TextView)v.findViewById(getResources().getIdentifier("mediacontroller_Battery", "id", context.getPackageName()));
//        textViewTime = (TextView)v.findViewById(getResources().getIdentifier("mediacontroller_time", "id", context.getPackageName()));
//
//        return v;
//
//    }
}
