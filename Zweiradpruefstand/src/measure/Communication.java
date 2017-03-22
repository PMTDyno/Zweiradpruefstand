package measure;

import data.Data;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import logging.Logger;

/**
 *
 * @author Levin Messing (meslem12@htl-kaindorf.ac.at)
 */
public class Communication
{

  private static final Data DATA = Data.getInstance();
  private static final Logger LOG = Logger.getLogger(Communication.class.getName());

  //private static final int TIMEOUT = 100;
  //private static final TimeUnit TIMOUT_UNIT = TimeUnit.MILLISECONDS;
  private static final int TIMEOUT = 5;
  private static final TimeUnit TIMOUT_UNIT = TimeUnit.SECONDS;

  private boolean connected = false;

  //The ASCII Control Bytes
  public static final byte SOT = 2; //0x02
  public static final byte EOT = 3; //0x03

  private PortCom port;
  private Thread receiveThread;
  private final LinkedList<Frame> receivedFrameList = new LinkedList<>();
  private long refreshLock = 0;

  public enum Request
  {
    REFRESH,
    MEASURE,
    START,
    MEASURENORPM,
    STARTNORPM
  }

  public Communication()
  {
    LOG.setLevel(Level.ALL);
    port = new PortCom();
  }

  public void connect(String serialPort) throws CommunicationException,
                                                TimeoutException
  {
    try
    {
      port.openPort(serialPort);

      receiveThread = new Thread(getFrame);
      receiveThread.start();

      //sleep here?
      refreshEco();
      connected = true;
    }
    catch (CommunicationException | TimeoutException ex)
    {
      throw ex;
    }
    catch (Exception ex)
    {
      throw new CommunicationException(ex);
    }

  }

  public String getPort()
  {
    return port.getPort();
  }

  /**
   *
   * @return The captured and already checked Frame Data
   * @throws CommunicationException if an error occured
   * @throws TimeoutException       if a Timeout occured
   */
  public String[] getFrameData() throws CommunicationException, TimeoutException
  {
    try
    {
      return readFrame(TIMEOUT, TIMOUT_UNIT).getData().split(":");
    }
    catch (CommunicationException ex)
    {
      LOG.warning(ex);
    }
    catch (TimeoutException ex)
    {
      throw ex;
    }
    catch (InterruptedException ex)
    {
    }

    throw new CommunicationException("received wrong frames");
  }

  /**
   *
   * @param timeout
   * @param unit
   * @return
   * @throws CommunicationException
   * @throws TimeoutException
   * @throws InterruptedException
   */
  public Frame readFrame(long timeout, TimeUnit unit)
          throws CommunicationException, TimeoutException,
                 InterruptedException
  {
    synchronized (receivedFrameList)
    {
      long startTimeMillis = System.currentTimeMillis();
      while(true)
      {
        if(!receivedFrameList.isEmpty())
        {
          return receivedFrameList.removeFirst();
        }

        long to = startTimeMillis + unit.toMillis(timeout)
                - System.currentTimeMillis();
        if(to <= 0)
        {
          throw new TimeoutException();
        }
        //is the following good practise?
        //probably not.., why not?
        try
        {
          receivedFrameList.wait(to);
        }
        catch (InterruptedException ex)
        {
        }
      }
    }

  }

  public String[] getAvailablePorts()
  {
    return port.getPortList();
  }

  public void disconnect() throws CommunicationException
  {
    try
    {
      if(!connected || port == null)
        return;

      port.closePort();
      connected = false;
      LOG.info("Port disconnected");

    }
    catch (Exception ex)
    {
      throw new CommunicationException(ex);
    }
  }

  public boolean isConnected()
  {
    return connected;
  }

  public void refreshEco() throws CommunicationException, TimeoutException,
                                  IllegalStateException
  {
    try
    {
      //every 4 seconds max!
      if(refreshLock + 4000 < System.currentTimeMillis())
      {
        setEco(getResponse(Request.REFRESH));
        refreshLock = System.currentTimeMillis();
      }
      else
        throw new IllegalStateException("refreshEco blocked");
    }
    catch (TimeoutException | IllegalStateException ex)
    {
      throw ex;
    }
    catch (Exception ex)
    {
      throw new CommunicationException(ex);
    }
  }

