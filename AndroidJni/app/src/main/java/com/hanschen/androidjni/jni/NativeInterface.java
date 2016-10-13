package com.hanschen.androidjni.jni;

import com.hanschen.androidjni.bean.UserInfo;

/**
 * Created by chenhang on 2016/10/13.
 */

public class NativeInterface extends BaseClass implements TestInterface {

    static {
        System.loadLibrary("native-lib");
    }

    public native String getStringFromNativeWorld();

    public native String getStringInfo(String input);

    public native void callUserInfoMethod(UserInfo userInfo);
}
