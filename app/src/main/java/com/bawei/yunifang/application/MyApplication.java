package com.bawei.yunifang.application;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Process;

import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import org.xutils.x;

import com.bawei.yunifang.utils.ImageLoaderUtils;

/**
 * Created by Pooh on 2016/11/28.
 */
public class MyApplication extends Application{

    private static Context applicationContext;
    private static Handler handler;
    private static int mainId;
    private static Thread thread;
    public static boolean quanxuan=true;

    //定义显示隐藏的登陆界面的图片及文字
    public static int showLoginImg=0;
    public static int unShowLoginImg=1;

    //判断要显示的是哪一个
    public static boolean myFlag=false;

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化ImageLoader
        ImageLoaderUtils.initConfiguration(this);
        //初始化xutils
        x.Ext.init(this);
        x.Ext.setDebug(true);
        //得到上下文
        applicationContext = getApplicationContext();
        //Handler方法
        handler = new Handler();
        //线程号
        mainId = Process.myTid();
        //获取主线程
        thread = Thread.currentThread();
        //qq登陆
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
        UMShareAPI.get(this);
    }
    //上下文的方法
    public static Context getConText(){
        return applicationContext;
    }
    //得到hander的方法
    public static Handler getHandler(){
        return handler;
    }
    //得到线程号的方法
    public static int getMainId(){
        return mainId;
    }
    //得到主线程的方法
    public static Thread getMainThread(){
        return thread;
    }
}
