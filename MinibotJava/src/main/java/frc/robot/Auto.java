package frc.robot;

public class AutonomousCommand extends Command
{

    public AutonomousForward()
    {
        addSequential(fullDrive.tankDrive(0.3, 0.3, true));
    }
}