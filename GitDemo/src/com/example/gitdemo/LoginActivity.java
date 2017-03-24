package com.example.gitdemo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gitdemo.base.BaseActivity;

public class LoginActivity extends BaseActivity{
	
	private SharedPreferences sp;

//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_login);
//		sp = this.getSharedPreferences("login_info", Context.MODE_PRIVATE);
//		
//		CrashHandler crashHandler = CrashHandler.getInstance();
//		crashHandler.init(this);
//		final EditText et_username = (EditText) findViewById(R.id.et_username);
//		final EditText et_psw = (EditText) findViewById(R.id.et_psw);
//		
//		String user_name = sp.getString("user_name", "");
//		String psw = sp.getString("psw", "");
//		if(!TextUtils.isEmpty(user_name)){
//			et_username.setText(user_name);
//			et_username.setSelection(user_name.length());
//		}
//		
//		if(!TextUtils.isEmpty(psw)){
//			et_psw.setText(psw);
//			et_psw.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
//			et_psw.setSelection(psw.length());
//		}
//		
//		Button btn_login = (Button) findViewById(R.id.btn_login);
//		btn_login.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				String user_name = et_username.getText().toString();
//				String psw = et_psw.getText().toString();
//				Editor editor = sp.edit();
//				editor.putString("user_name", user_name);
//				editor.putString("psw", psw);
//				editor.commit();
//				Log.i("ii", "user_name="+user_name);
//				if(TextUtils.isEmpty(user_name) || TextUtils.isEmpty(psw)){
//					Toast.makeText(LoginActivity.this, "用户名或密码不能为空", 0).show();
//				}else{
//					if("123".equals(user_name) && "123456".equals(psw)){
//						startActivity(new Intent(LoginActivity.this,MainActivity.class));
//						finish();
//					}else{
//						Toast.makeText(LoginActivity.this, "用户名或密码错误", 0).show();
//					}
//				}
//				
//			}
//		});
//	}

	@Override
	protected void initContentView(Bundle savedInstanceState) {
		setContentView(R.layout.activity_login);
		sp = this.getSharedPreferences("login_info", Context.MODE_PRIVATE);
		
		CrashHandler crashHandler = CrashHandler.getInstance();
		crashHandler.init(this);
		final EditText et_username = (EditText) findViewById(R.id.et_username);
		final EditText et_psw = (EditText) findViewById(R.id.et_psw);
		
		String user_name = sp.getString("user_name", "");
		String psw = sp.getString("psw", "");
		if(!TextUtils.isEmpty(user_name)){
			et_username.setText(user_name);
			et_username.setSelection(user_name.length());
		}
		
		if(!TextUtils.isEmpty(psw)){
			et_psw.setText(psw);
			et_psw.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
			et_psw.setSelection(psw.length());
		}
		
		Button btn_login = (Button) findViewById(R.id.btn_login);
		btn_login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String user_name = et_username.getText().toString();
				String psw = et_psw.getText().toString();
				Editor editor = sp.edit();
				editor.putString("user_name", user_name);
				editor.putString("psw", psw);
				editor.commit();
				Log.i("ii", "user_name="+user_name);
				if(TextUtils.isEmpty(user_name) || TextUtils.isEmpty(psw)){
					Toast.makeText(LoginActivity.this, "用户名或密码不能为空", 0).show();
				}else{
					if("123".equals(user_name) && "123456".equals(psw)){
						startActivity(new Intent(LoginActivity.this,MainActivity.class));
						finish();
					}else{
						Toast.makeText(LoginActivity.this, "用户名或密码错误,正确的用户名为123，密码是123456", 0).show();
					}
				}
				
			}
		});
		
	}

}
