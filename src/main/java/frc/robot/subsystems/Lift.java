// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Lift extends SubsystemBase {
  /** Creates a new Lift. */
  private WPI_TalonSRX moteurHauteur = new WPI_TalonSRX(15);
  private Encoder encoderHauteur = new Encoder(2,3);
  
  public Lift() {
    moteurHauteur.setInverted(true);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Position Hauteur", getPositionH());
  }
  public void vitesseMoteurHauteur(double vitesse) {
    moteurHauteur.set(ControlMode.PercentOutput, vitesse);

}
  public double getPositionH(){
  return encoderHauteur.getDistance();
}

}
