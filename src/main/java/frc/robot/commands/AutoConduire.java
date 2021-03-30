// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.BasePilotable;

public class AutoConduire extends CommandBase {

  BasePilotable basePilotable;
  double distance;
  double marge;
  int compteur;

  public AutoConduire(double distance, BasePilotable basePilotable) {

    this.basePilotable=basePilotable;
    this.distance=distance;
    marge=0.1;
    compteur=0;
    addRequirements(basePilotable);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    if (basePilotable.getPosition() > distance+marge) {

      basePilotable.autoConduire(-0.5,0);
    }

    else if (basePilotable.getPosition() < distance-marge) {

      basePilotable.autoConduire(0.5,0);  
    }

    else {

      basePilotable.stop();  
      compteur = compteur + 1;
    }
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    
    return compteur >= 2;
  }
}
