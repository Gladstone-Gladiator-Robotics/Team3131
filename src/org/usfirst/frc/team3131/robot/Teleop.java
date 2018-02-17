package org.usfirst.frc.team3131.robot;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.util.ArrayList;

public class Teleop {
	public Teleop(DifferentialDrive myRobot2){
		this.myRobot = myRobot2;
	}
	
	private DifferentialDrive myRobot;
	private XboxController controller = new XboxController(new Joystick(0));
	private double highSpeed = 1;
	private double lowSpeed = 0.7;
	private boolean useHighSpeed = true;
	AnalogInput infraRedRangefinder = new AnalogInput(0);
	private LiftMechanism lift = new LiftMechanism(controller, new Talon (5), new Talon (4));
	private GrabMechanism grabber = new GrabMechanism();
	
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
		double speedMultiplier;
		if (useHighSpeed) {
			speedMultiplier = highSpeed;
			if (controller.yButton()) {
				useHighSpeed = false;
			}
		}
		else {
			speedMultiplier = lowSpeed;
			if (controller.xButton()) {
				useHighSpeed = true;
			}
		}
		myRobot.arcadeDrive(
				-speedMultiplier * deadband(controller.leftJoystickY(), expo), 
				-speedMultiplier * deadband(controller.rightJoystickX(), expo));

	}
	
/*	private void infraRedRangeFinder() { -- Not currently on any bot :/
		int rangeFinderValue = infraRedRangefinder.getValue();
		SmartDashboard.putNumber("Raw Range Finder Value", rangeFinderValue);
		if(rangeFinderValue <= 20) {
			SmartDashboard.putNumber("Range Finder", 0);
		}
		else {
			SmartDashboard.putNumber("Range Finder", (4800/(rangeFinderValue - 20)));
		}
	}*/
	
	private void grabMechanism() {
		//int rangeFinderValue = infraRedRangefinder.getValue();   - Move this to GrabMechanism class if you want to get this working
		if (controller.aButton() && controller.bButton()){
			grabber.stop();
		}
		else if (controller.aButton()) {
			grabber.release();
		}
		else if (controller.bButton()) {
			grabber.grab();
		}
		/*else if ((4800/(rangeFinderValue - 20) >= 3)){
			//grabMotor.set(0);
		}*/
		else {
			grabber.stop();
		}
	}
	
	
	public void teleopPeriodic() {
		speedDrive();
		grabMechanism();
//		infraRedRangeFinder();
		lift.liftMechanism();
	}

}