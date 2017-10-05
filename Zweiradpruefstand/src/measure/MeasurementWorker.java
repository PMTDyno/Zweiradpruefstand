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
  private double KmhAtStart;

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
      ex.printStackTrace(System.err);
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
   * @return RawDatapoint
   * @throws CommunicationException
   * @throws TimeoutException
   * @throws InterruptedException
   */
  private RawDatapoint waitUntilStart() throws CommunicationException,
                                               TimeoutException,
                                               InterruptedException
  {

    RawDatapoint dp;
    double rpm = 0;             // U/min
    double kmh = 0;             // Km/h
    double hysteresisTime = data.getHysteresisTIME();  // ms

    double tmp = hysteresisTime / data.getPeriodTimeMs();
    int hysteresisCount = (int) tmp;

    int accepted = 0;

    com.setStatus("HOCHSCHALTEN");

    if(measRPM)
    {
      int hysteresisMin = data.getIdleRPM() - data.getHysteresisRPM();
      int hysteresisMax = data.getIdleRPM() + data.getHysteresisRPM();

      //wait until high rpm is reached once
      do
      {
        if(isCancelled())
        {
          LOG.finest("Cancel triggered!");
          return null;
        }
        com.sendFrame(Communication.Request.START);
        dp = getNextDatapoint();
        if(Integer.parseInt(dp.getRpm()) != 0)
          rpm = toUmin(Integer.parseInt(dp.getRpm()));
        else
          rpm = 0;

        setDials(dp);

        Thread.sleep(data.getPeriodTimeMs());

      } while(rpm < data.getStartRPM());

      LOG.info("High RPM reached once");
      LOG.info("Entering Hysteresis Loop now...");

      com.setStatus("WARTEN");

      //rpm hysteresis for hysteresisTime seconds then ready
      do
      {
        if(isCancelled())
        {
          LOG.finest("Cancel triggered!");
          return null;
        }
        com.sendFrame(Communication.Request.START);
        dp = getNextDatapoint();

        if(Integer.parseInt(dp.getRpm()) != 0)
          rpm = toUmin(Integer.parseInt(dp.getRpm()));
        else
          rpm = 0;

        if(rpm > hysteresisMin && rpm < hysteresisMax)
        {
          accepted++;
          if((accepted % 2) == 0)
            LOG.fine("Accepted Hysteresis " + accepted + " of " + hysteresisCount);
        }
        else
        {
          if(accepted != 0)
            LOG.fine("Resetting Accepted Hysteresis");

          accepted = 0;
        }
        Thread.sleep(data.getPeriodTimeMs());
        setDials(dp);

      } while(accepted <= hysteresisCount);

      LOG.info("Hysteresis finished - ready for start");

    }
    else
    {

      int hysteresisMin = data.getIdleKMH() - data.getHysteresisKMH();
      int hysteresisMax = data.getIdleKMH() + data.getHysteresisKMH();

      //wait until high kmh is reached once
      do
      {
        if(isCancelled())
        {
          LOG.finest("Cancel triggered!");
          return null;
        }
        com.sendFrame(Communication.Request.STARTNORPM);
        dp = getNextDatapoint();

        if(Integer.parseInt(dp.getWss()) != 0)
          kmh = toKmh(toRads(Integer.parseInt(dp.getWss())));
        else
          kmh = 0;

        setDials(dp);

        Thread.sleep(data.getPeriodTimeMs());

      } while(kmh < 8);

      LOG.info("High Speed(8Km/h) reached once");
      LOG.info("Entering Hysteresis Loop now...");

      com.setStatus("WARTEN");

      //kmh hysteresis for hysteresisTime seconds then ready
      do
      {
        if(isCancelled())
        {
          LOG.finest("Cancel triggered!");
          return null;
        }
        com.sendFrame(Communication.Request.STARTNORPM);
        dp = getNextDatapoint();

        if(Integer.parseInt(dp.getWss()) != 0)
          kmh = toKmh(toRads(Integer.parseInt(dp.getWss())));
        else
          kmh = 0;

        if(kmh > hysteresisMin && kmh < hysteresisMax)
        {
          accepted++;
          if((accepted % 10) == 0)
            LOG.fine("Accepted Hysteresis " + accepted + " of " + hysteresisCount);
        }
        else
        {
          if(accepted != 0)
            LOG.fine("Resetting Accepted Hysteresis");

          accepted = 0;
        }
        Thread.sleep(data.getPeriodTimeMs());
        setDials(dp);

      } while(accepted >= hysteresisCount);

      LOG.info("Hysteresis finished - ready for start");
    }

    com.setStatus("BEREIT");

    do
    {
      if(isCancelled())
      {
        LOG.finest("Cancel triggered!");
        return null;
      }

      if(measRPM)
      {
        com.sendFrame(Communication.Request.START);
        dp = getNextDatapoint();

        if(Integer.parseInt(dp.getRpm()) != 0)
          rpm = toUmin(Integer.parseInt(dp.getRpm()));
        else
          rpm = 0;

      }
      else
      {
        com.sendFrame(Communication.Request.STARTNORPM);
        dp = getNextDatapoint();

        if(Integer.parseInt(dp.getWss()) != 0)
          kmh = toKmh(toRads(Integer.parseInt(dp.getWss())));
        else
          kmh = 0;

      }

      Thread.sleep(data.getPeriodTimeMs());
      setDials(dp);

    } while(rpm < data.getStartRPM() && kmh < data.getStartKMH());

    KmhAtStart = kmh;

    com.setStatus("LÄUFT");

    return dp;
  }

  /**
   * This method is responsible for the main measurement.
   *
   * @return ArrayList of Datapoint
   * @throws CommunicationException
   * @throws TimeoutException
   * @throws InterruptedException
   */
  private ArrayList<Datapoint> collectData() throws CommunicationException,
                                                    TimeoutException,
                                                    InterruptedException
  {
    RawDatapoint dp;

    boolean clutchActivated = true;
    int clutchCount = 0;
    double maxUmin = 0;

    while(true)
    {

      if(clutchActivated && measRPM)
      {
        com.sendFrame(Communication.Request.MEASURE);
        dp = getNextDatapoint();
        double rpm = toUmin(Integer.parseInt(dp.getRpm()));

        Thread.sleep(data.getPeriodTimeMs());

        count++;
        rawList.add(dp);
        addAndPublish(dp);

        if(rpm > maxUmin)
        {
          maxUmin = rpm;
          clutchCount = 0;
          continue;
        }
        else
        {
          clutchCount++;
          LOG.fine("Clutch Count: %d", clutchCount);
        }

        if(clutchCount >= 5)
        {
          clutchActivated = false;
          LOG.info("CLUTCH DEACTIVATED");
        }
        else
        {
          continue;
        }

      }

      int stopCount = 0;
      do
      {
//        if(measRPM)
//          com.sendFrame(Communication.Request.MEASURE);
//        else
        com.sendFrame(Communication.Request.MEASURENORPM);

        dp = getNextDatapoint();
        if(toKmh(toRads(Integer.parseInt(dp.getWss()))) < KmhAtStart)
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

      //Thread.sleep(data.getPeriodTimeMs());
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

  }

  private void setDials(RawDatapoint dp)
  {

    double rpm = 0.0;
    double rad = toRads(Integer.parseInt(dp.getWss()));
    double kmh = toKmh(rad);
    double time = toSec(Integer.parseInt(dp.getTime()));

    if(Integer.parseInt(dp.getWss()) == 0)
      kmh = 0;

    if(measRPM && Integer.parseInt(dp.getRpm()) != 0)
      rpm = toUmin(Integer.parseInt(dp.getRpm()));

    Double[] chunks =
    {
      0.0, kmh, rpm
    };

    publish(chunks);

    //LOG.finest("published(" + chunks[0] + "count " + chunks[1] + "km/h " + chunks[2] + "rpm)");
  }

}
