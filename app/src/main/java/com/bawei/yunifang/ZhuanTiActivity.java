package com.bawei.yunifang;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import com.bawei.yunifang.adapter.ZhuanTiReceAdapter;
import com.bawei.yunifang.bean.HomeBean;
import com.bawei.yunifang.divider.DividerItemDecoration;
import com.bawei.yunifang.interface1.OnItemClickListener;

public class ZhuanTiActivity extends AppCompatActivity implements OnItemClickListener {

    private ImageView zhuanti_back_img;
    private TextView zhuanti_name;
    private TextView zhuanti_detail;
    private RecyclerView zhuan_recyclerView;
    private HomeBean.DataBean.SubjectsBean subjectsBean;
    private ArrayList<HomeBean.DataBean.SubjectsBean.GoodsListBean> goodsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuan_ti);
        //初始化控件
        initView();
        //接收传来的值
        getData();
        zhuan_recyclerView.setFocusable(false);
    }

    private void getData() {
        Intent inn=getIntent();
        Bundle bundle=inn.getExtras();
        subjectsBean = (HomeBean.DataBean.SubjectsBean) bundle.getSerializable("bean");
        //赋值
        setData();
    }

    private void setData() {
        zhuanti_name.setText(subjectsBean.getTitle());
        zhuanti_detail.setText(subjectsBean.getDetail());
        //设置适配器
        //设置排列方式LinearLayoutManager.VERTICAL  竖直  两列
        zhuan_recyclerView.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false));
        //设置数据适配器
        zhuan_recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        goodsList = (ArrayList<HomeBean.DataBean.SubjectsBean.GoodsListBean>) subjectsBean.getGoodsList();
        ZhuanTiReceAdapter zhuanAdapter=new ZhuanTiReceAdapter(goodsList,ZhuanTiActivity.this);
        zhuan_recyclerView.setAdapter(zhuanAdapter);
        zhuanAdapter.setOnItemClickListener(this);
    }

    private void initView() {
        zhuanti_back_img = (ImageView) findViewById(R.id.zhuanti_back_img);
        zhuanti_name = (TextView) findViewById(R.id.zhuanti_name);
        zhuanti_detail = (TextView) findViewById(R.id.zhuanti_detail);
        zhuan_recyclerView = (RecyclerView) findViewById(R.id.zhuanti_recyclerView);
    }

    @Override
    public void onItemClick(int position) {
        //Toast.makeText(ZhuanTiActivity.this,"......"+position,Toast.LENGTH_SHORT).show();
        Intent inn=new Intent(ZhuanTiActivity.this,DetailActivity.class);
        inn.putExtra("id",goodsList.get(position).getId());
        startActivity(inn);
    }

    @Override
    public void onItemLongClick(int position) {

    }
}
