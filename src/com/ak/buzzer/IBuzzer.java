package com.ak.buzzer;

import android.content.Context;
import android.net.Uri;

public interface IBuzzer {
	public void playSound();
	public void stopSound();
	public void onPlaybackComplete();
	public boolean isPlaybackRunning();
	public void setMediaSource(Context context,Uri resId);
	public void registerListener(IPlaybackListener listener);
	public void unregisterListener(IPlaybackListener listener);
}
