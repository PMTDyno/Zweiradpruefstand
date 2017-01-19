package measure;


/**
 *
 * @author Levin
 */
public interface Port
{

  void openPort (String port) throws CommunicationException;
  void closePort () throws CommunicationException;
  
  void writeBytes (byte[] s) throws CommunicationException;
  byte readByte () throws CommunicationException, InterruptedException;
  
  String[] getPortList ();
  String getPort ();
}
