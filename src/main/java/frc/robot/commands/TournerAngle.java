// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.BasePilotable;

public class TournerAngle extends CommandBase {

  BasePilotable basePilotable;
  double angleCible;
  double marge;
  boolean stop;

  public TournerAngle(double angleCible, BasePilotable basePilotable) {

    this.basePilotable = basePilotable;
    this.angleCible = angleCible;
    marge = 0.1;
    stop = false;
    addRequirements(basePilotable);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    if (basePilotable.getAngle()<angleCible-marge) {
      basePilotable.autoConduire(0, 0.5);
    }

    if (basePilotable.getAngle()>angleCible+marge) {
      basePilotable.autoConduire(0, -0.5);
    }

    else {
    basePilotable.autoConduire(0, 0);
    stop = true;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return stop;
  }
}
