package com.hanschen.androidjni.jni;

import android.util.Log;

import com.hanschen.androidjni.bean.UserInfo;

/**
 * Created by chenhang on 2016/10/13.
 */

public class NativeApi {

    static {
        System.loadLibrary("native-lib");
    }

    public static void onCall(String message) {
        Log.d("AndroidJni", String.format("onCall方法在线程%d中被调用, %s", Thread.currentThread().getId(), message));
    }

    /**
     * 获取JNI版本
     *
     * @return 版本号
     */
    public native int getJniVersion();

    /**
     * 从native返回一个字符串
     */
    public native String getStringFromNativeWorld();

    /**
     * 在native解析传入的字符串，并输出解析结果
     *
     * @param input 输入字符串
     * @return 解析结果
     */
    public native String getStringInfo(String input);

    /**
     * 在native调用{@link UserInfo}类的某个方法
     */
    public native void callUserInfoMethod(UserInfo userInfo);

    /**
     * 调用{@link Child}的父类的{@link Father#toString()}方法
     */
    public native void callSuperClassMethod(Child child);

    /**
     * 获取输入类的父类
     *
     * @return 输入类的父类
     * @throws ClassNotFoundException
     */
    public native Class getSuperClass(Class clazz) throws ClassNotFoundException;

    /**
     * 根据输入构造出的新{@link UserInfo}对象
     *
     * @return 新的对象
     */
    public native UserInfo newUserInfo(String username, String password);

    /**
     * 在native改变该对象的属性
     */
    public native void changeField(UserInfo userInfo);

    /**
     * 在native反转输入数组
     */
    public native void reverseArray(int[] array);


    /**
     * 在native层新启动一个线程，处理一些事情
     */
    public native void doSomethingBackground();


}
