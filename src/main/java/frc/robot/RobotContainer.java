// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.fasterxml.jackson.databind.JsonSerializable.Base;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.Allonger;
import frc.robot.commands.Avancer;
import frc.robot.commands.AutoHauteur;
import frc.robot.commands.AutoLongueur;
import frc.robot.commands.CapturerTube;
import frc.robot.commands.Descendre;
import frc.robot.commands.LongueurBras;
import frc.robot.commands.HauteurBras;
import frc.robot.commands.Monter;
import frc.robot.commands.ParalleleHauteurLongueur;
import frc.robot.commands.Pincer;
import frc.robot.commands.Retracter;
import frc.robot.commands.TrajetAutoPyramide;
import frc.robot.commands.TrajetAutoSafe;
import frc.robot.subsystems.BasePilotable;
import frc.robot.subsystems.Bras;
import frc.robot.subsystems.Cremaillere;
import frc.robot.subsystems.Pince;



public class RobotContainer {
  
  private final BasePilotable basePilotable = new BasePilotable();
  private final Bras bras = new Bras();
  private final Cremaillere lift = new Cremaillere();
  private final Pince pince = new Pince();
  private final Command safeJaune = new TrajetAutoSafe(1, basePilotable, lift, bras, pince);
  private final Command safeVert = new TrajetAutoSafe(-1, basePilotable, lift, bras, pince);
  private final Command pyramideJaune = new TrajetAutoPyramide(1, basePilotable, bras, lift, pince);
  private final Command pyramideVert = new TrajetAutoPyramide(-1, basePilotable, bras, lift, pince);
  private final SendableChooser <Command> chooser = new SendableChooser<>();
  private final Trigger pinceSwitchTrigger = new Trigger(pince::getSwitch);
  
XboxController manette = new XboxController(0);  

  public RobotContainer() {

    configureButtonBindings();

    basePilotable.setDefaultCommand(new RunCommand(()-> basePilotable.conduire(manette.getY(Hand.kLeft), manette.getX(Hand.kRight)), basePilotable));
    bras.setDefaultCommand(new LongueurBras(()-> -manette.getY(Hand.kRight), bras));
    //bras.setDefaultCommand(new LongueurBras(()-> manette.getTriggerAxis(Hand.kRight)-manette.getTriggerAxis(Hand.kLeft), bras));
    lift.setDefaultCommand(new HauteurBras(()-> manette.getTriggerAxis(Hand.kRight)-manette.getTriggerAxis(Hand.kLeft), lift));
    


    chooser.addOption("Safe Jaune", safeJaune);
    chooser.addOption("Safe Vert", safeVert);
    chooser.addOption("Pyramide Jaune", pyramideJaune);
    chooser.addOption("Pyramide Vert", pyramideVert);
    
  
    SmartDashboard.putData(chooser);
  }

  private void configureButtonBindings() {

    //faire des presets pour la pince à la place d'utiliser les bumper pour ajuster
    new JoystickButton(manette, Button.kX.value).whenPressed(new ParalleleHauteurLongueur(0, 0, lift, bras));//1er étage pyramide
    new JoystickButton(manette, Button.kA.value).whenPressed(new ParalleleHauteurLongueur(180, 1000, lift, bras));//2e étage pyramide
    new JoystickButton(manette, Button.kB.value).whenPressed(new ParalleleHauteurLongueur(370, 2800, lift, bras));//3e étage pyramide
    
    //new JoystickButton(manette, Button.kBumperRight.value).whenHeld(new Monter(lift));
    //new JoystickButton(manette, Button.kBumperLeft.value).whenHeld(new Descendre(lift));
    
    new JoystickButton(manette, Button.kBumperRight.value).whenPressed(new Pincer(pince));
    pinceSwitchTrigger.whenActive(new InstantCommand(pince::fermerPince, pince));

    //Fonction Non Limité Pour Se Remettre À 0
    new POVButton(manette, 0).whenHeld(new RunCommand(()-> lift.vitesseMoteurHauteur(0.3), lift)).whenReleased(new InstantCommand(lift::stop));
    new POVButton(manette, 180).whenHeld(new RunCommand(()-> lift.vitesseMoteurHauteur(-0.3), lift)).whenReleased(new InstantCommand(lift::stop));
    new POVButton(manette, 90).whenHeld(new RunCommand(()-> bras.vitesseMoteurLongueur(0.3), bras)).whenReleased(new InstantCommand(bras::stop));
    new POVButton(manette, 270).whenHeld(new RunCommand(()-> bras.vitesseMoteurLongueur(-0.3), bras)).whenReleased(new InstantCommand(bras::stop));
    new JoystickButton(manette, Button.kStart.value).whenHeld(new InstantCommand(bras::resetEncoder).andThen(new InstantCommand(lift::resetEncoder)));
    
    
}

  public Command getAutonomousCommand() {
    
    //return new ParalleleHauteurLongueur(180, 600, lift, bras);
    //return new AutoHauteur(180, lift);
    //return chooser.getSelected();
    

    return new InstantCommand(()-> basePilotable.setBrake(true),basePilotable) //brake auto
    .andThen(new InstantCommand(()-> basePilotable.setRamp(0.1),basePilotable)) //ramp auto
    .andThen(new InstantCommand(()-> basePilotable.resetGyro(), basePilotable))
    .andThen(chooser.getSelected().withTimeout(14.8)/*à tester*/) // trajet avec limite de temps
    .andThen(new InstantCommand(()-> basePilotable.setBrake(false),basePilotable)) //coast teleop
    .andThen(new InstantCommand(()-> basePilotable.setRamp(Constants.rampTeleop),basePilotable));//ramp teleop
  }
}