package measure;

import data.Data;
import gui.Gui;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import javax.swing.SwingWorker;
import logging.Logger;

/**
 *
 * @author Levin Messing (meslem12@htl-kaindorf.ac.at)
 */
public class Communication
{

    private static final Data DATA = Data.getInstance();
    private static final Logger LOGP = Logger.getParentLogger();
    private static final Logger LOG = Logger.getLogger(Communication.class.getName());

    //private static final int TIMEOUT = 100;
    //private static final TimeUnit TIMOUT_UNIT = TimeUnit.MILLISECONDS;
    private static final int TIMEOUT = 5;
    private static final TimeUnit TIMOUT_UNIT = TimeUnit.SECONDS;

    public static final Base64.Decoder DECODER = Base64.getDecoder();
    public static final Base64.Encoder ENCODER = Base64.getEncoder();

    private static int pack = 0;

    private static Communication instance = null;

    private boolean success = false;
    private static boolean connected = false;

    private SwingWorker worker = null;

    //The ASCII Control Bytes
    public static final byte C_START = 2; //0x02
    public static final byte C_STOP = 3; //0x03
    public static final byte C_GROUP_SEP = 29; //0x1d
    public static final byte C_UNIT_SEP = 31;
    public static final byte C_ACK = 6; //0x06
    public static final byte C_NACK = 21; //0x15
    //The ASCII Control Bytes in String format
    public static final String SC_START = Byte.toString(C_START); //start
    public static final String SC_STOP = Byte.toString(C_STOP); //stop
    public static final String SC_GROUP_SEP = Byte.toString(C_GROUP_SEP); //groupSeperator
    public static final String SC_UNIT_SEP = Byte.toString(C_UNIT_SEP); //unitSeperator
    public static final String SC_ACK = Byte.toString(C_ACK);
    public static final String SC_NACK = Byte.toString(C_NACK);

    public final LinkedList<Frame> receivedFrames = new LinkedList<>();

    private Port port;
    private Thread receiveThread;
    private final LinkedList<Frame> receivedFrameList = new LinkedList<>();

    /**
     *
     * @param serialPort
     * @throws measure.CommunicationException
     * @throws java.util.concurrent.TimeoutException
     */
    @SuppressWarnings("UseSpecificCatch")
    public Communication(String serialPort) throws CommunicationException,
                                                   TimeoutException, Exception
    {
        instance = this;
        LOG.setLevel(Level.ALL);

        port = new PortSim("");
        ((PortSim) port).setMode(PortSim.SIM_MODE.NORMAL);
        //port = new PortCom(serialPort);

        receiveThread = new Thread(getFrame);
        receiveThread.start();

        try
        {
            connect();

            //port.writeString("Test");
            //Frame frame = readFrame(TIMEOUT, TimeUnit.MILLISECONDS);
            //Frame frame = readFrame(TIMEOUT, TimeUnit.MINUTES); //for simulation/breakpoints
            //LOG.info(frame.toString());
        }
        catch (Exception ex)
        {
            port = null;
            throw ex;

        }
    }

    public String getPort()
    {
        return port.getPort();
    }

    /**
     *
     * @return The captured and already checked Frame Data
     * @throws CommunicationException
     * @throws TimeoutException
     */
    public String getFrameData() throws CommunicationException, TimeoutException
    {
        for(int i = 0; i < 3; i++)
        {
            try
            {
                return readFrame(TIMEOUT, TIMOUT_UNIT).getData();

            }
            catch (CommunicationException ex)
            {
                LOG.warning(ex);
            }
            catch (TimeoutException ex)
            {
                if(i == 2)
                    throw ex;
                LOG.fine(String.format("%s. Timeout", i + 1));
            }
            catch (InterruptedException ex)
            {
                break;
            }
        }
        throw new CommunicationException("received wrong frames");
    }

