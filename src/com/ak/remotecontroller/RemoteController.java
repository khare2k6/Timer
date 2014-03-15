package com.ak.remotecontroller;

import android.content.Context;
import android.net.Uri;

import com.ak.timer.CDTimer;
import com.ak.timer.ITimer;

public class RemoteController implements IController{

	private ITimer mTimer;
	public RemoteController(){
		mTimer = new CDTimer();
	}

	@Override
	public void startTimer(long time) {
		mTimer.startTimer(time);
	}

	@Override
	public void stopTimer() {
		mTimer.stopTimer();
	}

	@Override
	public void pauseTimer() {
		mTimer.pauseTimer();
	
	}

	@Override
	public void resumeTimer() {
		mTimer.resumeTimer();	
	}

	@Override
	public void setMediaSource(Context context, Uri resId) {
		mTimer.setMediaSource(context, resId);
	}

	@Override
	public void registerListener(ITimerActionsListener listener) {
		mTimer.registerListener(listener);
	}

	@Override
	public boolean isTimerRunning() {
		return mTimer.isTimerRunning();
	}

	@Override
	public long getTime() {
		return mTimer.getTime();
	}

	@Override
	public boolean isRestTimerRunning() {
		return mTimer.isRestTimerRunning();
	}

	@Override
	public void setRestTimer(long millis) {
		 mTimer.setRestTimer(millis);	
	}
	


}
