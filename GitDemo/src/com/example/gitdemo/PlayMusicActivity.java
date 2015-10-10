package com.example.gitdemo;

import java.io.File;
import java.io.IOException;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.gitdemo.base.BaseActivity;

public class PlayMusicActivity extends BaseActivity{
	
	private MediaPlayer player;

	private void initMediaPlayer() {
		player = new MediaPlayer();
		File f = new File(Environment.getExternalStorageDirectory()+"//wandoujia//music","eye.mp3");
		String path = f.getPath();
//		String path = Environment.getExternalStorageDirectory()+"/wandoujia/music/eye.mp3";
		
		Log.i("ii", "path====="+path);
		try {
			player.setDataSource(path);
			player.prepare();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void manage(View v){
		switch (v.getId()) {
		case R.id.btn_play:
			if(!player.isPlaying()){//如果不是播放状态，就播放
				player.start();
			}
			break;
		case R.id.btn_stop:
				if(player.isPlaying()){
					player.stop();
					initMediaPlayer();
				}
				break;
		case R.id.btn_pause:
			if(player.isPlaying()){//如果是正在播放时就暂停，若是停止或暂停转态，就不需要暂停了
				player.pause();
			}
			break;

		default:
			break;
		}
	}

	@Override
	protected void initContentView(Bundle savedInstanceState) {
		setContentView(R.layout.activity_playmusic);
		initMediaPlayer();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(null != player){
			player.stop();
			player.release();
		}
	}
}
