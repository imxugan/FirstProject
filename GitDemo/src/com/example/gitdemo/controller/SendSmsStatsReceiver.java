package com.example.gitdemo.controller;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class SendSmsStatsReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if(getResultCode() == Activity.RESULT_OK){
			Toast.makeText(context, "短信发送成功", 0).show();
		}else{
			Toast.makeText(context, "短信发送失败", 0).show();
		}

	}

}
