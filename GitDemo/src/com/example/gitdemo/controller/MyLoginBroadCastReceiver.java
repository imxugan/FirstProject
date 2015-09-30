package com.example.gitdemo.controller;

import com.example.gitdemo.LoginActivity;
import com.example.gitdemo.MainActivity;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.util.Log;
import android.view.WindowManager;

public class MyLoginBroadCastReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(final Context context, final Intent intent) {
		Log.i("ii", "intent.getClass()="+intent.getClass());
		Log.i("ii", "context="+context);
		AlertDialog.Builder builder = new Builder(context);
		builder.setTitle("waring")
		.setMessage("your account is login on anthor device")
		.setPositiveButton("OK", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				ActivityController.finishAll();
				Intent tent = new Intent(context,LoginActivity.class);
				tent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(tent);
			}
		})
		.setNegativeButton("cancle", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		})
		.setCancelable(false);
		AlertDialog alertDialog = builder.create();
		// 需要设置AlertDialog的类型，保证在广播接收器中可以正常弹出
		//还需要添加权限<uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW"/>
		alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		alertDialog.show();
	}
}
