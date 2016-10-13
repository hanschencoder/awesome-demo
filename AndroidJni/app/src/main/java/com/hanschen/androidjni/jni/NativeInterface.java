package com.hanschen.androidjni.jni;

/**
 * Created by chenhang on 2016/10/13.
 */

public class NativeInterface extends BaseClass implements TestInterface {

    static {
        System.loadLibrary("native-lib");
    }

    public native String getStringFromNativeWorld();
}
