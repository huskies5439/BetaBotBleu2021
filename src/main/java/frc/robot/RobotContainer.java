// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.Descendre;
import frc.robot.commands.LongueurBras;
import frc.robot.commands.Monter;
import frc.robot.commands.Pincer;
import frc.robot.subsystems.BasePilotable;
import frc.robot.subsystems.Bras;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  private final BasePilotable basePilotable = new BasePilotable();
  private final Bras bras = new Bras();
  
XboxController manette = new XboxController(0);  

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    configureButtonBindings();

    basePilotable.setDefaultCommand(new RunCommand(()-> basePilotable.conduire(manette.getY(Hand.kLeft), manette.getX(Hand.kRight)), basePilotable));
    bras.setDefaultCommand(new RunCommand(()-> bras.vitesseMoteurLongueur(manette.getTriggerAxis(Hand.kRight)-manette.getTriggerAxis(Hand.kLeft)), bras));
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    new JoystickButton(manette, Button.kA.value).toggleWhenPressed(new Pincer(bras));
    new JoystickButton(manette, Button.kBumperRight.value).whenHeld(new Monter(bras));
    new JoystickButton(manette, Button.kBumperLeft.value).whenHeld(new Descendre(bras));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return new RunCommand(()->basePilotable.autoConduire(3, 3), basePilotable);
  }
}
