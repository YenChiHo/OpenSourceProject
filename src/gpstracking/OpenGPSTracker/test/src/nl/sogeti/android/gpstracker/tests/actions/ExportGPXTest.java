package nl.sogeti.android.gpstracker.tests.actions;

import nl.sogeti.android.gpstracker.actions.tasks.XmlCreator;
import nl.sogeti.android.gpstracker.db.GPStracking.Tracks;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.net.Uri;
import android.test.mock.MockContentResolver;
import android.test.suitebuilder.annotation.SmallTest;
import junit.framework.Assert;
import junit.framework.TestCase;

public class ExportGPXTest extends TestCase
{   
   @SmallTest
   public void testIntentCreation()
   {
      ContentResolver resolver = new MockContentResolver();

      Uri uri = ContentUris.withAppendedId( Tracks.CONTENT_URI, 0 );
      Intent actionIntent = new Intent(Intent.ACTION_RUN, uri );
      actionIntent.setDataAndType( uri, Tracks.CONTENT_ITEM_TYPE );
      actionIntent.addFlags( Intent.FLAG_GRANT_READ_URI_PERMISSION );
      
      // Action match
      Assert.assertEquals( "Action", actionIntent.getAction(), Intent.ACTION_RUN  );
      
      // Category match
      Assert.assertEquals( "Category", actionIntent.getCategories(), null );
      
      // Data match
      Assert.assertEquals( "Mock Infered Data Type", Tracks.CONTENT_ITEM_TYPE, actionIntent.resolveType( resolver ) );
      Assert.assertEquals( "Mock Data Type", Tracks.CONTENT_ITEM_TYPE, actionIntent.getType() ) ;
      
      Assert.assertEquals( "Data Schema", "content", actionIntent.getScheme() );
      Assert.assertEquals( "Data Authority", "nl.sogeti.android.gpstracker", actionIntent.getData().getAuthority() );
      Assert.assertEquals( "Data Path", "/tracks/0", actionIntent.getData().getPath() );
   }
   
   @SmallTest
   public void testCleanFilename()
   {
      String dirty = "abc=+:;/123";
      String clean = XmlCreator.cleanFilename( dirty, "ERROR" );
      Assert.assertEquals( "Cleaned", "abc123" , clean );
   }
   
   @SmallTest
   public void testCleanFilenameEmpty()
   {
      String dirty = "";
      String clean = XmlCreator.cleanFilename( dirty, "Untitled" );
      Assert.assertEquals( "Cleaned", "Untitled" , clean );
   }
   
   @SmallTest
   public void testCleanFilenameNull()
   {
      String dirty = null;
      String clean = XmlCreator.cleanFilename( dirty, "Untitled2" );
      Assert.assertEquals( "Cleaned", "Untitled2" , clean );
   }
   
   @SmallTest
   public void testCleanFilenameAllSpecial()
   {
      String dirty = "!!??";
      String clean = XmlCreator.cleanFilename( dirty, "Untitled3" );
      Assert.assertEquals( "Cleaned", "Untitled3" , clean );
   }
}
