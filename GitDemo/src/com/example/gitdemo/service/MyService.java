package com.example.gitdemo.service;

import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service{
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Log.i("ii", "MyService onCreate=");
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i("ii", "onStartCommand="+flags);
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		//采用startservice时，这个方法根本不调用
		Log.i("ii", "MyService onBind=");
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
		Log.i("ii", "MyService onDestroy=");
	}

}
