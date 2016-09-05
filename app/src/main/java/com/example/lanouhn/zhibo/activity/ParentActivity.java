package com.example.lanouhn.zhibo.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.example.lanouhn.zhibo.R;
import com.example.lanouhn.zhibo.contants.videoAuthor.VideoAuthor;
import com.example.lanouhn.zhibo.view.MyMediaController_match;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

/**
 * 全屏播放页面
 */
public class ParentActivity extends AppCompatActivity {

    private String securityUrl;

    private VideoView mVideoView;
    private MediaController mMediaController;    //视频控制器
    private MyMediaController_match myMediaController;    //自定义的视频控制器

    private RelativeLayout containerView;    //弹幕

    private VideoAuthor videoAuthor;    //主播信息的实体类

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Vitamio.isInitialized(this);
        if (!io.vov.vitamio.LibsChecker.checkVitamioLibs(this))
            return;
        //设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_parent);

        initData();

        containerView = (RelativeLayout) findViewById(R.id.containerView);
        mVideoView = (VideoView) findViewById(R.id.surface_view);

        mVideoView.setVideoPath(securityUrl);

        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mMediaController = new MediaController(ParentActivity.this);//实例化控制器
                myMediaController = new MyMediaController_match(ParentActivity.this, mVideoView, ParentActivity.this, videoAuthor, containerView);
                mMediaController.show(5000);//控制器显示5s后自动隐藏
                mVideoView.setMediaController(myMediaController);//绑定控制器
                //此处设置播放速度为正常速度1
                mediaPlayer.setPlaybackSpeed(1.0f);
            }
        });
        mVideoView.setVideoQuality(MediaPlayer.VIDEOQUALITY_HIGH);//设置播放画质 高画质
        mVideoView.requestFocus();//取得焦点
    }

    private void initData() {
        Intent intent = getIntent();
        securityUrl = intent.getStringExtra("url");
        videoAuthor = (VideoAuthor) intent.getSerializableExtra("author");
    }
}
