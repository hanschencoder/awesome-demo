package com.hanschen.androidjni;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.hanschen.androidjni.bean.UserInfo;
import com.hanschen.androidjni.jni.NativeInterface;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Context         context;
    private NativeInterface nativeInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;
        nativeInterface = new NativeInterface();

        findViewById(R.id.get_string_btn).setOnClickListener(MainActivity.this);
        findViewById(R.id.get_string_info_btn).setOnClickListener(MainActivity.this);
        findViewById(R.id.call_java_method_btn).setOnClickListener(MainActivity.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.get_string_btn:
                Toast.makeText(context, nativeInterface.getStringFromNativeWorld(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.get_string_info_btn:
                Toast.makeText(context, nativeInterface.getStringInfo("Hello, I am Java String"), Toast.LENGTH_SHORT).show();
                break;
            case R.id.call_java_method_btn:
                Toast.makeText(context, "注意观察Log输出", Toast.LENGTH_SHORT).show();
                nativeInterface.callUserInfoMethod(new UserInfo("HansChen", "123456"));
                break;
        }
    }
}
