/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package measure;

import junit.framework.TestCase;
import org.junit.Test;

/**
 *
 * @author Messing Levin <meslem12@htl-kaindorf.ac.at>
 */
public class Crc16Test extends TestCase
{
  
  public Crc16Test(String testName)
  {
    super(testName);
    
  }

  @Test
  public void testCheckCRC()
  {
    System.out.println("measure.Crc16: checkCRC()");
            
    assertEquals(true, Crc16.checkCRC("123456789", "29B1"));
    assertEquals(true, Crc16.checkCRC("abcdefghi", "1E7C"));
    assertEquals(true, Crc16.checkCRC("0,0226:0,0061=", "96BC"));
    assertEquals(true, Crc16.checkCRC("0,0:0,0=", "FCBD"));
    assertEquals(true, Crc16.checkCRC("5000,0:9000,0=", "D25A"));
    assertEquals(true, Crc16.checkCRC(",.:=-/*", "3FE6"));
    
    assertEquals(false, Crc16.checkCRC("123456789", "29B2"));
    assertEquals(false, Crc16.checkCRC("abcdefghi", "1E7X"));
    assertEquals(false, Crc16.checkCRC("0,0226:0,0061=", "936BC"));
    assertEquals(false, Crc16.checkCRC("0,0:0,0=", "0xFCBD"));
    assertEquals(false, Crc16.checkCRC("5000,0:9000,0", "D25A"));
    assertEquals(false, Crc16.checkCRC(",.:=-/*", "3FE6"));
    
  }

  /**
   * Test of getCRC method, of class Crc16.
   */
  public void testGetCRC()
  {
//    System.out.println("getCRC");
//    String data = "";
//    String expResult = "";
//    String result = Crc16.getCRC(data);
//    assertEquals(expResult, result);
//    // TODO review the generated test code and remove the default call to fail.
//    fail("The test case is a prototype.");
  }
  
}
