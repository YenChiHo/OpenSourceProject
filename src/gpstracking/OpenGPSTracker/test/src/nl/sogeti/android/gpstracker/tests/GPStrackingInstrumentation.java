package nl.sogeti.android.gpstracker.tests;

import junit.framework.TestSuite;
import nl.sogeti.android.gpstracker.tests.actions.ExportGPXTest;
import nl.sogeti.android.gpstracker.tests.db.GPStrackingProviderTest;
import nl.sogeti.android.gpstracker.tests.gpsmock.MockGPSLoggerServiceTest;
import nl.sogeti.android.gpstracker.tests.logger.GPSLoggerServiceTest;
import nl.sogeti.android.gpstracker.tests.userinterface.LoggerMapTest;
import android.test.InstrumentationTestRunner;
import android.test.InstrumentationTestSuite;

/**
 * Perform unit tests Run on the adb shell:
 *
 * <pre>
 *   am instrument -w nl.sogeti.android.gpstracker.tests/.GPStrackingInstrumentation
 * </pre>
 *
 * @version $Id$
 * @author rene (c) Jan 22, 2009, Sogeti B.V.
 */
public class GPStrackingInstrumentation extends InstrumentationTestRunner
{

   /**
    * (non-Javadoc)
    * @see android.test.InstrumentationTestRunner#getAllTests()
    */
   @Override
   public TestSuite getAllTests()
   {
      TestSuite suite = new InstrumentationTestSuite( this );
      suite.setName( "GPS Tracking Testsuite" );
      suite.addTestSuite( GPStrackingProviderTest.class );
      suite.addTestSuite( MockGPSLoggerServiceTest.class );
      suite.addTestSuite( GPSLoggerServiceTest.class );
      suite.addTestSuite( ExportGPXTest.class );
      suite.addTestSuite( LoggerMapTest.class );
      
//      suite.addTestSuite( OpenGPSTrackerDemo.class );   // The demo recorded for youtube
//      suite.addTestSuite( MapStressTest.class );       // The stress test of the map viewer
      return suite;
   }

   /**
    * (non-Javadoc)
    * @see android.test.InstrumentationTestRunner#getLoader()
    */
   @Override
   public ClassLoader getLoader()
   {
      return GPStrackingInstrumentation.class.getClassLoader();
   }
}
