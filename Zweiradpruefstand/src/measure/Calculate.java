package measure;

import data.Data;
import data.Datapoint;
import java.util.ArrayList;
import logging.Logger;
import org.jfree.data.xy.XYSeries;

/**
 *
 * @author Levin Messing
 */
public class Calculate
{

  private static final Logger LOG = Logger.getLogger(Calculate.class.getName());
  private static final java.util.logging.Level DEBUGLEVEL = java.util.logging.Level.ALL;

  private final Data data = Data.getInstance();

  private XYSeries seriesPower = new XYSeries("Power Final");
  private XYSeries seriesTorque = new XYSeries("Torque Final");

  private ArrayList<Double> trq = new ArrayList<>();
  private ArrayList<Double> trqNoFilter = new ArrayList<>();
  private ArrayList<Double> trqSchl = new ArrayList<>(); //schleppmoment

  private ArrayList<Double> rpm = new ArrayList<>(); //in U/min
  private ArrayList<Double> rpmNoFilter = new ArrayList<>();

  private ArrayList<Double> time = new ArrayList<>(); //in s
  private ArrayList<Double> pwr = new ArrayList<>();

  private ArrayList<Double> alpha = new ArrayList<>();
  private ArrayList<Double> alphaNoFilter = new ArrayList<>();

  private ArrayList<Double> omega = new ArrayList<>();
  private ArrayList<Double> omegaNoFilter = new ArrayList<>();

  private ArrayList<Double> omegaSchl = new ArrayList<>();

  public Calculate()
  {
    LOG.setDebugLevel(DEBUGLEVEL);

  }

  public XYSeries getSeriesTrq()
  {
    return convertListToSeries(trq, "Torque");
  }

  public XYSeries getSeriesTrqNoFilter()
  {
    return convertListToSeries(trqNoFilter, "Torque No Filter");
  }

  public XYSeries getSeriesOmega()
  {
    return convertListToSeries(omega, "Omega");
  }

  public XYSeries getSeriesOmegaNoFilter()
  {
    return convertListToSeries(omegaNoFilter, "Omega No Filter");
  }

  public XYSeries getSeriesAlpha()
  {
    return convertListToSeries(alpha, "Alpha");
  }

  public XYSeries getSeriesAlphaNoFilter()
  {
    return convertListToSeries(alphaNoFilter, "Alpha No Filter");
  }

  public XYSeries getSeriesRpm()
  {
    return convertListToSeries(rpm, "Rpm");
  }

  public XYSeries getSeriesRpmNoFilter()
  {
    return convertListToSeries(rpmNoFilter, "Rpm No Filter");
  }

  private XYSeries convertListToSeries(ArrayList<Double> list, String key)
  {
    XYSeries tmp = new XYSeries(key);

    for(int i = 0; i < list.size(); i++)
    {
      tmp.add(time.get(i), list.get(i));
    }

    return tmp;
  }

  //--------------------------------------------
  private int getValMaxIndex(ArrayList<Double> aL)
  {
    int valMax = 0;
    int i;
    for(i = 0; i < aL.size(); i++)
    {
      if(aL.get(i) > aL.get(valMax))
      {
        valMax = i;
      }
    }
    return valMax;
  }

  private ArrayList<Double> filterValuesOrder(ArrayList<Double> aL, double smoothing, int order)
  {
    for(int i = 0; i < order; i++)
    {
      aL = filterValues(aL, smoothing);
    }

    return aL;
  }

  private ArrayList<Double> filterValues(ArrayList<Double> aL, double smoothing) //higher smoothingvalue means more smoothing
  {
    ArrayList<Double> smoothedValues = new ArrayList<>();
    smoothedValues.add(aL.get(0));
    for(int i = 0; i < aL.size() - 1; i++)
    {
//      System.out.println(i);
      smoothedValues.add((1 - smoothing) * smoothedValues.get(i) + smoothing * aL.get(i + 1));
    }

    return smoothedValues;

  }

