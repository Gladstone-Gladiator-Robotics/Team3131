package org.usfirst.frc.team3131.robot;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Talon;

public class GrabMechanism {
	
	private Talon grabMotor = new Talon(3);
	Preferences prefs = Preferences.getInstance();
	
	public void release(){
		double releaseSpeed = prefs.getDouble("Release speed", .7);
		grabMotor.set(releaseSpeed);
	}
	public void grab(){
		double grabSpeed = prefs.getDouble("Grab speed", -.3);
		grabMotor.set(grabSpeed);
	}
	public void stop(){
		grabMotor.set(0);
	}
}
