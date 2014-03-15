package com.ak.timer;

import android.content.Context;
import android.net.Uri;

import com.ak.remotecontroller.ITimerActionsListener;

public interface ITimer extends ICountDownListener{
	public void startTimer(long time);
	public void stopTimer();
	public void pauseTimer();
	public void resumeTimer();
	public boolean isTimerRunning();
	public boolean isRestTimerRunning();
	public void setRestTimer(long millis);
	public long getTime();
	public void registerListener(ITimerActionsListener listener);
	public void setMediaSource(Context context,Uri resId);

}
