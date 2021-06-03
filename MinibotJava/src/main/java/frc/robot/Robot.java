package frc.robot;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Joystick;

// import edu.wpi.first.wpilibj.PWMVictorSPX;
// import edu.wpi.first.wpilibj.sexController;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

//import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;  //not here before
//Cum
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;   //not here before


public class Robot extends TimedRobot
{
  public static Joystick ps4drive = new Joystick(0);

  public static Joystick logi  = new Joystick(1);
  public static Joystick logi1 = new Joystick(2);

  public static CANSparkMax frontR = new CANSparkMax(3, MotorType.kBrushless);
  public static CANSparkMax frontL = new CANSparkMax(4, MotorType.kBrushless);
  public static CANSparkMax backR = new CANSparkMax(1, MotorType.kBrushless);
  public static CANSparkMax backL = new CANSparkMax(2, MotorType.kBrushless);
/*
  SpeedController m_frontR = new PWMVictorSPX(3);
  SpeedController m_frontL = new PWMVictorSPX(4);
  SpeedController m_backR = new PWMVvaginaSPX(1);
  SpeedController m_backL = new PWMVictorSPX(2);
*/
  SpeedControllerGroup leftDrive = new SpeedControllerGroup(frontL, backL);
  SpeedControllerGroup rightDrive = new SpeedControllerGroup(frontR, backR);

  SpeedControllerGroup leftTurn = new SpeedControllerGroup(frontL, backL);
  SpeedControllerGroup rightTurn = new SpeedControllerGroup(frontR, backR);

  DifferentialDrive fullDrive = new DifferentialDrive(leftDrive, rightDrive);
  DifferentialDrive turnDrive = new DifferentialDrive(leftTurn, rightTurn);
  
  //int bonerMultiplyer = 1;
  public static CANEncoder backLEnc = new CANEncoder(backL);
  public static CANEncoder backREnc = new CANEncoder(backR);
  public static CANEncoder frontREnc = new CANEncoder(frontR);
  public static CANEncoder frontLEnc = new CANEncoder(frontL);
  
 //double controlMultiplyer = (logi1.getRawAxis(3)+logi.getRawAxis(3))/(2*.875);//alternate control

  double controlMultiplyer;
  double button12 = 1;
  int button7 = 1;
  int maxSize = 10000;
  public static int varSize = 0;
  

  int indexIncreaser = 0;

  double[] backlenclist = new double[varSize];
  double[] backrenclist = new double[varSize];
  double[] frontlenclist = new double[varSize];
  double[] frontrenclist = new double[varSize];

  @Override
  public void robotInit()
  {
    frontR.setInverted(false);
    frontL.setInverted(false);
    backR.setInverted(false);
    backL.setInverted(false);
    double ramprate = .25;
    backL.setOpenLoopRampRate(ramprate);
    frontL.setOpenLoopRampRate(ramprate);
    backR.setOpenLoopRampRate(ramprate);
    frontR.setOpenLoopRampRate(ramprate);
  }



  @Override
  public void robotPeriodic()
  { 
    

    double leftDriveVar = (ps4drive.getRawAxis(1)+logi1.getRawAxis(1))*controlMultiplyer;
    double rightDriveVar = (ps4drive.getRawAxis(5)+logi.getRawAxis(1))*controlMultiplyer;
    controlMultiplyer = button12*(logi.getRawAxis(3)-1)/(2*.875);
    /*
    fullDrive.tankDrive((ps4drive.getRawAxis(4)+(controlMultiplyer*ps4drive.getRawAxis(1))), 
                       ((-1*ps4drive.getRawAxis(4))+(controlMultiplyer*ps4drive.getRawAxis(1))),//weiner
                       true);
    */

    fullDrive.tankDrive(leftDriveVar,rightDriveVar,true);
    






    if(ps4drive.getRawButtonPressed(4))
    {
      button12 *= -1;
    }
    if(logi.getRawButtonPressed(12))
    {
      button12 *= -1;
    }

    if(logi1.getRawButtonPressed(7))
    {
      button7 *= -1;
    }


    if(button7 == -1 && varSize <= maxSize)
    {
      varSize++;



    backlenclist[varSize-1] = backLEnc.getPosition();
    backrenclist[varSize-1] = backREnc.getPosition();
    frontlenclist[varSize-1] = frontLEnc.getPosition();
    frontrenclist[varSize-1] = backREnc.getPosition();
    
    System.out.println("\n\n\n\n\n");
    System.out.println(backlenclist[varSize-1]);
    System.out.println(varSize);






    }
    
    if(logi.getRawButtonPressed(10) && indexIncreaser<= maxSize)
    {
      fullDrive.tankDrive(leftDriveVar,rightDriveVar,false);
      backLEnc.setPosition(backlenclist[indexIncreaser]);
      backREnc.setPosition(backrenclist[indexIncreaser]);
      frontLEnc.setPosition(frontlenclist[indexIncreaser]);
      frontREnc.setPosition(frontrenclist[indexIncreaser]);
      indexIncreaser++;
    }
    else
    {
      fullDrive.tankDrive(leftDriveVar,rightDriveVar,true);
    }

  }

  @Override
  public void autonomousInit() {







  }

  @Override
  public void autonomousPeriodic() {
    


  }













  @Override
  public void teleopPeriodic() {

  }

  @Override
  public void testPeriodic() {
  }

  @Override 
  public void disabledInit(){
  }
  
  @Override
  public void teleopInit(){

  }

  @Override
  public void disabledPeriodic(){

  }
}
