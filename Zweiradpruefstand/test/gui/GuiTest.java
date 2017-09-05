/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import measure.MeasurementWorker;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Messing Levin <meslem12@htl-kaindorf.ac.at>
 */
public class GuiTest
{
  
  public GuiTest()
  {
  }
  
  @BeforeClass
  public static void setUpClass()
  {
  }
  
  @AfterClass
  public static void tearDownClass()
  {
  }

  /**
   * Test of main method, of class Gui.
   */
  @Test
  public void testConvertion()
  {
    System.out.println("gui.Gui: Gui()");
    
    Gui gui = new Gui();
    assertNotNull(gui);
    gui.setVisible(true);
    assertTrue(gui.isVisible());
    
    
    
    
    
    
  }

  
}
