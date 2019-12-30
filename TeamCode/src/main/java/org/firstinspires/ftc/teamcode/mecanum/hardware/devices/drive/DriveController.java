package org.firstinspires.ftc.teamcode.mecanum.hardware.devices.drive;

import android.icu.text.IDNA;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.framework.userhardware.PIDController;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.AngleDriveSegment;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.DriveSegment;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.Path;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.PurePursuitSegment;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.Segment;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.StrafeSegment;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.TurnSegment;
import org.firstinspires.ftc.teamcode.framework.userhardware.purepursuit.PathPoint;
import org.firstinspires.ftc.teamcode.framework.userhardware.purepursuit.Pose;
import org.firstinspires.ftc.teamcode.framework.userhardware.purepursuit.PursuitPath;
import org.firstinspires.ftc.teamcode.framework.util.SubsystemController;
import org.firstinspires.ftc.teamcode.mecanum.hardware.util.TelemetryRecord;
import org.firstinspires.ftc.teamcode.mecanum.hardware.util.StrafeTrapezoid;
import org.firstinspires.ftc.teamcode.mecanum.hardware.util.StraightTrapezoid;
import org.firstinspires.ftc.teamcode.mecanum.opmodes.auton.vector.Vector;
import org.upacreekrobotics.dashboard.Config;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static java.lang.Math.abs;
import static java.lang.Math.pow;
import static org.firstinspires.ftc.teamcode.framework.userhardware.DoubleTelemetry.LogMode.INFO;
import static org.firstinspires.ftc.teamcode.mecanum.hardware.Constants.*;

import static org.firstinspires.ftc.teamcode.mecanum.hardware.RobotState.currentPath;

@Config
public class DriveController extends SubsystemController {

    private Drive drive;

    private PIDController anglePID, straightPID, distancePID, strafePID;

    private double baseHeading = 0;

    private double turnY = 0, turn_z = 0, leftPower = 0, rightPower = 0, Drive_Power = 1.0;

    private ElapsedTime runtime;

    private DecimalFormat DF;

    public static double PATH_P = 15, PATH_F = 5, pi = Math.PI;

    public static double PROP_GAIN=0.1,INT_GAIN=0.0,DIFF_GAIN=0.1;

    private double previousTime;

    private DecimalFormat DFpwr, DFenc;

    TelemetryRecord[] RecTelem = new TelemetryRecord[1000];
    ElapsedTime RecTelemTime;


    //Utility Methods
    public DriveController() {

        runtime = new ElapsedTime();

        DF = new DecimalFormat("#.###");
        //Put general setup here
        drive = new Drive(hardwareMap,telemetry);
        //anglePID = new PIDController(10, 20, 50, 0.3, 0.0);//D was 150
        anglePID = new PIDController(20, 0.7, 40, 0.3, 0.0);//D was 150
        //anglePID.setLogging(true);
        straightPID = new PIDController(10, 0.000, 0.01, 1, 0);
        //distancePID = new PIDController(PROP_GAIN,INT_GAIN ,DIFF_GAIN , 2, 0.1);
        distancePID = new PIDController(0.3, 0.1, 0, 2, 0.1);
        strafePID = new PIDController(10,0,0,0,0.1);
        for ( int i = 0; i<RecTelem.length; i++)
        {
            RecTelem[i] = new TelemetryRecord();
            RecTelem[i].Index = i;
        }
        RecTelemTime = new ElapsedTime();
        DFpwr = new DecimalFormat(" #.000");
        DFpwr.setMinimumIntegerDigits(1);
        DFenc = new DecimalFormat(" 00000.0;-00000.0");

    }

    public synchronized void update() {

    }

    public synchronized void stop() {
        drive.stop();
    }


    public  double getStraightPosition(){

        return drive.getStraightPosition();
    }

    public  double getStrafePosition(){
        return drive.getStrafePosition();
    }

