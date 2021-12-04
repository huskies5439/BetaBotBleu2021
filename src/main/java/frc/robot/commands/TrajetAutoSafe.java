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
public class TrajetAutoSafe extends SequentialCommandGroup {
   int side;

  /** Creates a new TrajetAutonome. */
  public TrajetAutoSafe(int side, BasePilotable basePilotable, Cremaillere cremaillere, Bras bras, Pince pince) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(

    //Brake on ramp = 0.1

    new ParallelCommandGroup(new Avancer(0.35, 0.2, basePilotable), new ParalleleHauteurLongueur(190, 1075, cremaillere, bras)), 

    new WaitCommand(0.5),

    new CapturerTube(pince, cremaillere),

    new Tourner (-105*side, basePilotable),

    new Avancer(2.9, 0.8, basePilotable),
    
    new InstantCommand(pince::ouvrirPince),

    new ParallelCommandGroup(
      new Avancer(-2.9, 0.8, basePilotable), 
      new ParalleleHauteurLongueur(200, 1500, cremaillere, bras)),

    new Tourner(20*side, basePilotable),

    new WaitCommand(0.5),

    new CapturerTube(pince, cremaillere),

    new ParalleleHauteurLongueur(180, 2400, cremaillere, bras),//pour ne pas dropper le tube sur le support

    new InstantCommand(pince::ouvrirPince), //lacher le tube
      //accelerer tests, a oter pour compe
    new WaitCommand(1), 

    new ParalleleHauteurLongueur(0, 0, cremaillere, bras)
    
    );
  }
}
