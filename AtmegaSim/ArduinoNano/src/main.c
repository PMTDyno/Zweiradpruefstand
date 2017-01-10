//***********************************************************************
// AIIT Template Level 3
// ----------------------------------------------------------------------
// Description:
//   UART-Support, Timer, Task-System, 7-Segment-Support, LCD-Support
// ----------------------------------------------------------------------
// Created: Aug 23, 2016 (SX)
//***********************************************************************

#include "global.h"

#include <stdio.h>
#include <string.h>

#include <avr/io.h>
#include <avr/interrupt.h>
#include <util/delay.h>

#include "sys.h"
#include "app.h"

// defines
// ...

// declarations and definations
// ...

// constants located in program flash and SRAM
const char MAIN_WELCOME[] = "\n\rProgramm ?";
const char MAIN_DATE[] = __DATE__;
const char MAIN_TIME[] = __TIME__;


int main (void)
{
  sys_init();
  app_init();
  printf("%s %s %s\n\r", MAIN_WELCOME, MAIN_DATE, MAIN_TIME);

#ifdef GLOBAL_SURE_LCD
  printf("LCD ");
  if (sys.lcd.status==1)
  {
    printf("detected and ready to use\n");
    sys_lcd_putString("?? - ");
    sys_lcd_putString(MAIN_TIME);
  }
  else
    printf("not ready (status=%d)\n", sys.lcd.status);
#endif // GLOBAL_SURE_LCD

  sys_newline();

  // enable interrupt system
  sei();

  while (1)
  {
    sys_main();
    app_main();
  }
  return 0;
}
