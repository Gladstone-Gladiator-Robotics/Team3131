package org.usfirst.frc.team3131.robot;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */

/* ROBORIO PWM AND DIO PORT GUIDE
 * PWM :
 * 1 - Left drive motor
 * 2 - Right drive motor
 * 4 - Lift motor
 * DIO :
 * 5 - Top limit switch
 * 6 - Bottom limit switch (neither of these exist yet)
 */

public class Robot extends IterativeRobot {
	private DifferentialDrive myRobot;
	private Teleop teleop;
	private Encoder encDrive = new Encoder(0, 1, true, Encoder.EncodingType.k4X);
	private Preferences prefs = Preferences.getInstance();
	// PowerDistributionPanel pdp = new PowerDistributionPanel();
	String gameData;
	private Talon leftDriveTalon = new Talon(1);
	private Talon rightDriveTalon = new Talon(2);
	private ADXRS450_Gyro gyro = new ADXRS450_Gyro();
	private Autonomous autonomous = new Autonomous(myRobot, encDrive, prefs, gyro);

	private void sendDataToSmartDashboard() {
		SmartDashboard.putNumber("Right Encoder Distance", encDrive.getDistance());
		SmartDashboard.putNumber("Gyroscope Angle", gyro.getAngle());
	}
	
	 /* This function is run when the robot is first started up and should be
	  * used for any initialization code.
	  */
	public void robotInit() {
		myRobot = new DifferentialDrive(leftDriveTalon, rightDriveTalon);
		myRobot.setDeadband(0);
		teleop = new Teleop(myRobot);
		gyro.calibrate();
		CameraServer.getInstance().startAutomaticCapture();
	}

	public void robotPeriodic() {
		setMotorDirections();
		sendDataToSmartDashboard();
	}
	
	public void autonomousInit() {
		autonomous.autonomousInit();
	}

	public void autonomousPeriodic() {
		autonomous.autonomousPeriodic();
	}

	public void teleopInit() {
		encDrive.reset();
	}

	private void setMotorDirections() {
		leftDriveTalon.setInverted(prefs.getBoolean("Left Motor Inverted", true));
		rightDriveTalon.setInverted(prefs.getBoolean("Right Motor Inverted", false));
	}
	
	public void teleopPeriodic() {
		teleop.teleopPeriodic();
	}

	public void testPeriodic() {

	}
}