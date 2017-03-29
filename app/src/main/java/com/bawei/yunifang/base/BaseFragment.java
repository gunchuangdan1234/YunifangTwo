package com.bawei.yunifang.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bawei.yunifang.view.ShowingPage;

/**
 * Created by Pooh on 2016/11/28.
 */
public abstract class BaseFragment extends Fragment{

    private ShowingPage showingPage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        showingPage = new ShowingPage(getActivity()) {
            @Override
            protected void onload(){
            }

            @Override
            public View createSuccessView() {
                //加载布局
                return BaseFragment.this.createSuccessView();
            }
        };
                //加载数据
                BaseFragment.this.onload();
        return showingPage;
    }
    protected abstract void onload();
    //加载布局的抽象方法
    protected  abstract View createSuccessView();

    //对外提供的方法，判断将要加载哪一个布局
    public void showCurrentPage(ShowingPage.StateType stateType){
        if(stateType!=null){
            showingPage.showCurrentPage(stateType);
        }
    }
}
