package nl.sogeti.android.gpstracker.tests.perf;

import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import nl.sogeti.android.gpstracker.db.GPStracking.Tracks;
import nl.sogeti.android.gpstracker.db.GPStracking.Waypoints;
import nl.sogeti.android.gpstracker.viewer.map.CommonLoggerMap;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Debug;
import android.test.ActivityInstrumentationTestCase2;
import android.test.PerformanceTestCase;
import android.test.suitebuilder.annotation.LargeTest;
import android.util.Log;


public class MapStressTest extends ActivityInstrumentationTestCase2<CommonLoggerMap> implements PerformanceTestCase
{
   private static final Class<CommonLoggerMap> CLASS = CommonLoggerMap.class;
   private static final String PACKAGE = "nl.sogeti.android.gpstracker";
   private static final String TAG = "OGT.MapStressTest";
   private Intermediates mIntermediates;

   public MapStressTest()
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

   @LargeTest
   public void testCreateTestData() throws XmlPullParserException, IOException
   {
      //createTrackBigTest( 2000 );
      //createTrackFromKMLData( "/mnt/sdcard/estland50k.xml" );
   }

   private void createTrackFromKMLData( String xmlResource ) throws XmlPullParserException, IOException
   {

      XmlPullParserFactory factory = XmlPullParserFactory.newInstance();

      XmlPullParser xpp = factory.newPullParser();
      xpp.setInput( new FileReader( xmlResource ) );


      ContentResolver resolver = this.getActivity().getContentResolver();
      Uri trackUri = resolver.insert( Tracks.CONTENT_URI, null );
      
      int eventType = xpp.getEventType();
      while( eventType != XmlPullParser.END_DOCUMENT )
      {

         if( eventType == XmlPullParser.START_TAG )
         {
            if( "coordinates".equals( xpp.getName() ) )
            {
               //Start new Segment
               Uri segmentUri = resolver.insert( Uri.withAppendedPath( trackUri, "segments" ), null );
               Uri waypointUri = Uri.withAppendedPath( segmentUri, "waypoints" );
               //Insert all coordinates as waypoints
               xpp.next();
               String coords = xpp.getText();
               StringTokenizer tokizer = new StringTokenizer( coords, " " );
               String[] tuple = new String[3];
               String waypoint;
               ContentValues wp = new ContentValues();
               while( tokizer.hasMoreTokens() )
               {
                  waypoint = tokizer.nextToken();
                  Log.d( TAG, "Insert waypoint: "+waypoint );
                  tuple = waypoint.split( "," );
                  wp.put( Waypoints.LONGITUDE, new Double( tuple[0] ) );
                  wp.put( Waypoints.LATITUDE, new Double( tuple[1] ) );
                  wp.put( Waypoints.ALTITUDE, new Double( tuple[2] ) );
                  resolver.insert( waypointUri, wp );
               }
            }
         }
         eventType = xpp.next();
      }

   }

   private void createTrackBigTest( int total )
   {
      // zig-zag through the netherlands
      double lat1 = 52.195d;
      double lon1 = 4.685d;
      double lat2 = 51.882d;
      double lon2 = 5.040d;
      double lat3 = 52.178d;
      double lon3 = 5.421d;

      ContentResolver resolver = this.getActivity().getContentResolver();
      ContentValues wp = new ContentValues();
      wp.put( Waypoints.ACCURACY, new Double( 10d ) );
      wp.put( Waypoints.ALTITUDE, new Double( 5d ) );
      wp.put( Waypoints.SPEED, new Double( 15d ) );

      // E.g. returns: content://nl.sogeti.android.gpstracker/tracks/2
      Uri trackUri = resolver.insert( Tracks.CONTENT_URI, null );
      Uri segmentUri = resolver.insert( Uri.withAppendedPath( trackUri, "segments" ), null );
      Uri waypointUri = Uri.withAppendedPath( segmentUri, "waypoints" );

      for( int step = 0; step < total / 2; step++ )
      {
         double latitude = lat1 + ( ( lat1 - lat2 ) / total ) * step;
         double longtitude = lon1 + ( ( lon2 - lon1 ) / total ) * step;
         wp.put( Waypoints.LATITUDE, new Double( latitude ) );
         wp.put( Waypoints.LONGITUDE, new Double( longtitude ) );
         resolver.insert( waypointUri, wp );
      }
      for( int step = 0; step < total / 2; step++ )
      {
         double latitude = lat2 + ( ( lat3 - lat2 ) / total ) * step;
         double longtitude = lon2 + ( ( lon3 - lon2 ) / total ) * step;
         wp.put( Waypoints.LATITUDE, new Double( latitude ) );
         wp.put( Waypoints.LONGITUDE, new Double( longtitude ) );
         resolver.insert( waypointUri, wp );
      }
   }

   /**
    * Open the first track in the list and scroll around forcing redraws during a perf test
    * 
    * @throws InterruptedException
    */
   @LargeTest
   public void testBrowseFirstTrack() throws InterruptedException
   {
      int actions = 0;
      String[] timeActions = { "G", "G", "T", "T", "T" };

      // Start method tracing for Issue 18
      Debug.startMethodTracing( "testBrowseFirstTrack" );
      if( this.mIntermediates != null )
      {
         this.mIntermediates.startTiming( true );
      }
      while( actions < timeActions.length )
      {
         this.sendKeys( timeActions[actions] );
         actions++;
         Thread.sleep( 300L );
      }
      if( this.mIntermediates != null )
      {
         this.mIntermediates.finishTiming( true );
      }
      Debug.stopMethodTracing();
      Log.d( TAG, "Completed actions: " + actions );
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
