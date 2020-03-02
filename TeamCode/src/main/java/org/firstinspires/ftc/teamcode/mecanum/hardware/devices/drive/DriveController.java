package org.firstinspires.ftc.teamcode.mecanum.hardware.devices.drive;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.framework.userhardware.DoubleTelemetry;
import org.firstinspires.ftc.teamcode.framework.userhardware.PIDController;
import org.firstinspires.ftc.teamcode.framework.userhardware.inputs.sensors.DistanceColorSensor;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.Path;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.PurePursuitSegment;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.Segment;
import org.firstinspires.ftc.teamcode.framework.userhardware.purepursuit.MecanumPurePursuitController;
import org.firstinspires.ftc.teamcode.framework.userhardware.purepursuit.Pose;
import org.firstinspires.ftc.teamcode.framework.userhardware.purepursuit.PursuitPath;
import org.firstinspires.ftc.teamcode.framework.util.SubsystemController;
import org.firstinspires.ftc.teamcode.mecanum.hardware.devices.led.LedController;
import org.firstinspires.ftc.teamcode.mecanum.hardware.util.TelemetryRecord;
import org.firstinspires.ftc.teamcode.mecanum.hardware.util.StraightTrapezoid;
import org.upacreekrobotics.dashboard.Config;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static java.lang.Math.abs;
import static java.lang.Math.addExact;
import static java.lang.Math.pow;
import static org.firstinspires.ftc.teamcode.framework.userhardware.DoubleTelemetry.LogMode.INFO;
import static org.firstinspires.ftc.teamcode.mecanum.hardware.Constants.*;

import static org.firstinspires.ftc.teamcode.mecanum.hardware.RobotState.currentPath;

@Config
public class DriveController extends SubsystemController {

    private Drive drive;

    private PIDController anglePID, straightPID, distancePID, strafePID;

    public DistanceColorSensor distanceSensor;

    private double baseHeading = 0;

    private double turnY = 0, turn_z = 0, leftPower = 0, rightPower = 0, Drive_Power = 1.0;

    private ElapsedTime runtime;

    private DecimalFormat DF;

    public static double PATH_P = 15, PATH_F = 5, pi = Math.PI;

    public static double PROP_GAIN = 0.1, INT_GAIN = 0.0, DIFF_GAIN = 0.1;

    private double previousTime;

    private DecimalFormat DFpwr, DFenc;
    private boolean isMotorIntaking = false;

    private LedController led;

    private boolean boolEmergencyStop = false;

    private final boolean BOOL_ODOMETRY_CODE_ENABLED = false;

    private final double ODOMETRY_ERROR_THRESHOLD_IN_INCHES = 1;

    TelemetryRecord[] RecTelem = new TelemetryRecord[1000];
    ElapsedTime RecTelemTime;


    //Utility Methods
    public DriveController() {

        runtime = new ElapsedTime();

        DF = new DecimalFormat("#.###");
        //Put general setup here
        drive = new Drive(hardwareMap, telemetry);
        //anglePID = new PIDController(10, 20, 50, 0.3, 0.0);//D was 150
        anglePID = new PIDController(20, 0.7, 40, 0.3, 0.0);//D was 150
        //anglePID.setLogging(true);
        straightPID = new PIDController(10, 0.000, 0.01, 1, 0);
        //distancePID = new PIDController(PROP_GAIN,INT_GAIN ,DIFF_GAIN , 2, 0.1);
        distancePID = new PIDController(0.3, 0.1, 0, 2, 0.1);
        strafePID = new PIDController(10, 0, 0, 0, 0.1);
        for (int i = 0; i < RecTelem.length; i++) {
            RecTelem[i] = new TelemetryRecord();
            RecTelem[i].Index = i;
        }
        RecTelemTime = new ElapsedTime();
        DFpwr = new DecimalFormat(" #.000");
        DFpwr.setMinimumIntegerDigits(1);
        DFenc = new DecimalFormat(" 00000.0;-00000.0");

        distanceSensor = new DistanceColorSensor("Color_Sensor");
        led = new LedController();

    }

