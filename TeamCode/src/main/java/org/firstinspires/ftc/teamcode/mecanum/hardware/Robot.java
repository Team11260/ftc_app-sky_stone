package org.firstinspires.ftc.teamcode.mecanum.hardware;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.provider.ContactsContract;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractAuton;
import org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractOpMode;
import org.firstinspires.ftc.teamcode.framework.userhardware.DoubleTelemetry;
import org.firstinspires.ftc.teamcode.framework.userhardware.inputs.sensors.vision.ImageProcessor;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.Path;
import org.firstinspires.ftc.teamcode.framework.util.RobotCallable;
import org.firstinspires.ftc.teamcode.mecanum.hardware.devices.arm.ArmController;

import org.firstinspires.ftc.teamcode.mecanum.hardware.devices.dragger.DraggerController;
import org.firstinspires.ftc.teamcode.mecanum.hardware.devices.drive.DriveController;
import org.firstinspires.ftc.teamcode.mecanum.hardware.devices.intake.IntakeController;

import org.firstinspires.ftc.teamcode.mecanum.hardware.devices.led.LedController;
import org.firstinspires.ftc.teamcode.mecanum.hardware.devices.lift.LiftController;
import org.firstinspires.ftc.teamcode.mecanum.hardware.devices.tapemeasure.TapeMeasureController;
import org.firstinspires.ftc.teamcode.mecanum.opmodes.test.AbstractRobot;

import static org.firstinspires.ftc.teamcode.mecanum.hardware.Constants.*;

import static org.firstinspires.ftc.teamcode.framework.userhardware.DoubleTelemetry.LogMode.INFO;

public class Robot extends AbstractRobot {


    private ImageProcessor imageProcessor;
    public DriveController driver;
    public IntakeController intake;
    public ArmController arm;
    public LiftController lift;
    public TapeMeasureController tapeMeasure;

    public DraggerController dragger;
    public LedController led;
    public Bitmap image;

    public Robot() {

        //telemetry = new DoubleTelemetry(super.telemetry, Dashboard.getInstance().getTelemetry(), new Logger(Dashboard.getCurrentOpMode()));
        driver = new DriveController();
        intake = new IntakeController();
        arm = new ArmController();
        lift = new LiftController();
        dragger = new DraggerController();
        tapeMeasure = new TapeMeasureController();
        led = new LedController();
    }


    public void runDrivePath(Path path) {
        driver.runDrivePath(path);

    }

    public void strafe(double power, int delay) {
        driver.strafe(power);
        delay(delay);
        driver.stop();
    }

    public void update() {
        driver.update();
    }


    public String getSkyStonePositionThreeStones(int loopcount, boolean isRed) {


        if (imageProcessor == null)
            imageProcessor = new ImageProcessor(false);


        int xorigin = isRed ? RED_XORIGIN : BLUE_XORIGIN;
        int yorigin = isRed ? RED_YORIGIN : BLUE_YORIGIN;
        int LINEWIDTH = LINE_WIDTH;
        int threshold = THRESHOLD;
        // int height = YORIGIN + (BLOCKHEIGHT/2);
        int height = yorigin + HEIGHT;
        int x_left = xorigin;
        int x_center = x_left + BLOCKWIDTH;
        int x_right = x_center + BLOCKWIDTH;
        String stonePosition = "Center";


        image = imageProcessor.getImage();


        int lowest = 999999;
        String[] positions = {"Left", "Center", "Right"};
        for (int i = 0; i < 3; i++) {
            int pos = getPixelStripeAve(xorigin + i * BLOCKWIDTH, yorigin);


            if (pos < lowest) {
                lowest = pos;
                stonePosition = positions[i];

            }

            if (AbstractAuton.isRunActive()) return null;
        }

        if (lowest == 999999) {
            telemetry.addData(INFO, "No SkyStone Found");
            telemetry.update();
        }


//        telemetry.addData(INFO, "left box  " + getPixelStripeAve(xorigin, yorigin));
//        telemetry.addData(INFO, "center box  " + getPixelStripeAve(xorigin + BLOCKWIDTH, yorigin));
//        telemetry.addData(INFO, "right box  " + getPixelStripeAve(xorigin + 2 * BLOCKWIDTH, yorigin));
        //telemetry.addData(INFO, "Front Third  "  +  getFrontThirdBlock(10, 320));
//        telemetry.update();

        ImageProcessor.drawBox(image, xorigin, yorigin, 3 * BLOCKWIDTH, BLOCKHEIGHT, LINEWIDTH, Color.rgb(0, 0, 225));
        ImageProcessor.drawBox(image, x_left + 10, yorigin + 5, BLOCKWIDTH - 20, BLOCKHEIGHT - 10, LINEWIDTH, Color.rgb(225, 0, 0));
        //delay(500);
        ImageProcessor.drawBox(image, x_center + 10, yorigin + 5, BLOCKWIDTH - 20, BLOCKHEIGHT - 10, LINEWIDTH, Color.rgb(225, 0, 0));
        //delay(500);
        ImageProcessor.drawBox(image, x_right + 10, yorigin + 5, BLOCKWIDTH - 20, BLOCKHEIGHT - 10, LINEWIDTH, Color.rgb(225, 0, 0));

        imageProcessor.setImage(image);


        return stonePosition;


    }


