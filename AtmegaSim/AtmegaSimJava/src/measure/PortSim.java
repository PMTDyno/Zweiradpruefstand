package measure;

import java.util.LinkedList;
import logging.Logger;


/**
 *
 * @author Levin Messing (meslem12@htl-kaindorf.ac.at)
 */
public class PortSim implements Port
{
  private static final Logger LOG = Logger.getLogger(PortSim.class.getName());


  public static enum SIM_MODE
  {
    NORMAL,
    DELAYED,
    NOTHING;
  }

  private final LinkedList<PortChunk> receivedChunks = new LinkedList<>();
  private SIM_MODE mode = SIM_MODE.NORMAL;
  private String port;
  private final String [] availablePorts = { "SIM-NORMAL", "SIM-DELAYED" };

  public PortSim ()
  {
    LOG.fine("test");
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
  }


  void runDelayed (byte[] response)
  {
    new Thread(new Runnable()
    {

      @Override
      public void run ()
      {

        try
        {
          Thread.sleep(150);
        }
        catch (InterruptedException ex)
        {
          LOG.warning(ex);
        }
        LOG.info("chunk received");
        synchronized (receivedChunks)
        {
          receivedChunks.add(new PortChunk(response));
          receivedChunks.notifyAll();
        }
      }

    }
    ).start();
  }


  void runNormal (byte[] response)
  {
    String str = new String(response);
    for (int i = 0; i < 10; i++)
    {

    }
//        synchronized (receivedChunks)
//        {
//            receivedChunks.add(new Chunk(response));
//            receivedChunks.notifyAll();
//        }
  }


  @Override
  public void writeBytes (byte [] b) throws CommunicationException
  {
    LOG.finer(b, "PortSim: writebytes(byte []) [mode=%s]", mode.name());
    
    try
    {
      switch (mode)
      {
        case NORMAL:
          break;
        case DELAYED:
          break;
        case NOTHING:
          break;
        default:
          throw new UnsupportedOperationException("no mode set");
      }
    }
    catch (Exception ex)
    {
      LOG.warning(ex);
      throw new CommunicationException(ex);
    }
  }


  @Override
  public byte readByte () throws CommunicationException, InterruptedException
  {
    synchronized (receivedChunks)
    {
      try
      {
        if (receivedChunks.isEmpty())
        {
          receivedChunks.wait();
        }
        final PortChunk ch = receivedChunks.getFirst();
        final byte rv = ch.next();
        if (!ch.isByteAvailable())
        {
          receivedChunks.removeFirst();
        }
        return rv;
      }
      catch (InterruptedException ex)
      {
        throw new CommunicationException(ex);
      }
    }
  }




}
