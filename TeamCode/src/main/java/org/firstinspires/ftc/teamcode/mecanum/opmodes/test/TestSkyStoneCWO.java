package org.firstinspires.ftc.teamcode.mecanum.opmodes.test;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.framework.userhardware.inputs.sensors.IMU;
import org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractAuton;
import org.firstinspires.ftc.teamcode.framework.userhardware.DoubleTelemetry;
import org.firstinspires.ftc.teamcode.framework.userhardware.PIDController;
import org.firstinspires.ftc.teamcode.framework.userhardware.inputs.sensors.vision.ImageProcessor;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.DriveSegment;
import org.firstinspires.ftc.teamcode.mecanum.hardware.devices.Drive;
import org.upacreekrobotics.dashboard.Config;

import static org.firstinspires.ftc.teamcode.framework.userhardware.DoubleTelemetry.LogMode.INFO;

//import org.firstinspires.ftc.teamcode.framework.userhardware.inputs.sensors.vision.vuforia.Vuforia;

@Autonomous(name = "Test Sky CWO Auton", group = "New")

@Config
//  At 3 feet a stone is approximately  180x90 pixels

public class TestSkyStoneCWO extends AbstractAuton {

    ImageProcessor imageProcessor;
    private PIDController anglePID, straightPID, distancePID;
    private String skystoneLocation;
    Drive drive;
    ElapsedTime runtime;
    private IMU imu;

    @Override
    public void RegisterStates() {

    }

     public void InitLoop() {

         skystoneLocation = getSkyStonePositionThreeStones();

         telemetry.addData(DoubleTelemetry.LogMode.INFO, "Current SkyStone Position: " +skystoneLocation);
         telemetry.update();
     }

    @Override
    public void Init() {
        telemetry.addData(DoubleTelemetry.LogMode.INFO, "init");
        telemetry.update();
        imageProcessor = new ImageProcessor(false);
        drive = new Drive(hardwareMap, telemetry);
        anglePID = new PIDController(17, 0.1, 250, 0.3, 0.1);//D was 150
        //anglePID.setLogging(true);
        straightPID = new PIDController(50, 0.5, 50, 1, 0);
        distancePID = new PIDController(0.6, 0.1, 0, 2, 0.1);
        runtime = new ElapsedTime();

        telemetry.addData(DoubleTelemetry.LogMode.INFO, "init");
        telemetry.update();
    }

    @Override
    public void Run() {
        imageProcessor.shutdown();
        //drive.resetAngleToZero();

       // driveTheSegment(new DriveSegment("test drive", 24, 0.7, 20));

        switch (skystoneLocation) {
            case "RIGHT":
                goLeft(1500, -0.3);
                driveTheSegment(new DriveSegment("test drive", 24, 0.3, 20));
                drive.setPowers(0, 0);
                break;
            case "LEFT":
                goLeft(4000, 0.3);
                driveTheSegment(new DriveSegment("test drive", 24, 0.3, 20));
                drive.setPowers(0, 0);
                break;
            case "CENTER":
                goLeft(2000, 0.3);
                driveTheSegment(new DriveSegment("test drive", 24, 0.3, 20));
                drive.setPowers(0, 0);
                break;
            default:
                break;
        }

        while (opModeIsActive()) {

        }
    }

    public String getSkyStonePositionThreeStones() {

        String skystonePosition;
        Bitmap image;

        int XORIGIN = 205;
        int YORIGIN = 330;
        int BLOCKWIDTH = 295;
        int BLOCKHEIGHT = 148;
        int LINEWIDTH = 10;
        int SWATHXOFFSET = 30;
        int SWATHYOFFSET = 10;
        int SWATHYHEIGHT = 98;
        int THRESHOLD = 100;

        int threshold = THRESHOLD;
        int height = YORIGIN+SWATHYOFFSET;
        int x_left = XORIGIN + SWATHXOFFSET;
        int x_center = x_left + BLOCKWIDTH;
        int x_right = x_center + BLOCKWIDTH;

        image = imageProcessor.getImage();

        if (getLineAverage(image, x_left, height, SWATHYHEIGHT) < threshold)
            skystonePosition = "LEFT";
        else if (getLineAverage(image, x_center, height, SWATHYHEIGHT) < threshold)
            skystonePosition = "CENTER";
        else if (getLineAverage(image, x_right, height, SWATHYHEIGHT) < threshold)
            skystonePosition = "RIGHT";
        else
            skystonePosition = "No Sky Stone Found";

        ImageProcessor.drawBox(image, XORIGIN, YORIGIN, 3 * BLOCKWIDTH, BLOCKHEIGHT, LINEWIDTH, Color.rgb(0, 0, 225));
        ImageProcessor.drawBox(image, XORIGIN, YORIGIN, 3 * BLOCKWIDTH, BLOCKHEIGHT, LINEWIDTH, Color.rgb(0, 0, 225));
        ImageProcessor.drawBox(image, XORIGIN + SWATHXOFFSET, YORIGIN + SWATHYOFFSET, 1, BLOCKHEIGHT - 50, LINEWIDTH = 4, Color.rgb(225, 0, 0));
        ImageProcessor.drawBox(image, XORIGIN + SWATHXOFFSET+BLOCKWIDTH, YORIGIN + SWATHYOFFSET, 1, BLOCKHEIGHT - 50, LINEWIDTH = 4, Color.rgb(225, 0, 0));
        ImageProcessor.drawBox(image, XORIGIN + SWATHYOFFSET+BLOCKWIDTH+BLOCKWIDTH, YORIGIN + SWATHYOFFSET, 1, BLOCKHEIGHT - 50, LINEWIDTH = 4, Color.rgb(225, 0, 0));
        imageProcessor.setImage(image);

        return skystonePosition;
    }

