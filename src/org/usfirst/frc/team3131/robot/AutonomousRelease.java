package org.usfirst.frc.team3131.robot;

import edu.wpi.first.wpilibj.DriverStation;

public class AutonomousRelease implements AutoCommand {
	
	private boolean didRelease;
	String gameData = DriverStation.getInstance().getGameSpecificMessage();
	
	public void periodic() {
		GrabMechanism.getInstance().release();
		didRelease = true;
	}
	public boolean isFinished() {
		if(gameData.length () > 0){
			if(gameData.charAt(0) == 'L'){
				return didRelease;
			}
			else {
				return true;
			}
		}
		else {
			return didRelease;
		}
	}
}
