package com.example.gitdemo.callback;

import android.util.Log;

public class CEO implements CallBack{
	public CEO(Worker worker){
		worker.work(this,this.getClass().getSimpleName()+"无线项目");
	}

	//这个方法就是java中所称的回调方法
	@Override
	public void resultBack(String result) {
		Log.i("ii", result);
	}
}
