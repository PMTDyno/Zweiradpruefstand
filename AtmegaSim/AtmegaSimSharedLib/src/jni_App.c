#define _GNU_SOURCE

#include <jni.h>
#include <stdio.h>
#include "jni_App.h"
#include <stdarg.h>
#include <unistd.h>
#include <stdint.h>

#include "app.h" // is a link to the Atmega Project 


JNIEXPORT jstring JNICALL Java_jni_App_nativeVersion (JNIEnv *env, jobject obj)
{
   char nativeVersion[] = "1.0";
   jstring result = (*env)->NewStringUTF(env, nativeVersion); 
   return result;   
}

struct jni_App_Cookie {
  jobject obj;
  jmethodID mid;
};

struct jni_App_Globals {
  JNIEnv *env;
  struct jni_App_Cookie out;
  struct jni_App_Cookie log;
} jni_app_globals = { NULL, { NULL, NULL }, {NULL, NULL } };

void jni_App_setGlobals (JNIEnv *env, jobject obj)
{
  // http://docs.oracle.com/javase/8/docs/technotes/guides/jni/spec/functions.html
  jni_app_globals.env = env;
  jclass cls = (*env)->GetObjectClass(env, obj);

  jfieldID fid = (*env)->GetFieldID(env, cls, "out", "Ljava/io/OutputStream;");
  jni_app_globals.out.obj = (fid == 0) ? NULL : (*env)->GetObjectField(env, obj, fid);
  if (jni_app_globals.out.obj != NULL) {
    jclass clasz = (*env)->GetObjectClass(env, jni_app_globals.out.obj);
    jni_app_globals.out.mid = (*env)->GetMethodID(env, clasz, "write", "(I)V");
  }

  fid = (*env)->GetFieldID(env, cls, "log", "Ljava/io/OutputStream;");
  jni_app_globals.log.obj = (fid == 0) ? NULL : (*env)->GetObjectField(env, obj, fid);
  if (jni_app_globals.log.obj != NULL)
  {
    jclass clasz = (*env)->GetObjectClass(env, jni_app_globals.log.obj);
    jni_app_globals.log.mid = (*env)->GetMethodID(env, clasz, "write", "(I)V");
  }
}



int noop(void) { return 0; }


int my_writefn (struct jni_App_Cookie *cookie, const char *data, int n) 
{
//  printf("my_writefn %08x\n", cookie);
  if (cookie == NULL || jni_app_globals.env == NULL || cookie->obj == NULL)
    return 0;
  jvalue jargs [1];
  while (n-- > 0)
  {
    jargs[0].i = (int)*data++;
    (*jni_app_globals.env)->CallIntMethodA(jni_app_globals.env, cookie->obj, cookie->mid, jargs);
  }
  return n;
}

int my_closefn (struct jni_App_Cookie *cookie) 
{
  if (cookie == NULL || jni_app_globals.env == NULL || cookie->obj == NULL)
    return 0;

  //printf("close %08x\n", cookie);
  jvalue jargs [1];
  jargs[0].i = 0;
  (*jni_app_globals.env)->CallIntMethodA(jni_app_globals.env, cookie->obj, cookie->mid, jargs);
  return 0;
}

cookie_io_functions_t my_fns = {
  (void*) noop,        // read
  (void*) my_writefn,  // write
  (void*) noop,        // seek
  (void*) my_closefn   // close
};

void sys_log (const char *fname, int line, int pid, const char *format, ...)
{
  if (jni_app_globals.log.obj == NULL)
    return;
  
  FILE *f = fopencookie(&jni_app_globals.log, "w", my_fns);
  va_list args;
  va_start(args, format);
  fprintf(f, "%s:%d: (PID %d) ", fname, line, pid);
  vfprintf(f, format, args);
  va_end(args);
  fclose(f);
}

void sys_printf (const char *format, ...)
{
  if (jni_app_globals.out.obj == NULL)
    return;
  FILE *f = fopencookie(&jni_app_globals.out, "w", my_fns);
  va_list args;
  va_start(args, format);
  vfprintf(f, format, args);
  va_end(args);
  fclose(f);
}

