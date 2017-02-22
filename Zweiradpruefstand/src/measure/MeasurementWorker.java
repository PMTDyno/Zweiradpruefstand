package measure;

import data.Datapoint;
import data.Data;
import data.RawDatapoint;
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
public class MeasurementWorker extends SwingWorker<ArrayList<Datapoint>, Integer>
{

  private final Data data = Data.getInstance();
  private static final Logger LOG = Logger.getLogger(Communication.class.getName());

  private final Communication com;

  private final ArrayList<RawDatapoint> list = new ArrayList<>();
  private final AtomicBoolean stopRequest = new AtomicBoolean(false);

  public MeasurementWorker(Communication com)
  {
    this.com = com;
  }

  public void stop()
  {
    stopRequest.set(true);
  }

  private RawDatapoint getNextDatapoint() throws CommunicationException,
                                                 TimeoutException
  {
    double wdz, rpm;
    int time;

    String[] tmp = com.getFrameData();
    wdz = Double.parseDouble(tmp[0].replace(',', '.'));
    rpm = Double.parseDouble(tmp[1].replace(',', '.'));
    time = Integer.parseInt(tmp[2]);

    return new RawDatapoint(wdz, rpm, time);
  }

  @Override
  protected ArrayList<Datapoint> doInBackground() throws CommunicationException,
                                                         TimeoutException,
                                                         Exception
  {
    //WDZ - rpm - TIME

    try
    {
      RawDatapoint dp;
      double rpm;
      int count = 0;

      do
      {

        com.sendFrame(Communication.Request.START);
        dp = getNextDatapoint();

        //converting to U/min
        rpm = dp.getRpm() / 1000000;
        if(data.isTwoStroke())
          rpm = 1 / rpm * 60;
        else
          rpm = (1 / rpm * 60) * 2;

        Thread.sleep(data.getPeriodTimeMs());

      } while(rpm < data.getStartRPM());

      list.add(dp);
      LOG.fine("collecting data...");
      while(true)
      {
        count++;
        publish(count);

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

          data.setRawDataList(list);

          ArrayList<Datapoint> measureList = new ArrayList<>();

          //converting time
          for(RawDatapoint datapoint : list)
          {
            //in seconds
            Datapoint tmp = new Datapoint(datapoint.getWdz() / 1000000,
                                          datapoint.getRpm() / 1000000,
                                          datapoint.getTime() / 1000000);

            //seconds to rad/s
            tmp.setWdz(1 / (tmp.getWdz() * 26) * 2 * Math.PI);

            //seconds to U/min
            if(data.isTwoStroke())
              tmp.setRpm(1 / tmp.getRpm() * 60);
            else
              tmp.setRpm((1 / tmp.getRpm() * 60) * 2);

            measureList.add(tmp);

          }

          return measureList;
        }

        Thread.sleep(data.getPeriodTimeMs());

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
