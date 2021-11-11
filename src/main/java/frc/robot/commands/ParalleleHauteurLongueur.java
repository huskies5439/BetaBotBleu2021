// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.AutoHauteur;
import frc.robot.commands.AutoLongueur;
import frc.robot.subsystems.Bras;
import frc.robot.subsystems.Lift;

public class ParalleleHauteurLongueur extends ParallelCommandGroup {
  /** Creates a new ParalleleHauteurLongueur. */
  public ParalleleHauteurLongueur(int cibleH, int cibleL, Bras bras, Lift lift) {
    new AutoLongueur(cibleL, bras);
    new AutoHauteur(cibleH, lift);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
