// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.BasePilotable;

public class Tourner extends CommandBase {

  BasePilotable basePilotable;
  double angleCible;
  double marge;
  boolean stop;

  public Tourner(double angleCible, BasePilotable basePilotable) {

    this.basePilotable = basePilotable;
    this.angleCible = angleCible;
    marge = 1;
    stop = false;
    addRequirements(basePilotable);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    stop = false;
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    if (basePilotable.getAngle()<angleCible-marge) {
      basePilotable.autoConduire(0, 0.3);
    }

    else if (basePilotable.getAngle()>angleCible+marge) {
      basePilotable.autoConduire(0, -0.3);
    }

    else {
   
    stop = true;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
 basePilotable.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return stop;
  }
}
