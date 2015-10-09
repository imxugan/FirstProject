package com.example.gitdemo;

import android.app.Application;
import android.util.Log;

public class MyApplicaton extends Application{
	
	private static final String TAG = "MyApplicaton";

	@Override
	public void onCreate() {
		super.onCreate();
		Log.i(TAG, "onCreate");
	}
}
