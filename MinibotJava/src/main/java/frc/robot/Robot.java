/*************************************************************
 * 
 * this is the code just to drive jake do not change this code, 
 * changing this code can result in possible situation of not 
 * being able to recover from confusing or disfunctional code
 * 
 *************************************************************/

package frc.robot;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Joystick;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

public class Robot extends TimedRobot
{
  public static Joystick ps4drive = new Joystick(0);


  public static CANSparkMax frontR = new CANSparkMax(3, MotorType.kBrushless);
  public static CANSparkMax frontL = new CANSparkMax(4, MotorType.kBrushless);
  public static CANSparkMax backR = new CANSparkMax(1, MotorType.kBrushless);
  public static CANSparkMax backL = new CANSparkMax(2, MotorType.kBrushless);

  SpeedControllerGroup leftDrive = new SpeedControllerGroup(frontL, backL);
  SpeedControllerGroup rightDrive = new SpeedControllerGroup(frontR, backR);

  SpeedControllerGroup leftTurn = new SpeedControllerGroup(frontL, backL);
  SpeedControllerGroup rightTurn = new SpeedControllerGroup(frontR, backR);

  DifferentialDrive fullDrive = new DifferentialDrive(leftDrive, rightDrive);
  DifferentialDrive turnDrive = new DifferentialDrive(leftTurn, rightTurn);
  
  private Command autonomousCommand = null;

  private SendableChooser<Command> chooser = new SendableChooser<>();

  private final Command AutoOption = new Auto(this);

  public Command getAutonomousCommand() 
   {
     return chooser.getSelected();
   }




  public static CANEncoder backLEnc = new CANEncoder(backL);
  public static CANEncoder backREnc = new CANEncoder(backR);
  public static CANEncoder frontREnc = new CANEncoder(frontR);
  public static CANEncoder frontLEnc = new CANEncoder(frontL);


  

  double controlMultiplyer;
  double bumperMultiplier=.5;
  double speedInvert = 1;
  int speedlock = 1;


public double timer;
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

    chooser.setDefaultOption("AutoOption0", AutoOption);
    chooser.addOption("AutoOption1", AutoOption);
    chooser.addOption("AutoOption2", AutoOption);
    chooser.addOption("AutoOption3", AutoOption);


  }



  @Override
  public void robotPeriodic()
  { 
    
    controlMultiplyer = speedInvert*bumperMultiplier;
    double leftDriveVar = (ps4drive.getRawAxis(1))*controlMultiplyer;
    double rightDriveVar = (ps4drive.getRawAxis(5))*controlMultiplyer;
    
    /*
    fullDrive.tankDrive((ps4drive.getRawAxis(4)+(controlMultiplyer*ps4drive.getRawAxis(1))), 
                       ((-1*ps4drive.getRawAxis(4))+(controlMultiplyer*ps4drive.getRawAxis(1))),//weiner
                       true);
    */
    fullDrive.tankDrive(leftDriveVar,rightDriveVar,true);
    




    //this locks the speed for the bot so it cannot be changed
    if(ps4drive.getRawButtonPressed(3))
    {
      speedlock *= -1;
      System.out.println(speedlock);
    }
    //this is to invert the direction of the robot
    if(ps4drive.getRawButtonPressed(4))
    {
      speedInvert *= -1;
    }

    //this is the code to change the speed of the robot
    if(speedlock==1)
    {
        /*
        note:sometimes when increasing, the first decrease will also increase
        but after will not, vice versa as well
        */
        if(ps4drive.getRawButtonPressed(5) && bumperMultiplier > 0)
        {
          bumperMultiplier-=.05;
          System.out.println(controlMultiplyer);
        }
        if(ps4drive.getRawButtonPressed(6)&&bumperMultiplier<1)
        {
          bumperMultiplier+=.05;
          System.out.println(controlMultiplyer);
        }
    }

    

  }
// If going to fail
// Dont;
  @Override
  public void autonomousInit() 
  {
    autonomousCommand = this.getAutonomousCommand();

    if (autonomousCommand != null) 
    {
      autonomousCommand.start();
    }

  }

  @Override
  public void autonomousPeriodic() 
  {

    //CommandScheduler.getInstance().run();

    //int decision = 2;
    /***
     * decision table (eventually make switch case)
     * 1 is forward     1 go - 3 stop
     * 2 is backward    1 go - 3 stop
     * 3 is spin CW     3 go - 3 stop
     * 4 is spin CCW    3 go - 3 stop
     * 5 is complex move
     * 
     * 
     *  
     ***/



    /*
     switch (decision)
     {
       
        //one:forward
        case 1:
        {
          auto.AutonomousForward(1000, .3);
          auto.AutonomousStop(3000);
        }
        break;
        //two:backward
        case 2:
        {
          auto.AutonomousForward(1000, -.3);
          auto.AutonomousStop(3000);
        }
        break;
        //three:clockwise
        case 3:
        {
          auto.AutonomousSpin(3000, .3);
          auto.AutonomousStop(3000);
        }
        break;
        //four counter clockwise
        case 4:
        {
          auto.AutonomousSpin(3000, -.3);
          auto.AutonomousStop(3000);
        }
        break;

        case 5:
        {

        }
        break;




        default:
        {

        }
        break;
     }
    if(decision==1)
    {
      auto.AutonomousForward(1000, .3);
      auto.AutonomousStop(3000);
    }

    if(decision==2)
    {
      auto.AutonomousSpin(3000, 0.3);
      auto.AutonomousStop(3000);
    }
    */
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
