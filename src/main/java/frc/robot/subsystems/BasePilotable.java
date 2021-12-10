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

  //Initialisation des moteurs et du drive
  private WPI_TalonFX moteurDroit = new WPI_TalonFX(4);
  private WPI_TalonFX moteurGauche = new WPI_TalonFX(3);
  private DifferentialDrive drive = new DifferentialDrive(moteurGauche,moteurDroit);
  
  private double conversionEncodeur;
  
  //Initialisation du gyro
  private ADXRS450_Gyro gyro = new ADXRS450_Gyro();

  

  public BasePilotable() {
    //Reset des senseurs
    resetEncoder();
    resetGyro();
    
    //Conversion des encodeurs des Falcon en mètres
    //2048 clics par tour d'encodeur, pignon du Falcon 14 dents fait tourner une gear 72 dents. 
    //Un sproket 16 dents fait tourner une gear 44 dents. La roue à un diamètre de 4 pouces
    conversionEncodeur = (1.0/2048)*(14.0/72)*(16.0/44)*Math.PI*Units.inchesToMeters(4); 

    //Configuration des moteurs (ramps, brake, inversion)
    setRamp(Constants.rampTeleop);
    setBrake(false);
    moteurDroit.setInverted(true);//Afin que le robot avance avec une vitesse positive
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

  //Conduire teleop, avec limite de vitesse
  public void conduire(double vx, double vz) {
    
    drive.arcadeDrive(-0.8*vx, 0.65*vz);
  }

  //Conduire teleop. Les vitesse positives correspondent à avancer et tourner horaire.
  //On désactive la fonction "mise au carrée" du arcadeDrive pour avoir un système linéaire
  public void autoConduire(double vx, double vz) {

    drive.arcadeDrive(vx, vz, false);
  }

  //Toujours pratique un stop au lieu de caller conduire(0,0)
  public void stop(){

    drive.arcadeDrive(0, 0);
  }

  //Ramp : le nombre de secondes avant qu'un changement de vitesse s'effectue
  public void setRamp(double ramp) {

    moteurDroit.configOpenloopRamp(ramp);
    moteurGauche.configOpenloopRamp(ramp);
  }

  //Lorsque le robot passe à une vitesse de 0 (Idle), est-ce qu'il roule librement ou brake.
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

  //Obtenir position et vitesse des moteurs.
  public double getPositionD() {
    
    return moteurGauche.getSelectedSensorPosition()*conversionEncodeur;
  }

  public double getPositionG() {

    return -moteurDroit.getSelectedSensorPosition()*conversionEncodeur;//négatif pour que avancer = positif
  }

  public double getPosition() {

    return (getPositionG() + getPositionD() ) / 2.0;
  }
  
  public double getVitesseD() {

    return -moteurDroit.getSelectedSensorVelocity()*conversionEncodeur*10;//x10 car les encodeurs des Falcon donne des clics par 100 ms.
  }

  public double getVitesseG() {

    return moteurGauche.getSelectedSensorVelocity()*conversionEncodeur*10;
  }

  public double getVitesse() {

    return (getVitesseD() + getVitesseG()) / 2;
  }

  public void resetEncoder() {

    moteurDroit.setSelectedSensorPosition(0);
    moteurGauche.setSelectedSensorPosition(0);
  }

  //Fonction gyro
  public void resetGyro() {

    gyro.reset();
  }

  public double getAngle() {

    return gyro.getAngle();
  }
}