    public boolean isSixthStone() {
        if (imageProcessor == null)
            imageProcessor = new ImageProcessor(false);

        image = imageProcessor.getImage();


        int xorigin = 250;
        int yorigin = 90;



        int blockOne = getPixelStripeAveRun(xorigin+10,yorigin+5);

        int blockTwo = getPixelStripeAveRun(xorigin+10+BLOCKWIDTH,yorigin+5);

        boolean sixth = blockOne>=blockTwo;

        telemetry.addData(DoubleTelemetry.LogMode.INFO,"Block One: "+ blockOne + "\nBlock Two: "+blockTwo);
        telemetry.update();











        ImageProcessor.drawBoxRun(image, xorigin, yorigin, 2 * BLOCKWIDTH, BLOCKHEIGHT, 7, Color.rgb(0, 0, 255));

        ImageProcessor.drawBoxRun(image,xorigin+10,yorigin+5,10,BLOCKHEIGHT-10,5,Color.rgb(255,0,0));

        ImageProcessor.drawBoxRun(image,xorigin+10+BLOCKWIDTH,yorigin+5,10,BLOCKHEIGHT-10,5,Color.rgb(255,0,0));

        imageProcessor.setImage(image);



        return sixth;


    }


    public void imageShutDown() {
        imageProcessor.shutdown();
    }


    public static String place;

    public RobotCallable findLastStone() {
        return () -> {
            ElapsedTime runTime = new ElapsedTime();
            runTime.reset();
            while (runTime.milliseconds() < 5000)
                place = getSkyStonePositionThreeStones(0, true);
        };
    }

    public int getLineAverage(Bitmap image, int x, int y) {
        int sum = 0;
        for (int i = 0; i < (BLOCKHEIGHT - 50); i++)
            sum += Color.red(image.getPixel(x, y + i));
        //telemetry.addData(DoubleTelemetry.LogMode.INFO, sum);
        //telemetry.update();
        return sum / (BLOCKHEIGHT - 50);
    }

    public int getPixelStripeAve(int x, int y) {

        int stripeWidth = BLOCKWIDTH - 20;
        int stripeHeight = BLOCKHEIGHT - 10;
        int sum = 0;

        for (int i = 0; i < stripeWidth; i++) {
            for (int j = 0; j < stripeHeight / 3; j++) {
                if (AbstractOpMode.isRunActive()) return -1;
                sum += Color.red(image.getPixel(x + i + 10, y + 3 * j + 5));
            }
        }
        return ((int) ((3 * sum) / (stripeHeight * stripeWidth)));
    }


    public int getPixelStripeAveRun(int x, int y) {

        int stripeWidth = BLOCKWIDTH - 20;
        int stripeHeight = BLOCKHEIGHT - 10;
        int sum = 0;

        for (int i = 0; i < stripeWidth; i++) {
            for (int j = 0; j < stripeHeight / 3; j++) {
               // if (AbstractOpMode.isRunActive()) return -1;
                sum += Color.red(image.getPixel(x + i + 10, y + 3 * j + 5));
            }
        }
        return ((int) ((3 * sum) / (stripeHeight * stripeWidth)));
    }




