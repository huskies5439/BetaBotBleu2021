// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Pince;

public class Pincer extends CommandBase {
  
  Pince pince;
  
  public Pincer(Pince pince) {

    this.pince = pince;
    addRequirements(pince);
  }

  @Override
  public void initialize() {

    pince.ouvrirPince();
  }

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {

    pince.fermerPince();
  }

  @Override
  public boolean isFinished() {
    
    return false;
  }
}