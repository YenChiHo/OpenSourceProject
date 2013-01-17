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
  
  //��ܮy�Ъ���r����
  longitude = (TextView)this.findViewById(R.id.longitude);
  latitude = (TextView)this.findViewById(R.id.latitude);
  
  //���o�w��A��
  LocationManager locMgr = (LocationManager)(this.getSystemService(Context.LOCATION_SERVICE));
  if (locMgr.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
	//���}��GPS�w��  
	Toast.makeText(this, "�ϥ�GPS�w��", Toast.LENGTH_LONG).show();
	
	//���o�̫��s���w���I
	updateText(locMgr.getLastKnownLocation(LocationManager.GPS_PROVIDER));
	
	locMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
	
  } else if (locMgr.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
	//���}�Ҿ��a�x�άO�����w��  
	Toast.makeText(this, "�ϥκ����w��", Toast.LENGTH_LONG).show();
		
	//���o�̫��s���w���I
	updateText(locMgr.getLastKnownLocation(LocationManager.NETWORK_PROVIDER));		
	
	locMgr.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
	
  } else {
	//�S���}�ҥ���w��A��, �}�Ҩt�Ϊ��]�w����
	Toast.makeText(this, "�L�w��A��", Toast.LENGTH_LONG).show();
	startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
  }  
 }
 
 //��s��r�ؤ�����T
 private void updateText(Location location) {
  //��sTextView�����e
  Double longitudeD = location.getLongitude();	//���o�g��
  Double latitudeD = location.getLatitude();	//���o�n��

  longitude.setText("�g��:" + String.valueOf(longitudeD));
  latitude.setText("�n��:" + String.valueOf(latitudeD));
 }

 //��@LocationListener�ݴ���
 public void onLocationChanged(Location location) {
  //�w���T���ܮɡA�i��g�n�׸�T����s	 
  updateText(location);
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