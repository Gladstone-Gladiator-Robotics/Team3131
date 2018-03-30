package org.usfirst.frc.team3131.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Talon;

public class LiftMechanism {

	public LiftMechanism(XboxController controller){
		this.controller = controller;
	}

	private XboxController controller;
	private static Talon liftMotor = new Talon (4);
	private static DigitalInput topLimitSwitch = new DigitalInput(6);
	private static DigitalInput bottomLimitSwitch = new DigitalInput(5);
	private double liftMotorSpeed;
	private boolean backButtonPressed;
	private boolean startButtonPressed;
	private boolean previousBackButtonPressed;
	private boolean previousStartButtonPressed;
	
	public static void autoLift() {
		if (isAtTopLimit()) {
			liftMotor.set(0);
		} else {
			liftMotor.set(-1);
		}
	}
	
	public static boolean isAtTopLimit() {
		return topLimitSwitch.get();
	}
	
	private void liftMotor(){
		double autoLift = 1;
		double manualLift = (controller.leftTrigger() - controller.rightTrigger());
		boolean isAtBottomLimit = bottomLimitSwitch.get();
		
		if (controller.backButton() && !previousBackButtonPressed) {
			backButtonPressed = !backButtonPressed;  //"boolean = !boolean" toggles it between true and false, neat!
			startButtonPressed = false;
		}
		if (controller.startButton() && !previousStartButtonPressed) {
			startButtonPressed = !startButtonPressed; 
			backButtonPressed = false;
		}
		previousBackButtonPressed = controller.backButton();
		previousStartButtonPressed = controller.startButton();
		
		
		if (backButtonPressed) {
			liftMotorSpeed = autoLift;
		}
		else if (startButtonPressed) {
			liftMotorSpeed = -autoLift;
		}
		else {
			liftMotorSpeed = manualLift;
		}
		
		
 		if (isAtBottomLimit) {
 			backButtonPressed = false;
			if (liftMotorSpeed > 0){
				liftMotor.set(0);
				return;
			}
		}
		
		if (isAtTopLimit()){
			startButtonPressed = false;
			if (liftMotorSpeed < 0){
				liftMotor.set(0);
				return;
			}
		}
		liftMotor.set(liftMotorSpeed);
	}
		
	private void rumble(){
		if ((controller.rightTrigger() > 0 && controller.leftTrigger() > 0) || (controller.rightBumper() && controller.leftBumper())){
			controller.rumble();
		}
		else {
			controller.stopRumble();
		}
	}
	
	public void liftMechanism() {
		rumble();
		liftMotor();
	}	
}
