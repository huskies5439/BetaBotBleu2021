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


public class TrajetAutoPyramide extends SequentialCommandGroup {
  int side;

  /** Creates a new TrajetAutoJaunePyramide. */
  public TrajetAutoPyramide(int side, BasePilotable basePilotable, Bras bras, Cremaillere cremaillere, Pince pince) {
    
    addCommands(

   
    new ParallelCommandGroup(
      new Avancer(0.475, 0.7, basePilotable), 
      new ParalleleHauteurLongueur(20, 1200, cremaillere, bras)), //Ramasser le 1er tube
    new WaitCommand(0.1),
    new Tourner (-100*side, basePilotable),

    new ParallelCommandGroup(
      new Avancer(2.85, 0.7, basePilotable), 
      new ParalleleHauteurLongueur(370, 2500, cremaillere, bras)), //Aller vers la pyramide

    new Tourner(-45*side, basePilotable),

    new Avancer(1.0, 0.7, basePilotable), //s'accoter sur la pyramide

    new InstantCommand(pince::ouvrirPince), //lâcher le tube

    new Avancer(-0.95, 0.7, basePilotable), //retourner vers le support

    new InstantCommand(pince::ouvrirPince), //s'assurer que la pince est ouverte

    new Tourner(59*side, basePilotable),

    new ParallelCommandGroup(
      new Avancer(2.4, 0.75, basePilotable), 
      new ParalleleHauteurLongueur(30, 1750, cremaillere, bras)), //attrape le 2e tube

    new Avancer(-0.5, 0.3, basePilotable),

    new Tourner(-75*side, basePilotable),

    new ParallelCommandGroup(
      new Avancer(1.95, 0.8, basePilotable),
      new ParalleleHauteurLongueur(180, 1000, cremaillere, bras)), //retourne vers la pyramide
    
    new InstantCommand(pince::ouvrirPince) //lâche le tube
    
   
    );
  }
}