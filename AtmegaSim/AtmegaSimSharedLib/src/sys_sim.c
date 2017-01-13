#include "sys_sim.h"
#include <unistd.h>

uint8_t eventFlag;

__pid_t sys_pid ()
{
  return getpid(); 
}


Sys_Event sys_setEvent (Sys_Event event)
{
  uint8_t eventIsPending = 0;
  
  if (eventFlag & event)
    eventIsPending = 1;
  eventFlag |= event;
  return eventIsPending;
}

Sys_Event sys_clearEvent (Sys_Event event)
{
  uint8_t eventIsPending = 0;

  if (eventFlag & event)
    eventIsPending = 1;
  eventFlag &= ~event;

  return eventIsPending;  
}

Sys_Event sys_isEventPending (Sys_Event event)
{
  return 0;  
}

