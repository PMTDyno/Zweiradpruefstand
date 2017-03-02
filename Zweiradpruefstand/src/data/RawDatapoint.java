package data;

/**
 *
 * @author levin
 */
public class RawDatapoint
{

  private String wss;  // µs
  private String rpm;  // µs
  private String time; // µs

  /**
   *
   * @param wss  Wellendrehzahl in us
   * @param rpm  Motordrehzahl in us
   * @param time Zeitpunkt der Messung in us
   */
  public RawDatapoint(String wss, String rpm, String time)
  {
    this.wss = wss;
    this.rpm = rpm;
    this.time = time;
  }

  public String getWss()
  {
    return wss;
  }

  public String getRpm()
  {
    return rpm;
  }

  public String getTime()
  {
    return time;
  }

  public void setTime(String time)
  {
    this.time = time;
  }

  public void setWss(String wss)
  {
    this.wss = wss;
  }

  public void setRpm(String rpm)
  {
    this.rpm = rpm;
  }
  
  

}
