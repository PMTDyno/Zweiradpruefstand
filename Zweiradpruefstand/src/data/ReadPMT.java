package data;

import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author levin
 */
public class ReadPMT
{

  Data data;
  private final File file;

  public ReadPMT(File file) throws Exception
  {
    data = Data.getInstance();
//    System.out.println(file);
    this.file = file;
    if(!file.exists())
    {
      throw new IllegalArgumentException("File does not exist");
    }
    if(!file.isFile())
    {
      throw new IllegalArgumentException("Path is not a file");
    }
    if(!file.canRead())
    {
      throw new IllegalArgumentException("File is not able to be read.");
    }

  }

  public ReadPMT(String path) throws Exception
  {
    data = Data.getInstance();
    file = new File(path);
    if(!file.exists())
    {
      throw new IllegalArgumentException("File does not exist");
    }
    if(!file.isFile())
    {
      throw new IllegalArgumentException("Path is not a file");
    }
    if(!file.canRead())
    {
      throw new IllegalArgumentException("File is not able to be read.");
    }

  }

  /**
   * reads the csv and stores it in the data singleton
   *
   * @return the list of datapoints
   * @throws java.lang.Exception
   */
  public ArrayList<Datapoint> read() throws Exception
  {
//    ArrayList<Datapoint> al = new ArrayList<>();
//    data.setMeasureList(al);

    ArrayList<Datapoint> list = new ArrayList<>();

    try (BufferedReader r = new BufferedReader(new java.io.FileReader(file)))
    {
      while(r.ready())
      {
        String tmp = r.readLine();
        String[] numbers = tmp.split(":");
        // time - rpm - wss
        double rpm;
        double wss = (1 / (Double.parseDouble(numbers[2].replace(",", ".")) * 26)) * 2 * Math.PI;
        
        if(data.isTwoStroke())
          rpm = 1 / Double.parseDouble(numbers[1].replace(",", ".")) * 60;
        else
          rpm = (1 / Double.parseDouble(numbers[1].replace(",", ".")) * 60) * 2;

        double time = Double.parseDouble(numbers[0].replace(",", "."));

        list.add(new Datapoint(wss, rpm, time));
      }

      r.close();
    }

    return list;

  }

}
