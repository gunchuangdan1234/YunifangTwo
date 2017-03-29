package com.bawei.yunifang.viewholder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bawei.yunifang.R;

public class GongXiaoActivity extends AppCompatActivity {

    private ImageView gong_back_img;
    private TextView gong_title_tv;
    private LinearLayout gong_title_lin;
    private RecyclerView gong_recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gong_xiao);
        //初始化控件
        initView();
    }

    private void initView() {
        gong_back_img = (ImageView) findViewById(R.id.gong_back_img);
        gong_title_tv = (TextView) findViewById(R.id.gong_title_tv);
        gong_title_lin = (LinearLayout) findViewById(R.id.gong_title_lin);
        gong_recyclerView = (RecyclerView) findViewById(R.id.gong_recyclerView);
    }
}
