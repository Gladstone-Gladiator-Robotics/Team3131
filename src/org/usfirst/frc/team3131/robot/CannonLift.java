package org.usfirst.frc.team3131.robot;

import edu.wpi.first.wpilibj.Talon;

public class CannonLift {

	public CannonLift(Talon cLiftMotor) {
		this.cLiftMotor = cLiftMotor;
	}
	private Talon cLiftMotor;
	public double cLiftSpeed;
	
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
// I think this is totally useless actually :(