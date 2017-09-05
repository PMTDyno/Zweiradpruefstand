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

    assertTrue(Crc16.checkCRC("123456789", "29B1"));
    assertTrue(Crc16.checkCRC("abcdefghi", "1E7C"));
    assertTrue(Crc16.checkCRC("0,0226:0,0061=", "96BC"));
    assertTrue(Crc16.checkCRC("0,0:0,0=", "FCBD"));
    assertTrue(Crc16.checkCRC("5000,0:9000,0=", "D25A"));
    assertTrue(Crc16.checkCRC(",.:=-/*", "3FE6"));

    assertFalse(Crc16.checkCRC("123456789", "29B2"));
    assertFalse(Crc16.checkCRC("abcdefghi", "1E7X"));
    assertFalse(Crc16.checkCRC("0,0226:0,0061=", "936BC"));
    assertFalse(Crc16.checkCRC("0,0:0,0=", "0xFCBD"));
    assertFalse(Crc16.checkCRC("5000,0:9000,0", "D25A"));
    assertFalse(Crc16.checkCRC(",.:=-/*", "3FE6"));

  }

  @Test
  public void testGetCRC()
  {
    System.out.println("measure.Crc16: getCRC()");

    assertEquals("29B1", Crc16.getCRC("123456789"));
    assertEquals("1E7C", Crc16.getCRC("abcdefghi"));
    assertEquals("96BC", Crc16.getCRC("0,0226:0,0061="));
    assertEquals("FCBD", Crc16.getCRC("0,0:0,0="));
    assertEquals("D25A", Crc16.getCRC("5000,0:9000,0="));
    assertEquals("3FE6", Crc16.getCRC(",.:=-/*"));

  }

}
