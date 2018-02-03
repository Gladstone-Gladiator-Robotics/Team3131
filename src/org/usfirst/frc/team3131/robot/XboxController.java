package org.usfirst.frc.team3131.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;

public class XboxController {
	public XboxController(Joystick stick){
		this.stick = stick;
	}
	private Joystick stick;
	public boolean aButton(){
		return stick.getRawButton(1);
	}
	public boolean bButton(){
		return stick.getRawButton(2);
	}
	public boolean xButton(){
		return stick.getRawButton(3);
	}
	public boolean yButton(){
		return stick.getRawButton(4);
	}
	public boolean rightBumper(){
		return stick.getRawButton(5);
	}
	public boolean leftBumper(){
		return stick.getRawButton(6);
	}
	public double leftJoystickX(){
		return stick.getRawAxis(0);
	}
	public double leftJoystickY(){
		return stick.getRawAxis(1);
	}
	public double leftTrigger(){
		return stick.getRawAxis(2);
	}
	public double rightTrigger(){
		return stick.getRawAxis(3);
	}
	public double rightJoystickX(){
		return stick.getRawAxis(4);
	}
	public double rightJoystickY(){
		return stick.getRawAxis(5);
	}
	public void rumble(){
		int intensity = 1;
		stick.setRumble(RumbleType.kLeftRumble, intensity);
		stick.setRumble(RumbleType.kRightRumble, intensity);
	}
	public void stopRumble() {
		int intensity = 0;
		stick.setRumble(RumbleType.kLeftRumble, intensity);
		stick.setRumble(RumbleType.kRightRumble, intensity);
	}
}
