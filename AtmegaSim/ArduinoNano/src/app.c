#include "global.h"

#include <stdio.h>
#include <string.h>

#ifndef SIMULATION
#include <avr/io.h>
#include <avr/interrupt.h>
#include <util/delay.h>
#include "sys.h"
#else
#include "../../AtmegaSimSharedLib/src/sys_sim.h"
#endif
#include "app.h"


// defines
// ...


// declarations and definations

volatile struct App app;


// functions

void app_init (void)
{
  sys_log(__FILE__, __LINE__, sys_pid(), "init() [%s %s]", __DATE__, __TIME__);
  memset((void *)&app, 0, sizeof(app));
}

void app_incErrCnt (volatile uint8_t *cnt)
{
  if ((*cnt) < 255)
    (*cnt)++;
}

//--------------------------------------------------------

void app_main (void)
{
  //sys_log(__FILE__, __LINE__, sys_pid(), "main()");
  if (sys_clearEvent(APP_EVENT_FRAME_RECEIVED))
  {
    app.uart.txBuffer[0] = app.uart.recBuffer[0];  // SOT
    app.uart.txBuffer[1] = app.uart.recBuffer[1];  // Package Number
    app.uart.txBuffer[2] = APP_ACK;
    app.uart.txBuffer[3] = 'O';
    app.uart.txBuffer[4] = 'K';
    app.uart.txBuffer[5] = APP_GS;
    app.uart.txBuffer[6] = '0';
    app.uart.txBuffer[7] = '0';
    app.uart.txBuffer[8] = '0';
    app.uart.txBuffer[9] = '0';
    app.uart.txBuffer[10] = APP_EOT;
    sys_printf((char *)app.uart.txBuffer);
    sys_log(__FILE__, __LINE__, sys_pid(), "%d bytes Response sent", 11);
    cli();
    app.uart.framePending = 0;
    app.uart.recIndex = 0;
    sei();
  }
}

//--------------------------------------------------------

void app_task_1ms (void)
{
  sys_log(__FILE__, __LINE__, sys_pid(), "task_1ms()");
}

void app_task_2ms (void)
{
  sys_log(__FILE__, __LINE__, sys_pid(), "task_2ms()");
}

void app_task_4ms (void)
{
  sys_log(__FILE__, __LINE__, sys_pid(), "task_4ms()");
}

void app_task_8ms (void)
{
  sys_log(__FILE__, __LINE__, sys_pid(), "task_8ms()");
}

void app_task_16ms (void)
{
  sys_log(__FILE__, __LINE__, sys_pid(), "task_16ms()");
}

void app_task_32ms (void)
{
  sys_log(__FILE__, __LINE__, sys_pid(), "task_32ms()");
}

void app_task_64ms (void)
{
  sys_log(__FILE__, __LINE__, sys_pid(), "task_64ms()");
}

void app_task_128ms (void)
{
  sys_log(__FILE__, __LINE__, sys_pid(), "task_1128s()");
}

void app_timer0_ovf (void)
{
  sys_log(__FILE__, __LINE__, sys_pid(), "timer0_ovf()");
}

void app_timer1_ovf (void)
{
  sys_log(__FILE__, __LINE__, sys_pid(), "timer1_ovf()");
}

void app_timer2_ovf (void)
{
  sys_log(__FILE__, __LINE__, sys_pid(), "timer2_ovf()");
}

void app_uart_isr (uint8_t b)
{
  //sys_log(__FILE__, __LINE__, sys_pid(), "uart_isr(0x%02x)", b);
  if (app.uart.framePending)
  {
    app_incErrCnt(&app.uart.errCnt_recFrameWhilePending);
    sys_log(__FILE__, __LINE__, sys_pid(), "Error: Received Frame while old frame pending");
    return;
  }
  
  if (app.uart.recIndex == 0 && b != APP_SOT)
      return;

  app.uart.recBuffer[app.uart.recIndex++] = b;
  
  if (b == APP_EOT) 
  {
    app.uart.framePending = 1;
    sys_setEvent(APP_EVENT_FRAME_RECEIVED);
    sys_log(__FILE__, __LINE__, sys_pid(), "Frame with %d bytes received", app.uart.recIndex);
  }
  else if (app.uart.recIndex >= APP_UART_BUFFER_SIZE)
  {
    app.uart.recIndex = 0;
    app_incErrCnt(&app.uart.errCnt_recFrameTooLong);
    sys_log(__FILE__, __LINE__, sys_pid(), "Error: Received Frame too long");
  }
  
  
  //sys_printf("%c", b);
}



