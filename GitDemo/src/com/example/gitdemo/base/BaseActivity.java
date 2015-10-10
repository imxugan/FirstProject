package com.example.gitdemo.base;

import com.example.gitdemo.controller.ActivityController;

import android.app.Activity;
import android.os.Bundle;

public abstract class BaseActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActivityController.addActivity(this);
		initContentView(savedInstanceState);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		ActivityController.removeActivity(this);
	}
	
	
	// 初始化UI，setContentView等
    protected abstract void initContentView(Bundle savedInstanceState);
}
