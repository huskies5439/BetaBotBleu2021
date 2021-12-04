// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Bras extends SubsystemBase {
  
  private WPI_TalonSRX moteurLongueur = new WPI_TalonSRX(16);
  
  private Encoder encoderLongueur = new Encoder(0,1);

  public Bras() {
    moteurLongueur.setNeutralMode(NeutralMode.Brake);
  }

  @Override
  public void periodic() {

    SmartDashboard.putNumber("Position Longueur", getPositionL());
  }
  
  public void vitesseMoteurLongueur(double vitesse) {

    moteurLongueur.set(ControlMode.PercentOutput, vitesse);
  }

  public void stop() {

    vitesseMoteurLongueur(0);
  }
  
  public double getPositionL() {

    return encoderLongueur.getDistance();
  }

  public void resetEncoder() {
    
    encoderLongueur.reset();
  }
}