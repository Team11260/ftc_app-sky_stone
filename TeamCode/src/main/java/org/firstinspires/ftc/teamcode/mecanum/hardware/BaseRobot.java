package org.firstinspires.ftc.teamcode.mecanum.hardware;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.framework.userhardware.DoubleTelemetry;
import org.firstinspires.ftc.teamcode.mecanum.hardware.devices.drive.Drive;

import static org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractOpMode.delay;
import static org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractOpMode.getHardwareMap;
import static org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractOpMode.getTelemetry;


//This is a test robot class to use with CWOTest op mode

public class BaseRobot {

    private double xPosition;           //X position of robot
    private double yPosition;
    private double speed;           // speed of the robot
    private Drive drive;            // accesses the motors that drive the robot wheels

    public BaseRobot(){

        drive = new Drive(getHardwareMap(), getTelemetry());

    }

    public void setSpeed(double value){
        if (value > 1.0) value = 1.0;
        speed = value; }

    public double getSpeed(){return speed;}

    public double getXPosition(){return xPosition;}

    public void driveStraight(double location, double speed){
        if ((location- drive.getStraightPosition())>0) {
            drive.setDrivePowerTwo(speed, 0.8 * speed);
            while (drive.getStraightPosition() < location) { }
            drive.setDrivePowerTwo(0.0, 0.0);
        }
        if ((location- drive.getStraightPosition())<0) {
            drive.setDrivePowerTwo(-speed, -0.8 * speed);
            while (drive.getStraightPosition() > location) { }
            drive.setDrivePowerTwo(0.0, 0.0);
        }
    }

    public void turn(double angle, double speed){
        if ((angle-drive.getActualHeadingDegrees()) > 0) {
            drive.setDrivePowerTwo(-speed, speed);
            while (drive.getActualHeadingDegrees() < angle) { }
            drive.setDrivePowerTwo(0.0, 0.0);
        }
        if ((angle-drive.getActualHeadingDegrees()) < 0) {
            drive.setDrivePowerTwo(speed, -speed);
            while (drive.getActualHeadingDegrees() < angle) { }
            drive.setDrivePowerTwo(0.0, 0.0);
        }
    }

    public void turnLeft(){ turn(90, 0.5); }

    public void turnRight(){ turn(-90, 0.5);}

    public void launch(){}

    public void dropWobble(){}

    public void testDrive(double speed){

        drive.setDrivePowerTwo(speed, speed);
        delay(3000);
        drive.setDrivePowerTwo(0.0, 0.0);
    }


}
