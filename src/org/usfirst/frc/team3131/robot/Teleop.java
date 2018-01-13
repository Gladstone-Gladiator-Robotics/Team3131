package org.usfirst.frc.team3131.robot;


import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Teleop {
	public Teleop(DifferentialDrive myRobot2){
		this.myRobot = myRobot2;
	}
	
	private DifferentialDrive myRobot;
	private Joystick stick = new Joystick(0);
	private double highSpeed = 1;
	private double lowSpeed = 0.7;
	private boolean useHighSpeed = true;
	private DigitalInput bottomLimitSwitch = new DigitalInput(3);

	
	private static double deadband (double joystick, int power) {
		if (joystick < 0 && power % 2 == 0) {
			return -Math.pow(joystick, power);
		}
		else {
			return Math.pow(joystick, power);
		}
	}

	private void speedDrive() {
		int expo = 2;
		if (useHighSpeed) {
			myRobot.arcadeDrive(-highSpeed * deadband(stick.getRawAxis(1), expo), -highSpeed * deadband(stick.getRawAxis(4), expo));
			if (stick.getRawButton(4)) {
				useHighSpeed = false;
			}
		}
		else {
			myRobot.arcadeDrive(-lowSpeed * deadband(stick.getRawAxis(1), expo), -lowSpeed * deadband(stick.getRawAxis(4), expo));
			if (stick.getRawButton(3)) {
				useHighSpeed = true;
			}
		}
	}
	
	public void teleopPeriodic() {
		speedDrive();
		SmartDashboard.putBoolean("Bottom Limit Switch", bottomLimitSwitch.get());
	}

}