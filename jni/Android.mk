LOCAL_PATH := $(call my-dir)
 
include $(CLEAR_VARS)
 
# Here we give our module name and source file(s)
LOCAL_MODULE    := mynguoz
LOCAL_SRC_FILES := mynguoz.c
 
include $(BUILD_SHARED_LIBRARY)