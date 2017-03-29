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
import com.bawei.yunifang.bean.AddressBean;
import com.bawei.yunifang.sql.sqlDao;

/**
 * Created by Pooh on 2016/12/16.
 */
public class Address_Update_Fragment extends Fragment implements View.OnClickListener {

    private View vv;
    private TextView address_news_title_tv;
    private TextView address_news_baocun_tv;
    private EditText address_news_shouhuoren_edit;
    private EditText address_news_phone_edit;
    private EditText address_news_shengshi_edit;
    private EditText address_news_detail_edit;
    private String uppdateName;
    private sqlDao dao;
    private ImageView address_news_back_but;

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
        getSqlDao();
    }

    private void getSqlDao() {
        dao = new sqlDao(getActivity());
    }

    private void initView() {
        address_news_title_tv = (TextView) vv.findViewById(R.id.address_news_title_tv);
        address_news_baocun_tv = (TextView) vv.findViewById(R.id.address_news_baocun_tv);
        address_news_back_but = (ImageView)vv.findViewById(R.id.address_news_back_but);
        address_news_back_but.setOnClickListener(this);

        address_news_shouhuoren_edit = (EditText) vv.findViewById(R.id.address_news_shouhuoren_edit);
        address_news_phone_edit = (EditText) vv.findViewById(R.id.address_news_phone_edit);
        address_news_shengshi_edit = (EditText) vv.findViewById(R.id.address_news_shengshi_edit);
        address_news_detail_edit = (EditText) vv.findViewById(R.id.address_news_detail_edit);
        //赋值
        address_news_title_tv.setText("修改收货地址");
        //监听事件
        address_news_baocun_tv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.address_news_baocun_tv:

                //得到当前输入的值
                String name=address_news_shouhuoren_edit.getText().toString();
                String phone = address_news_phone_edit.getText().toString();
                String address=address_news_shengshi_edit.getText().toString();
                String detailAddress=address_news_detail_edit.getText().toString();
                //判空
                boolean bb=panKong(name,phone,address,detailAddress);
                if(!bb){
                    AddressActivity.gett(AddressActivity.Update);
                    AddressBean bean=new AddressBean(name,phone,address,detailAddress,false,"0");
                    dao.updateSqlUpdate(uppdateName,bean);
                }
                break;
            case R.id.address_news_back_but:
                AddressActivity.gett(AddressActivity.XuanZe);
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

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            //获取值
            AddressBean bean=Address_Manager_Fragment.list.get(Address_Manager_Fragment.position);
            //赋值
            address_news_shouhuoren_edit.setText(bean.getName());
            address_news_phone_edit.setText(bean.getPhone());
            address_news_shengshi_edit.setText(bean.getAddress());
            address_news_detail_edit.setText(bean.getDetailAddress());
            //得到当前要修改的name
            uppdateName = bean.getName();
        }
    }
}
