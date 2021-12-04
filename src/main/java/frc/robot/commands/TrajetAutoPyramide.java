// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.BasePilotable;
import frc.robot.subsystems.Bras;
import frc.robot.subsystems.Cremaillere;
import frc.robot.subsystems.Pince;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class TrajetAutoPyramide extends SequentialCommandGroup {
  int side;

  /** Creates a new TrajetAutoJaunePyramide. */
  public TrajetAutoPyramide(int side, BasePilotable basePilotable, Bras bras, Cremaillere cremaillere, Pince pince) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(

    //Brake on ramp = 0.1
    new ParallelCommandGroup(
      new Avancer(0.45, 0.7, basePilotable), 
      new ParalleleHauteurLongueur(50, 1200, cremaillere, bras)), 
    
    new Tourner (-100*side, basePilotable),

    new ParallelCommandGroup(
      new Avancer(2.35, 0.7, basePilotable), 
      new ParalleleHauteurLongueur(370, 2800, cremaillere, bras)), 

    new Tourner(-45*side, basePilotable),

    new Avancer(0.95, 0.7, basePilotable), //s'accoter sur la pyramide

    new InstantCommand(pince::ouvrirPince),

    new Avancer(-1, 0.7, basePilotable),

    new InstantCommand(pince::ouvrirPince),

    new Tourner(63*side, basePilotable),

    new ParallelCommandGroup(
      new Avancer(2.15, 0.7, basePilotable), 
      new ParalleleHauteurLongueur(70, 1750, cremaillere, bras)), 

    new Avancer(-0.5, 0.3, basePilotable),

    new Tourner(-75*side, basePilotable),

    new ParallelCommandGroup(
      new Avancer(1.95, 0.7, basePilotable),
      new ParalleleHauteurLongueur(180, 1000, cremaillere, bras)),
    
    new InstantCommand(pince::ouvrirPince),
    
    new WaitCommand(2),
    
    new ParalleleHauteurLongueur(0, 0, cremaillere, bras)
    );
  }
}