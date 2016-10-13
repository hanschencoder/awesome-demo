#include <jni.h>
#include <stdio.h>
#include<malloc.h>
#include <android/log.h>

#define LOG_TAG    "AndroidJni"
#define log(...)  __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)

JNIEXPORT jstring JNICALL
Java_com_hanschen_androidjni_jni_NativeInterface_getStringFromNativeWorld(JNIEnv *env,
                                                                          jobject instance) {
    log("call native method: %d", __LINE__);
    jstring returnValue = (*env)->NewStringUTF(env, "Hello, this is string from native world");
    return returnValue;
}

JNIEXPORT jstring JNICALL
Java_com_hanschen_androidjni_jni_NativeInterface_getStringInfo(JNIEnv *env, jobject instance,
                                                               jstring input_) {
    jboolean isCopy = JNI_FALSE;
    jsize length = (*env)->GetStringLength(env, input_);
    const char *input = (*env)->GetStringUTFChars(env, input_, &isCopy);
    log("input:%s, length:%d, isCopy:%d", input, length, isCopy);

    char *output = (char *) malloc((length + 20) * sizeof(char));
    sprintf(output, "input:%s, length:%d", input, length);

    (*env)->ReleaseStringUTFChars(env, input_, input);

    return (*env)->NewStringUTF(env, output);
}

JNIEXPORT void JNICALL
Java_com_hanschen_androidjni_jni_NativeInterface_callUserInfoMethod(JNIEnv *env, jobject instance,
                                                                    jobject userInfo) {
    jclass clazz = (*env)->GetObjectClass(env, userInfo);
    jmethodID methodId = (*env)->GetMethodID(env, clazz, "toString", "()Ljava/lang/String;");
    jthrowable throwable = (*env)->ExceptionOccurred(env);
    if (throwable == NULL) {
        jstring result = (*env)->CallObjectMethod(env, userInfo, methodId);
        const char *temp = (*env)->GetStringUTFChars(env, result, NULL);
        log("toString:%s", temp);
        (*env)->ReleaseStringUTFChars(env, result, temp);
    } else {
        log("Method not found");
        (*env)->ExceptionDescribe(env);
        (*env)->ExceptionClear(env);
    }

    methodId = (*env)->GetMethodID(env, clazz, "setUsername", "(Ljava/lang/String;)V");
    throwable = (*env)->ExceptionOccurred(env);
    if (throwable == NULL) {
        (*env)->CallVoidMethod(env, userInfo, methodId, (*env)->NewStringUTF(env, "newUsername"));
        log("call setUsername");
    } else {
        log("Method not found");
        (*env)->ExceptionDescribe(env);
        (*env)->ExceptionClear(env);
    }

    methodId = (*env)->GetMethodID(env, clazz, "toString", "()Ljava/lang/String;");
    throwable = (*env)->ExceptionOccurred(env);
    if (throwable == NULL) {
        jstring result = (*env)->CallObjectMethod(env, userInfo, methodId);
        const char *temp = (*env)->GetStringUTFChars(env, result, NULL);
        log("toString:%s", temp);
        (*env)->ReleaseStringUTFChars(env, result, temp);
    } else {
        log("Method not found");
        (*env)->ExceptionDescribe(env);
        (*env)->ExceptionClear(env);
    }
}