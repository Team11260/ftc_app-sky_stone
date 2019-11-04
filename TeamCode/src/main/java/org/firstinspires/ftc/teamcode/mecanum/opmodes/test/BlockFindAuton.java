package org.firstinspires.ftc.teamcode.mecanum.opmodes.test;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.SparseIntArray;
import android.widget.ImageView;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
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

import static org.firstinspires.ftc.teamcode.mecanum.hardware.Constants.backUp;
import static org.firstinspires.ftc.teamcode.mecanum.hardware.Constants.collectBlock;
import static org.firstinspires.ftc.teamcode.mecanum.hardware.Constants.collectCenterSkyStone;
import static org.firstinspires.ftc.teamcode.mecanum.hardware.Constants.forwardDrive;
import static org.firstinspires.ftc.teamcode.mecanum.hardware.Constants.lastDrive;

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

        switch (robot.getSkyStonePositionThreeStones()) {
            case "Right":

                break;

            case "Left":

                break;

            case "Center":

                break;

            default:

        }

        telemetry.addData(DoubleTelemetry.LogMode.INFO, "segment distance");
        //robot.runDrivePath(path);
        /*arm.setArmDownPosition();
        arm.setGripperReleasePostion();
        robot.runDrivePath(collectCenterSkyStone);

        robot.strafe(-0.4,400);

        robot.runDrivePath(collectBlock);
        arm.setGripperGripPosition();
        delay(500);
        arm.setArmUpPosition();
        robot.runDrivePath(backUp);
        robot.strafe(-0.4,4000);
        robot.runDrivePath(forwardDrive);
        arm.setArmDownPosition();
        delay(500);
        robot.runDrivePath(lastDrive);*/
        arm.setArmPinPosition();
        arm.setGripperGripPosition();
        delay(500);
        robot.runDrivePath(lastDrive);


        robot.stop();

    }


    @Override
    public void Stop() {
        robot.driver.stop();
    }
}
