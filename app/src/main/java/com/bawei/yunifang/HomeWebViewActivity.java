package com.bawei.yunifang;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeWebViewActivity extends AppCompatActivity implements View.OnClickListener {

    private WebView home_webview;
    private TextView home_webview_title;
    private ImageView home_webview_back_img;
    private ImageView home_webview_share_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_web_view);
        //初始化控件
        initView();

        //webview的设置
        setWebView();

    }

    private void setWebView() {
        String name=getIntent().getStringExtra("name");
        String title=getIntent().getStringExtra("title");
        home_webview_title.setText(title);
        home_webview.loadUrl(name);
        //rela.setOnTouchListener(this);
        home_webview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        WebSettings webSettings = home_webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }

    private void initView() {
        //找出控件
        home_webview = (WebView) findViewById(R.id.home_webview);
        home_webview_title = (TextView) findViewById(R.id.home_webview_title);
        //返回的按钮
        home_webview_back_img = (ImageView) findViewById(R.id.home_webview_back_img);
        home_webview_share_img = (ImageView) findViewById(R.id.home_webview_share_img);
        //设置监听
        home_webview_back_img.setOnClickListener(this);
        home_webview_share_img.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_webview_back_img:
                finish();
                break;
            case R.id.home_webview_share_img:

                break;
            default:
                break;
        }
    }
}
