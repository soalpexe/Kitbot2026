// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {
    private CommandScheduler scheduler;
    private Timer timer;

    private RobotContainer container;

    public Robot() {
        scheduler = CommandScheduler.getInstance();
        timer = new Timer();

        container = new RobotContainer(Constants.controllerID);
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
    public void teleopInit() {}
}
