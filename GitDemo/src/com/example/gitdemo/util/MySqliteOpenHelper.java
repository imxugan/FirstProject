package com.example.gitdemo.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class MySqliteOpenHelper extends SQLiteOpenHelper {
	public static final String Create_Book = "create table book ("
			+"id integer primary key autoincrement,"
			+"author text,"
			+"price real,"
			+"pages integer," //别忘记逗号
			+"name text"
			+")";
	
	public static final String Craeate_Category = "create table category("
			+"id integer primary key autoincrement,"
			+"category_name text,"
			+"category_code text"
			+")";
	private Context mContext;

	public MySqliteOpenHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		this.mContext = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(Create_Book);
		db.execSQL(Craeate_Category);
		Toast.makeText(mContext, "create database succeed", 0).show();
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.i("ii", "oldVersion="+oldVersion+"------newVersion="+newVersion);
//		db.execSQL("drop table if exists book");
//		db.execSQL("drop table if exists Category");
//		onCreate(db);
		switch (oldVersion) {
		case 1:
			db.execSQL(Craeate_Category);
			Log.i("ii", "case 1:");
			//注意这里没有break
		case 10:
			db.execSQL("alter table book add column category_id integer");
			Log.i("ii", "case 10:");
			//注意这里没有break
		default:
			Log.i("ii", "default=如果case1 和case10都不匹配则执行default后直接退出");
			//注意这里没有break
		}
	}

}