    /**
     *
     * @param timeout
     * @param unit
     * @return
     * @throws CommunicationException
     * @throws TimeoutException
     * @throws InterruptedException
     */
    public Frame readFrame(long timeout, TimeUnit unit)
            throws CommunicationException, TimeoutException,
                   InterruptedException
    {
        synchronized (receivedFrameList)
        {
            long startTimeMillis = System.currentTimeMillis();
            while(true)
            {
                if(!receivedFrameList.isEmpty())
                {
                    return receivedFrameList.removeFirst();
                }

                long to = startTimeMillis + unit.toMillis(timeout)
                        - System.currentTimeMillis();
                if(to <= 0)
                {
                    throw new TimeoutException();
                }
                //is the following good practise?
                //probably not.., why not?
                try
                {
                    receivedFrameList.wait(to);
                }
                catch (InterruptedException ex)
                {
                    LOG.warning(ex);
                }
            }
        }

    }

    public static int getCurrentPackage()
    {
        return pack;
    }

    public static void acceptPackage() throws IOException
    {
        switch (pack)
        {
            case 0:
                pack = 1;
                break;
            case 1:
                pack = 0;
                break;
            default:
                LOG.severe("package number fatal error");
                throw new IOException("FATAL ERROR");
        }
    }

    public void sendAck(int pkg, boolean ack) throws CommunicationException
    {
        try
        {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            baos.write(Communication.C_START);
            Thread.sleep(10);
            baos.write(String.valueOf(getCurrentPackage()).getBytes());
            Thread.sleep(10);

            if(ack)
            {
                baos.write(Communication.C_ACK);
                baos.write(Communication.C_STOP);
                port.writeBytes(baos.toByteArray());

                LOG.fine("ACK SENT");
            }
            else
            {
                baos.write(Communication.C_NACK);
                baos.write(Communication.C_STOP);
                port.writeBytes(baos.toByteArray());
                LOG.fine("NACK SENT");
            }

        }
        catch (Exception ex)
        {
            throw new CommunicationException(ex);
        }
    }

    public static String[] getAvailablePorts()
    {
        
        //return new PortCom("list").getPortList();
        return new PortSim("list").getPortList();
    }

    public static Communication getInstance()
    {
        return instance;
    }

    public void disconnect() throws CommunicationException
    {
        try
        {
            if(port != null)
            {
                port.closePort();
                connected = false;
            }
            else
                throw new CommunicationException("closing Port failed");
        }
        catch (Exception ex)
        {
            throw new CommunicationException(ex);
        }
    }

    public static boolean isConnected()
    {
        return connected;
    }

    public void refreshEco() throws CommunicationException, TimeoutException
    {
        try
        {
            sendFrame("refresh");
            setEco(getFrameData());
        }
        catch (Exception ex)
        {
            throw new CommunicationException(ex);
        }
    }

    public boolean isSuccess()
    {
        return success;
    }

    /**
     * sets the worker
     *
     * @param w the worker
     */
    public void setWorker(SwingWorker w)
    {
        worker = w;
    }

    /**
     * starts the measurement
     *
     * @param gui
     */
    public void start(Gui gui)
    {
        startWorker(new MeasurementWorker(gui, this));
    }

    /**
     * sets the worker and starts it
     *
     * @param w the worker
     */
    private void startWorker(SwingWorker w)
    {
        worker = w;
        worker.execute();
    }

    /**
     * cancels the worker if it isn't <code>null</code>.<br>
     * Also sets the success variable to false.
     */
    public void cancelWorker()
    {
        success = false;
        if(worker != null)
            worker.cancel(true);
    }

    /**
     * Cancels the worker if it isn't <code>null</code>.<br>
     * Also sets the success variable to true.
     */
    public void stopWorker()
    {
        success = true;
        if(worker != null)
            worker.cancel(true);
    }

    /**
     * set to true if the measurement finished succesfully
     *
     * @param success
     */
    public void setSuccess(boolean success)
    {
        this.success = success;
    }

