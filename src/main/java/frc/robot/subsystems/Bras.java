// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Bras extends SubsystemBase {
  private DoubleSolenoid pince  = new DoubleSolenoid(0,1);
  private WPI_TalonSRX moteurHauteur = new WPI_TalonSRX(15);
  private WPI_TalonSRX moteurLongueur = new WPI_TalonSRX(16);
  private Encoder encoderHauteur = new Encoder(2,3);
  private Encoder encoderLongueur = new Encoder(0,1);


  public Bras() {
    moteurHauteur.setInverted(true);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Position Longueur", getPositionL());
    SmartDashboard.putNumber("Position Hauteur", getPositionH());
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

  public void vitesseMoteurLongueur(double vitesse) {
    moteurLongueur.set(ControlMode.PercentOutput, vitesse);

  }
  public double getPositionH(){
    return encoderHauteur.getDistance();
  }
  public double getPositionL(){
    return encoderLongueur.getDistance();
  }

}