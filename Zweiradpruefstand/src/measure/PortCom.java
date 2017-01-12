package measure;

import java.util.LinkedList;
import java.util.logging.Level;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import jssc.SerialPortList;
import logging.Logger;

/**
 * Several methods to communicate with the serial Port
 *
 * @author Levin Messing (meslem12@htl-kaindorf.ac.at)
 */
public class PortCom implements Port, SerialPortEventListener
{

    private static final Logger LOG = Logger.getLogger(MeasurementWorker.class.getName());

    private static boolean block = false;
    private SerialPort serialPort;
    private final LinkedList<Chunk> receivedChunks = new LinkedList<>();

    public PortCom(String port)
    {
        LOG.setLevel(Level.ALL);
        serialPort = new SerialPort(port);
    }

    /**
     * @return the String array of available ports
     */
    @Override
    public String[] getPortList()
    {
        return SerialPortList.getPortNames();
    }

    /**
     * @return the current port
     */
    @Override
    public String getPort()
    {
        return serialPort.getPortName();
    }

    @Override
    public void openPort() throws CommunicationException
    {
        try
        {
            if(!serialPort.openPort())
            {
                throw new CommunicationException("open Port failed");
            }

            serialPort.setParams(SerialPort.BAUDRATE_57600,
                                 SerialPort.DATABITS_8,
                                 SerialPort.STOPBITS_1,
                                 SerialPort.PARITY_NONE);
            serialPort.addEventListener(this);
        }
        catch (SerialPortException ex)
        {
            throw new CommunicationException("open Port failed");
        }
        catch (Exception ex)
        {
            throw new CommunicationException(ex);
        }
    }

    @Override
    public void closePort() throws CommunicationException
    {
        try
        {
            if(!serialPort.closePort())
            {
                throw new CommunicationException("closing Port failed");
            }

            serialPort = null;
        }
        catch (SerialPortException ex)
        {
            throw new CommunicationException("closing Port failed");
        }
        catch (Exception ex)
        {
            throw new CommunicationException(ex);
        }
    }

    @Override
    public byte readByte() throws CommunicationException, InterruptedException
    {
        synchronized (receivedChunks)
        {
            try
            {

                while(receivedChunks.isEmpty())
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
                throw ex;
            }
            catch (IndexOutOfBoundsException ex)
            {
                LOG.severe("Index out of Bounds");
                throw new CommunicationException(ex);
            }
            catch (Exception ex)
            {
                throw new CommunicationException(ex);
            }

        }
    }

    @Override
    public synchronized void writeBytes(byte[] s) throws CommunicationException
    {
        try
        {
//            while(block)
//            {
//                Thread.sleep(500);
//                System.out.println("BLOCKED");;
//            }

//            block = true;
            //Thread sleep for primus - debugging
//            String str = new String(s, "utf-8");
//            LOG.finest(String.format("sending %s", str));
//
//            for(int i = 0; i < str.length(); i++)
//            {
//                Thread.sleep(200);
//                serialPort.writeString(str.substring(i, i + 1));
//                System.out.println(str.substring(i, i + 1));
//
//            }
            serialPort.writeBytes(s);
            //LOG.info("Frame written: %s", new String(s, "utf-8"));

//            block = false;
        }
        catch (SerialPortException ex)
        {
            throw new CommunicationException("writing String failed");
        }
        catch (Exception ex)
        {
            throw new CommunicationException(ex);
        }

    }

    @Override
    public void serialEvent(SerialPortEvent serialPortEvent)
    {
        if(serialPortEvent.getEventType() == SerialPortEvent.RXCHAR)
        {
            try
            {
                byte[] ba = serialPort.readBytes();
                synchronized (receivedChunks)
                {
                    receivedChunks.add(new Chunk(ba));
                    receivedChunks.notifyAll();
                }
            }
            catch (Exception ex)
            {
                LOG.warning(ex);
            }

        }
    }

    private class Chunk
    {

        private final byte[] data;
        private int nextIndex = 0;

        public Chunk(byte[] data)
        {
//            if(data == null)
//                throw new IllegalArgumentException();
            this.data = data;
        }

        public byte next()
        {
//            
//            try
//            {
//                System.out.println("reading Index " + (nextIndex));
////            if(nextIndex + 1 >= data.length)
////                throw new IndexOutOfBoundsException();
//                LOG.info(String.format("%c", data[nextIndex]));
//                byte rv = data[nextIndex];
//                nextIndex++;
//
//                return rv;
//            }
//            catch (NullPointerException ex)
//            {
//                ex.printStackTrace(System.err);
//            }
//            return 0x0a;
            
            return data[nextIndex++];
        }

        public boolean isByteAvailable()
        {
            return nextIndex < data.length;
        }

    }

}
