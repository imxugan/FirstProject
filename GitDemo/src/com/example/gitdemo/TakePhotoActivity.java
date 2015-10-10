package com.example.gitdemo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.gitdemo.base.BaseActivity;

public class TakePhotoActivity extends BaseActivity{
	private ImageView iv_show_image;
	private Uri imageUri;

//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_takephoto);
//		initView();
//	}
	

	public void manage(View v){
		switch (v.getId()) {
		case R.id.btn_takephoto:
//			。android官方的文档显示，通过intent传递的文件最大不能超过1MB，所以这种方式切割图片通常不能超过400x400，我在我的图片软件里面采用的解决办法是，把切割的图片存储到临时文件，然后在返回的activity里面读取文件来处理，可以得到你想得到的任何尺寸！
			File file = new File(Environment.getExternalStorageDirectory(), "temp.jpg");
			if(file.exists()){
				boolean delete = file.delete();
				Log.i("ii", "delete="+delete);
			}
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			imageUri = Uri.fromFile(file);
			Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");//路径中不要有空格
			intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
			startActivityForResult(intent, 100);
			break;
		case R.id.btn_choosephoto:
			File f = new File(Environment.getExternalStorageDirectory(),"mychoose.jpg");
			if(f.exists()){
				f.delete();
			}
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			imageUri = Uri.fromFile(f);
			Intent i = new Intent("android.intent.action.GET_CONTENT");
			i.setType("image/*");
			i.putExtra("scale", true);
			i.putExtra("crop", true);
			i.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
			startActivityForResult(i, 93);
			break;
		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case 100:
			if(resultCode == RESULT_OK){
				Intent intent = new Intent("com.android.camera.action.CROP");
				intent.setDataAndType(imageUri, "image/*");
				intent.putExtra("scale", true);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
				startActivityForResult(intent, 90);
			}
			break;
		case 90:
			if(resultCode == RESULT_OK){
				try {
					Log.i("ii", "imageUri===="+imageUri);
					Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
					Log.i("ii", "bitmap===="+bitmap);
					iv_show_image.setImageBitmap(bitmap);
				} catch (FileNotFoundException e) {
					Log.i("ii", "FileNotFoundException===="+e.toString());
					e.printStackTrace();
				}
			}
			break;
		case 93:
			if(resultCode == RESULT_OK){
				Uri selectedImage = data.getData();
	            String[] filePathColumn = { MediaStore.Images.Media.DATA };
	 
	            Cursor cursor = getContentResolver().query(selectedImage,
	                    filePathColumn, null, null, null);
	            cursor.moveToFirst();
	 
	            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
	            String picturePath = cursor.getString(columnIndex);
	            cursor.close();
	            iv_show_image.setImageBitmap(BitmapFactory.decodeFile(picturePath));
			}
			
			break;
		default:
			break;
		}
	}
	
	
	private void initView() {
		iv_show_image = (ImageView) findViewById(R.id.iv_show_image);
	}


	@Override
	protected void initContentView(Bundle savedInstanceState) {
		setContentView(R.layout.activity_takephoto);
		initView();
		
	}
}
