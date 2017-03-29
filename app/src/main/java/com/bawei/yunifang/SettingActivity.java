package com.bawei.yunifang;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import com.bawei.yunifang.utils.DataClearManager;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView setting_qingchuhuancun_tv;
    private TextView setting_yihuancun_tv;
    private File caCheDir;
    private ImageView setting_back_img;
    private TextView setting_know_tv;
    private TextView setting_yijian_tv;
    private RelativeLayout setting_my_rela;
    private RelativeLayout setting_phone_rela;
    private TextView setting_jiancha_tv;
    private TextView setting_banben_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        //初始化控件
        initView();
        //获取数据的方法
        getData();
        //监听方法
        initListener();
    }

    private void initListener() {
        //清除缓存的tv
        setting_qingchuhuancun_tv.setOnClickListener(this);
        //返回按钮的img
        setting_back_img.setOnClickListener(this);
    }

    private void getData() {
        try {
            caCheDir = this.getCacheDir();
            String cacheSize = DataClearManager.getCacheSize(caCheDir);
            setting_yihuancun_tv.setText(cacheSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        //清除缓存
        setting_qingchuhuancun_tv = (TextView) findViewById(R.id.setting_qingchuhuancun_tv);
        setting_yihuancun_tv = (TextView) findViewById(R.id.setting_yihuancun_tv);
        //返回的按钮
        setting_back_img = (ImageView) findViewById(R.id.setting_back_img);
        //购物须知tv
        setting_know_tv = (TextView) findViewById(R.id.setting_know_tv);
        //意见反馈
        setting_yijian_tv = (TextView) findViewById(R.id.setting_yijian_tv);
        setting_yijian_tv.setOnClickListener(this);
        //关于我们
        setting_my_rela = (RelativeLayout) findViewById(R.id.setting_my_rela);
        setting_my_rela.setOnClickListener(this);
        //拨打电话
        setting_phone_rela = (RelativeLayout) findViewById(R.id.setting_phone_rela);
        setting_phone_rela.setOnClickListener(this);
        //版本更新
        setting_jiancha_tv = (TextView) findViewById(R.id.setting_jiancha_tv);
        //版本
        setting_banben_tv = (TextView) findViewById(R.id.setting_banben_tv);
        setting_banben_tv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setting_qingchuhuancun_tv:
                try {
                    DataClearManager.cleanInternalCache(SettingActivity.this);
                    String cacheSize = DataClearManager.getCacheSize(caCheDir);
                    //Toast.makeText(SettingActivity.this,"清一下",Toast.LENGTH_SHORT).show();
                    setting_yihuancun_tv.setText(cacheSize);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.setting_back_img:
                finish();
                break;
            case R.id.setting_phone_rela:
                //用intent启动拨打电话
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:4006880900"));
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(intent);
                break;
            case R.id.setting_my_rela:
                //跳转到关于我们的界面
                Intent inn=new Intent(SettingActivity.this,QBCodeActivity.class);
                startActivity(inn);
                break;
            case R.id.setting_banben_tv:
                Toast.makeText(this, "已经是最新版本了！", Toast.LENGTH_SHORT).show();
                break;
            case R.id.setting_yijian_tv:
                //跳转到意见反馈的界面
                Intent inn2=new Intent(SettingActivity.this,OpinionActivity.class);
                startActivity(inn2);
                break;
            default:
                break;
        }
    }
}
