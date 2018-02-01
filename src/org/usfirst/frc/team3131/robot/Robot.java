package org.usfirst.frc.team3131.robot;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */

public class Robot extends IterativeRobot {
	private DifferentialDrive myRobot;
	private Teleop teleop;
	private Encoder encRight= new Encoder(2, 3, true, Encoder.EncodingType.k4X);
	private Encoder encLeft= new Encoder(0, 1, false, Encoder.EncodingType.k4X);
	private AutoCommand[] commands;
	private SendableChooser<Integer> autoChooser;
	private Preferences prefs;
	private SendableChooser<Integer> encoderChooser;
	//PowerDistributionPanel pdp = new PowerDistributionPanel();
	private ADXRS450_Gyro gyro = new ADXRS450_Gyro();
	String gameData;
	
	
	private double forwardTimeMS;
	private double encoderDistanceInches;
	
	
	private AutoCommand[] getCommandsForAutoForward() {
		return new AutoCommand[] {
				new Forward(myRobot,(int)forwardTimeMS),
		};
	}
	
	private AutoCommand[] getCommandsForAutoEncoder() {
		return new AutoCommand[] {
			new ForwardDistance(myRobot, encRight, encLeft, encoderDistanceInches, gyro)
		};
	}
	
	private AutoCommand[] getCommandsForAutoStop() {
		return new AutoCommand[] {};
	}
		
	private double getDistancePerPulse() {
		double gear1 = 14;
		double gear2 = 50;
		double gear3 = 16;
		double gear4 = 48;
		double gearRatio = (gear1/gear2)*(gear3/gear4);
		int pulsePerMotorRev = 20;
		double radiusInInches = 3;
		double circumference = 2*Math.PI*radiusInInches;
		return gearRatio * circumference / pulsePerMotorRev;
	}
	
	private void sendEncoderDataToSmartDashboard() {
		SmartDashboard.putNumber("Left Encoder Distance", encLeft.getDistance()); 
		SmartDashboard.putNumber("Right Encoder Distance", encRight.getDistance());
		SmartDashboard.putNumber("Gyroscope Angle", gyro.getAngle());
		}
	
/*	private void fmsTest(){
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		SmartDashboard.putBoolean("Left Switch is ours", gameData.charAt(0) == 'L');		
	}

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		myRobot = new DifferentialDrive(new Talon(1), new Talon(2));
		myRobot.setDeadband(0);
		teleop = new Teleop(myRobot);
		autoChooser = new SendableChooser<Integer>();
		autoChooser.addDefault("Auto Forward", 0);
		autoChooser.addObject("Auto Encoder", 1);
		autoChooser.addObject("Auto Stop", 2);
		SmartDashboard.putData("Autonomous Chooser", autoChooser);
		encoderChooser = new SendableChooser<Integer>();
		encoderChooser.addDefault("Competition", 0);
		encoderChooser.addObject("Test", 1);
		SmartDashboard.putData("Encoder Chooser", encoderChooser);
		prefs = Preferences.getInstance();
		encRight.setDistancePerPulse(getDistancePerPulse());
		encLeft.setDistancePerPulse(getDistancePerPulse());
		gyro.calibrate();
		
		
		
/*		if (encoderChooser.getSelected() == 0) {
			// Use Encoder Objects
		}
		else if (encoderChooser.getSelected() == 1) {
			// Use DIO Objects for testing purposes
		}*/
	}

	public void autonomousInit() { 
		forwardTimeMS = prefs.getDouble("Forward Time in Milliseconds", 4000);
		encoderDistanceInches = prefs.getDouble("Encoder Distance in Inches", 100);
		commands = getAutoCommands();
		pausedTime = 0;
	}

	private AutoCommand[] getAutoCommands() {
 		switch (autoChooser.getSelected()) {
 		case 0:
			return getCommandsForAutoForward();
 		case 1:
			return getCommandsForAutoEncoder();
 		case 2:
 		default:
			return getCommandsForAutoStop();
 		}
	}
	
	
	int pausedTime = 0;
	
	
	public void autonomousPeriodic(){
		
		if ( pausedTime < 100) {
			pausedTime++;

			myRobot.arcadeDrive(0,0);
			return;
		}
		
		sendEncoderDataToSmartDashboard();
		for(int i=0; i<commands.length; ++i) {
			if (!commands[i].isFinished()) {
				commands[i].periodic();
				System.out.println("Running autonomous command " + i);
				return;
			}
		}
		myRobot.arcadeDrive(0,0);
	}

	public void teleopInit(){
		encRight.reset();
		encLeft.reset();
	    } 
	
	public void teleopPeriodic() {
		teleop.teleopPeriodic();
		sendEncoderDataToSmartDashboard();
		//fmsTest();
		//SmartDashboard.putNumber("Power Distribution Panel ?", pdp.getCurrent(0));
	}
    
    public void testPeriodic() {
    	
    }
}