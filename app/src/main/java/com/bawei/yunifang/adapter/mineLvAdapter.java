package com.bawei.yunifang.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import com.bawei.yunifang.R;
import com.bawei.yunifang.base.CommonAdapter;
import com.bawei.yunifang.base.MyViewHolder;
import com.bawei.yunifang.utils.mineList;

/**
 * Created by Pooh on 2016/11/29.
 */
public class mineLvAdapter<T> extends CommonAdapter<T>{

    private ImageView my_order_img;
    private ImageView my_order_orrow;
    private TextView my_order_tv1;
    private TextView my_order_tv2;
    private LinearLayout my_order_background;

    public mineLvAdapter(Context context, List<T> mDatas){
        super(context,mDatas);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder viewHolder=new MyViewHolder(mContext,parent, R.layout.mine_item_view,position);
        my_order_img = viewHolder.getView(R.id.my_order_img);
        my_order_orrow = viewHolder.getView(R.id.my_order_orrow);
        my_order_tv1 = viewHolder.getView(R.id.my_order_tv1);
        my_order_tv2 = viewHolder.getView(R.id.my_order_tv2);
        my_order_background = viewHolder.getView(R.id.my_order_background);
        //设置阴影部分隐藏显示
        if(position==0||position==2){
            my_order_background.setVisibility(View.VISIBLE);
        }else{
            my_order_background.setVisibility(View.GONE);
        }
        //赋值
        my_order_tv1.setText(mineList.list_mineItemBean.get(position).getName());
        my_order_img.setImageResource(mineList.list_mineItemBean.get(position).getImg());
        return viewHolder.getConvertView();
    }
}
