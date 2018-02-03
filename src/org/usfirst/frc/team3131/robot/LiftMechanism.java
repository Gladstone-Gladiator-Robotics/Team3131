package org.usfirst.frc.team3131.robot;

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
	
	
	public void liftMechanism() {
		if (controller.rightTrigger() > 0 && controller.leftTrigger() > 0){
			controller.rumble();
		}
		else {
			controller.stopRumble();
		}
		stageOneMotor.set(controller.rightTrigger() - controller.leftTrigger());
		stageTwoMotor.set(controller.rightJoystickY());
		
	}	
}
