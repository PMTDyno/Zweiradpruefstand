#include "global.h"

#include <stdio.h>
#include <string.h>

#ifndef SIMULATION
#include <avr/io.h>
#include <avr/interrupt.h>
#include <util/delay.h>
#include "sys.h"
#else
#include "sys_sim.h"
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


//--------------------------------------------------------

void app_main (void)
{
  sys_log(__FILE__, __LINE__, sys_pid(), "main()");
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
  sys_log(__FILE__, __LINE__, sys_pid(), "uart_isr(0x%02x)", b);
  sys_printf("%c", b);
}



