package com.bawei.yunifang;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.zhy.autolayout.AutoLayoutActivity;

import com.bawei.yunifang.factory.FragmentFactory;
import com.bawei.yunifang.view.NoScrollViewPager;

public class MainActivity extends AutoLayoutActivity {

    private NoScrollViewPager noScrollViewPager;
    private RadioGroup main_vg;
    private RadioButton radioButton;
    private RadioButton rb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        //初始化控件
        initView();
    }

    private void initView() {
        noScrollViewPager = (NoScrollViewPager) findViewById(R.id.noscroll_vp);
        main_vg = (RadioGroup) findViewById(R.id.main_vg);
        rb = (RadioButton) findViewById(R.id.rg_rb1);
        //设置适配器
        vp_adapter();
        //设置监听
        rg_onclick();
        //缓存为加载三爷
        noScrollViewPager.setOffscreenPageLimit(3);
    }
    private void rg_onclick() {
        //监听事件
        main_vg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //给radiobutton的字体赋值
                toTextColor(group, checkedId);
                switch (checkedId) {
                    case R.id.rg_rb1:
                        noScrollViewPager.setCurrentItem(0);
                    break;
                    case R.id.rg_rb2:
                        noScrollViewPager.setCurrentItem(1);
                        break;
                    case R.id.rg_rb3:
                        noScrollViewPager.setCurrentItem(2);
                        break;
                    case R.id.rg_rb4:
                        noScrollViewPager.setCurrentItem(3);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void toTextColor(RadioGroup group, int checkedId) {
        //找到颜色资源
        Resources resource = (Resources) getBaseContext().getResources();
        ColorStateList csl = (ColorStateList) resource.getColorStateList(R.color.text_color);
        ColorStateList cs2 = (ColorStateList) resource.getColorStateList(R.color.text_color_no);
        //为字体赋颜色
        for (int i = 0; i < group.getChildCount(); i++) {
            RadioButton rb= (RadioButton) group.getChildAt(i);
            if(rb.getId()==checkedId){
                if (csl != null) {
                    rb.setTextColor(csl);
                }
            }else{
                if (csl != null) {
                    rb.setTextColor(cs2);
                }
            }
        }
    }

    private void vp_adapter() {
        //设置适配器
        noScrollViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return FragmentFactory.getFragment(position);
            }

            @Override
            public int getCount() {
                return 4;
            }
        });
    }
}
