package com.bawei.yunifang;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;

import com.bawei.yunifang.bean.SqlBean;
import com.bawei.yunifang.fragment.Cart_Fragment;
import com.bawei.yunifang.pay.PayDemoActivity;
import com.bawei.yunifang.sql.sqlDao;
import com.bawei.yunifang.view.MyGridView;


public class DingDanActivity extends AppCompatActivity implements View.OnClickListener {

    private MyGridView dingdan_mylv;
    private ArrayList<SqlBean> list_my;
    private ArrayList<SqlBean> list;
    private sqlDao dao;
    private CommonAdapter commonAdapter;
    private TextView dingdan_lv_jia;
    private TextView dingdan_lv_jian;
    private TextView xianshiquanbu;
    private RelativeLayout dingdan_rela_address;
    private TextView dingdan_hide_tv;
    private LinearLayout dingdan_show_lin;
    private TextView dingdan_name;
    private TextView dingda_phone;
    private TextView dingda_address;
    private TextView dingdan_pay_tv;
    private CheckBox dingdan_zhifubao_check;
    private CheckBox dingdan_wx_check;
    private TextView dingdan_count_tv;
    private float countPrice=0;
    private ArrayList<SqlBean> sqlBeen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ding_dan);
        //初始化控件
        initView();
        //查询数据库
        selectSql();
        //控件赋值
        setData();
        //接收传来的值并且赋值
        getArgs();
    }

    private void getArgs() {
        Intent inn=getIntent();
        String name=inn.getStringExtra("name");
        String phone=inn.getStringExtra("phone");
        String address=inn.getStringExtra("address");
        if(!TextUtils.isEmpty(name)){
            //赋值
            dingdan_hide_tv.setVisibility(View.GONE);
            dingdan_show_lin.setVisibility(View.VISIBLE);
            dingdan_name.setText(name);
            dingda_phone.setText(phone);
            dingda_address.setText(address);
        }
    }

    private void setData() {
        //lv设置适配器
        //设置唯一的标识
//赋值
        commonAdapter = new CommonAdapter<SqlBean>(DingDanActivity.this, R.layout.dingdan_lv_view,list) {

            @Override
            protected void convert(ViewHolder viewHolder, SqlBean item, int position) {
                ImageView dingdan_lv_img=viewHolder.getView(R.id.dingdan_lv_img);
                TextView dingdan_lv_name=viewHolder.getView(R.id.dingdan_lv_name);
                TextView dingdan_lv_price=viewHolder.getView(R.id.dingdan_lv_price);
                TextView dingdan_lv_num=viewHolder.getView(R.id.dingdan_lv_num);
                dingdan_lv_jia = viewHolder.getView(R.id.dingdan_lv_jia);
                dingdan_lv_jian = viewHolder.getView(R.id.dingdan_lv_jian);
                //设置唯一的标识
                dingdan_lv_jia.setTag(position);
                dingdan_lv_jian.setTag(position);
                dingdan_lv_jia.setOnClickListener(DingDanActivity.this);
                dingdan_lv_jian.setOnClickListener(DingDanActivity.this);

                if(Integer.parseInt(list.get(position).getNum()+"")>1){
                    dingdan_lv_jian.setBackgroundResource(R.mipmap.heijian);
                }else{
                    dingdan_lv_jian.setBackgroundResource(R.mipmap.huijian);
                }

                //赋值
                ImageLoader.getInstance().displayImage(list.get(position).getImgUrl(),dingdan_lv_img);
                dingdan_lv_name.setText(list.get(position).getName());
                dingdan_lv_price.setText("￥"+list.get(position).getPrice());
                dingdan_lv_num.setText(list.get(position).getNum()+"");
            }
        };
        dingdan_mylv.setAdapter(commonAdapter);

        //计算出总价格
        if(list!=null&&list.size()>0){
            for (int i = 0; i <list.size() ; i++) {
                countPrice=countPrice+Float.parseFloat(list.get(i).getPrice());
            }
        }
        //总价格赋值
        dingdan_count_tv.setText("总价：￥"+countPrice);
    }

    private void selectSql() {
        //得到数据库的管理类
        dao = new sqlDao(DingDanActivity.this);
        list_my=new ArrayList<>();
        ArrayList<SqlBean> list_ying=dao.selectSql();
        for (int i = 0; i < list_ying.size(); i++) {
            if(list_ying.get(i).getTag()==1){
                list_my.add(list_ying.get(i));
            }
        }
            list = new ArrayList<>();
        if(list_my.size()>2){
            list.add(list_my.get(0));
            list.add(list_my.get(1));
        }else{
            for (int i = 0; i < list_my.size(); i++) {
                list.add(list_my.get(i));
            }
        }
        Toast.makeText(this, "...."+list.size(), Toast.LENGTH_SHORT).show();
    }

    private void initView() {
        dingdan_mylv = (MyGridView) findViewById(R.id.dingdan_mylv);
        xianshiquanbu = (TextView) findViewById(R.id.xianshiquanbu);
        dingdan_hide_tv = (TextView) findViewById(R.id.dingda_hide_tv);
        dingdan_show_lin = (LinearLayout) findViewById(R.id.dingda_show_lin);
        dingdan_name = (TextView) findViewById(R.id.dingda_name);
        dingda_phone = (TextView) findViewById(R.id.dingda_phone);
        dingda_address = (TextView) findViewById(R.id.dingda_address);
        dingdan_pay_tv = (TextView) findViewById(R.id.dingdan_pay_tv);
        dingdan_zhifubao_check = (CheckBox) findViewById(R.id.dingdan_zhifubao_check);
        dingdan_zhifubao_check.setChecked(true);
        dingdan_wx_check = (CheckBox) findViewById(R.id.dingdan_wx_check);
        dingdan_count_tv = (TextView) findViewById(R.id.dingdan_count_tv);
        //设置监听
        dingdan_zhifubao_check.setOnClickListener(this);
        dingdan_wx_check.setOnClickListener(this);
        dingdan_pay_tv.setOnClickListener(this);
        xianshiquanbu.setOnClickListener(this);
        dingdan_rela_address = (RelativeLayout) findViewById(R.id.dingdan_rela_address);
        dingdan_rela_address.setOnClickListener(this);
    }
    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dingdan_lv_jia:
                int num=Integer.parseInt(list.get(Integer.parseInt(v.getTag()+"")).getNum());
                num++;
                list.get(Integer.parseInt(v.getTag()+"")).setNum(num+"");
                commonAdapter.notifyDataSetChanged();
                break;
            case R.id.dingdan_lv_jian:
                int num2=Integer.parseInt(list.get(Integer.parseInt(v.getTag()+"")).getNum());
                if(num2>1){
                    num2--;
                    list.get(Integer.parseInt(v.getTag()+"")).setNum(num2+"");
                    commonAdapter.notifyDataSetChanged();
                }
                break;
            case R.id.xianshiquanbu:
                list=list_my;
                Log.i("kkkkkkkk", "onClick: ......"+list.size());
                setData();
                xianshiquanbu.setVisibility(View.GONE);
                break;
            case R.id.dingdan_rela_address:
                startActivity(new Intent(DingDanActivity.this,AddressActivity.class));
                finish();
                break;
            //付款的界面
            case R.id.dingdan_pay_tv:
                //跳转到支付界面
                Intent inn=new Intent(DingDanActivity.this, PayDemoActivity.class);
                inn.putExtra("name",list.get(0).getName());
                inn.putExtra("dec","镇店之宝，面膜精选！");
                startActivity(inn);

                Toast.makeText(this, "..dingdan_pay_tv...", Toast.LENGTH_SHORT).show();


                //查询数据库
                sqlBeen = dao.selectSql();
                for (int i = 0; i < sqlBeen.size(); i++) {
                    //将当前状态值改变为待付款状态，将商品从购物成为中移除
                    if(sqlBeen.get(i).getTag()==1){
                        dao.updateSqlTag(sqlBeen.get(i).getName(),3);
                    }
                    //刷新适配器
                    Cart_Fragment.Refrush();
                }

                break;
            case R.id.dingdan_wx_check:
                dingdan_wx_check.setChecked(false);
                Toast.makeText(this, "微信支付正在维护，敬请见谅！", Toast.LENGTH_SHORT).show();
                break;
            case R.id.dingdan_zhifubao_check:
                dingdan_zhifubao_check.setChecked(true);
                break;
            default:
                break;
        }

    }

}
