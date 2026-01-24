// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Hopper;
import frc.robot.subsystems.Shooter;

public class RobotContainer {
    private CommandScheduler scheduler;
    public CommandXboxController controller;

    private Drivetrain drivetrain;
    private Shooter shooter;
    private Hopper hopper;

    public RobotContainer(int controllerID) {
        scheduler = CommandScheduler.getInstance();
        controller = new CommandXboxController(controllerID);

        drivetrain = new Drivetrain(Constants.Drivetrain.leftMotorID, Constants.Drivetrain.rightMotorID);
        shooter = new Shooter(Constants.Shooter.motorID);
        hopper = new Hopper(Constants.Hopper.motorID);

        configureBindings();
    }

    private void configureBindings() {
        scheduler.setDefaultCommand(
            drivetrain,
            drivetrain.driveCmd(
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

        controller.leftBumper()
            .onTrue(intakeCmd())
            .onFalse(stopCmd());

        controller.rightBumper()
            .onTrue(shootCmd())
            .onFalse(stopCmd());
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
