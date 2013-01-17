package nl.sogeti.android.gpstracker.tests.perf;

import nl.sogeti.android.gpstracker.tests.R;
import nl.sogeti.android.gpstracker.tests.utils.MockGPSLoggerDriver;
import nl.sogeti.android.gpstracker.viewer.map.CommonLoggerMap;
import android.os.Debug;
import android.test.ActivityInstrumentationTestCase2;
import android.test.PerformanceTestCase;
import android.test.suitebuilder.annotation.LargeTest;

public class LoggerStressTest extends ActivityInstrumentationTestCase2<CommonLoggerMap> implements PerformanceTestCase
{
   private static final Class<CommonLoggerMap> CLASS = CommonLoggerMap.class;
   private static final String PACKAGE = "nl.sogeti.android.gpstracker";
   private Intermediates mIntermediates;

   public LoggerStressTest()
   {
      super( PACKAGE, CLASS );
   }

   @Override
   protected void setUp() throws Exception 
   {
      super.setUp();
      getActivity();
   }  

   protected void tearDown() throws Exception
   {
      super.tearDown();
   }
   
   /**
    * Just pours a lot of tracking actions at the application
    * 
    * @throws InterruptedException
    */
   @LargeTest
   public void testLapsAroundUtrecht() throws InterruptedException
   {    
      // Our data feeder to the emulator
      MockGPSLoggerDriver service = new MockGPSLoggerDriver( getInstrumentation().getContext(), R.xml.rondjesingelutrecht, 10 );

      this.sendKeys( "T T T T" );
      this.sendKeys( "MENU DPAD_RIGHT T T E S T R O U T E ENTER" );
      this.sendKeys("ENTER" ); 

      // Start method tracing for Issue 18
      Debug.startMethodTracing("rondjesingelutrecht" );
      if( this.mIntermediates != null )
      {
         this.mIntermediates.startTiming( true ) ;
      }

      service.run();

      // Start method tracing for Issue 18
      if( this.mIntermediates != null )
      {
         this.mIntermediates.finishTiming( true ) ;
      }
      Debug.stopMethodTracing();
   }

   public boolean isPerformanceOnly()
   {
      return true;
   }

   public int startPerformance( Intermediates intermediates )
   {
      this.mIntermediates = intermediates;
      return 1;
   }
}
