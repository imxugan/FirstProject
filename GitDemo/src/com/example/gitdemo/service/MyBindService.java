package com.example.gitdemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class MyBindService extends Service{
	
	public class MyBinder extends Binder{
		public void show(){
			Toast.makeText(getApplicationContext(), "我是服务中的方法", 0).show();
		}
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		Log.i("ii", "MyBindService onCreate=");
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		//采用bindservice时，这个方法根本不调用
		Log.i("ii", "MyBindService onStartCommand  startId="+startId);
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public boolean onUnbind(Intent intent) {
		Log.i("ii", "MyBindService onUnbind=");
		return super.onUnbind(intent);
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		Log.i("ii", "MyBindService onBind=");
		return new MyBinder();
	}
	
	@Override
	public void onDestroy() {
		Log.i("ii", "MyBindService onDestroy=");
		super.onDestroy();
	}

}
