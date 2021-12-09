// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.LongueurBras;
import frc.robot.commands.HauteurBras;
import frc.robot.commands.ParalleleHauteurLongueur;
import frc.robot.commands.Pincer;
import frc.robot.commands.TrajetAutoPyramide;
import frc.robot.subsystems.BasePilotable;
import frc.robot.subsystems.Bras;
import frc.robot.subsystems.Cremaillere;
import frc.robot.subsystems.Pince;



public class RobotContainer {
  
  //Initialisation des sous-systèmes
  private final BasePilotable basePilotable = new BasePilotable();
  private final Bras bras = new Bras();
  private final Cremaillere lift = new Cremaillere();
  private final Pince pince = new Pince();

  //Trajet autonomes
  private final Command pyramideJaune = new TrajetAutoPyramide(1, basePilotable, bras, lift, pince);
  private final Command pyramideVert = new TrajetAutoPyramide(-1, basePilotable, bras, lift, pince);
  private final Command trajetVide = new WaitCommand(14);
  private final SendableChooser <Command> chooser = new SendableChooser<>();

  //Créer le trigger correspondant aux limit switch de la pince
  private final Trigger pinceSwitchTrigger = new Trigger(pince::getSwitch);
  
  XboxController manette = new XboxController(0);  

  public RobotContainer() {

    configureButtonBindings();

    basePilotable.setDefaultCommand(new RunCommand(()-> basePilotable.conduire(manette.getY(Hand.kLeft), manette.getX(Hand.kRight)), basePilotable));
    bras.setDefaultCommand(new LongueurBras(()-> -manette.getY(Hand.kRight), bras));
    lift.setDefaultCommand(new HauteurBras(()-> manette.getTriggerAxis(Hand.kRight)-manette.getTriggerAxis(Hand.kLeft), lift));
    

    chooser.addOption("Pyramide Jaune", pyramideJaune);
    chooser.addOption("Pyramide Vert", pyramideVert);
    chooser.setDefaultOption("!!!!Trajet vide - À changer!!!", trajetVide);
    
  
    SmartDashboard.putData(chooser);
  }

  private void configureButtonBindings() {

    //Preset hauteur-longueur pour le bras
    new JoystickButton(manette, Button.kX.value).whenPressed(new ParalleleHauteurLongueur(60, 0, lift, bras));//1er étage pyramide
    new JoystickButton(manette, Button.kA.value).whenPressed(new ParalleleHauteurLongueur(210, 1000, lift, bras));//2e étage pyramide
    new JoystickButton(manette, Button.kB.value).whenPressed(new ParalleleHauteurLongueur(370, 2800, lift, bras));//3e étage pyramide
    
    //Commandes pour la pince
    new JoystickButton(manette, Button.kBumperRight.value).whenHeld(new Pincer(pince));//c'est un toggle, mais géré dans la commande plutôt que ici
    pinceSwitchTrigger.whenActive(new InstantCommand(pince::fermerPince, pince));

    //Fonctions non limitées pour mettre le bras à 0 entre les matchs
    new POVButton(manette, 0).whenHeld(new RunCommand(()-> lift.vitesseMoteurHauteur(0.3), lift)).whenReleased(new InstantCommand(lift::stop));
    new POVButton(manette, 180).whenHeld(new RunCommand(()-> lift.vitesseMoteurHauteur(-0.3), lift)).whenReleased(new InstantCommand(lift::stop));
    new POVButton(manette, 90).whenHeld(new RunCommand(()-> bras.vitesseMoteurLongueur(0.3), bras)).whenReleased(new InstantCommand(bras::stop));
    new POVButton(manette, 270).whenHeld(new RunCommand(()-> bras.vitesseMoteurLongueur(-0.3), bras)).whenReleased(new InstantCommand(bras::stop));
    new JoystickButton(manette, Button.kStart.value).whenHeld(new InstantCommand(bras::resetEncoder).andThen(new InstantCommand(lift::resetEncoder)));
    
    
}

  public Command getAutonomousCommand() {
    

    return new InstantCommand(()-> basePilotable.setBrake(true),basePilotable) //brake auto
    .andThen(new InstantCommand(()-> basePilotable.setRamp(0.1),basePilotable)) //ramp auto
    .andThen(new InstantCommand(basePilotable::resetGyro, basePilotable))
    .andThen(chooser.getSelected().withTimeout(14.8)/*à tester*/) // trajet avec limite de temps
    .andThen(new InstantCommand(()-> basePilotable.setBrake(false),basePilotable)) //coast teleop
    .andThen(new InstantCommand(()-> basePilotable.setRamp(Constants.rampTeleop),basePilotable));//ramp teleop
  }
}