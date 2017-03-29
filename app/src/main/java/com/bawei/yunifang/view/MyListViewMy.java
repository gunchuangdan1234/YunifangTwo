package com.bawei.yunifang.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by Pooh on 2016/12/18.
 */
public class MyListViewMy extends ListView{
    public MyListViewMy(Context context) {
        super(context);
    }

    public MyListViewMy(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyListViewMy(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
