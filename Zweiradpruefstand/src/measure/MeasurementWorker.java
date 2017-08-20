package measure;

import data.Datapoint;
import data.Data;
import data.RawDatapoint;
import java.util.ArrayList;
import logging.Logger;
import javax.swing.SwingWorker;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;

/**
 * starts the measurement and collects all the data
 *
 * @author Levin Messing (meslem12@htl-kaindorf.ac.at)
 */
public class MeasurementWorker extends SwingWorker<ArrayList<Datapoint>, Double>
{

  private int count;
  private boolean measRPM = false;
  private final Data data = Data.getInstance();
  private static final Logger LOG = Logger.getLogger(Communication.class.getName());

  private final Communication com;

  private final ArrayList<RawDatapoint> rawList = new ArrayList<>();
  private final ArrayList<Datapoint> measureList = new ArrayList<>();

  private final AtomicBoolean stopRequest = new AtomicBoolean(false);

  public MeasurementWorker(Communication com)
  {
    LOG.setLevel(Level.ALL);
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
    if(measRPM)
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
    measRPM = data.isMeasRPM();

    //WSS - RPM - TIME
    simLoop(false);

    count = 0;

    try
    {

      RawDatapoint dp = waitUntilStart();

      if(dp == null)
        return null;

      count++;

      rawList.add(dp);
      addAndPublish(dp);

      LOG.fine("Collecting data...");

      return collectData();
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
    catch (InterruptedException ex)
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

  /**
   * Checks if rpm is constant at a given value. If it is it will check when it
   * will be higher than startRpm. After that it will return the latest
   * RawDatapoint.
   *
   * @return
   * @throws CommunicationException
   * @throws TimeoutException
   * @throws InterruptedException
   */
  private RawDatapoint waitUntilStart() throws CommunicationException,
                                               TimeoutException,
                                               InterruptedException
  {
    RawDatapoint dp;
    double rpm = 0;   // U/min
    double kmh = 0;   // Km/h

    do
    {
      if(isCancelled())
      {
        LOG.finest("Cancel triggered!");
        return null;
      }

      if(measRPM)
        com.sendFrame(Communication.Request.START);
      else
        com.sendFrame(Communication.Request.STARTNORPM);

      dp = getNextDatapoint();

      if(measRPM)
      {
        //converting to U/min 
        rpm = toUmin(Integer.parseInt(dp.getRpm()));
      }
      else
      {
        kmh = toKmh(toRads(Integer.parseInt(dp.getWss())));
      }

      Thread.sleep(data.getPeriodTimeMs());

      //automatic starting of measurement when rpm is higher than...
    } while(rpm < data.getStartRPM() && kmh < data.getStartKMH());

    return dp;
  }

  private ArrayList<Datapoint> collectData() throws CommunicationException,
                                                    TimeoutException,
                                                    InterruptedException
  {
    RawDatapoint dp;

    while(true)
    {

      int stopCount = 0;
      do
      {
        if(measRPM)
          com.sendFrame(Communication.Request.MEASURE);
        else
          com.sendFrame(Communication.Request.MEASURENORPM);

        dp = getNextDatapoint();

        if(toUmin(Integer.parseInt(dp.getRpm())) < data.getStartRPM() + 50)
          stopCount++;
        else
          stopCount = 0;

        if(stopCount >= 5)
        {
          LOG.finest("Automatic stopping triggered!");
          data.setRawDataList(rawList);
          LOG.info(measureList.size() + " Datensätze erfasst");

          return measureList;
        }

        Thread.sleep(data.getPeriodTimeMs());

      } while(stopCount >= 1);

      count++;
      rawList.add(dp);
      addAndPublish(dp);

      if(isCancelled())
      {
        LOG.finest("Cancel triggered!");
        return null;
      }
      if(stopRequest.get())
      {
        LOG.finest("Stop Request triggered!");
        data.setRawDataList(rawList);
        LOG.info(measureList.size() + " Datensätze erfasst");

        return measureList;
      }

      Thread.sleep(data.getPeriodTimeMs());
    }
  }

  private void simLoop(boolean start) throws InterruptedException
  {
    if(start)
    {
      LOG.fine("Starting endless simulation loop");
      int cnt = 0;
      double power = 0;
      double umin = 0;
      while(true)
      {
        Double[] val =
        {
          cnt * 1.0, power, umin
        };

        publish(val);

        power = Math.sin(cnt / 180.0 * Math.PI) * 20 + 30;
        umin = power * 100;

        cnt++;

        if(cnt >= 360)
          cnt = 0;

        if(cnt > 10000 || isCancelled() || stopRequest.get())
          break;

        Thread.sleep(data.getPeriodTimeMs());
      }
    }
  }

  /**
   * Converts Motor Microseconds to U/min
   *
   * @param rpm
   * @return
   */
  private double toUmin(int rpm)
  {
    if(data.isTwoStroke())
      return (1.0 / toSec(rpm) * 60.0);
    else
      return (1.0 / toSec(rpm) * 60.0) * 2.0;
  }

  /**
   * Converts Wheel Microseconds to rad/s
   *
   * @param wss
   * @return
   */
  private double toRads(int wss)
  {
    return (1.0 / (toSec(wss) * 26.0) * 2.0 * Math.PI);
  }

  /**
   * Converts rad/s to km/h
   *
   * @param rad
   * @return
   */
  private double toKmh(double rad)
  {
    return (rad * 0.175 * 3.6);
  }

  /**
   * Converts µs to Seconds
   *
   * @param µs
   * @return
   */
  private double toSec(int µs)
  {
    return (µs / 1000000.0);
  }

  /**
   * Converts the RawDatapoint and adds it to the list<Datapoint> <br>
   * Also publishes the chunks for the dials.
   *
   * @param dp
   */
  private void addAndPublish(RawDatapoint dp)
  {

    LOG.finest("raw data: rpm=" + dp.getRpm()
            + "µs wss=" + dp.getWss()
            + "µs time=" + dp.getTime() + "µs");
    
    double rpm = 0.0;
    double rad = toRads(Integer.parseInt(dp.getWss()));
    double kmh = toKmh(rad);
    double time = toSec(Integer.parseInt(dp.getTime()));

    if(measRPM)
      rpm = toUmin(Integer.parseInt(dp.getRpm()));

    measureList.add(new Datapoint(rad, rpm, time));

//    if(measRPM && rpm < data.getStartRPM())
//      stopRequest.set(true);
    if(!measRPM && kmh < data.getStartKMH())
      stopRequest.set(true);

    //count - kmh - rpm
    Double[] chunks =
    {
      count * 1.0, kmh, rpm
    };

    publish(chunks);

    LOG.finest("published(" + chunks[0] + "count " + chunks[1] + "km/h " + chunks[2] + "rpm)");
  }

}
