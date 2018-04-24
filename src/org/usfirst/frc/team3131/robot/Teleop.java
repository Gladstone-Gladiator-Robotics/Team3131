package org.usfirst.frc.team3131.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
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
//	private LiftMechanism lift = new LiftMechanism(controller);
//	private GrabMechanism grabber = GrabMechanism.getInstance();
	private CannonLift cLift = new CannonLift();
	private Solenoid solenoid1 = new Solenoid(0);
	private Solenoid solenoid2 = new Solenoid(1);
	private Solenoid solenoid3 = new Solenoid(2);
	
	private static double deadband (double joystick, int power) {
		if (joystick < 0 && power % 2 == 0) {
			return -Math.pow(joystick, power);
		}
		else {
			return Math.pow(joystick, power);
		}
	}

	private void speedDrive() {
		int expo = 2; //The deadband is essentially a parabola, this sets the exponent for the curve
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
				speedMultiplier * deadband(controller.rightJoystickX(), expo));

	}
	
	private void airCannons() {
		solenoid1.set(controller.xButton());
		solenoid2.set(controller.yButton());
		solenoid3.set(controller.bButton());
	}
	
	private void cannonLift() {
		if (controller.dPadUp()) {
			cLift.up();
		}
		else if(controller.dPadDown()) {
			cLift.down();
		}
		else {
			cLift.stop();
		}
	}
	
	public void teleopPeriodic() {
		speedDrive();
		airCannons();
		cannonLift();
	}
}