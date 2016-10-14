#include <jni.h>
#include <stdio.h>
#include<malloc.h>
#include <pthread.h>
#include <unistd.h>
#include <android/log.h>

#define LOG_TAG    "AndroidJni"
#define log(...)  __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
#define REQUEST_VERSION JNI_VERSION_1_2

static JavaVM *javaVM = NULL;

/**
 * 这里有个需要注意的地方，如果在native代码中新启动一个线程，如通过pthread_create新建线程
 * 因为application没有该线程的栈帧，所以在新的线程中调用FindClass方法，那么VM会从系统的类加载器
 * 而不是应用的类加载器开始搜索相关类去加载。所以应用自定义的类是找不到的
 *
 * 为了解决这个问题，我们在JNI_OnLoad中对需要使用的类进行缓存
 *
 */
static jclass nativeApiRef = NULL;

/**
 * native library 加载的时候被调用，返回值为当前library依赖的最低环境
 */
JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *vm, void *reserved) {
    log("JNI_OnLoad");
    javaVM = vm;

    JNIEnv *env = NULL;
    (*vm)->GetEnv(vm, (void **) &env, REQUEST_VERSION);
    jclass clazz = (*env)->FindClass(env, "com/hanschen/androidjni/jni/NativeApi");
    nativeApiRef = (*env)->NewGlobalRef(env, clazz);
    return REQUEST_VERSION;
}

JNIEXPORT void JNICALL JNI_OnUnload(JavaVM *vm, void *reserved) {
    log("JNI_OnUnload");
}


static void callJavaMethod(JNIEnv *env, jstring message) {

    jmethodID methodId = (*env)->GetStaticMethodID(env, nativeApiRef, "onCall",
                                                   "(Ljava/lang/String;)V");
    jthrowable throwable = (*env)->ExceptionOccurred(env);
    if (throwable == NULL) {
        (*env)->CallStaticVoidMethod(env, nativeApiRef, methodId, message);
    } else {
        (*env)->ExceptionDescribe(env);
        (*env)->ExceptionClear(env);
    }
}


JNIEXPORT jint JNICALL
Java_com_hanschen_androidjni_jni_NativeApi_getJniVersion(JNIEnv *env, jobject instance) {

    return (*env)->GetVersion(env);
}


JNIEXPORT jstring JNICALL
Java_com_hanschen_androidjni_jni_NativeApi_getStringFromNativeWorld(JNIEnv *env,
                                                                    jobject instance) {
    jstring returnValue = (*env)->NewStringUTF(env, "Hello, this is string from native world");
    return returnValue;
}