    public synchronized void update() {
        drive.updatePose();

        Pose currentPosition = drive.getCurrentPosition();

        telemetry.getSmartdashboard().putGraph("Pure Pursuit", "Position", currentPosition.getX(), currentPosition.getY());
        telemetry.addData(INFO, "X: " + drive.getXActualPositionInches() + " Y: " + drive.getYActualPositionInches() + " Is following: " + drive.isFollowing());
        telemetry.addData(INFO, "Calc X: " + currentPosition.getX() + " calc Y: " + currentPosition.getY() + " Is following: " + drive.isFollowing());

        telemetry.addData(INFO, "Heading: " + drive.getHeading());
        telemetry.update();

    }

    public synchronized void stop() {
        drive.stop();
    }


    public double getStraightPosition() {

        return drive.getStraightPosition();
    }

    public double getStrafePosition() {
        return drive.getStrafePosition();
    }

    public double getFrontLeftPosition() {
        return drive.getFrontLeftPosition();
    }

    public double getBackLeftPosition() {
        return drive.getBackLeftPosition();
    }

    //Autonomous Methods
    public synchronized void runDrivePath(Path path) {

        boolean lastPathPaused = false;

        if (currentPath != null && currentPath.isPaused()) {
            lastPathPaused = true;
        }

        currentPath = path;
        currentPath.reset();

        if (lastPathPaused) currentPath.pause();

        // telemetry.addData(INFO, "Starting path: " + currentPath.getName() + "  paused: " + currentPath.isPaused() + "  done: " + currentPath.isDone());

        while (!path.isDone() && opModeIsActive()) {

            //SmoothPath is done
            if (path.getNextSegment() == null) break;

            telemetry.addData(INFO, "Starting segment: " + path.getCurrentSegment().getName() + " in path: " + currentPath.getName() + "  paused: " + currentPath.isPaused() + "  done: " + currentPath.isDone());

            if (path.getCurrentSegment().getType() == Segment.SegmentType.PUREPURSUIT) {
                purePursuitToSegment((PurePursuitSegment) path.getCurrentSegment());


            }

            telemetry.addData(INFO, "Finished segment: " + path.getCurrentSegment().getName() + " in path: " + currentPath.getName() + "  paused: " + currentPath.isPaused() + "  done: " + currentPath.isDone());
        }
        telemetry.update();
        // telemetry.addData(INFO, "Finished path: " + currentPath.getName() + "  paused: " + currentPath.isPaused() + "  done: " + currentPath.isDone());
    }

    public void blockFind() {
        double distanceRemaining = 10.0;
        this.strafe(0.35);
        ElapsedTime MeasureTime = new ElapsedTime();
        MeasureTime.reset();
        int i = 0;
        double totalTime = 0;
        while ((distanceRemaining > 2.2) || (distanceRemaining < 0.2) || Double.isNaN(distanceRemaining)) {
            distanceRemaining = this.distanceSensor.getDistance();
            telemetry.addData(DoubleTelemetry.LogMode.INFO, "Distance from distance sensor: " + distanceRemaining);
            telemetry.update();
            i++;
            // drive.updatePose();


        }
        this.strafe(0);


        totalTime = MeasureTime.milliseconds();


        telemetry.addData(DoubleTelemetry.LogMode.INFO, "Average Time: " + totalTime / i);
        telemetry.addData(DoubleTelemetry.LogMode.INFO, "Loop count: " + i);


        telemetry.addData(DoubleTelemetry.LogMode.INFO, "Loop done distance: " + distanceRemaining);

        telemetry.update();

    }


    public synchronized void purePursuitToSegment(PurePursuitSegment segment) {
        telemetry.addData(INFO, "Pure Pursuit Segment is starting");
        telemetry.addData(INFO, "");

        delay(segment.getPeriod());
        testPurePursuit(segment.getPursuitPath(), segment.getTargetHeading(), segment.getTimeOut(), segment.getBlockSense());
    }

    public void setHeadingMode(MecanumPurePursuitController.HeadingMode headingMode) {
        drive.setHeadingMode(headingMode);
    }

