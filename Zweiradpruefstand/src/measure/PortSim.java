package measure;


import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedList;
import jni.App;
import logging.Logger;


/**
 *
 * @author Levin Messing (meslem12@htl-kaindorf.ac.at)
 */
public class PortSim implements Port
{
  private static final Logger LOG = Logger.getLogger(PortSim.class.getName());

  private static final jni.App app = new App();
  
  public static enum SIM_MODE
  {
    NORMAL,
    DELAYED,
    NOTHING;
  }

  private final LinkedList<PortChunk> receivedChunks = new LinkedList<>();
  private SIM_MODE mode = SIM_MODE.NORMAL;
  private String port;
  private final String [] availablePorts = { "SIM-NORMAL" };
  private JniAppThread jniAppThread;

  public PortSim ()
  {
  }

  @Override
  public boolean isOpened ()
  {
    return port != null;
  }


  @Override
  public String[] getPortList ()
  {
    return availablePorts;
  }


  @Override
  public String getPort ()
  {
    return port;
  }


  @Override
  public void openPort (String port) throws CommunicationException
  {
    if (this.port != null)
      throw new CommunicationException("Port " + port + " already open");
    for (SIM_MODE m : SIM_MODE.values())
    {
      if (port.endsWith(m.name()))
      {
        app.setOut(new OutputStream() {

          @Override
          public void write (int b) throws IOException
          {
            receiveByte(b);
          }
          
        });
        app.init();
        jniAppThread = new JniAppThread();
        jniAppThread.start();
        this.port = port;
        mode = m;
        LOG.fine("PortSim: openPort(%s)", port);
        return;
      }
    }
    throw new IllegalArgumentException("port " + port + " not supported");
  }

  
  @Override
  public void closePort () throws CommunicationException
  {
    if (this.port == null)
      throw new CommunicationException("no port open");
    LOG.fine("PortSim: closePort()");
    this.port = null;
    if (jniAppThread != null)
    {
      jniAppThread.interrupt();
      jniAppThread = null;
    }
      
  }


  private void receiveByte (int b)
  {
    synchronized (receivedChunks)
    {
      //LOG.info("PORTSIM: add chunk byte %02x", b);
      receivedChunks.add(new PortChunk(b));
      receivedChunks.notifyAll();
    }
  }
  

  @Override
  public void writeBytes (byte [] data) throws CommunicationException
  {
    if (!isOpened())
      throw new CommunicationException("Port not open, cannot write bytes");
    LOG.finer(data, "PortSim: writebytes(byte []) [mode=%s]", mode.name());
    try
    {
      for (byte b : data)
        app.uart_isr(b);
    }
    catch (Throwable th)
    {
      LOG.warning(th);
      throw new CommunicationException(th);
    }
  }


  @Override
  public byte readByte () throws CommunicationException, InterruptedException
  {
    synchronized (receivedChunks)
    {
      try
      {
        while (receivedChunks.isEmpty())
        {
          receivedChunks.wait();
        }

        final PortChunk ch = receivedChunks.getFirst();

        final byte rv = ch.next();
        if (!ch.isByteAvailable())
        {  
          //LOG.info("PORTSIM: remove chunk byte %02x", rv);
          receivedChunks.removeFirst();
        }
        return rv;
      }
      catch (InterruptedException ex)
      {
        throw ex;
      }
      catch (Exception ex)
      {
        throw new CommunicationException(ex);
      }
    }
  }


  private class JniAppThread extends Thread
  {

    @Override
    public void run ()
    {
      while (!Thread.currentThread().isInterrupted())
      {
        try
        {
          Thread.sleep(1);
          app.main();
        }
        catch (InterruptedException ex) {}
        catch (Throwable th)
        {
          LOG.warning(th);
        }
      }
    }
    
  }

}