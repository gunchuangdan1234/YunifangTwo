package com.bawei.yunifang.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.yunifang.AddressActivity;
import com.bawei.yunifang.R;
import com.bawei.yunifang.application.MyApplication;
import com.bawei.yunifang.bean.AddressBean;
import com.bawei.yunifang.sql.sqlDao;

/**
 * Created by Pooh on 2016/12/16.
 */
public class Address_News_Fragment extends Fragment implements View.OnClickListener {

    private View vv;
    private ImageView address_news_back_but;
    private TextView address_news_title_tv;
    private TextView address_news_baocun_tv;
    private EditText address_news_shouhuoren_edit;
    private EditText address_news_phone_edit;
    private EditText address_news_shengshi_edit;
    private EditText address_news_detail_edit;
    private sqlDao dao;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        vv = inflater.inflate(R.layout.address_news_fragment,null);
        return vv;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //初始化控件
        initView();
        //得到数据库的管理类
        dao = new sqlDao(getActivity());
    }

    private void initView() {
        address_news_back_but = (ImageView)vv.findViewById(R.id.address_news_back_but);
        address_news_title_tv = (TextView) vv.findViewById(R.id.address_news_title_tv);
        address_news_baocun_tv = (TextView) vv.findViewById(R.id.address_news_baocun_tv);
        address_news_shouhuoren_edit = (EditText) vv.findViewById(R.id.address_news_shouhuoren_edit);
        address_news_phone_edit = (EditText) vv.findViewById(R.id.address_news_phone_edit);
        address_news_shengshi_edit = (EditText) vv.findViewById(R.id.address_news_shengshi_edit);
        address_news_detail_edit = (EditText) vv.findViewById(R.id.address_news_detail_edit);
        //设置监听
        address_news_baocun_tv.setOnClickListener(this);
        address_news_back_but.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.address_news_baocun_tv:

                //获取输入的值
                String name=address_news_shouhuoren_edit.getText().toString();
                String phone=address_news_phone_edit.getText().toString();
                String address=address_news_shengshi_edit.getText().toString();
                String detailaddress=address_news_detail_edit.getText().toString();

                boolean bb=panKong(name,phone,address,detailaddress);
                if(!bb){
                    //存入到地址的数据库
                    //调到管理界面
                    AddressActivity.gett(AddressActivity.News);
                    dao.addAddress(new AddressBean(name,phone,address,detailaddress,false,1+""));
                }

                //Toast.makeText(getActivity(), "...", Toast.LENGTH_SHORT).show();
                break;
            case R.id.address_news_back_but:
                if(MyApplication.myFlag){
                    AddressActivity.gett(AddressActivity.XuanZe);
                }else{
                    AddressActivity.gett(AddressActivity.Update);
                }
                break;
            default:
                break;
        }
    }

    private boolean panKong(String name, String phone, String address, String detailAddress) {
        if(TextUtils.isEmpty(name)){
            Toast.makeText(getActivity(), "请输入收货人姓名", Toast.LENGTH_SHORT).show();
            return true;
        }else if(TextUtils.isEmpty(phone)){
            Toast.makeText(getActivity(), "请输入收货人电话", Toast.LENGTH_SHORT).show();
            return true;
        }else if(TextUtils.isEmpty(address)){
            Toast.makeText(getActivity(), "请输入收货地址", Toast.LENGTH_SHORT).show();
            return true;
        }else if(TextUtils.isEmpty(detailAddress)){
            Toast.makeText(getActivity(), "请输入收货详细地址", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }
}
