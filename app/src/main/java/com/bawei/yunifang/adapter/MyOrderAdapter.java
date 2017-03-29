package com.bawei.yunifang.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import com.bawei.yunifang.MyOrderActivity;
import com.bawei.yunifang.R;
import com.bawei.yunifang.bean.SqlBean;
import com.bawei.yunifang.fragment.MyOrderFragment;
import com.bawei.yunifang.pay.PayDemoActivity;
import com.bawei.yunifang.sql.sqlDao;
import com.bawei.yunifang.utils.CommonUtils;
import com.bawei.yunifang.viewholder.MyOrderViewHolder;

import static java.lang.Float.parseFloat;

/**
 * Created by Pooh on 2016/12/26.
 */

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderViewHolder> implements View.OnClickListener {
    ArrayList<SqlBean> list;
    Context context;
    //最后一条数据
    public static int last=1;
    //不是最后一条数据
    public static int next=0;
    private View myViewHolder;
    private PopupWindow popupWindow;

    public MyOrderAdapter(ArrayList<SqlBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyOrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType!=last){
            myViewHolder = View.inflate(context, R.layout.order_recy_item1,null);
        }else{
            myViewHolder = View.inflate(context, R.layout.order_recy_item2,null);
        }
        MyOrderViewHolder myOrderViewHolder=new MyOrderViewHolder(myViewHolder,viewType);
        return myOrderViewHolder;
    }

    @Override
    public void onBindViewHolder(MyOrderViewHolder holder, int position) {
        //if(getItemViewType(position)!=last){
        if(position!=list.size()){

            ImageLoader.getInstance().displayImage(list.get(position).getImgUrl(),holder.my_order_img);
            holder.order_tv_name.setText(list.get(position).getName());
            holder.order_tv_number.setText("购买数量："+list.get(position).getNum());
            holder.order_tv_price.setText("价格："+list.get(position).getPrice());

        if(position!=0){
            holder.order_recyitem1_tv.setVisibility(View.GONE);
        }
        }else{
            float price=0;
            //计算出所有商品的价格
            for (int i = 0; i < list.size(); i++) {
                float pp=parseFloat(list.get(i).getPrice())*Integer.parseInt(list.get(i).getNum());
                price=price+pp;
            }
            holder.order_item2_tv1.setText("总价：￥"+price);
            holder.order_item2_tv2.setText("数量："+list.size());
            holder.order_item2_but1.setOnClickListener(this);
            holder.order_item2_but2.setOnClickListener(this);
        }
       // }
    }


    @Override
    public int getItemCount() {
        return list.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if(position==list.size()){
            return last;
        }
        return next;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.order_item2_but1:
                bottomwindow(v);
                getBack();
                break;
            //跳转到支付界面
            case R.id.order_item2_but2:
                context.startActivity(new Intent(context, PayDemoActivity.class));
                break;
            //订单的popwindow控件布局
            case R.id.quxiaodingdan_tv2:
                popupWindow.dismiss();
                break;
            case R.id.querendingdan_tv1:
                //将所有的订单删除
                sqlDao dao=new sqlDao(context);
                for (int i = 0; i < list.size(); i++) {
                    if(list.get(i).getTag()==3){
                        dao.deleteSql(list.get(i).getName());
                        list.remove(i);
                    }
                }

                //再次查询数据库
                boolean bb=false;
                ArrayList<SqlBean> list=dao.selectSql();
                if(list!=null&&list.size()>0){
                    for (int i = 0; i < list.size(); i++) {
                        if(list.get(i).getTag()==3){
                            //刷新适配器
                            bb=true;
                        }
                    }
                }
                this.notifyDataSetChanged();
                if(!bb){
                    //显示图片，隐藏recyclerView
                    MyOrderFragment.order_sprview.setVisibility(View.GONE);
                    MyOrderFragment.order_rela_fragment.setVisibility(View.VISIBLE);
                }

                break;
            default:
                break;
        }
    }


    //弹出pop
    public void bottomwindow(View v){
        if (popupWindow != null && popupWindow.isShowing()) {
            return;
        }
        //LinearLayout layout2 = (LinearLayout) mContext.getLayoutInflater().inflate(R.layout.window_popup, null);
        LinearLayout layout= (LinearLayout) CommonUtils.inflate(R.layout.quxiaodingdan_popwindow);
        //获取popwindow的中的控件
        getPopControl(layout);
        popupWindow = new PopupWindow(layout,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        //点击空白处时，隐藏掉pop窗口
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //添加弹出、弹入的动画
        popupWindow.setAnimationStyle(R.style.Popupwindow);
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        //popupWindow.showAtLocation(v, Gravity.LEFT | Gravity.BOTTOM, 0, -location[1]);
        popupWindow.showAtLocation(v, Gravity.CENTER,0,0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                BackBackground();
            }
        });
    }
    //popwindow消失，背景颜色逐渐变亮
    private void BackBackground() {
        new Thread(new Runnable(){
            @Override
            public void run() {
                //此处while的条件alpha不能<= 否则会出现黑屏
                while(alpha<0.999f){
                    try {
                        Thread.sleep(4);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.d("HeadPortrait","alpha:"+alpha);
                    Message msg =handler.obtainMessage();
                    msg.what = 1;
                    alpha+=0.01f;
                    msg.obj =alpha ;
                    handler.sendMessage(msg);
                }
            }

        }).start();
    }
    //获取控件的方法
    private void getPopControl(LinearLayout layout) {
        TextView querendingdan_tv1= (TextView) layout.findViewById(R.id.querendingdan_tv1);
        TextView quxiaodingdan_tv2= (TextView) layout.findViewById(R.id.quxiaodingdan_tv2);
        //添加监听
        querendingdan_tv1.setOnClickListener(this);
        quxiaodingdan_tv2.setOnClickListener(this);
    }


    //点击按钮时背景颜色逐渐变暗
    private float alpha=1.0f;
    private void getBack() {
        new Thread(new Runnable(){
            @Override
            public void run() {
                while(alpha>0.5f){
                    try {
                        //4是根据弹出动画时间和减少的透明度计算
                        Thread.sleep(4);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Message msg =handler.obtainMessage();
                    msg.what = 1;
                    //每次减少0.01，精度越高，变暗的效果越流畅
                    alpha-=0.01f;
                    msg.obj =alpha ;
                    handler.sendMessage(msg);
                }
            }

        }).start();
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            backgroundAlpha((float) msg.obj);
        }
    };

    public void backgroundAlpha(float bgAlpha)
    {
        WindowManager.LayoutParams lp = MyOrderActivity.window.getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0n
        MyOrderActivity.window.setAttributes(lp);
        MyOrderActivity.window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }
}
