// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Cremaillere;

public class HauteurBras extends CommandBase {

  Constants hauteurMax;
  Constants hauteurMin;
  Cremaillere lift;
  DoubleSupplier vitesse;

  public HauteurBras(DoubleSupplier vitesse,Cremaillere lift) {

    this.lift = lift;
    this.vitesse = vitesse;
    addRequirements(lift);
  }

  @Override
  public void initialize() {

    //bras.ramp(x); ajouter une ramp si nécessaire
  }

  @Override
  public void execute() { //limite pour descendre et Monter la cremaillere
   
    if ((lift.getPositionH() > Constants.hauteurMax && vitesse.getAsDouble() > 0) 
        || (lift.getPositionH() < Constants.hauteurMin && vitesse.getAsDouble() < 0) 
        || Math.abs(vitesse.getAsDouble()) <= 0.15)
        
    {
      lift.stop();
    }

    else {
      lift.vitesseMoteurHauteur(vitesse.getAsDouble());
    }
    //SmartDashboard.putNumber("vitesse ", vitesse.getAsDouble());
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    
    return false;
  }
}
