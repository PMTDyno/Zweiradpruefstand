package measure;

import java.util.Iterator;


/**
 *
 * @author sx
 */
public class PortChunk implements Iterable<Byte>
{
  private final byte singleByteValue;
  private final byte[] data;
  private int nextIndex = 0;

  public PortChunk (byte[] data)
  {
    if (data == null)
      throw new NullPointerException();
 
    this.data = data;
    singleByteValue = 0;
  }

  public PortChunk (int data)
  {
    if (data < 0 || data > 255)
    {
      throw new IllegalArgumentException();
    }
    this.data = null;
    singleByteValue = (byte)(data >= 128 ? (data - 256) : data);
  }
  
  public byte next ()
  {
    if (data == null)
    {
      if (nextIndex != 0)
        throw new IllegalStateException("no value available");
      nextIndex++;
      return singleByteValue;
    }
    else
      return data[nextIndex++];
  }

  public boolean isByteAvailable ()
  {
    return data == null ? (nextIndex == 0) :  (nextIndex < data.length);
  }

  @Override
  public Iterator<Byte> iterator ()
  {
    return new PortChunkIterator();
  }

  
  private class PortChunkIterator implements Iterator<Byte>
  {
    private int index;
    
    @Override
    public boolean hasNext ()
    {
      return index < data.length;  
    }

    @Override
    public Byte next ()
    {
      return data[index++];
    }

    @Override
    public void remove ()
    {
      throw new UnsupportedOperationException("remove not supported");
    }
    
  }
  
  
}
