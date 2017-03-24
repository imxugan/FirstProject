package com.example.gitdemo.test;

import com.example.gitdemo.LoginActivity;
import com.example.gitdemo.controller.ActivityController;
import com.example.gitdemo.util.LogUtil;

import android.test.AndroidTestCase;

public class ActivityControllerTest extends AndroidTestCase{

	//setUp()方法会在所有的测试用例执行之前调用，可以在这里进行一些初始化操作
	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}
	
	//tearDown()方法会在所有的测试用例执行之后调用，可以在这里进行一些资源释放的操作。
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testAddActivity(){
		LogUtil.i("ii", "test LogUtil");
		assertEquals(0, ActivityController.activityMap.size());
		LoginActivity logAc = new LoginActivity();
		ActivityController.addActivity(logAc);
		assertEquals(1, ActivityController.activityMap.size());
		ActivityController.addActivity(logAc);
		assertEquals(1, ActivityController.activityMap.size());
	}
	

}
