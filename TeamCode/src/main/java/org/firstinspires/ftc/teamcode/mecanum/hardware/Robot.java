package org.firstinspires.ftc.teamcode.mecanum.hardware;

import android.graphics.Bitmap;
import android.graphics.Color;

import org.firstinspires.ftc.teamcode.framework.userhardware.inputs.sensors.vision.ImageProcessor;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.DriveSegment;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.Path;
import org.firstinspires.ftc.teamcode.framework.util.RobotCallable;
import org.firstinspires.ftc.teamcode.mecanum.hardware.devices.arm.ArmController;
import org.firstinspires.ftc.teamcode.mecanum.hardware.devices.drive.DriveController;
import org.firstinspires.ftc.teamcode.mecanum.hardware.devices.intake.IntakeController;

import org.firstinspires.ftc.teamcode.mecanum.hardware.devices.lift.LiftController;
import org.firstinspires.ftc.teamcode.mecanum.opmodes.test.AbstractRobot;

import static org.firstinspires.ftc.teamcode.mecanum.hardware.Constants.*;

import static org.firstinspires.ftc.teamcode.framework.userhardware.DoubleTelemetry.LogMode.INFO;

public class Robot extends AbstractRobot {

    private ImageProcessor imageProcessor;

    public DriveController driver;
    public IntakeController intake;
    public ArmController arm;
    public LiftController lift;


    public Robot() {
        imageProcessor = new ImageProcessor(false);
        //telemetry = new DoubleTelemetry(super.telemetry, Dashboard.getInstance().getTelemetry(), new Logger(Dashboard.getCurrentOpMode()));

        driver = new DriveController();
        //intake = new IntakeController();
        arm = new ArmController();
        lift = new LiftController();
    }

    public void runDrivePath(Path path) {
        driver.runDrivePath(path);
    }

    public void strafe(double power, int delay) {
        driver.strafe(power);
        delay(delay);
        driver.stop();
    }


    public void driveToSegment(DriveSegment segment) {
        driver.driveToSegment(segment);
    }

    public String getSkyStonePositionThreeStones() {

        int LINEWIDTH = LINE_WIDTH;
        int threshold = THRESHOLD;
        // int height = YORIGIN + (BLOCKHEIGHT/2);
        int height = YORIGIN + HEIGHT;
        int x_left = XORIGIN;
        int x_center = x_left + BLOCKWIDTH;
        int x_right = x_center + BLOCKWIDTH;
        Bitmap image;

        image = imageProcessor.getImage();
        // Webcam- Width: 848 Height: 480
        //telemetry.addData(DoubleTelemetry.LogMode.INFO,"width: "+image.getWidth()+" height: "+image.getHeight());
        //telemetry.update();
        //delay(1000);

        // This is the Latest
        //ImageProcessor.drawBox(image, XORIGIN, YORIGIN, 3 * BLOCKWIDTH, BLOCKHEIGHT, LINEWIDTH, Color.rgb(0, 0, 225));
        //ImageProcessor.drawBox(image, XORIGIN + 30, YORIGIN + 10, 1, BLOCKHEIGHT - 50, LINEWIDTH = 4, Color.rgb(225, 0, 0));
        // ImageProcessor.drawBox(image, XORIGIN + 30+BLOCKWIDTH, YORIGIN + 10, 1, BLOCKHEIGHT - 50, LINEWIDTH = 4, Color.rgb(225, 0, 0));
        // ImageProcessor.drawBox(image, XORIGIN + 30+BLOCKWIDTH+BLOCKWIDTH, YORIGIN + 10, 1, BLOCKHEIGHT - 50, LINEWIDTH = 4, Color.rgb(225, 0, 0));
        //imageProcessor.setImage(image);

        String stonePosition;
       /* if (getLineAverage(image, x_left, height) < threshold) {
            stonePosition = "Left";
        } else if (getLineAverage(image, x_center, height) < threshold) {

            stonePosition = "Center";
        } else if (getLineAverage(image, x_right, height) < threshold) {

            stonePosition = "Right";
        } else {
            stonePosition = "No Sky Stone Found";
        }*/
        if (getPixelStripeAve(image, XORIGIN, YORIGIN) < threshold) {
            stonePosition = "Left";
        } else if (getPixelStripeAve(image, XORIGIN + BLOCKWIDTH, YORIGIN) < threshold) {

            stonePosition = "Center";
        } else if (getPixelStripeAve(image, XORIGIN + 2 * BLOCKWIDTH, YORIGIN) < threshold) {

            stonePosition = "Right";
        } else {
            stonePosition = "No Sky Stone Found";
        }
        telemetry.addData(INFO, "left box  " + getPixelStripeAve(image, XORIGIN, YORIGIN));
        telemetry.addData(INFO, "center box  " + getPixelStripeAve(image, XORIGIN + BLOCKWIDTH, YORIGIN));
        telemetry.addData(INFO, "right box  " + getPixelStripeAve(image, XORIGIN + 2 * BLOCKWIDTH, YORIGIN));
        telemetry.update();
        ImageProcessor.drawBox(image, XORIGIN, YORIGIN, 3 * BLOCKWIDTH, BLOCKHEIGHT, LINEWIDTH, Color.rgb(0, 0, 225));
        ImageProcessor.drawBox(image, x_left + 10, YORIGIN + 5, BLOCKWIDTH - 20, BLOCKHEIGHT - 10, LINEWIDTH, Color.rgb(225, 0, 0));
        //delay(500);
        ImageProcessor.drawBox(image, x_center + 10, YORIGIN + 5, BLOCKWIDTH - 20, BLOCKHEIGHT - 10, LINEWIDTH, Color.rgb(225, 0, 0));
        //delay(500);
        ImageProcessor.drawBox(image, x_right + 10, YORIGIN + 5, BLOCKWIDTH - 20, BLOCKHEIGHT - 10, LINEWIDTH, Color.rgb(225, 0, 0));
        imageProcessor.setImage(image);

        //ImageProcessor.drawBox(image, x_left, YORIGIN + 20, 1, BLOCKHEIGHT - 50, LINEWIDTH, Color.rgb(225, 0, 0));
        //ImageProcessor.drawBox(image, x_center, YORIGIN + 20, 1, BLOCKHEIGHT - 50, LINEWIDTH, Color.rgb(225, 0, 0));
        //ImageProcessor.drawBox(image, x_right, YORIGIN + 20, 1, BLOCKHEIGHT - 50, LINEWIDTH, Color.rgb(225, 0, 0));

        return stonePosition;
    }

