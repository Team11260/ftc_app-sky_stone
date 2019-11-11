package org.firstinspires.ftc.teamcode.mecanum.hardware;

import android.graphics.Bitmap;
import android.graphics.Color;

import org.firstinspires.ftc.teamcode.framework.userhardware.inputs.sensors.vision.ImageProcessor;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.DriveSegment;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.Path;
import org.firstinspires.ftc.teamcode.mecanum.hardware.devices.arm.ArmController;
import org.firstinspires.ftc.teamcode.mecanum.hardware.devices.drive.DriveController;
import org.firstinspires.ftc.teamcode.mecanum.hardware.devices.intake.IntakeController;

import org.firstinspires.ftc.teamcode.mecanum.opmodes.test.AbstractRobot;

public class Robot extends AbstractRobot {

    private ImageProcessor imageProcessor;

    int BLOCKHEIGHT = 130;

    public DriveController driver;
    public IntakeController intake;
    public ArmController arm;


    public Robot() {
        imageProcessor = new ImageProcessor(false);
        //telemetry = new DoubleTelemetry(super.telemetry, Dashboard.getInstance().getTelemetry(), new Logger(Dashboard.getCurrentOpMode()));

        driver = new DriveController();
        //intake = new IntakeController();
        arm = new ArmController();
    }

    public void runDrivePath(Path path) {
        driver.runDrivePath(path);
    }

    public void strafe(double power, int delay){
        driver.strafe(power);
        delay(delay);
        driver.stop();
    }



    public void driveToSegment(DriveSegment segment) {
        driver.driveToSegment(segment);
    }

    public String getSkyStonePositionThreeStones() {
        int XORIGIN = 180;
        int YORIGIN = 260;
        int BLOCKWIDTH = 250;
        int LINEWIDTH = 10;
        int threshold = 100;
        // int height = YORIGIN + (BLOCKHEIGHT/2);
        int height = YORIGIN + 10;
        int x_left = XORIGIN + 30;
        int x_center = x_left + BLOCKWIDTH;
        int x_right = x_center + BLOCKWIDTH;
        Bitmap image;

        image = imageProcessor.getImage();
        ImageProcessor.drawBox(image, XORIGIN, YORIGIN, 3 * BLOCKWIDTH, BLOCKHEIGHT, LINEWIDTH, Color.rgb(0, 0, 225));
        // ImageProcessor.drawBox(image, XORIGIN + 30, YORIGIN + 10, 1, BLOCKHEIGHT - 50, LINEWIDTH = 4, Color.rgb(225, 0, 0));
        // ImageProcessor.drawBox(image, XORIGIN + 30+BLOCKWIDTH, YORIGIN + 10, 1, BLOCKHEIGHT - 50, LINEWIDTH = 4, Color.rgb(225, 0, 0));
        // ImageProcessor.drawBox(image, XORIGIN + 30+BLOCKWIDTH+BLOCKWIDTH, YORIGIN + 10, 1, BLOCKHEIGHT - 50, LINEWIDTH = 4, Color.rgb(225, 0, 0));
        imageProcessor.setImage(image);

        String stonePosition;
        if (getLineAverage(image, x_left, height) < threshold) {
            stonePosition = "Left";
        } else if (getLineAverage(image, x_center, height) < threshold) {

            stonePosition = "Center";
        } else if (getLineAverage(image, x_right, height) < threshold) {

            stonePosition = "Right";
        } else {
            stonePosition = "No Sky Stone Found";
        }

        telemetry.update();
        ImageProcessor.drawBox(image, XORIGIN + 60, YORIGIN + 10, 1, BLOCKHEIGHT - 50, LINEWIDTH = 4, Color.rgb(225, 0, 0));
        ImageProcessor.drawBox(image, XORIGIN + 60 + BLOCKWIDTH, YORIGIN + 10, 1, BLOCKHEIGHT - 50, LINEWIDTH = 4, Color.rgb(225, 0, 0));
        ImageProcessor.drawBox(image, XORIGIN + 60 + BLOCKWIDTH + BLOCKWIDTH, YORIGIN + 10, 1, BLOCKHEIGHT - 50, LINEWIDTH = 4, Color.rgb(225, 0, 0));

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

    public void startRotation() {
        intake.startIntake();
    }


    public void stopRotating() {
        intake.stopIntake();
    }

    public void toggleRotation(){
        intake.toggleRotation();
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
