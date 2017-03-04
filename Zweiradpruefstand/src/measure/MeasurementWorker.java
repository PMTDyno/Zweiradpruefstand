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
    String wss, rpm, time;

    String[] tmp = com.getFrameData();

    wss = tmp[0];
    if(data.isMeasRPM())
    {
      rpm = tmp[1];
      time = tmp[2];
    }
    else
    {
      rpm = "0";
      time = tmp[1];
    }

    return new RawDatapoint(wss, rpm, time);
  }

  @Override
  protected ArrayList<Datapoint> doInBackground() throws CommunicationException,
                                                         TimeoutException,
                                                         Exception
  {
    LOG.fine("Worker gestartet");
    
    //WSS - rpm - TIME

//    int cnt = 0;
//    while(true)
//    {
//      publish(cnt);
//      
//      cnt++;
//      
//      if(cnt > 10000 || isCancelled() || stopRequest.get())
//        break;
//      
//      Thread.sleep(data.getPeriodTimeMs());
//    }
    
    
    
    try
    {
      RawDatapoint dp;
      double rpm;
      int count = 0;

      do
      {
        if(data.isMeasRPM())
          com.sendFrame(Communication.Request.START);
        else
          com.sendFrame(Communication.Request.STARTNORPM);

        dp = getNextDatapoint();

        if(data.isMeasRPM())
        {
          //converting to U/min 
          rpm = Integer.parseInt(dp.getRpm()) / 1000000.0;
          if(data.isTwoStroke())
            rpm = 1 / rpm * 60;
          else
            rpm = (1 / rpm * 60) * 2;
        }
        else
        {
          //todo starten wenn geschwindigkeit erreicht
          Thread.sleep(data.getPeriodTimeMs());
          break;
        }
        Thread.sleep(data.getPeriodTimeMs());

        //automatic starting of measurement when rpm is higher than...
      } while(rpm < data.getStartRPM());

      list.add(dp);
      LOG.fine("Collecting data...");
      while(true)
      {
        count++;
        publish(count);

        if(data.isMeasRPM())
          com.sendFrame(Communication.Request.MEASURE);
        else
          com.sendFrame(Communication.Request.MEASURENORPM);

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

          if(data.isMeasRPM())
          {
            //converting time
            for(RawDatapoint datapoint : list)
            {
              //in seconds
              Datapoint tmp = new Datapoint(Integer.parseInt(datapoint.getWss()) / 1000000.0,
                                            Integer.parseInt(datapoint.getRpm()) / 1000000.0,
                                            Integer.parseInt(datapoint.getTime()) / 1000000.0);

              //seconds to rad/s
              tmp.setWss(1 / (tmp.getWss() * 26) * 2 * Math.PI);

              //seconds to U/min
              if(data.isTwoStroke())
                tmp.setRpm(1 / tmp.getRpm() * 60);
              else
                tmp.setRpm((1 / tmp.getRpm() * 60) * 2);

              measureList.add(tmp);
            }
          }
          else
          {
            //converting time
            for(RawDatapoint datapoint : list)
            {
              //in seconds
              Datapoint tmp = new Datapoint(Integer.parseInt(datapoint.getWss()) / 1000000.0,
                                            0.0,
                                            Integer.parseInt(datapoint.getTime()) / 1000000.0);

              //seconds to rad/s
              tmp.setWss(1 / (tmp.getWss() * 26) * 2 * Math.PI);

              measureList.add(tmp);
            }
          }
          LOG.info(measureList.size() + " Datensätze erfasst");
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
    catch(InterruptedException ex)
    {
      return null;
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
