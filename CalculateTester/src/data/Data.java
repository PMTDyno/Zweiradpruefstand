package data;

import java.util.ArrayList;

/**
 * Stores the main data
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
  public static Data getInstance()
  {
    if(instance == null)
    {
      instance = new Data();
    }
    return instance;
  }

  private Data()
  {
  }

  private String filePath = "Error im Benutzerverzeichnis beheben";
  private String vehicle = "Puch M50 SG";
  private String powerunit = "PS";

  private double inertia = 3.7017;
  private double vmax = 0;
  private double temperature = 20;
  private double pressure = 1013;
  private double correctionPower = 1.0;
  private double correctionTorque = 1.0;
  private double maxpower = 0.0;
  private double maxtorque = 0.0;

  private int startKMH = 10;
  private int startRPM = 3200;
  private int idleKMH = 4;
  private int idleRPM = 2500;
  private int pngWidth = 800;
  private int pngHeight = 600;
  private int windowWidth = 1200;
  private int windowHeight = 700;
  private int windowRelativeX = 0;
  private int windowRelativeY = 0;
  private int periodTimeMs = 20;
  private int humidity = 40;
  private int hysteresisRPM = 400;
  private int hysteresisKMH = 2;
  private int hysteresisTIME = 3000;

  private double filterTrqSmoothing = 0.4;
  private int filterTrqOrder = 4;

  private double filterOmegaSmoothing = 0.5;
  private int filterOmegaOrder = 1;

  private double filterAlphaSmoothing = 0.4;
  private int filterAlphaOrder = 4;

  private double filterRpmSmoothing = 0.5;
  private int filterRpmOrder = 2;

  private ArrayList<RawDatapoint> rawDataList = new ArrayList<>();
  private ArrayList<Datapoint> measureList = null;

  private boolean twoStroke = true;
  private boolean measRPM = true;
  private boolean automatic = false;
  private boolean schleppEnable = true;

  /*
   * ------------------------------------------------------------------------
   */
  public ArrayList<Datapoint> getMeasureList()
  {
    return measureList;
  }

  public double getFilterTrqSmoothing()
  {
    return filterTrqSmoothing;
  }

  public void setFilterTrqSmoothing(double filterTrqSmoothing)
  {
    this.filterTrqSmoothing = filterTrqSmoothing;
  }

  public int getFilterTrqOrder()
  {
    return filterTrqOrder;
  }

  public void setFilterTrqOrder(int filterTrqOrder)
  {
    this.filterTrqOrder = filterTrqOrder;
  }

  public double getFilterRpmSmoothing()
  {
    return filterRpmSmoothing;
  }

  public void setFilterRpmSmoothing(double filterRpmSmoothing)
  {
    this.filterRpmSmoothing = filterRpmSmoothing;
  }

  public int getFilterRpmOrder()
  {
    return filterRpmOrder;
  }

  public void setFilterRpmOrder(int filterRpmOrder)
  {
    this.filterRpmOrder = filterRpmOrder;
  }

  public double getFilterOmegaSmoothing()
  {
    return filterOmegaSmoothing;
  }

  public void setFilterOmegaSmoothing(double filterOmegaSmoothing)
  {
    this.filterOmegaSmoothing = filterOmegaSmoothing;
  }

  public int getFilterOmegaOrder()
  {
    return filterOmegaOrder;
  }

  public void setFilterOmegaOrder(int filterOmegaOrder)
  {
    this.filterOmegaOrder = filterOmegaOrder;
  }

  public double getFilterAlphaSmoothing()
  {
    return filterAlphaSmoothing;
  }

  public void setFilterAlphaSmoothing(double filterAlphaSmoothing)
  {
    this.filterAlphaSmoothing = filterAlphaSmoothing;
  }

  public int getFilterAlphaOrder()
  {
    return filterAlphaOrder;
  }

  public void setFilterAlphaOrder(int filterAlphaOrder)
  {
    this.filterAlphaOrder = filterAlphaOrder;
  }

  public void setMeasureList(ArrayList<Datapoint> measureList)
  {
    this.measureList = measureList;
  }

  public int getIdleRPM()
  {
    return idleRPM;
  }

  public int getHysteresisTIME()
  {
    return hysteresisTIME;
  }

  public void setHysteresisTIME(int hysteresisTIME)
  {
    this.hysteresisTIME = hysteresisTIME;
  }

  public int getHysteresisRPM()
  {
    return hysteresisRPM;
  }

  public int getIdleKMH()
  {
    return idleKMH;
  }

  public void setIdleKMH(int idleKMH)
  {
    this.idleKMH = idleKMH;
  }

  public int getHysteresisKMH()
  {
    return hysteresisKMH;
  }

  public void setHysteresisKMH(int hysteresisKMH)
  {
    this.hysteresisKMH = hysteresisKMH;
  }

  public void setHysteresisRPM(int hysteresisRPM)
  {
    this.hysteresisRPM = hysteresisRPM;
  }

  public void setIdleRPM(int idleRPM)
  {
    this.idleRPM = idleRPM;
  }

  public ArrayList<RawDatapoint> getRawDataList()
  {
    return rawDataList;
  }

  public void setRawDataList(ArrayList<RawDatapoint> rawDataList)
  {
    this.rawDataList = rawDataList;
  }

  public boolean isAutomatic()
  {
    return automatic;
  }

  public void setAutomatic(boolean automatic)
  {
    this.automatic = automatic;
  }

  public int getStartKMH()
  {
    return startKMH;
  }

  public void setStartKMH(int startKMH)
  {
    this.startKMH = startKMH;
  }

  public int getStartRPM()
  {
    return startRPM;
  }

  public void setStartRPM(int startRPM)
  {
    this.startRPM = startRPM;
  }

  public boolean isSchleppEnable()
  {
    return schleppEnable;
  }

  public void setSchleppEnable(boolean schleppEnable)
  {
    this.schleppEnable = schleppEnable;
  }

  public boolean isMeasRPM()
  {
    return measRPM;
  }

  public void setMeasRPM(boolean measRPM)
  {
    this.measRPM = measRPM;
  }

  public String getFilePath()
  {
    return filePath;
  }

  public void setFilePath(String filePath)
  {
    this.filePath = filePath;
  }

  public String getVehicle()
  {
    return vehicle;
  }

  public void setVehicle(String vehicle)
  {
    this.vehicle = vehicle;
  }

  public String getPowerunit()
  {
    return powerunit;
  }

  public void setPowerunit(String powerunit)
  {
    this.powerunit = powerunit;
  }

  public double getInertia()
  {
    return inertia;
  }

  public void setInertia(double inertia)
  {
    this.inertia = inertia;
  }

  public double getTemperature()
  {
    return temperature;
  }

  public void setTemperature(double temperature)
  {
    this.temperature = temperature;
  }

  public double getPressure()
  {
    return pressure;
  }

  public void setPressure(double pressure)
  {
    this.pressure = pressure;
  }

  public double getCorrectionPower()
  {
    return correctionPower;
  }

  public void setCorrectionPower(double correctionPower)
  {
    this.correctionPower = correctionPower;
  }

  public double getCorrectionTorque()
  {
    return correctionTorque;
  }

  public void setCorrectionTorque(double correctionTorque)
  {
    this.correctionTorque = correctionTorque;
  }

  public double getMaxpower()
  {
    return maxpower;
  }

  public void setMaxpower(double maxpower)
  {
    this.maxpower = maxpower;
  }

  public double getMaxtorque()
  {
    return maxtorque;
  }

  public void setMaxtorque(double maxtorque)
  {
    this.maxtorque = maxtorque;
  }

  public int getPngWidth()
  {
    return pngWidth;
  }

  public void setPngWidth(int pngWidth)
  {
    this.pngWidth = pngWidth;
  }

  public int getPngHeight()
  {
    return pngHeight;
  }

  public void setPngHeight(int pngHeight)
  {
    this.pngHeight = pngHeight;
  }

  public int getWindowWidth()
  {
    return windowWidth;
  }

  public void setWindowWidth(int windowWidth)
  {
    this.windowWidth = windowWidth;
  }

  public int getWindowHeight()
  {
    return windowHeight;
  }

  public void setWindowHeight(int windowHeight)
  {
    this.windowHeight = windowHeight;
  }

  public int getWindowRelativeX()
  {
    return windowRelativeX;
  }

  public void setWindowRelativeX(int windowRelativeX)
  {
    this.windowRelativeX = windowRelativeX;
  }

  public int getWindowRelativeY()
  {
    return windowRelativeY;
  }

  public void setWindowRelativeY(int windowRelativeY)
  {
    this.windowRelativeY = windowRelativeY;
  }

  public int getPeriodTimeMs()
  {
    return periodTimeMs;
  }

  public void setPeriodTimeMs(int periodTimeMs)
  {
    this.periodTimeMs = periodTimeMs;
  }

  public int getHumidity()
  {
    return humidity;
  }

  public void setHumidity(int humidity)
  {
    this.humidity = humidity;
  }

  public double getVmax()
  {
    if(vmax == 0)
    {
      return -1;
    }

    return vmax;
  }

  public boolean isTwoStroke()
  {
    return twoStroke;
  }

  public void setTwoStroke(boolean twoStroke)
  {
    this.twoStroke = twoStroke;
  }

  public void setVmax(double vmax)
  {
    this.vmax = vmax;
  }

}
