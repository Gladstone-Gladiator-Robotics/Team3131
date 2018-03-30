package org.usfirst.frc.team3131.robot;

public class AutonomousLift implements AutoCommand {

	public void periodic() {
		LiftMechanism.autoLift();
	}
	public boolean isFinished() {
		return LiftMechanism.isAtTopLimit();
	}
}
