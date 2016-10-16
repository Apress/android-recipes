LOCAL_PATH := ./jni
   
include $(CLEAR_VARS)  
   
LOCAL_MODULE    := NDKGreetings  
LOCAL_SRC_FILES := NDKGreetings.c  
   
include $(BUILD_SHARED_LIBRARY)
