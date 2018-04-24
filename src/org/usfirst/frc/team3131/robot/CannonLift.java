package org.usfirst.frc.team3131.robot;

import edu.wpi.first.wpilibj.Talon;

public class CannonLift {

	private Talon cLiftMotor = new Talon(3);
	
	public void up() {
		cLiftMotor.set(1);
	}
	public void down() {
		cLiftMotor.set(-1);
	}
	public void stop() {
		cLiftMotor.set(0);
	}
}
