package measure;

import data.Data;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javax.swing.SwingWorker;
import logging.Logger;


/**
 *
 * @author Levin Messing <meslem12@htl-kaindorf.ac.at>
 */
public class Communication
{

  private static final Data DATA = Data.getInstance();
  private static final Logger LOG = Logger.getLogger(Communication.class.getName());

  //private static final int TIMEOUT = 100;
  //private static final TimeUnit TIMOUT_UNIT = TimeUnit.MILLISECONDS;
  private static final int TIMEOUT = 5;
  private static final TimeUnit TIMOUT_UNIT = TimeUnit.SECONDS;

  private boolean success = false;
  private static boolean connected = false;

  private SwingWorker worker = null;

  //The ASCII Control Bytes
  public static final byte SOT = 2; //0x02
  public static final byte EOT = 3; //0x03

  public final LinkedList<Frame> receivedFrames = new LinkedList<>();

  private Port port;
  private Thread receiveThread;
  private final LinkedList<Frame> receivedFrameList = new LinkedList<>();

  public Communication ()
  {
    //false == port
    //true == simulation
    if (true)
    {
      port = new PortSim();
      //((PortSim) port).setMode(PortSim.SIM_MODE.NORMAL);
    }
    else
    {
      port = new PortCom();
    }

  }

  public void init (String serialPort) throws CommunicationException, TimeoutException
  {
    try
    {
      port.openPort(serialPort);

      receiveThread = new Thread(getFrame);
      receiveThread.start();

      refreshEco();
      connected = true;
    }
    catch (CommunicationException | TimeoutException ex)
    {
      throw ex;
    }
    catch (Exception ex)
    {
      port = null;
      throw new CommunicationException(ex);
    }

  }


  public String getPort ()
  {
    return port.getPort();
  }


  /**
   *
   * @return The captured and already checked Frame Data
   * @throws CommunicationException
   * @throws TimeoutException
   */
  public String[] getFrameData () throws CommunicationException, TimeoutException
  {
    for (int i = 0; i < 3; i++)
    {
      try
      {
        return readFrame(TIMEOUT, TIMOUT_UNIT).getData().split("-", 3);
      }
      catch (CommunicationException ex)
      {
        LOG.warning(ex);
      }
      catch (TimeoutException ex)
      {
        if (i == 2)
        {
          throw ex;
        }
        LOG.fine(String.format("%s. Timeout", i + 1));
      }
      catch (InterruptedException ex)
      {
      }
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
  public Frame readFrame (long timeout, TimeUnit unit)
          throws CommunicationException, TimeoutException,
                 InterruptedException
  {
    synchronized (receivedFrameList)
    {
      long startTimeMillis = System.currentTimeMillis();
      while (true)
      {
        if (!receivedFrameList.isEmpty())
        {
          return receivedFrameList.removeFirst();
        }

        long to = startTimeMillis + unit.toMillis(timeout)
                - System.currentTimeMillis();
        if (to <= 0)
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
          LOG.warning(ex);
        }
      }
    }

  }


  public String[] getAvailablePorts ()
  {
              
    System.out.print("in getAvailablePorts()");
     
    return port.getPortList();
  }


  public void disconnect () throws CommunicationException
  {
    try
    {
      if (port != null)
      {
        port.closePort();
        connected = false;
      }
      else
      {
        throw new CommunicationException("closing Port failed");
      }
    }
    catch (Exception ex)
    {
      throw new CommunicationException(ex);
    }
  }


  public boolean isConnected ()
  {
    return connected;
  }


  public void refreshEco () throws CommunicationException, TimeoutException
  {

    try
    {
      sendFrame("refresh");
      setEco(getFrameData());
    }
    catch (Exception ex)
    {
      throw new CommunicationException(ex);
    }
  }


  public boolean isSuccess ()
  {
    return success;
  }


  /**
   * set to true if the measurement finished succesfully
   *
   * @param success
   */
  public void setSuccess (boolean success)
  {
    this.success = success;
  }

public boolean isOpened ()
  {
    return port.isOpened();
  }
  
  /**
   * only accepts request, start and measure as parameter
   *
   * @param data request, start or measure
   * @throws CommunicationException
   * @throws TimeoutException
   *
   */
  public void sendFrame (String data) throws CommunicationException,
                                             TimeoutException, IllegalArgumentException
  {
    if(port == null)
      throw new CommunicationException("port == null in sendFrame()");
    
    ByteArrayOutputStream baos = new ByteArrayOutputStream();

    //build frame
    if (!data.equals("refresh") && !data.equals("start") && !data.equals("measure"))
    {
      throw new IllegalArgumentException();
    }

    try
    {
      baos.write(SOT);
      baos.write(data.getBytes());
      baos.write(EOT);
      
      //send frame max 3 times
      for (int i = 0; i < 3; i++)
      {
        port.writeBytes(baos.toByteArray());
        LOG.info("Frame written: %s", baos.toString());
      }
    }
    catch(NullPointerException ex)
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
   * converts the data string to 3 ecosystem variables and sets them in the data.data class
   *
   * @param data the string to be converted and set
   */
  public void setEco (String[] data)
  {

    double temperature = Double.parseDouble(data[0]);
    int humidity = Integer.parseInt(data[1]);
    double pressure = Double.parseDouble(data[2]);

//        System.out.println(temperature);
//        System.out.println(humidity);
//        System.out.println(pressure);
    if (temperature < (-100) || temperature > 100
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
    public void run ()
    {
      try
      {
        FrameBytes frame = null;
        while (true)
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
              if (frame != null)
              {
                frame.update(b);
                LOG.info(String.format("Frame received: %s", new String(frame.getFrameBytes(), "utf-8")));
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
              if (frame != null)
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
        ex.printStackTrace(System.err);
        LOG.warning(ex.getMessage());
      }
      catch (Exception ex)
      {
        LOG.warning(ex);
      }
    }

  };


  public void cancelWorker ()
  {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }


  public void stopWorker ()
  {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }


  public void setWorker (Object object)
  {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }


  public void start (Object object)
  {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

}
