package org.firstinspires.ftc.teamcode.mecanum.opmodes.auton;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractAuton;
import org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractOpMode;
import org.firstinspires.ftc.teamcode.framework.userhardware.DoubleTelemetry;
import org.firstinspires.ftc.teamcode.mecanum.hardware.devices.arm.ArmController;
import org.firstinspires.ftc.teamcode.mecanum.hardware.devices.drive.Drive;

@Autonomous(name = "CG Auton", group = "New")

public class CGAuton extends AbstractAuton {
    HardwareMap hardwareMap;
    DoubleTelemetry telemetry;
    Drive drive;
    //DriveController driver;
    ArmController arm;


    public CGAuton () {

    }

    public void Init(){

        telemetry = AbstractOpMode.getTelemetry();
        hardwareMap = AbstractOpMode.getHardwareMap();
        arm = new ArmController();
        //driver = new DriveController();
        drive = new Drive(hardwareMap, telemetry);
        drive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        drive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        arm.setArmDownPosition();
        arm.setGripperReleasePosition();
    }

    public void RegisterStates(){}

    public void Run(){
        double distance = 0.0;
        double target = -30.0;
        double target2 = -21.0;
        double target3 = 80.0;
        double speed = 0.25;

        strafe(-speed);
        while(distance > target){
            distance = drive.getStrafePosition();
        }
        drive.stop();

        arm.setGripperGripPosition();
        delay(500);
        arm.setArmAutonPosition();
        delay(1000);

        strafe(speed);
        while(distance < target2){
            distance = drive.getStrafePosition();
        }
        drive.stop();
        delay(500);

        drive.setDrivePowerAll(speed,speed,speed,speed);
        while(distance < target3) {
            distance = drive.getStraightPosition();
        }
        drive.stop();
        delay(500);

        strafe(-speed);
        while(distance > target){
            distance = drive.getStrafePosition();
        }
        drive.stop();

        arm.setArmDownPosition();
        delay(500);
        arm.setGripperReleasePosition();
        delay(500);
        arm.setArmAutonPosition();
    }



    public void strafe(double power) {
        drive.setDrivePowerAll(power, -power,-power,power);
    }

}
