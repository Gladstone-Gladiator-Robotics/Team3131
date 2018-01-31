package org.usfirst.frc.team3131.robot;

import java.io.Console;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class ForwardDistance implements AutoCommand{
	ForwardDistance(DifferentialDrive myRobot, Encoder encRight, Encoder encLeft, double distanceInInches, ADXRS450_Gyro gyro) {
		this.myRobot = myRobot;
		this.encRight = encRight;
		this.encLeft = encLeft;
		this.distance = distanceInInches;
		this.gyro = gyro;
	}

	DifferentialDrive  myRobot;
	Encoder encRight;
	Encoder encLeft;
	ADXRS450_Gyro gyro;
	boolean isFinished;
	boolean isInitialized;
	double distance;
	double curveCorrect;
	double Kp = 0.25;

	private void init() {
		encRight.reset();
		encLeft.reset();
		gyro.reset();
	}
	
	public void periodic() {
		System.out.println("forwardjava periodic");
		if (!isInitialized){
			init();
		isInitialized = true;
		}
		double angle = gyro.getAngle();
		myRobot.arcadeDrive(.75, -angle*Kp);
		
		/*double correctionFactorWeight = Preferences.getInstance().getDouble("Correction Factor Weight", 1);
		double correctionFactor = (1-(encRight.getDistance()/encLeft.getDistance())) * correctionFactorWeight;
		myRobot.arcadeDrive(0.55,correctionFactor);
		System.out.println("forwardjava correctionFactor = " + correctionFactor);*/
	}

	public boolean isFinished() {
		
		if (!isInitialized){
			return false;
		}
		if (isFinished) {
			return true;
		}
		if (0 > distance) {
			isFinished = (encRight.getDistance() < distance);
		}
		else {
			isFinished = (encRight.getDistance() > distance);
		}
		return isFinished;
	}
}