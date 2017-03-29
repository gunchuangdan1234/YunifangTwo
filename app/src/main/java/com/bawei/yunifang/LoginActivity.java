package com.bawei.yunifang;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

import com.bawei.yunifang.application.MyApplication;
import com.bawei.yunifang.fragment.Mine_Fragment;
import com.bawei.yunifang.utils.CommonUtils;
import com.bawei.yunifang.utils.ImageLoaderUtils;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView login_back_img;
    private TextView login_sign_tv;
    private TextView login_number_1;
    private TextView login_phone_1;
    private TextView login_number_2;
    private TextView login_phone_2;
    private EditText login_edit_phone;
    private EditText login_edit_pass;
    private EditText login_edit_code;
    private TextView login_tv_getcode;
    private Button login_but;
    private LinearLayout login_code_lin;
    private TextView disanfang_tv;
    private TextView disanfang_xiahuaxian;
    private TextView disanfang_jieshao;
    private PopupWindow popupWindow;
    private ImageView login_guanggao_img;
    private LinearLayout background_lin;
    private ImageView login_qq_img;
    private ImageView login_weibo_img;
    private ImageView login_wx_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //初始化控件的方法
        initView();
        //控件的监听方法
        initClick();
        //判断下部首单立减的图片是否显示
        initShow();
    }

    private void initShow() {
        int showInt=getIntent().getIntExtra("show",100);
        if(showInt== MyApplication.unShowLoginImg){
            disanfang_tv.setVisibility(View.GONE);
            disanfang_xiahuaxian.setVisibility(View.GONE);
            disanfang_jieshao.setVisibility(View.GONE);
            login_guanggao_img.setVisibility(View.GONE);
        }else{
            disanfang_tv.setVisibility(View.VISIBLE);
            disanfang_xiahuaxian.setVisibility(View.VISIBLE);
            disanfang_jieshao.setVisibility(View.VISIBLE);
            login_guanggao_img.setVisibility(View.VISIBLE);
        }
    }

    //控件的监听方法
    private void initClick() {
        login_number_1.setOnClickListener(this);
        login_phone_1.setOnClickListener(this);
        login_back_img.setOnClickListener(this);
        //第三方灯按钮的监听
        disanfang_tv.setOnClickListener(this);
    }

    //初始化控件的方法
    private void initView() {
        //返回按钮的箭头
        login_back_img = (ImageView) findViewById(R.id.login_back_img);
        //注册
        login_sign_tv = (TextView) findViewById(R.id.login_sign_tv);
        //手机登陆及账号登陆
        login_number_1 = (TextView) findViewById(R.id.login_number_1);
        login_phone_1 = (TextView) findViewById(R.id.login_phone_1);
        login_number_2 = (TextView) findViewById(R.id.login_number_2);
        login_phone_2 = (TextView) findViewById(R.id.login_phone_2);
        //输入手机号码
        login_edit_phone = (EditText) findViewById(R.id.login_edit_phone);
        //输入密码
        login_edit_pass = (EditText) findViewById(R.id.login_edit_pass);
        //输入验证码
        login_edit_code = (EditText) findViewById(R.id.login_edit_code);
        login_code_lin = (LinearLayout)findViewById(R.id.login_code_lin);
        //获取验证码
        login_tv_getcode = (TextView) findViewById(R.id.login_tv_getcode);
        //登陆按钮
        login_but = (Button) findViewById(R.id.login_but);
        //第三方快速登录
        disanfang_tv = (TextView) findViewById(R.id.disanfang_tv);
        disanfang_xiahuaxian = (TextView) findViewById(R.id.disanfang_youjiantou);
        disanfang_jieshao = (TextView) findViewById(R.id.disanfang_tv_jieshao);
        login_guanggao_img = (ImageView) findViewById(R.id.login_guanggao_img);
        //设置背景色
        background_lin = (LinearLayout) findViewById(R.id.background_lin);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //background_lin.setBackgroundResource(R.mipmap.bb);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_number_1:
                login_number_2.setVisibility(View.VISIBLE);
                login_phone_2.setVisibility(View.INVISIBLE);
                login_code_lin.setVisibility(View.GONE);
                login_edit_pass.setVisibility(View.VISIBLE);
                break;
            case R.id.login_phone_1:
                login_number_2.setVisibility(View.INVISIBLE);
                login_phone_2.setVisibility(View.VISIBLE);
                login_code_lin.setVisibility(View.VISIBLE);
                login_edit_pass.setVisibility(View.GONE);
                break;
            case R.id.login_back_img:
                finish();
                break;
            case R.id.disanfang_tv:
                //弹出popwindow
                bottomwindow(disanfang_tv);
                //渐变背景色
                getBack();
                break;
            //QQ登陆
            case R.id.login_qq_img:
                UMShareAPI  mShareAPI = UMShareAPI.get( LoginActivity.this );
                //mShareAPI.doOauthVerify(LoginActivity.this, SHARE_MEDIA.QQ, umAuthListener);
                mShareAPI.getPlatformInfo(LoginActivity.this, SHARE_MEDIA.QQ, umAuthListener);
                break;
            //微博登陆
            case R.id.login_weibo_img:
                Toast.makeText(this, "微博登陆正在维护，请稍后！", Toast.LENGTH_SHORT).show();
                break;
            //微信登陆
            case R.id.login_wx_img:
                Toast.makeText(this, "微信登陆正在维护，请稍后！", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
   }


    //弹出pop
    public void bottomwindow(View v){
        if (popupWindow != null && popupWindow.isShowing()) {
            return;
        }
        //LinearLayout layout2 = (LinearLayout) mContext.getLayoutInflater().inflate(R.layout.window_popup, null);
        LinearLayout layout= (LinearLayout) CommonUtils.inflate(R.layout.third_party_popu);
        //获取popwindow的中的控件
        getPopControl(layout);
        popupWindow = new PopupWindow(layout,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        //点击空白处时，隐藏掉pop窗口
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //添加弹出、弹入的动画
        popupWindow.setAnimationStyle(R.style.Popupwindow);
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        popupWindow.showAtLocation(v, Gravity.LEFT | Gravity.BOTTOM, 0, -location[1]);
        //popupWindow.showAtLocation(v, Gravity.CENTER,0,0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                BackBackground();
            }
        });
    }
    //查找popwindow中的控件
    private void getPopControl(LinearLayout layout) {
        //查找控件
        login_qq_img = (ImageView) layout.findViewById(R.id.login_qq_img);
        login_weibo_img = (ImageView) layout.findViewById(R.id.login_weibo_img);
        login_wx_img = (ImageView) layout.findViewById(R.id.login_wx_img);
        //设置点击事件
        login_qq_img.setOnClickListener(this);
        login_weibo_img.setOnClickListener(this);
        login_wx_img.setOnClickListener(this);
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            backgroundAlpha((float) msg.obj);
        }
    };

    public void backgroundAlpha(float bgAlpha)
    {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0n
        getWindow().setAttributes(lp);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }
    //popwindow消失，背景颜色逐渐变亮
    private float alpha=1.0f;
    private void BackBackground() {
        new Thread(new Runnable(){
            @Override
            public void run() {
                //此处while的条件alpha不能<= 否则会出现黑屏
                while(alpha<0.999f){
                    try {
                        Thread.sleep(4);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.d("HeadPortrait","alpha:"+alpha);
                    Message msg =handler.obtainMessage();
                    msg.what = 1;
                    alpha+=0.01f;
                    msg.obj =alpha ;
                    handler.sendMessage(msg);
                }
            }

        }).start();
    }

    //点击时背景逐渐变暗
    private void getBack() {
        new Thread(new Runnable(){
            @Override
            public void run() {
                while(alpha>0.5f){
                    try {
                        //4是根据弹出动画时间和减少的透明度计算
                        Thread.sleep(4);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Message msg =handler.obtainMessage();
                    msg.what = 1;
                    //每次减少0.01，精度越高，变暗的效果越流畅
                    alpha-=0.01f;
                    msg.obj =alpha ;
                    handler.sendMessage(msg);
                }
            }

        }).start();
    }
    //QQ登陆
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            //Toast.makeText(getApplicationContext(), "Authorize succeed"+data.toString(), Toast.LENGTH_SHORT).show();
            //登陆成功之后更换头像
            ImageLoader.getInstance().displayImage(data.get("profile_image_url"),Mine_Fragment.mine_img_xiaomogu,ImageLoaderUtils.initOptions());
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText( getApplicationContext(), "Authorize fail", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText( getApplicationContext(), "Authorize cancel", Toast.LENGTH_SHORT).show();
        }
    };
}