    /**
     *
     * @throws CommunicationException
     * @throws TimeoutException
     */
    private void connect() throws CommunicationException, TimeoutException
    {
        port.openPort();
        try
        {
            String str1 = getFrameData();

            String tmp;
            if(!str1.equals("req"))
            {
                disconnect();
                throw new CommunicationException("wrong Device");
            }

            //LOG.finest("got req");
            sendFrame("ready");
            LOG.finest("sent ready");

            str1 = getFrameData();

            setEco(str1);

            connected = true;

        }
        catch (CommunicationException ex)
        {
            throw ex;
        }
        catch (TimeoutException ex)
        {
            disconnect();
            LOG.severe("Timeout");
            throw ex;
        }
        catch (Exception ex)
        {
            disconnect();
            ex.printStackTrace(System.err);
            LOG.severe("Unsupported Exception", ex);
            throw new CommunicationException(ex);
        }
    }

    /**
     *
     * @param data
     * @throws CommunicationException
     * @throws TimeoutException
     */
    public void sendFrame(String data) throws CommunicationException,
                                              TimeoutException
    {
        //build frame

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try
        {
            baos.write(Communication.C_START);
            baos.write(String.valueOf(getCurrentPackage()).getBytes());
            baos.write(Communication.ENCODER.encode(data.getBytes()));
            baos.write(Communication.C_GROUP_SEP);
            baos.write(Crc16.getCRC(new String(baos.toByteArray(), "utf-8")).getBytes("utf-8"));
            baos.write(Communication.C_STOP);
        }
        catch (IOException ex)
        {
            throw new CommunicationException("Error converting data string to bytes");
        }
        catch (Exception ex)
        {
            throw new CommunicationException(ex);
        }

        //send frame until ack - max 3 times
        for(int i = 0; i < 3; i++)
        {
            try
            {
                port.writeBytes(baos.toByteArray());
                LOG.info("Frame written: %s", baos.toString());
                Frame ack = readFrame(TIMEOUT, TIMOUT_UNIT);
                if(ack.isAck())
                    return;
            }
            catch (TimeoutException | InterruptedException | CommunicationException ex)
            {
            }
        }
        throw new TimeoutException();

    }

    public void setEco(String data) throws NumberFormatException,
                                           UnsupportedEncodingException
    {
        byte[] str =
        {
            C_UNIT_SEP
        };
        String rv[] = data.split(new String(str, "utf-8"), 3);

        double temperature = Double.parseDouble(rv[0]);
        int humidity = Integer.parseInt(rv[1]);
        double pressure = Double.parseDouble(rv[2]);

//        System.out.println(temperature);
//        System.out.println(humidity);
//        System.out.println(pressure);
        if(temperature < (-100) || temperature > 100
                || humidity < 0 || humidity > 100
                || pressure < 100 || pressure > 2000)
        {
            throw new IllegalStateException();
        }

        DATA.setTemperature(temperature);
        DATA.setHumidity(humidity);
        DATA.setPressure(pressure);

    }

    //Runnable or Thread?, better would be to use modern kind of
    // multithreading -> executors
    private Runnable getFrame = new Runnable()
    {

        @Override
        public void run()
        {
            try
            {
                FrameBytes frame = null;
                while(true)
                {
                    byte b = port.readByte();
                    //System.out.println(b);
                    switch (b)
                    {

                        //ignore \n and \r
                        case 10:
                            break;
                        case 13:
                            break;

                        case C_START:
                            frame = new FrameBytes();
                            frame.update(b);
                            break;

                        case C_STOP:
                            if(frame != null)
                            {
                                frame.update(b);
                                LOG.info(String.format("Frame received: %s", new String(frame.getFrameBytes(), "utf-8")));
                                synchronized (receivedFrameList)
                                {
                                    receivedFrameList.add(new Frame(frame));
                                    receivedFrameList.notifyAll();
                                    frame = null;
                                }
                            }
                            else
                            {
                                LOG.warning("missing C_START");
                            }
                            break;

                        default:
                            if(frame != null)
                            {
                                frame.update(b);
                            }
                            else
                            {
                                LOG.warning("missing C_START " + String.format("'%c'", b));
                            }
                    }
                }
            }
            catch (CommunicationException ex)
            {
                ex.printStackTrace(System.err);
                LOG.warning(ex.getMessage());
            }
            catch (Exception ex)
            {
                LOG.warning(ex);
            }
        }

    };

}
