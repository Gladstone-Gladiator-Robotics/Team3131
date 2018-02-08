package org.usfirst.frc.team3131.robot;

import java.io.Console;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class ForwardDistance implements AutoCommand{
	ForwardDistance(DifferentialDrive myRobot, Encoder encRight, double distanceInInches, ADXRS450_Gyro gyro, double Kp ) {
		this.myRobot = myRobot;
		this.encRight = encRight;
		this.distance = distanceInInches;
		this.gyro = gyro;
		this.Kp = Kp;
	}

	DifferentialDrive  myRobot;
	Encoder encRight;
	ADXRS450_Gyro gyro;
	boolean isFinished;
	boolean isInitialized;
	double distance;
	double curveCorrect;
	double Kp;
	

	private void init() {
		encRight.reset();
		gyro.reset();
	}
	
	public void periodic() {
		if (!isInitialized){
			init();
		isInitialized = true;
		}
		double angle = gyro.getAngle() * Kp;
		System.out.println("forwardjava raw angle = " + angle);
		if (angle >= .2){
			angle = .2;
		}
		else if (angle <= -.2){
			angle = -.2;
		}
		myRobot.arcadeDrive(.65, -angle);
		System.out.println("forwardjava angle = " + angle);
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