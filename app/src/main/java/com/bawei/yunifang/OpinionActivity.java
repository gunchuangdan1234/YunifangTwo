package com.bawei.yunifang;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class OpinionActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView opinion_write_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opinion);
        //初始化控件
        initView();
    }

    private void initView() {
        opinion_write_tv = (TextView) findViewById(R.id.opinion_write_tv);
        opinion_write_tv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.opinion_write_tv:
                Intent inn=new Intent(this,MessageActivity.class);
                startActivity(inn);
                break;
            default:
                break;
        }
    }
}
