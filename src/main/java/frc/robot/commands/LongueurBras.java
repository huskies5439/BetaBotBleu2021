// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Bras;

public class LongueurBras extends CommandBase {

  Constants longueurMax;
  Constants longueurMin;
  Bras bras;
  DoubleSupplier vitesse;

  public LongueurBras(DoubleSupplier vitesse,Bras bras) {

    this.bras = bras;
    this.vitesse = vitesse;
    addRequirements(bras);
  }

  @Override
  public void initialize() {

    //bras.ramp(x); ajouter une ramp si nécessaire
  }

  @Override
  public void execute() {
   
    if ((bras.getPositionL() > Constants.longueurMax && vitesse.getAsDouble() > 0) 
        || (bras.getPositionL() < Constants.longueurMin && vitesse.getAsDouble() < 0) 
        || Math.abs(vitesse.getAsDouble()) <= 0.15)
        
    {
      bras.stop();
    }

    else {
      bras.vitesseMoteurLongueur(vitesse.getAsDouble());
    }
    SmartDashboard.putNumber("vitesse bras", vitesse.getAsDouble());
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    
    return false;
  }
}