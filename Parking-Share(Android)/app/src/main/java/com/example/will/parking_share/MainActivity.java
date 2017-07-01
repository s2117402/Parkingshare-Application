package com.example.will.parking_share;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button bt = (Button) findViewById(R.id.login);
        Button bt2 = (Button) findViewById(R.id.gotoregister);
        final EditText user = (EditText) findViewById(R.id.username);
        final EditText pass = (EditText) findViewById(R.id.password);
        requestPermission();
        final Handler mHandler = new Handler() {
            public void handleMessage(Message msg) {
                String tst = msg.getData().getString("1");
                Toast.makeText(MainActivity.this, tst, Toast.LENGTH_SHORT).show();
                if(tst.equals("Login Successfully")){
                    try {
                        JPushInterface.setAliasAndTags(getApplicationContext(),
                                user.getText().toString(),
                                null,
                                mAliasCallback);
                        Intent intent = new Intent();
                        intent.putExtra("username", user.getText().toString());
                        //從MainActivity 到Main2Activity
                        intent.setClass(MainActivity.this , UserActivity.class);
                        //開啟Activity
                        startActivity(intent);
                        finish();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                super.handleMessage(msg);
            }
        };
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = user.getText().toString();
                String password = pass.getText().toString();
                if (username == "" || password == "") {
                    Message msg = new Message();
                    Bundle mBundle = new Bundle();
                    mBundle.putString("1", "Username or Password can't be empty!");
                    msg.setData(mBundle);
                    mHandler.sendMessage(msg);
                    Log.d("TAG", "u&p empty");
                }else{
                    final Map<String,String> info=new HashMap<String, String>();
                    info.put("name",username);
                    info.put("password",password);
                    new Thread(){
                        public void run(){
                            try{
                                String result=SendPost.sendPost("http://192.168.0.101:5000/","authenticate",info);
                                Message msg = new Message();
                                Bundle mBundle = new Bundle();
                                mBundle.putString("1", result);
                                msg.setData(mBundle);
                                mHandler.sendMessage(msg);
                                Log.d("TAG", "log in");
                            }catch(Exception e){
                                e.printStackTrace();
                            }
                        }

                    }.start();

                }

            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent();
                    //從MainActivity 到Main2Activity
                    intent.setClass(MainActivity.this , RegisterActivity.class);
                    //開啟Activity
                    startActivity(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    public final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs ;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                    break;
                default:
                    logs = "Failed with errorCode = " + code;
            }
        }
    };

    public void requestPermission() {
        //判断当前Activity是否已经获得了该权限
        final int EXTERNAL_STORAGE_REQ_CODE = 10;
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //如果App的权限申请曾经被用户拒绝过，就需要在这里跟用户做出解释
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "please give me the permission", Toast.LENGTH_SHORT).show();
            } else {
                //进行权限请求
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        EXTERNAL_STORAGE_REQ_CODE);
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        EXTERNAL_STORAGE_REQ_CODE);
            }
        }
    }
}


