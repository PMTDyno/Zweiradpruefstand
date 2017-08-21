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

  /**
   *
   * @return wss in us
   */
  public String getWss()
  {
    return wss;
  }

  /**
   *
   * @return rpm in us
   */
  public String getRpm()
  {
    return rpm;
  }

  /**
   *
   * @return time in us
   */
  public String getTime()
  {
    return time;
  }

  /**
   *
   * @param time time in us
   */
  public void setTime(String time)
  {
    this.time = time;
  }

  /**
   *
   * @param wss wss in us
   */
  public void setWss(String wss)
  {
    this.wss = wss;
  }

  /**
   *
   * @param rpm rpm in us
   */
  public void setRpm(String rpm)
  {
    this.rpm = rpm;
  }

}
