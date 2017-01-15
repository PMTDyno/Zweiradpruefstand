package measure;

import java.io.UnsupportedEncodingException;
import logging.Logger;


/**
 *
 * @author ...
 */
public class Communication
{
  private static final Logger LOG = Logger.getLogger(Communication.class.getName());
  
  public static final byte SOT = 2;  // 0x02  Start of Text
  public static final byte EOT = 3;  // 0x03  End of Text 
  public static final byte GS = 29;  // 0x1d  Group Seperator
  public static final byte US = 31;  // 0x1f  Unit Seperator
  public static final byte ACK = 6;  // 0x06
  public static final byte NAK = 21; // 0x15
  
  private static byte [] cutByteArray (byte [] ba, int start, int length)
  {
    byte [] rv = new byte [length];
    for (int i = start; i<(start+length); i++)
      rv[i-start] = ba[i];
    return rv;
  }
  
  
  // ************************************************************************************
  
  private final Port port;
  private int packageNumber;    // 0 or 1, the last used number of a sent frame
  private long sendTime;        // timestamp when sending packet
  private byte [] pendingFrame; // last frame send server, waiting for response if not null
  private Thread receiveOsiLayer2Thread;  
  private final OsiReceive osiReceive = new OsiReceive();
  private final OsiTransmit osiTransmit = new OsiTransmit();

  public Communication (Port port)
  {
    this.port = port;
  }
  
  public void connect () throws CommunicationException
  {
    try
    {
      receiveOsiLayer2Thread = new ReceiveThread();
      receiveOsiLayer2Thread.start();
      sendFrame("refresh");
    }
    catch (Exception ex)
    {
      if (ex instanceof CommunicationException)
        throw (CommunicationException)ex;
      throw new CommunicationException(ex);
    }
  }
  
  public void sendData (String data) throws CommunicationException
  {
    try
    {
      sendFrame(data);
    }
    catch (Exception ex)
    {
      if (ex instanceof CommunicationException)
        throw (CommunicationException)ex;
      throw new CommunicationException(ex);
    }
  }
  
  
  private void sendFrame (String data) throws UnsupportedEncodingException, CommunicationException
  {
    byte [] dataBytes = data.getBytes("UTF-8"); 
//    byte [] ba = new byte [dataBytes.length + 8];
//    ba[0] = SOT;
//    ba[1] = (byte)(packageNumber + '0');
//    System.arraycopy(dataBytes, 0, ba, 2, dataBytes.length);
//    ba[2 + dataBytes.length] = GS;
//    
//    // CRC
//    ba[2 + dataBytes.length + 1] = '0';
//    ba[2 + dataBytes.length + 2] = '0';
//    ba[2 + dataBytes.length + 3] = '0';
//    ba[2 + dataBytes.length + 4] = '0';
//    
//    ba[2 + dataBytes.length + 5] = EOT;
//     
//    LOG.finest(ba, "Communication.sendFrame(): now sending frame");
//    port.writeBytes(ba);
//    pendingFrame = ba;
//    sendTime = System.currentTimeMillis();
    osiTransmit.layer7(dataBytes);

  }


  
  
  private class OsiTransmit
  {
    private int nextSn;
    private byte [] pendingFrame;
    private long timeoutMillis;
    

    public int getPendingFrameSN ()
    {
      if (pendingFrame != null)
        return (int)(pendingFrame[1] - '0');
      else
        return -1; // no frame pending
    }

    private void layer7 (byte [] data) throws CommunicationException
    {
      LOG.finer(data, "OsiTransmit-layer7: send data");
      layer6(data);
    }
        
    private void layer6 (byte [] data) throws CommunicationException
    {
      LOG.finer(data, "OsiTransmit-layer6: send data");
      // to do Base64 encoding
      layer5(data);
    }
    
    private void layer5 (byte [] data) throws CommunicationException
    {
      LOG.finer(data, "OsiTransmit-layer5: send data");
      layer4(data);
    }

    private void layer4 (byte [] data) throws CommunicationException
    {
      LOG.finer(data, "OsiTransmit-layer4: send data");
      byte [] ba = new byte [data.length + 1] ;
      ba[0] = (byte)(nextSn + '0');
      nextSn = (nextSn + 1) % 2;
      System.arraycopy(data, 0, ba, 1, data.length);
      layer3(ba);
    }

    private void layer3 (byte [] data) throws CommunicationException
    {
      LOG.finer(data, "OsiTransmit-layer3: send data");
      layer2(data);
    }

