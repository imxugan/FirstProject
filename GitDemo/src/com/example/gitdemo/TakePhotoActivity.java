package com.example.gitdemo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import com.example.gitdemo.base.BaseActivity;

public class TakePhotoActivity extends BaseActivity{
	private ImageView iv_show_image;
	private Uri imageUri;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_takephoto);
		initView();
	}
	

	public void manage(View v){
		switch (v.getId()) {
		case R.id.btn_takephoto:
			File file = new File(Environment.getExternalStorageDirectory(), "temp.jpg");
			if(file.exists()){
				file.delete();
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
					Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
					iv_show_image.setImageBitmap(bitmap);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
			break;
		default:
			break;
		}
	}
	
	
	@Override
	public void initView() {
		iv_show_image = (ImageView) findViewById(R.id.iv_show_image);
	}
}
