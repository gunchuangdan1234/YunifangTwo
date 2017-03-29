package com.bawei.yunifang.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bawei.yunifang.R;
import com.bawei.yunifang.adapter.MyOrderAdapter;

/**
 * Created by Pooh on 2016/12/26.
 */

public class MyOrderViewHolder extends RecyclerView.ViewHolder{

    public static ImageView my_order_img;
    public static TextView order_tv_name;
    public static TextView order_tv_price;
    public static TextView order_tv_number;
    public static TextView order_recyitem1_tv;
    public static TextView order_item2_tv1;
    public static TextView order_item2_tv2;
    public static TextView order_item2_but1;
    public static TextView order_item2_but2;

    public MyOrderViewHolder(View itemView,int tag) {
        super(itemView);
        if(tag!= MyOrderAdapter.last){
            my_order_img = (ImageView) itemView.findViewById(R.id.my_order_img22);
            order_tv_name = (TextView) itemView.findViewById(R.id.order_tv_name);
            order_tv_price = (TextView) itemView.findViewById(R.id.order_tv_price);
            order_tv_number = (TextView) itemView.findViewById(R.id.order_tv_number);
            order_recyitem1_tv = (TextView) itemView.findViewById(R.id.order_recyitem1_tv);
         }else{
            order_item2_tv1 = (TextView) itemView.findViewById(R.id.order_item2_tv1);
            order_item2_tv2 = (TextView) itemView.findViewById(R.id.order_item2_tv2);
            order_item2_but1 = (TextView) itemView.findViewById(R.id.order_item2_but1);
            order_item2_but2 = (TextView) itemView.findViewById(R.id.order_item2_but2);
        }
    }
}
