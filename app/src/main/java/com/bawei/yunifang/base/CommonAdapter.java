package com.bawei.yunifang.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Pooh on 2016/11/29.
 */
public abstract class CommonAdapter<T> extends BaseAdapter{
    protected LayoutInflater mInflater;
    protected Context mContext;
    protected List<T> mDatas;

    public CommonAdapter(Context context, List<T> mDatas)
    {
        mInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.mDatas = mDatas;
    }
    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
