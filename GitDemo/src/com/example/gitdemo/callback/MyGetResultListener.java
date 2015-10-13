package com.example.gitdemo.callback;

public interface MyGetResultListener {
	void OnError(Exception e);
	void onFinsh(String string);
}
