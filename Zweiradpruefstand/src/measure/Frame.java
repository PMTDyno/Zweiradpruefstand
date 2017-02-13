package measure;

import java.util.logging.Level;
import logging.Logger;

/**
 *
 * @author Levin Messing <meslem12@htl-kaindorf.ac.at>
 */
public class Frame
{

  private static final Logger LOG = Logger.getLogger(Frame.class.getName());

  private final FrameBytes bytes;
  private String frame;
  private String data;


  public Frame (FrameBytes bytes) throws CommunicationException
  {
    LOG.setLevel(Level.ALL);
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

      //DATA AVAILABLE?
      int index = frame.indexOf('=');
      if (index < 0)
      {
        throw new CommunicationException("missing seperator '='");
      }

      data = frame.substring(1, index);
      if (data == null || data.isEmpty())
      {
        throw new CommunicationException("no data available");
      }

      if (data.split("-", 3).length != 3)
      {
        throw new CommunicationException("wrong format! missing '-'");
      }

      LOG.fine("DATA AVAILABLE");

      //CHECKSUM
      String checksum = frame.substring(index + 1, frame.indexOf(Communication.EOT));
      if (!Crc16.checkCRC(frame.substring(0, index + 1), checksum))
      {
        throw new CommunicationException("checksum not matching");
      }

      LOG.fine("CHECKSUM MATCHING");


      LOG.info("Frame correct!");
    }
    catch (CommunicationException ex)
    {
      //LOG.warning(ex.getMessage());
      throw ex;
    }
    catch (Exception ex)
    {
      throw new CommunicationException(ex);
    }
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
