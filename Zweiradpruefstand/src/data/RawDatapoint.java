package data;

/**
 *
 * @author levin
 */
public class RawDatapoint
{

  private double wss;  // µs
  private double rpm;  // µs
  private double time; // µs

  /**
   *
   * @param wss  Wellendrehzahl in us
   * @param rpm  Motordrehzahl in us
   * @param time Zeitpunkt der Messung in us
   */
  public RawDatapoint(double wss, double rpm, double time)
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
