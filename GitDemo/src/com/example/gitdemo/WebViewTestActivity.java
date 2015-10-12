package com.example.gitdemo;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.gitdemo.base.BaseActivity;

public class WebViewTestActivity extends BaseActivity{

	private WebView webView;

	@Override
	protected void initContentView(Bundle savedInstanceState) {
		setContentView(R.layout.activity_webview);
		//要联网，记得加联网权限
		webView = (WebView) findViewById(R.id.webview);
		Log.i("ii", "webView======"+webView);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.setWebViewClient(new WebViewClient(){
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				
				return true;//不在浏览器中打开，webview自己来处理
			}
			
		});
		webView.loadUrl("http://www.163.com");
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode ==KeyEvent.KEYCODE_BACK && webView.canGoBack() ){
			webView.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
