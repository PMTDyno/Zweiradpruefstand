package data;

import java.awt.HeadlessException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;
import java.util.Objects;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.logging.Level;
import logging.Logger;

/**
 * Writes and reads the config file
 *
 * @author Levin Messing (meslem12@htl-kaindorf.ac.at)
 */
public class Config
{

  private final Data data = Data.getInstance();
  private static final Logger LOG = Logger.getLogger(Config.class.getName());
  private static Config instance = null;

  private Config()
  {
    LOG.setLevel(Level.ALL);
  }

  public static Config getInstance()
  {
    if(instance == null)
    {
      instance = new Config();
    }
    return instance;
  }

  /**
   * reads the config file and saves the configs in the Gui class
   *
   * @throws Exception
   */
  public void load() throws Exception
  {
    File folder;
    File file = null;
    File home;

    try
    {
      home = new File(System.getProperty("user.home"));
    }
    catch (NullPointerException ex)
    {
      home = null;
    }
    try
    {
      if(home != null && home.exists())
      {
        folder = new File(home + "/PMTDyno");
        if(!folder.exists())
          if(!folder.mkdir())
            throw new Exception();

        file = new File(folder + "/PMTDyno.config");
      }
      else
      {
        file = new File("PMTDyno.config");  //created in the path where the program was executed
      }

      if(file.exists())
      {

        try (BufferedReader r = new BufferedReader(new FileReader(file)))
        {
          String line;
          for(int i = 0; i < 9; i++)
          {
            line = r.readLine();

            if(line == null)
              throw new IOException("Fehlende Werte in Config File!");

            String array[] = line.split("=");

            if(array.length != 2)
              throw new IOException("Falsches Format!(Zeile " + i + 1 + ")");

            String type = array[0].trim();  //Leerzeichen entfernen
            String value = array[1].trim();
            type = type.toUpperCase(Locale.GERMAN);

            if(type.isEmpty() || value.isEmpty())
              throw new IOException("Fehlender Wert oder Definition!(Zeile " + i + 1 + ")");

            if(Objects.equals(type, "VEHICLENAME"))
              data.setVehicle(value);
            else if(Objects.equals(type, "POWERUNIT"))
            {
              if(value.equals("PS") || value.equals("kW"))
                data.setPowerunit(value);
              else
                throw new IOException("POWERUNIT hat falschen Wert! (PS oder kW)");
            }
            else if(Objects.equals(type, "TWO_STROKE"))
            {
              if(value.equals("true") || value.equals("false"))
                data.setTwoStroke(Boolean.valueOf(value));
              else
                throw new IOException("TWO_STROKE hat falschen Wert! (true oder false)");
            }
            else if(Objects.equals(type, "PNG_WIDTH"))//min 400
              data.setPngWidth(Integer.parseInt(value));
            else if(Objects.equals(type, "PNG_HEIGHT"))//min 200
              data.setPngHeight(Integer.parseInt(value));
            else if(Objects.equals(type, "WINDOW_WIDTH"))
              data.setWindowWidth(Integer.parseInt(value));
            else if(Objects.equals(type, "WINDOW_HEIGHT"))
              data.setWindowHeight(Integer.parseInt(value));
            else if(Objects.equals(type, "RELATIVE_TO_X"))
              data.setWindowRelativeX(Integer.parseInt(value));
            else if(Objects.equals(type, "RELATIVE_TO_Y"))
              data.setWindowRelativeY(Integer.parseInt(value));
            else
              throw new IOException("Einheit konnte nicht gelesen werden: " + type);

          }
          LOG.finer(String.format("Config file loaded from: %s", file));
          data.setFilePath(file.toPath().toString());
        }

        catch (HeadlessException | IOException | NumberFormatException ex)
        {
          throw ex;
        }
      }
    }
    catch (Exception ex)
    {
      if(file != null)
      {
        throw new Exception("Lesen von PMTConfig (" + file + ") nicht möglich: \n"
                + "Datei manuell reparieren oder löschen!\n"
                + "Programm startet mit zurückgesetzten Werten.", ex);
      }
      else
      {
        throw new Exception("Unbekannter Fehler beim erstellen der Config.Datei", ex);
      }

    }
  }

  /**
   * Creates and saves the config file
   *
   * @throws Exception
   */
  public void save() throws Exception
  {
    File folder;
    File file = null;
    File home;

    try
    {
      home = new File(System.getProperty("user.home"));
    }
    catch (NullPointerException ex)
    {
      home = null;
    }
    try
    {
      if(home != null && home.exists())
      {
        folder = new File(home + "/PMTDyno");
        if(!folder.exists())
          if(!folder.mkdir())
            throw new Exception();

        file = new File(folder + "/PMTDyno.config");
      }
      else
      {
        file = new File("PMTDyno.config");
      }

      try (BufferedWriter w = new BufferedWriter(new FileWriter(file))) //closes itself
      {
        w.write("VEHICLENAME = ");
        w.write(data.getVehicle());
        w.append("\n");

        w.write("POWERUNIT = ");
        w.write(data.getPowerunit());
        w.append("\n");

        w.write("TWO_STROKE = ");
        w.write(Boolean.toString(data.isTwoStroke()));
        w.append("\n");

        w.write("PNG_WIDTH = ");
        w.write(Integer.toString(data.getPngWidth()));
        w.append("\n");

        w.write("PNG_HEIGHT = ");
        w.write(Integer.toString(data.getPngHeight()));
        w.append("\n");

        w.write("WINDOW_WIDTH = ");
        w.write(Integer.toString(data.getWindowWidth()));
        w.append("\n");

        w.write("WINDOW_HEIGHT = ");
        w.write(Integer.toString(data.getWindowHeight()));
        w.append("\n");

        w.write("RELATIVE_TO_X = ");
        w.write(String.valueOf(data.getWindowRelativeX()));
        w.append("\n");

        w.write("RELATIVE_TO_Y = ");
        w.write(String.valueOf(data.getWindowRelativeY()));
        w.append("\n");

        LOG.finer(String.format("Config file saved in %s", file));
      }
      catch (HeadlessException | IOException ex)
      {
        throw new Exception("Speichern von PMTConfig (" + file + ") nicht möglich: \n"
                + "Datei manuell reparieren oder löschen!\n"
                + "Programm verwendet zurückgesetzte Einstellungen", ex);
      }
    }
    catch (Exception ex)
    {
      if(file != null)
      {
        throw new Exception("Speichern von PMTConfig (" + file + ") nicht möglich: \n"
                + "Datei manuell reparieren oder löschen!\n"
                + "Programm verwendet zurückgesetzte Einstellungen", ex);
      }
      else
      {
        throw new Exception("Unbekannter Fehler beim erstellen der Config.Datei", ex);
      }
    }
  }

}
