package com.bawei.yunifang;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.liaoinstan.springview.widget.SpringView;

import java.util.ArrayList;

import com.bawei.yunifang.adapter.MoreGoodsReceAdapter;
import com.bawei.yunifang.base.BaseDate;
import com.bawei.yunifang.bean.MoreGoods;
import com.bawei.yunifang.divider.DividerItemDecoration;
import com.bawei.yunifang.view.ShowingPage;

public class CategoryActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView category_back_img;
    private TextView mianmo_tv1;
    private TextView mianmo_tv2;
    private TextView mianmo_tv3;
    private TextView category_background_tv1;
    private TextView category_background_tv2;
    private TextView category_background_tv3;
    private RecyclerView category_recyclerView;
    private SpringView category_springView;

        private MoreGoods bean;
    //处理消息
    Handler handler=new Handler(){


        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //接受值
            String data= (String) msg.obj;
            Gson gson=new Gson();
            bean = gson.fromJson(data, MoreGoods.class);
            Log.i("kkkk", "handleMessage: ....."+ bean.getData().get(0).getGoods_name());
            //控件赋值
            setViewData(bean);
        }
    };
    private String id;
    private ArrayList<MoreGoods.DataBean> data;
    private LinearLayout category_lin;
    private TextView category_title_tv;
    private String name;
    private LinearLayout gong_lin;
    private View cate_xian;
    private TextView gong_tv5;
    private TextView gong_tv1;
    private TextView gong_tv2;
    private TextView gong_tv3;
    private TextView gong_tv4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        //初始化布局
        initView();
        //接收传来的值
        id = getIntent().getStringExtra("id");
        name = getIntent().getStringExtra("name");
        category_title_tv.setText(name);
        if(Integer.parseInt(id)==38){
            id="9";
            category_lin.setVisibility(View.VISIBLE);
        }else{
            category_lin.setVisibility(View.GONE);
        }

        if(name.equals("功效")){
            id="17";
            category_lin.setVisibility(View.GONE);
            gong_lin.setVisibility(View.VISIBLE);
            cate_xian.setVisibility(View.GONE);
        }else{
            gong_lin.setVisibility(View.GONE);
        }
        Log.i("kkkkkk", "onCreate: ......"+id);
        //解析数据
        getData(id);

    }

    private void setViewData(MoreGoods bean) {
        //设置排列方式LinearLayoutManager.VERTICAL  竖直  两列
        category_recyclerView.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false));
        //设置数据适配器
        category_recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        data = (ArrayList<MoreGoods.DataBean>) bean.getData();
        MoreGoodsReceAdapter adapter=new MoreGoodsReceAdapter(CategoryActivity.this,data);
        category_recyclerView.setAdapter(adapter);
    }

    private void getData(String id) {
        new BaseDate() {
            @Override
            protected void setResultError(ShowingPage.StateType stateLoadError) {

            }

            @Override
            public void setResultData(String data) {
                Log.i("kkkkkk", "setResultData: ...."+data);
                Message msg=new Message();
                msg.obj=data;
                handler.sendMessage(msg);
            }
                   //http://m.yunifang.com/yunifang/mobile/goods/getall?random=92414&encode=33984fb43bc319ca16f9b7efb4baf895&category_id=39
        }.getDate("http://m.yunifang.com/yunifang/mobile/goods/getall","random=92414&encode=33984fb43bc319ca16f9b7efb4baf895&category_id="+id,Integer.parseInt(id),BaseDate.NOMALTIME);
    }

    private void initView() {
        category_back_img = (ImageView) findViewById(R.id.category_back_img);
        mianmo_tv1 = (TextView) findViewById(R.id.mianmo_tv1);
        mianmo_tv2 = (TextView) findViewById(R.id.mianmo_tv2);
        mianmo_tv3 = (TextView) findViewById(R.id.mianmo_tv3);
        category_background_tv1 = (TextView) findViewById(R.id.category_background_tv1);
        category_background_tv2 = (TextView) findViewById(R.id.category_background_tv2);
        category_background_tv3 = (TextView) findViewById(R.id.category_background_tv3);
        category_recyclerView = (RecyclerView) findViewById(R.id.category_recyclerView);
        category_springView = (SpringView) findViewById(R.id.category_springView);
        category_lin = (LinearLayout) findViewById(R.id.category_lin);
        category_title_tv = (TextView) findViewById(R.id.category_title_tv);
        gong_lin = (LinearLayout) findViewById(R.id.gong_lin);
        cate_xian = findViewById(R.id.cate_xian);

        //功效的布局
        gong_tv5 = (TextView) findViewById(R.id.gong_tv5);
        gong_tv4 = (TextView) findViewById(R.id.gong_tv4);
        gong_tv3 = (TextView) findViewById(R.id.gong_tv3);
        gong_tv2 = (TextView) findViewById(R.id.gong_tv2);
        gong_tv1 = (TextView) findViewById(R.id.gong_tv1);
        gong_tv5.setOnClickListener(this);
        gong_tv4.setOnClickListener(this);
        gong_tv3.setOnClickListener(this);
        gong_tv2.setOnClickListener(this);
        gong_tv1.setOnClickListener(this);

        //点击事件
        mianmo_tv1.setOnClickListener(this);
        mianmo_tv2.setOnClickListener(this);
        mianmo_tv3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mianmo_tv1:
                id="9";
                changeTvColor(mianmo_tv1,mianmo_tv2,mianmo_tv3);
                changeTvBackColor(category_background_tv1,category_background_tv2,category_background_tv3);
                break;
            case R.id.mianmo_tv2:
                id="10";
                changeTvColor(mianmo_tv2,mianmo_tv1,mianmo_tv3);
                changeTvBackColor(category_background_tv2,category_background_tv1,category_background_tv3);
                break;
            case R.id.mianmo_tv3:
                id="23";
                changeTvColor(mianmo_tv3,mianmo_tv2,mianmo_tv1);
                changeTvBackColor(category_background_tv3,category_background_tv1,category_background_tv2);
                break;
            case R.id.gong_tv5:
                id="20";
                changeTvColor2(gong_tv5,gong_tv1,gong_tv2,gong_tv3,gong_tv4);
                break;
            case R.id.gong_tv4:
                id="18";
                changeTvColor2(gong_tv4,gong_tv1,gong_tv2,gong_tv3,gong_tv5);
                break;
            case R.id.gong_tv3:
                id="19";
                changeTvColor2(gong_tv3,gong_tv1,gong_tv2,gong_tv4,gong_tv5);
                break;
            case R.id.gong_tv2:
                id="31";
                changeTvColor2(gong_tv2,gong_tv3,gong_tv1,gong_tv4,gong_tv5);
                break;
            case R.id.gong_tv1:
                id="17";
                changeTvColor2(gong_tv1,gong_tv2,gong_tv3,gong_tv4,gong_tv5);
                break;
            default:
                break;
        }
        getData(id);
    }

    //改变字体颜色
    public void changeTvColor(TextView tv1,TextView tv2,TextView tv3){
        tv1.setTextColor(Color.parseColor("#F38C8B"));
        tv2.setTextColor(Color.parseColor("#323232"));
        tv3.setTextColor(Color.parseColor("#323232"));
    }
    //改变字体颜色
    public void changeTvColor2(TextView tv1, TextView tv2, TextView tv3, TextView tv4, TextView tv5){
        tv1.setTextColor(Color.parseColor("#F38C8B"));
        tv2.setTextColor(Color.parseColor("#323232"));
        tv3.setTextColor(Color.parseColor("#323232"));
        tv4.setTextColor(Color.parseColor("#323232"));
        tv5.setTextColor(Color.parseColor("#323232"));
    }
    //改变tv背景颜色
    public void changeTvBackColor(TextView tv1,TextView tv2,TextView tv3){
        tv1.setBackgroundColor(Color.parseColor("#F38C8B"));
        tv2.setBackgroundColor(Color.parseColor("#323232"));
        tv3.setBackgroundColor(Color.parseColor("#323232"));
    }
}
