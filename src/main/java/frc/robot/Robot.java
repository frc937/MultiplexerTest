// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

//import com.revrobotics.Rev2mDistanceSensor.Port;
import com.revrobotics.Rev2mDistanceSensor.Unit;
import com.revrobotics.*;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;




/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project
 */

public class Robot extends TimedRobot {

  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  private TCA9548A multiplexer;
  private Rev2mDistanceSensor distOnboard;
  private ColorSensorV3 color;
  private XboxController controller;
  private static final int deviceID = 1; // pwm port
  //private CANSparkMax m_motor;
  private Spark m_motor;

  private AnalogInput pressure;


  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
  
    multiplexer = new TCA9548A();
    multiplexer.setBus(1);
    /*distOnboard = new Rev2mDistanceSensor(Port.kMXP);
    distOnboard.setDistanceUnits(Unit.kMillimeters);*/

    color = new ColorSensorV3(Port.kMXP);
  
    m_motor = new Spark(deviceID);

    pressure = new AnalogInput(0);


   controller = new XboxController(0);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {}

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
   
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {
      //distOnboard.setAutomaticMode(true);
      /*System.out.println("BUSSES ENABLED: " + multiplexer.getEnabledBusses());
      multiplexer.setBus(1);
      System.out.println("BUSSES ENABLED: " + multiplexer.getEnabledBusses());*/
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    multiplexer.setBus(1);
    /*distOnboard.setAutomaticMode(true);
      if(distOnboard.isRangeValid()) {
          SmartDashboard.putNumber("Range Onboard", distOnboard.getRange());
          SmartDashboard.putNumber("Timestamp Onboard", distOnboard.getTimestamp());

      }

      SmartDashboard.putBoolean("Valid range", distOnboard.isRangeValid());
      distOnboard.setAutomaticMode(false);*/
      SmartDashboard.putNumber("Red", color.getRed());
      SmartDashboard.putNumber("Blue", color.getBlue());
      SmartDashboard.putNumber("Green", color.getGreen());
      multiplexer.setBus(0);

      SmartDashboard.putNumber("Pressure reading", pressure.getVoltage());

      if (controller.getAButton()) {
          m_motor.set(0.5);
      } else if (controller.getBButton()) {
          m_motor.set(-0.5);
      } else { 
        m_motor.set(0);
      }

  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {}

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {
      distOnboard.setAutomaticMode(false);
  }
}
