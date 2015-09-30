package com.example.gitdemo;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.gitdemo.callback.CEO;
import com.example.gitdemo.callback.Charger;
import com.example.gitdemo.callback.Employee;
import com.example.gitdemo.callback.Manager;
import com.example.gitdemo.callback.MyLocalBroadCastReceiver;
import com.example.gitdemo.callback.Worker;
import com.example.gitdemo.controller.MyLoginBroadCastReceiver;

public class MainActivity extends Activity {
	private LocalBroadcastManager manager;
	private MyLoginBroadCastReceiver receiver;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		callBackTest();
		
		testBroadCast();
		
		forceOffLine();
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		manager.unregisterReceiver(receiver);
	}

}