    public void testPurePursuit(PursuitPath pursuitPath, double targetHeading, double timeOut, boolean blockSense) {

        pursuitPath.reset();

        //drive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        drive.setTargetHeading(targetHeading);

        pursuitPath.build();

//        for(PathPoint point : pursuitPath.getPoints()) {
//            telemetry.getSmartdashboard().putGraph("position", "target", point.getX(), point.getY());
//            telemetry.getSmartdashboard().putGraph("position", "velocity", point.getX(), point.getVelocity());
//        }

        drive.follow(pursuitPath);
        ElapsedTime runtime = new ElapsedTime();
        runtime.reset();
        int loopCounts = 0;

//        Pose InitCurrentPose = drive.getCurrentPosition();
//        telemetry.addData(INFO,"Init position X:" + InitCurrentPose.getX());
//        telemetry.addData(INFO,"Init position y:" + InitCurrentPose.getY());
//        telemetry.update();


        double lastTime = 0.000001;
        while (opModeIsActive() && drive.isFollowing() && runtime.milliseconds() < timeOut) {
            drive.update();
            loopCounts++;

            telemetry.addData(INFO, "X Position: " + drive.getCurrentPosition().getX()+ " Y Position: " + drive.getCurrentPosition().getY()+ " Heading: "+drive.getCurrentPosition().getHeading());



            telemetry.update();


//            telemetry.update();
//            telemetry.getSmartdashboard().putGraph("position","actual",drive.getCurrentPosition().getX(),currentPose.getY());
//            telemetry.getSmartdashboard().putGraph("velocity","actual",currentPose.getX(),drive.getDistance()/(runtime.seconds()-lastTime));



        }




        lastTime = runtime.seconds();

        if (blockSense) {

            blockFind();
        }


        drive.setPower(0, 0);


        telemetry.addData(INFO, "loop counts: " + loopCounts);
        telemetry.addData(INFO, "runtime: " + lastTime);
        telemetry.addData(INFO, "loop time: " + lastTime / loopCounts);
        if(lastTime/loopCounts>0.02) led.setGreen();
        telemetry.update();


    }


//    public void approachWall(double power, int threshold){
//        double oldDistance;
//
//        drive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        drive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//
//        oldDistance = drive.getBackRightPosition();
//        drive.setPower(power, power);
//
//        delay(100);
//        while(threshold< Math.abs(oldDistance-drive.getBackRightPosition())){
//            oldDistance = drive.getBackRightPosition();
//            delay(10);
//        }
//        drive.setPower(0,0);
//    }

//    public void approachFoundation(double power, int loopTime, double velThreshold) {
//
//        double slowPower = 0.17;
//        double oldDistance;
//        double velocity;
//        double oldvelocity = 8.0;
//        double oldTime = 0.0;
//        int loop = 0;
//        //int loopTime = 1;
//        int startTime = 350;
//        double spaceBuffer = 0.18;
//        ArrayList<Double> locations = new ArrayList<Double>();
//        ArrayList<Double> velocities = new ArrayList<Double>();
//
//        drive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        drive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        oldTime =  (double) System.currentTimeMillis();
//        //oldDistance = -drive.getBackLeftPosition()/STRAIGHT_COUNTS_PER_INCH;
//        oldDistance = drive.getBackRightPosition()/STRAIGHT_ENCODER_COUNTS_INCH;
//        //locations.add(oldDistance);
//        //strafe(-power);
//        drive.setDrivePowerAll(power,power,power,power);
//        delay(startTime);
//        //velocity = 1000.0*((-drive.getBackLeftPosition()/STRAIGHT_COUNTS_PER_INCH) - oldDistance)/((double)System.currentTimeMillis()-oldTime);
//        velocity = 1000.0*((drive.getBackRightPosition()/STRAIGHT_ENCODER_COUNTS_INCH) - oldDistance)/((double)System.currentTimeMillis()-oldTime);
//        oldTime =  (double) System.currentTimeMillis();
//        //oldDistance = -drive.getBackLeftPosition()/STRAIGHT_COUNTS_PER_INCH;
//        oldDistance = drive.getBackRightPosition()/STRAIGHT_ENCODER_COUNTS_INCH;
//        locations.add(oldDistance);
//        velocities.add(velocity);
//        runtime.reset();
//        //while (((-drive.getBackLeftPosition()/STRAIGHT_COUNTS_PER_INCH) - oldDistance) > spaceBuffer) {
//        while ((velocity+oldvelocity) > (2*velThreshold)){
//
//            delay(loopTime);
//
//            //velocity = 1000.0*((-drive.getBackLeftPosition()/STRAIGHT_COUNTS_PER_INCH) - oldDistance)/((double)System.currentTimeMillis()-oldTime);
//            velocity = 1000.0*((drive.getBackRightPosition()/STRAIGHT_ENCODER_COUNTS_INCH) - oldDistance)/((double)System.currentTimeMillis()-oldTime);
//            oldTime = (double) System.currentTimeMillis();
//            //oldDistance = -drive.getBackLeftPosition()/STRAIGHT_COUNTS_PER_INCH;
//            oldDistance = drive.getBackRightPosition()/STRAIGHT_ENCODER_COUNTS_INCH;
//            //telemetry.addData(INFO, "Side encoder position: " + drive.getBackLeftPosition() / STRAIGHT_COUNTS_PER_INCH);
//            //telemetry.update();
//            locations.add(oldDistance);
//            velocities.add((velocity+oldvelocity)/2);
//            oldvelocity = velocity;
//            ++loop;
//        }
//        drive.stop();
//        locations.add(oldDistance);
//        velocities.add((velocity+oldvelocity)/2);
//        //locations.add(-drive.getBackLeftPosition()/STRAIGHT_COUNTS_PER_INCH);
//        for(int i=0; i < locations.size();++i) {
//            telemetry.addData(INFO, "Next stage locations   " + locations.get(i));
//            telemetry.getSmartdashboard().putGraph("Turbo", "Power",locations.get(i),velocities.get(i));
//        }
//        telemetry.update();
//        //delay(9000);
//        telemetry.addData(INFO, "Time for drive: " + runtime.milliseconds());
//        telemetry.addData(INFO, "Average loop time for drive: " + runtime.milliseconds() / loop);
//        telemetry.addData(INFO, "Loop count " + loop);
//        telemetry.addData(INFO, "Side encoder position: " + drive.getBackLeftPosition() / STRAIGHT_ENCODER_COUNTS_INCH);
//        telemetry.update();
//    }

