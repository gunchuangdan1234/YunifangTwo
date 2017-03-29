package com.bawei.yunifang.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import com.bawei.yunifang.R;
import com.bawei.yunifang.bean.MoreGoods;
import com.bawei.yunifang.interface1.OnItemClickListener;
import com.bawei.yunifang.viewholder.MoreGoodsViewHolder;

/**
 * Created by Pooh on 2016/12/7.
 */
public class MoreGoodsReceAdapter extends RecyclerView.Adapter<MoreGoodsViewHolder>{

    private final Context context;
    private final ArrayList<MoreGoods.DataBean> list;
    private OnItemClickListener onItmeClickListener;

    public MoreGoodsReceAdapter(Context context, ArrayList<MoreGoods.DataBean> list) {
        this.list=list;
        this.context=context;
    }

    @Override
    public MoreGoodsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //查找布局
        View vv=View.inflate(context, R.layout.item_more_goods_view,null);

        MoreGoodsViewHolder viewHolder=new MoreGoodsViewHolder(vv);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MoreGoodsViewHolder holder, final int position) {
        //设置数据
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItmeClickListener != null) {
                    onItmeClickListener.onItemClick(position);
                }
            }
        });
        ImageLoader.getInstance().displayImage(list.get(position).getGoods_img(),holder.fragment_category_img);
        holder.fragment_category_jieshao.setText(list.get(position).getGoods_name());
        holder.fragment_category_name.setText(list.get(position).getEfficacy());
        holder.fragment_category_price_tv1.setText("￥"+list.get(position).getShop_price());
        holder.fragment_category_price_tv2.setText("￥"+list.get(position).getMarket_price());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItmeClickListener = onItemClickListener;
    }
}
