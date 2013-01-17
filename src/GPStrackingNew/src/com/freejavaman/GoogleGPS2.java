package com.freejavaman;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

public class GoogleGPS2 extends MapActivity implements LocationListener {
 
 private MapView mapView;
 private MapController mapController;
 private int zoomInt = 18;
 
 private Button zoomInBtn, zoomOutBtn;
 
 public void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.main3);
  
  //���oMapView�������
  mapView = (MapView)this.findViewById(R.id.mapView);
  
  //���Zoom����
  mapView.displayZoomControls(true);
  
  //�]�w�O�_��ܵ�D
  //mapView.setStreetView(true);
  
  //�]�w�O�_��ܽìP
  //mapView.setSatellite(false);
  
  //���oMapView���󪺱����
  mapController = mapView.getController();
  
  //�]�w��l���Y��{��
  mapController.setZoom(zoomInt);
  
  //���o��j, �Y�p���s
  zoomInBtn = (Button)this.findViewById(R.id.zoomInBtn);
  zoomOutBtn = (Button)this.findViewById(R.id.zoomOutBtn);
  
  //����a�ϩ�j
  zoomInBtn.setOnClickListener(new OnClickListener(){
   public void onClick(View view) {
	//�P�_�Y��{�סA�O�_�b�̤j�Ƚd��
	//�Y�٦b�d�򤺡A�h�N�Y��{�ץ[�@�A�ç�sMapView
	if (zoomInt < mapView.getMaxZoomLevel()) {
	 zoomInt++;
	 mapController.setZoom(zoomInt);
	} 
   }	  
  });
  
  //����a���Y�p
  zoomOutBtn.setOnClickListener(new OnClickListener(){
   public void onClick(View view) {
    //�P�_�Y��{�סA�O�_�b�̤p�Ƚd��
	//�Y�٦b�d�򤺡A�h�N�Y��{�״�@�A�ç�sMapView
	if (zoomInt > 1) {
	 zoomInt--;
	 mapController.setZoom(zoomInt);
	} 
   }	  
  });  
  
  //���o�w��A��
  LocationManager locMgr = (LocationManager)(this.getSystemService(Context.LOCATION_SERVICE));
  if (locMgr.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
	//���}��GPS�w��  
	Toast.makeText(this, "�ϥ�GPS�w��", Toast.LENGTH_LONG).show();
	
	//���o�̫��s���w���I
	updateMapView(locMgr.getLastKnownLocation(LocationManager.GPS_PROVIDER));
	
	locMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
	
  } else if (locMgr.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
	//���}�Ҿ��a�x�άO�����w��  
	Toast.makeText(this, "�ϥκ����w��", Toast.LENGTH_LONG).show();
		
	//���o�̫��s���w���I
	updateMapView(locMgr.getLastKnownLocation(LocationManager.NETWORK_PROVIDER));		
	
	locMgr.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
	
  } else {
	//�S���}�ҥ���w��A��, �}�Ҩt�Ϊ��]�w����
	Toast.makeText(this, "�L�w��A��", Toast.LENGTH_LONG).show();
	startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
  }  
 }
 
 //�~��MapActivity�ҥ�������
 protected boolean isRouteDisplayed() {
  return false;
 }
  
 //��sMapView
 private void updateMapView(Location location) {
  //��sMapView�����
  Double longitudeD = location.getLongitude();	//���o�g��
  Double latitudeD = location.getLatitude();	//���o�n��
  
  //�x�_�����g�n��
  //longitudeD = 121.51785277777778;
  //latitudeD = 25.047608333333333;
  
  //�ഫ���פ�����
  /*String longitudeDMS = Location.convert(longitudeD, Location.FORMAT_SECONDS);  
  String latitudeDMS = Location.convert(latitudeD, Location.FORMAT_SECONDS);
  Log.v("googleGPS", "longitudeStr:" + longitudeDMS);
  Log.v("googleGPS", "latitudeDMS:" + latitudeDMS);
  Log.v("googleGPS", "longitude after:" + Location.convert(longitudeDMS));
  Log.v("googleGPS", "longitude after:" + Location.convert(latitudeDMS));*/
    
  //�NLocationPoint����A�ഫ��GeoPoint����
  GeoPoint point = new GeoPoint((int)(latitudeD * 1E6), (int)(longitudeD * 1E6));
  
  //Log.v("googleGPS", "longitudeD 1E6:" + point.getLongitudeE6());
  //Log.v("googleGPS", "longitudeD 1E6:" + point.getLatitudeE6());
  
  //����w���I�ҫ��w�ϰ쪺����, ���L�{�ʵe
  //mapController.animateTo(point);
  mapController.setCenter(point);
  
  //�]�w��j�Y�p�{��
  mapController.setZoom(zoomInt);
  
  //�bMapView�W�A�e�W�w���I��T
  List<Overlay> overlays = mapView.getOverlays();
  
  //���M�����e���w���I��T
  if (!overlays.isEmpty()) {
   overlays.clear();
  }
  
  //�[�J�s���w���I
  PointOverlay myOverlay = new PointOverlay((int)(longitudeD * 1E6), (int)(latitudeD * 1E6));
  overlays.add(myOverlay);
 }

 //��@LocationListener�ݴ���
 public void onLocationChanged(Location location) {
  //�w���T���ܮɡA�i��MapView��s	 
  updateMapView(location);
 }

 //��@LocationListener�ݴ���
 public void onProviderDisabled(String provider) {
 }

 //��@LocationListener�ݴ���
 public void onProviderEnabled(String provider) {
 }

 //��@LocationListener�ݴ���
 public void onStatusChanged(String provider, int status, Bundle extras) {
 } 
}

//��ܦbMapView�W��
class PointOverlay extends Overlay {
 
 int geoLongitude, geoLatitude;
	
 public PointOverlay(int geoLongitude, int geoLatitude) {
  this.geoLongitude = geoLongitude;
  this.geoLatitude = geoLatitude;
 }

 //ø�s�w���I
 public void draw(Canvas canvas, MapView mapView, boolean shadow) {	 
  super.draw(canvas, mapView, shadow);
  
  //�B�z��v������
  Projection projection = mapView.getProjection();
  
  //�g�n�שҫ��V���w���I
  GeoPoint geoPoint = new GeoPoint(geoLatitude, geoLongitude);
  
  //�a�ϤW���w���I
  Point mapPoint= new Point();
  
  //�i��w���I��v�B�z
  projection.toPixels(geoPoint, mapPoint);
  
  //�]�w����
  Paint paint = new Paint();
  paint.setAntiAlias(true); //��������
  paint.setStyle(Paint.Style.FILL); 
  paint.setColor(Color.RED); //�]�w�C��
  paint.setTextSize(36); //�]�w��r�j�p
  
  //�e�W��߶�
  canvas.drawCircle(mapPoint.x, mapPoint.y, 10, paint);  
  canvas.drawText("�ڦb�o��", mapPoint.x + 10, mapPoint.y + 10, paint);
 }
}