package com.example.gitdemo.callback;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyLocalBroadCastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Toast.makeText(context, "这个是从ScreenTes项目发送过来的广播", 0).show();
		Log.i("ii", "验证是否能够收到从ScreenTest项目发送过来的本地广播，最终的验证结果是不能收到");
	}

}