    public int getFrontThirdBlock(int x, int y) {

        int stripeWidth = 200;
        int stripeHeight = 150;
        int sum = 0;

        image = imageProcessor.getImage();

        for (int i = 0; i < stripeWidth; i++) {
            for (int j = 0; j < stripeHeight / 10; j++) {
                sum += Color.red(image.getPixel(x + i + 10, y + 10 * j + 5));
            }
        }
        return ((int) ((sum * 10) / (stripeHeight * stripeWidth)));

    }

    public int findSecondBlock() {
        return getFrontThirdBlock(10, 320);
    }


    public RobotCallable checkPositionCallable() {
        return () -> {

            double sum = 0.0;
            ElapsedTime runTime = new ElapsedTime();
            runTime.reset();
            while (runTime.milliseconds() < 5000) ;

            for (int i = 1; i <= 10; i++)
                sum += driver.getStrafePosition();

            if (Math.abs(sum / 10) < 5.00) {
                //driver.setDrivePowerAll(0, 0, 0, 0);
                AbstractOpMode.requestStopOpMode();
            }

        };
    }


    public RobotCallable armDownCallable() {
        return () -> {
            setArmDown();
        };
    }

    public RobotCallable distanceGrip() {
        return () -> {
            while (driver.distanceSensor.getDistance() > 2.2) {
                telemetry.addData(INFO, "Distance: " + driver.distanceSensor.getDistance());
                telemetry.update();
            }
            arm.setGripperGripPosition();
        };
    }

    public RobotCallable setArmDownCallable() {
        return () -> {

            arm.setArmDownPosition();
            delay(300);
            arm.setGripperReleasePosition();
            delay(400);
            arm.setArmAutonPosition();
            delay(300);
            arm.setGripperGripPosition();


        };

    }

    public RobotCallable preparePickUpCallable() {
        return () -> {
            arm.setArmDownPosition();
            setGripperRelease();
        };

    }

    public RobotCallable grabStoneCallable() {
        return () -> {
            grabStone();
        };
    }

    public void grabStone() {
        //delay(500);
        setGripperGrip();
        delay(500);
        setArmUp();
    }

    public RobotCallable grabStone2Callable() {
        return () -> {
            grabStone2();
        };
    }

    public void grabStone2() {
        double distanceRemaining = 100;
        while (distanceRemaining > 4.0 || Double.isNaN(distanceRemaining))
            distanceRemaining = driver.distanceSensor.getDistance();
        setGripperGrip();
        delay(500);
        setArmUp();


    }

    public RobotCallable deliverStoneCallable() {
        return () -> {
            deliverStone();
        };
    }

    public void deliverStone() {
        arm.setArmHalfwayPosition();
        setGripperRelease();
        delay(700);
        setArmUp();
        //delay(700);
        setGripperGrip();
        //delay(300);
    }

    public RobotCallable deliverLastStoneCallable() {
        return () -> {
            deliverLastStone();
        };
    }

    public void deliverLastStone() {
        setGripperRelease();
        arm.setArmLastStonePosition();
        delay(700);
        setArmUp();
        //delay(700);
        setGripperGrip();
        //delay(300);
    }


    public void setArmDown() {
        arm.setArmDownPosition();
    }

    public RobotCallable setArmUpCallable() {
        return () -> {
            arm.setArmAutonPosition();
        };

    }

    public RobotCallable redDelayedArmDownCallable() {
        return () -> {
            while (driver.getCurrentPosition().getX() < -28) ;
            setGripperRelease();
            setArmDown();
        };
    }

    public RobotCallable redDelayedArmDownLongCallable() {
        return () -> {
            while (driver.getCurrentPosition().getX() < 0) ;
            setGripperRelease();
            setArmDown();
        };
    }

    public RobotCallable blueDelayedArmDownCallable() {
        return () -> {
            while (driver.getCurrentPosition().getX() > 28) ;
            setGripperRelease();
            setArmDown();
        };
    }

    public RobotCallable blueDelayedArmDownLongCallable() {
        return () -> {
            while (driver.getCurrentPosition().getX() > 10) ;
            setGripperRelease();
            setArmDown();
        };
    }