JNIEXPORT void JNICALL Java_jni_App_init (JNIEnv *env, jobject obj)
{
  jni_App_setGlobals(env, obj);
  sys_log(__FILE__, __LINE__, getpid(), "app_init()");
  app_init();
}

JNIEXPORT void JNICALL Java_jni_App_main (JNIEnv *env, jobject obj)
{
  jni_App_setGlobals(env, obj);
  sys_log(__FILE__, __LINE__, getpid(), "app_main()");
  app_main();
}

JNIEXPORT void JNICALL Java_jni_App_task_11ms (JNIEnv *env, jobject obj)
{
  jni_App_setGlobals(env, obj);
  sys_log(__FILE__, __LINE__, getpid(), "app_task_1ms()");
  app_task_1ms();
}

JNIEXPORT void JNICALL Java_jni_App_task_12ms (JNIEnv *env, jobject obj)
{
  jni_App_setGlobals(env, obj);
  sys_log(__FILE__, __LINE__, getpid(), "app_task_2ms()");
  app_task_2ms();
}

JNIEXPORT void JNICALL Java_jni_App_task_14ms (JNIEnv *env, jobject obj)
{
  jni_App_setGlobals(env, obj);
  sys_log(__FILE__, __LINE__, getpid(), "app_task_4ms()");
  app_task_4ms();
}

JNIEXPORT void JNICALL Java_jni_App_task_18ms (JNIEnv *env, jobject obj)
{
  jni_App_setGlobals(env, obj);
  sys_log(__FILE__, __LINE__, getpid(), "app_task_8ms()");
  app_task_8ms();
}

JNIEXPORT void JNICALL Java_jni_App_task_116ms (JNIEnv *env, jobject obj)
{
  jni_App_setGlobals(env, obj);
  sys_log(__FILE__, __LINE__, getpid(), "app_task_16ms()");
  app_task_16ms();
}

JNIEXPORT void JNICALL Java_jni_App_task_132ms (JNIEnv *env, jobject obj)
{
  jni_App_setGlobals(env, obj);
  sys_log(__FILE__, __LINE__, getpid(), "app_task_32ms()");
  app_task_32ms();
}

JNIEXPORT void JNICALL Java_jni_App_task_164ms (JNIEnv *env, jobject obj)
{
  jni_App_setGlobals(env, obj);
  sys_log(__FILE__, __LINE__, getpid(), "app_task_64ms()");
  app_task_64ms();
}

JNIEXPORT void JNICALL Java_jni_App_task_1128ms (JNIEnv *env, jobject obj)
{
  jni_App_setGlobals(env, obj);
  sys_log(__FILE__, __LINE__, getpid(), "app_task_128ms()");
  app_task_128ms();
}

JNIEXPORT void JNICALL Java_jni_App_timer0_1ovf (JNIEnv *env, jobject obj)
{
  jni_App_setGlobals(env, obj);
  sys_log(__FILE__, __LINE__, getpid(), "app_timer0_ovf()");
  app_timer0_ovf();
}

JNIEXPORT void JNICALL Java_jni_App_timer1_1ovf (JNIEnv *env, jobject obj)
{
  jni_App_setGlobals(env, obj);
  sys_log(__FILE__, __LINE__, getpid(), "app_timer1_ovf()");
  app_timer1_ovf();
}

JNIEXPORT void JNICALL Java_jni_App_timer2_1ovf (JNIEnv *env, jobject obj)
{
  jni_App_setGlobals(env, obj);
  sys_log(__FILE__, __LINE__, getpid(), "app_timer2_ovf()");
  app_timer2_ovf();
}

JNIEXPORT void JNICALL Java_jni_App_uart_1isr (JNIEnv *env, jobject obj, jbyte byte)
{
  jni_App_setGlobals(env, obj);
  sys_log(__FILE__, __LINE__, getpid(), "app_uart_isr(0x%02x)", (unsigned char)byte);
  app_uart_isr((uint8_t) byte);
}
