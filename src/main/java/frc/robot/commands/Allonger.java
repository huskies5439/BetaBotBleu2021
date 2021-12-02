// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Bras;

public class Allonger extends CommandBase {

  Bras bras;

  public Allonger(Bras bras) {

    this.bras = bras;
    addRequirements(bras);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
 
    if (bras.getPositionL() <= Constants.longueurMax) { //limiter le bras pour pas qu'ils se déraille

      bras.vitesseMoteurLongueur(1);
    }

    else {

      bras.vitesseMoteurLongueur(0);
    }
  }

  @Override
  public void end(boolean interrupted) {

    bras.vitesseMoteurLongueur(0);
  }

  @Override
  public boolean isFinished() {
    
    return false;
  }
}
