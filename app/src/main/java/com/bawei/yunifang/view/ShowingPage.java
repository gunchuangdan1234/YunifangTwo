package com.bawei.yunifang.view;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.bawei.yunifang.R;
import com.bawei.yunifang.utils.CommonUtils;

/**
 * Created by Pooh on 2016/11/28.
 */
public abstract class ShowingPage extends FrameLayout implements View.OnClickListener {
    //表示状态
    public static final int STATE_UNLOAD = 1;
    public static final int STATE_LOAD = 2;
    public static final int STATE_LOAD_ERROR = 3;
    public static final int STATE_LOAD_EMPTY = 4;
    public static final int STATE_LOAD_SUCCESS = 5;

    //定义一个初始状态--当前是未加载状态
    public int currentState = STATE_UNLOAD;
    private View showingpage_unload;
    private View showingpage_load_error;
    private View showingpage_load_empty;
    private View showingpage_loading;
    private View showingPage_success;
    private LayoutParams params;
    private Button bt_reload;

    //从代码中调用的构造方法
    public ShowingPage(Context context) {
        super(context);
        params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        //初始化布局
        if(showingpage_unload==null){
            showingpage_unload= CommonUtils.inflate(R.layout.showingpage_unload);
            //并添加到framlayout中
            this.addView(showingpage_unload);
        }
        //初始化加载错误的界面
        if(showingpage_load_error==null){
            showingpage_load_error=CommonUtils.inflate(R.layout.showingpage_load_error);
            //查找重新加载的按钮
            bt_reload = (Button) showingpage_load_error.findViewById(R.id.bt_reload);
            bt_reload.setOnClickListener(this);
            //并添加到framlayout中
            this.addView(showingpage_load_error);
        }
        //初始化加载为空的界面
        if(showingpage_load_empty==null){
            showingpage_load_empty=CommonUtils.inflate(R.layout.showingpage_load_empty);
            //并添加到framlayout中
            this.addView(showingpage_load_empty);
        }
        //初始化正在加载中的界面
        if(showingpage_loading==null){
            showingpage_loading=CommonUtils.inflate(R.layout.showingpage_loading);
        }
        //添加展示
        showPage();
        //加载数据
        onload();
    }

    protected abstract void onload();

    private void showPage() {
        CommonUtils.runOnMainThread(new Runnable() {
            @Override
            public void run() {
                showUIPage();
            }
        });
    }

    private void showUIPage() {
        //此方法是设置当前界面要显示哪一个布局
        if(showingpage_loading!=null){
            showingpage_loading.setVisibility(currentState==STATE_LOAD?View.VISIBLE:View.GONE);
        }
        if(showingpage_load_empty!=null){
            showingpage_load_empty.setVisibility(currentState==STATE_LOAD_EMPTY?View.VISIBLE:View.GONE);
        }
        if(showingpage_load_error!=null){
            showingpage_load_error.setVisibility(currentState==STATE_LOAD_ERROR?View.VISIBLE:View.GONE);
        }
        if(showingpage_unload!=null){
            showingpage_unload.setVisibility(currentState==STATE_UNLOAD?View.VISIBLE:View.GONE);
        }
        //成功的状态-----无界面
        if(showingPage_success==null&&currentState==STATE_LOAD_SUCCESS){
            //将当前的界面设置为加载成功的界面
            showingPage_success=createSuccessView();
            //将当前界面添加到framlayout中
            this.addView(showingPage_success);
        }
        if(showingPage_success!=null){
            showingPage_success.setVisibility(currentState==STATE_LOAD_SUCCESS?View.VISIBLE:View.GONE);
        }
    }
    //返回的加载的成功界面
    public abstract View createSuccessView();

    //提供一个请求之后，设置当前界面，展示界面的方法
    public void  showCurrentPage(StateType stateType){
        this.currentState=stateType.getCurrentState();
        //重置界面
        showPage();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_reload:
                resetView();
                break;
        }
    }

    private void resetView() {
        //重置状态
        if(currentState!=STATE_UNLOAD){
            currentState=STATE_UNLOAD;
        }
        //重新加载界面
        showPage();
        //重新加载数据
        onload();
    }

    /**
     * 枚举类
     */
    public enum StateType{
        //请求类型
        STATE_LOAD_ERROR(3),STATE_LOAD_EMPTY(4),STATE_LOAD_SUCCESS(5);
        private final int currentState;

        StateType(int currentState){
            this.currentState=currentState;
        }

        public int getCurrentState(){
            return currentState;
        }
    }
}
