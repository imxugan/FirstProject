package com.example.gitdemo.service;

import com.example.gitdemo.R;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.util.Log;

public class MyIntentService extends IntentService{
	
	public MyIntentService() {
//		super(name);
		
		
		//java.lang.RuntimeException: Unable to instantiate service com.example.gitdemo.service.MyIntentService: java.lang.InstantiationException: can't instantiate class com.example.gitdemo.service.MyIntentService; no empty constructor
		super("MyIntentService");
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.i("ii", "MyIntentService onCreate=");
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i("ii", "MyIntentService	onStartCommand="+flags);
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		//采用startservice时，这个方法根本不调用
		Log.i("ii", "MyIntentService onBind=");
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
		Log.i("ii", "MyIntentService onDestroy=");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Log.i("ii", "MyIntentService onHandleIntent="+Thread.currentThread().getId());
	}

}
