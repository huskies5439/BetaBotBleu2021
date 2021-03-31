// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.BasePilotable;

public class AutoConduire extends CommandBase {

  BasePilotable basePilotable;
  double distance;
  double marge;
  boolean stop;
  double ajustementRotation;

  public AutoConduire(double distance, BasePilotable basePilotable) {

    this.basePilotable=basePilotable;
    this.distance=distance;
    marge = 0.1;
    stop = false;
    addRequirements(basePilotable);
  }

  @Override
  public void initialize() {

    basePilotable.resetEncoder();
    basePilotable.resetGyro();
  }

  @Override
  public void execute() {

    ajustementRotation = (0-basePilotable.getAngle()) * 0.01;

    if (basePilotable.getPosition() > distance + marge) {

      basePilotable.autoConduire(-0.5, ajustementRotation);
    }

    else if (basePilotable.getPosition() < distance - marge) {

      basePilotable.autoConduire(0.5, ajustementRotation);  
    }

    else {

      basePilotable.stop();  
      stop = true;
    }
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    
    return stop;
  }
}