package com.example.gitdemo.callback;

import android.util.Log;

public class Charger implements CallBack{
	public Charger(Worker worker){
		worker.work(this, this.getClass().getSimpleName()+"sdk项目");
	}

	@Override
	public void resultBack(String result) {
		Log.i("ii", result);
	}
}
