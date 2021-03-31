// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Bras;

public class AutoLongueur extends CommandBase {

  Bras bras;
  int cible;
  int marge;
  boolean stop;

  public AutoLongueur(int cible, Bras bras) {

    this.cible=cible;
    this.bras=bras;
    marge = 10;
    stop = false;
    addRequirements(bras);
    }

  @Override
  public void initialize() {}

  @Override
  public void execute() {

    if (bras.getPositionL() > cible + marge) {

      bras.vitesseMoteurLongueur(-1);
    }

    else if (bras.getPositionL() < cible - marge) {

      bras.vitesseMoteurLongueur(1);  
    }

    else {

      bras.stop();  
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