package org.usfirst.frc.team3131.robot;

import edu.wpi.first.wpilibj.Timer;

public class AutoDelay implements AutoCommand{
	AutoDelay(int delaySeconds){
		this.delaySeconds = delaySeconds;
	}
	
	double delaySeconds;
	private boolean didDelay;
	
	public void periodic() {
		Timer.delay(11);
		didDelay = true;
	}
	public boolean isFinished() {
		return didDelay;
	}

}
