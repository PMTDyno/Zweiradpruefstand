package data;

import java.util.ArrayList;
import measure.Datapoint;


/**
 * Stores the main data variables
 *
 * @author Levin Messing (meslem12@htl-kaindorf.ac.at)
 */
public class Data
{

  private static Data instance = null;


  /**
   *
   * @return The Data Instance
   */
  public static Data getInstance ()
  {
    if (instance == null)
    {
      instance = new Data();
    }
    return instance;
  }


  private Data ()
  {
  }


  private String filePath = "Error im Benutzerpfad beheben";
  private String vehicle = "Honda CWF 125";
  private String powerunit = "PS";

  private double inertia = 3.7017;
  private double vmax;
  private double temperature;
  private double pressure;
  private double correctionPower = 1.0;
  private double correctionTorque = 1.0;
  private double maxpower = 0.0;
  private double maxtorque = 0.0;
  private double[] power;
  private double[] torque;

  private int pngWidth = 800;
  private int pngHeight = 600;
  private int windowWidth = 1200;
  private int windowHeight = 700;
  private int windowRelativeX = 0;
  private int windowRelativeY = 0;
  private int periodTimeMs = 40;
  private int maxMeasureTimeSec = 60;
  private int humidity;

  private int[] wheelRpm;
  private int[] motorRpm;
  private ArrayList<Datapoint> measureList = null;

  private boolean twoStroke = true;


  /*
   * ------------------------------------------------------------------------
   */

  public ArrayList<Datapoint> getMeasureList ()
  {
    return measureList;
  }


  public void setMeasureList (ArrayList<Datapoint> measureList)
  {
    this.measureList = measureList;
  }


  public String getFilePath ()
  {
    return filePath;
  }


  public void setFilePath (String filePath)
  {
    this.filePath = filePath;
  }


  public String getVehicle ()
  {
    return vehicle;
  }


  public void setVehicle (String vehicle)
  {
    this.vehicle = vehicle;
  }


  public String getPowerunit ()
  {
    return powerunit;
  }


  public void setPowerunit (String powerunit)
  {
    this.powerunit = powerunit;
  }


  public double getInertia ()
  {
    return inertia;
  }


  public void setInertia (double inertia)
  {
    this.inertia = inertia;
  }


  public double getTemperature ()
  {
    return temperature;
  }


  public void setTemperature (double temperature)
  {
    this.temperature = temperature;
  }


  public double getPressure ()
  {
    return pressure;
  }


  public void setPressure (double pressure)
  {
    this.pressure = pressure;
  }


  public double getCorrectionPower ()
  {
    return correctionPower;
  }


  public void setCorrectionPower (double correctionPower)
  {
    this.correctionPower = correctionPower;
  }


  public double getCorrectionTorque ()
  {
    return correctionTorque;
  }


  public void setCorrectionTorque (double correctionTorque)
  {
    this.correctionTorque = correctionTorque;
  }


  public double getMaxpower ()
  {
    return maxpower;
  }


  public void setMaxpower (double maxpower)
  {
    this.maxpower = maxpower;
  }


  public double getMaxtorque ()
  {
    return maxtorque;
  }


  public void setMaxtorque (double maxtorque)
  {
    this.maxtorque = maxtorque;
  }


  public double[] getPower ()
  {
    return power;
  }


  public void setPower (double[] power)
  {
    this.power = power;
  }


  public double[] getTorque ()
  {
    return torque;
  }


  public void setTorque (double[] torque)
  {
    this.torque = torque;
  }


  public int getPngWidth ()
  {
    return pngWidth;
  }


  public void setPngWidth (int pngWidth)
  {
    this.pngWidth = pngWidth;
  }


  public int getPngHeight ()
  {
    return pngHeight;
  }


  public void setPngHeight (int pngHeight)
  {
    this.pngHeight = pngHeight;
  }


  public int getWindowWidth ()
  {
    return windowWidth;
  }


  public void setWindowWidth (int windowWidth)
  {
    this.windowWidth = windowWidth;
  }


  public int getWindowHeight ()
  {
    return windowHeight;
  }


  public void setWindowHeight (int windowHeight)
  {
    this.windowHeight = windowHeight;
  }


  public int getWindowRelativeX ()
  {
    return windowRelativeX;
  }


  public void setWindowRelativeX (int windowRelativeX)
  {
    this.windowRelativeX = windowRelativeX;
  }


  public int getWindowRelativeY ()
  {
    return windowRelativeY;
  }


  public void setWindowRelativeY (int windowRelativeY)
  {
    this.windowRelativeY = windowRelativeY;
  }


  public int getPeriodTimeMs ()
  {
    return periodTimeMs;
  }


  public void setPeriodTimeMs (int periodTimeMs)
  {
    this.periodTimeMs = periodTimeMs;
  }


  public int getMaxMeasureTimeSec ()
  {
    return maxMeasureTimeSec;
  }


  public void setMaxMeasureTimeSec (int maxMeasureTimeSec)
  {
    this.maxMeasureTimeSec = maxMeasureTimeSec;
  }


  public int getHumidity ()
  {
    return humidity;
  }


  public void setHumidity (int humidity)
  {
    this.humidity = humidity;
  }


  public int[] getWheelRpm ()
  {
    return wheelRpm;
  }


  public void setWheelRpm (int[] wheelRpm)
  {
    this.wheelRpm = wheelRpm;
  }


  public int[] getMotorRpm ()
  {
    return motorRpm;
  }


  public double getVmax ()
  {
    if (vmax == 0)
    {
      return -1;
    }

    return vmax;
  }


  public void setMotorRpm (int[] motorRpm)
  {
    this.motorRpm = motorRpm;
  }


  public boolean isTwoStroke ()
  {
    return twoStroke;
  }


  public void setTwoStroke (boolean twoStroke)
  {
    this.twoStroke = twoStroke;
  }


  public void setVmax (double vmax)
  {
    this.vmax = vmax;
  }
}
