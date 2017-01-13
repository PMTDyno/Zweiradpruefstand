package measure;

import java.util.Iterator;


/**
 *
 * @author sx
 */
public class PortChunk implements Iterable<Byte>
{
  private final byte[] data;
  private int nextIndex = 0;

  public PortChunk (byte[] data)
  {
    if (data == null)
    {
      throw new NullPointerException();
    }
    this.data = data;
  }

  public byte next ()
  {
    return data[nextIndex++];
  }

  public boolean isByteAvailable ()
  {
    return nextIndex < data.length;
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
