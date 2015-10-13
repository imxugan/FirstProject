package com.example.gitdemo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.example.gitdemo.callback.CEO;
import com.example.gitdemo.callback.Charger;
import com.example.gitdemo.callback.Employee;
import com.example.gitdemo.callback.Manager;
import com.example.gitdemo.callback.MyLocalBroadCastReceiver;
import com.example.gitdemo.callback.Worker;
import com.example.gitdemo.controller.MyLoginBroadCastReceiver;
import com.example.gitdemo.controller.MySmsBroadCastReceiver;
import com.example.gitdemo.controller.SendSmsStatsReceiver;

public class MainActivity extends Activity {
	private LocalBroadcastManager manager;
	private MyLoginBroadCastReceiver receiver;
	private EditText et_input;
	private Builder mBuilder;
	private NotificationManager service;
	private MySmsBroadCastReceiver smsReceiver;
	private SendSmsStatsReceiver smsStatsReceiver;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		init();
		
		callBackTest();
		
		testBroadCast();
		
		forceOffLine();
		
		
		
	}
	
	private void init() {
		et_input = (EditText) findViewById(R.id.et_input);
		
		String data = readSavedData();
		if(!TextUtils.isEmpty(data)){
			et_input.setText(data);
			et_input.setSelection(data.length());
			et_input.setFocusable(false);
		}
		
		service = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		
	}
	

	private String readSavedData() {
		FileInputStream inputStream = null;
		BufferedReader reader = null;
		
		try {
			inputStream = this.openFileInput("login_data");
			reader = new BufferedReader(new InputStreamReader(inputStream));
			String line ;
			StringBuffer buffer = new StringBuffer();
			while((line = reader.readLine())!=null){
				buffer.append(line);
			}
			return buffer.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(reader != null){
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			if(inputStream !=null){
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	private void saveData(String data) {
		FileOutputStream output = null;
		BufferedWriter bw = null ;
		try {
			output = this.openFileOutput("login_data", Context.MODE_PRIVATE);
			
			bw = new BufferedWriter(new OutputStreamWriter(output));
			
			bw.write(data);
			bw.flush();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(bw != null){
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			if(output != null){
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void forceOffLine(){
		manager = LocalBroadcastManager.getInstance(MainActivity.this);
		IntentFilter filter = new IntentFilter("com.example.gitdemo.LoginBroadCast");
		receiver = new MyLoginBroadCastReceiver();
		manager.registerReceiver(receiver, filter);
		
		Button btn_force_offline = (Button) findViewById(R.id.btn_force_offline);
		btn_force_offline.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				manager.sendBroadcast(new Intent("com.example.gitdemo.LoginBroadCast"));
			}
		});
	}

	private void testBroadCast() {
		LocalBroadcastManager manager = LocalBroadcastManager.getInstance(this);
		IntentFilter filter = new IntentFilter("com.example.screentest.LocalBroadCast");
		manager.registerReceiver(new MyLocalBroadCastReceiver(), filter);
	}

	private void callBackTest() {
		//现在我们来总结下满足回调的两个基本条件：
		//1.Class A调用Class B中的X方法
		//2.ClassB中X方法执行的过程中调用Class A中的Y方法完成回调
		//创建一个员工
		Employee employee = new Employee();
		//创建一个经理
		new Manager(employee);
		
		/*****************************************/
		//假设总经理出差前交了件事情给我去办，不巧……副总经理也要给我件事去办，更无耻的是……主管也发任务过来了，
		//都要求说做完就打电话通知他们……这时我们就要定义更多类，什么总经理类啦~经理类啦~主管类啦~杂七杂八的类，
		//但是这些杂七杂八的大爷们都要求做完事情就电话通知，每个类都会有一个类似phoneCall的方法作为回调方法，
		//这时，我们利用面向对象的思想来看~是不是可以把这个回调方法抽象出来作为一个独立的抽象类或接口呢
		//创建一个员工
		Worker worker = new Worker();
		//创建一个CEO
		new CEO(worker);
		//创建一个主管
		new Charger(worker);
	}

	
	public void done(View v){
		switch (v.getId()) {
		case R.id.btn_save_data:
			String input = et_input.getText().toString();
			if(!TextUtils.isEmpty(input)){
				saveData(input);
			}else{
				saveData("this is only a data save test");
			}
			break;
		case R.id.btn_test_database:
			startActivity(new Intent(MainActivity.this,DataBaseActivity.class));	
			break;
		case R.id.btn_notification:
			initNotify();
			break;
		case R.id.btn_notify_anthor_activity:
			notifyActivity();
			break;
		case R.id.btn_sms:
			receivceSms();
			break;
		case R.id.btn_send_sms:
			sendSms();
			break;
		case R.id.btn_takephoto:
			startActivity(new Intent(MainActivity.this,TakePhotoActivity.class));
			break;
		case R.id.btn_sound:
			startActivity(new Intent(MainActivity.this,PlayMusicActivity.class));
			break;
		case R.id.btn_video:
			startActivity(new Intent(MainActivity.this,PlayVideoActivity.class));
			break;
		case R.id.btn_change_ui:
			startActivity(new Intent(MainActivity.this,UpdataUIActivity.class));
			break;
			
		case R.id.btn_service:
			startActivity(new Intent(MainActivity.this,TestServiceActivity.class));
			break;
		case R.id.btn_webview:
			startActivity(new Intent(MainActivity.this,WebViewTestActivity.class));
			break;
		case R.id.btn_http_connect:
			startActivity(new Intent(MainActivity.this,HttpTestActivity.class));
			break;
		case R.id.btn_parse_data:
			startActivity(new Intent(MainActivity.this,ParseDataActivity.class));
			break;
		case R.id.btn_transmit_object:
			startActivity(new Intent(MainActivity.this,TransmitObjectActivity.class));
			break;
		default:
			break;
		}
	}
	
	private void sendSms() {
		smsStatsReceiver = new SendSmsStatsReceiver();
		IntentFilter filter = new IntentFilter("SENT_SMS_ACTION");
		registerReceiver(smsStatsReceiver, filter);
		SmsManager smsManager = SmsManager.getDefault();
		String destinationAddress = "123456";
		Intent intent = new Intent("SENT_SMS_ACTION");
		PendingIntent sentIntent = PendingIntent.getBroadcast(MainActivity.this, 100, intent,  0);
		smsManager.sendTextMessage(destinationAddress, null, "android 书哪家强", sentIntent, null);
		
	}

	private void receivceSms() {
		smsReceiver = new MySmsBroadCastReceiver();
		IntentFilter filter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
		registerReceiver(smsReceiver, filter);
	}
	
	/** 初始化通知栏 */
	private void initNotify(){
		mBuilder = new NotificationCompat.Builder(this);
		mBuilder.setContentTitle("测试标题")
				.setContentText("测试内容")
//				.setContentIntent(getDefalutIntent(Notification.FLAG_AUTO_CANCEL))
//				.setNumber(number)//显示数量
				.setTicker("测试通知来啦")//通知首次出现在通知栏，带上升动画效果的
				.setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示
				.setPriority(Notification.PRIORITY_DEFAULT)//设置该通知优先级
				.setAutoCancel(true)
				.setOngoing(false)//ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
				.setDefaults(Notification.DEFAULT_VIBRATE)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合：
				//Notification.DEFAULT_ALL  Notification.DEFAULT_SOUND 添加声音 // requires VIBRATE permission
				.setSmallIcon(R.drawable.ic_launcher);
		service.notify(1, mBuilder.build());
//		service.cancel(1);//添加这句代码，通知会在状态栏自动消失
	}
	
	/** 初始化通知栏 */
	private void notifyActivity(){
		mBuilder = new NotificationCompat.Builder(this);
		mBuilder.setContentTitle("测试标题")
//				.setLights(Color.RED, 1000, 1000)//需要<uses-permission android:name="android.permission.FLASHLIGHT">
				.setContentText("打开一个新的activity")
				.setContentIntent(getDefalutIntent(Notification.FLAG_AUTO_CANCEL))
//				.setNumber(number)//显示数量
				.setTicker("测试通知来啦")//通知首次出现在通知栏，带上升动画效果的
				.setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示
				.setPriority(100)//设置该通知优先级
				.setAutoCancel(true)//设置??????
				.setOngoing(false)//ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
				.setDefaults(Notification.DEFAULT_VIBRATE|Notification.DEFAULT_LIGHTS)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合：
				//Notification.DEFAULT_ALL  Notification.DEFAULT_SOUND 添加声音 // requires VIBRATE permission
				.setSmallIcon(R.drawable.ic_launcher);
		Notification build = mBuilder.build();
		service.notify(2, build);
//		通过查看灯的实现源码，发现如下问题： 
//		在来电或者系统屏幕亮的情况下是没办法控制灯的
	}
	
	/**
	 * @获取默认的pendingIntent,为了防止2.3及以下版本报错
	 * @flags属性:  
	 * 在顶部常驻:Notification.FLAG_ONGOING_EVENT  
	 * 点击去除： Notification.FLAG_AUTO_CANCEL 
	 */
	public PendingIntent getDefalutIntent(int flags){
		PendingIntent pendingIntent= PendingIntent.getActivity(this, 1, new Intent(this,NotificationActivity.class), flags);
		return pendingIntent;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.i("ii", "MainActivity	receiver="+receiver);
		Log.i("ii", "MainActivity	smsReceiver="+smsReceiver);
		Log.i("ii", "MainActivity	smsStatsReceiver="+smsStatsReceiver);
		
		manager.unregisterReceiver(receiver);
		if(smsReceiver != null){
			unregisterReceiver(smsReceiver);
		}
		
		if(smsStatsReceiver != null){
			unregisterReceiver(smsStatsReceiver);
		}
		
	}

}
