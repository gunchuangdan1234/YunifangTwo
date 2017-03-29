package com.bawei.yunifang;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class MessageActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView messagebackimg;
    private ImageView messageticketimg;
    private EditText messageedit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        //初始化控件
        initialize();
    }

    private void initialize() {

        messagebackimg = (ImageView) findViewById(R.id.message_back_img);
        messageticketimg = (ImageView) findViewById(R.id.message_ticket_img);
        messageedit = (EditText) findViewById(R.id.message_edit);

        messagebackimg.setOnClickListener(this);
        messageticketimg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.message_back_img:
            case R.id.message_ticket_img:
                finish();
                break;
            default:
                break;
        }
    }
}
