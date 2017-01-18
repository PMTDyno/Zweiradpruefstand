package measure;

import java.util.logging.Level;
import logging.Logger;


/**
 *
 * @author Levin Messing (meslem12@htl-kaindorf.ac.at)
 */
public class Frame
{

  private static final Logger LOG = Logger.getLogger(Frame.class.getName());

  private final FrameBytes bytes;
  private String frame;
  private String data;
  private boolean ack = false;


  public Frame (FrameBytes bytes) throws CommunicationException
  {
    this.bytes = bytes;
    checkFrame();
  }


  public String getData ()
  {
    return data;
  }


  private void checkFrame () throws CommunicationException
  {
    try
    {
      //FRAME AVAILABLE?
      if (bytes == null)
      {
        throw new CommunicationException("no frame captured");
      }

      if (bytes.getFrameBytes().length < 1)
      {
        throw new CommunicationException("no frame captured");
      }

      //START STOP
      frame = new String(bytes.getFrameBytes(), "utf-8");
      if (frame.charAt(0) != Communication.SOT)
      {
        throw new CommunicationException("frame not starting with SOT");
      }

      if (frame.charAt(frame.length() - 1) != Communication.EOT)
      {
        throw new CommunicationException("frame not ending with EOT");
      }

      LOG.fine("SOT/EOT CORRECT");

      
      if (frame.indexOf('=') < 0)
      {
        throw new CommunicationException("missing seperator '='");
      }

      data = frame.substring(2, frame.indexOf('='));
      if (data == null || data.isEmpty())
      {
        throw new CommunicationException("no data available");
      }
      LOG.fine("DATA AVAILABLE");

      //CHECKSUM
      String checksum = frame.substring(frame.indexOf(Communication.C_GROUP_SEP) + 1, frame.indexOf(Communication.EOT));
      if (!Crc16.checkCRC(frame.substring(0, frame.indexOf(Communication.C_GROUP_SEP) + 1), checksum))
      {
        throw new CommunicationException("checksum not matching");
      }

      LOG.fine("CHECKSUM MATCHING");

      //BASE64
      data = new String(Communication.DECODER.decode(data), "utf-8");

      //SEND ACK
      if (frame.charAt(1) != Communication.C_ACK && frame.charAt(1) != Communication.C_NACK)
      {
        LOG.info("Frame correct!");
        Communication.getInstance().sendAck(p, true);
      }
      Communication.acceptPackage();
      catch (CommunicationException ex


  
  
    )
        {
            Communication.getInstance().sendAck(Communication.getCurrentPackage(), false);
    //LOG.warning(ex.getMessage());
    throw ex;
  }
      catch (Exception ex


  
  
    )
        {
            throw new CommunicationException(ex);
  }
    }




  /**
   *
   * @return true if the frame is an acknowledge
   */
  public boolean isAck ()
  {
    return ack;
  }


  @Override
  public String toString ()
  {
    StringBuilder sb = new StringBuilder();
    sb.append("Frame{");
    byte[] data = bytes.getFrameBytes();
    sb.append(String.format("%d bytes: ", data.length));
    for (int i = 0; i < data.length; i++)
    {
      if (i > 0)
      {
        sb.append(", ");
      }
      sb.append(String.format("%02x", data[i]));
      if (data[i] >= ' ' && data[i] < 127)
      {
        sb.append(String.format("(%c)", (char) data[i]));
      }
    }
    sb.append('}');
    return sb.toString();
  }

}
