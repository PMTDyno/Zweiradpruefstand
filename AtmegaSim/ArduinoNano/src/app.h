#ifndef APP_H_INCLUDED
#define APP_H_INCLUDED

// declarations

#define APP_UART_BUFFER_SIZE 64

#define APP_SOT 2
#define APP_EOT 3
#define APP_ACK 6
#define APP_NAK 21
#define APP_GS  29

struct App_Uart
{
  uint8_t recBuffer[APP_UART_BUFFER_SIZE];
  uint8_t txBuffer[APP_UART_BUFFER_SIZE];
  uint8_t recIndex;
  uint8_t framePending;
  uint8_t expectedSn;
  uint8_t errCnt_recFrameTooLong;
  uint8_t errCnt_recFrameWhilePending;
  uint8_t errCnt_recFrameError;
};

struct App_FrameStart 
{
  uint8_t sot;
  uint8_t sn;
  uint8_t data[];
};

struct App_FrameEnd 
{
  uint8_t gs;
  uint8_t crc[4];
  uint8_t eot;
};


struct App
{
  struct App_Uart uart;
};

extern volatile struct App app;


// defines

#define APP_EVENT_FRAME_RECEIVED   0x01
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
