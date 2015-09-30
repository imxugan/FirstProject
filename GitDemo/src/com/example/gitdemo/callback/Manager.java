package com.example.gitdemo.callback;

import android.util.Log;

public class Manager {
	public Manager(Employee employee){
		employee.work(this,"电商项目");
	}

	public Manager() {
		super();
	}

	//这个方法就是java中所称的回调方法
	public void getCall(String workResult) {
		Log.i("ii", workResult);
	}
}
