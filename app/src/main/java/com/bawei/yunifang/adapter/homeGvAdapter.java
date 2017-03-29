package com.bawei.yunifang.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import com.bawei.yunifang.R;
import com.bawei.yunifang.base.CommonAdapter;
import com.bawei.yunifang.base.MyViewHolder;
import com.bawei.yunifang.bean.HomeBean;
import com.bawei.yunifang.fragment.Home_Fragment;
import com.bawei.yunifang.utils.ImageLoaderUtils;

/**
 * Created by Pooh on 2016/12/1.
 */
public class homeGvAdapter<T> extends CommonAdapter<T>{

    private View home_gv_item;

    public homeGvAdapter(Context context, List<T> mDatas) {
        super(context, mDatas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder viewHolder=new MyViewHolder(mContext,parent,R.layout.home_gv_item,position);
        ImageView home_gv_item_img=viewHolder.getView(R.id.home_gv_item_img);
        TextView home_gv_item_tv=viewHolder.getView(R.id.home_gv_item_tv);
        HomeBean homeBean=Home_Fragment.homeBean;
        ImageLoader.getInstance().displayImage(homeBean.getData().getAd5().get(position).getImage(),home_gv_item_img, ImageLoaderUtils.initOptions());
        home_gv_item_tv.setText(homeBean.getData().getAd5().get(position).getTitle());
        return viewHolder.getConvertView();
    }
}
