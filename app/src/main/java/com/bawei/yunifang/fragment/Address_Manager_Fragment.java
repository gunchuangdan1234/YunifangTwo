package com.bawei.yunifang.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;

import com.bawei.yunifang.AddressActivity;
import com.bawei.yunifang.R;
import com.bawei.yunifang.application.MyApplication;
import com.bawei.yunifang.bean.AddressBean;
import com.bawei.yunifang.sql.sqlDao;

/**
 * Created by Pooh on 2016/12/16.
 */
public class Address_Manager_Fragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {

    private View vv;
    private TextView address_xuanze_title_tv;
    private ListView address_xuanze_lv;
    private sqlDao dao;
    public static ArrayList<AddressBean> list;
    public static int position=0;
    private CommonAdapter commAdapter;
    private TextView address_xuanze_manager_tv;
    private ImageView address_xuanze_back_but;
    private TextView address_xuanze_but;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        vv = inflater.inflate(R.layout.address_xuanze_fragment,null);
        return vv;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //初始化控件
        initView();
    }

    private void setAdapterMy() {
        //设置数据适配器
        //赋值
        commAdapter = new CommonAdapter<AddressBean>(getActivity(), R.layout.address_lv_view,list) {
            @Override
            protected void convert(ViewHolder viewHolder, AddressBean item, int position) {
                TextView address_lv_backgorund1=viewHolder.getView(R.id.address_lv_backgorund1);
                TextView address_lv_backgorund2=viewHolder.getView(R.id.address_lv_backgorund2);
                TextView address_lv_address=viewHolder.getView(R.id.address_lv_address);
                TextView address_lv_name=viewHolder.getView(R.id.address_lv_name);
                TextView address_lv_phone=viewHolder.getView(R.id.address_lv_phone);
                CheckBox address_lv_check=viewHolder.getView(R.id.address_lv_check);
                address_lv_check.setVisibility(View.GONE);

                //赋值
                if(position!=0){
                    address_lv_backgorund1.setVisibility(View.GONE);
                    address_lv_backgorund2.setVisibility(View.GONE);
                }
                address_lv_address.setText(list.get(position).getAddress());
                address_lv_name.setText(list.get(position).getName());
                address_lv_phone.setText(list.get(position).getPhone());
            }
        };
        address_xuanze_lv.setAdapter(commAdapter);
    }

    private void selectSqlAddress() {
        dao = new sqlDao(getActivity());
        list = dao.selectSqlAddress();
    }

    private void initView() {
        address_xuanze_title_tv = (TextView) vv.findViewById(R.id.address_xuanze_title_tv);
        address_xuanze_lv = (ListView) vv.findViewById(R.id.address_xuanze_lv);
        address_xuanze_manager_tv = (TextView) vv.findViewById(R.id.address_xuanze_manager_tv);
        address_xuanze_manager_tv.setVisibility(View.GONE);
        address_xuanze_back_but = (ImageView) vv.findViewById(R.id.address_xuanze_back_but);
        address_xuanze_back_but.setOnClickListener(this);
        address_xuanze_but = (TextView) vv.findViewById(R.id.address_xuanze_but);
        address_xuanze_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddressActivity.gett(AddressActivity.Other);
                MyApplication.myFlag=true;
            }
        });
        //设置数据适配器
        address_xuanze_lv.setOnItemClickListener(this);
        address_xuanze_lv.setFocusable(true);
        //赋值
        address_xuanze_title_tv.setText("管理收货地址");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            //查询数据库的数据
            selectSqlAddress();
            if(list!=null){
                //设置适配器
                setAdapterMy();
            }
        }
        Log.i("kkkkkkk", "onHiddenChanged: .......manager"+hidden);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //显示修改界面
        AddressActivity.gett(AddressActivity.Manager);
        //设置唯一的TAG值
        Address_Manager_Fragment.position=position;
    }

    @Override
    public void onClick(View v) {
        //显示选择界面
        AddressActivity.gett(AddressActivity.News);
    }
}
