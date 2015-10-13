package com.example.gitdemo.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.example.gitdemo.MyApplicaton;
import com.example.gitdemo.callback.MyGetResultListener;

/**
 * 联网请求类
 * @author xgxg
 * 当服务器成功响应的时候我们就可以在onFinish()方法里对响应数据进行处理了，
 * 类似地，如果出现了异常，就可以在onError()方法里对异常情况进行处理。
 * 如此一来，我们就巧妙地利用回调机制将响应数据成功返回给调用方了。
 * 另外需要注意的是，onFinish()方法和onError()方法最终还是在子线程中运行的，
 * 因此我们不可以在这里执行任何的UI操作，
 * 如果需要根据返回的结果来更新UI，则仍然要使用上一章中我们学习的异步消息处理机制。
 *
 */
public class MyHttpUtil {
	public static void httpRequest(final String address,final MyGetResultListener listener){
		if(!isNetWorkAvailable(MyApplicaton.getMyContext())){
			Toast.makeText(MyApplicaton.getMyContext(), "网络连接不可用", Toast.LENGTH_SHORT).show();
			return;
		}
		
		new Thread(){
			private BufferedReader br;
			private InputStream inputStream;

			public void run() {
				HttpURLConnection conn = null;
				URL url;
				try {
					url = new URL(address);
					conn = (HttpURLConnection) url.openConnection();
					conn.setReadTimeout(5000);
					conn.setConnectTimeout(5000);
					conn.setRequestMethod("GET");
					conn.setDoInput(true);
					conn.setDoOutput(true);
					inputStream = conn.getInputStream();
					br = new BufferedReader(new InputStreamReader(inputStream));
					String line = null;
					StringBuilder sb = new StringBuilder();
					while((line = br.readLine())!=null){
						sb.append(line);
					}
					if(listener != null){
						listener.onFinsh(sb.toString());
					}
					
				} catch (Exception e) {
					if(listener != null){
						listener.OnError(e);
					}
				}finally{
					if(br != null){
						try {
							br.close();
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
			};
		}.start();
	}

	/**
	 * 当前网络是否可用
	 * 
	 * @param paramContext
	 * @return false表示不可用
	 */
	static boolean isNetWorkAvailable(Context paramContext) {
		//测试代码
		isConnect(paramContext);
		
		
		
		ConnectivityManager localConnectivityManager = (ConnectivityManager) paramContext
				.getSystemService("connectivity");
		
		if (localConnectivityManager == null) {
			Log.i("ii", "localConnectivityManager is null return false");
			return false;
		}
		NetworkInfo localNetworkInfo = localConnectivityManager.getNetworkInfo(0);
		if (localNetworkInfo.getState() == NetworkInfo.State.CONNECTED) {
			//联网方式为手机移动数据连接
			Log.i("ii", "is NetworkInfo.State.CONNECTED return true");
			return true;
		}
		//联网方式为wifi连接
		localNetworkInfo = localConnectivityManager.getNetworkInfo(1);
		Log.i("ii", "  isConnected()返回值     "+(localNetworkInfo.getState() == NetworkInfo.State.CONNECTED));
		return (localNetworkInfo.getState() == NetworkInfo.State.CONNECTED);
	}
	
	public static boolean isConnect(Context context){
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo[] allNetworkInfo = manager.getAllNetworkInfo();
		if(null != allNetworkInfo){
			for (int i = 0; i < allNetworkInfo.length; i++) {
				Log.i("ii", "allNetworkInfo["+i+"].getTypeName="+allNetworkInfo[i].getTypeName());
			}
		}
		NetworkInfo networkInfo = manager.getNetworkInfo(0);
//		Log.i("ii", "getNetworkInfo(0)="+networkInfo);
		if(NetworkInfo.State.CONNECTED == networkInfo.getState()){
			return true;
		}
		networkInfo = manager.getNetworkInfo(1);
//		Log.i("ii", "getNetworkInfo(1)="+networkInfo);
		if(NetworkInfo.State.CONNECTED == networkInfo.getState()){
			return true;
		}
		
		return false;
		
	}
}
