package org.usfirst.frc.team3131.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class Teleop {
	public Teleop(DifferentialDrive myRobot2){
		this.myRobot = myRobot2;
	}
	
	private DifferentialDrive myRobot;
	private XboxController controller = new XboxController(new Joystick(0));
	private double highSpeed = 0.77;
	private double lowSpeed = 0.6;
	private boolean useHighSpeed = true;
	private Talon cannonAimMotor = new Talon(3);
//	private LiftMechanism lift = new LiftMechanism(controller);
//	private GrabMechanism grabber = GrabMechanism.getInstance();
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
			if (controller.dPadDown()) {
				useHighSpeed = false;
			}
		}
		else {
			speedMultiplier = lowSpeed;
			if (controller.dPadUp()) {
				useHighSpeed = true;
			}
		}
		myRobot.arcadeDrive(
				-speedMultiplier * deadband(controller.leftJoystickY(), expo),  
				-speedMultiplier * deadband(controller.rightJoystickX(), expo));
		// Left joystick should go forward and backwards, right joystick to turn left and right
	}
	
	private void airCannons() {
		solenoid1.set(controller.bButton()); //Buttons correspond to the cannons, left to right
		solenoid2.set(controller.yButton());
		solenoid3.set(controller.xButton());
	}
	
	private void cannonAim() {
		cannonAimMotor.set((controller.rightTrigger() - controller.leftTrigger())/2);
	}

	
	public void teleopPeriodic() {
		speedDrive();
		airCannons();
		cannonAim();
	}
}