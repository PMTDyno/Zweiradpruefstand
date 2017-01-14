package measure;

import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;


/**
 *
 * @author Levin Messing (meslem12@htl-kaindorf.ac.at)
 */
public class FrameBytes
{

  private final ByteArrayOutputStream baos = new ByteArrayOutputStream(64);
  private long startTimeMillis;
  private long endTimeMillis;
  private byte[] byteArrayCache;


  public FrameBytes ()
  {
  }


  public void update (byte b)
  {
    if (byteArrayCache != null)
    {
      throw new IllegalStateException("frame already ended");
    }

    if (baos.size() == 0)
    {
      startTimeMillis = System.currentTimeMillis();
    }

    baos.write(b);
    endTimeMillis = System.currentTimeMillis();
  }

  
  byte [] frameBytes ()
  {
    if (byteArrayCache == null)
    {
      byteArrayCache = baos.toByteArray();
    }
    return byteArrayCache;
  }


  public long getStartTimeMillis ()
  {
    return startTimeMillis;
  }


  public long getEndTimeMillis ()
  {
    return endTimeMillis;
  }


  @Override
  public String toString ()
  {
    return "Frame{" + baos.size() + "Bytes}";
  }

}
