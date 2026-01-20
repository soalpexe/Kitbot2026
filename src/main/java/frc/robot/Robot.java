// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class Robot extends TimedRobot {
    CommandScheduler scheduler;
    Timer timer;

    CommandXboxController controller;
    RobotContainer container;

    public Robot() {
        scheduler = CommandScheduler.getInstance();
        timer = new Timer();

        controller = new CommandXboxController(Constants.controllerID);
        container = new RobotContainer();
    }

    @Override
    public void robotPeriodic() {
        scheduler.run();
        
        if (timer.get() > 5) {
            System.gc();
            timer.reset();
        }
    }

    @Override
    public void autonomousInit() {}

    @Override
    public void autonomousPeriodic() {}

    @Override
    public void teleopInit() {
        scheduler.setDefaultCommand(
            container.drivetrain,
            container.drivetrain.driveCmd(
                () -> {
                    double input = controller.getLeftY();
                    double inputDB = Utils.inTolerance(0, input, Constants.deadband) ? 0 : input;

                    return inputDB;
                },
                () -> {
                    double input = controller.getRightX();
                    double inputDB = Utils.inTolerance(0, input, Constants.deadband) ? 0 : input;

                    return inputDB;
                }
            )
        );
    }

    @Override
    public void teleopPeriodic() {
        controller.leftBumper()
            .onTrue(container.intakeCmd())
            .onFalse(container.stopCmd());
            
        controller.rightBumper()
            .onTrue(container.shootCmd())
            .onFalse(container.stopCmd());
    }
}
