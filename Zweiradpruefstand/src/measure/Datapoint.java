package measure;


/**
 *
 * @author levin
 */
public class Datapoint
{

  private final double wdz, mdz;
  private final int time;


  /**
   *
   * @param wdz Wellendrehzahl
   * @param mdz Motordrehzahl
   * @param time Zeitpunkt der Messung
   */
  public Datapoint (double wdz, double mdz, int time)
  {
    this.wdz = wdz;
    this.mdz = mdz;
    this.time = time;
  }


  public double getWdz ()
  {
    return wdz;
  }


  public double getMdz ()
  {
    return mdz;
  }


  public int getTime ()
  {
    return time;
  }

  
  
  

}
