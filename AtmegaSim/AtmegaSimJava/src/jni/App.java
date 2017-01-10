package jni;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;


/**
 *
 * @author Manfred Steiner<sx@htl-kaindorf.at>
 */
public class App
{
  static {
    //System.loadLibrary("libnativeappsim");
    System.loadLibrary("AtmegaSimSharedLib");
  }
  
  private final OutputStream out, log;  
  
  public native String nativeVersion();
  public native void init ();
  public native void main ();
  public native void task_1ms ();
  public native void task_2ms ();
  public native void task_4ms ();
  public native void task_8ms ();
  public native void task_16ms ();
  public native void task_32ms ();
  public native void task_64ms ();
  public native void task_128ms ();
  public native void timer0_ovf ();
  public native void timer1_ovf ();
  public native void timer2_ovf ();
  public native void uart_isr (byte b);


  public App ()
  {
    out = new OutputStream()
    {
      @Override
      public void write (int b) throws IOException
      {
        System.out.println(String.format(" ---> From Out: %02x", b));
      }
    };

    log = new OutputStream()
    {
      private ByteArrayOutputStream baos;
      
      @Override
      public void write (int b) throws IOException
      {
        if (baos == null)
          baos = new ByteArrayOutputStream(256);
        if (b == 0) 
        {
          System.out.println(new String(baos.toByteArray()));
          baos.reset();
        }
        else
        {
          baos.write(b);
        }
      }
    };
    
  }
  
  
  
  public String version ()
  {
    return "1.0";
  }
  
  public static void test () 
  {
    App app = new App();
    System.out.println("Version: " + app.version() + "  Native: " + app.nativeVersion());
    app.init();
    app.main();
    app.task_1ms();
    app.task_2ms();
    app.task_4ms();
    app.task_8ms();
    app.task_16ms();
    app.task_32ms();
    app.task_64ms();
    app.task_128ms();
    app.timer0_ovf();
    app.timer1_ovf();
    app.timer2_ovf();
    app.uart_isr((byte)0xab);
  }
  
}
