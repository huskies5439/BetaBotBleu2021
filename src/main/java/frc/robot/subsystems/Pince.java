// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;


import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Pince extends SubsystemBase {
  private DoubleSolenoid pince = new DoubleSolenoid(0, 1);

  private DigitalInput switchGauche = new DigitalInput(4);//rouge
  private DigitalInput switchDroite = new DigitalInput(5);//bleu
  
  boolean ouvert;

  boolean switchOveride;
  

  public Pince() {
    ouvrirPince();
    switchOveride=false;
 
  }

  @Override
  public void periodic() {
    //SmartDashboard.putBoolean("LimitSwitch", getSwitch());
    //SmartDashboard.putBoolean("pince ouverte", getState());
    
  }

  public boolean getSwitch() {
    
    return (!switchGauche.get() || !switchDroite.get()) && !getOveride();
  
  }


  public void ouvrirPince() {

    pince.set(Value.kForward);
    ouvert=true;
  }

  public void fermerPince() {

    pince.set(Value.kReverse);
    ouvert=false;
  }

  public boolean getState(){
    return ouvert;
  }

  public boolean getOveride(){
    return switchOveride;
  }

  public void setOveride(){
    switchOveride = true;
  }

  public void releaseOveride(){
    switchOveride = false;
  }
}