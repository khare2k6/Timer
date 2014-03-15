package com.ak.remotecontroller;

import android.content.Context;
import android.net.Uri;

public interface IController {

	//public void setCountDownTimerInstance(ITimer timer);
	public void startTimer(long time);
	public void stopTimer();
	public void pauseTimer();
	public void resumeTimer();
	public boolean isTimerRunning();
	public boolean isRestTimerRunning();
	public long getTime();
	public void setMediaSource(Context context,Uri resId);
	public void registerListener(ITimerActionsListener listener);
	public void setRestTimer(long millis);
}
