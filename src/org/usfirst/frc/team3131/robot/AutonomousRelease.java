package org.usfirst.frc.team3131.robot;

public class AutonomousRelease implements AutoCommand {
	
	private boolean didRelease;
	
	public void periodic() {
		GrabMechanism.getInstance().release();
		didRelease = true;
	}
	public boolean isFinished() {
		return didRelease;
	}
}
