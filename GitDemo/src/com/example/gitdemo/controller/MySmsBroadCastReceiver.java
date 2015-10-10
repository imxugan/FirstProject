package com.example.gitdemo.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

public class MySmsBroadCastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();
		Object[] object = (Object[])bundle.get("pdus");
		SmsMessage[] message = new SmsMessage[object.length];
		for (int i = 0; i < message.length; i++) {
			message[i] = SmsMessage.createFromPdu((byte[])object[i]);
		}
		String orginalAddress = message[0].getOriginatingAddress();
		String smsContent = "";
		for (int i = 0; i < message.length; i++) {
			smsContent += message[i].getMessageBody();
		}
		Log.i("ii", "orginalAddress="+orginalAddress);
		Log.i("ii", "smsContent="+smsContent);
	}

}