    public void driveToVector(Vector vector){
        double cos = vector.cos(), sin = vector.sin(),x = vector.x, y = vector.y;
        setDrivePowerAll(cos*x + sin*y,sin*y - cos*x,sin*y - cos*x, cos*x + sin*y );
        telemetry.addData(INFO,"cosX: " + cos*x);
        telemetry.addData(INFO, "sinY: " + sin*y);
        telemetry.update();
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

            if (path.getCurrentSegment().getType() == Segment.SegmentType.TURN) {
                turnToSegment((TurnSegment) path.getCurrentSegment());
            }
            else if (path.getCurrentSegment().getType() == Segment.SegmentType.DRIVE) {
                driveToSegment((DriveSegment) path.getCurrentSegment());
            }
            else if (path.getCurrentSegment().getType() == Segment.SegmentType.STRAFE){
                strafeToSegment((StrafeSegment) path.getCurrentSegment());
            }
            else if (path.getCurrentSegment().getType() == Segment.SegmentType.ANGLEDRIVE) {
                angleDriveToSegment((AngleDriveSegment) path.getCurrentSegment());
            }
            else if (path.getCurrentSegment().getType() == Segment.SegmentType.PUREPURSUIT){
                purePursuitToSegment((PurePursuitSegment) path.getCurrentSegment());
            }

          //  telemetry.addData(INFO, "Finished segment: " + path.getCurrentSegment().getName() + " in path: " + currentPath.getName() + "  paused: " + currentPath.isPaused() + "  done: " + currentPath.isDone());
        }

