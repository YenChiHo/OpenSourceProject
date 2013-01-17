package com.freejavaman;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

public class GoogleGPS extends MapActivity implements LocationListener {
 
 private MapView mapView;
 private MapController mapController;
 private int zoomInt = 18;
 
 public void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.main2);
  
  //取得MapView obj
  mapView = (MapView)this.findViewById(R.id.mapView);
  
  //取得MapView控制元件
  mapView.displayZoomControls(true);
  
  //≥]©w¨Oß_≈„•‹µÛπD
  //mapView.setStreetView(true);
  
  //≥]©w¨Oß_≈„•‹Ω√¨P
  //mapView.setSatellite(false);
  
  //®˙±oMapView™´•Û™∫±±®Ó§∏•Û
  mapController = mapView.getController();
  
  //≥]©w™Ï©l™∫¡Y©Òµ{´◊
  mapController.setZoom(zoomInt);
  
  //®˙±o©w¶Ï™A∞»
  LocationManager locMgr = (LocationManager)(this.getSystemService(Context.LOCATION_SERVICE));
  if (locMgr.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
	//¶≥∂}±“GPS©w¶Ï  
	Toast.makeText(this, "®œ•ŒGPS©w¶Ï", Toast.LENGTH_LONG).show();
	
	//®˙±o≥Ã´·ßÛ∑s™∫©w¶Ï¬I
	updateMapView(locMgr.getLastKnownLocation(LocationManager.GPS_PROVIDER));
	
	locMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
	
  } else if (locMgr.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
	//¶≥∂}±“æ˜¶a•x©Œ¨O∫Ù∏Ù©w¶Ï  
	Toast.makeText(this, "®œ•Œ∫Ù∏Ù©w¶Ï", Toast.LENGTH_LONG).show();
		
	//®˙±o≥Ã´·ßÛ∑s™∫©w¶Ï¬I
	updateMapView(locMgr.getLastKnownLocation(LocationManager.NETWORK_PROVIDER));		
	
	locMgr.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
	
  } else {
	//®S¶≥∂}±“•Ù¶Û©w¶Ï™A∞», ∂}±“®t≤Œ™∫≥]©w≠∂≠±
	Toast.makeText(this, "µL©w¶Ï™A∞»", Toast.LENGTH_LONG).show();
	startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
  }  
 }
 
 //ƒ~©”MapActivity©“•≤∂∑¥£®—
 protected boolean isRouteDisplayed() {
  return false;
 }
  
 //ßÛ∑sMapView
 private void updateMapView(Location location) {
  //ßÛ∑sMapView™∫∏ÍÆ∆
  Double longitudeD = location.getLongitude();	//®˙±o∏g´◊
  Double latitudeD = location.getLatitude();	//®˙±oΩn´◊
  
  //•x•_®ÆØ∏∏gΩn´◊
  longitudeD = 121.51785277777778;
  latitudeD = 25.047608333333333;
  
  //¬‡¥´¶®´◊§¿¨Ì™Ì•‹
  /*String longitudeDMS = Location.convert(longitudeD, Location.FORMAT_SECONDS);  
  String latitudeDMS = Location.convert(latitudeD, Location.FORMAT_SECONDS);
  Log.v("googleGPS", "longitudeStr:" + longitudeDMS);
  Log.v("googleGPS", "latitudeDMS:" + latitudeDMS);
  Log.v("googleGPS", "longitude after:" + Location.convert(longitudeDMS));
  Log.v("googleGPS", "longitude after:" + Location.convert(latitudeDMS));*/
    
  //±NLocationPoint™´•Û°A¬‡¥´¶®GeoPoint™´•Û
  GeoPoint point = new GeoPoint((int)(latitudeD * 1E6), (int)(longitudeD * 1E6));
  
  //Log.v("googleGPS", "longitudeD 1E6:" + point.getLongitudeE6());
  //Log.v("googleGPS", "longitudeD 1E6:" + point.getLatitudeE6());
  
  //≤æ®Ï©w¶Ï¬I©“´¸©w∞œ∞Ï™∫§§•°, ¶≥πLµ{∞ µe
  //mapController.animateTo(point);
  mapController.setCenter(point);
  
  //≥]©w©Ò§j¡Y§pµ{´◊
  mapController.setZoom(zoomInt);  
 }

 //πÍß@LocationListenerª›¥£®—
 public void onLocationChanged(Location location) {
  //©w¶Ï∏Í∞TßÔ≈‹Æ…°A∂i¶ÊMapViewßÛ∑s	 
  updateMapView(location);
 }

 //πÍß@LocationListenerª›¥£®—
 public void onProviderDisabled(String provider) {
 }

 //πÍß@LocationListenerª›¥£®—
 public void onProviderEnabled(String provider) {
 }

 //πÍß@LocationListenerª›¥£®—
 public void onStatusChanged(String provider, int status, Bundle extras) {
 }
 
}