package com.example.lanouhn.zhibo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lanouhn.zhibo.R;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;

/**
 * 个人中心页面
 */
public class PersonActivity extends AppCompatActivity {

    private ImageView person_iv_back;    //返回按钮
    private ImageView person_iv_setup;    //设置按钮
    private RelativeLayout person_rl_login;    //点击登录
    private TextView person_tv_out;    //退出登录

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        initView();
        initEvent();
    }

    private void initEvent() {
        person_rl_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Platform qq = ShareSDK.getPlatform(QQ.NAME);
//                qq.setPlatformActionListener(paListener);
//                qq.authorize();//单独授权
//                qq.showUser(null);//授权并获取用户信息
//                //authorize与showUser单独调用一个即可
//                //移除授权
//                //weibo.removeAccount(true);
            }
        });
    }

    private void initView() {

        person_rl_login = (RelativeLayout) findViewById(R.id.person_rl_login);
        person_iv_setup = (ImageView) findViewById(R.id.person_iv_setup);
        person_iv_back = (ImageView) findViewById(R.id.person_iv_back);
        person_tv_out = (TextView) findViewById(R.id.person_tv_out);
    }
}
