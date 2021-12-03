// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.subsystems.Bras;
import frc.robot.subsystems.Cremaillere;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ParalleleHauteurLongueur extends ParallelCommandGroup {
  /** Creates a new ParalleleHauteurLongueur. */
  public ParalleleHauteurLongueur(int cibleH, int cibleL, Cremaillere cremaillere, Bras bras) {
    addCommands( //plusieurs commande comme en autonome pour faire les 2 choses en même temps
      new AutoLongueur(cibleL, bras),
      new AutoHauteur(cibleH, cremaillere)
    );
  }
}
