package com.bawei.yunifang;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.liaoinstan.springview.widget.SpringView;

import java.util.ArrayList;

import com.bawei.yunifang.adapter.MoreGoodsReceAdapter;
import com.bawei.yunifang.base.BaseDate;
import com.bawei.yunifang.bean.MoreGoods;
import com.bawei.yunifang.divider.DividerItemDecoration;
import com.bawei.yunifang.interface1.OnItemClickListener;
import com.bawei.yunifang.utils.URLUtils;
import com.bawei.yunifang.view.ShowingPage;

public class MoreGoodsActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recyclerView;
    private SpringView more_goods_spr;
    private String data;
    private MoreGoods moreGoods;
    private ArrayList<MoreGoods.DataBean> list_moreGoods;
    private ImageView more_goods_back_img;
    private TextView more_goods_noemal_pai_tv;
    private TextView more_goods_sheng_pai_tv;
    private TextView more_goods_jiang_pai_tv;
    private TextView more_goods_backgound1;
    private TextView more_goods_backgound2;
    private TextView more_goods_backgound3;
    private ArrayList<MoreGoods.DataBean> listSortMy;
    private MoreGoodsReceAdapter moreGoodsReceAdapter;

    //判断条目点击事件时要调用哪一个集合
    public static boolean listFlag=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_goods);
        //初始化控件
        initView();
        //请求网络数据
        getWww();

    }

    private void getWww() {
        new BaseDate() {
            @Override
            protected void setResultError(ShowingPage.StateType stateLoadError) {

            }

            @Override
            public void setResultData(String data) {
                MoreGoodsActivity.this.data = data;
                //rece数据适配
                initDateRece();
            }
        }.getDate(URLUtils.moreGoodsUrl, URLUtils.moreGoodsUrlArgs, 0, BaseDate.NOMALTIME);
    }

    private void initDateRece() {
        //解析数据
        getData();
        //设置布局管理器，制定管理器类型以及排列方式
        //设置排列方式LinearLayoutManager.VERTICAL  竖直  两列
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false));
        //设置数据适配器
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        moreGoodsReceAdapter = new MoreGoodsReceAdapter(MoreGoodsActivity.this, list_moreGoods);
        recyclerView.setAdapter(moreGoodsReceAdapter);
        MyAdapterLitenner();

    }

    private void MyAdapterLitenner() {
        //设置监听
        moreGoodsReceAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                    Intent inn=new Intent(MoreGoodsActivity.this,DetailActivity.class);
                    if(listFlag){
                        inn.putExtra("id",listSortMy.get(position).getId());
                    }else{
                        inn.putExtra("id",list_moreGoods.get(position).getId());
                    }
                    startActivity(inn);
            }

            @Override
            public void onItemLongClick(int position) {

            }
        });
    }

    private void getData() {
        Gson gson = new Gson();
        moreGoods = gson.fromJson(data, MoreGoods.class);
        list_moreGoods = new ArrayList<>();
        for (int i = 0; i < moreGoods.getData().size(); i++) {
            list_moreGoods.add(moreGoods.getData().get(i));
        }
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.more_goods_recy);
        more_goods_spr = (SpringView) findViewById(R.id.more_goods_spring);
        //返回按钮的img
        more_goods_back_img = (ImageView) findViewById(R.id.more_goods_back_img);
        more_goods_back_img.setOnClickListener(this);
        //默认排序
        more_goods_noemal_pai_tv = (TextView) findViewById(R.id.more_goods_noemal_pai_tv);
        more_goods_backgound1 = (TextView) findViewById(R.id.more_goods_backgound1);
        more_goods_noemal_pai_tv.setOnClickListener(this);
        //升序排序
        more_goods_sheng_pai_tv = (TextView) findViewById(R.id.more_goods_sheng_pai_tv);
        more_goods_backgound2 = (TextView) findViewById(R.id.more_goods_backgound2);
        more_goods_sheng_pai_tv.setOnClickListener(this);
        //降序排序
        more_goods_jiang_pai_tv = (TextView) findViewById(R.id.more_goods_jiang_pai_tv);
        more_goods_backgound3 = (TextView) findViewById(R.id.more_goods_backgound3);
        more_goods_jiang_pai_tv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.more_goods_back_img:
                finish();
                break;
            case R.id.more_goods_noemal_pai_tv:
                //改变字体颜色
                chageTvColor(more_goods_noemal_pai_tv, more_goods_sheng_pai_tv, more_goods_jiang_pai_tv);
                chageTvColor2(more_goods_backgound1, more_goods_backgound2, more_goods_backgound3);
                //重新设置适配器
                moreGoodsReceAdapter = new MoreGoodsReceAdapter(MoreGoodsActivity.this, list_moreGoods);
                recyclerView.setAdapter(moreGoodsReceAdapter);
                //要用哪一个集合
                listFlag=false;
                //设置监听的方法
                MyAdapterLitenner();
                break;
            case R.id.more_goods_sheng_pai_tv:
                chageTvColor(more_goods_sheng_pai_tv, more_goods_noemal_pai_tv, more_goods_jiang_pai_tv);
                chageTvColor2(more_goods_backgound2, more_goods_backgound1, more_goods_backgound3);
                listSort(0);
                moreGoodsReceAdapter = new MoreGoodsReceAdapter(MoreGoodsActivity.this, listSortMy);
                recyclerView.setAdapter(moreGoodsReceAdapter);
                listFlag=true;
                //设置监听的方法
                MyAdapterLitenner();
                break;
            case R.id.more_goods_jiang_pai_tv:
                chageTvColor(more_goods_jiang_pai_tv, more_goods_sheng_pai_tv, more_goods_noemal_pai_tv);
                chageTvColor2(more_goods_backgound3, more_goods_backgound2, more_goods_backgound1);
                listSort(1);
                moreGoodsReceAdapter = new MoreGoodsReceAdapter(MoreGoodsActivity.this, listSortMy);
                recyclerView.setAdapter(moreGoodsReceAdapter);
                listFlag=true;
                //设置监听的方法
                MyAdapterLitenner();
                break;
        }
    }

    //设置tv的字体颜色
    public void chageTvColor(TextView tv1, TextView tv2, TextView tv3) {
        //将第一个变为红色
        tv1.setTextColor(Color.parseColor("#FC7791"));
        tv2.setTextColor(Color.parseColor("#535353"));
        tv3.setTextColor(Color.parseColor("#535353"));
    }

    public void chageTvColor2(TextView tv1, TextView tv2, TextView tv3) {
        //将第一个变为红色
        tv1.setBackgroundColor(Color.parseColor("#FC7791"));
        tv2.setBackgroundColor(Color.parseColor("#535353"));
        tv3.setBackgroundColor(Color.parseColor("#535353"));
    }

    //集合排序
    public void listSort(int sort) {
        listSortMy = new ArrayList<>();
        listSortMy.addAll(list_moreGoods);
        if(sort==0){
            for (int i = 0; i < listSortMy.size(); i++) {
                for (int j = i + 1; j < listSortMy.size(); j++) {
                    if (listSortMy.get(i).getShop_price() > listSortMy.get(j).getShop_price()) {
                        MoreGoods.DataBean temp = listSortMy.get(i);
                        listSortMy.set(i, listSortMy.get(j));
                        listSortMy.set(j, temp);
                    }
                }
            }
        }else {
            for (int i = 0; i < listSortMy.size(); i++) {
                for (int j = i + 1; j < listSortMy.size(); j++) {
                    if (listSortMy.get(i).getShop_price() < listSortMy.get(j).getShop_price()) {
                        MoreGoods.DataBean temp = listSortMy.get(i);
                        listSortMy.set(i, listSortMy.get(j));
                        listSortMy.set(j, temp);
                    }
                }
            }
        }
    }
}
