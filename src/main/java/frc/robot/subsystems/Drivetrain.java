// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drivetrain extends SubsystemBase {
    private TalonFX leftMotor, rightMotor;

    public Drivetrain(int leftMotorID, int rightMotorID) {
        leftMotor = new TalonFX(leftMotorID);
        rightMotor = new TalonFX(rightMotorID);

        TalonFXConfiguration leftConfig = new TalonFXConfiguration();
        leftConfig.MotorOutput.NeutralMode = NeutralModeValue.Brake;
        
        TalonFXConfiguration rightConfig = new TalonFXConfiguration();
        rightConfig.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;
        rightConfig.MotorOutput.NeutralMode = NeutralModeValue.Brake;

        leftMotor.getConfigurator().apply(leftConfig);
        rightMotor.getConfigurator().apply(rightConfig);
    }

    public void drive(double power, double steer) {
        double leftOut = power + steer, rightOut = power - steer;

        leftMotor.setControl(new DutyCycleOut(leftOut));
        rightMotor.setControl(new DutyCycleOut(rightOut));
    }

    public Command driveCmd(DoubleSupplier powerSupplier, DoubleSupplier steerSupplier) {
        return Commands.run(
            () -> {
                double power = powerSupplier.getAsDouble();
                double steer = steerSupplier.getAsDouble();

                drive(power, steer);
            },
            this
        );
    }
    
    public Command stopCmd() {
        return Commands.runOnce(
            () -> drive(0, 0),
            this
        );
    }

    @Override
    public void periodic() {}
}
