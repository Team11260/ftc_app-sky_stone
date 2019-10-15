package org.firstinspires.ftc.teamcode.mecanum.opmodes.test;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.SparseIntArray;
import android.widget.ImageView;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractAuton;
import org.firstinspires.ftc.teamcode.framework.userhardware.DoubleTelemetry;
//import org.firstinspires.ftc.teamcode.framework.userhardware.inputs.sensors.vision.vuforia.Vuforia;
import org.firstinspires.ftc.teamcode.framework.userhardware.inputs.sensors.IMU;
import org.firstinspires.ftc.teamcode.framework.userhardware.inputs.sensors.vision.ImageProcessor;
import org.firstinspires.ftc.teamcode.framework.userhardware.inputs.sensors.vision.vuforia.VuforiaImpl;
import org.firstinspires.ftc.teamcode.mecanum.hardware.devices.Drive;
import org.upacreekrobotics.dashboard.Config;

import java.util.HashMap;

@Autonomous(name = "BlockFind Auton", group = "New")

@Config
//  At 3 feet a stone is approximately  180x90 pixels

public class BlockFindAuton extends AbstractAuton {
    ImageProcessor imageProcessor;
    Robot robot;
    IMU imu;
    Drive drive;


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
        imu = new IMU(hardwareMap);
        drive = new Drive(hardwareMap, telemetry);

        // imageProcessor = new ImageProcessor(false);

        telemetry.addData(DoubleTelemetry.LogMode.INFO, "init");
        telemetry.update();
    }

    @Override
    public void Run() {

        telemetry.addData(DoubleTelemetry.LogMode.INFO, "run started" + opModeIsActive());
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
        turnTo(30);
        while (opModeIsActive()) {

            /*telemetry.addData(DoubleTelemetry.LogMode.INFO, Color.red(image.getPixel(XORIGIN + 40, 355)));
            telemetry.addData(DoubleTelemetry.LogMode.INFO, Color.green(image.getPixel(XORIGIN + 40, 355)));
            telemetry.addData(DoubleTelemetry.LogMode.INFO, Color.blue(image.getPixel(XORIGIN + 40, 355)));
            */

            //telemetry.addData(DoubleTelemetry.LogMode.INFO, robot.getSkyStonePositionThreeStones());
            //telemetry.update();
            telemetry.addData(DoubleTelemetry.LogMode.INFO, imu.getHeading());
            telemetry.update();

        }

    }

    public void turnTo(double angle) {
        double currentangle = imu.getHeading();
        double threshold = 4.0;
        double error = currentangle - angle;
        double p = 0.2;

        if (angle < 0) {
            p = -p;
        }
        while (Math.abs(imu.getHeading()-angle) > threshold) {
            drive.setDrivePowerAll(p, -p,p, -p);
            telemetry.addData(DoubleTelemetry.LogMode.INFO, imu.getHeading());
            telemetry.update();
        }
        drive.setDrivePowerAll(0, 0, 0, 0);
        telemetry.addData(DoubleTelemetry.LogMode.INFO, imu.getHeading());
        telemetry.update();
    }


    public void Strafe() {


    }
}
