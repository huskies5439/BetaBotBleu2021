// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Pince extends SubsystemBase {
  private DoubleSolenoid pince  = new DoubleSolenoid(0,1);
 
  public Pince() {}

  @Override
  public void periodic() {}

  public void ouvrirPince() {

    pince.set(Value.kReverse);
  }

  public void fermerPince() {
    
    pince.set(Value.kForward);
  }
}