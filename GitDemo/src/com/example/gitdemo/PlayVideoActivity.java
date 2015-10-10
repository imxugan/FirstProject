package com.example.gitdemo;

import java.io.File;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.VideoView;

import com.example.gitdemo.base.BaseActivity;

public class PlayVideoActivity extends BaseActivity{

	private VideoView videoview;

	@Override
	protected void initContentView(Bundle savedInstanceState) {
		setContentView(R.layout.activity_playvideo);
		videoview = (VideoView) findViewById(R.id.videview);
		initVideoView();
	}

	private void initVideoView() {
//		File f = new File(Environment.getExternalStorageDirectory()+"//wandoujia//video","aa.mp4");
//		videoview.setVideoPath(f.getPath());
//		Log.i("ii", "path====="+f.getPath());
		
		File f = new File(Environment.getExternalStorageDirectory()+"//wandoujia//video","X素大战.Pixels.2015.mp4");
		String path = f.getPath();
		videoview.setVideoPath(path);
		Log.i("ii", "path====="+f.getPath());
	}

	public void manage(View v){
		switch (v.getId()) {
		case R.id.btn_play:
			if(!videoview.isPlaying()){
				videoview.start();
			}
			break;
		case R.id.btn_pause:
			if(videoview.isPlaying()){
				videoview.pause();
			}
			break;
		case R.id.btn_replay:
			if(videoview.isPlaying()){
				videoview.resume();
			}
			break;
		default:
			break;
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(null != videoview){
			videoview.suspend();
		}
	}

}
