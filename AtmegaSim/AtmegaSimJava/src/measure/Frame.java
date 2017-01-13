package measure;

import logging.Logger;


/**
 *
 * @author ...
 */
public class Frame
{
  private static final Logger LOG = Logger.getLogger(Frame.class.getName());
  
  private final FrameBytes bytes;
  private String data;
  
  public Frame (FrameBytes bytes) throws CommunicationException
  {
    this.bytes = bytes;
    checkFrame();
  }
  
  private void checkFrame ()
  {
    
  }


  public String getData ()
  {
    return data;
  }
  
  
}