    public int getLineAverage(Bitmap image, int x, int y, int swathyheight) {
        int sum = 0;
        for (int i = 0; i < swathyheight; i++)
            sum += Color.red(image.getPixel(x, y + i));
        sum /=  swathyheight;
        telemetry.addData(DoubleTelemetry.LogMode.INFO,sum);
        telemetry.update();
        return sum;
    }

    public void driveTheSegment(DriveSegment segment) {
        telemetry.addData(INFO, "Drive Segment is starting");
        telemetry.addData(INFO, "");
        telemetry.addData(INFO, "");
        telemetry.addData(INFO, "");

        double DRIVE_COUNTS_PER_INCH = 2520.0/58.0;
        double difference;
        double baseHeading = 0;
        double leftPower = 0.25, rightPower = 0.23;
        double power = 0.5;
        double angle_servo_gain = 0.003;
        double prop_gain = 0.02;

        double distance = segment.getDistance(), speed = segment.getSpeed(), angle = baseHeading;
       // if (segment.getAngle() != null) angle = segment.getAngle();
        int error = segment.getError();

        baseHeading = angle;

        straightPID.reset(); //Resets the PID values in the PID class to make sure we do not have any left over values from the last segment
        distancePID.reset();
        straightPID.setMinimumOutput(0);

        int position = (int) (distance * DRIVE_COUNTS_PER_INCH); //

        double turn = 0;
        speed = range(speed);

        drive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        telemetry.addData(DoubleTelemetry.LogMode.INFO, "Heading  " + drive.getActualHeadingDegrees());
        telemetry.update();

        //drive.setPositionP(5);
        //telemetry.update();

        double currentHeading;

        int loop = 0;

        drive.setPowers(leftPower, rightPower);
        while(-drive.getLeftPosition()<(position-error)){

            loop++;

            telemetry.addData(DoubleTelemetry.LogMode.INFO, "Heading  " + drive.getActualHeadingDegrees());
            telemetry.update();

            difference = drive.getActualHeadingDegrees();

            leftPower = leftPower + difference*angle_servo_gain;
            rightPower = rightPower - difference*angle_servo_gain;
            drive.setPowers(leftPower+difference*prop_gain, rightPower-difference*prop_gain);
            if (-drive.getLeftPosition()>(position/2))
                 drive.setPowers(leftPower/2,rightPower/2);
             else
                 drive.setPowers(leftPower, rightPower);


           /* if (segment.isDone()) {
                drive.setPowers(0, 0);
                return;
            }
            if (!segment.isRunning()) {
                drive.setPowers(0, 0);
                continue;
            }*/

            //currentHeading = getHeading();

            //power = range(distancePID.output(position, (-drive.getRightPosition() - drive.getLeftPosition()) / 2.0));
            //telemetry.addData(DoubleTelemetry.LogMode.INFO,"power " + power +"  Turbo3");
            //telemetry.update();
           /* if (angle - currentHeading > 180) {
                turn = anglePID.output(angle, 360 + currentHeading);
            } else if (currentHeading - angle > 180) {
                turn = anglePID.output(angle, angle - (360 - (currentHeading - angle)));
            } else {
                turn = anglePID.output(angle, currentHeading);
            }*/

            /*if (power > 0) {
                leftPower = range(power * (speed - turn));
                rightPower = range(power * (speed + turn));
            } else {
                leftPower = range(power * (speed + turn));
                rightPower = range(power * (speed - turn));
            }*/

        }

        drive.setPowers(0.0, 0.0);
        telemetry.addData(DoubleTelemetry.LogMode.INFO, "Heading  " + drive.getActualHeadingDegrees());
        telemetry.addData(DoubleTelemetry.LogMode.INFO, -drive.getLeftPosition());
        telemetry.addData(DoubleTelemetry.LogMode.INFO, -drive.getRightPosition());
        telemetry.update();
        /*telemetry.addData(INFO, "Average loop time for drive: " + runtime.milliseconds() / loop);
        telemetry.addData(INFO, "Left encoder position: " + drive.getLeftPosition() + "  Right encoder position: " + drive.getRightPosition());
        telemetry.addData(INFO, "Final angle: " + getHeading());
        telemetry.update();*/

        // drive.setPowers(0, 0);
        //drive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void goForward(int time, double power){
        drive.setDrivePowerAll(power, power, power, power);
        delay(time);
        drive.setDrivePowerAll(0,0,0,0);
    }

    public void goLeft(int time, double power){

        drive.setDrivePowerAll(-power, power, power, -power);
        delay(time);
        drive.setPowers(0.0,0.0);
    }

    public double getHeading() {
        if (imu == null) return 0.0;
        return imu.getHeading();
    }

    public boolean atPosition(double x, double y, double error) {
        double upperRange = x + error;
        double lowerRange = x - error;

        return y >= lowerRange && y <= upperRange;
    }

    private synchronized double range(double val) {
        if (val < -1) val = -1;
        if (val > 1) val = 1;
        return val;
    }
    /*public int getPixelNineAve(Bitmap image, int x, int y) {

        int sum = 0;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                sum = sum + (Color.red(image.getPixel(x - 1 + i, y - 1 + j)));
            }
        }
        return sum / 9;
        //return Color.red(image.getPixel(x , y ));
    }

    public int getFour(Bitmap image, int x, int y) {

        int sum = 0;
        int yoffset = 20;
        int xoffset = 130;

        sum = getPixelNineAve(image, x, y - yoffset);
        sum = sum + getPixelNineAve(image, x, y + yoffset);
        sum = sum + getPixelNineAve(image, x + xoffset, y - yoffset);
        sum = sum + getPixelNineAve(image, x + xoffset, y + yoffset);

        return sum / 4;
        //return Color.red(image.getPixel(x , y ));
    }*/

}
