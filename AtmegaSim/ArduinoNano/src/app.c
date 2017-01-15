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

uint8_t app_isHexChar (uint8_t c)
{
  return (c>='0' && c<='9') || (c>='a' && c<='f');
}

//--------------------------------------------------------

void app_main (void)
{
  //sys_log(__FILE__, __LINE__, sys_pid(), "main()");
  if (sys_clearEvent(APP_EVENT_FRAME_RECEIVED))
  {
    struct App_FrameStart *pStart = (struct App_FrameStart *) app.uart.recBuffer;
    struct App_FrameEnd *pEnd = (struct App_FrameEnd *) &app.uart.recBuffer[app.uart.recIndex-6];
    uint8_t i = 0;
    
    if (pStart->sot != APP_SOT || (pStart->sn != '0' && pStart->sn != '1') ||
        pEnd->gs != APP_GS || pEnd->eot != APP_EOT ||
        !app_isHexChar(pEnd->crc[0]) || !app_isHexChar(pEnd->crc[1]) ||
        !app_isHexChar(pEnd->crc[2]) || !app_isHexChar(pEnd->crc[3]))
    {
      // frame error
      sys_log(__FILE__, __LINE__, sys_pid(), "frame error");
      app_incErrCnt(&app.uart.errCnt_recFrameError);
      app.uart.txBuffer[i++] = APP_SOT;
      app.uart.txBuffer[i++] = pStart->sn;
      app.uart.txBuffer[i++] = APP_NAK;
    }
    else if (pStart->sn != (app.uart.expectedSn + '0') && app.uart.txBuffer[0] == APP_SOT)
    {
      // wrong sequence number and last frame in app.uart.txBuffer
      sys_log(__FILE__, __LINE__, sys_pid(), "wrong sequence number, send again last frame");
      sys_printf((char *)app.uart.txBuffer);
      sys_log(__FILE__, __LINE__, sys_pid(), "%d bytes Response sent", strlen((const char *)app.uart.txBuffer));
      cli();
      app.uart.framePending = 0;
      app.uart.recIndex = 0;
      sei();
      return;
    }
    else if (pStart->sn != (app.uart.expectedSn + '0'))
    {
      // wrong sequence number but no frame in app.uart.txBuffer available
      app.uart.txBuffer[i++] = APP_SOT;
      app.uart.txBuffer[i++] = ((app.uart.expectedSn + 1) % 2) + '0';
      app.uart.txBuffer[i++] = APP_ACK;
    }
    else
    {
      // frame ok
      sys_log(__FILE__, __LINE__, sys_pid(), "frame ok", 11);
      app.uart.txBuffer[i++] = APP_SOT;
      app.uart.txBuffer[i++] = pStart->sn;
      app.uart.txBuffer[i++] = APP_ACK;
      app.uart.txBuffer[i++] = 'O';
      app.uart.txBuffer[i++] = 'K';
      app.uart.expectedSn = (app.uart.expectedSn + 1) % 2;
    }
    
    app.uart.txBuffer[i++] = APP_GS;
    app.uart.txBuffer[i++] = '0';
    app.uart.txBuffer[i++] = '0';
    app.uart.txBuffer[i++] = '0';
    app.uart.txBuffer[i++] = '0';
    app.uart.txBuffer[i++] = APP_EOT;
    app.uart.txBuffer[i++] = 0;
    
    sys_printf((char *)app.uart.txBuffer);
    sys_log(__FILE__, __LINE__, sys_pid(), "%d bytes Response sent", i-1);
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



