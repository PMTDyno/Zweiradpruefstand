package data;

/**
 *
 * @author levin
 */
public class Datapoint
{

  private double wss;  // rad/s
  private double rpm;  // U/min
  private double time; // 

  /**
   *
   * @param wss  Wellendrehzahl
   * @param rpm  Motordrehzahl
   * @param time Zeitpunkt der Messung
   */
  public Datapoint(double wss, double rpm, double time)
  {
    this.wss = wss;
    this.rpm = rpm;
    this.time = time;
  }

  public double getWss()
  {
    return wss;
  }

  public double getRpm()
  {
    return rpm;
  }

  public double getTime()
  {
    return time;
  }

  public void setTime(double time)
  {
    this.time = time;
  }

  public void setWss(double wss)
  {
    this.wss = wss;
  }

  public void setRpm(double rpm)
  {
    this.rpm = rpm;
  }
  
  

}
