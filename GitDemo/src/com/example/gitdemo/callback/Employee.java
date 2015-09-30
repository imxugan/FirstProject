package com.example.gitdemo.callback;

public class Employee {

	public void work(Manager manager, String string) {
		String workResult = "任务完成";
		manager.getCall(string+workResult);
	}
}
