//#undef _cplusplus
#include <jni.h>
//#include <string.h>

JNIEXPORT jstring JNICALL
Java_com_example_manguoz_NewItems_getMessage
          (JNIEnv *env, jobject thisObj) {
	jstring hello = "Usinibore. I am NewItems";

	//return (*env)->NewStringUTF(env, "I am the NewItems Activity. "
			//"shambambaaaa " ABI ".");
   //return (*env)->NewStringUTF(env, "Hello from native code!");
	return hello;
}
