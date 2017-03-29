package com.bawei.yunifang.fragment;

import android.content.Intent;
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
import com.bawei.yunifang.DingDanActivity;
import com.bawei.yunifang.R;
import com.bawei.yunifang.application.MyApplication;
import com.bawei.yunifang.bean.AddressBean;
import com.bawei.yunifang.sql.sqlDao;

/**
 * Created by Pooh on 2016/12/16.
 */
public class Address_Choose_Fragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    private View vv;
    private TextView address_xuanze_manager_tv;
    private CommonAdapter commAdapter;
    private ArrayList<AddressBean> list;
    private sqlDao dao;
    private ListView address_xuanze_lv;
    private ArrayList<AddressBean> addressBeen;
    private TextView address_xuanze_but;
    private ImageView address_xuanze_back_but;

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
        //查询数据库
        selecteSql();
        if(list!=null&&list.size()>0){
            list.get(0).setFlag(true);
        }
    }

    public void selecteSql() {
        dao=new sqlDao(getActivity());
        list = dao.selectSqlAddress();
        if(list!=null){
            //设置适配器
            setAdapterMy();
        }
    }

    private void initView() {
        address_xuanze_manager_tv = (TextView) vv.findViewById(R.id.address_xuanze_manager_tv);
        address_xuanze_lv = (ListView) vv.findViewById(R.id.address_xuanze_lv);
        address_xuanze_lv.setOnItemClickListener(this);
        address_xuanze_manager_tv.setOnClickListener(this);
        address_xuanze_but = (TextView) vv.findViewById(R.id.address_xuanze_but);
        address_xuanze_back_but = (ImageView) vv.findViewById(R.id.address_xuanze_back_but);
        address_xuanze_back_but.setOnClickListener(this);
        address_xuanze_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddressActivity.gett(AddressActivity.Other);
                MyApplication.myFlag=false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.address_xuanze_manager_tv:
                AddressActivity.gett(AddressActivity.XuanZe);
                break;
            case R.id.address_xuanze_back_but:
                //跳转到确认订单的界面
                Intent inn=new Intent(getActivity(), DingDanActivity.class);
                if(list!=null){
                    inn.putExtra("name",list.get(0).getName());
                    inn.putExtra("phone",list.get(0).getPhone());
                    inn.putExtra("address",list.get(0).getAddress());
                }
                getActivity().startActivity(inn);
                //将当前的Acyivity销毁
                getActivity().finish();
                break;
            default:
                break;
        }
    }



    private void setAdapterMy() {
        //设置数据适配器
        //赋值
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
                //赋值
                if(position!=0){
                    address_lv_backgorund1.setVisibility(View.GONE);
                    address_lv_backgorund2.setVisibility(View.GONE);
                }
                if(list.get(position).isFlag()){
                    address_lv_check.setVisibility(View.VISIBLE);
                    address_lv_check.setChecked(list.get(position).isFlag());
                }else{
                    address_lv_check.setVisibility(View.GONE);
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
        //Toast.makeText(getActivity(), "11111..."+hidden+list.size(), Toast.LENGTH_SHORT).show();
        Log.i("kkkkkkk", "onHiddenChanged: .......choose"+hidden);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //跳转到确认订单的界面
        Intent inn=new Intent(getActivity(), DingDanActivity.class);
        inn.putExtra("name",list.get(position).getName());
        inn.putExtra("phone",list.get(position).getPhone());
        inn.putExtra("address",list.get(position).getAddress());
        getActivity().startActivity(inn);
        //将当前的Acyivity销毁
        getActivity().finish();
    }
}
