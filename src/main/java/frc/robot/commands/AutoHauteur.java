// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Lift;

public class AutoHauteur extends CommandBase {

  Lift lift;
  int cible;
  int marge;
  int compteur;

  public AutoHauteur(int cible,Lift lift) {

    this.cible=cible;
    this.lift=lift;
    marge=10;
    compteur=0;
    addRequirements(lift);
    }

  @Override
  public void initialize() {}

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

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    
    return compteur >= 4;
  }
}