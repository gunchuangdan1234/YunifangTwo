package com.bawei.yunifang.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bawei.yunifang.R;

/**
 * Created by Pooh on 2016/12/26.
 */

public class MyViewHolder2 extends RecyclerView.ViewHolder{

    public static TextView order_item2_tv1;
    public static TextView order_item2_tv2;
    public static TextView order_item2_but1;
    public static TextView order_item2_but2;

    public MyViewHolder2(View itemView) {
        super(itemView);
        order_item2_tv1 = (TextView) itemView.findViewById(R.id.order_item2_tv1);
        order_item2_tv2 = (TextView) itemView.findViewById(R.id.order_item2_tv2);
        order_item2_but1 = (TextView) itemView.findViewById(R.id.order_item2_but1);
        order_item2_but2 = (TextView) itemView.findViewById(R.id.order_item2_but2);
    }
}
