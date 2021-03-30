// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Lift;

public class Monter extends CommandBase {

  Lift lift;

  public Monter(Lift lift) {

    this.lift = lift;
    addRequirements(lift);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
 
    if (lift.getPositionH() <= 654) {

      lift.vitesseMoteurHauteur(1);
    }

    else {

      lift.vitesseMoteurHauteur(0);
    }
  }

  @Override
  public void end(boolean interrupted) {

    lift.vitesseMoteurHauteur(0);
  }

  @Override
  public boolean isFinished() {
    
    return false;
  }
}
