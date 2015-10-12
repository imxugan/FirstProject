package com.example.gitdemo;

import android.app.IntentService;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;

import com.example.gitdemo.base.BaseActivity;
import com.example.gitdemo.service.MyBindService;
import com.example.gitdemo.service.MyForegroundService;
import com.example.gitdemo.service.MyIntentService;
import com.example.gitdemo.service.MyBindService.MyBinder;
import com.example.gitdemo.service.MyLongRunningService;
import com.example.gitdemo.service.MyService;

public class TestServiceActivity extends BaseActivity{

	private Intent service;
	private MyServiceConnection conn;
	private MyBindService.MyBinder binder;
	public boolean isServiceConnected;
	private Intent foregroundService;
	private Intent intentService;
	private Intent myLongRunningService;

	@Override
	protected void initContentView(Bundle savedInstanceState) {
		setContentView(R.layout.activity_service);
	}
	
	public void manage(View v){
		switch (v.getId()) {
		case R.id.btn_start_service:
			service = new Intent(TestServiceActivity.this,MyService.class);
			startService(service);
			break;
		case R.id.btn_stop_service:
			stopService(service);
			break;
		case R.id.btn_bind_service:
			conn = new MyServiceConnection();
			service = new Intent(TestServiceActivity.this,MyBindService.class);
			bindService(service, conn, BIND_AUTO_CREATE);
			break;
		case R.id.btn_unbind_service:
//			Log.i("ii", "conn======"+conn);
//			Log.i("ii", "service======"+service);
//			Log.i("ii", "binder======"+binder);
			//多次解绑服务失败，如何破？？？
			if(isServiceConnected){
				unbindService(conn);
				isServiceConnected = false;
			}
			break;
		case R.id.btn_create_froeground_service:
			foregroundService = new Intent(TestServiceActivity.this,MyForegroundService.class);
		    startService(foregroundService);
			break;
		case R.id.btn_intent_service:
			intentService = new Intent(TestServiceActivity.this,MyIntentService.class);
			startService(intentService);
			Log.i("ii", "TestServiceActivity="+Thread.currentThread().getId());
			break;
			
		case R.id.btn_practice:
			myLongRunningService = new Intent(TestServiceActivity.this,MyLongRunningService.class);
			startService(myLongRunningService);
			break;
		default:
			break;
		}
	}
	
	private class MyServiceConnection implements ServiceConnection{

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			binder = (MyBinder) service;
			binder.show();
			isServiceConnected = true;
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			binder = null;
		}
		
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.i("ii", "TestServiceActivity service="+service);
		Log.i("ii", "TestServiceActivity foregroundService="+foregroundService);
		Log.i("ii", "TestServiceActivity intentService="+intentService);
		if(service != null){
			stopService(service);
		}
		
		if(foregroundService != null){
			stopService(foregroundService);
		}
		
		if(isServiceConnected){
			unbindService(conn);
			isServiceConnected = false;
		}
		
		if(intentService != null){
			stopService(intentService);
		}
		
		if(myLongRunningService != null){
			stopService(myLongRunningService);
		}
	}

}
