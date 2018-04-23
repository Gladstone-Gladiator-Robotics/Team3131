package org.usfirst.frc.team3131.robot;


import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class Forward extends TimedAutoCommand{
	Forward(DifferentialDrive myRobot2, int milliseconds) {
		super(milliseconds);
		this.myRobot = myRobot2;
	}
	
	DifferentialDrive myRobot; 
	private Ramp ramp = new Ramp(0, 0, 0.2);
	
	void init() {
		ramp.reset();
	}
	
	public void periodicStuff() {
		ramp.set(0.7);
		myRobot.arcadeDrive(ramp.get(), 0);
	}
}