package com.ak.service;


import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.ak.reikitimer.R;
import com.ak.remotecontroller.IController;
import com.ak.remotecontroller.ITimerActionsListener;
import com.ak.remotecontroller.RemoteController;
import com.ak.timer.CDTimer.State;
import com.ak.ui.MainActivity;

public class ControllerService extends Service implements ITimerActionsListener{
	private IController mController;
	private final String TAG = ControllerService.class.getSimpleName();
	private final int ID = 1;
	Notification foregroundNotification;
	private Uri DEGAULT_BELL_SOUND = Uri.parse("android.resource://com.ak.reikitimer/" + R.raw.bell);
	private Uri DEGAULT_REST_TIMER_SOUND = Uri.parse("android.resource://com.ak.reikitimer/" + R.raw.startsound);
	private boolean mPlayRestMedia = false;
	private SharedPreferences mPreference;
	public static final String SHARED_PREF = "timerAppPref";
	public static final String MEDIA_URI_KEY = "getMediaUri";
	public static final String REST_PERIOD_KEY = "restTime";
	public static final int DEFUALT_REST_TIMER_PERIOD = 3;
	
	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG,"onCreate");
		mController = new RemoteController();	
		foregroundNotification = new Notification.Builder(ControllerService.this)
		.setContentTitle("Timer Running")
		.setContentText("Repeatative timer is running")
		.setSmallIcon(R.drawable.ic_clock2)
		.setContentIntent(PendingIntent.getActivity(ControllerService.this, ID, 
				new Intent(ControllerService.this,MainActivity.class), Intent.FLAG_ACTIVITY_NEW_TASK))
		.build();
		startForeground(ID,foregroundNotification);
		mController.registerListener(this);
		mPreference = getSharedPreferences(SHARED_PREF, 0);
		//Initial setting media URI
		mController.setMediaSource(this, getMediaUri());
		mController.setRestTimer(mPreference.getInt(REST_PERIOD_KEY ,DEFUALT_REST_TIMER_PERIOD)*1000);
	}


	@Override
	public IBinder onBind(Intent arg0) {
		Log.d(TAG,"onBind");
		return new LocalBinder();
	}

	public void setMediaSource(Context context,Uri resId){
		mController.setMediaSource(context, resId);
	}
	
	public boolean isTimerRunning(){
		return mController.isTimerRunning();
	}
	
	public boolean isRestTimerRunning(){
		return mController.isRestTimerRunning();
	}
	public long getTime(){
		return mController.getTime();
	}
	
	public void setRestTimer(){
		mController.setRestTimer(mPreference.getInt(REST_PERIOD_KEY ,DEFUALT_REST_TIMER_PERIOD)*1000);
		
	}
	public void startTimer(long milli){
		mController.startTimer(milli);
	}
	public void stopTimer(){
		mController.stopTimer();
	}
	
	public void pauseTimer(){
		mController.pauseTimer();
	}
	
	public void resumeTimer(){
		mController.resumeTimer();
	}
	
	public void registerListener(ITimerActionsListener listener){
		mController.registerListener(listener);
	}
	public void setMediaSourceChanged(){
		mController.setMediaSource(this, getMediaUri());
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(TAG,"onDestroy");
		stopForeground(true);
	}

	public class LocalBinder extends Binder{
		public ControllerService getControllerService(){
			return ControllerService.this;
		}
	}

	@Override
	public void onTimeTick(long milliSecUntilFinished) {
		//do nothing
	}


	@Override
	public void onTimerFinish() {		
		//do nothing
	}
	/**
	 * Get URI of the media to be played as alarm
	 * on timer expiry
	 * @return
	 */
	private Uri getMediaUri(){
		if(mPlayRestMedia){
			return DEGAULT_REST_TIMER_SOUND;
		}
		String uri = mPreference.getString(MEDIA_URI_KEY, null);
		if(uri != null){
			return Uri.parse(uri);
		}
		return DEGAULT_BELL_SOUND;
	}

	@Override
	public void onStateChange(State newState) {
		Log.d(TAG,"newState = "+newState.toString());
		switch(newState){
		case REST_PERIOD_STATE:
			Log.d(TAG,"setting to rest media");
			mPlayRestMedia = true;
			mController.setMediaSource(this, getMediaUri());
			break;
		case STARTED:
			if(mPlayRestMedia){
				mPlayRestMedia = false;
				Log.d(TAG,"resetting to bell sound");
				mController.setMediaSource(this, getMediaUri());
			}
			break;
		default:
			break;
		}
	}
}
