#include "sys_sim.h"
#include <unistd.h>

__pid_t sys_pid ()
{
  return getpid(); 
}


Sys_Event sys_setEvent (Sys_Event event)
{
  return 0;  
}

Sys_Event sys_clearEvent (Sys_Event event)
{
  return 0;  
}

Sys_Event sys_isEventPending (Sys_Event event)
{
  return 0;  
}

