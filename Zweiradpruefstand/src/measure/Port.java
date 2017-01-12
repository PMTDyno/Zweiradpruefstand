package measure;

/**
 *
 * @author Levin
 */
public interface Port
{
    
    void openPort() throws CommunicationException;
    void closePort() throws CommunicationException;
    void writeBytes(byte[] s) throws CommunicationException;
    //String readString(int byteCount, int timeout) throws CommunicationException;
    byte readByte() throws CommunicationException, InterruptedException;
    String[] getPortList();
    String getPort();

}
