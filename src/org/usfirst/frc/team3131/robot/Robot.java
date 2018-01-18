package org.usfirst.frc.team3131.robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Talon;
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
	private Encoder encRight= new Encoder(0, 1, false, Encoder.EncodingType.k4X);
	private Encoder encLeft= new Encoder(2, 3, false, Encoder.EncodingType.k4X);
	private AutoCommand[] commands;
	private SendableChooser<Integer> autoChooser;
	private Preferences prefs;
	private SendableChooser<Integer> encoderChooser;
	//PowerDistributionPanel pdp = new PowerDistributionPanel();
	
	private double forwardTimeMS;
	private double encoderDistanceInches;
	
	
	private AutoCommand[] getCommandsForAutoForward() {
		return new AutoCommand[] {
				new Forward(myRobot,(int)forwardTimeMS),
		};
	}
	
	private AutoCommand[] getCommandsForAutoEncoder() {
		return new AutoCommand[] {
				//new ForwardDistance(myRobot, encRight, encoderDistanceInches)
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
		SmartDashboard.putNumber("encRight", encRight.getDistance());

		SmartDashboard.putNumber("encLeftt", encLeft.getDistance());
	}

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		CameraServer.getInstance().startAutomaticCapture();
		// myRobot = new RobotDrive(1,2);
		myRobot = new DifferentialDrive(new Talon(1), new Talon(2));
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
		
		//Encoder encRight;
		//encRight = new Encoder(0, 1, true, Encoder.EncodingType.k4X);  // ports 2 and 3 weren't working
		//encRight.setDistancePerPulse(getDistancePerPulse());

		if (encoderChooser.getSelected() == 0) {
			// Use Encoder Objects
		}
		else if (encoderChooser.getSelected() == 1) {
			// Use DIO Objects for testing purposes
		}
	}

	public void autonomousInit() { 
		forwardTimeMS = prefs.getDouble("Forward Time in Milliseconds", 4000);
		encoderDistanceInches = prefs.getDouble("Encoder Distance in Inches", 60);
		
 		//encRight.reset();
		commands = getAutoCommands();
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
	
	public void autonomousPeriodic(){
		sendEncoderDataToSmartDashboard();
		for(int i=0; i<commands.length; ++i) {
			if (!commands[i].isFinished()) {
				commands[i].periodic();
				return;
			}
		}
		myRobot.arcadeDrive(0,0);
	}

	public void teleopInit(){
 		//encRight.reset();
    } 
	
	public void teleopPeriodic() {
		teleop.teleopPeriodic();
		sendEncoderDataToSmartDashboard();
		//SmartDashboard.putNumber("Power Distribution Panel ?", pdp.getCurrent(0));
	}
    
    public void testPeriodic() {
    	
    }
}