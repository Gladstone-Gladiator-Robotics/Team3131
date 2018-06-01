package org.usfirst.frc.team3131.robot;

import edu.wpi.first.wpilibj.DriverStation;

public class AutonomousLift implements AutoCommand {

	String gameData = DriverStation.getInstance().getGameSpecificMessage();
	
	public void periodic() {
		LiftMechanism.autoLift();
	}
	public boolean isFinished() {
		if(gameData.length() > 0){
			if(gameData.charAt(0) == 'L'){
				return LiftMechanism.isAtTopLimit();
			}
			else {
				return true;
			}
		}
		else {
			return true;
		}
	}
}
