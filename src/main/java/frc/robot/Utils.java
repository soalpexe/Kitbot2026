// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

public class Utils {
    public static boolean inTolerance(double target, double current, double tolerance) {
        return Math.abs(target - current) < tolerance;
    }
}
