package com.example.lanouhn.zhibo;

/**
 * 主显示页面
 */

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lanouhn.zhibo.activity.PersonActivity;
import com.example.lanouhn.zhibo.fakeserver.FakeServer;
import com.example.lanouhn.zhibo.utils.HttpUtil;
import com.example.lanouhn.zhibo.fragment.EntFragment;
import com.example.lanouhn.zhibo.fragment.GameFragment;
import com.example.lanouhn.zhibo.fragment.SuipaiFragment;
import com.example.lanouhn.zhibo.fragment.ZhiboFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;

public class MainActivity extends FragmentActivity {

    private FrameLayout main_frame;
    private RadioGroup main_rg;
    private TextView main_tv_title;    //标题
    private ImageView main_iv_head;    //登录

    private FragmentManager manager;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initEvent();

//        fakeLogin("张三", "123456", "rtmp://live.hkstv.hk.lxdns.com/live/hks");



        //设置默认的布局文件
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.main_frame, new ZhiboFragment());
        transaction.commit();
    }

    private void initEvent() {
        /**
         * 点击RadioButton切换页面
         */
        main_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                manager = getSupportFragmentManager();
                transaction = manager.beginTransaction();
                switch (checkedId) {
                    case R.id.main_rb_zhibo:
                        transaction.replace(R.id.main_frame, new ZhiboFragment());
                        main_tv_title.setText(getResources().getString(R.string.main_rb_zhibo));
                        break;
                    case R.id.main_rb_game:
                        transaction.replace(R.id.main_frame, new GameFragment());
                        main_tv_title.setText(getResources().getString(R.string.main_rb_game));
                        break;
                    case R.id.main_rb_suipai:
                        transaction.replace(R.id.main_frame, new SuipaiFragment());
                        main_tv_title.setText(getResources().getString(R.string.main_rb_suipai));
                        break;
                    case R.id.main_rb_ent:
                        transaction.replace(R.id.main_frame, new EntFragment());
                        main_tv_title.setText(getResources().getString(R.string.main_rb_ent));
                        break;
                }
                transaction.commit();
            }
        });

        /**
         * 登录按钮的点击事件
         */
        main_iv_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PersonActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        main_frame = (FrameLayout) findViewById(R.id.main_frame);
        main_rg = (RadioGroup) findViewById(R.id.main_rg);
        main_tv_title = (TextView) findViewById(R.id.main_tv_title);
        main_iv_head = (ImageView) findViewById(R.id.main_iv_head);

    }

    //用来监听按钮类的事件
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //实现双击退出
        exitByTwoClick();

        return false;
    }

    //用来标识是否退出
    private static boolean isExit = false;

    //双击退出方法
    private void exitByTwoClick() {
        //计时器
        Timer tExit = null;
        if (isExit == false) {
            isExit = true;  //准备退出
            Toast.makeText(this, getResources().getString(R.string.main_out), Toast.LENGTH_SHORT).show();

            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false;  //取消退出
                }
            }, 2000);
        } else {
            finish();
        }
    }

    private void fakeLogin(String id, String password, final String url) {
        final UserInfo user = FakeServer.getLoginUser(id, password);
        FakeServer.getToken(user, new HttpUtil.OnResponse() {
            @Override
            public void onResponse(int code, String body) {
                if (code != 200) {
                    Toast.makeText(MainActivity.this, body, Toast.LENGTH_SHORT).show();
                    return;
                }

                String token;
                try {
                    JSONObject jsonObj = new JSONObject(body);
                    token = jsonObj.getString("token");
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Token 解析失败!", Toast.LENGTH_SHORT).show();
                    return;
                }

                LiveKit.connect(token, new RongIMClient.ConnectCallback() {
                    @Override
                    public void onTokenIncorrect() {
                        Log.d("aaa", "connect onTokenIncorrect");
                        // 检查appKey 与token是否匹配.
                    }

                    @Override
                    public void onSuccess(String userId) {
                        Log.d("aaa", "connect onSuccess");
                        LiveKit.setCurrentUser(user);
//                        Intent intent = new Intent(MainActivity.this, LiveShowActivity.class);
//                        intent.putExtra(LiveShowActivity.LIVE_URL, url);
//                        startActivity(intent);
                    }

                    @Override
                    public void onError(RongIMClient.ErrorCode errorCode) {
                        Log.d("aaa", "connect onError = " + errorCode);
                        // 根据errorCode 检查原因.
                    }
                });
            }
        });
    }


}