    public void strafe(double power) {

        drive.setDrivePowerAll(power, -power, -power, power);

    }

    public synchronized void realignHeading() {

        double ROTATE_MAX_POWER = 0.1;
        double angleError;
        double power;

        delay(400);     //wait for skid twist to finish
        angleError = getHeading();

        while (angleError > 1.0 || angleError < -1.0) {

            angleError = getHeading();
            power = ROTATE_MAX_POWER * angleError;
            if (power > ROTATE_MAX_POWER)
                power = ROTATE_MAX_POWER;
            if (power < -ROTATE_MAX_POWER)
                power = -ROTATE_MAX_POWER;

            drive.setDrivePowerAll(power, -power, power, -power);
            //telemetry.addData(INFO,"angle error  " + angleError);
            //telemetry.addData(INFO,"power  "  + power);
            //telemetry.update();
        }
    }

    public synchronized void runPath(Path path) {

        //drive.follow(path);

        while (opModeIsActive() && drive.isFollowing()) {

            drive.update();

            telemetry.getSmartdashboard().putGraph("SmoothPath", "Actual", drive.getCurrentPosition().getX(), drive.getCurrentPosition().getY());

        }

        drive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public synchronized void setPosition(int position, double power) {
        drive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        drive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        drive.setPower(range(power), range(power));
        // drive.setTargetPosition(position);
    }


    public double getHeading() {
        return drive.getHeading();
    }

   /*public double getPitch() {
        return drive.getPitch();
    }*/

    public int getLeftPosition() {
        return drive.getFrontLeftPosition();
    }

    public int getRightPosition() {
        return drive.getFrontRightPosition();
    }

    public synchronized void resetAngleToZero() {
        drive.resetAngleToZero();
    }

    //TeleOp Methods
    public synchronized void setPowerNoEncoder(double left, double right) {
        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        drive.setPower(range(left), range(right));
    }

    public synchronized void setPower(double left, double right) {

        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        drive.setPower(range(left), range(right));
    }

    public synchronized void setDrivePowerAll(double FL, double FR, double BL, double BR) {
        drive.setDrivePowerAll(FL, FR, BL, BR);
    }

    public synchronized void setY(double y) {
        turnY = (float) scaleInput(y);
    }

    public synchronized void setZ(double z) {
        turn_z = z;
        turn_z = (float) scaleInput(turn_z);
    }


    /* public synchronized void updateYZDrive() {
         if ((currentMineralLiftState == MineralLiftState.IN_MOTION ||
                 currentMineralLiftState == MineralLiftState.DUMP_POSITION) &&
                 currentMatchState == MatchState.TELEOP) {
             leftPower = range((turnY + turn_z) * (Drive_Power * DRIVE_MINERAL_LIFT_RAISED_SCALAR));
             rightPower = range((turnY - turn_z) * (Drive_Power * DRIVE_MINERAL_LIFT_RAISED_SCALAR));
         } else {
             leftPower = range((turnY + turn_z) * Drive_Power);
             rightPower = range((turnY - turn_z) * Drive_Power);
         }

         drive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
         drive.setPower(leftPower, rightPower);
     }
 */
    //Util Methods
    private synchronized double scaleInput(double val) {
        return (range(pow(val, 3)));
    }

    private synchronized double range(double val) {
        if (val < -1) val = -1;
        if (val > 1) val = 1;
        if (val < 0.13 && val > 0) val = 0.13;
        if (val > -0.13 && val < 0) val = -0.13;
        return val;
    }
/*
    public synchronized boolean isGyroCalibrated() {
        return drive.isGyroCalibrated();
    }

    public void setLightOn() {
        drive.setLightPower(1);
    }

    public void setLightOff() {
        drive.setLightPower(0);
    }

    public void dropTeamMarker() {
        //Teleop dump marker sequence
        if (RobotState.currentMatchState == MatchState.TELEOP) {
            drive.setMarkerServo(DRIVE_TEAM_MARKER_EXTENDED);
            delay(DRIVE_DUMP_TEAM_MARKER_DELAY);
            drive.setMarkerServo(DRIVE_TEAM_MARKER_TELEOP_RETRACTED);
            return;
        }

        //Auton dump marker sequence
        telemetry.addData(INFO, "Start marker dump");
        currentPursuitPath.pause();
        telemetry.addData(INFO, "Pause path");
        drive.setMarkerServo(DRIVE_TEAM_MARKER_EXTENDED);
        delay(DRIVE_DUMP_TEAM_MARKER_DELAY);
        drive.setMarkerServo(DRIVE_TEAM_MARKER_RETRACTED);
        currentPursuitPath.resume();
        telemetry.addData(INFO, "Marker dumped");
    }*/

    public Pose getCurrentPosition() {
        return drive.getCurrentPosition();
    }

    public void updatePose() {
        drive.updatePose();
        telemetry.addData(INFO, "Pose X: " + getCurrentPosition().getX());
        telemetry.addData(INFO, "pose y: " + getCurrentPosition().getY());
        telemetry.update();
    }

    public void startMotorIntake() {
        drive.setLeftMotorIntakePower(1.0);
        drive.setRightMotorIntakePower(1.0);
    }

    public void stopMotorIntake() {
        drive.setLeftMotorIntakePower(0.0);
        drive.setRightMotorIntakePower(0.0);
    }

    public void reverseMotorIntake() {
        drive.setLeftMotorIntakePower(-0.7);
        drive.setRightMotorIntakePower(-0.7);
    }

    public void toggleMotorIntake() {
        if (isMotorIntaking) stopMotorIntake();
        else startMotorIntake();
        isMotorIntaking = !isMotorIntaking;
    }

    public void resetPosition() {
        drive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
}