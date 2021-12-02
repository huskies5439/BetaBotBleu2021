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
import frc.robot.subsystems.Lift;
import frc.robot.subsystems.Pince;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class TrajetAutoPyramide extends SequentialCommandGroup {
  int side;

  /** Creates a new TrajetAutoJaunePyramide. */
  public TrajetAutoPyramide(int side, BasePilotable basePilotable, Bras bras, Lift lift, Pince pince) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(

    //Brake on ramp = 0.1
    new ParallelCommandGroup(new Avancer(0.35, 0.2, basePilotable), new ParalleleHauteurLongueur(190, 1075, lift, bras)), 
    
    new CapturerTube(pince, lift),

    new Tourner (-100*side, basePilotable),

    new ParallelCommandGroup(new Avancer(2.25, 0.8, basePilotable), new ParalleleHauteurLongueur(370, 2200, lift, bras)), 

    new Tourner(-45*side, basePilotable),

    new Avancer(0.95, 0.6, basePilotable), //s'accoter sur la pyramide

    new WaitCommand(0.5),

    new InstantCommand(pince::ouvrirPince),

    new Avancer(-1.25, 0.8, basePilotable),

    new Tourner(55.5*side, basePilotable),

    new ParallelCommandGroup(new Avancer(1.80, 0.8, basePilotable), new ParalleleHauteurLongueur(200, 1500, lift, bras)),

    new WaitCommand(0.5),

    new CapturerTube(pince, lift)
  
      //accelerer tests, a oter pour compe
    /*new WaitCommand(1), 

    new ParalleleHauteurLongueur(0, 0, lift, bras)*/
    );
  }
}