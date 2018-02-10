package org.usfirst.frc.team3131.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LiftMechanism {

	public LiftMechanism(XboxController controller, Talon stageOneMotor, Talon stageTwoMotor){
		this.controller = controller;
		this.smallMotor = stageOneMotor;
		this.bigMotor = stageTwoMotor;
	}

	private XboxController controller;
	private Talon smallMotor;
	private Talon bigMotor;
	
	private DigitalInput bigMotorTopLimitSwitch = new DigitalInput(5);
	private DigitalInput bigMotorBottomLimitSwitch = new DigitalInput(6);
	
	private void smallMotor(){
		double smallMotorSpeed = 1;
		
		if (controller.rightBumper()){
			smallMotor.set(smallMotorSpeed);
		}
		else if (controller.leftBumper()){
			smallMotor.set(-smallMotorSpeed);
		}
		else {
			smallMotor.set(0);
		}
	}
	
	private void bigMotor(){
		boolean isAtTopLimit = !bigMotorTopLimitSwitch.get();
		boolean isAtBottomLimit = bigMotorBottomLimitSwitch.get();
		double multiplier = -.4;
		double bigMotorSpeed = controller.rightTrigger() - controller.leftTrigger();
		
		if (isAtBottomLimit) {
			
			if (bigMotorSpeed > 0){
				bigMotor.set(0);
				return;
			}
		}
		
		if (isAtTopLimit){
			if (bigMotorSpeed < 0){
				bigMotor.set(0);
				return;
			}
		}
		
		bigMotor.set(bigMotorSpeed * multiplier);		
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
		smallMotor();
		bigMotor();
		
	}	
}
