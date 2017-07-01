package com.example.will.parking_share;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import cn.jpush.android.api.JPushInterface;

public class UserActivity extends AppCompatActivity {
    public Handler mHandler;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Button share=(Button)findViewById(R.id.share);
        Button find=(Button)findViewById(R.id.find);
        TextView userview=(TextView)findViewById(R.id.userview);
        Intent i = getIntent();
        username=i.getStringExtra("username");
        userview.setText("User: "+username);
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        mHandler = new Handler() {
            public void handleMessage(Message msg) {
                String tst = msg.getData().getString("1");
                Toast.makeText(UserActivity.this, tst, Toast.LENGTH_SHORT).show();
                super.handleMessage(msg);
            }
        };

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent();
                    //從MainActivity 到Main2Activity
                    intent.setClass(UserActivity.this ,ShareActivity.class);
                    //開啟Activity
                    startActivity(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent();
                    intent.putExtra("username",username);
                    //從MainActivity 到Main2Activity
                    intent.setClass(UserActivity.this ,findActivity.class);
                    //開啟Activity
                    startActivity(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }



}
