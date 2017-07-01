package com.example.will.parking_share;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        final Handler mHandler = new Handler() {
            public void handleMessage(Message msg) {
                String tst = msg.getData().getString("1");
                Toast.makeText(RegisterActivity.this, tst, Toast.LENGTH_SHORT).show();
                if(tst.equals("Register successfully")){
                    try {
                        Intent intent = new Intent();
                        //從MainActivity 到Main2Activity
                        intent.setClass(RegisterActivity.this , MainActivity.class);
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
        final EditText usernameR=(EditText)findViewById(R.id.usernameR);
        final EditText passwordR=(EditText)findViewById(R.id.passwordR);
        Button register=(Button)findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Map<String,String> info=new HashMap<String, String>();
                String username=usernameR.getText().toString();
                String password=passwordR.getText().toString();
                if (username == "" || password == "") {
                    Message msg = new Message();
                    Bundle mBundle = new Bundle();
                    mBundle.putString("1", "Username or Password can't be empty!");
                    msg.setData(mBundle);
                    mHandler.sendMessage(msg);
                    Log.d("TAG", "u&p empty");
                }else {
                    final Map<String, String> infoR = new HashMap<String, String>();
                    infoR.put("name", username);
                    infoR.put("password", password);
                    new Thread() {
                        public void run() {
                            try {
                                String result = SendPost.sendPost("http://192.168.100.4:5000/", "register", infoR);
                                Message msg = new Message();
                                Bundle mBundle = new Bundle();
                                mBundle.putString("1", result);
                                msg.setData(mBundle);
                                mHandler.sendMessage(msg);
                                Log.d("TAG", "log in");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    }.start();
                }
            }
        }
        );
    }
}
