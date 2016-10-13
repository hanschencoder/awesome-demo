#include <jni.h>
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