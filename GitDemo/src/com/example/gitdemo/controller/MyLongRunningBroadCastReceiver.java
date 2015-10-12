package com.example.gitdemo.controller;

import com.example.gitdemo.LoginActivity;
import com.example.gitdemo.MainActivity;
import com.example.gitdemo.service.MyLongRunningService;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.util.Log;
import android.view.WindowManager;

public class MyLongRunningBroadCastReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(final Context context, final Intent intent) {
		context.startService(new Intent(context,MyLongRunningService.class));
	}
}
