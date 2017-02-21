package data;

/**
 *
 * @author levin
 */
public class RawDatapoint
{

  private double wdz;  // µs
  private double mdz;  // µs
  private double time; // µs

  /**
   *
   * @param wdz  Wellendrehzahl in us
   * @param mdz  Motordrehzahl in us
   * @param time Zeitpunkt der Messung in us
   */
  public RawDatapoint(double wdz, double mdz, double time)
  {
    this.wdz = wdz;
    this.mdz = mdz;
    this.time = time;
  }

  public double getWdz()
  {
    return wdz;
  }

  public double getMdz()
  {
    return mdz;
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

  public void setMdz(double mdz)
  {
    this.mdz = mdz;
  }
  
  

}
