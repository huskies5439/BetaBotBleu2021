// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Bras extends SubsystemBase {
 private DoubleSolenoid pince  = new DoubleSolenoid(0,1);
 private WPI_TalonSRX moteurHauteur = new WPI_TalonSRX(15);
  public Bras() {}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
  public void ouvrirPince() {
    pince.set(Value.kReverse);
  }

  public void fermerPince() {
    pince.set(Value.kForward);
  }

  public void vitesseMoteurHauteur(double vitesse) {
      moteurHauteur.set(ControlMode.PercentOutput, vitesse);

  }

}
