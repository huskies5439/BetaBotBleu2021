// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpiutil.math.MathUtil;
import frc.robot.Constants;
import frc.robot.subsystems.Bras;
import frc.robot.subsystems.Cremaillere;

public class AutoLongueur extends CommandBase {

  //Faire un program qui permetrait de remettre la longueur dans le range de l'encoder
  Bras bras;
  int cibleH;
  int cible;
  int marge;
  boolean stop;
  int longueurMax;

  public AutoLongueur(int cible, Bras bras, int cibleH) {

    this.cible = cible;
    this.bras = bras;
    this.cibleH = cibleH;
    marge = 10;
    stop = false;
    addRequirements(bras);
    
    }

  @Override
  public void initialize() {
    stop=false;
    if (cibleH>200){
      longueurMax=2800;
    }
    else{
      longueurMax = Constants.longueurMax;
    }
    
    cible = MathUtil.clamp(cible, Constants.longueurMin, longueurMax);
  }

  @Override
  public void execute() {

    if (bras.getPositionL() > cible + marge) {

      bras.vitesseMoteurLongueur(-1);
    }

    else if (bras.getPositionL() < cible - marge) {

      bras.vitesseMoteurLongueur(1);  
    }

    else {

      stop = true;
    }
  }

  @Override
  public void end(boolean interrupted) {
    bras.stop();  
  }

  @Override
  public boolean isFinished() {
    
    return stop;
  }
}