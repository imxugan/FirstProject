package com.example.gitdemo;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.gitdemo.base.BaseActivity;

public class UpdataUIActivity extends BaseActivity{

	protected static final int SUCCESS = 0;
	private TextView tv;
	private ProgressDialog pd;
	
	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SUCCESS:
				tv.setText("this is handler");
				break;

			default:
				break;
			}
		};
	};

	@Override
	protected void initContentView(Bundle savedInstanceState) {
		setContentView(R.layout.activity_update_ui);
		tv = (TextView) findViewById(R.id.tv);
		pd = new ProgressDialog(UpdataUIActivity.this);
		pd.setMax(100);
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);//设置水平进度条
		pd.setTitle("正在下载中......");
		
	}
	
	public void manage(View v){
		switch (v.getId()) {
		case R.id.btn_change_ui:
			new Thread(){
				public void run() {
					Message msg = Message.obtain();
					msg.what = SUCCESS;
					mHandler.sendMessage(msg);
				};
			}.start();
			break;
		case R.id.btn_asytask:
			new MyAsynctask().execute();
			break;
		case R.id.btn_download:
			new DownLoadTask().execute();
			break;
		default:
			break;
		}
	}
	
	private class DownLoadTask extends AsyncTask<Void, Integer, Boolean>{
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pd.show();
			
		}
		
		@Override
		protected Boolean doInBackground(Void... params) {
			//耗时操作都放入这个方法中去执行
			int i = 0;  
            while (i < 100) {  
                try {  
                    Thread.sleep(200);  
                    // 更新进度条的进度,可以在子线程中更新进度条进度  
                    pd.incrementProgressBy(1);  
//	                    pd.incrementSecondaryProgressBy(10);//二级进度条更新方式  
                    i++;  
                    publishProgress(i);//这句代码是关键,否则onProgressUpdate方法不会执行
                } catch (Exception e) {  
                    // TODO: handle exception  
                }  
            }  
	           
			return true;
		}
		
		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
			Log.i("ii", "values[0]====="+values[0]);
			pd.setMessage("已经下载了"+values[0]+"%");
			pd.setTitle("正在下载中......已经下载了"+values[0]+"%");
			
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result){
				// 在进度条走完时删除Dialog  
	            pd.dismiss(); 
			}
		}
		
	}
	
	private class MyAsynctask extends AsyncTask<Void, Integer, Void>{
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
		
		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			tv.setText("asynctask is work");
		}
		
	}
}
