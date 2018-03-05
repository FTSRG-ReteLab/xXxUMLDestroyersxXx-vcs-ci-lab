package hu.bme.mit.train.controller;

import com.google.common.graph.Graph;
import java.util.Timer;
import java.util.TimerTask;
import hu.bme.mit.train.interfaces.TrainController;
import com.google.*;


public class TrainControllerImpl implements TrainController {

	private int step = 0;
	private int referenceSpeed = 0;
	private int speedLimit = 0;
	private Tachograph tac;
	Timer t= new Timer();
	
	TimerTask tt=new TimerTask(){
		public void run(){
			followSpeed();
		}
	};

	@Override
	public void followSpeed() {
		
		if (referenceSpeed < 0) {
			referenceSpeed = 0;
		} else {
		    if(referenceSpeed+step > 0) {
                referenceSpeed += step;
            } else {
		        referenceSpeed = 0;
            }
		}

		enforceSpeedLimit();
	}
	
	public void startTrain(){
		t.scheduleAtFixedRate(tt, 0, 1000);
	}

	@Override
	public int getReferenceSpeed() {
		return referenceSpeed;
	}

	@Override
	public void setSpeedLimit(int speedLimit) {
		this.speedLimit = speedLimit;
		enforceSpeedLimit();
		
	}

	private void enforceSpeedLimit() {
		if (referenceSpeed > speedLimit) {
			referenceSpeed = speedLimit;
		}
	}

	public void emergencyBrake() {
		this.referenceSpeed = 0;
		this.step = 0;
		}

	@Override
	public void setJoystickPosition(int joystickPosition) {
		this.step = joystickPosition;
	}

}
