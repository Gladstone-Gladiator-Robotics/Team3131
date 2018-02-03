package org.usfirst.frc.team3131.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Talon;

public class LiftMechanism {

	public LiftMechanism(XboxController controller, Talon stageOneMotor, Talon stageTwoMotor){
		this.controller = controller;
		this.stageOneMotor = stageOneMotor;
		this.stageTwoMotor = stageTwoMotor;
	}

	private XboxController controller;
	private Talon stageOneMotor;
	private Talon stageTwoMotor;
	/*
	private DigitalInput stageOneTopLimitSwitch = new DigitalInput(2);
	private DigitalInput stageOneBottomLimitSwitch = new DigitalInput(4);
	private DigitalInput stageTwoTopLimitSwitch = new DigitalInput(5);
	private DigitalInput stageTwoBottomLimitSwitch = new DigitalInput(6);
	
	private void stageOne(){
		
		if (controller.rightTrigger() > 0 && controller.leftTrigger() > 0){
			controller.rumble();
		}
		else {
			controller.stopRumble();
		}
		
		double up;
		double down;
		if  (stageOneTopLimitSwitch.get()){
			up = 0;
		}
		else {
			up = controller.rightTrigger();
		}
		if  (stageOneBottomLimitSwitch.get()){
			down = 0;
		}
		else {
			down = controller.leftTrigger();
		}
		
		stageOneMotor.set(up - down);
		
	}
	private void stageTwo(){
		
		if (controller.rightJoystickY() > 0 && stageTwoTopLimitSwitch.get()){
			stageTwoMotor.set(0);
		}
		else if (controller.rightJoystickY() < 0 && stageTwoBottomLimitSwitch.get()){
			stageTwoMotor.set(0);
		}
		else {
			stageTwoMotor.set(controller.rightJoystickY());		
		}
	}
	*/
	public void liftMechanism() {
		//stageOne();
		//stageTwo();
	}	
}
