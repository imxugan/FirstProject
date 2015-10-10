package com.example.gitdemo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;

import com.example.gitdemo.base.BaseActivity;
import com.example.gitdemo.service.MyBindService;
import com.example.gitdemo.service.MyBindService.MyBinder;
import com.example.gitdemo.service.MyService;

public class TestServiceActivity extends BaseActivity{

	private Intent service;
	private MyServiceConnection conn;

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
			Log.i("ii", "conn======"+conn);
			//多次解绑服务失败，如何破？？？
			if(conn != null){
				unbindService(conn);
			}
			break;
		default:
			break;
		}
	}
	
	private class MyServiceConnection implements ServiceConnection{

		private MyBindService.MyBinder binder;

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			binder = (MyBinder) service;
			binder.show();
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			binder = null;
		}
		
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		stopService(service);
		
		unbindService(conn);
	}

}
