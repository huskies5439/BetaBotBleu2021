// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.BasePilotable;

public class Avancer extends CommandBase {

  BasePilotable basePilotable;
  double distance;
  double marge;
  boolean stop;
  double ajustementRotation;
  double angleDirection;
  double vitesse;

  public Avancer(double distance, double vitesse, BasePilotable basePilotable) {

    this.basePilotable = basePilotable;
    this.distance = distance;
    this.vitesse = Math.abs(vitesse);
    marge = 0.01;
    stop = false;
    addRequirements(basePilotable);
  }

  @Override
  public void initialize() {

    basePilotable.resetEncoder();
    angleDirection = basePilotable.getAngle();
    stop=false;
  }

  @Override
  public void execute() {

    ajustementRotation = (angleDirection-basePilotable.getAngle()) * 0.025 /* à calibrer */;
    
    if (vitesse > 0.4 && Math.abs(distance - basePilotable.getPosition()) <= 0.3) {

      vitesse = vitesse/2.0;
    }

    if (basePilotable.getPosition() > distance + marge) {

      basePilotable.autoConduire(-vitesse, ajustementRotation);
    }

    else if (basePilotable.getPosition() < distance - marge) {

      basePilotable.autoConduire(vitesse, ajustementRotation);  
    }

    else {

     
      stop = true;
    }
   
  }

  @Override
  public void end(boolean interrupted) {
     basePilotable.stop();
      }

  @Override
  public boolean isFinished() {
    
    return stop;
  }
}