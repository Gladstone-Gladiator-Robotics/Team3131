package org.usfirst.frc.team3131.robot;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Autonomous {
	Autonomous(DifferentialDrive myRobot, Encoder encDrive, Preferences prefs, ADXRS450_Gyro gyro){
		this.myRobot = myRobot;
		this.encDrive = encDrive;
		this.prefs = prefs;
		this.gyro = gyro;
		autoChooser = new SendableChooser<Integer>();
		autoChooser.addDefault("Only Forward", 0);
		autoChooser.addObject("Left switch", 1);
		autoChooser.addObject("11 Second Delay and Forward", 2);
		autoChooser.addObject("Do nothing", 3);
		SmartDashboard.putData("Autonomous Chooser", autoChooser);
	}
	
	private DifferentialDrive myRobot;
	private Encoder encDrive;
	private Preferences prefs;
	private double forwardTimeMS;
	private double encoderDistanceInches;
	private double kpValue;
	private ADXRS450_Gyro gyro;
	private AutoCommand[] commands;
	private SendableChooser<Integer> autoChooser;
	
	private double getDistancePerPulse() {
		double gear1 = 14;
		double gear2 = 50;
		double gear3 = 16;
		double gear4 = 48;
		double gearRatio = (gear1 / gear2) * (gear3 / gear4);
		int pulsePerMotorRev = 20;
		double radiusInInches = 3;
		double circumference = 2 * Math.PI * radiusInInches;
		return gearRatio * circumference / pulsePerMotorRev;
	}
	
	private AutoCommand[] getCommandsForAutoForward() {
		return new AutoCommand[] { new Forward(myRobot, (int) forwardTimeMS), };
	}

	private AutoCommand[] getCommandsForAutoCube() {
		return new AutoCommand[] { 
				new AutonomousLift(), new Forward(myRobot, 2550),  new AutonomousRelease(), };

//		return new AutoCommand[] { new AutonomousLift(), new Forward(myRobot, 2600), new AutonomousRelease()};
	}

	private AutoCommand[] getCommandsForAutoStop() {
		return new AutoCommand[] {};
	}
	private AutoCommand[] getCommandsForAutoDelayForward() {
		return new AutoCommand[] { new AutoDelay(11), new Forward(myRobot, 3000), }; 
	}
	
	private AutoCommand[] getAutoCommands() {
		switch (autoChooser.getSelected()) {
		case 0:
			return getCommandsForAutoForward();
		case 1:
			return getCommandsForAutoCube();
		case 2:
			return getCommandsForAutoDelayForward();
		default:
			return getCommandsForAutoStop();
		}
	}
	
	public void autonomousInit() {
		encDrive.setDistancePerPulse(getDistancePerPulse());
		forwardTimeMS = prefs.getDouble("Forward Time in Milliseconds", 3000);
		encoderDistanceInches = prefs.getDouble("Encoder Distance in Inches", 96);
		commands = getAutoCommands();
		kpValue = prefs.getDouble("Kp Value Set", .05); // borked
	}

	public void autonomousPeriodic() {
		for (int i = 0; i < commands.length; ++i) {
			if (!commands[i].isFinished()) {
				commands[i].periodic();
				System.out.println("Running autonomous command " + i);
				return;
			}
		}
		myRobot.arcadeDrive(0, 0);
	}
}
