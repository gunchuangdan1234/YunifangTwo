package com.bawei.yunifang.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.ArrayList;

import com.bawei.yunifang.R;
import com.bawei.yunifang.adapter.MyOrderAdapter;
import com.bawei.yunifang.bean.SqlBean;
import com.bawei.yunifang.divider.DividerItemDecoration;
import com.bawei.yunifang.sql.sqlDao;

/**
 * Created by Pooh on 2016/12/26.
 */

public class MyOrderFragment extends Fragment implements SpringView.OnFreshListener {

    private sqlDao dao;
    private View vv;
    public static SpringView order_sprview;
    private RecyclerView order_recy;
    public static RelativeLayout order_rela_fragment;
    private int tag;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //加载布局文件
        vv = inflater.inflate(R.layout.order_fragment,null);
        return vv;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //数据库的管理类
        dao = new sqlDao(getActivity());
        //初始化布局控件
        initView();
        //拿到传过来的值
        tag = Integer.parseInt(getArguments().getString("tag"));
        //设置数据适配器
        setMyAdapter();
    }

    private void setMyAdapter() {
        //查询数据库的方法
        ArrayList<SqlBean> list=dao.selectSql();
        //挑选出待付款的商品
        ArrayList<SqlBean> waitPay=new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).getTag()==tag){
                waitPay.add(list.get(i));
            }
        }
        Log.i("setMyAdapter", "setMyAdapter: ......"+list.size());
        //设置排列方式LinearLayoutManager.VERTICAL  竖直  两列
        order_recy.setLayoutManager(new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false));
        //设置数据适配器
        order_recy.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));


        if(waitPay!=null&&waitPay.size()>0){
            order_recy.setAdapter(new MyOrderAdapter(list,getActivity()));
        }else{
            order_sprview.setVisibility(View.GONE);
            order_rela_fragment.setVisibility(View.VISIBLE);
        }
    }

    private void initView() {
        //没有数据时要显示的图片
        order_rela_fragment = (RelativeLayout) vv.findViewById(R.id.order_rela_fragment);
        order_sprview = (SpringView) vv.findViewById(R.id.order_sprview);
        order_sprview.setEnable(true);
        order_sprview.setType(SpringView.Type.FOLLOW);
        order_recy = (RecyclerView) vv.findViewById(R.id.order_recy);
        //spr设置头和脚
        order_sprview.setFooter(new DefaultFooter(getActivity()));
        order_sprview.setHeader(new DefaultHeader(getActivity()));
        //设置刷新的方法
        order_sprview.setListener(this);
    }

    //对外提供的方法
    public static Fragment getInstance(int tag){
        MyOrderFragment fragment=new MyOrderFragment();
        Bundle bundler=new Bundle();
        bundler.putString("tag",tag+"");
        fragment.setArguments(bundler);
        return fragment;
    }

    @Override
    public void onRefresh() {
        //停止刷新及加载
        order_sprview.onFinishFreshAndLoad();
    }

    @Override
    public void onLoadmore() {
        //停止刷新及加载
        order_sprview.onFinishFreshAndLoad();
    }

}
