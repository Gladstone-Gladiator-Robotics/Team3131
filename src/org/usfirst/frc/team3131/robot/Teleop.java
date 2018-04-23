package org.usfirst.frc.team3131.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class Teleop {
	public Teleop(DifferentialDrive myRobot2){
		this.myRobot = myRobot2;
	}
	
	private DifferentialDrive myRobot;
	private XboxController controller = new XboxController(new Joystick(0));
	private double highSpeed = 0.77;
	private double lowSpeed = 0.5;
	private boolean useHighSpeed = true;
	private LiftMechanism lift = new LiftMechanism(controller);
	private GrabMechanism grabber = GrabMechanism.getInstance();
	//private Talon climbMotor = new Talon(7);
	
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
		
		/*if (isClimberActivated()) {
			myRobot.arcadeDrive(0, 0);
			return;
		}*/
		
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
				speedMultiplier * deadband(controller.rightJoystickX(), expo));

	}
	
	/*private boolean isClimberActivated() {
		return controller.rightStickButton();
	}
	
	private void climbMechanism() {
		if (isClimberActivated()){
			climbMotor.set(controller.rightJoystickY());
		}
		else {
			climbMotor.set(0);
		}
	}*/
	private void grabPiston() {
		if (controller.rightBumper() && controller.leftBumper()) {
			grabber.stop();
		}
		else if (controller.leftBumper()) {
			grabber.release();
		}
		else if (controller.rightBumper()) {
			grabber.grab();
		}
		else {
			grabber.stop();
		}
	}
	
	public void teleopPeriodic() {
		speedDrive();
		grabPiston();
		lift.liftMechanism();
		//climbMechanism();
		grabPiston();
	}

}