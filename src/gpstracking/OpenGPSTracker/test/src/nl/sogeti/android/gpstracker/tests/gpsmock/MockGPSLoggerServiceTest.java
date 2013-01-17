package nl.sogeti.android.gpstracker.tests.gpsmock;

import junit.framework.Assert;
import nl.sogeti.android.gpstracker.tests.R;
import nl.sogeti.android.gpstracker.tests.utils.MockGPSLoggerDriver;
import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;

/**
 * ????
 *
 *
 * @version $Id$
 * @author rene (c) Jan 22, 2009, Sogeti B.V.
 */
public class MockGPSLoggerServiceTest extends AndroidTestCase
{
   MockGPSLoggerDriver service;
   
   public MockGPSLoggerServiceTest()
   {
      this.service = new MockGPSLoggerDriver( getContext(), R.xml.denhaagdenbosch, 1000 );
   }
   
   @SmallTest
   public void testGPGGACreateLocationCommand()
   {
      String command = MockGPSLoggerDriver.createGPGGALocationCommand( 5.117719d, 52.096524d, 0d );
      Assert.assertTrue("Start of a NMEA sentence: ", command.startsWith( "GPGGA" ));
      Assert.assertTrue("Body of a NMEA sentence", command.contains( "05205.791440" ));
   }
   
   @SmallTest
   public void testGPRMCreateLocationCommand()
   {
      String command = MockGPSLoggerDriver.createGPRMCLocationCommand( 5.117719d, 52.096524d, 0d, 0d );
      Assert.assertTrue("Start of a NMEA sentence: ", command.startsWith( "GPRMC" ));
      Assert.assertTrue("Body of a NMEA sentence", command.contains( "05205.791440" ));
   }
   
   @SmallTest
   public void testCalulateChecksum()
   {
      Assert.assertEquals( "4F", MockGPSLoggerDriver.calulateChecksum("GPGGA,064746.000,4925.4895,N,00103.9255,E,1,05,2.1,-68.0,M,47.1,M,,0000") );
      Assert.assertEquals( "47", MockGPSLoggerDriver.calulateChecksum("GPGGA,123519,4807.038,N,01131.000,E,1,08,0.9,545.4,M,46.9,M,," ) );
      Assert.assertEquals( "39", MockGPSLoggerDriver.calulateChecksum("GPRMC,120557.916,A,5058.7456,N,00647.0515,E,0.00,82.33,220503,,") );
   }
}
