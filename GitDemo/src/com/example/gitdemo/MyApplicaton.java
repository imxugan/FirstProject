package com.example.gitdemo;

import android.app.Application;
import android.content.Context;
import android.util.Log;

public class MyApplicaton extends Application{
	
	private static final String TAG = "MyApplicaton";
	private static Context context;

	@Override
	public void onCreate() {
		super.onCreate();
		Log.i(TAG, "onCreate");
		context = getApplicationContext();
	}
	
	public static Context getMyContext(){
		if(null == context){
			return null;
		}
		return  context;
	}
}
