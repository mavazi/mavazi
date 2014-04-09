#include<jni.h>
//#include<string.h>

JNIEXPORT jstring JNICALL

Java_com_example_testjni_MainActivity_stringFromJNI(JNIEnv *env, jobject obj){
	jstring hello = "I am the NewItems Activity.";
	return hello;
	//return (*env)->NewStringUTF(env, "I am the NewItems Activity.");
}
