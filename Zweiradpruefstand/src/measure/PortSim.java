package measure;

import java.util.LinkedList;
import java.util.logging.Level;
import jssc.SerialPort;
import logging.Logger;

/**
 *
 * @author Levin Messing (meslem12@htl-kaindorf.ac.at)
 */
public class PortSim implements Port
{

    private static final Logger LOG = Logger.getLogger(PortSim.class.getName());
    
    
    private final LinkedList<Chunk> receivedChunks = new LinkedList<>();
    private SIM_MODE mode;
    private String port;

    public PortSim()
    {
        LOG.info("PortSim: PortSim()");
    }

    @Override
    public String[] getPortList()
    {
        String[] rv =
        {
            "SIM 1", "SIM 2"
        };
        return rv;
    }

    @Override
    public String getPort()
    {
        return port;
    }


  @Override
  public void openPort (String port) throws CommunicationException
  {
    LOG.info("PortSim: openPort(" + port + ")");
  }


    public static enum SIM_MODE
    {
        NORMAL,
        DELAYED,
        NOTHING;
    }

    @Override
    public void closePort() throws CommunicationException
    {
        LOG.info("PortSim: closePort()");
    }

    void runDelayed(byte[] response)
    {
        new Thread(new Runnable()
        {

            @Override
            public void run()
            {

                try
                {
                    Thread.sleep(150);
                }
                catch (InterruptedException ex)
                {
                    LOG.warning(ex);
                }
                LOG.info("chunk received");
                synchronized (receivedChunks)
                {
                    receivedChunks.add(new Chunk(response));
                    receivedChunks.notifyAll();
                }
            }

        }
        ).start();
    }

    void runNormal(byte[] response)
    {
        String str = new String(response);
        for(int i = 0; i < 10; i++)
        {
            
        }
//        synchronized (receivedChunks)
//        {
//            receivedChunks.add(new Chunk(response));
//            receivedChunks.notifyAll();
//        }
    }

    @Override
    public void writeBytes(byte[] s) throws CommunicationException
    {
        try
        {
            LOG.info("PortSim: writeString(\"%s\")", new String(s, "utf-8"));
            byte[] sb;
            sb = s;
            byte[] response = new byte[sb.length + 2];
            response[0] = Communication.SOT;
            System.arraycopy(sb, 0, response, 1, sb.length);
            response[sb.length + 1] = Communication.EOT;
            switch (mode)
            {
                case NORMAL:
                    runNormal(response);
                    break;
                case DELAYED:
                    runDelayed(response);
                    break;
                case NOTHING:
                    break;
                default:
                    throw new UnsupportedOperationException();
            }
        }
        catch (Exception ex)
        { // UnsupportedEncodingException
            LOG.warning(ex);
        }
    }

    @Override
    public byte readByte() throws CommunicationException, InterruptedException
    {
        synchronized (receivedChunks)
        {
            try
            {
                if(receivedChunks.isEmpty())
                {
                    receivedChunks.wait();
                }
                final Chunk ch = receivedChunks.getFirst();
                final byte rv = ch.next();
                if(!ch.isByteAvailable())
                {
                    receivedChunks.removeFirst();
                }
                return rv;
            }
            catch (InterruptedException ex)
            {
                throw new CommunicationException(ex);
            }
        }
    }

    public SIM_MODE getMode()
    {
        return mode;
    }

    public void setMode(SIM_MODE mode)
    {
        this.mode = mode;
    }

    private class Chunk
    {

        private final byte[] data;
        private int nextIndex;

        public Chunk(byte[] data)
        {
            this.data = data;
        }

        public byte next()
        {
            return data[nextIndex++];
        }

        public boolean isByteAvailable()
        {
            return nextIndex < data.length;
        }

    }

}
