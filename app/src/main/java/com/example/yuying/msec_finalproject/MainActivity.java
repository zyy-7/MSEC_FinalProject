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
    private EditText mUsrName;
    private EditText mPw1;
    private EditText mPw2;
    private boolean isStored = false;
    private String MyPw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBntok = (Button) findViewById(R.id.bntok);
        mBntclr = (Button) findViewById(R.id.bntclr);
        mUsrName = (EditText) findViewById(R.id.userid);
        mPw1 = (EditText) findViewById(R.id.pw1);
        mPw2 = (EditText) findViewById(R.id.pw2);

        MyPw = GetStoredPW();

        mBntok.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                if(!isStored)
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
                        SharedPreferences sp = getSharedPreferences("PassWord", MODE_PRIVATE);
                        SharedPreferences.Editor ed = sp.edit();
                        ed.putString("pw", NewPW);
                        ed.commit();
                        Intent intent = new Intent();
                        intent.setClass(MainActivity.this, SysuActivity.class);
                        MainActivity.this.startActivity(intent);
                    }
                }
                else
                {
                    String Pw = mPw2.getText().toString();
                    if(Pw.equals(""))
                    {
                        Toast.makeText(MainActivity.this, "Password cannot be empty.", Toast.LENGTH_SHORT).show();
                    }
                    else if(!Pw.equals(MyPw))
                    {
                        Toast.makeText(MainActivity.this, "Password MisMatch.", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Intent intent = new Intent();
                        intent.setClass(MainActivity.this, SysuActivity.class);
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
                if(!isStored)
                    mPw1.setText("");
            }
        });
    }

    private String GetStoredPW()
    {
        SharedPreferences sp = getSharedPreferences("PassWord", MODE_PRIVATE);
        String pw = sp.getString("pw", "");

        if(!pw.equals("")){
            mPw1.setVisibility(View.INVISIBLE);
            mPw2.setHint("Password");
            mPw2.setText("");
            isStored = true;
        }
        return pw;
    }

}

