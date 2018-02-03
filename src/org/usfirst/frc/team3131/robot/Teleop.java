package org.usfirst.frc.team3131.robot;

/* Controller Mapping
 * A = 1, B = 2, X = 3, Y = 4
 * LB = 5, RB = 6, Back = 7, Start = 8
 * Left TS = 9, Right TS = 10
 * Right Trigger = 3, Left Trigger = 2
 * 
 */
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.util.ArrayList;
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
	private Talon grabMotor = new Talon (7);
	private Talon stageOneMotor = new Talon (5);
	private Talon stageTwoMotor = new Talon (4);
	
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
	
	private void infraRedRangeFinder() {
		int rangeFinderValue = infraRedRangefinder.getValue();
		SmartDashboard.putNumber("Raw Range Finder Value", rangeFinderValue);
		if(rangeFinderValue <= 20) {
			SmartDashboard.putNumber("Range Finder", 0);
		}
		else {
			SmartDashboard.putNumber("Range Finder", (4800/(rangeFinderValue - 20)));
		}
	}
	
	private void grabMechanism() {
		//int rangeFinderValue = infraRedRangefinder.getValue();
		if (controller.aButton() && controller.bButton()){
			grabMotor.set(0);
		}
		else if (controller.aButton()) {
			grabMotor.set(.4);
		}
		else if (controller.bButton()) {
			grabMotor.set(-.4);
		}
		/*else if ((4800/(rangeFinderValue - 20) >= 3)){
			//grabMotor.set(0);
		}*/
		else {
			grabMotor.set(0);
		}
	}
	
	private void liftMechanism() {
		if (controller.rightTrigger() > 0 && controller.leftTrigger() > 0){
			controller.rumble();
		}
		else {
			controller.stopRumble();
		}
		stageOneMotor.set(controller.rightTrigger() - controller.leftTrigger());
		stageTwoMotor.set(controller.rightJoystickY());
		
	}	
	
	
	public void teleopPeriodic() {
		speedDrive();
		grabMechanism();
		infraRedRangeFinder();
		liftMechanism();
	}

}