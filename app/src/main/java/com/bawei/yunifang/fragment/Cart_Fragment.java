package com.bawei.yunifang.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;

import com.bawei.yunifang.DingDanActivity;
import com.bawei.yunifang.R;
import com.bawei.yunifang.application.MyApplication;
import com.bawei.yunifang.base.BaseFragment;
import com.bawei.yunifang.bean.SqlBean;
import com.bawei.yunifang.sql.sqlDao;
import com.bawei.yunifang.view.ShowingPage;
import com.bawei.yunifang.view.XListView;

/**
 * Created by Pooh on 2016/11/28.
 */
public class Cart_Fragment extends BaseFragment implements View.OnClickListener{
    private String s;
    private XListView xlv;
    private static sqlDao dao;
    private static ArrayList<SqlBean> list;
    private static CommonAdapter adapter;
    private static CheckBox sopping_quanxuan;
    private float price=0;
    private TextView sopping_zhongjia;
    private Button jiesuan_but;

    @Override
    protected void onload() {
        //设置布局
        Cart_Fragment.this.showCurrentPage(ShowingPage.StateType.STATE_LOAD_SUCCESS);
    }

    @Override
    protected View createSuccessView() {
        View vv = View.inflate(getActivity(), R.layout.activity_sopping,null);
        //找空间
        xlv = (XListView) vv.findViewById(R.id.sopping_xlv);
        sopping_quanxuan = (CheckBox) vv.findViewById(R.id.sopping_quanxuan);
        sopping_quanxuan.setOnClickListener(this);
        sopping_zhongjia = (TextView) vv.findViewById(R.id.sopping_zhongjia);
        //结算的按钮
        jiesuan_but = (Button) vv.findViewById(R.id.jiesuan_but);
        jiesuan_but.setOnClickListener(this);
        getData();
        return vv;
    }

    private void getData() {
        dao = new sqlDao(getActivity());
        list = dao.selectSql();
        adapter = new CommonAdapter<SqlBean>(getActivity(), R.layout.shopping_xlv_view, list) {
            @Override
            protected void convert(ViewHolder viewHolder, SqlBean item, int position) {
                CheckBox sopping_xlv_check=viewHolder.getView(R.id.sopping_xlv_check);
                sopping_xlv_check.setTag(position);
                ImageView sopping_xlv_img=viewHolder.getView(R.id.sopping_xlv_img);
                TextView shopping_xlv_name_tv=viewHolder.getView(R.id.shopping_xlv_name_tv);
                TextView shopping_shopprice_tv=viewHolder.getView(R.id.shopping_shopprice_tv);
                TextView shopping_xlv_num=viewHolder.getView(R.id.shopping_xlv_num);

                ImageLoader.getInstance().displayImage(item.getImgUrl(),sopping_xlv_img);
                if(item.getName().length()>17){
                    shopping_xlv_name_tv.setText(item.getName().substring(0,17)+"...");
                }else {
                    shopping_xlv_name_tv.setText(item.getName());
                }
                shopping_shopprice_tv.setText("￥"+item.getPrice());
                shopping_xlv_num.setText("数量:"+item.getNum());
                sopping_xlv_check.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        boolean ff=false;
                        //Toast.makeText(mContext, "..."+v.getTag(), Toast.LENGTH_SHORT).show();
                        int tag= (int) v.getTag();
                        list.get(tag).setFlag(!list.get(tag).isFlag());
                        adapter.notifyDataSetChanged();
                        //判断全选的复选框是否被选上
                        for (int i = 0; i < list.size(); i++) {
                            if(!list.get(i).isFlag()){
                                ff=true;
                                MyApplication.quanxuan=true;
                            }
                        }
                        if(!ff){
                            MyApplication.quanxuan=false;
                        }
                        sopping_quanxuan.setChecked(!ff);
                        //总价的计算
                        //将总价归零
                        price=0;
                        for (int i = 0; i < list.size(); i++) {
                            if(list.get(i).isFlag()){
                                price=price+Float.parseFloat(list.get(i).getPrice())*Integer.parseInt(list.get(i).getNum());
                                //将数据库中的标记选中的值为1
                                dao.updateSqlTag(list.get(i).getName(),1);
                            }else{
                                //将数据库中的标记选中的值为0
                                dao.updateSqlTag(list.get(i).getName(),0);
                            }
                        }
                        //总价赋值
                        sopping_zhongjia.setText("总价：￥"+price);
                    }
                });
                sopping_xlv_check.setChecked(list.get(position).isFlag());
            }
        };
        xlv.setAdapter(adapter);
    }
    //刷新适配器的方法
    public static void Refrush(){
        list=dao.selectSql();
        Log.i("kkkkkk", "Refrush: ........"+list.size());
        adapter.notifyDataSetChanged();
        sopping_quanxuan.setChecked(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sopping_quanxuan:
                if(MyApplication.quanxuan){
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).setFlag(true);
                        price=price+Float.parseFloat(list.get(i).getPrice())*Integer.parseInt(list.get(i).getNum());
                        //将数据库中的标记选中的值为1
                        dao.updateSqlTag(list.get(i).getName(),1);
                    }
                }else{
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).setFlag(false);
                        price=0;
                        //将数据库中的标记选中的值为0
                        dao.updateSqlTag(list.get(i).getName(),0);
                    }
                }
                MyApplication.quanxuan=!MyApplication.quanxuan;
                adapter.notifyDataSetChanged();
                //总价赋值
                sopping_zhongjia.setText("总价：￥"+price);
                break;
            case R.id.jiesuan_but:
                boolean ff=false;
                //跳转到订单界面
                ArrayList<SqlBean> bb=dao.selectSql();
                for (int i = 0; i < bb.size(); i++) {
                    if(list.get(i).getTag()==1){
                       ff=true;
                    }
                }
                if(ff&&price!=0.0){
                    Intent inn=new Intent(getActivity(), DingDanActivity.class);
                    inn.putExtra("countPrice",price);
                    startActivity(inn);
                }else{
                    Toast.makeText(getActivity(),"请选择商品...", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(getActivity(), "结算按钮", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
