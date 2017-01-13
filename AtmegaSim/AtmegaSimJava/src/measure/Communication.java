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
  
  // ************************************************************************************
  
  private final Port port;
  private int packageNumber;    // 0 or 1, the last used number of a sent frame
  private long sendTime;        // timestamp when sending packet
  private byte [] pendingFrame; // last frame send server, waiting for response if not null
            

  public Communication (Port port)
  {
    this.port = port;
  }
  
  public void connect ()
  {
    try
    {
      sendFrame("refresh");
    }
    catch (Exception ex)
    {
      
    }
  }
  
  
  private void sendFrame (String data) throws UnsupportedEncodingException, CommunicationException
  {
    byte [] dataBytes = data.getBytes("UTF-8"); 
    byte [] ba = new byte [dataBytes.length + 8];
    ba[0] = SOT;
    ba[1] = (byte)(packageNumber + '0');
    System.arraycopy(dataBytes, 0, ba, 2, dataBytes.length);
    ba[2 + dataBytes.length] = GS;
    
    // CRC
    ba[2 + dataBytes.length + 1] = '0';
    ba[2 + dataBytes.length + 2] = '0';
    ba[2 + dataBytes.length + 3] = '0';
    ba[2 + dataBytes.length + 4] = '0';
    
    ba[2 + dataBytes.length + 5] = EOT;
     
    LOG.finest(ba, "Communication.sendFrame(): now sending frame");
    port.writeBytes(ba);
    pendingFrame = ba;
    sendTime = System.currentTimeMillis();
  }
  
  
  
}