    private void layer2 (byte [] data) throws CommunicationException
    {
      LOG.finer(data, "OsiTransmit-layer2: send data");
      byte [] ba = new byte [data.length + 7];
      ba[0] = SOT;
      System.arraycopy(data, 0, ba, 1, data.length);
      ba[1+data.length] = GS;
      
      // to do calculate CRC
      ba[1 + data.length + 1] = '0';
      ba[1 + data.length + 2] = '0';
      ba[1 + data.length + 3] = '0';
      ba[1 + data.length + 4] = '0';
      
      ba[1 + data.length + 5] = EOT;
      layer1(ba);
    }

    private void layer1 (byte [] frame) throws CommunicationException
    {
      LOG.finer(frame, "OsiTransmit-layer1: send data");
      port.writeBytes(frame);
      pendingFrame = frame;
      timeoutMillis = System.currentTimeMillis() + 100;
    }
  }
  
  
  
  private class OsiReceive 
  {
    private FrameBytes frameBytes;

    private void layer7 (byte [] frame, int start, int length)
    {
      LOG.finer(cutByteArray(frame, start, length), "OsiReceive-layer7: data received");
    }
    
    private void layer6 (byte [] frame, int start, int length)
    {
      LOG.finer(cutByteArray(frame, start, length), "OsiReceive-layer6: data received");
      // to do Base64 decoding
      layer7(frame, start, length);
    }
    
    private void layer5 (byte [] frame, int start, int length)
    {
      LOG.finer(cutByteArray(frame, start, length), "OsiReceive-layer5: data received");
      layer6(frame, start, length);
    }
    
    private void layer4 (byte [] frame, int start, int length)
    {
      try
      {
        LOG.finer(cutByteArray(frame, start, length), "OsiReceive-layer4: data received");
        final byte sn = frame[start]; // packet sequence number
        if (sn != '0' && sn != '1')
          throw new OsiReceiveException("Frame error, unvalid SN field");
        
        final int snValue = (int)(sn - '0');
        final int expectedSn = osiTransmit.getPendingFrameSN();
        if (expectedSn != 0 && expectedSn != 1)
          throw new OsiReceiveException("No frame pending");
        if (expectedSn != snValue)
        {
          LOG.finer(frame, "OsiReceive-layer4: wrong package number, expect %d");
          // to do inform to repeat
          return;
        }
        
        final byte ack = frame[start+1];
        if (ack != ACK && ack != NAK)
          throw new OsiReceiveException("Frame error, unvalid ACK field");
        if (ack == NAK)
        {
          LOG.finer(frame, "OsiReceive-layer4: NAK received");
          // to do inform to repeat
          return;
        }
        
        layer5(frame, start+2, length-2);
      }
      catch (OsiReceiveException ex)
      {
        LOG.warning(frame, "OsiReceive-layer4: %s", ex.getMessage(), length);
        // to do, inform upper layers to repeat last frame
      }
      catch (Exception ex)
      {
        LOG.warning(frame, "OsiReceive-layer4 internal error", ex);
      }
      
    }
    
    
    private void layer3 (byte [] frame, int start, int length)
    {
      LOG.finer(cutByteArray(frame, start, length), "OsiReceive-layer3: data received");
      layer4(frame, start, length);
    }
    
    
    private void layer2 (byte [] frame, int start, int length)
    {
      LOG.finer(cutByteArray(frame, start, length), "OsiReceive-layer2: frame received");      
      if (length < 7 || frame[start + length - 6] != GS)
      {
        LOG.warning(frame, "OsiReceive-layer2: Frame error (length=%d)", length);
        // to do, inform upper layers to repeat last frame
        return;
      }
      // to do check CRC 
      layer3(frame, start+1, start+frame.length-7);
    }
    
    
    private void layer1 (byte b)
    {
      LOG.finest("OsiReceive-layer1: byte %02x", b);
      if (frameBytes == null && b != SOT)
        LOG.warning("receiving unexpected byte %02x", b);

      else if (b == SOT)
      {
        frameBytes = new FrameBytes();
      }
      frameBytes.update(b);

      if (b == EOT) 
      {
        byte [] frame = frameBytes.frameBytes();
        LOG.debug(frame, "RX-OSI-1: Frame received");
        layer2(frame, 0, frame.length);
        frameBytes = null;
      }
    }  
    
    
    private class OsiReceiveException extends Exception
    {
      public OsiReceiveException (String message)
      {
        super(message);
      }
    }
    
    
  }
  
  
  
  
  

  
  private class ReceiveThread extends Thread
  {
    @Override
    public void run ()
    {
      while (!Thread.currentThread().isInterrupted())
      {
        try
        {
          byte b = port.readByte();
          //LOG.debug("readByte returns with %02x", b);
          osiReceive.layer1(b);
        }
        catch (Exception ex)
        {
          LOG.warning(ex);
        }
      }
    }
    
  }
  
  
  
}
