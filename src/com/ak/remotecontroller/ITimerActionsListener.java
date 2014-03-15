package com.ak.remotecontroller;

import com.ak.timer.CDTimer.State;
import com.ak.timer.ICountDownListener;

public interface ITimerActionsListener extends ICountDownListener{

	public void onStateChange(State newState);
}
