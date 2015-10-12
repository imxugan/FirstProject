package com.example.gitdemo.service;

import com.example.gitdemo.R;

import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.util.Log;

public class MyForegroundService extends Service{
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Log.i("ii", "MyForegroundService onCreate=");
		NotificationCompat.Builder builder = new Builder(getApplication());
		builder.setContentTitle("this is notification title")
		.setTicker("notification is comming")
		.setContentText("this is notification content")
		.setSmallIcon(R.drawable.ic_launcher);
		startForeground(1, builder.build());
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i("ii", "MyForegroundService	onStartCommand="+flags);
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		//采用startservice时，这个方法根本不调用
		Log.i("ii", "MyForegroundService onBind=");
		return null;
	}
	
	@Override
	public void unbindService(ServiceConnection conn) {
		//采用startservice时，这个方法根本不调用
		super.unbindService(conn);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.i("ii", "MyForegroundService onDestroy=");
	}

}
