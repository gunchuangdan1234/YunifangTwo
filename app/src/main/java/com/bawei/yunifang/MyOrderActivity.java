package com.bawei.yunifang;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import com.bawei.yunifang.bean.SqlBean;
import com.bawei.yunifang.fragment.MyOrderFragment;
import com.bawei.yunifang.sql.sqlDao;

public class MyOrderActivity extends FragmentActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private LinearLayout myorder_lin;
    private int id;
    public static sqlDao dao;
    private static ArrayList<SqlBean> list;
    private ViewPager order_vp;
    private ArrayList<TextView> list_tv;
    private TextView my_order_quanbu_tv;
    private TextView my_order_daifukuan_tv;
    private TextView my_order_daifahuo_tv;
    private TextView my_order_daishouhuo_tv;
    private TextView my_order_daipingjia_tv;
    public static Window window;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        //接收传过来的值
        Intent intent=getIntent();
        id = intent.getIntExtra("id",0);
       // Toast.makeText(this, "..."+ id, Toast.LENGTH_SHORT).show();
        //初始化控件
        initView();
        //数据库的管理类
        dao = new sqlDao(MyOrderActivity.this);
        //初始化数据适配器
        setMyAdapter();

        window=getWindow();
    }

    private void setMyAdapter() {
        order_vp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                if(position==0){
                    return MyOrderFragment.getInstance(3);
                }
                return MyOrderFragment.getInstance(position+2);
            }

            @Override
            public int getCount() {
                return 5;
            }
        });
    }

    private void initView() {
        list_tv = new ArrayList<>();
        myorder_lin = (LinearLayout) findViewById(R.id.myorder_lin);
        order_vp= (ViewPager) findViewById(R.id.my_order_vp);
        //设置监听
        order_vp.setOnPageChangeListener(this);

        //表示状态的几个tv
        my_order_quanbu_tv = (TextView) findViewById(R.id.my_order_quanbu_tv);
        my_order_daifukuan_tv = (TextView) findViewById(R.id.my_order_daifukuan_tv);
        my_order_daifahuo_tv = (TextView) findViewById(R.id.my_order_daifahuo_tv);
        my_order_daishouhuo_tv = (TextView) findViewById(R.id.my_order_daishouhuo_tv);
        my_order_daipingjia_tv = (TextView) findViewById(R.id.my_order_daipingjia_tv);
        list_tv.add(my_order_quanbu_tv);
        list_tv.add(my_order_daifukuan_tv);
        list_tv.add(my_order_daifahuo_tv);
        list_tv.add(my_order_daishouhuo_tv);
        list_tv.add(my_order_daipingjia_tv);

        //判断刚刚进入界面时，要显示哪一个界面
        for (int i = 0; i < myorder_lin.getChildCount(); i++) {
            TextView tv= (TextView) myorder_lin.getChildAt(i);
            if((id+1)==i){
                tv.setTextColor(Color.parseColor("#FC6B87"));
            }else{
                tv.setTextColor(Color.parseColor("#323232"));
            }
            tv.setTag(i+"");
            tv.setOnClickListener(this);
        }
    }

    //对外提供的刷新的方法
    public static void MyOrderRefrush(){
        list = dao.selectSql();
    }

    @Override
    public void onClick(View v) {
//        Toast.makeText(this, "...."+v.getTag(), Toast.LENGTH_SHORT).show();
        //获取tag值
        String tag= (String) v.getTag();
        for (int i = 0; i < list_tv.size(); i++) {
            if(Integer.parseInt(tag)==i){
                list_tv.get(i).setTextColor(Color.parseColor("#FC6B87"));
                order_vp.setCurrentItem(i);
            }else{
                list_tv.get(i).setTextColor(Color.parseColor("#323232"));
            }
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < list_tv.size(); i++) {
            if(position==i){
                list_tv.get(i).setTextColor(Color.parseColor("#FC6B87"));
            }else{
                list_tv.get(i).setTextColor(Color.parseColor("#323232"));
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
