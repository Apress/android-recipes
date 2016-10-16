// NDKGreetings.c

#include <jni.h>
 
jstring
   Java_com_apress_ndkgreetings_NDKGreetings_getGreetingMessage(JNIEnv* env,
                                                                jobject this)
{
   return (*env)->NewStringUTF(env, "Greetings from the NDK!");
}
