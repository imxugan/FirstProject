package com.example.gitdemo.service;

import com.example.gitdemo.R;
import com.example.gitdemo.callback.MyLocalBroadCastReceiver;
import com.example.gitdemo.controller.MyLongRunningBroadCastReceiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.util.Log;

public class MyLongRunningService extends Service{
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Log.i("ii", "MyAllRunningService onCreate=");
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i("ii", "MyAllRunningService	onStartCommand="+flags);
		AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
		long triggerAtMillis = SystemClock.elapsedRealtime() + 10 * 1000;
		intent = new Intent(getApplication(),MyLongRunningBroadCastReceiver.class);
		PendingIntent operation = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);
		alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtMillis, operation);
		
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		//采用startservice时，这个方法根本不调用
		Log.i("ii", "MyAllRunningService onBind=");
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
		Log.i("ii", "MyAllRunningService onDestroy=");
	}

}
