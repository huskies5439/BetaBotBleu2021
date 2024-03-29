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
  public void initialize() {//fermer et ouvrir la pince

  pince.setOveride();

    if (pince.getState()){
      pince.fermerPince();
    }
    else{
       pince.ouvrirPince();
      }
   
  }

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {
    
    pince.releaseOveride();//assurer que ca se referme
  }

  @Override
  public boolean isFinished() {
    
    return false;
  }
}