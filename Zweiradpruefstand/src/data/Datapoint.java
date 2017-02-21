package data;

/**
 *
 * @author levin
 */
public class Datapoint
{

  private double wdz;  // rad/s
  private double mdz;  // U/min
  private double time; // 

  /**
   *
   * @param wdz  Wellendrehzahl
   * @param mdz  Motordrehzahl
   * @param time Zeitpunkt der Messung
   */
  public Datapoint(double wdz, double mdz, double time)
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