  public boolean isOpened()
  {
    return port.isOpened();
  }

  public String[] getResponse(Request request) throws
          CommunicationException, TimeoutException
  {
    for(int i = 0; i < 3; i++)
    {
      try
      {
        sendFrame(request);
        return getFrameData();
      }
      catch (TimeoutException ex)
      {
        LOG.warning((i + 1) + ". Timeout");
      }
      catch (Exception ex)
      {
        throw ex;
      }
    }
    throw new TimeoutException("Timeout");
  }

  /**
   * only accepts request, start and measure as parameter
   *
   * @param request refresh, start or measure
   * @throws CommunicationException
   * @throws TimeoutException
   *
   */
  public void sendFrame(Request request) throws CommunicationException,
                                                TimeoutException,
                                                IllegalArgumentException
  {
    if(port == null)
    {
      throw new CommunicationException("port not initialized!");
    }
    if(!isOpened())
    {
      throw new CommunicationException("port not open!");
    }

    ByteArrayOutputStream baos = new ByteArrayOutputStream();

    try
    {
      //build frame
      baos.write(SOT);
      switch (request)
      {
        case REFRESH:
          baos.write("e".getBytes("UTF-8"));
          break;
        case START:
          baos.write("s".getBytes("UTF-8"));
          break;
        case MEASURE:
          baos.write("m".getBytes("UTF-8"));
          break;
        case STARTNORPM:
          baos.write("g".getBytes("UTF-8"));
          break;
        case MEASURENORPM:
          baos.write("r".getBytes("UTF-8"));
          break;
        default:
          LOG.severe("FATAL ERROR");
      }
      baos.write(EOT);

      port.writeBytes(baos.toByteArray());

      LOG.fine("Frame written: " + baos.toString());

    }
    catch (NullPointerException ex)
    {
      ex.printStackTrace(System.err);
    }
    catch (IOException ex)
    {
      throw new CommunicationException("Error converting data string to bytes");
    }
    catch (Exception ex)
    {
      throw new CommunicationException(ex);
    }
  }

  /**
   * converts the data string to 3 ecosystem variables and sets them in the
   * data.data class
   *
   * @param data the string to be converted and set
   */
  public void setEco(String[] data)
  {

    double temperature = Double.parseDouble(data[0]);
    int humidity = Integer.parseInt(data[1]);
    double pressure = Double.parseDouble(data[2]) / 100;

//        System.out.println(temperature);
//        System.out.println(humidity);
//        System.out.println(pressure);
    if(temperature < (-100) || temperature > 100
            || humidity < 0 || humidity > 100
            || pressure < 0 || pressure > 2000)
    {
      throw new IllegalStateException();
    }

    DATA.setTemperature(temperature);
    DATA.setHumidity(humidity);
    DATA.setPressure(pressure);

  }

  //Runnable or Thread?, better would be to use modern kind of
  // multithreading -> executors
  private Runnable getFrame = new Runnable()
  {

    @Override
    public void run()
    {
      try
      {
        FrameBytes frame = null;
        while(true)
        {
          byte b = port.readByte();
          //System.out.println(b);
          switch (b)
          {

            //ignore \n and \r
            case 10:
              break;
            case 13:
              break;

            case SOT:
              frame = new FrameBytes();
              frame.update(b);
              break;

            case EOT:
              if(frame != null)
              {
                frame.update(b);
                LOG.info(String.format("Frame received: %s",
                                       new String(frame.getFrameBytes(), "utf-8")));
                
                synchronized (receivedFrameList)
                {
                  receivedFrameList.add(new Frame(frame));
                  receivedFrameList.notifyAll();
                  frame = null;
                }
              }
              else
              {
                LOG.warning("missing C_START");
              }
              break;

            default:
              if(frame != null)
              {
                frame.update(b);
              }
              else
              {
                LOG.warning("missing C_START " + String.format("'%c'", b));
              }
          }
        }
      }
      catch (CommunicationException ex)
      {
//        ex.printStackTrace(System.err);
        LOG.warning(ex.getMessage());
      }
      catch (Exception ex)
      {
        LOG.warning(ex);
      }
    }

  };

}