    public int getLineAverage(Bitmap image, int x, int y) {
        int sum = 0;
        for (int i = 0; i < (BLOCKHEIGHT - 50); i++)
            sum += Color.red(image.getPixel(x, y + i));
        //telemetry.addData(DoubleTelemetry.LogMode.INFO, sum);
        //telemetry.update();
        return sum / (BLOCKHEIGHT - 50);
    }

    public int getPixelStripeAve(Bitmap image, int x, int y) {

        int stripeWidth = BLOCKWIDTH - 20;
        int stripeHeight = BLOCKHEIGHT - 10;
        int sum = 0;

        for (int i = 0; i < stripeWidth; i++) {
            for (int j = 0; j < stripeHeight; j++) {
                sum += Color.red(image.getPixel(x + i + 10, y + j + 5));
            }
        }
        return ((int) (sum / (stripeHeight * stripeWidth)));

    }

    public RobotCallable setArmDownCallable() {
        return () -> {
            RobotState.currentPath.pause();
            arm.setArmDownPosition();
            delay(300);
            arm.setGripperReleasePostion();
            delay(400);
            arm.setArmUpPosition();
            delay(300);
            arm.setGripperGripPosition();
            RobotState.currentPath.resume();

        };

    }

    public void setArmDown() {
        arm.setArmDownPosition();
    }

    public RobotCallable setArmUpCallable() {
        return () -> {
            arm.setArmUpPosition();
        };

    }

    public void setArmUp() {
        arm.setArmUpPosition();
    }

    public RobotCallable setGripperGripCallable() {
        return () -> {
            RobotState.currentPath.pause();
            arm.setGripperGripPosition();
            delay(500);
            arm.setArmUpPosition();
            // delay(500);
            RobotState.currentPath.resume();
        };

    }

    public void setGripperGrip() {
        arm.setGripperGripPosition();
    }

    public RobotCallable setGripperReleaseCallable() {
        return () -> {
            arm.setGripperReleasePostion();
        };

    }

    public void setGripperRelease() {
        arm.setGripperReleasePostion();
    }

    public void startRotation() {
        intake.startIntake();
    }

    public RobotCallable startRotationCallable() {
        return () -> startRotation();
    }

    public void stopRotating() {
        intake.stopIntake();
    }

    public RobotCallable stopRotatingCallable() {
        return () -> stopRotating();
    }

    public void toggleRotation() {
        intake.toggleRotation();
    }


    public RobotCallable toggleRotationCallable() {
        return () -> toggleRotation();
    }

    public void toggleConeyor() {
        intake.toggleConveyor();
    }


    public RobotCallable toggleConeyorCallable() {
        return () -> toggleConeyor();
    }

    public void toggleTilt(){


    }

    public void setDrivePowerAll(double FL, double FR, double BL, double BR) {
        driver.setDrivePowerAll(FL, FR, BL, BR);
    }


    public void runTestPurePursuit() {
        driver.testPurePursuit();
    }

    public void stop() {
        driver.stop();
    }

    //    public int getPixelNineAve(Bitmap image, int x, int y) {
//
//        int sum = 0;
//
//        for (int i = 0; i < 3; i++) {
//            for (int j = 0; j < 3; j++) {
//                sum = sum + (Color.red(image.getPixel(x - 1 + i, y - 1 + j)));
//            }
//        }
//        return sum / 9;
//        //return Color.red(image.getPixel(x , y ));
//    }
//
//    public int getFour(Bitmap image, int x, int y) {
//
//        int sum = 0;
//        int yoffset = 20;
//        int xoffset = 130;
//
//        sum = getPixelNineAve(image, x, y - yoffset);
//        sum = sum + getPixelNineAve(image, x, y + yoffset);
//        sum = sum + getPixelNineAve(image, x + xoffset, y - yoffset);
//        sum = sum + getPixelNineAve(image, x + xoffset, y + yoffset);
//
//        return sum / 4;
//        //return Color.red(image.getPixel(x , y ));
//    }


}
