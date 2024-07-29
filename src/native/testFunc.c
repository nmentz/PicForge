#include <jni.h>
#include <stdio.h>      // C Standard IO Header
#include "HelloJNI.h"   // Generated


JNIEXPORT jint JNICALL Java_HelloJNI_CppReturnThree(JNIEnv *env, jobject thisObj, jint number) {
   return number;
}