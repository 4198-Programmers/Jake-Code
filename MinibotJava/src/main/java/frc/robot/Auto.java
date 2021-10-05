package frc.robot;

import java.util.concurrent.Delayed;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class Auto extends Command
{
    //double timer = timeSinceInitialized();

    private Robot robot;
        public Auto(Robot robot) {
            super();
            this.robot = robot;
        }

    public void AutonomousForward(int time, double speed)
    {
        double start = System.currentTimeMillis();

        while(timerBoolean(start, time))
        {
            robot.fullDrive.tankDrive(speed, speed, true);
        }
        
        isFinished();
    }

    public void AutonomousStop(int time)
    {
        double start = System.currentTimeMillis();

        while(timerBoolean(start, time))
        {
            robot.fullDrive.tankDrive(0, 0, true);
        }
        
        isFinished();
    }

    public void AutonomousSpin(int time, double speed)
    {
        double start = System.currentTimeMillis();

        while(timerBoolean(start, time))
        {
            robot.fullDrive.tankDrive(speed, -speed, true);
        }

        isFinished();
    }


    private boolean timerBoolean(double startTimeMS, double DurationMS) {
        

        double delayTime = startTimeMS + DurationMS;
        
        if(System.currentTimeMillis() <= delayTime)
        {
            return true;
        }
        else
        {
            return false;
        }
        /** 
        * this method takes the current time (start Time)
        * and a duration (DurationMS) and checks to see if the 
        * time has passed the durration
        * this is essentially a timer with the alarm
        * turning the boolean to false.
        **/
    }

    @Override
    protected boolean isFinished(){
        return false;
    }
}