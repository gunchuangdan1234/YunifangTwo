package com.bawei.yunifang.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bawei.yunifang.R;

/**
 * Created by Pooh on 2016/12/7.
 */
public class ZhuanTiViewHolder extends RecyclerView.ViewHolder{


    public ImageView fragment_more_goods_img;
    public TextView fragment_more_goods_name;
    public TextView fragment_more_goods_jieshao;
    public TextView fragment_more_goods_price_tv1;
    public TextView fragment_more_goods_price_tv2;

    public ZhuanTiViewHolder(View itemView) {
        super(itemView);
        //找控件
        fragment_more_goods_img = (ImageView) itemView.findViewById(R.id.fragment_more_goods_img);
        fragment_more_goods_name = (TextView) itemView.findViewById(R.id.fragment_more_goods_name);
        fragment_more_goods_jieshao = (TextView) itemView.findViewById(R.id.fragment_more_goods_jieshao);
        fragment_more_goods_price_tv1 = (TextView) itemView.findViewById(R.id.fragment_more_goods_price_tv1);
        fragment_more_goods_price_tv2 = (TextView) itemView.findViewById(R.id.fragment_more_goods_price_tv2);
    }
}
