package data;

/**
 *
 * @author levin
 */
public class RawDatapoint
{

  private double wdz;  // µs
  private double rpm;  // µs
  private double time; // µs

  /**
   *
   * @param wdz  Wellendrehzahl in us
   * @param rpm  Motordrehzahl in us
   * @param time Zeitpunkt der Messung in us
   */
  public RawDatapoint(double wdz, double rpm, double time)
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
