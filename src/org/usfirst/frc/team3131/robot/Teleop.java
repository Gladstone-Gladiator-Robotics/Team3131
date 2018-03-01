package org.usfirst.frc.team3131.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class Teleop {
	public Teleop(DifferentialDrive myRobot2){
		this.myRobot = myRobot2;
	}
	
	private DifferentialDrive myRobot;
	private XboxController controller = new XboxController(new Joystick(0));
	private double highSpeed = 1;
	private double lowSpeed = 0.7;
	private boolean useHighSpeed = false;
	private LiftMechanism lift = new LiftMechanism(controller, new Talon (5), new Talon (4));
	private GrabMechanism grabber = new GrabMechanism();
	private Talon climbMotor = new Talon(7);
	private DoubleSolenoid solenoid  = new DoubleSolenoid (0, 1);
	private boolean buttonDeButton = false;
	private double delayThing = 0;
	
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
		
		if (isClimberActivated()) {
			myRobot.arcadeDrive(0, 0);
			return;
		}
		
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
				speedMultiplier * deadband(controller.leftJoystickY(), expo), 
				-speedMultiplier * deadband(controller.rightJoystickX(), expo));

	}
	
	private void grabMechanism() {
		if (controller.aButton() && controller.bButton()){
			grabber.stop();
		}
		else if (controller.aButton()) {
			grabber.release();
		}
		else if (controller.bButton()) {
			grabber.grab();
		}
		else {
			grabber.stop();
		}
	}
	
	private boolean isClimberActivated() {
		return controller.rightStickButton();
	}
	
	private void climbMechanism() {
		if (isClimberActivated()){
			climbMotor.set(controller.rightJoystickY());
		}
		else {
			climbMotor.set(0);
		}
	}
	private void grabPiston() {
		/*if (controller.aButton()){buttonDeButton = true;}
		if (buttonDeButton){
			delayThing = 0;
			buttonDeButton = false;
			if (delayThing >10) {
				delayThing++;
			}
			else {
				buttonDeButton = false;
			}
		}*/
		if (controller.aButton() && controller.bButton()) {
			solenoid.set(DoubleSolenoid.Value.kOff);
		}
		else if (controller.aButton()) {
			delayThing++;
			if (delayThing >= 10) {
				solenoid.set(DoubleSolenoid.Value.kForward);
			}
		}
		else if (controller.bButton()) {
			solenoid.set(DoubleSolenoid.Value.kReverse);
		}
		else {
			delayThing = 0;
			solenoid.set(DoubleSolenoid.Value.kOff);
		}
	}
	
	public void teleopPeriodic() {
		speedDrive();
		grabMechanism();
		lift.liftMechanism();
		climbMechanism();
		grabPiston();
	}

}