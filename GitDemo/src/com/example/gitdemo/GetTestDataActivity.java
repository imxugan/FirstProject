package com.example.gitdemo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.gitdemo.base.BaseActivity;
import com.example.gitdemo.bean.Book;
import com.example.gitdemo.bean.Person;

public class GetTestDataActivity extends BaseActivity{

	@Override
	protected void initContentView(Bundle savedInstanceState) {
		setContentView(R.layout.acvitity_get_test_data);
		Intent intent = getIntent();
		Person p = (Person) intent.getExtras().getSerializable("person");
		if(null != p){
			Log.e("ii", "GetTestDataActivity---Serializable--"+p.id+"---"+p.name+"---"+p.age+"---"+p.sex);
		}
		
		Book book = intent.getParcelableExtra("book");
		if(null != book){
			Log.e("ii", "GetTestDataActivity---Parcelable--"+book.id+"---"+book.name+"---"+book.pages);
		}
	}

}
