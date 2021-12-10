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


  //Limit switch pour la pince automatique
  private DigitalInput switchGauche = new DigitalInput(4);//fil rouge
  private DigitalInput switchDroite = new DigitalInput(5);//fil bleu
  
  //Pour suivre l'état de la pince dans Pincer()
  boolean ouvert;

  //Pour désactiver la fonction automatique des switchs lors du pinçage manuel
  boolean switchOveride;
  

  public Pince() {
    ouvrirPince();
    switchOveride=false;
 
  }

  @Override
  public void periodic() {
   
    
  }

  //Fonctions contrôle manuel
  //la variable ouvert sert à faire le toggle dans la commande Pincer() plutôt que dans le RobotContainer
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
  
  //Controle automatique de la pince
  public boolean getSwitch() {
    
    return (!switchGauche.get() || !switchDroite.get()) && !getOveride();
  
  }

  //Overide de la pince auto lorsqu'on utilise le controle manuel
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