    public RobotCallable blockFind() {
        return () -> {
            double distanceRemaining = 10.0;
            driver.strafe(0.25);
            ElapsedTime MeasureTime = new ElapsedTime();
            MeasureTime.reset();
            int i = 0;
            double totalTime = 0;
            while ((distanceRemaining > 3.2) || Double.isNaN(distanceRemaining)) {
                distanceRemaining = driver.distanceSensor.getDistance();
//                telemetry.addData(DoubleTelemetry.LogMode.INFO, "Distance from distance sensor: " + distanceRemaining);
//                telemetry.update();
                i++;
                //driver.updatePose();
            }
            driver.strafe(0);

            totalTime = MeasureTime.milliseconds();

            telemetry.addData(DoubleTelemetry.LogMode.INFO, "Distance from distance sensor: " + distanceRemaining);
            telemetry.update();

            telemetry.addData(DoubleTelemetry.LogMode.INFO, "Average Time: " + totalTime / i);
            telemetry.addData(DoubleTelemetry.LogMode.INFO, "Loop count: " + i);


            telemetry.addData(DoubleTelemetry.LogMode.INFO, "Loop done distance: " + distanceRemaining);

            telemetry.update();
        };
    }

    public void setArmUp() {
        arm.setArmAutonPosition();
    }

    public RobotCallable setGripperGripCallable() {
        return () -> {
            RobotState.currentPath.pause();
            arm.setGripperGripPosition();
            delay(600);
            arm.setArmAutonPosition();
            delay(500);
            RobotState.currentPath.resume();
        };

    }

    public void setGripperGrip() {
        arm.setGripperGripPosition();
    }

    public RobotCallable setGripperReleaseCallable() {
        return () -> {
            arm.setGripperReleasePosition();
        };

    }

    public RobotCallable getDistanceLoop() {
        return () -> {
            while (AbstractOpMode.isOpModeActive()) {
                telemetry.addData(INFO, "Distance: " + driver.distanceSensor.getDistance());
                telemetry.update();
            }
        };
    }

    public void setGripperRelease() {
        arm.setGripperReleasePosition();
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
        intake.toggleIntake();
    }

    //public void runTestPurePursuit(PursuitPath pursuitPath) {
    //   driver.testPurePursuit(pursuitPath);
    //}

    public RobotCallable toggleRotationCallable() {
        return () -> toggleRotation();
    }

    public void toggleConeyor() {
        intake.toggleConveyor();
    }


    public RobotCallable toggleConeyorCallable() {
        return () -> toggleConeyor();
    }

    public void toggleTilt() {


    }

    public void toggleBothDraggersFull() {
        dragger.toggleDragger();
    }

    public RobotCallable toggleBothCallable() {
        return () -> toggleBothDraggersFull();
    }

    public void toggleBothDraggersHalf() {
        dragger.toggleDraggerHalf();


    }


    public void setDraggerDown() {
        dragger.setBackDown();
        dragger.setFrontDown();
        delay(500);
    }

    public RobotCallable setDraggerDownCallable() {
        return () -> {

            setDraggerDown();
        };
    }

    public RobotCallable setDraggerDownDelayedCallable() {

        return () -> {
            while (driver.getCurrentPosition().getY() > -30) ;
            setDraggerDown();
        };


    }

    public void setDraggerHalfway() {
        dragger.setBackHalfway();
        dragger.setFrontHalfway();
        delay(300);
    }

    public void setDraggerUp() {
        dragger.setDraggerUp();
    }

    public RobotCallable delayedBlueDraggerHalfwayCallable() {
        return () -> {
            while (driver.getCurrentPosition().getX() < 20) ;
            setDraggerHalfway();
        };
    }

    public RobotCallable delayedRedDraggerHalfwayCallable() {
        return () -> {
            while (driver.getCurrentPosition().getX() > -20) ;
            setDraggerHalfway();
        };
    }

    public RobotCallable delayedDraggerUpCallable() {
        return () -> {
            delay(1500);
            setDraggerUp();
        };
    }

    public RobotCallable setDraggerUpCallable() {
        return () -> {
            setDraggerUp();
        };
    }

    public RobotCallable delayedDraggerDownCallable() {
        return () -> {
            while(driver.getCurrentPosition().getX() < -50) ;
            setDraggerDown();
        };
    }


