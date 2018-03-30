package org.usfirst.frc.team3131.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class GrabMechanism {
	
	private static GrabMechanism instance;
	public static synchronized GrabMechanism getInstance() {
		if (instance == null) {
			instance = new GrabMechanism();
		}
		return instance;
	}

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
