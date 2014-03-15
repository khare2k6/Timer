package com.ak.buzzer;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.util.Log;

public class Buzzer implements IBuzzer,OnCompletionListener{

	private List<IPlaybackListener> mListener;
	private MediaPlayer mPlayer;

	public Buzzer(){
		mListener = new ArrayList<IPlaybackListener>();
	}
	
	@Override
	public void playSound() {
		if(mPlayer != null)
			mPlayer.start();
	}

	@Override
	public void onPlaybackComplete() {
		for(IPlaybackListener listener:mListener)
			listener.onPlaybackFinish();
	}

	@Override
	public boolean isPlaybackRunning() {
		return mPlayer.isPlaying();
	}

	@Override
	public void setMediaSource(Context context,Uri resId) {
		if(mPlayer != null)
			mPlayer.release();
		Log.d("Buzzer","setMediaSource:"+resId);
		mPlayer = MediaPlayer.create(context, resId);
		Log.d("Buzzer","setMediaSource mPlayer:"+mPlayer);
		mPlayer.setOnCompletionListener(this);
	}
	
	@Override
	public void registerListener(IPlaybackListener listener) {
		mListener.add(listener);
	}

	@Override
	public void unregisterListener(IPlaybackListener listener) {
		mListener.remove(listener);
	}

	@Override
	public void stopSound() {
	mPlayer.pause();
	mPlayer.seekTo(0);
	}

	@Override
	public void onCompletion(MediaPlayer arg0) {
		onPlaybackComplete();
	}
}
