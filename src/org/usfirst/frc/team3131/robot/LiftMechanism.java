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
	public boolean backButtonPressed;
	public boolean startButtonPressed;
	public double downMultiplier = .5;
	
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
	
	/*private enum BigMotorSpeed{
		manualBigMotorSpeed, autoUp, autoDown;
	}*/
	
	private void bigMotor(){
		boolean isAtTopLimit = !bigMotorTopLimitSwitch.get();
		boolean isAtBottomLimit = bigMotorBottomLimitSwitch.get();
		double multiplier = 1;
		double manualUpDown = ((controller.rightTrigger() * .5) - (controller.leftTrigger() * downMultiplier));
		double autoUp = .7;
		double autoDown = -.7;
		double bigMotorDrive;
		
		/*switch (bigMotorSpeed){
		case manualBigMotorSpeed: bigMotorDrive = manualBigMotorSpeed;
		case autoUp: bigMotorDrive = autoUp;
		case autoDown: bigMotorDrive = autoDown;
		default: bigMotorDrive = manualBigMotorSpeed;
		}*/
		
		if (backButtonPressed){
			bigMotorDrive = autoUp;
		}
		else if (startButtonPressed){
			bigMotorDrive = autoDown;
		}
		else {
			bigMotorDrive = manualUpDown;
			backButtonPressed = controller.backButton();
			startButtonPressed = controller.startButton();
		}
		
/*		if (controller.xButton()){		FIND DIFFERENT BUTTONS TO ALLOCATE THIS TO
			downMultiplier = 1; //Press X to enable climb mode, this will also enable slow speed for main drive
		}
		if (controller.yButton()){
			downMultiplier = .5; //Press Y to enable regular mode, this will also enable high speed for main drive
		}
*/		
		if (isAtBottomLimit) {
			startButtonPressed = false;
			if (manualUpDown > 0){
				bigMotor.set(0);
				return;
			}
		}
		
		if (isAtTopLimit){
			backButtonPressed = false;
			if (manualUpDown < 0){
				bigMotor.set(0);
				return;
			}
		}
		bigMotor.set(bigMotorDrive * multiplier);
	}
	
	private void rumble(){
		if ((controller.rightTrigger() > 0 && controller.leftTrigger() > 0) || (controller.rightBumper() && controller.leftBumper())){
			controller.rumble();
			backButtonPressed = false;
		}
		else {
			controller.stopRumble();
			backButtonPressed = false;
		}
	}
	
	public void liftMechanism() {
		rumble();
		smallMotor();
		bigMotor();
		
	}	
}
