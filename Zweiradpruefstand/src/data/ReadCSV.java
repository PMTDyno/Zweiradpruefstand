package data;

import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayList;
import measure.Datapoint;


/**
 *
 * @author levin
 */
public class ReadCSV
{

  private final File file;

  public ReadCSV (String path) throws Exception
  {    
    file = new File(path);
    if (!file.exists())
    {
      throw new IllegalArgumentException("File does not exist");
    }
    if (!file.isFile())
    {
      throw new IllegalArgumentException("Path is not a file");
    }
    if (!file.canRead())
    {
      throw new IllegalArgumentException("File is not able to be read.");
    }

  }


  /**
   * reads the csv and stores it in the data singleton
   * @return the list of datapoints
   * @throws java.lang.Exception
   */
  public ArrayList<Datapoint> read () throws Exception
  {
//    ArrayList<Datapoint> al = new ArrayList<>();
//    data.setMeasureList(al);
    
    ArrayList<Datapoint> list = new ArrayList<>();
    
    try (BufferedReader r = new BufferedReader(new java.io.FileReader(file)))
    {
      while (r.ready())
      {
        String tmp = r.readLine();
        String[] numbers = tmp.split(":");
        // time - mdz - wdz

        double wdz = (1 / (Double.parseDouble(numbers[2].replace(",", ".")) * 26)) * 2 * Math.PI;
        double mdz = 1 / Double.parseDouble(numbers[1].replace(",", ".")) * 60;
        double time = Double.parseDouble(numbers[0].replace(",", "."));

        list.add(new Datapoint(wdz, mdz, time));
      }

      r.close();
    }
    
    return list;
    
  }

}
