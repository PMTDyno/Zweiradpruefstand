#ifndef APP_H_INCLUDED
#define APP_H_INCLUDED

// declarations

struct App
{
  uint8_t flags_u8;
};

extern volatile struct App app;


// defines

#define APP_EVENT_0   0x01
#define APP_EVENT_1   0x02
#define APP_EVENT_2   0x04
#define APP_EVENT_3   0x08
#define APP_EVENT_4   0x10
#define APP_EVENT_5   0x20
#define APP_EVENT_6   0x40
#define APP_EVENT_7   0x80


// functions

void app_init (void);
void app_main (void);

void app_task_1ms   (void);
void app_task_2ms   (void);
void app_task_4ms   (void);
void app_task_8ms   (void);
void app_task_16ms  (void);
void app_task_32ms  (void);
void app_task_64ms  (void);
void app_task_128ms (void);

void app_timer0_ovf (void);
void app_timer1_ovf (void);
void app_timer2_ovf (void);

void app_uart_isr (uint8_t b);

#endif // APP_H_INCLUDED
