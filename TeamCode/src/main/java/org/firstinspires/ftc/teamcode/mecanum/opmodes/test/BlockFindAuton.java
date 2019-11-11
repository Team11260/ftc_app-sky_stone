package org.firstinspires.ftc.teamcode.mecanum.opmodes.test;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.SparseIntArray;
import android.widget.ImageView;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractAuton;
import org.firstinspires.ftc.teamcode.framework.userhardware.DoubleTelemetry;
//import org.firstinspires.ftc.teamcode.framework.userhardware.inputs.sensors.vision.vuforia.Vuforia;
import org.firstinspires.ftc.teamcode.framework.userhardware.inputs.sensors.IMU;
import org.firstinspires.ftc.teamcode.framework.userhardware.inputs.sensors.vision.ImageProcessor;
import org.firstinspires.ftc.teamcode.framework.userhardware.inputs.sensors.vision.vuforia.VuforiaImpl;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.DriveSegment;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.Path;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.TurnSegment;
import org.firstinspires.ftc.teamcode.mecanum.hardware.devices.ArmController;
import org.firstinspires.ftc.teamcode.mecanum.hardware.devices.Drive;
import org.upacreekrobotics.dashboard.Config;

import java.text.DecimalFormat;
import java.util.HashMap;

import static org.firstinspires.ftc.teamcode.mecanum.hardware.Constants.avoidRobot;
import static org.firstinspires.ftc.teamcode.mecanum.hardware.Constants.backUp;
import static org.firstinspires.ftc.teamcode.mecanum.hardware.Constants.collectBlock;
import static org.firstinspires.ftc.teamcode.mecanum.hardware.Constants.collectCenterSkyStone;
import static org.firstinspires.ftc.teamcode.mecanum.hardware.Constants.collectLeftSkyStone;
import static org.firstinspires.ftc.teamcode.mecanum.hardware.Constants.collectRightSkyStone;
import static org.firstinspires.ftc.teamcode.mecanum.hardware.Constants.dragFoundation;
import static org.firstinspires.ftc.teamcode.mecanum.hardware.Constants.forwardDrive;

@Autonomous(name = "BlockFind Auton", group = "New")

@Config
//  At 3 feet a stone is approximately  180x90 pixels

public class BlockFindAuton extends AbstractAuton {
    ImageProcessor imageProcessor;
    Robot robot;
    boolean dashBoardSwitch = true;
    DecimalFormat DF;

    ArmController arm;

    public static double distance = 24;


    @Override
    public void RegisterStates() {


    }

    public void InitLoop() {


        telemetry.addData(DoubleTelemetry.LogMode.INFO, robot.getSkyStonePositionThreeStones());

        telemetry.update();

    }

    @Override
    public void Init() {

        robot = new Robot();
        //drive = new Drive(hardwareMap, telemetry);
        DF = new DecimalFormat("#.##");
        // imageProcessor = new ImageProcessor(false);
        arm = new ArmController();

        telemetry.addData(DoubleTelemetry.LogMode.INFO, "init");
        telemetry.update();
    }

    @Override
    public void Run() {

        telemetry.addData(DoubleTelemetry.LogMode.INFO, "run started");
        telemetry.update();

        //arm.setArmDownPosition();
        //arm.setGripperReleasePostion();


        //robot.driver.setPowerAll(0.3,0.3,0.3,0.3);
        //robot.driver.setPower(0.3,0.3*1.1);
       // delay(2000);
        //robot.driver.setPower(0.0,0.0);
        robot.runDrivePath(collectLeftSkyStone);

        /*switch (robot.getSkyStonePositionThreeStones()) {
            case "Right":

                robot.runDrivePath(collectRightSkyStone);
                arm.setGripperGripPosition();
                delay(500);
                arm.setArmUpPosition();
                robot.runDrivePath(backUp);
                robot.strafe(-0.4,2700);

                break;

            case "Left":

                robot.runDrivePath(collectLeftSkyStone);
                arm.setGripperGripPosition();
                delay(500);
                arm.setArmUpPosition();
                robot.runDrivePath(backUp);
                robot.strafe(-0.4,3300);

                break;

            case "Center":

                robot.runDrivePath(collectCenterSkyStone);
                arm.setGripperGripPosition();
                delay(500);
                arm.setArmUpPosition();
                robot.runDrivePath(backUp);
                robot.strafe(-0.4,3000);

                break;

            default:

        }*/

        telemetry.addData(DoubleTelemetry.LogMode.INFO, "segment distance");
        //robot.runDrivePath(path);
        //robot.runDrivePath(collectCenterSkyStone);

        //robot.strafe(-0.4,400);

        //robot.runDrivePath(collectBlock);
        //robot.runDrivePath(forwardDrive);
        //robot.driver.setPower(0.2,0.2);
        //delay(300);
        //robot.stop();

        //arm.setArmDownPosition();
        //arm.setGripperReleasePostion();
        //arm.setArmBackPosition();
       // robot.runDrivePath(backUp);
        //robot.strafe(0.4,3000);
//        robot.runDrivePath(dragFoundation);
//        robot.strafe(0.3, 1200);
//        robot.runDrivePath(avoidRobot);
//        robot.strafe(0.3, 1200);
     // robot.driver.setPower(-0.3,-0.3);
       // delay(500);


        robot.stop();

    }


    @Override
    public void Stop() {
        robot.driver.stop();
    }
}
