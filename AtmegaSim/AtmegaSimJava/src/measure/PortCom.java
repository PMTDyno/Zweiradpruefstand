package measure;

import java.util.LinkedList;
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
  private static final Logger LOG = Logger.getLogger(PortCom.class.getName());

  private SerialPort serialPort;
  private final LinkedList<PortChunk> receivedChunks = new LinkedList<>();


  public PortCom ()
  {
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
    if (serialPort != null)
      throw new CommunicationException("serial port already open");
    try
    {
      serialPort = new SerialPort(port);
      LOG.fine("Open serial port %s (57600/8N1)", serialPort.getPortName());
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
      LOG.fine("close serial port %s", serialPort.getPortName());
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

        final PortChunk ch = receivedChunks.getFirst();

        final byte rv = ch.next();
        if (!ch.isByteAvailable())
        {  //private static boolean block = false;
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
        throw new CommunicationException(ex);
      }
      catch (Exception ex)
      {
        throw new CommunicationException(ex);
      }

    }
  }

  //private static boolean block = false;
  @Override
  public synchronized void writeBytes (byte[] data) throws CommunicationException
  {
    LOG.finer(data, "PortCom: writebytes(byte [])");
    try
    {
      serialPort.writeBytes(data);
      LOG.debug("bytes written");
    }
    catch (SerialPortException ex)
    {
      throw new CommunicationException("writing String failed");
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
        LOG.finer("PortCom: %s bytes received", ba.length);
        synchronized (receivedChunks)
        {
          receivedChunks.add(new PortChunk(ba));
          receivedChunks.notifyAll();
        }
      }
      catch (Exception ex)
      {
        LOG.warning(ex);
      }

    }
  }


  @Override
  public boolean isOpened ()
  {
    return serialPort != null && serialPort.isOpened();
  }

}
