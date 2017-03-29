package com.bawei.yunifang.factory;

import android.support.v4.app.Fragment;

import java.util.HashMap;

import com.bawei.yunifang.fragment.Cart_Fragment;
import com.bawei.yunifang.fragment.Category_Fragment;
import com.bawei.yunifang.fragment.Home_Fragment;
import com.bawei.yunifang.fragment.Mine_Fragment;

/**
 * Created by Pooh on 2016/11/28.
 */
public class FragmentFactory {
    public static HashMap<Integer,Fragment> map=new HashMap<Integer, Fragment>();
    private static Fragment fragment;

    public static Fragment getFragment(int position){
        fragment = map.get(position);
        if(fragment !=null){
            return fragment;
        }
        switch (position) {
            case 0:
                fragment=new Home_Fragment();
                break;
            case 1:
                fragment=new Category_Fragment();
                break;
            case 2:
                fragment=new Cart_Fragment();
                break;
            case 3:
                fragment=new Mine_Fragment();
                break;
        }
        map.put(position,fragment);
        return fragment;
    }
}
