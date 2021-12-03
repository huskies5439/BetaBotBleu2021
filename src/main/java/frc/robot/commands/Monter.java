// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Cremaillere;

public class Monter extends CommandBase {

  Cremaillere cremaillere;

  public Monter(Cremaillere cremaillere) {

    this.cremaillere = cremaillere;
    addRequirements(cremaillere);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
 
    if (cremaillere.getPositionH() <= Constants.hauteurMax) {//monter la cremaillere

      cremaillere.vitesseMoteurHauteur(1);
    }

    else {

      cremaillere.vitesseMoteurHauteur(0);
    }
  }

  @Override
  public void end(boolean interrupted) {

    cremaillere.vitesseMoteurHauteur(0);
  }

  @Override
  public boolean isFinished() {
    
    return false;
  }
}