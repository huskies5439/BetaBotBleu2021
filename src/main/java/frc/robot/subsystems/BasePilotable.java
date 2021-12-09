// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Units;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

public class BasePilotable extends SubsystemBase {

  private WPI_TalonFX moteurDroit = new WPI_TalonFX(4);
  private WPI_TalonFX moteurGauche = new WPI_TalonFX(3);
  private ADXRS450_Gyro gyro = new ADXRS450_Gyro();
  private DifferentialDrive drive = new DifferentialDrive(moteurGauche,moteurDroit);

  private double conversionMoteur;

  public BasePilotable() {

    resetEncoder();
    resetGyro();
    conversionMoteur = (1.0/2048)*(14.0/72)*(16.0/44)*Math.PI*Units.inchesToMeters(4); 


    setRamp(Constants.rampTeleop);
    setBrake(false);
    moteurDroit.setInverted(true);
    moteurGauche.setInverted(true);
  }

  @Override
  public void periodic() {

    /* SmartDashboard.putNumber("Vitesse Droite", getVitesseD());
    SmartDashboard.putNumber("Vitesse Gauche", getVitesseG());
    SmartDashboard.putNumber("Vitesse Moyenne", getVitesse()); 
    SmartDashboard.putNumber("Position Droite", getPositionD());
    SmartDashboard.putNumber("Position Gauche", getPositionG()); */
    SmartDashboard.putNumber("Position Moyenne", getPosition());
    SmartDashboard.putNumber("Angle", getAngle());
  }

  public void conduire(double vx, double vz) {
    
    drive.arcadeDrive(-0.8*vx, 0.65*vz);
  }

  public void autoConduire(double vx, double vz) {

    drive.arcadeDrive(vx, vz, false);
  }

  public void stop(){

    drive.arcadeDrive(0, 0);
  }

  public void setRamp(double ramp) {

    moteurDroit.configOpenloopRamp(ramp);
    moteurGauche.configOpenloopRamp(ramp);
  }

  public void setBrake(boolean isBrake) {

    if (isBrake) {
      moteurDroit.setNeutralMode(NeutralMode.Brake);
      moteurGauche.setNeutralMode(NeutralMode.Brake);
    }
    else {
      moteurDroit.setNeutralMode(NeutralMode.Coast);
      moteurGauche.setNeutralMode(NeutralMode.Coast);
    }
  }

  public double getPositionD() {
    
    return moteurGauche.getSelectedSensorPosition()*conversionMoteur;
  }

  public double getPositionG() {

    return -moteurDroit.getSelectedSensorPosition()*conversionMoteur;
  }

  public double getPosition() {

    return (getPositionG() + getPositionD() ) / 2.0;
  }
  
  public double getVitesseD() {

    return -moteurDroit.getSelectedSensorVelocity()*conversionMoteur*10;
  }

  public double getVitesseG() {

    return moteurGauche.getSelectedSensorVelocity()*conversionMoteur*10;
  }

  public double getVitesse() {

    return (getVitesseD() + getVitesseG()) / 2;
  }

  public void resetEncoder() {

    moteurDroit.setSelectedSensorPosition(0);
    moteurGauche.setSelectedSensorPosition(0);
  }

  public void resetGyro() {

    gyro.reset();
  }

  public double getAngle() {

    return gyro.getAngle();
  }
}