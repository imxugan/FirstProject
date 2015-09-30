package com.example.gitdemo.callback;

public class Worker {

	public void work(CallBack callBacker, String string) {
		String result = "项目完成";
		callBacker.resultBack(string+result);
	}

}
