// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import frc.robot.commands.AutoConduire;
import frc.robot.commands.AutoHauteur;
import frc.robot.commands.AutoLongueur;
import frc.robot.commands.CapturerTube;
import frc.robot.commands.Descendre;
import frc.robot.commands.LongueurBras;
import frc.robot.commands.Monter;
import frc.robot.commands.ParalleleHauteurLongueur;
import frc.robot.commands.Pincer;
import frc.robot.subsystems.BasePilotable;
import frc.robot.subsystems.Bras;
import frc.robot.subsystems.Lift;
import frc.robot.subsystems.Pince;

public class RobotContainer {

  private final BasePilotable basePilotable = new BasePilotable();
  private final Bras bras = new Bras();
  private final Lift lift = new Lift();
  private final Pince pince = new Pince();
  
XboxController manette = new XboxController(0);  

  public RobotContainer() {

    configureButtonBindings();

    basePilotable.setDefaultCommand(new RunCommand(()-> basePilotable.conduire(manette.getY(Hand.kLeft), manette.getX(Hand.kRight)), basePilotable));
    bras.setDefaultCommand(new LongueurBras(()-> manette.getTriggerAxis(Hand.kRight)-manette.getTriggerAxis(Hand.kLeft), bras));
  }

  private void configureButtonBindings() {

    //faire des presets pour la pince à la place d'utuliser les bumper pour ajuster
    new JoystickButton(manette, Button.kA.value).whenPressed(new CapturerTube(pince, lift));
    new JoystickButton(manette, Button.kY.value).whenPressed(new ParalleleHauteurLongueur(180, 600, lift, bras));
    new JoystickButton(manette, Button.kB.value).toggleWhenPressed(new Pincer(pince));
    
    new JoystickButton(manette, Button.kBumperRight.value).whenHeld(new Monter(lift));
    new JoystickButton(manette, Button.kBumperLeft.value).whenHeld(new Descendre(lift));
    //Fonction Non Limité Pour Se Remettre À 0
    new POVButton(manette, 0).whenHeld(new RunCommand(()-> lift.vitesseMoteurHauteur(0.3), lift)).whenReleased(new InstantCommand(lift::stop));
    new POVButton(manette, 180).whenHeld(new RunCommand(()-> lift.vitesseMoteurHauteur(-0.3), lift)).whenReleased(new InstantCommand(lift::stop));
    new POVButton(manette, 90).whenHeld(new RunCommand(()-> bras.vitesseMoteurLongueur(0.3), bras)).whenReleased(new InstantCommand(bras::stop));
    new POVButton(manette, 270).whenHeld(new RunCommand(()-> bras.vitesseMoteurLongueur(-0.3), bras)).whenReleased(new InstantCommand(bras::stop));
    new JoystickButton(manette, Button.kStart.value).whenHeld(new InstantCommand(bras::resetEncoder).andThen(new InstantCommand(lift::resetEncoder)));
  }

  public Command getAutonomousCommand() {
    
    //return new ParalleleHauteurLongueur(180, 600, lift, bras);
    return new AutoHauteur(180, lift);
  }
}