       // telemetry.addData(INFO, "Finished path: " + currentPath.getName() + "  paused: " + currentPath.isPaused() + "  done: " + currentPath.isDone());
    }

    public synchronized void purePursuitToSegment(PurePursuitSegment segment) {
        telemetry.addData(INFO, "Pure Pursuit Segment is starting");
        telemetry.addData(INFO, "");

        delay(segment.getPeriod());
        testPurePursuit(segment.getPursuitPath(), segment.getTargetHeading());
    }

    /*public PursuitPath configurePath(PursuitPath path) {
        path.setMaxSpeed(1.4);
        path.setTurnSpeed(1);
        path.setTrackingErrorSpeed(5.0);
        path.setLookAheadDistance(3);
        path.setVelocityLookAheadPoints(1);
        path.setMaxAcceleration(0.05);
        path.setMaxDeceleration(0.01);
        path.setTurnErrorScalar(0);
        path.setPathSmoothing(0.5);
        path.setMinSpeed(0.2);

        return path;
    }*/

    public void testPurePursuit(PursuitPath pursuitPath, double targetHeading){

        pursuitPath.reset();

        //drive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        drive.setTargetHeading(targetHeading);

        pursuitPath.build();

        for(PathPoint point : pursuitPath.getPoints()) {
            //telemetry.getSmartdashboard().putGraph("position", "target", point.getX(), point.getY());
            //telemetry.getSmartdashboard().putGraph("position", "velocity", point.getX(), point.getVelocity());
        }

        drive.follow(pursuitPath);

        Pose InitCurrentPose = drive.getCurrentPosition();
        telemetry.addData(INFO,"Init position X:" + InitCurrentPose.getX());
        telemetry.addData(INFO,"Init position y:" + InitCurrentPose.getY());
        telemetry.update();

        while(opModeIsActive() && drive.isFollowing()){
            drive.update();
            Pose currentPose = drive.getCurrentPosition();
            telemetry.addData(INFO,"position X:" + currentPose.getX());
            telemetry.addData(INFO,"position y:" + currentPose.getY());
            telemetry.update();
        }

        drive.setPower(0, 0);

        Pose currentPose = drive.getCurrentPosition();
        telemetry.addData(INFO,"Final position X:" + currentPose.getX());
        telemetry.addData(INFO,"Final position y:" + currentPose.getY());
        telemetry.addData(INFO, "Final Heading"+ drive.getActualHeadingDegrees());
        telemetry.update();

    }


    public synchronized void driveToSegment(DriveSegment segment) {

        telemetry.addData(INFO, "Drive Segment is starting");
        telemetry.addData(INFO, "");
        telemetry.update();

        double distance = segment.getDistance(), speed = segment.getSpeed(), angle = baseHeading;
        String segmentName = segment.getName();
        double error = segment.getError();
        double anglereset = segment.getError();
        double period = segment.getPeriod();
        double switchDirection = 1.0;
        if (distance<0) switchDirection = -1.0;
        double destination = (distance*switchDirection);

        boolean alignHeading = segment.getAlignHeading();
        distancePID.reset();

        double leftPower=0.0, rightPower=0.0;
        double power = 0.0;
        double velocity = 0.0, oldTime = 0.0;
        double oldDistance = 0;
        double straightOffset = 0.0;

        StraightTrapezoid straightTrapezoid = new StraightTrapezoid();

        double driveLeftCorrection = 1.0;

        double distanceTravelled = 0, distanceError;
        double noise = 0.0;

        ArrayList<Double> noiseValues = new ArrayList<Double>();
        ArrayList<Double> powerValues = new ArrayList<Double>();
        ArrayList<Double> positionValues = new ArrayList<Double>();
        ArrayList<Double> angleCorrectionValues = new ArrayList<Double>();
        ArrayList<Double> headingValues = new ArrayList<Double>();

        drive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        drive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        straightOffset = drive.getStraightPosition();

        double angleError = 0.0, angleCorrection = 0.0, angleCorrectionGain=0.01*0.0;
        double currentHeading=getHeading(), turn = 0.0;
        angle = 0.0;

        int loop = 0;

        RecTelemTime.reset();
        runtime.reset();

        while ((!atPosition(destination, distanceTravelled, error)) && opModeIsActive() && (runtime.milliseconds()<period)){
            if (segment.isDone()) {
                setPower(0, 0);
                return;
            }
            if (!segment.isRunning()) {
                setPower(0, 0);
                continue;
            }

            //  Get new location values
            distanceTravelled =  (drive.getStraightPosition()-straightOffset)*switchDirection;
            distanceError = destination - distanceTravelled;

            // Calculate new power based on location in path
            //power = 1.0;
            //power = distancePID.output(destination, distanceTravelled);
            power = straightTrapezoid.getPower(segmentName,distanceError,distanceTravelled);

            // Heading correction
            currentHeading = getHeading();
           if (angle - currentHeading > 180) {
                turn = anglePID.output(angle, 360 + currentHeading);
            } else if (currentHeading - angle > 180) {
                turn = anglePID.output(angle, angle - (360 - (currentHeading - angle)));
            } else {
                turn = anglePID.output(angle, currentHeading);
            }
            turn = -anglePID.output(angle, currentHeading);

            //   Set power according to corrections
            /*if (power > 0) {
                leftPower = range(power * (speed - turn));
                rightPower = range(power * (speed + turn));
            } else {
                leftPower = range(power * (speed + turn));
                rightPower = range(power * (speed - turn));
            }*/

            leftPower = range((speed*power*switchDirection) + turn);
            rightPower = range((speed*power*switchDirection) -turn);

            drive.setDrivePowerAll(leftPower, rightPower , leftPower , rightPower);

            //  Fill arrays to be graphed in smart Dashboard

            headingValues.add(currentHeading);
            positionValues.add(distanceTravelled);
        loop++;

        } //end of while loop

        drive.stop();
       // telemetry.addData(INFO, "Made it out of loop");
        //telemetry.update();

        //  Fill in smart Dashboard plots after loop to account for after skid
        for(int i=0; i<20; ++i) {
            headingValues.add(currentHeading);
            positionValues.add(distanceTravelled);
            delay(50);
        }

        //if (alignHeading)    realignHeading();
        //realignHeading();

        telemetry.addData(INFO, "Time for drive: " + runtime.milliseconds());
        telemetry.addData(INFO, "Average loop time for drive: " + runtime.milliseconds() / loop);
        telemetry.addData(INFO, "Loop count " + loop);
        telemetry.addData(INFO, "Straight encoder goal: " + distance );
        telemetry.addData(INFO, "Straight encoder position: " + (drive.getStraightPosition()-straightOffset));
        telemetry.update();

        //  Make smart Dashboard plots
        for(int i=0; i< positionValues.size();++i){
            telemetry.getSmartdashboard().putGraph("Turbo", "Angle correction", positionValues.get(i), angleCorrectionValues.get(i));
            telemetry.getSmartdashboard().putGraph("Turbo", "Heading", positionValues.get(i), headingValues.get(i));}
        //drive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }

    public synchronized void strafeToSegment(StrafeSegment segment) {

        telemetry.addData(INFO, "Strafe Segment is starting");
        telemetry.addData(INFO, "");
        telemetry.update();

        double distance = segment.getDistance(), speed = segment.getSpeed(), angle = baseHeading;
        String segmentName = segment.getName();
        double error = segment.getError();
        double anglereset = segment.getError();
        //double period = segment.getPeriod();
        double period = 50000;
        double switchDirection = 1.0;
        if (distance<0) switchDirection = -1.0;
        double destination = (distance*switchDirection);

        boolean alignHeading = segment.getAlignHeading();
        distancePID.reset();

        double leftPower=0.0, rightPower=0.0;
        double strafePower = 0.0;
        double power = 0.0;
        double velocity = 0.0, oldTime = 0.0;
        double oldDistance = 0;
        double strafeOffset = 0.0;

        StrafeTrapezoid strafeTrapezoid = new StrafeTrapezoid();

        double driveLeftCorrection = 1.09;

        double distanceTravelled = 0.0;
        double distanceError = destination - distanceTravelled;

        ArrayList<Double> velocityValues = new ArrayList<Double>();
        ArrayList<Double> powerValues = new ArrayList<Double>();
        ArrayList<Double> positionValues = new ArrayList<Double>();
        ArrayList<Double> targetPowerValues = new ArrayList<Double>();
        ArrayList<Double> headingValues = new ArrayList<Double>();

        speed = range(speed);
        //drive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //drive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        drive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //drive.encodersZero();
        strafeOffset = drive.getStrafePosition();

        double angleError=0.0;
        double angleCorrection=0.0;
        double angleCorrectionGain=0.007*10;

        double currentHeading, turn=0.0;
        //resetAngleToZero();

        int loop = 0;

        RecTelemTime.reset();

        //telemetry.addData(INFO, "Strafe encoder position: " + (drive.getStrafePosition()-strafeOffset));
        //telemetry.update();

        runtime.reset();

        //while (((distanceTravelled < destination) && opModeIsActive())){
        while ((!atPosition(destination, distanceTravelled, error)) && opModeIsActive() && (runtime.milliseconds()< period)){

            if (segment.isDone()) {
                setPower(0, 0);
                return;
            }
            if (!segment.isRunning()) {
                setPower(0, 0);
                continue;
            }

            //  Get new location values
            //distanceTravelled = ((double) (drive.getBackLeftPosition())*switchDirection) / STRAIGHT_COUNTS_PER_INCH;
            distanceTravelled =  (drive.getStrafePosition()-strafeOffset)*switchDirection;
            distanceError = destination - distanceTravelled;
            //telemetry.addData(INFO, "Strafe encoder position: " + (drive.getStrafePosition()-strafeOffset));
            //telemetry.update();

            // Calculate new power based on location in path
            power = 1.0;
            //power = distancePID.output(destination, distanceTravelled);
            //power = strafeTrapezoid.getPower(segmentName,distanceError,distanceTravelled);

            // Heading correction
            currentHeading = getHeading();
            if (angle - currentHeading > 180) {
                turn = anglePID.output(angle, 360 + currentHeading);
            } else if (currentHeading - angle > 180) {
                turn = anglePID.output(angle, angle - (360 - (currentHeading - angle)));
            } else {
                turn = anglePID.output(angle, currentHeading);
            }
            turn = 0.0;
            /*if ((Math.abs(distanceTravelled)>2.0) && (Math.abs(distanceError)>(2.0)))
                angleCorrection = angleError * angleCorrectionGain;
             else
                angleCorrection = 0.0;*/
            //headingValues.add(angleError);
            //angleCorrection = angleError * angleCorrectionGain;

            //   Set power according to corrections
            /*if (power > 0) {
                leftPower = range(power * (speed - turn));
                rightPower = range(power * (speed + turn));
            } else {
                leftPower = range(power * (speed + turn));
                rightPower = range(power * (speed - turn));
            }*/
            leftPower = range((speed*power*switchDirection) );
            rightPower = range((speed*power*switchDirection));
            //leftPower = power*switchDirection + angleCorrection;
            //rightPower = (power*switchDirection - angleCorrection) * driveLeftCorrection;

            drive.setDrivePowerAll(leftPower+turn,-rightPower-turn,-leftPower+turn,rightPower-turn);

            //  Fill arrays to be graphed in smart Dashboard

            //targetPowerValues.add(angleCorrection);
            // powerValues.add(power);
            headingValues.add(currentHeading);
            positionValues.add(distanceTravelled);
            //telemetry.addData(INFO, "Power level   " +  power);
            //telemetry.addData(INFO, "Straight encoder position: " + drive.getStraightPosition()/STRAIGHT_COUNTS_PER_INCH);
            //telemetry.update();
            loop++;

        } //end of while loop
        drive.stop();

        if (alignHeading)    realignHeading();
        //realignHeading();

        drive.stop();

        telemetry.addData(INFO, "Time for drive: " + runtime.milliseconds());
        telemetry.addData(INFO, "Average loop time for drive: " + runtime.milliseconds() / loop);
        telemetry.addData(INFO, "Loop count " + loop);
        telemetry.addData(INFO, "Strafe encoder goal: " + distance );
        //telemetry.addData(INFO, "Straight encoder position: " + (drive.getStraightPosition()-strafeOffset));
        telemetry.addData(INFO, "Strafe encoder position: " + (drive.getStrafePosition()-strafeOffset));
        //telemetry.addData(INFO, "Angle heading   " +  getHeading());
        telemetry.update();

        //  Make smart Dashboard plots
        /*for(int i=0; i< positionValues.size();++i){
            //telemetry.getSmartdashboard().putGraph("Turbo", "Power", positionValues.get(i), 60.0*powerValues.get(i));
            telemetry.getSmartdashboard().putGraph("Turbo", "Heading", positionValues.get(i), headingValues.get(i));
            //telemetry.getSmartdashboard().putGraph("Turbo", "Velocity", positionValues.get(i), velocityValues.get(i));
            //telemetry.getSmartdashboard().putGraph("Turbo", "Target Velocity", positionValues.get(i), targetVelocityValues.get(i));
        }*/

        //drive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }


    public synchronized void angleDriveToSegment(AngleDriveSegment segment){

        telemetry.addData(INFO, "Angle Drive Segment is starting");
        telemetry.addData(INFO, "");
        telemetry.update();


        double distance = segment.getDistance(), speed = segment.getSpeed(), angle = baseHeading;
        String segmentName = segment.getName();
        double error = segment.getError();
        //double anglereset = segment.getError();
        double period = 50000;
        double switchDirection = 1.0;
        if (distance<0) switchDirection = -1.0;
        double destination = (distance*switchDirection);

        //boolean alignHeading = segment.getAlignHeading();
        distancePID.reset();

        double leftPower=0.0, rightPower=0.0;
        double power = 0.0;
        double velocity = 0.0, oldTime = 0.0;
        double oldDistance = 0;
        double straightOffset = 0.0;
        double strafeOffset = 0.0;

        double strafePower = 0.0;

        StrafeTrapezoid strafeTrapezoid = new StrafeTrapezoid();

        double driveLeftCorrection = 1.0;

        double distanceTravelled = 0.0;
        double distanceError = destination - distanceTravelled;

        ArrayList<Double> velocityValues = new ArrayList<Double>();
        ArrayList<Double> powerValues = new ArrayList<Double>();
        ArrayList<Double> positionValues = new ArrayList<Double>();
        ArrayList<Double> targetPowerValues = new ArrayList<Double>();
        ArrayList<Double> headingValues = new ArrayList<Double>();

        //drive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //drive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        drive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //drive.encodersZero();
        straightOffset = drive.getStraightPosition();
        strafeOffset = drive.getStrafePosition();

        double angleError=0.0;
        double angleCorrection=0.0;
        double angleCorrectionGain=0.007*10;

        double currentHeading, turn=0.0;
        //resetAngleToZero();

        int loop = 0;

        //resetAngleToZero();


        RecTelemTime.reset();
        telemetry.addData(INFO, "Straight encoder position: " + (drive.getStraightPosition()-straightOffset));
        telemetry.addData(INFO, "Strafe encoder position: " + (drive.getStrafePosition()-strafeOffset));
        telemetry.update();
        runtime.reset();

        //while ((distanceTravelled < (destination*switchDirection) && opModeIsActive())){
        while ((!atPosition(destination, distanceTravelled, error)) && opModeIsActive() && (runtime.milliseconds()< period)){
            if (segment.isDone()) {
                setPower(0, 0);
                return;
            }
            if (!segment.isRunning()) {
                setPower(0, 0);
                continue;
            }
            //  Get new location values

            distanceTravelled =  (drive.getStraightPosition()-straightOffset)*switchDirection;
            distanceError = destination - distanceTravelled;

            //   Calculate velocity, if needed

            // velocity = (1000.0*(double)(distanceTravelled - oldDistance))/((double)System.currentTimeMillis()-oldTime);
            // oldTime = (double) System.currentTimeMillis();
            // oldDistance = distanceTravelled;

            // Calculate new power based on location in path
            power = 1.0;

            //power = distancePID.output(destination, distanceTravelled);
            // power = straightTrapezoid.getPower(segmentName,distanceError,distanceTravelled);


            //power = straightTrapezoid.getPower("test1",distanceError,distanceTravelled);
            //power = strafeTrapezoid.getPower("test1",distanceError,distanceTravelled);

             // Heading correction

                currentHeading = getHeading();
                if (angle - currentHeading > 180) {
                    turn = anglePID.output(angle, 360 + currentHeading);
                } else if (currentHeading - angle > 180) {
                    turn = anglePID.output(angle, angle - (360 - (currentHeading - angle)));
                } else {
                    turn = anglePID.output(angle, currentHeading);
                }
                turn = 0.0;

            /*if ((Math.abs(distanceTravelled)>2.0) && (Math.abs(distanceError)>(2.0)))
                angleCorrection = angleError * angleCorrectionGain;
             else
                angleCorrection = 0.0;*/
                //headingValues.add(angleError);
                //angleCorrection = angleError * angleCorrectionGain;
                // angleError = getHeading();

                //   Set power according to corrections
            /*if (power > 0) {
                leftPower = range(power * (speed - turn));
                rightPower = range(power * (speed + turn));
            } else {
                leftPower = range(power * (speed + turn));
                rightPower = range(power * (speed - turn));
            }*/
            //leftPower = range((speed*power*switchDirection) + turn);
            //rightPower = range((speed*power*switchDirection) - turn);
            //drive.setDrivePowerAll(leftPower, rightPower , leftPower , rightPower);

            power = 0.3;
            //drive.setDrivePowerAll(leftPower, rightPower , leftPower , rightPower);
            //drive.setDrivePowerAll(leftPower,-rightPower,(-leftPower)*0.91,rightPower);
            if (distance<0)
                drive.setDrivePowerAll(-power,0,0,-power);
            else
                drive.setDrivePowerAll(0,power,power,0);

            //telemetry.getSmartdashboard().putGraph("Turbo", "Power",distanceTravelled,leftPower);

            loop++;
            // telemetry.addData(INFO, "Angle heading   " +  angleError);
            // telemetry.update();

        } //end of while loop

        drive.stop();

        telemetry.addData(INFO, "Time for drive: " + runtime.milliseconds());
        telemetry.addData(INFO, "Average loop time for drive: " + runtime.milliseconds() / loop);
        telemetry.addData(INFO, "Loop count " + loop);
        telemetry.addData(INFO, "Straight encoder goal: " + distance );
        telemetry.addData(INFO, "Straight encoder position: " + (drive.getStraightPosition()-straightOffset));
        telemetry.addData(INFO, "Strafe encoder position: " + (drive.getStrafePosition()-strafeOffset));
        telemetry.addData(INFO, "Angle heading   " +  getHeading());
        telemetry.update();

        //drive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    public synchronized void turnToSegment(TurnSegment segment) {

        double angle = segment.getAngle(), speed = segment.getSpeed(), error = segment.getError(), period = segment.getPeriod();

        telemetry.addData(INFO, "___________________");
        telemetry.addData(INFO, "");
        telemetry.addData(INFO, "");
        telemetry.addData(INFO, "Angle = " + segment.getAngle() + " Speed = " + segment.getSpeed()
                + " error = " + segment.getError() + " Period = " + segment.getPeriod());
        if (angle > 180) {
            baseHeading = angle - 360;
        } else if (angle < -180) {
            baseHeading = angle + 360;
        } else {
            baseHeading = angle;
        }

        telemetry.addData(INFO, "Angle: " + angle);
        telemetry.addData(INFO, "Baseheading: " + baseHeading);
        telemetry.addData(INFO, "Initial Angle is: " + getHeading());
        telemetry.update();

        straightPID.reset();
        //drive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        drive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        double power;
        while (opModeIsActive()) {

            double currentHeading;
            int angleCall = 0;
            int loop = 0;
            runtime.reset();

            //While we are not in the error band keep turning
            while ((getHeading()>angle) && opModeIsActive()) {

                if (segment.isDone()) {
                    setPower(0, 0);
                    return;
                }
                if (!segment.isRunning()) {
                    setPower(0, 0);
                    continue;
                }

                //Use the PIDController class to calculate power values for the wheels
               /*  if (angle > 180) {
                    angleCall = 1;
                    power = straightPID.output(angle, currentHeading < 0 && currentHeading < baseHeading + 30 ? 360 + currentHeading : currentHeading);
                } else if (angle < -180) {
                    angleCall = 2;
                    power = straightPID.output(angle, currentHeading > 0 && currentHeading > baseHeading - 30 ? currentHeading - 360 : currentHeading);
                } else if (angle - currentHeading > 180) {
                    angleCall = 3;
                    power = straightPID.output(angle, 360 + currentHeading);
                } else if (currentHeading - angle > 180) {
                    angleCall = 4;
                    power = straightPID.output(angle, angle - (360 - (currentHeading - angle)));
                } else {
                    angleCall = 5;
                    power = straightPID.output(angle, currentHeading);
                }*/
                power = speed;
                if (power > speed) power = speed;
                if (power < -speed) power = -speed;
                //power = 0.3;

                //telemetry.addData(INFO, "Final angle: " + DF.format(getHeading()));
                //telemetry.update();

                /*telemetry.addData(INFO,
                        "                                                                                                                                 call = "
                                + angleCall + " Power = " + DF.format(power) + " Angle = " + getHeading());*/
                //if (angle > 0)
                //    drive.setDrivePowerAll(0, power,0,power);
               // else
                //    drive.setDrivePowerAll(power, 0,power,0);
                drive.setDrivePowerAll(0, -power,0,-power);
                loop++;
            }
            drive.stop();
            //runtime.reset();
            telemetry.addData(INFO, "Average loop time for turn: " + DF.format(runtime.milliseconds() / loop)
                    + " Time = " + runtime.milliseconds() + " Loops = " + loop);

            //while (runtime.milliseconds() < period) {
            //    if ((abs(getHeading() - baseHeading)) > error && (abs(getHeading() + baseHeading)) > error) break;
            //}
            //if ((abs(getHeading() - baseHeading)) > error && (abs(getHeading() + baseHeading)) > error) continue;


            telemetry.addData(INFO, "");
            telemetry.addData(INFO, "Average loop time for turn: " + DF.format(runtime.milliseconds() / loop)
                    + " Time = " + runtime.milliseconds() + " Loops = " + loop);
            //telemetry.addData(INFO, "Left encoder position: " + DF.format(drive.getFrontLeftPosition()) + "  Right encoder position: " + DF.format(drive.getFrontRightPosition()));
            telemetry.addData(INFO, "Final angle: " + DF.format(getHeading()));
            telemetry.update();

            //drive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            //return;
        }
        //drive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }


    public void approachWall(double power, int threshold){
        double oldDistance;

        drive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        drive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        oldDistance = drive.getBackRightPosition();
        drive.setPower(power, power);

        delay(100);
        while(threshold< Math.abs(oldDistance-drive.getBackRightPosition())){
            oldDistance = drive.getBackRightPosition();
            delay(10);
        }
        drive.setPower(0,0);
    }

    public void approachFoundation(double power, int loopTime, double velThreshold) {

        double slowPower = 0.17;
        double oldDistance;
        double velocity;
        double oldvelocity = 8.0;
        double oldTime = 0.0;
        int loop = 0;
        //int loopTime = 1;
        int startTime = 350;
        double spaceBuffer = 0.18;
        ArrayList<Double> locations = new ArrayList<Double>();
        ArrayList<Double> velocities = new ArrayList<Double>();

        drive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        drive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        oldTime =  (double) System.currentTimeMillis();
        //oldDistance = -drive.getBackLeftPosition()/STRAIGHT_COUNTS_PER_INCH;
        oldDistance = drive.getBackRightPosition()/STRAIGHT_ENCODER_COUNTS_INCH;
        //locations.add(oldDistance);
        //strafe(-power);
        drive.setDrivePowerAll(power,power,power,power);
        delay(startTime);
        //velocity = 1000.0*((-drive.getBackLeftPosition()/STRAIGHT_COUNTS_PER_INCH) - oldDistance)/((double)System.currentTimeMillis()-oldTime);
        velocity = 1000.0*((drive.getBackRightPosition()/STRAIGHT_ENCODER_COUNTS_INCH) - oldDistance)/((double)System.currentTimeMillis()-oldTime);
        oldTime =  (double) System.currentTimeMillis();
        //oldDistance = -drive.getBackLeftPosition()/STRAIGHT_COUNTS_PER_INCH;
        oldDistance = drive.getBackRightPosition()/STRAIGHT_ENCODER_COUNTS_INCH;
        locations.add(oldDistance);
        velocities.add(velocity);
        runtime.reset();
        //while (((-drive.getBackLeftPosition()/STRAIGHT_COUNTS_PER_INCH) - oldDistance) > spaceBuffer) {
        while ((velocity+oldvelocity) > (2*velThreshold)){

            delay(loopTime);

            //velocity = 1000.0*((-drive.getBackLeftPosition()/STRAIGHT_COUNTS_PER_INCH) - oldDistance)/((double)System.currentTimeMillis()-oldTime);
            velocity = 1000.0*((drive.getBackRightPosition()/STRAIGHT_ENCODER_COUNTS_INCH) - oldDistance)/((double)System.currentTimeMillis()-oldTime);
            oldTime = (double) System.currentTimeMillis();
            //oldDistance = -drive.getBackLeftPosition()/STRAIGHT_COUNTS_PER_INCH;
            oldDistance = drive.getBackRightPosition()/STRAIGHT_ENCODER_COUNTS_INCH;
            //telemetry.addData(INFO, "Side encoder position: " + drive.getBackLeftPosition() / STRAIGHT_COUNTS_PER_INCH);
            //telemetry.update();
            locations.add(oldDistance);
            velocities.add((velocity+oldvelocity)/2);
            oldvelocity = velocity;
            ++loop;
        }
        drive.stop();
        locations.add(oldDistance);
        velocities.add((velocity+oldvelocity)/2);
        //locations.add(-drive.getBackLeftPosition()/STRAIGHT_COUNTS_PER_INCH);
        for(int i=0; i < locations.size();++i) {
            telemetry.addData(INFO, "Next stage locations   " + locations.get(i));
            telemetry.getSmartdashboard().putGraph("Turbo", "Power",locations.get(i),velocities.get(i));
        }
        telemetry.update();
        //delay(9000);
        telemetry.addData(INFO, "Time for drive: " + runtime.milliseconds());
        telemetry.addData(INFO, "Average loop time for drive: " + runtime.milliseconds() / loop);
        telemetry.addData(INFO, "Loop count " + loop);
        telemetry.addData(INFO, "Side encoder position: " + drive.getBackLeftPosition() / STRAIGHT_ENCODER_COUNTS_INCH);
        telemetry.update();
    }

    public void strafe(double power) {


        drive.setDrivePowerAll(power, -power,-power,power);

    }

    public synchronized void realignHeading(){

        double ROTATE_MAX_POWER = 0.1;
        double angleError;
        double power ;

        delay(400);     //wait for skid twist to finish
        angleError = getHeading();

        while (angleError>1.0 || angleError<-1.0) {

            angleError = getHeading();
            power = ROTATE_MAX_POWER*angleError;
            if (power>ROTATE_MAX_POWER)
                power = ROTATE_MAX_POWER;
            if(power<-ROTATE_MAX_POWER)
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

    public synchronized void setDrivePowerAll(double FL,double FR,double BL,double BR){
        drive.setDrivePowerAll(FL,FR,BL,BR);
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
        if (val<0.13&&val>0) val = 0.13;
        if (val>-0.13&&val<0) val = -0.13;
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

    public void resetPosition() {
        drive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
}