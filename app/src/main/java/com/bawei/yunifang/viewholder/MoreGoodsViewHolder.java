package com.bawei.yunifang.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bawei.yunifang.R;

/**
 * Created by Pooh on 2016/12/7.
 */
public class MoreGoodsViewHolder extends RecyclerView.ViewHolder{

    public final ImageView fragment_category_img;
    public final TextView fragment_category_name;
    public final TextView fragment_category_jieshao;
    public final TextView fragment_category_price_tv1;
    public final TextView fragment_category_price_tv2;

    public MoreGoodsViewHolder(View itemView) {
        super(itemView);
        //找控件
        fragment_category_img = (ImageView) itemView.findViewById(R.id.fragment_more_goods_img);
        fragment_category_name = (TextView) itemView.findViewById(R.id.fragment_more_goods_name);
        fragment_category_jieshao = (TextView) itemView.findViewById(R.id.fragment_more_goods_jieshao);
        fragment_category_price_tv1 = (TextView) itemView.findViewById(R.id.fragment_more_goods_price_tv1);
        fragment_category_price_tv2 = (TextView) itemView.findViewById(R.id.fragment_more_goods_price_tv2);
    }
}
