package data;

/**
 *
 * @author levin
 */
public class Datapoint
{

  private double wdz;  // rad/s
  private double rpm;  // U/min
  private double time; // 

  /**
   *
   * @param wdz  Wellendrehzahl
   * @param rpm  Motordrehzahl
   * @param time Zeitpunkt der Messung
   */
  public Datapoint(double wdz, double rpm, double time)
  {
    this.wdz = wdz;
    this.rpm = rpm;
    this.time = time;
  }

  public double getWdz()
  {
    return wdz;
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

  public void setWdz(double wdz)
  {
    this.wdz = wdz;
  }

  public void setRpm(double rpm)
  {
    this.rpm = rpm;
  }
  
  

}
