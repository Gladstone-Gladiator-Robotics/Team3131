package org.usfirst.frc.team3131.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Talon;

public class LiftMechanism {

	public LiftMechanism(XboxController controller, Talon liftMotor){
		this.controller = controller;
		this.liftMotor = liftMotor;
	}

	private XboxController controller;
	private Talon liftMotor;
	private DigitalInput topLimitSwitch = new DigitalInput(6);
	private DigitalInput bottomLimitSwitch = new DigitalInput(5);
	//public boolean backButtonPressed;
	//public boolean startButtonPressed;
	
	private void liftMotor(){ 
		double liftMotorSpeed = (controller.rightTrigger() - controller.leftTrigger());
		boolean isAtTopLimit = topLimitSwitch.get();
		boolean isAtBottomLimit = bottomLimitSwitch.get();
		
 		if (isAtBottomLimit) {
			if (liftMotorSpeed < 0){
				liftMotor.set(0);
				return;
			}
		}
		
		if (isAtTopLimit){
			if (liftMotorSpeed > 0){
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
