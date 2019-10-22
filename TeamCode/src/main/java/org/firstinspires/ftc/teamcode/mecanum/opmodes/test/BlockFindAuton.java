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
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.DriveSegment;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.Path;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.TurnSegment;
import org.firstinspires.ftc.teamcode.mecanum.hardware.devices.Drive;
import org.upacreekrobotics.dashboard.Config;

import java.text.DecimalFormat;
import java.util.HashMap;

@Autonomous(name = "BlockFind Auton", group = "New")

@Config
//  At 3 feet a stone is approximately  180x90 pixels

public class BlockFindAuton extends AbstractAuton {
    ImageProcessor imageProcessor;
    Robot robot;
    boolean dashBoardSwitch = true;
    DecimalFormat DF;

    DriveSegment segment;
    TurnSegment turnSegment;
    Path test;
    public static double distance = 12;


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
        DF=new DecimalFormat("#.##");
        // imageProcessor = new ImageProcessor(false);
        segment = new DriveSegment("test",distance,0.4,100);
        //turnSegment = new TurnSegment("turn",60,0.5,5,500);
        test = new Path("test");
        test.addSegment(segment);
        //test.addSegment(turnSegment);

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
        //robot.driveToSegment((DriveSegment)test.getNextSegment());
        robot.runDrivePath(test);
        //turnTo(30);
        while (false && opModeIsActive()) {

            /*telemetry.addData(DoubleTelemetry.LogMode.INFO, Color.red(image.getPixel(XORIGIN + 40, 355)));
            telemetry.addData(DoubleTelemetry.LogMode.INFO, Color.green(image.getPixel(XORIGIN + 40, 355)));
            telemetry.addData(DoubleTelemetry.LogMode.INFO, Color.blue(image.getPixel(XORIGIN + 40, 355)));
            */

            //telemetry.addData(DoubleTelemetry.LogMode.INFO, robot.getSkyStonePositionThreeStones());
            //telemetry.update();
            }

    }

    /*public void turnTo(double angle) {
        double currentangle = imu.getHeading();
        double threshold = 4.0;
        double error = currentangle - angle;
        double p = 0.5;
        double ratio = 1;

        if (angle < 0) {
            p = -p;
        }
        while (Math.abs(imu.getHeading()-angle) > threshold) {
            ratio = Math.abs(imu.getHeading() - angle)/angle;
            drive.setDrivePowerAll(ratio*p, ratio*-p,ratio*p, ratio*-p);
            if (p*ratio>0.5) break;
            telemetry.addData(DoubleTelemetry.LogMode.INFO, imu.getHeading(),DF.format(p*ratio));
            telemetry.update();
        }
        drive.setDrivePowerAll(0, 0, 0, 0);
        telemetry.addData(DoubleTelemetry.LogMode.INFO, imu.getHeading());
        telemetry.update();
    }*/


    public void Strafe() {


    }
    @Override
    public void Stop(){
        robot.driver.stop();
    }
}
