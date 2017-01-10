#ifndef SYS_SIM_H
#define SYS_SIM_H

#ifdef __cplusplus
extern "C" {
#endif

#include <stdint.h>
#include <unistd.h>  
    
typedef uint8_t Sys_Event;

Sys_Event  sys_setEvent             (Sys_Event event);
Sys_Event  sys_clearEvent           (Sys_Event event);
Sys_Event  sys_isEventPending       (Sys_Event event);    

extern void sys_printf (const char *format, ...);
extern void sys_log (char *fileName, int line, __pid_t pid, const char *format, ...);
extern __pid_t sys_pid ();

#define ISR(x) void x ()

#ifdef __cplusplus
}
#endif

#endif /* SYS_SIM_H */

