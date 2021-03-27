// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Units;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType; 

public class BasePilotable extends SubsystemBase {

  private CANSparkMax neod = new CANSparkMax(23,MotorType.kBrushless);
  private CANSparkMax neog = new CANSparkMax(22,MotorType.kBrushless);
  private ADXRS450_Gyro gyro = new ADXRS450_Gyro();
  private DifferentialDrive drive = new DifferentialDrive(neog,neod);

  private double conversionEncoder;

  public BasePilotable() {
    resetEncoder();
    resetGyro();
    conversionEncoder=(1.0/6)*(16.0/44)*Math.PI*Units.inchesToMeters(4); //à calculer (diamètre de roue)
    setConversionFactors(conversionEncoder);

    setRamp(0.5);
    setNeutralMode(IdleMode.kCoast);
    neod.setInverted(true);//à vérifier
    neog.setInverted(true);//à vérifier
  }

  @Override
  public void periodic() {
  /*SmartDashboard.putNumber("Vitesse Droite", getVitesseD());
    SmartDashboard.putNumber("Vitesse Gauche", getVitesseG());
    SmartDashboard.putNumber("Vitesse Moyenne", getVitesse());*/
    SmartDashboard.putNumber("Position Droite", getPositionD());
    SmartDashboard.putNumber("Position Gauche", getPositionG());
    SmartDashboard.putNumber("Position Moyenne", getPosition());
    SmartDashboard.putNumber("Angle", getAngle());
  }

  public void conduire(double vx, double vz) {
    drive.arcadeDrive(-vx, 0.7*vz); // à configurer
  }

  public void autoConduire(double gauche, double droit){
    neog.setVoltage(gauche);
    neod.setVoltage(-droit);
  }

  public void setRamp(double ramp) {
    neod.setOpenLoopRampRate(ramp);
    neog.setOpenLoopRampRate(ramp);
  }

  public void setNeutralMode(IdleMode mode) {
    neod.setIdleMode(mode);
    neog.setIdleMode(mode);
  }

  public double getPositionD() {
    return -neod.getEncoder().getPosition();
  }

  public double getPositionG() {
    return neog.getEncoder().getPosition();
  }

  public double getPosition() {
    return (getPositionG()+getPositionD())/2.0;
  }
  
  public double getVitesseD() {
    return -neod.getEncoder().getVelocity();
  }

  public double getVitesseG() {
    return neog.getEncoder().getVelocity();
  }

  public double getVitesse() {
    return (getVitesseD() + getVitesseG()) / 2;
  }

  public void resetEncoder() {
    neod.getEncoder().setPosition(0);
    neog.getEncoder().setPosition(0);
  }

  public void resetGyro() {
    gyro.reset();
  }

  public double getAngle() {
    return gyro.getAngle();
  }

  public void setConversionFactors(double facteur) {
    neod.getEncoder().setPositionConversionFactor(facteur);
    neog.getEncoder().setPositionConversionFactor(facteur);
    neod.getEncoder().setVelocityConversionFactor(facteur/60.00);
    neog.getEncoder().setVelocityConversionFactor(facteur/60.00);
  }
}