package measure;

/**
 *
 * @author Levin Messing (meslem12@htl-kaindorf.ac.at)
 */
public class CommunicationException extends Exception {

    /**
     * Constructs an instance of <code>CommunicationException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public CommunicationException(String msg) {
        super(msg);
    }
    
    /**
     * Constructs an instance of <code>CommunicationException</code> with the specified Exception.
     * @param ex the Exception
     */
    public CommunicationException(Exception ex)
    {
        super(ex);
    }

    CommunicationException()
    {
        throw new UnsupportedOperationException("CommunicationException.CommunicationException not implemented");
    }
}