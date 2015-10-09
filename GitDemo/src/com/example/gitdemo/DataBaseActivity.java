package com.example.gitdemo;

import com.example.gitdemo.util.MySqliteOpenHelper;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class DataBaseActivity extends Activity{
	
	private MySqliteOpenHelper helper;
	private SQLiteDatabase writableDatabase;
	private EditText et_show_data;
	private String name;
	private double price;
	private int pages;
	private String author;

	@Override
	protected void onCreate(Bundle savedInstanceState) {  
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_database);
		helper = new MySqliteOpenHelper(DataBaseActivity.this, "book.db", null, 15);
		init();
	}

	private void init() {
		et_show_data = (EditText) findViewById(R.id.et_show_data);
	}
	
	public void manage(View v){
		switch (v.getId()) {
		case R.id.btn_create_database:
			writableDatabase = helper.getWritableDatabase();
			
			break;
		case R.id.btn_add:
			writableDatabase = helper.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put("name", "the first code for android");
			values.put("author", "guolin");
			values.put("price", 68.00);
			values.put("pages", 575);
			writableDatabase.insert("book", null, values);
			values.clear();
			values.put("name", "think in java");
			values.put("author", "dashen");
			values.put("price", 98);
			values.put("pages", 1909);
			writableDatabase.insert("book", null, values);
			
			//向数据库插入数据的第二种写法
			String sql = "insert into book (name,author,price,pages) values(?,?,?,?)";
			writableDatabase.execSQL(sql,new String[]{"fengkuang android","ligang","78","699"});
			break;
		case R.id.btn_modify:
			writableDatabase = helper.getWritableDatabase();
			ContentValues value = new ContentValues();
			value.put("price", 58);
			writableDatabase.update("book", value, "name = ?", new String[]{"think in java"});
			
			//修改数据库中的数据的第二种写法
			String updateSql = "update book set price = ? where name=?";
			writableDatabase.execSQL(updateSql,new String[]{"100","fengkuang android"});
			break;
		case R.id.btn_delete:
			writableDatabase = helper.getWritableDatabase();
			writableDatabase.delete("book", "pages > ?", new String[]{"600"});
			
			//删除数据库中的数据的第二种写法
			writableDatabase.execSQL("delete from book where pages>?",new String[]{"599"});
			break;
		case R.id.btn_delete_all:
			writableDatabase = helper.getWritableDatabase();
			writableDatabase.execSQL("delete from book");
			break;
		case R.id.btn_replace:
			//现在有一个需求，需要向book表中的旧数据删除后添加新数据，如果新数据添加失败，则旧数据不能删除
			writableDatabase = helper.getWritableDatabase();
			writableDatabase.beginTransaction();
			try{
				writableDatabase.execSQL("delete from book");
//				if(true){
//					throw new NullPointerException();//抛出异常导致事务不能成功执行，所以新数据无法插入表中，旧数据这时就不会删除
//				}
				ContentValues values_data = new ContentValues();
				values_data.put("name", "激荡三十年");
				values_data.put("author", "吴晓波");
				values_data.put("price", 48);
				values_data.put("pages", 990);
				writableDatabase.insert("book", null, values_data);
				//别忘记这行代码
				writableDatabase.setTransactionSuccessful();//事务已经成功执行
			}catch(Exception e){
				
			}finally{
				writableDatabase.endTransaction();
			}
			
			
			break;
		case R.id.btn_search:
			et_show_data.setText("");
			SQLiteDatabase readableDatabase = helper.getReadableDatabase();
			Cursor cursor = readableDatabase.query("book", null, null, null, null, null, null);
			Log.i("ii", "olumnNames="+cursor.getColumnNames());
			Log.i("ii", "cursor.moveToFirst()="+cursor.moveToFirst());
			if(cursor.moveToFirst()){
				do{
					name = cursor.getString(cursor.getColumnIndex("name"));
					price = cursor.getDouble(cursor.getColumnIndex("price"));
					pages = cursor.getInt(cursor.getColumnIndex("pages"));
					author = cursor.getString(cursor.getColumnIndex("author"));
				}while(cursor.moveToNext());
				et_show_data.setText(name+"---"+author+"---"+price+"---"+pages);
			}
			cursor.close();
			
			//查询数据库的第二种写法
			Cursor rawQuery = readableDatabase.rawQuery("select * from book", null);
			if(rawQuery.moveToFirst()){
				do{
					name = rawQuery.getString(cursor.getColumnIndex("name"));
					Log.i("ii", "name="+name);
					price = rawQuery.getDouble(cursor.getColumnIndex("price"));
					pages = rawQuery.getInt(cursor.getColumnIndex("pages"));
					author = rawQuery.getString(cursor.getColumnIndex("author"));
					Log.i("ii", "ColumnIndex="+cursor.getColumnIndex("category_id"));
//					Log.i("ii", "category_id="+rawQuery.getInt(cursor.getColumnIndex("category_id")));
				}while(rawQuery.moveToNext());
				et_show_data.setText(name+"---"+author+"---"+price+"---"+pages);
			}
			rawQuery.close();
			break;
		default:
			break;
		}
	}
}
