package org.usfirst.frc.team3131.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Talon;

public class LiftMechanism {

	public LiftMechanism(XboxController controller, Talon stageOneMotor, Talon stageTwoMotor){
		this.controller = controller;
		this.bigMotor = stageOneMotor;
		this.smallMotor = stageTwoMotor;
	}

	private XboxController controller;
	private Talon smallMotor;
	private Talon bigMotor;
	private DigitalInput bigMotorTopLimitSwitch = new DigitalInput(6);
	private DigitalInput bigMotorBottomLimitSwitch = new DigitalInput(5);
	public boolean backButtonPressed;
	public boolean startButtonPressed;
	public double downMultiplier = .25; //To swap between Drive and Climb mode for the big motor, where it is set to .5(half speed) is drive mode and 1(full speed) is climb mode
	
	private void smallMotor(){ // Call this "top lift motor" or "grab mechanism lift" around electrical
		double smallMotorSpeed = 100;
		
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
	
	private void bigMotor(){ // Call this "bottom and middle lift motors" or "arm lift" around electrical
		boolean isAtTopLimit = bigMotorTopLimitSwitch.get();
		boolean isAtBottomLimit = !bigMotorBottomLimitSwitch.get();
		double manualUpDown = ((controller.rightTrigger() * .6) - (controller.leftTrigger() * downMultiplier)); //Right trigger goes up, left goes down
		double autoUp = .7;
		double autoDown = -.7;
		double bigMotorDrive;

/*		if (controller.backButton()){backButtonPressed = true;}
		if (controller.startButton()){startButtonPressed = true;}*/
		
/*		if (backButtonPressed){
			bigMotorDrive = autoUp;
		}
		else if (startButtonPressed){
			bigMotorDrive = autoDown;
		}
		else {
			bigMotorDrive = manualUpDown;
		}*/
		
		if (controller.dPadUp()){
			downMultiplier = .75; //Press UP on Dpad to enable climb mode, this will allow the big motor to move FULL SPEED only when going down
		}
		if (controller.dPadDown()){
			downMultiplier = .55; //Press DOWN on Dpad to enable regular mode, this will allow the big motor to move half speed going down
		}
	
		if (isAtBottomLimit) {
//			startButtonPressed = false;
			if (manualUpDown < 0){
				bigMotor.set(0);
				return;
			}
		}
		
		if (isAtTopLimit){
//			backButtonPressed = false;
			if (manualUpDown > 0){
				bigMotor.set(0);
				return;
			}
		}
		bigMotor.set(manualUpDown);
	}
	
	private void rumble(){
		if ((controller.rightTrigger() > 0 && controller.leftTrigger() > 0) || (controller.rightBumper() && controller.leftBumper())){
			controller.rumble();
//			backButtonPressed = false;
		}
		else {
			controller.stopRumble();
//			backButtonPressed = false;
		}
	}
	
	public void liftMechanism() {
		rumble();
		smallMotor();
		bigMotor();
	}	
}
