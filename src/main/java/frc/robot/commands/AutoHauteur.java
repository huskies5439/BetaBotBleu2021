// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpiutil.math.MathUtil;
import frc.robot.Constants;
import frc.robot.subsystems.Cremaillere;

public class AutoHauteur extends CommandBase {

  Cremaillere lift;
  int cible;
  int marge;
  boolean stop;

  public AutoHauteur(int cible, Cremaillere lift) {

    this.cible = cible;
    this.lift = lift;
    marge = 10;
    stop = false;
    addRequirements(lift);
    cible = MathUtil.clamp(cible, Constants.hauteurMin, Constants.hauteurMax);
    }

  @Override
  public void initialize() {
    stop=false;
  }

  @Override
  public void execute() {

    if (lift.getPositionH() > cible + marge) {
      lift.vitesseMoteurHauteur(-1);
    }

    else if (lift.getPositionH() < cible - marge) {

      lift.vitesseMoteurHauteur(1);  
    }

    else {

      stop = true;
    }
  }

  @Override
  public void end(boolean interrupted) {
     lift.stop();  
  }

  @Override
  public boolean isFinished() {
    
    return stop;
  }
}