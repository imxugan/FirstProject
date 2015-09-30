package com.example.gitdemo;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gitdemo.base.BaseActivity;

public class LoginActivity extends BaseActivity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		CrashHandler crashHandler = CrashHandler.getInstance();
		crashHandler.init(this);
		final EditText et_username = (EditText) findViewById(R.id.et_username);
		final EditText et_psw = (EditText) findViewById(R.id.et_psw);
		Button btn_login = (Button) findViewById(R.id.btn_login);
		btn_login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String user_name = et_username.getText().toString();
				String psw = et_psw.getText().toString();
				Log.i("ii", "user_name="+user_name);
				if(TextUtils.isEmpty(user_name) || TextUtils.isEmpty(psw)){
					Toast.makeText(LoginActivity.this, "用户名或密码不能为空", 0).show();
				}else{
					if("123".equals(user_name) && "123456".equals(psw)){
						startActivity(new Intent(LoginActivity.this,MainActivity.class));
						finish();
					}else{
						Toast.makeText(LoginActivity.this, "用户名或密码错误", 0).show();
					}
				}
				
			}
		});
	}

}
