package org.firstinspires.ftc.teamcode.mecanum.hardware;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.framework.userhardware.DoubleTelemetry;
import org.firstinspires.ftc.teamcode.mecanum.hardware.devices.TestMotor.TestMotor;
import org.firstinspires.ftc.teamcode.mecanum.hardware.devices.drive.Drive;

import static org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractOpMode.delay;
import static org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractOpMode.getHardwareMap;
import static org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractOpMode.getTelemetry;
import static org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractOpMode.telemetry;


//This is a test robot class to use with CWOTest op mode

public class BaseRobot {

    private double xPosition;           //X position of robot
    private double yPosition;
    private double speed;           // speed of the robot
    private Drive drive;            // accesses the motors that drive the robot wheels
    private TestMotor testMotor;

    public BaseRobot(){

        drive = new Drive(getHardwareMap(), getTelemetry());
        //testMotor = new TestMotor(getHardwareMap());

    }

    public void setTMPower(double speed){
        testMotor.setTestMotorPower(0.6);
    }

    public void testMotorForward(double speed){
        setTMPower(speed);
        delay(1000);
        setTMPower(0.0);
    }

    public void straightPower(double speed){

        drive.setDrivePowerTwo(speed, speed);
    }

    public void setSpeed(double value){
        if (value > 1.0) value = 1.0;
        speed = value; }

    public double getSpeed(){return speed;}

    public double getXPosition(){return xPosition;}

    public void driveStraight(double location, double speed){

        telemetry.addData("firststep  ", location);
        telemetry.addData("more  ", getLocation());
        telemetry.update();
        if ((location - getLocation())>0) {
            telemetry.addData("here");
            telemetry.update();
            drive.setDrivePowerTwo(speed, speed);
            while (getLocation() < location) { }
            drive.setDrivePowerTwo(0.0, 0.0);
        }
       else {
            drive.setDrivePowerTwo(-speed, -speed);
            while (getLocation() > location) { }
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

    public int getBackLeftLocation(){
        return drive.getBackLeftPosition();
    }

    public int getBackRightLocation(){
        return drive.getBackRightPosition();
    }

    public double getLocation(){
        return drive.getStraightPosition();
    }

    public void testDrive(double speed){

        drive.setDrivePowerTwo(speed, speed);
        delay(1000);
        drive.setDrivePowerTwo(0.0, 0.0);
    }

    public void setPower(double leftSpeed, double rightSpeed){
        drive.setDrivePowerTwo(leftSpeed,rightSpeed);
    }


}
