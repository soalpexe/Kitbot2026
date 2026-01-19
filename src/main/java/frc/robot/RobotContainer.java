// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Hopper;
import frc.robot.subsystems.Shooter;

public class RobotContainer {
    Drivetrain drivetrain;
    Shooter shooter;
    Hopper hopper;

    public RobotContainer() {
        drivetrain = new Drivetrain(Constants.Drivetrain.leftMotorID, Constants.Drivetrain.rightMotorID);
        shooter = new Shooter(Constants.Shooter.motorID);
        hopper = new Hopper(Constants.Hopper.motorID);
    }

    public Command intakeCmd() {
        return Commands.sequence(
            shooter.setOutCmd(0.5),
            hopper.setOutCmd(1)
        );
    }
    
    public Command shootCmd() {
        return Commands.sequence(
            shooter.setOutCmd(0.7),
            Commands.waitSeconds(0.5),
            hopper.setOutCmd(-1)
        );
    }
    
    public Command stopCmd() {
        return Commands.sequence(
            shooter.stopCmd(),
            hopper.stopCmd()
        );
    }
}
