package org.usfirst.frc.team3131.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class GrabMechanism {
	
	private DoubleSolenoid solenoid  = new DoubleSolenoid (0, 1); //0 forward, 1 reverse
		
	public void release(){
		solenoid.set(DoubleSolenoid.Value.kForward);
	}
	public void grab(){
		solenoid.set(DoubleSolenoid.Value.kReverse);
	}
	public void stop(){
		solenoid.set(DoubleSolenoid.Value.kOff);
	}
}
