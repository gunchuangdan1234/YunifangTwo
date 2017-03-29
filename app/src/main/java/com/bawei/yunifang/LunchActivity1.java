package com.bawei.yunifang;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import com.zhy.autolayout.AutoLayoutActivity;

public class LunchActivity1 extends AutoLayoutActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_lunch);
        new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    Intent inn=new Intent(LunchActivity1.this,LunchActivity2.class);
                    startActivity(inn);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
