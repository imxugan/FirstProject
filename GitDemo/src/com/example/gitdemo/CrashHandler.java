package com.example.gitdemo;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 *	ClassName:	CrashHandler
 *	Function: 	UncaughtException,当程序发生异常时，该类来捕获异常信息
 */
class CrashHandler implements UncaughtExceptionHandler {
	
	private static final String TAG = "CH";
	/**
	 * 	系统默认的UncaughtException处理类
	 * 	Thread.UncaughtExceptionHandler			:		mDefaultHandler	
	 */
	private UncaughtExceptionHandler mDefaultHandler ;
	/**
	 * 	CrashHandler实例
	 * 	CrashHandler:mInstance	
	 */
	private static CrashHandler mInstance = new CrashHandler() ;
	/**
	 * 	程序的Context对象
	 * 	Context:mContext	
	 */
	private Context mContext ;
	
	/**
	 * 	Creates a new instance of CrashHandler.
	 */
	private CrashHandler() {
	}
	/**
	 * 	getInstance:{获取CrashHandler实例，单例模式 }
	 * 	@return 	CrashHandler   
	 * 	@throws 
	 */
	public static CrashHandler getInstance() {
		return mInstance ;
	}
	/**
	 * 	init:{初始化}
	 * 	@param 		paramContext
	 * 	@return 	void   
	 * 	@throws 
	 */
	public void init(Context paramContext) {
		mContext = paramContext ;
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler() ;
		Thread.setDefaultUncaughtExceptionHandler(this) ;
	}
	
	/**
	 * 	当UncaughtException发生时会转入该重写的方法处理
	 */
	@Override
	public void uncaughtException(Thread paramThread , Throwable paramThrowable) {
		 if (!handleException(paramThrowable) && mDefaultHandler != null) {  
	            mDefaultHandler.uncaughtException(paramThread, paramThrowable);  
        } else {  
        	Writer mWriter = new StringWriter() ;
    		PrintWriter mPrintWriter = new PrintWriter(mWriter) ;
    		paramThrowable.printStackTrace(mPrintWriter) ;
    		Throwable mThrowable = paramThrowable.getCause() ;
    		while(mThrowable != null) {
    			mThrowable.printStackTrace(mPrintWriter) ;
    			mPrintWriter.append("\r\n") ;
    			mThrowable = mThrowable.getCause() ;
    		}
    		mPrintWriter.flush();
    		mPrintWriter.close() ;
    		String mResult = mWriter.toString() ;
    		Log.i(TAG, "mResult="+mResult);
            android.os.Process.killProcess(android.os.Process.myPid());  
        }  
	}
	
	  /** 
     * 自定义错误处理，收集错误信息，发送错误报告等操作均在此完成 
     *  
     * @param ex 
     * @return true：如果处理了该异常信息；否则返回 false 
     */  
    private boolean handleException(Throwable ex) {  
        if (ex == null) {  
            return false;  
        }  
        return true;  
    }  
	
}
