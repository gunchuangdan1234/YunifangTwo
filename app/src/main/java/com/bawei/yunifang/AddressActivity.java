package com.bawei.yunifang;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;

import com.bawei.yunifang.bean.AddressBean;
import com.bawei.yunifang.fragment.Address_Choose_Fragment;
import com.bawei.yunifang.fragment.Address_Manager_Fragment;
import com.bawei.yunifang.fragment.Address_News_Fragment;
import com.bawei.yunifang.fragment.Address_Update_Fragment;
import com.bawei.yunifang.fragment.Cart_Fragment;
import com.bawei.yunifang.sql.sqlDao;

public class AddressActivity extends AppCompatActivity implements View.OnClickListener {

    private FrameLayout address_fram;
    public static Address_Choose_Fragment address_choose_fragment;
    public static Address_Update_Fragment address_update_fragment;
    public static Address_Manager_Fragment address_manager_fragment;
    public static Address_News_Fragment address_news_fragment;
    private View viewById;
    private static FragmentManager manager;
    //标记
    //管理
    public static int Manager=0;
    public static int XuanZe=1;
    public static int News=2;
    public static int Update=3;
    public static int Other=4;
    private sqlDao dao;
    private ArrayList<AddressBean> listAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        //初始化控件
        initView();
        //添加fragment
        addFragment();
    }

    private void addFragment() {
        // 获取一个Fragment的容器
        manager = getSupportFragmentManager();
        // 开启事务
        android.support.v4.app.FragmentTransaction fragmentTransaction1 = manager.beginTransaction();
        // 添加Fragment
        address_choose_fragment = new Address_Choose_Fragment();
        address_update_fragment = new Address_Update_Fragment();
        address_news_fragment = new Address_News_Fragment();
        address_manager_fragment = new Address_Manager_Fragment();
        fragmentTransaction1.add(R.id.address_fram, address_choose_fragment);
        fragmentTransaction1.add(R.id.address_fram, address_update_fragment);
        fragmentTransaction1.add(R.id.address_fram, address_manager_fragment);
        fragmentTransaction1.add(R.id.address_fram, address_news_fragment);
        //先查询数据库是否有地址
        dao = new sqlDao(AddressActivity.this);
        listAddress = dao.selectSqlAddress();
        if(listAddress!=null&&listAddress.size()>0){
            hideShow(address_choose_fragment,address_news_fragment,address_update_fragment, address_manager_fragment);

        }else {
            hideShow(address_news_fragment, address_choose_fragment, address_update_fragment, address_manager_fragment);
        }
            // 提交
        fragmentTransaction1.commit();
    }

    private void initView() {
        address_fram = (FrameLayout) findViewById(R.id.address_fram);
    }

    public static void gett(int tag){
        if(tag==News){
            hideShow(address_choose_fragment,address_news_fragment,address_update_fragment,address_manager_fragment);
        }else if(tag==XuanZe){
            hideShow(address_manager_fragment,address_news_fragment,address_update_fragment,address_choose_fragment);
        }else if(tag==Manager){
            hideShow(address_update_fragment,address_manager_fragment,address_news_fragment,address_choose_fragment);
        }else if(tag==Update){
            hideShow(address_manager_fragment,address_update_fragment,address_news_fragment,address_choose_fragment);
        }else if(tag==Other){
            hideShow(address_news_fragment,address_manager_fragment,address_update_fragment,address_choose_fragment);
        }
    }

    @Override
    public void onClick(View v) {

    }
    //隐藏显示的方法
    public static void hideShow(Fragment f1,Fragment f2,Fragment f3,Fragment f4){
        android.support.v4.app.FragmentTransaction fragmentTransaction2 = manager.beginTransaction();
        fragmentTransaction2.show(f1);
        fragmentTransaction2.hide(f2);
        fragmentTransaction2.hide(f3);
        fragmentTransaction2.hide(f4);
        fragmentTransaction2.commit();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        for (int i = 0; i < listAddress.size(); i++) {
            dao.updateSqlTag(listAddress.get(i).getName(),0);
        }
        Cart_Fragment.Refrush();
    }
}