JNIEXPORT jstring JNICALL
Java_com_hanschen_androidjni_jni_NativeApi_getStringInfo(JNIEnv *env, jobject instance,
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
Java_com_hanschen_androidjni_jni_NativeApi_callUserInfoMethod(JNIEnv *env, jobject instance,
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

JNIEXPORT void JNICALL
Java_com_hanschen_androidjni_jni_NativeApi_callSuperClassMethod(JNIEnv *env, jobject instance,
                                                                jobject child) {

    jclass clazz = (*env)->GetObjectClass(env, child);
    jclass superClazz = (*env)->GetSuperclass(env, clazz);
    jmethodID methodId = (*env)->GetMethodID(env, superClazz, "toString", "()Ljava/lang/String;");
    jstring result = (*env)->CallNonvirtualObjectMethod(env, child, superClazz, methodId);

    const char *temp = (*env)->GetStringUTFChars(env, result, NULL);
    log("toString:%s", temp);
    (*env)->ReleaseStringUTFChars(env, result, temp);
}

JNIEXPORT jclass JNICALL
Java_com_hanschen_androidjni_jni_NativeApi_getSuperClass(JNIEnv *env, jobject instance,
                                                         jclass clazz) {

    jclass super = (*env)->GetSuperclass(env, clazz);
    if (super == NULL) {
        jclass throwClazz = (*env)->FindClass(env, "java/lang/ClassNotFoundException");
        (*env)->ThrowNew(env, throwClazz, "找不到该类的父类");
        return NULL;
    }
    return super;
}

JNIEXPORT jobject JNICALL
Java_com_hanschen_androidjni_jni_NativeApi_newUserInfo(JNIEnv *env, jobject instance,
                                                       jstring username_, jstring password_) {
    char newUsernameBuf[20] = "";
    char newPasswordBuf[20] = "";

    const char *username = (*env)->GetStringUTFChars(env, username_, 0);
    const char *password = (*env)->GetStringUTFChars(env, password_, 0);
    sprintf(newUsernameBuf, "native:%s", username);
    sprintf(newPasswordBuf, "native:%s", password);
    (*env)->ReleaseStringUTFChars(env, username_, username);
    (*env)->ReleaseStringUTFChars(env, password_, password);

    jstring newUsername = (*env)->NewStringUTF(env, newUsernameBuf);
    jstring newPassword = (*env)->NewStringUTF(env, newPasswordBuf);

    jclass clazz = (*env)->FindClass(env, "com/hanschen/androidjni/bean/UserInfo");
    jmethodID methodId = (*env)->GetMethodID(env, clazz, "<init>",
                                             "(Ljava/lang/String;Ljava/lang/String;)V");
    return (*env)->NewObject(env, clazz, methodId, newUsername, newPassword);
}

JNIEXPORT void JNICALL
Java_com_hanschen_androidjni_jni_NativeApi_changeField(JNIEnv *env, jobject instance,
                                                       jobject userInfo) {

    jclass clazz = (*env)->GetObjectClass(env, userInfo);
    jfieldID fieldId = (*env)->GetFieldID(env, clazz, "username", "Ljava/lang/String;");

    char newUsernameBuf[20] = "";
    jstring username = (*env)->GetObjectField(env, userInfo, fieldId);
    const char *usernameBuf = (*env)->GetStringUTFChars(env, username, NULL);
    sprintf(newUsernameBuf, "~~~ %s ~~~", usernameBuf);
    (*env)->ReleaseStringUTFChars(env, username, usernameBuf);

    jstring newUsername = (*env)->NewStringUTF(env, newUsernameBuf);
    (*env)->SetObjectField(env, userInfo, fieldId, newUsername);
}

JNIEXPORT void JNICALL
Java_com_hanschen_androidjni_jni_NativeApi_reverseArray(JNIEnv *env, jobject instance,
                                                        jintArray array_) {
    jint length = (*env)->GetArrayLength(env, array_);

    jint tmp;
    jint *array = (*env)->GetIntArrayElements(env, array_, NULL);
    for (int i = 0; i < length / 2; i++) {
        tmp = array[i];
        array[i] = array[length - 1 - i];
        array[length - 1 - i] = tmp;
    }

    //0:提交修改并且释放array
    (*env)->ReleaseIntArrayElements(env, array_, array, 0);
}

/**
 * 该线程如果要使用JNIEnv，需要执行AttachCurrentThread，并在线程结束前调用DetachCurrentThread
 */
static void thread(void) {

    JNIEnv *env;
    (*javaVM)->AttachCurrentThread(javaVM, &env, NULL);

    char message[50] = "";
    for (int i = 0; i < 10; i++) {
        sprintf(message, "[nativeMessage:%d, threadId:%u]", i, gettid());
        callJavaMethod(env, (*env)->NewStringUTF(env, message));
    }

    (*javaVM)->DetachCurrentThread(javaVM);
}

JNIEXPORT void JNICALL
Java_com_hanschen_androidjni_jni_NativeApi_doSomethingBackground(JNIEnv *env, jobject instance) {

    log("create new thread from thread:%u", gettid());

    pthread_t id;
    int ret = pthread_create(&id, NULL, (void *) thread, NULL);
    if (ret != 0) {
        log("create pthread failure!");
    }
}