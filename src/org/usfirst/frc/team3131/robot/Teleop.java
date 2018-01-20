package org.usfirst.frc.team3131.robot;


import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.util.ArrayList;
import java.util.ArrayList;

public class Teleop {
	public Teleop(DifferentialDrive myRobot2){
		this.myRobot = myRobot2;
	}
	
	private DifferentialDrive myRobot;
	private Joystick stick = new Joystick(0);
	private double highSpeed = 1;
	private double lowSpeed = 0.7;
	private boolean useHighSpeed = true;
	AnalogInput infraRedRangefinder = new AnalogInput(0);
	AnalogInput ultraSonicRangefinder = new AnalogInput(3);
	ArrayList<Integer> previousUltrasonicValues = new ArrayList<Integer>();
	
	
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
	
	private void infraRedRangeFinder() {
		int rangeFinderValue = infraRedRangefinder.getValue();
		SmartDashboard.putNumber("raw range finder value", rangeFinderValue);
		if(rangeFinderValue <= 20) {
			SmartDashboard.putNumber("Range Finder", 0);
		}
		else {
			SmartDashboard.putNumber("Range Finder", (4800/(rangeFinderValue - 20)));
		}
	}
	
	
	private double getAverageForUltrasonicRange(int sampleSize) {
		int total = 0;
		int count = 0;
		
		for (int i = Math.max(previousUltrasonicValues.size() - sampleSize, 0); i < previousUltrasonicValues.size(); ++i) {
			total = total + previousUltrasonicValues.get(i);
			count = count + 1;
		}
		return total / count;
	}
	
	
	private void ultraSonicAnalog() {
		
		previousUltrasonicValues.add(ultraSonicRangefinder.getValue());
		
		if (previousUltrasonicValues.size() > 200) {
			previousUltrasonicValues.remove(0);
		}

		SmartDashboard.putNumber("Ultrasonic averaged value (200)", getAverageForUltrasonicRange(200));
		SmartDashboard.putNumber("Ultrasonic averaged value (150)", getAverageForUltrasonicRange(150));
		SmartDashboard.putNumber("Ultrasonic averaged value (100)", getAverageForUltrasonicRange(100));
		SmartDashboard.putNumber("Ultrasonic averaged value (75)", getAverageForUltrasonicRange(75));
		SmartDashboard.putNumber("Ultrasonic averaged value (50)", getAverageForUltrasonicRange(50));
		SmartDashboard.putNumber("Ultrasonic averaged value (25)", getAverageForUltrasonicRange(25));
		SmartDashboard.putNumber("Ultrasonic averaged value (15)", getAverageForUltrasonicRange(15));
		SmartDashboard.putNumber("Ultrasonic averaged value (10)", getAverageForUltrasonicRange(10));
		SmartDashboard.putNumber("Ultrasonic raw voltage", ultraSonicRangefinder.getVoltage());
		SmartDashboard.putNumber("Ultrasonic raw value", ultraSonicRangefinder.getValue());
		SmartDashboard.putNumber("Ultrasonic in mm", ultraSonicRangefinder.getVoltage() * 5);
	}
	
	public void teleopPeriodic() {
		speedDrive();
		infraRedRangeFinder();
		//ultraSonicAnalog();
	}

}