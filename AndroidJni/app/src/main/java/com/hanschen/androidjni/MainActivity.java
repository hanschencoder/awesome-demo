package com.hanschen.androidjni;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.hanschen.androidjni.bean.UserInfo;
import com.hanschen.androidjni.jni.Child;
import com.hanschen.androidjni.jni.NativeApi;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Context   context;
    private NativeApi nativeApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;
        nativeApi = new NativeApi();

        findViewById(R.id.get_string_btn).setOnClickListener(MainActivity.this);
        findViewById(R.id.get_jni_version_btn).setOnClickListener(MainActivity.this);
        findViewById(R.id.get_string_info_btn).setOnClickListener(MainActivity.this);
        findViewById(R.id.call_java_method_btn).setOnClickListener(MainActivity.this);
        findViewById(R.id.call_super_class_method_btn).setOnClickListener(MainActivity.this);
        findViewById(R.id.get_super_class_btn).setOnClickListener(MainActivity.this);
        findViewById(R.id.new_user_info_from_native_btn).setOnClickListener(MainActivity.this);
        findViewById(R.id.change_field_btn).setOnClickListener(MainActivity.this);
        findViewById(R.id.reverse_array_btn).setOnClickListener(MainActivity.this);
        findViewById(R.id.create_new_thread_btn).setOnClickListener(MainActivity.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.get_jni_version_btn:
                toast("JNI Version: 0x%08X", nativeApi.getJniVersion());
                break;
            case R.id.get_string_btn:
                toast(nativeApi.getStringFromNativeWorld());
                break;
            case R.id.get_string_info_btn:
                toast(nativeApi.getStringInfo("Hello, I am Java String"));
                break;
            case R.id.call_java_method_btn:
                toast("注意观察Log输出");
                nativeApi.callUserInfoMethod(new UserInfo("HansChen", "123456"));
                break;
            case R.id.call_super_class_method_btn:
                toast("注意观察Log输出");
                nativeApi.callSuperClassMethod(new Child());
                break;
            case R.id.get_super_class_btn:
                try {
                    Class clazz = nativeApi.getSuperClass(Child.class);
                    toast(clazz.getName());
                } catch (ClassNotFoundException e) {
                    toast(e.getMessage());
                    e.printStackTrace();
                }
                break;
            case R.id.new_user_info_from_native_btn:
                toast(nativeApi.newUserInfo("test", "111111").toString());
                break;
            case R.id.change_field_btn:
                UserInfo userInfo = new UserInfo("Hans", "111111");
                nativeApi.changeField(userInfo);
                toast(userInfo.toString());
                break;
            case R.id.reverse_array_btn:
                int[] array = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
                nativeApi.reverseArray(array);
                toast(Arrays.toString(array));
                break;
            case R.id.create_new_thread_btn:
                toast("注意观察Log输出");
                nativeApi.doSomethingBackground();
                break;
        }
    }

    private void toast(String format, Object... args) {
        Toast.makeText(context.getApplicationContext(), String.format(format, args), Toast.LENGTH_SHORT).show();
    }
}
