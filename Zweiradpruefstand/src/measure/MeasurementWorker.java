package measure;

import data.Data;
import java.util.ArrayList;
import logging.Logger;
import javax.swing.SwingWorker;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * starts the measurement and collects all the data
 *
 * @author Levin Messing (meslem12@htl-kaindorf.ac.at)
 */
public class MeasurementWorker extends SwingWorker<ArrayList<Datapoint>, Object>
{

  private final Data data = Data.getInstance();
  private static final Logger LOG = Logger.getLogger(Communication.class.getName());

  private final Communication com;

  private int wheelRpm[];
  private int motorRpm[];
  private final ArrayList<Datapoint> list = new ArrayList<>();
  private final AtomicBoolean stopRequest = new AtomicBoolean(false);

  public MeasurementWorker(Communication com)
  {
    this.com = com;
  }

  public void stop()
  {
    stopRequest.set(true);
  }

  private Datapoint getNextDatapoint() throws CommunicationException,
                                              TimeoutException
  {
    double wdz, mdz;
    int time;

    String[] tmp = com.getFrameData();
    wdz = Double.parseDouble(tmp[0].replace(',', '.'));
    mdz = Double.parseDouble(tmp[1].replace(',', '.'));
    time = Integer.parseInt(tmp[2]);

//    //if mdz < last measured mdz
//    if(mdz < list.get(list.size()-1).getMdz())
//    {
//      LOG.warning("OVERWRITING MDZ! last: " + mdz + " new: " + list.get(list.size()-1).getMdz());
//      mdz = list.get(list.size()-1).getMdz();
//    }
    return new Datapoint(wdz, mdz, time);
  }

  @Override
  protected ArrayList<Datapoint> doInBackground() throws CommunicationException,
                                                         TimeoutException,
                                                         Exception
  {
    //WDZ - MDZ - TIME

    try
    {

      com.sendFrame(Communication.Request.START);
      LOG.finest("sent start");

      list.add(getNextDatapoint());

      while(true)
      {

        Thread.sleep(data.getPeriodTimeMs());

        com.sendFrame(Communication.Request.MEASURE);
        list.add(getNextDatapoint());

        if(isCancelled())
        {
          LOG.finest("Cancel triggered!");
          return null;
        }
        if(stopRequest.get())
        {
          LOG.finest("Stop Request triggered!");
          return list;
        }

      }

    }
    catch (CommunicationException ex)
    {
      LOG.severe("Error sending/receiving data: " + ex.getMessage(), ex);
      throw ex;
    }
    catch (TimeoutException ex)
    {
      LOG.severe("No response from controller - Timeout");
      throw ex;
    }
    catch (Exception ex)
    {
      LOG.severe(ex);
      throw ex;
    }
    finally
    {
      LOG.info("MeasurementWorker done!");
    }

  }

}
