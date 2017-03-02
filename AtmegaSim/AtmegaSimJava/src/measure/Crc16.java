package measure;

import java.io.UnsupportedEncodingException;
import logging.*;

/**
 *
 * @author Levin Messing (meslem12@htl-kaindorf.ac.at)
 */
public class Crc16
{

    private static final Logger LOG = Logger.getLogger(Crc16.class.getName());

    private static int crc;
    private static int polynomial;

    public void Crc16()
    {
    }

    public static void main(String[] args) throws Exception
    {
        byte[] bits = new byte[30];
        bits = "1cmVhZHk=".getBytes();
        //System.out.println(Integer.toBinaryString(bits[2]));
        
        String str = new String(bits);
        System.out.println(str + " = " + getCRC(str));
        //System.out.println(checkCRC("req", getCRC("req")));
    }

    /**
     * Calculates the checksum of the source and compares it with the given
     * checksum <br>
     * The CRC16-CCITT 0xFFFF is used
     *
     * @param source
     * @param checksum
     * @return true if the checksum matches <br>
     * false if the checksum does not match or an error occured
     */
    public static boolean checkCRC(String source, String checksum)
    {

        try
        {
            crc = 0xFFFF;          // initial value
            polynomial = 0x1021;   // 0001 0000 0010 0001  (0, 5, 12)
            byte[] bytes = source.getBytes("UTF-8");

            //byte[] bytes = "test".getBytes("UTF-8");
            for(byte b : bytes)
            {
                for(int i = 0; i < 8; i++)
                {
                    boolean bit = ((b >> (7 - i) & 1) == 1);
                    boolean c15 = ((crc >> 15 & 1) == 1);
                    crc <<= 1;
                    if(c15 ^ bit)
                        crc ^= polynomial;
                }
            }

            crc &= 0xffff;

            //System.out.println(crc + "   " + checksum);
            return crc == Integer.decode("0x" + checksum);
        }
        catch (Exception ex)
        {
            LOG.severe("Unsupported Exception: " + ex);
        }
        return false;
    }

    /**
     * calculates the checksum from the given data String<br>
     * The CRC16-CCITT 0xFFFF is used
     *
     * @param data
     * @return the checksum or <code>null</code> if an unexpected error occured
     */
    public static String getCRC(String data)
    {
        try
        {
            crc = 0xFFFF;          // initial value
            polynomial = 0x1021;   // 0001 0000 0010 0001  (0, 5, 12)
            byte[] bytes = data.getBytes("UTF-8");

            //byte[] bytes = "test".getBytes("UTF-8");
            for(byte b : bytes)
            {
                for(int i = 0; i < 8; i++)
                {
                    boolean bit = ((b >> (7 - i) & 1) == 1);
                    boolean c15 = ((crc >> 15 & 1) == 1);
                    crc <<= 1;
                    if(c15 ^ bit)
                        crc ^= polynomial;
                }
            }

            crc &= 0xffff;
            String str = Integer.toHexString(crc);
            while(str.length()<4)
              str = "0" + str;
            
            return str;
        }
        catch (Exception ex)
        {
            LOG.severe(ex);
            return null;
        }
    }

}
