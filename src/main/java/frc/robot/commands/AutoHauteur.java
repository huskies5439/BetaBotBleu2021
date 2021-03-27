// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Lift;

public class AutoHauteur extends CommandBase {
  /** Creates a new AutoHauteur. */
  Lift lift;
  double cible;
  int marge;
  int compteur;

  public AutoHauteur(Double cible,Lift lift) {
    this.cible=cible;
    this.lift=lift;
    marge=30;
    compteur=0;
    addRequirements(lift);
    }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (lift.getPositionH() > cible+marge) {
      lift.vitesseMoteurHauteur(-1);
    }
    else if (lift.getPositionH() < cible-marge) {
      lift.vitesseMoteurHauteur(1);  
    }
    else {
      lift.stop();  
      compteur = compteur + 1;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return compteur >= 4;
  }
}