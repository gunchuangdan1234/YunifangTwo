package com.bawei.yunifang;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.zhy.autolayout.AutoLayoutActivity;

public class LunchActivity2 extends AutoLayoutActivity implements View.OnClickListener {
    int n=4;
    boolean flag=true;
    private TextView lunch2_tv;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            n--;
            lunch2_tv.setText("跳过"+n+"s");
            lunch2_tv.setTextColor(Color.RED);
            handler.sendEmptyMessageDelayed(0,900);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_lunch2);
        lunch2_tv = (TextView) findViewById(R.id.lunch2_tv);
        //监听
        lunch2_tv.setOnClickListener(this);
        //三秒跳转
        myIntent();
    }

    private void myIntent() {
        new Thread(){
            @Override
            public void run() {
                try {
                    //改变textview的值
                    handler.sendEmptyMessageDelayed(0,1000);
                    //睡眠3秒跳转
                    Thread.sleep(3000);
                    //当点击跳转时置为false,不执行此操作
                    if(flag){
                        Intent inn=new Intent(LunchActivity2.this,MainActivity.class);
                        startActivity(inn);
                        finish();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @Override
    public void onClick(View v) {
        flag=false;
        handler.removeCallbacksAndMessages(null);
        Intent inn=new Intent(LunchActivity2.this,MainActivity.class);
        startActivity(inn);
        finish();
    }
}