    public RobotCallable setDraggerHalfwayCallable() {
        return () -> {
            setDraggerHalfway();
        };
    }

    public LiftController lift() {
        return lift;
    }

    public IntakeController intake() {
        return intake;
    }

    public TapeMeasureController tapeMeasure() {
        return tapeMeasure;
    }


    public ArmController arm() {
        return arm;
    }

    public DraggerController dragger() {
        return dragger;
    }


    public void blueDragFoundation() {
        double x = -0.2, y = 0.6, z = 0.3;

        double frontLeft = (x - y - z);
        double frontRight = (x + y + z);
        double backLeft = (x + y - z);
        double backRight = (x - y + z);

        setDrivePowerAll(frontLeft, frontRight, backLeft, backRight);
        while (driver.getHeading() < 80) ;
        setDrivePowerAll(0, 0, 0, 0);
        delay(200);
        dragger.setDraggerUp();
        setDrivePowerAll(0.6, -0.6, -0.6, 0.6);
        delay(1100);
        tapeMeasure.extend();
        delay(300);
        setDrivePowerAll(0, 0, 0, 0);
    }

    public void blueParkWithTape() {
        double x = 0.0, y = 0.0, z = 1.0;

        double frontLeft = (x - y - z);
        double frontRight = (x + y + z);
        double backLeft = (x + y - z);
        double backRight = (x - y + z);
        setDrivePowerAll(frontLeft, frontRight, backLeft, backRight);
        while (driver.getHeading() < 90) ;
        setDrivePowerAll(0, 0, 0, 0);
        delay(300);
        setDraggerUp();
    }

    public void redDragFoundation() {
        /*
        delay(600);

        double x = 0.0, y = 0.5, z = -0.3;

        double frontLeft = (x - y - z);
        double frontRight = (x + y + z);
        double backLeft = (x + y - z);
        double backRight = (x - y + z);

        setDrivePowerAll(frontLeft, frontRight, backLeft, backRight);
        while (driver.getHeading() > -75) ;
        setDrivePowerAll(0, 0, 0, 0);
        delay(200);
        dragger.setDraggerUp();
        setDrivePowerAll(0.6, -0.6, -0.6, 0.6);
        delay(1300);
        setDrivePowerAll(0, 0, 0, 0);
        tapeMeasure.extend();
        delay(100);*/

        delay(600);

        double x = 0.0, y = 0.6, z = 0.0;

        double frontLeft = (x - y - z);
        double frontRight = (x + y + z);
        double backLeft = (x + y - z);
        double backRight = (x - y + z);

        setDrivePowerAll(frontLeft, frontRight, backLeft, backRight);

        delay(1500);

        x = 0.0;
        y = 0.6;
        z = -0.3;

        frontLeft = (x - y - z);
        frontRight = (x + y + z);
        backLeft = (x + y - z);
        backRight = (x - y + z);

        setDrivePowerAll(frontLeft, frontRight, backLeft, backRight);
        while (driver.getHeading() > -75) ;
        dragger.setDraggerUp();
        setDrivePowerAll(0.6, -0.6, -0.6, 0.6);
        delay(1300);
        setDrivePowerAll(0, 0, 0, 0);
        tapeMeasure.extend();
        delay(100);
    }

    public void redParkWithTape() {
        delay(300);
        double x = 0., y = 0.0, z = -1.0;

        double frontLeft = (x - y - z);
        double frontRight = (x + y + z);
        double backLeft = (x + y - z);
        double backRight = (x - y + z);
        setDrivePowerAll(frontLeft, frontRight, backLeft, backRight);
        while (driver.getHeading() > -80) ;
        setDrivePowerAll(0, 0, 0, 0);
        delay(300);
        setDraggerUp();
    }

    public void redPark() {
        double x = 0.0, y = 0.6, z = 0.0;

        double frontLeft = (x - y - z);
        double frontRight = (x + y + z);
        double backLeft = (x + y - z);
        double backRight = (x - y + z);

        setDrivePowerAll(frontLeft, frontRight, backLeft, backRight);
        delay(1700);
        setDrivePowerAll(0, 0, 0, 0);
    }

    public void setDrivePowerAll(double FL, double FR, double BL, double BR) {
        driver.setDrivePowerAll(FL, FR, BL, BR);
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
