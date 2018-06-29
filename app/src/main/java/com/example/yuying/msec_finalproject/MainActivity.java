package com.example.yuying.msec_finalproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button mBntok;
    private Button mBntclr;
    private Button mBntchange;
    private int mMode;
    private EditText mUsrName;
    private EditText mPw1;
    private EditText mPw2;
    private static final int DECODE_ENCRYPTION_KEY = 64;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBntok = (Button) findViewById(R.id.bntok);
        mBntclr = (Button) findViewById(R.id.bntclr);
        mBntchange = (Button) findViewById(R.id.bntchange);
        mUsrName = (EditText) findViewById(R.id.userid);
        mPw1 = (EditText) findViewById(R.id.pw1);
        mPw2 = (EditText) findViewById(R.id.pw2);
        //mode为1时，处于注册界面；mode等于2时，处于登录界面
        mMode = 1;

        mBntok.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                if(mMode == 1)
                {
                    String NewPW = mPw1.getText().toString();
                    String ConfirmPW = mPw2.getText().toString();
                    if(NewPW.equals("")||ConfirmPW.equals(""))
                    {
                        Toast.makeText(MainActivity.this, "Password cannot be empty.", Toast.LENGTH_SHORT).show();
                    }
                    else if(!NewPW.equals(ConfirmPW))
                    {
                        Toast.makeText(MainActivity.this, "Password MisMatch.", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        String username = mUsrName.getText().toString();
                        SharedPreferences sp = getSharedPreferences("PassWord", MODE_PRIVATE);
                        SharedPreferences.Editor ed = sp.edit();
                        //加密
                        NewPW = encryptionString(NewPW, DECODE_ENCRYPTION_KEY);
                        ed.putString(username, NewPW);
                        ed.commit();
                        Intent intent = new Intent();
                        intent.setClass(MainActivity.this, UrlActivity.class);
                        MainActivity.this.startActivity(intent);
                    }
                }
                else
                {
                    String Username = mUsrName.getText().toString();
                    SharedPreferences sp = getSharedPreferences("PassWord", MODE_PRIVATE);
                    String pw = sp.getString(Username, "");
                    pw = decodeString(pw, DECODE_ENCRYPTION_KEY);
                    String Pw = mPw2.getText().toString();

                    if(Pw.equals(""))
                    {
                        Toast.makeText(MainActivity.this, "Password cannot be empty.", Toast.LENGTH_SHORT).show();
                    }
                    else if(!Pw.equals(pw))
                    {
                        Toast.makeText(MainActivity.this, "Password MisMatch.", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Intent intent = new Intent();
                        intent.setClass(MainActivity.this, UrlActivity.class);
                        MainActivity.this.startActivity(intent);
                    }
                }
            }
        });

        mBntclr.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                mPw2.setText("");
                mUsrName.setText("");
                if(mMode == 1){
                    mPw1.setText("");
                }
            }
        });

        mBntchange.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                if(mMode == 1){
                    mMode = 2;
                    mBntchange.setText("切换至注册界面");
                    mPw1.setVisibility(View.INVISIBLE);
                    mPw2.setHint("Password");
                    mPw2.setText("");
                    mUsrName.setText("");
                }
                else{
                    mMode = 1;
                    mBntchange.setText("切换至登录界面");
                    mPw1.setVisibility(View.VISIBLE);
                    mPw1.setHint("New Password");
                    mPw2.setHint("Comfirm Password");
                    mPw1.setText("");
                    mPw2.setText("");
                    mUsrName.setText("");
                }
            }
        });
    }
    /**
     * 加密方法
     * @param str 要加密的字符串
     * @param key 加密的密匙
     * @return 返回加密后的字符串
     */
    private String encryptionString(String str, int key) {
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            chars[i] = (char) (chars[i] + key);
        }
        return String.valueOf(chars);
    }

    /**
     * 解密方法
     * @param str 要解密的字符串
     * @param key 解密的密匙，跟加密一样
     * @return 返回解密后的字符串
     */
    private String decodeString(String str, int key) {
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            chars[i] = (char) (chars[i] - key);
        }
        return String.valueOf(chars);
    }
}

