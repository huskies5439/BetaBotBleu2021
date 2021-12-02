// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpiutil.math.MathUtil;
import frc.robot.Constants;
import frc.robot.subsystems.Lift;

public class AutoHauteur extends CommandBase {

  //Faire un program qui permetrait de remettre la longueur dans le range de l'encoder
  Lift lift;
  int cible;
  int marge;
  boolean stop;

  public AutoHauteur(int cible, Lift lift) {

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

    if (lift.getPositionH() > cible + marge) {//bloque les moteurs si les limites sont plus grande

      lift.vitesseMoteurHauteur(-1);
    }

    else if (lift.getPositionH() < cible - marge) {///bloque les moteurs si les limites sont plus petite

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