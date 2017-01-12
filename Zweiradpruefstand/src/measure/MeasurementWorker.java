package measure;

import data.Data;
import logging.Logger;
import javax.swing.SwingWorker;
import gui.Gui;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;

/**
 * starts the measurement and collects all the data
 *
 * @author Levin Messing (meslem12@htl-kaindorf.ac.at)
 */
public class MeasurementWorker extends SwingWorker
{

    private final Data data = Data.getInstance();
    private static final Logger LOGP = Logger.getParentLogger();
    private static final Logger LOG = Logger.getLogger(Communication.class.getName());

    private final Gui gui;
    private final Communication com;

    private int wheelRpm[];
    private int motorRpm[];
    private boolean done = false;
    private boolean error = true;

    public MeasurementWorker(Gui gui, Communication com)
    {
        LOG.setLevel(Level.ALL);
        this.gui = gui;
        this.com = com;
    }

    @Override
    protected Object doInBackground()
    {
        error = true;

        String[] data1 = new String[gui.getMaxElements()];
        String[] data2 = new String[data1.length];

        byte[] bytes;
        String tmp;
        String[] measure = new String[2];
        String[] cacheWheelRpm = new String[data1.length];
        String[] cacheMotorRpm = new String[data1.length];
        int readData = 0;
        try
        {

            com.sendFrame("start");
            LOG.finest("sent start");

            for(int i = 0; i < data1.length; i++)
            {
                if(!isCancelled())
                {
                    tmp = com.getFrameData();
                    int index = tmp.indexOf(Communication.C_UNIT_SEP);
                    cacheWheelRpm[i] = tmp.substring(0, index);
                    cacheMotorRpm[i] = tmp.substring(index + 1, tmp.length());

                    LOG.info(String.format("Data: %s", tmp));
                    LOG.info(String.format("Wheel: %s", cacheWheelRpm[i]));
                    LOG.info(String.format("Motor: %s", cacheMotorRpm[i]));

                    //TWEAK 500 AND 60
//                    if(Integer.parseInt(cacheMotorRpm[i]) < 2000 || Integer.parseInt(cacheWheelRpm[i]) < 10)
//                    {
//                        i--;
//                        continue;
//                    }
                    readData++;

//                    tmp = com.getFrameData();
//                    //LOG.severe(String.format("%s", new String(tmp.getBytes(), "utf-8")));
//                    int index = tmp.indexOf(Communication.C_UNIT_SEP);
//                    measure[0] = tmp.substring(0, index - 1);
//                    measure[1] = tmp.substring(index+1, tmp.length() - 1);
                    LOG.finest(String.format("%s", new String(cacheWheelRpm[i].getBytes(), "utf-8")));
                    LOG.finest(String.format("%s", new String(cacheMotorRpm[i].getBytes(), "utf-8")));
//                    cacheWheelRpm[i] = measure[0];
//                    cacheMotorRpm[i] = measure[1];
//
//                    readData++;
                }
                else
                {
                    LOG.finest("Cancel/Stop triggered!");
                    break;
                }

                //if maximum reached
                if(i == data1.length - 1)
                {
                    gui.setCancellingEnabled(false);
                    com.setSuccess(true);
                }
            }

            com.sendFrame("stop");
            LOG.finest("stop sent");

            if(com.isSuccess())
            {

                Thread.sleep(500);

                tmp = com.getFrameData();

                com.setEco(tmp);
                LOG.finer("updated ecosystem");

                wheelRpm = new int[readData];
                motorRpm = new int[readData];

                for(int i = 0; i < readData; i++)
                {

                    wheelRpm[i] = Integer.parseInt(cacheWheelRpm[i]);
                    motorRpm[i] = Integer.parseInt(cacheMotorRpm[i]);
                }

            }
            else
            {
                done = true;
                return null;
            }
        }
        catch (CommunicationException ex)
        {
            LOG.severe("Error sending/receiving data", ex);
            done = true;
            gui.showErrorMessage("Fehler bei Messung", "Fehler bei Datenübertragung.\n"
                                 + "Erneut verbinden und/oder Messung wiederholen.");
            return null;
        }
        catch (TimeoutException ex)
        {
            LOG.severe("No response from controller - Timeout");
            done = true;
            gui.showErrorMessage("Fehler bei Messung", "Keine Antwort von Gerät bekommen (Timeout).\n"
                                 + "Messung wiederholen.");
            return null;
        }
        catch (Exception ex)
        {
            LOG.severe(ex);
            done = true;
            gui.showErrorMessage("Fehler bei Messung", "Unbekannter Error.\n"
                                 + "Bitte Messung wiederholen.");
            return null;
        }

        error = false;
        done = true;
        return null;
    }

    @Override
    protected void done()
    {
        int time = 0;
        try
        {
            this.get();
        }
        catch (InterruptedException | ExecutionException ex)
        {
            LOG.severe("Problem when waiting for Thread to end", ex);
        }
        catch (CancellationException ex)
        {
        }
        catch (Exception ex)
        {
            LOG.severe("Unsupported Exception", ex);
        }
        while(!done)
        {

//            LOG.finest("waiting for done to be true");
            try
            {
                Thread.sleep(300);
            }
            catch (InterruptedException ex)
            {
                LOG.severe("unsupported Thread.sleep() error");
            }
            catch (Exception ex)
            {
                LOG.severe("Unsupported Exception", ex);
            }
//            time++;
//            if(time >= 20) //6 Sekunden
//            {
//                error = true;
//                gui.showErrorMessage("Fehler", "Bearbeitung dauerte zu lange - automatischer Abbruch");
//                break;
//            }
        }

        if(!error)
        {
            data.setWheelRpm(null);
            data.setWheelRpm(wheelRpm);
            data.setMotorRpm(null);
            data.setMotorRpm(motorRpm);

            gui.measurementDone(this); //send the gui method that it finished. also sends the worker as parameter
        }
        else
        {
            try
            {
                com.sendFrame("stop");
                LOG.finest("sent stop");
            }
            catch (CommunicationException ex)
            {
                LOG.severe("Could not send stop", ex);
            }
            catch (Exception ex)
            {
                LOG.severe(ex);
            }

            com.setSuccess(false);
            gui.measurementDone(this);
        }
    }

}
