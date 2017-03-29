package com.bawei.yunifang.base;

import android.text.TextUtils;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.bawei.yunifang.application.MyApplication;
import com.bawei.yunifang.utils.CommonUtils;
import com.bawei.yunifang.utils.MD5Encoder;
import com.bawei.yunifang.view.ShowingPage;

/**
 * Created by Pooh on 2016/11/29.
 */
public abstract class BaseDate {

    public static int NOTIME=0;

    public static int NOMALTIME=24*3*60*60*1000;

    private final File cacheDir;

    public BaseDate(){
        //存到那里去？
        cacheDir = MyApplication.getConText().getCacheDir();
        File file=new File(cacheDir,"yunifang");
        //判断此文件夹是否存在，不存在就创建
        if(!file.exists()){
            file.mkdirs();
        }
    }
    /**
     * 这个方法供外面调用
     * @param path 路径
     * @param args 参数
     * @param index 索引
     * @param validTime 有效时间
     */
    public void getDate(String path,String args,int index,int validTime){
        //判断请求时间
        if(validTime==0){
            //直接请求网络
            getDataFromNet(path,args,index,validTime);
        }else{
            String data=getDataFromLocal(path,args,index,validTime);
            if(TextUtils.isEmpty(data)){
                getDataFromNet(path,args,index,validTime);
            }else{
                //请求到数据，作返回
                setResultData(data);
            }
        }
    }

    /**
     * 网络获取----OkHttpClient
     * @param path
     * @param args
     * @param index
     * @param validTime
     */
    private void getDataFromNet(final String path, final String args, final int index, final int validTime) {
        //1.创建对象
        OkHttpClient okHttpClient=new OkHttpClient();
        //2.创建Request
        Request request=new Request.Builder()
                .url(path+"?"+args)
                .build();
        //3.创建call对象
        Call call=okHttpClient.newCall(request);
        //4.请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                setResultError(ShowingPage.StateType.STATE_LOAD_ERROR);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                final String data=response.body().string();
                //设置数据
                CommonUtils.runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        setResultData(data);
                    }
                });
                //写入本地
                writeToLocal(path,args,index,validTime,data);
            }
        });
    }

    protected abstract void setResultError(ShowingPage.StateType stateLoadError);

    /**
     * 写入本地
     * @param data
     */
    private void writeToLocal(String path, String args, int index, int validTime, String data) {
        try {
            //每一次请求都创建一个文件
            File file=new File(cacheDir, MD5Encoder.encode(path)+index);
            BufferedWriter bw=new BufferedWriter(new FileWriter(file));
            //第一行存入时间
            bw.write(System.currentTimeMillis()+validTime+"\r\n");
            bw.write(data);
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 本地获取
     * @param path
     * @param args
     * @param index
     * @param validTime
     * @return
     */
    private String getDataFromLocal(String path, String args, int index, int validTime) {
        try {
            //创建文件
            File file=new File(cacheDir,MD5Encoder.encode(path)+index);
            BufferedReader br=new BufferedReader(new FileReader(file));
            String time=br.readLine();
            long myTime=Long.parseLong(time);
            if(System.currentTimeMillis()<myTime){
                StringBuilder strbuilder=new StringBuilder();
                String str="";
                while ((str=br.readLine())!=null) {
                    strbuilder.append(str);
                }
                br.close();
                return strbuilder.toString();
            }else{
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将请求成功的数据作返回
     * @param data
     */
    public abstract void setResultData(String data);

   
}
