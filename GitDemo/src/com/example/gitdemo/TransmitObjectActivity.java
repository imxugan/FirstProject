package com.example.gitdemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.gitdemo.base.BaseActivity;
import com.example.gitdemo.bean.Book;
import com.example.gitdemo.bean.Person;

public class TransmitObjectActivity extends BaseActivity{

	@Override
	protected void initContentView(Bundle savedInstanceState) {
		setContentView(R.layout.activity_transmit_object);
	}
	
	public void manage(View v){
		switch (v.getId()) {
		case R.id.btn_serializable_object:
			Intent i = new Intent(TransmitObjectActivity.this,GetTestDataActivity.class);
			Person p = new Person();
			p.age = 10;
			p.id = "1";
			p.name = "wandoneg";
			p.sex = "w0man";
			i.putExtra("person", p);//传递自定义对象时，自定义对象类需要实现序列化接口
			startActivity(i);
			break;
		case R.id.btn_parcelable_object:
			i = new Intent(TransmitObjectActivity.this,GetTestDataActivity.class);
			Book book = new Book();
			book.id = "12";
			book.pages = 20;
			book.name = "abc";
			i.putExtra("book", book);
			startActivity(i);
			break;
		default:
			break;
		}
	}

}