  /**
   * calculates Power and Torque and updates the Chart
   *
   * @return
   */
  public XYSeries[] calc() throws ArithmeticException
  {

    //LOG.fine("calculating...");
    double inertia = data.getInertia();
    double n; //uebersetzungsverhaeltnis rolle zu motor

    boolean schleppEnable = data.isSchleppEnable();

    double tempFactor = (1013 / data.getPressure()) * Math.sqrt((273 + data.getTemperature()) / 293); //Korrekturfaktor temp

    int removeCount = 0;

    //removing datapoints over 20000rpm 
    ArrayList<Datapoint> tempList = data.getMeasureList();
    for(int i = 0; i < tempList.size(); i++)
    {
      if(tempList.get(i).getRpm() > 20000)
      {
        tempList.remove(i);
        removeCount++;
        i--;
      }
    }

    data.setMeasureList(tempList);

    if(removeCount > 1)
      LOG.info("Removed " + removeCount + " Datapoints which are over 20000Rpm");

    //add values and filter wss
    //    LOG.debug("RPM1: " + data.getMeasureList().get(1).getRpm());
    for(int i = 0; i < data.getMeasureList().size() - 1; i++)
    {
      omega.add(data.getMeasureList().get(i).getWss());
      rpm.add(data.getMeasureList().get(i).getRpm());
      time.add(data.getMeasureList().get(i).getTime());
    }

    omegaNoFilter = omega;
    omega = filterValuesOrder(omega, data.getFilterOmegaSmoothing(), data.getFilterOmegaOrder());

    //VMAX ermitteln
    data.setVmax(omega.get(getValMaxIndex(omega)) * 0.175 * 3.6);

    //alpha berechnen
    //aenderung der drehzahl zur zeit
    for(int i = 0; i < omega.size() - 1; i++)
    {
      alpha.add((omega.get(i + 1) - omega.get(i)) / (time.get(i + 1) - time.get(i)));
    }

    alphaNoFilter = alpha;
    alpha = filterValuesOrder(alpha, data.getFilterAlphaSmoothing(), data.getFilterAlphaOrder());

    //faktor fuer berechnungseinheit
    double unitFactor;

    if(data.getPowerunit().contains("PS"))
    {
      unitFactor = 1.36;
    }
    else
    {
      unitFactor = 1;
    }

    //drehmoment roller
    if(!data.isMeasRPM())
    {

      for(int i = 0; i < alpha.size(); i++)
      {
        trq.add(alpha.get(i) * inertia);  //M=dOmega/dt * J

      }
    }
    else//drehmoment kein roller
    {
      rpmNoFilter = rpm;

      rpm = filterValuesOrder(rpm, data.getFilterRpmSmoothing(), data.getFilterRpmOrder());
      //uebersetzungsverhaeltnis:
      for(int i = 0; i < alpha.size(); i++)
      {
        //moment
        try
        {
          trq.add(alpha.get(i) * inertia * ((omega.get(i) / (rpm.get(i) / 60 * 2 * 3.14)) + (omega.get(i + 1) / (rpm.get(i + 1) / 60 * 2 * Math.PI))) / 2);  //M=dOmega/dt * J
       

        }
        catch (IndexOutOfBoundsException ex)
        {

          throw new ArithmeticException("Zu wenig Messwerte! (" + omega.size() + ')');

        }
      }
    }

    //index ermitteln, ab dem moment negativ ist und somit schleppmoment vorhanden ist
    int limitSchl = 0;
    try
    {
      for(limitSchl = getValMaxIndex(omega); true; limitSchl++)
      {
        if(trq.get(limitSchl) < 0)
        {
          break;
        }
      }
    }
    catch (IndexOutOfBoundsException ex)
    {
      schleppEnable = false;
      LOG.info("No Towing Torque");
      LOG.info("Berechnung erfolgt ohne Berücksichtigung des Schleppmoments");
    }

    //schleppmoment zu motormoment addieren, falls schleppmoment vorhanden ist
    if(schleppEnable)
    {
      trqSchl = new ArrayList<>(trq.subList(limitSchl, trq.size()));
      omegaSchl = new ArrayList<>(omega.subList(limitSchl, omega.size()));
//      ArrayList<Double> timeSchl = new ArrayList<>(time.subList(limitSchl, time.size()));
      
      double temp2;
      int min=0,i,i2;
      for(i2 = 0; i2 < limitSchl; i2++)
      {
        System.out.println("i2="+i2);
        double temp=100000;
          for(i = 0; i < omegaSchl.size(); i++)
          {
            temp2=(omega.get(i2)-omegaSchl.get(i));
            System.out.println("temp2="+temp2);
            if (temp2<temp && temp2>0)
            {
              System.out.println("omegaSchl="+omegaSchl.get(i)+" trqSchl="+trqSchl.get(i));
              temp=temp2;
              min=i;
            } 
          }  
          //
          if(trqSchl.get(min) < 0)
            {
              
              trq.set(i2, (trq.get(i2) + trqSchl.get(min) * -1));
            }
         }
      }

      trqNoFilter = trq;
      trq = filterValuesOrder(trq, data.getFilterTrqSmoothing(), data.getFilterTrqOrder());
    

    //leistung berechnen
    if(!data.isMeasRPM())
    {
      for(int i = 0; i < trq.size(); i++)
      {
        pwr.add((trq.get(i) * omega.get(i) / 1000) * unitFactor * tempFactor);
      }
    }
    else
    {
      for(int i = 0; i < trq.size(); i++)
      {
        pwr.add((trq.get(i) * ((rpm.get(i) / 60) * (2 * Math.PI)) / 1000) * unitFactor * tempFactor);
      }
    }

    if(!data.isMeasRPM() || data.isAutomatic())
    {
      for(int i = 0; i < trq.size(); i++)
      {
//        System.out.println(i + " Leistung: " + pwr.get(i) + " Drehmoment: " + trq.get(i) + " Geschwindigkeit: " + (omega.get(i) * 0.175 * 3.6));
        if(i == getValMaxIndex(omega))
        {
          break;
        }
        seriesPower.add(omega.get(i) * 0.175 * 3.6, pwr.get(i));
        seriesTorque.add(omega.get(i) * 0.175 * 3.6, trq.get(i));

      }
    }
    else
    {
      for(int i = 0; i < trq.size(); i++)
      {
//        System.out.println(i + " Leistung: " + pwr.get(i) + " Drehmoment: " + trq.get(i) + " Motordrehzahl: " + rpm.get(i));
        if(i == getValMaxIndex(rpm))
        {
          break;
        }
        seriesPower.add(rpm.get(i), pwr.get(i));
        seriesTorque.add(rpm.get(i), trq.get(i));
       // seriesPower.add(time.get(i), omega.get(i));
        //seriesTorque.add(time.get(i), trq.get(i));

      }
    }

    //LOG.fine("done calculating");
    XYSeries[] rv =
    {
      seriesPower, seriesTorque
    };

    return rv;

  }

}
