package com.freejavaman;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateGPS extends Activity implements LocationListener {

 TextView longitude;
 TextView latitude;
 
 public void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.main);
  
  //顯示座標的文字物件
  longitude = (TextView)this.findViewById(R.id.longitude);
  latitude = (TextView)this.findViewById(R.id.latitude);
  
  //取得定位服務
  LocationManager locMgr = (LocationManager)(this.getSystemService(Context.LOCATION_SERVICE));
  if (locMgr.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
	//有開啟GPS定位  
	Toast.makeText(this, "使用GPS定位", Toast.LENGTH_LONG).show();
	
	//取得最後更新的定位點
	updateText(locMgr.getLastKnownLocation(LocationManager.GPS_PROVIDER));
	
	locMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
	
  } else if (locMgr.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
	//有開啟機地台或是網路定位  
	Toast.makeText(this, "使用網路定位", Toast.LENGTH_LONG).show();
		
	//取得最後更新的定位點
	updateText(locMgr.getLastKnownLocation(LocationManager.NETWORK_PROVIDER));		
	
	locMgr.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
	
  } else {
	//沒有開啟任何定位服務, 開啟系統的設定頁面
	Toast.makeText(this, "無定位服務", Toast.LENGTH_LONG).show();
	startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
  }  
 }
 
 //更新文字框中的資訊
 private void updateText(Location location) {
  //更新TextView的內容
  Double longitudeD = location.getLongitude();	//取得經度
  Double latitudeD = location.getLatitude();	//取得緯度

  longitude.setText("經度:" + String.valueOf(longitudeD));
  latitude.setText("緯度:" + String.valueOf(latitudeD));
 }

 //實作LocationListener需提供
 public void onLocationChanged(Location location) {
  //定位資訊改變時，進行經緯度資訊的更新	 
  updateText(location);
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