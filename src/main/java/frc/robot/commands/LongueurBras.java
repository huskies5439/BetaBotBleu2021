// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Bras;

public class LongueurBras extends CommandBase {
  Bras bras;
  DoubleSupplier vitesse;

  public LongueurBras(DoubleSupplier vitesse,Bras bras) {
    this.bras=bras;
    this.vitesse=vitesse;
    addRequirements(bras);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    //bras.ramp(x); ajouter une ramp si nécessaire
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
   
    if (bras.getPositionL() <= 85000) {
      bras.vitesseMoteurLongueur(vitesse.getAsDouble());
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}