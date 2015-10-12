package com.example.gitdemo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EncodingUtils;
import org.apache.http.util.EntityUtils;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.example.gitdemo.base.BaseActivity;

public class HttpTestActivity extends BaseActivity{

	protected static final int SUCCESS = 0;
	private BufferedReader reader;
	private InputStream inputStream;
	private TextView tv;
	
	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case SUCCESS:
				String result = (String) msg.obj;
				tv.setText(result);
				break;

			default:
				break;
			}
		};
	};

	@Override
	protected void initContentView(Bundle savedInstanceState) {
		setContentView(R.layout.activity_http_test);
		
		tv = (TextView) findViewById(R.id.tv);
	}

	public void manage(View v){
		switch (v.getId()) {
		case R.id.btn_httpUrlConnection:
			new Thread(){
				public void run() {
					String result = requestHttpWithUrlConnection();
					Message msg = Message.obtain();
					msg.what = SUCCESS;
					msg.obj = result;
					mHandler.sendMessage(msg);
				};
			}.start();
			
			break;
		case R.id.btn_httpClient:
			new Thread(){
				public void run() {
					String result = (String) requestHttpWithClient();
					Message msg = Message.obtain();
					msg.what = SUCCESS;
					msg.obj = result;
					mHandler.sendMessage(msg);
				};
			}.start();
			
			break;
		default:
			break;
		}
	}

	private Object requestHttpWithClient() {
		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet("http://www.sohu.com");
		try {
			HttpResponse response = client.execute(get);
			if(response.getStatusLine().getStatusCode() == 200 ){//响应成功
				HttpEntity entity = response.getEntity();
				String result = EntityUtils.toString(entity, "utf-8");
				return result;
			}
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
		
	}

	private String requestHttpWithUrlConnection() {
		HttpURLConnection conn = null;
		try {
			URL url = new URL("http://www.163.com");
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setReadTimeout(5000);
			conn.setConnectTimeout(5000);
			inputStream = conn.getInputStream();
			
			reader = new BufferedReader(new InputStreamReader(inputStream));
			String line = null;
			//StringBufferd支持并发操作，线性安全的，适 合多线程中使用。StringBuilder不支持并发操作，线性不安全的，不适合多线程中使用
			StringBuilder sb = new StringBuilder();
			while((line = reader.readLine())!=null){
				sb.append(line);
			}
			return sb.toString();
		} catch (MalformedURLException e) {
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
			
			if(inputStream != null){
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			if(conn != null){
				conn.disconnect();
			}
		}
		return null;
	}
}
