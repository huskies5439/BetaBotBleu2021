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
  private final Cremaillere cremailliere = new Cremaillere();
  private final Pince pince = new Pince();

  //Trajet autonomes
  private final Command pyramideJaune = new TrajetAutoPyramide(1, basePilotable, bras, cremailliere, pince);
  private final Command pyramideVert = new TrajetAutoPyramide(-1, basePilotable, bras, cremailliere, pince);
  private final Command trajetVide = new WaitCommand(14);
  private final SendableChooser <Command> chooser = new SendableChooser<>();

  //Créer le trigger correspondant aux limit switch de la pince
  private final Trigger pinceSwitchTrigger = new Trigger(pince::getSwitch);
  
  XboxController manette = new XboxController(0);  

  public RobotContainer() {

    configureButtonBindings();

    //Conduire : Joystick gauche haut-bas pour avancer, Joystick droit gauche-droite pour tourner
    basePilotable.setDefaultCommand(new RunCommand(()-> basePilotable.conduire(manette.getY(Hand.kLeft), manette.getX(Hand.kRight)), basePilotable));
    //Allonger le bras manuellement (peu utile grâce aux presets) : Joystick Droit haut-bas avec une énorme deadband
    bras.setDefaultCommand(new LongueurBras(()-> -manette.getY(Hand.kRight), bras));
    //Monter descendre la crémaillière du bras : Triggers
    cremailliere.setDefaultCommand(new HauteurBras(()-> manette.getTriggerAxis(Hand.kRight)-manette.getTriggerAxis(Hand.kLeft), cremailliere));
    
    //Mettre les trajets autonomes dans le chooser
    chooser.addOption("Pyramide Jaune", pyramideJaune);
    chooser.addOption("Pyramide Vert", pyramideVert);
    //Le trajet par défaut est vide car si on oublie de sélectionner le bon trajet, on veut ne pas bouger afin d'éviter un accident
    chooser.setDefaultOption("!!!!Trajet vide - À changer!!!", trajetVide);
    
  
    SmartDashboard.putData(chooser);
  }

  private void configureButtonBindings() {

    //Preset hauteur-longueur pour le bras
    new JoystickButton(manette, Button.kX.value).whenPressed(new ParalleleHauteurLongueur(60, 0, cremailliere, bras));//1er étage pyramide
    new JoystickButton(manette, Button.kA.value).whenPressed(new ParalleleHauteurLongueur(210, 1000, cremailliere, bras));//2e étage pyramide
    new JoystickButton(manette, Button.kB.value).whenPressed(new ParalleleHauteurLongueur(370, 2800, cremailliere, bras));//3e étage pyramide
    
    //Commandes pour la pince
    new JoystickButton(manette, Button.kBumperRight.value).whenHeld(new Pincer(pince));//c'est un toggle, mais géré dans la commande plutôt que ici
    pinceSwitchTrigger.whenActive(new InstantCommand(pince::fermerPince, pince));//Détection automatique du tube avec les limit switchs

    //Fonctions non limitées pour mettre le bras à 0 entre les matchs. Utilise le D-pad
    new POVButton(manette, 0).whenHeld(new RunCommand(()-> cremailliere.vitesseMoteurHauteur(0.3), cremailliere)).whenReleased(new InstantCommand(cremailliere::stop));
    new POVButton(manette, 180).whenHeld(new RunCommand(()-> cremailliere.vitesseMoteurHauteur(-0.3), cremailliere)).whenReleased(new InstantCommand(cremailliere::stop));
    new POVButton(manette, 90).whenHeld(new RunCommand(()-> bras.vitesseMoteurLongueur(0.3), bras)).whenReleased(new InstantCommand(bras::stop));
    new POVButton(manette, 270).whenHeld(new RunCommand(()-> bras.vitesseMoteurLongueur(-0.3), bras)).whenReleased(new InstantCommand(bras::stop));
    new JoystickButton(manette, Button.kStart.value).whenHeld(new InstantCommand(bras::resetEncoder).andThen(new InstantCommand(cremailliere::resetEncoder)));
    
    
}

  public Command getAutonomousCommand() {
    
    //On effectue certaines opérations avant le trajet et après le trajet
    return new InstantCommand(()-> basePilotable.setBrake(true),basePilotable) //brake auto
    .andThen(new InstantCommand(()-> basePilotable.setRamp(0.1),basePilotable)) //ramp auto
    .andThen(new InstantCommand(basePilotable::resetGyro, basePilotable)) //reset gyro
    .andThen(chooser.getSelected().withTimeout(14.8)) // trajet avec limite de temps
    .andThen(new InstantCommand(()-> basePilotable.setBrake(false),basePilotable)) //coast teleop
    .andThen(new InstantCommand(()-> basePilotable.setRamp(Constants.rampTeleop),basePilotable));//ramp teleop
  }
}