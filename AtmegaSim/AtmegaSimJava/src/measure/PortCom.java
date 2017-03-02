package measure;

import java.util.LinkedList;
import java.util.logging.Level;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import jssc.SerialPortList;
import logging.Logger;


/**
 * Several methods to communicate with the serial Port
 *
 * @author Levin Messing (meslem12@htl-kaindorf.ac.at)
 */
public class PortCom implements Port, SerialPortEventListener
{

  private static final Logger LOG = Logger.getLogger(MeasurementWorker.class.getName());

  private SerialPort serialPort;
  private final LinkedList<Chunk> receivedChunks = new LinkedList<>();


  public PortCom ()
  {
    LOG.setLevel(Level.ALL);
  }


  /**
   * @return the String array of available ports
   */
  @Override
  public String[] getPortList ()
  {
    return SerialPortList.getPortNames();
  }


  /**
   * @return the current port
   */
  @Override
  public String getPort ()
  {
    return serialPort.getPortName();
  }


  @Override
  public void openPort (String port) throws CommunicationException
  {
    try
    {
      serialPort = new SerialPort(port);


      if (!serialPort.openPort())
      {
        throw new CommunicationException("open Port failed");
      }

      serialPort.setParams(SerialPort.BAUDRATE_57600,
                           SerialPort.DATABITS_8,
                           SerialPort.STOPBITS_1,
                           SerialPort.PARITY_NONE);
      serialPort.addEventListener(this);
    }
    catch (SerialPortException ex)
    {
      throw new CommunicationException("open Port failed");
    }
    catch (Exception ex)
    {
      throw new CommunicationException(ex);
    }
  }


  @Override
  public void closePort () throws CommunicationException
  {
    try
    {
      if (!serialPort.closePort())
      {
        throw new CommunicationException("closing Port failed");
      }

      serialPort = null;
    }
    catch (SerialPortException ex)
    {
      throw new CommunicationException("closing Port failed");
    }
    catch (Exception ex)
    {
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

        while (receivedChunks.isEmpty())
        {
          receivedChunks.wait();
        }

        final Chunk ch = receivedChunks.getFirst();

        final byte rv = ch.next();
        if (!ch.isByteAvailable())
        {
          receivedChunks.removeFirst();
        }
        return rv;
      }
      catch (InterruptedException ex)
      {
        throw ex;
      }
      catch (IndexOutOfBoundsException ex)
      {
        LOG.severe("Index out of Bounds");
        throw new CommunicationException(ex);
      }
      catch (Exception ex)
      {
        throw new CommunicationException(ex);
      }

    }
  }


  @Override
  public synchronized void writeBytes (byte[] s) throws CommunicationException
  {
    try
    {
      serialPort.writeBytes(s);
    }
    catch (SerialPortException ex)
    {
      throw new CommunicationException("writing bytes failed");
    }
    catch (Exception ex)
    {
      throw new CommunicationException(ex);
    }

  }


  @Override
  public void serialEvent (SerialPortEvent serialPortEvent)
  {
    if (serialPortEvent.getEventType() == SerialPortEvent.RXCHAR)
    {
      try
      {
        byte[] ba = serialPort.readBytes();
        synchronized (receivedChunks)
        {
          receivedChunks.add(new Chunk(ba));
          receivedChunks.notifyAll();
        }
      }
      catch (Exception ex)
      {
        LOG.warning(ex);
      }

    }
  }


  private class Chunk
  {

    private final byte[] data;
    private int nextIndex = 0;


    public Chunk (byte[] data)
    {
//            if(data == null)
//                throw new IllegalArgumentException();
      this.data = data;
    }


    public byte next ()
    {
      try
      {
        return data[nextIndex++];
      }
      catch (Exception ex)
      {
        ex.printStackTrace(System.err);
      }
      return 10; // \n
      
    }


    public boolean isByteAvailable ()
    {
      return nextIndex < data.length;
    }

  }

}
