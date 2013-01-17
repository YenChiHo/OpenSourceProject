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
  
  //取得MapView物件實體
  mapView = (MapView)this.findViewById(R.id.mapView);
  
  //顯示Zoom控制
  mapView.displayZoomControls(true);
  
  //設定是否顯示街道
  //mapView.setStreetView(true);
  
  //設定是否顯示衛星
  //mapView.setSatellite(false);
  
  //取得MapView物件的控制元件
  mapController = mapView.getController();
  
  //設定初始的縮放程度
  mapController.setZoom(zoomInt);
  
  //取得放大, 縮小按鈕
  zoomInBtn = (Button)this.findViewById(R.id.zoomInBtn);
  zoomOutBtn = (Button)this.findViewById(R.id.zoomOutBtn);
  
  //執行地圖放大
  zoomInBtn.setOnClickListener(new OnClickListener(){
   public void onClick(View view) {
	//判斷縮放程度，是否在最大值範圍內
	//若還在範圍內，則將縮放程度加一，並更新MapView
	if (zoomInt < mapView.getMaxZoomLevel()) {
	 zoomInt++;
	 mapController.setZoom(zoomInt);
	} 
   }	  
  });
  
  //執行地圖縮小
  zoomOutBtn.setOnClickListener(new OnClickListener(){
   public void onClick(View view) {
    //判斷縮放程度，是否在最小值範圍內
	//若還在範圍內，則將縮放程度減一，並更新MapView
	if (zoomInt > 1) {
	 zoomInt--;
	 mapController.setZoom(zoomInt);
	} 
   }	  
  });  
  
  //取得定位服務
  LocationManager locMgr = (LocationManager)(this.getSystemService(Context.LOCATION_SERVICE));
  if (locMgr.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
	//有開啟GPS定位  
	Toast.makeText(this, "使用GPS定位", Toast.LENGTH_LONG).show();
	
	//取得最後更新的定位點
	updateMapView(locMgr.getLastKnownLocation(LocationManager.GPS_PROVIDER));
	
	locMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
	
  } else if (locMgr.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
	//有開啟機地台或是網路定位  
	Toast.makeText(this, "使用網路定位", Toast.LENGTH_LONG).show();
		
	//取得最後更新的定位點
	updateMapView(locMgr.getLastKnownLocation(LocationManager.NETWORK_PROVIDER));		
	
	locMgr.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
	
  } else {
	//沒有開啟任何定位服務, 開啟系統的設定頁面
	Toast.makeText(this, "無定位服務", Toast.LENGTH_LONG).show();
	startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
  }  
 }
 
 //繼承MapActivity所必須提供
 protected boolean isRouteDisplayed() {
  return false;
 }
  
 //更新MapView
 private void updateMapView(Location location) {
  //更新MapView的資料
  Double longitudeD = location.getLongitude();	//取得經度
  Double latitudeD = location.getLatitude();	//取得緯度
  
  //台北車站經緯度
  //longitudeD = 121.51785277777778;
  //latitudeD = 25.047608333333333;
  
  //轉換成度分秒表示
  /*String longitudeDMS = Location.convert(longitudeD, Location.FORMAT_SECONDS);  
  String latitudeDMS = Location.convert(latitudeD, Location.FORMAT_SECONDS);
  Log.v("googleGPS", "longitudeStr:" + longitudeDMS);
  Log.v("googleGPS", "latitudeDMS:" + latitudeDMS);
  Log.v("googleGPS", "longitude after:" + Location.convert(longitudeDMS));
  Log.v("googleGPS", "longitude after:" + Location.convert(latitudeDMS));*/
    
  //將LocationPoint物件，轉換成GeoPoint物件
  GeoPoint point = new GeoPoint((int)(latitudeD * 1E6), (int)(longitudeD * 1E6));
  
  //Log.v("googleGPS", "longitudeD 1E6:" + point.getLongitudeE6());
  //Log.v("googleGPS", "longitudeD 1E6:" + point.getLatitudeE6());
  
  //移到定位點所指定區域的中央, 有過程動畫
  //mapController.animateTo(point);
  mapController.setCenter(point);
  
  //設定放大縮小程度
  mapController.setZoom(zoomInt);
  
  //在MapView上，畫上定位點資訊
  List<Overlay> overlays = mapView.getOverlays();
  
  //先清除先前的定位點資訊
  if (!overlays.isEmpty()) {
   overlays.clear();
  }
  
  //加入新的定位點
  PointOverlay myOverlay = new PointOverlay((int)(longitudeD * 1E6), (int)(latitudeD * 1E6));
  overlays.add(myOverlay);
 }

 //實作LocationListener需提供
 public void onLocationChanged(Location location) {
  //定位資訊改變時，進行MapView更新	 
  updateMapView(location);
 }

 //實作LocationListener需提供
 public void onProviderDisabled(String provider) {
 }

 //實作LocationListener需提供
 public void onProviderEnabled(String provider) {
 }

 //實作LocationListener需提供
 public void onStatusChanged(String provider, int status, Bundle extras) {
 } 
}

//顯示在MapView上的
class PointOverlay extends Overlay {
 
 int geoLongitude, geoLatitude;
	
 public PointOverlay(int geoLongitude, int geoLatitude) {
  this.geoLongitude = geoLongitude;
  this.geoLatitude = geoLatitude;
 }

 //繪製定位點
 public void draw(Canvas canvas, MapView mapView, boolean shadow) {	 
  super.draw(canvas, mapView, shadow);
  
  //處理投影的物件
  Projection projection = mapView.getProjection();
  
  //經緯度所指向的定位點
  GeoPoint geoPoint = new GeoPoint(geoLatitude, geoLongitude);
  
  //地圖上的定位點
  Point mapPoint= new Point();
  
  //進行定位點投影處理
  projection.toPixels(geoPoint, mapPoint);
  
  //設定筆刷
  Paint paint = new Paint();
  paint.setAntiAlias(true); //消除鋸齒
  paint.setStyle(Paint.Style.FILL); 
  paint.setColor(Color.RED); //設定顏色
  paint.setTextSize(36); //設定文字大小
  
  //畫上實心圓
  canvas.drawCircle(mapPoint.x, mapPoint.y, 10, paint);  
  canvas.drawText("我在這裡", mapPoint.x + 10, mapPoint.y + 10, paint);
 }
}