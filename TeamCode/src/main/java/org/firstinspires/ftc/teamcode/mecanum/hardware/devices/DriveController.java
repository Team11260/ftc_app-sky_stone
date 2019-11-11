package org.firstinspires.ftc.teamcode.mecanum.hardware.devices;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.framework.userhardware.DoubleTelemetry;
import org.firstinspires.ftc.teamcode.framework.userhardware.PIDController;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.DriveSegment;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.Path;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.Segment;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.StrafeSegment;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.TurnSegment;
import org.firstinspires.ftc.teamcode.framework.util.SubsystemController;
import org.upacreekrobotics.dashboard.Config;

import java.text.DecimalFormat;

import static java.lang.Math.abs;
import static java.lang.Math.pow;
import static org.firstinspires.ftc.teamcode.framework.userhardware.DoubleTelemetry.LogMode.INFO;
import static org.firstinspires.ftc.teamcode.mecanum.hardware.Constants.STRAIGHT_COUNTS_PER_INCH;

@Config
public class DriveController extends SubsystemController {

    private Drive drive;

    private PIDController anglePID, straightPID, distancePID, strafePID;

    private double baseHeading = 0;

    private double turnY = 0, turn_z = 0, leftPower = 0, rightPower = 0, Drive_Power = 1.0;

    private ElapsedTime runtime;

    private DecimalFormat DF;

    public static Path currentPath = null;

    private double DRIVE_COUNTS_PER_INCH = 189;//1440*x/(2.25pi)
    private double STRAFE_COUNTS_PER_INCH = 196;

    public static double PATH_P = 15, PATH_F = 5;

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
        anglePID = new PIDController(1, 0.01, 1, 0.3, 0.1);//D was 150
        //anglePID.setLogging(true);
        straightPID = new PIDController(5, 0.000, 0.01, 1, 0);
        distancePID = new PIDController(PROP_GAIN,INT_GAIN ,DIFF_GAIN , 2, 0.1);
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
         telemetry.addData(DoubleTelemetry.LogMode.TRACE, "Left drive position: " + drive.getFrontLeftPosition());
        telemetry.addData(DoubleTelemetry.LogMode.TRACE, "Right drive position: " + drive.getFrontRightPosition());
        drive.update();
        telemetry.addData(INFO, "X:" + drive.getCurrentPosition().getX() + "  Y: " + drive.getCurrentPosition().getY());


    }

    public synchronized void stop() {
        drive.stop();
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

        telemetry.addData(INFO, "Starting path: " + currentPath.getName() + "  paused: " + currentPath.isPaused() + "  done: " + currentPath.isDone());

        while (!path.isDone() && opModeIsActive()) {

            //SmoothPath is done
            if (path.getNextSegment() == null) break;

            telemetry.addData(INFO, "Starting segment: " + path.getCurrentSegment().getName() + " in path: " + currentPath.getName() + "  paused: " + currentPath.isPaused() + "  done: " + currentPath.isDone());

            if (path.getCurrentSegment().getType() == Segment.SegmentType.TURN) {
                turnToSegment((TurnSegment) path.getCurrentSegment());
            } else if (path.getCurrentSegment().getType() == Segment.SegmentType.DRIVE) {
                driveToSegment((DriveSegment) path.getCurrentSegment());
            }
            else if (path.getCurrentSegment().getType() == Segment.SegmentType.STRAFE){
                strafeToSegmentX((StrafeSegment) path.getCurrentSegment());
            }

            telemetry.addData(INFO, "Finished segment: " + path.getCurrentSegment().getName() + " in path: " + currentPath.getName() + "  paused: " + currentPath.isPaused() + "  done: " + currentPath.isDone());
        }

        telemetry.addData(INFO, "Finished path: " + currentPath.getName() + "  paused: " + currentPath.isPaused() + "  done: " + currentPath.isDone());
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

        straightPID.reset();
        drive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        double power;
        while (opModeIsActive()) {

            double currentHeading;
            int angleCall = 0;
            int loop = 0;
            runtime.reset();

            //While we are not in the error band keep turning
            while ((getHeading()<angle) && opModeIsActive()) {

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
                power = 0.4;
                if (power > speed) power = speed;
                if (power < -speed) power = -speed;
                //power = 0.3;

                /*telemetry.addData(INFO,
                        "                                                                                                                                 call = "
                                + angleCall + " Power = " + DF.format(power) + " Angle = " + getHeading());*/
                if (angle > 0)
                    drive.setDrivePowerAll(0, power,0,power);
                else
                    drive.setDrivePowerAll(power, 0,power,0);

                loop++;
            }

            runtime.reset();

            while (runtime.milliseconds() < period) {
                if ((abs(getHeading() - baseHeading)) > error && (abs(getHeading() + baseHeading)) > error) break;
            }
            if ((abs(getHeading() - baseHeading)) > error && (abs(getHeading() + baseHeading)) > error) continue;


            telemetry.addData(INFO, "");
            telemetry.addData(INFO, "Average loop time for turn: " + DF.format(runtime.milliseconds() / loop)
                    + " Time = " + runtime.milliseconds() + " Loops = " + loop);
            telemetry.addData(INFO, "Left encoder position: " + DF.format(drive.getFrontLeftPosition()) + "  Right encoder position: " + DF.format(drive.getFrontRightPosition()));
            telemetry.addData(INFO, "Final angle: " + DF.format(getHeading()));
            telemetry.update();

            drive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            return;
        }
        drive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public synchronized void driveToSegmentOld(DriveSegment segment) {
        telemetry.addData(INFO, "Drive Segment is starting");
        telemetry.addData(INFO, "");
        telemetry.addData(INFO, "");
        telemetry.addData(INFO, "");

        //AbstractOpMode.delay(100);

        double distance = segment.getDistance(), speed = segment.getSpeed(), angle = baseHeading;
        if (segment.getAngle() != null) angle = segment.getAngle();
        int error = segment.getError();

        baseHeading = angle;

        straightPID.reset(); //Resets the PID values in the PID class to make sure we do not have any left over values from the last segment
        distancePID.reset();
        straightPID.setMinimumOutput(0);
        int position = (int) (distance * DRIVE_COUNTS_PER_INCH); //
        double turn;
        speed = range(speed);
        drive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //drive.setPositionP(5);
        telemetry.update();
        double leftPower, rightPower;
        double power;

        double currentHeading;

        int loop = 0;
        runtime.reset();
        resetAngleToZero();
        RecTelemTime.reset();
        while ((!atPosition(position, drive.getFrontLeftPosition(), error) && opModeIsActive())) {


         //   telemetry.addData(INFO,"Entered driveSegement Loop");
         //   telemetry.update();
            /*if (segment.isDone()) {
                setPower(0, 0);
                return;
            }
            if (!segment.isRunning()) {
                setPower(0, 0);
                continue;
            }*/

            currentHeading = getHeading();

            power = range(distancePID.output(position, (drive.getFrontLeftPosition())));

          //  telemetry.addData(INFO,"Power "+power);
          //  telemetry.update();
            if (angle - currentHeading > 180) {
                turn = anglePID.output(angle, 360 + currentHeading);
            } else if (currentHeading - angle > 180) {
                turn = anglePID.output(angle, angle - (360 - (currentHeading - angle)));
            } else {
                turn = anglePID.output(angle, currentHeading);
            }

            if (power > 0) {
                leftPower = range(power * (speed - turn));
                rightPower = range(power * (speed + turn));
            } else {
                leftPower = range(power * (speed + turn));
                rightPower = range(power * (speed - turn));
            }

            drive.setPower(leftPower, rightPower*1.05);
            if ( loop == 0) { RecTelem[loop].Time = RecTelemTime.milliseconds(); }
            else            {  RecTelem[loop].Time = RecTelemTime.milliseconds() - previousTime; }

            RecTelem[loop].Angle = currentHeading;
            RecTelem[loop].Error = angle - currentHeading;
            RecTelem[loop].LeftPower = leftPower;
            RecTelem[loop].RightPower = rightPower;
            RecTelem[loop].BackLeftCount = 5;//drive.getFrontLeftPosition(); // These cost us time (10ms)
            RecTelem[loop].BackRightCount = 6;//drive.getFrontRightPosition();//^
            previousTime = RecTelemTime.milliseconds();

            loop++;
          //  telemetry.addData(INFO,"Loop :"+loop+" "+drive.getFrontLeftPosition()+"" +
            //                                      ""+drive.getFrontRightPosition());
          //  telemetry.update();
        } //end of while loop
        drive.stop();
        /*for ( int n = 0; n < loop; n++ )
        {
            telemetry.addData(INFO, "index = " + DFpwr.format( RecTelem[n].Index)
                    + " deltatime = " + DFpwr.format( RecTelem[n].Time)
                    + " angle = " + DFpwr.format(RecTelem[n].Angle)
                    + " error = " + DFpwr.format(RecTelem[n].Error)
                    + "   left = " + DFpwr.format(RecTelem[n].LeftPower)
                    + " right = " + DFpwr.format(RecTelem[n].RightPower)
                    + "    left enc = " +DFenc.format(RecTelem[n].BackLeftCount)
                    + "    right enc = " +DFenc.format(RecTelem[n].BackRightCount)
            );
        }*/
        telemetry.addData(INFO, "Average loop time for drive: " + runtime.milliseconds() / loop);
        telemetry.addData(INFO, "Loop count " + loop);
        telemetry.addData(INFO, "Left encoder position: " + drive.getFrontLeftPosition() + "  Right encoder position: " + drive.getFrontRightPosition());
        telemetry.addData(INFO, "Final angle: " + getHeading());
        //telemetry.update();


        //delay(1000);
        telemetry.addData(INFO, "Left encoder position: " + drive.getFrontLeftPosition() + "  Right encoder position: " + drive.getFrontRightPosition());
        telemetry.update();
        //drive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    public synchronized void driveToSegment(DriveSegment segment) {

        telemetry.addData(INFO, "Drive Segment is starting");
        telemetry.addData(INFO, "");
        telemetry.update();

        double distance = segment.getDistance(), speed = segment.getSpeed(), angle = baseHeading;

        double leftPower=0.0, rightPower=0.0;
        double power = 0.0;
        double strafePower = 0.0;
        double velocity = 0.0, oldDistance = 0.0, oldTime = 0.0;
        StraightTrapezoid straightTrapezoid = new StraightTrapezoid();

        double destination =  (distance-1 );
        double driveLeftCorrection = 1.09;

        double distanceTravelled = 0.0;
        double distanceError = destination - distanceTravelled;

        double anglePropCorr=0.0, angleIntCorr=0.0;
        double anglePropGain = 1.0, angleIntGain = 1.0;
        double anglePowerCorr = 0.0;

        speed = range(speed);
        drive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        drive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        double angleError=0.0;
        double angleCorrection=0.0;
        double angleCorrectionGain=0.007;
        double SLIDE_OFFSET = 2.0;
        //resetAngleToZero();
        double switchDirection = 1.0;
        int loop = 0;
        if (destination<0) switchDirection = -1.0;


        RecTelemTime.reset();

        telemetry.addData(INFO, "Straight encoder position: " + drive.getBackRightPosition());
        telemetry.addData(INFO, "Side encoder position: " + drive.getBackLeftPosition());
        telemetry.update();
        runtime.reset();


        //if (destination>0) {
            while ((distanceTravelled < ((destination-SLIDE_OFFSET)*switchDirection) && opModeIsActive())){

                if (segment.isDone()) {
                    setPower(0, 0);
                    return;
                }
                if (!segment.isRunning()) {
                    setPower(0, 0);
                    continue;
                }

                power = straightTrapezoid.getPower("test1",distanceError,distanceTravelled);

                angleError = getHeading();

                if (distanceError>4.0 && destination>0)
                    angleCorrection = angleError * angleCorrectionGain;
                else
                    angleCorrection = 0.0;

                leftPower = power*switchDirection + angleCorrection;
                rightPower = (power*switchDirection - angleCorrection) * driveLeftCorrection;

                drive.setDrivePowerAll(leftPower, rightPower , leftPower , rightPower);

                distanceTravelled = (double) (drive.getBackRightPosition()) / STRAIGHT_COUNTS_PER_INCH;

                distanceTravelled = distanceTravelled * switchDirection;
                distanceError = (destination*switchDirection) - distanceTravelled;
                velocity = (distanceTravelled - oldDistance)/((double)runtime.milliseconds()-oldTime);
                oldTime = (double) runtime.milliseconds();
                oldDistance = distanceTravelled;
                telemetry.getSmartdashboard().putGraph("Turbo", "Power",distanceTravelled,power);
                telemetry.getSmartdashboard().putGraph("Turbo", "Velocity",distanceTravelled,10.0*velocity);

                loop++;
                // telemetry.addData(INFO, "Angle heading   " +  angleError);
                // telemetry.update();

            } //end of while loop

        for(int i=0; i<40; ++i) {
            distanceTravelled = (double) (drive.getBackRightPosition()) / STRAIGHT_COUNTS_PER_INCH;
            velocity = (distanceTravelled - oldDistance)/((double)runtime.milliseconds()-oldTime);
            oldTime = (double) runtime.milliseconds();
            oldDistance = distanceTravelled;

            telemetry.getSmartdashboard().putGraph("Turbo", "Power", distanceTravelled, power);
            telemetry.getSmartdashboard().putGraph("Turbo", "Velocity", distanceTravelled, 10.0 * velocity);
            delay(50);
        }

        delay(200);

            power = 0.08;

            while (getHeading()>1.0 || getHeading()<-1.0) {

                angleError = getHeading();
                if (angleError>0)
                    leftPower = power;
                else
                    leftPower = -power;
                rightPower = -leftPower;
                drive.setDrivePowerAll(leftPower, rightPower, leftPower, rightPower);
            }

        drive.stop();
        delay(500);

        telemetry.addData(INFO, "Time for drive: " + runtime.milliseconds());
        telemetry.addData(INFO, "Average loop time for drive: " + runtime.milliseconds() / loop);
        telemetry.addData(INFO, "Loop count " + loop);
        telemetry.addData(INFO, "Straight encoder goal: " + distance );
        telemetry.addData(INFO, "Straight encoder position: " + drive.getBackRightPosition()/STRAIGHT_COUNTS_PER_INCH);
        telemetry.addData(INFO, "Side encoder position: " + drive.getBackLeftPosition()/STRAIGHT_COUNTS_PER_INCH);
        telemetry.addData(INFO, "Angle heading   " +  getHeading());
        telemetry.update();


        delay(9000);
        //drive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public synchronized void strafeToSegmentX(StrafeSegment segment) {

        telemetry.addData(INFO, "Drive Segment is starting");
        telemetry.addData(INFO, "");
        telemetry.update();

        double distance = segment.getDistance(), speed = segment.getSpeed(), angle = baseHeading;

        double leftPower=0.0, rightPower=0.0;
        double power = 0.0;
        double strafePower = 0.0;
        StraightTrapezoid straightTrapezoid = new StraightTrapezoid();
        StrafeTrapezoid strafeTrapezoid = new StrafeTrapezoid();


        double destination =  (distance-1 );
        double driveLeftCorrection = 1.09;

        double distanceTravelled = 0.0;
        double distanceError = destination - distanceTravelled;

        double anglePropCorr=0.0, angleIntCorr=0.0;
        double anglePropGain = 1.0, angleIntGain = 1.0;
        double anglePowerCorr = 0.0;

        speed = range(speed);
        drive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        drive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        double angleError=0.0;
        double angleCorrection=0.0;
        double angleCorrectionGain=0.007;
        //resetAngleToZero();
        double switchDirection = 1.0;
        int loop = 0;
        if (destination<0) switchDirection = -1.0;


        RecTelemTime.reset();

        telemetry.addData(INFO, "Straight encoder position: " + drive.getBackRightPosition());
        telemetry.addData(INFO, "Side encoder position: " + drive.getBackLeftPosition());
        telemetry.update();
        runtime.reset();


        //if (destination>0) {
        while ((distanceTravelled < (destination*switchDirection) && opModeIsActive())){

            if (segment.isDone()) {
                setPower(0, 0);
                return;
            }
            if (!segment.isRunning()) {
                setPower(0, 0);
                continue;
            }

            //power = straightTrapezoid.getPower("test1",distanceError,distanceTravelled);
            power = strafeTrapezoid.getPower("test1",distanceError,distanceTravelled);


            angleError = getHeading();


            if (distanceError>4.0 && destination>0)
                angleCorrection = angleError * angleCorrectionGain;
            else
                angleCorrection = 0.0;

            if (distanceError>4.0 && destination>0)
                angleCorrection = angleError * angleCorrectionGain;
            else
                angleCorrection = 0.0;


            leftPower = power*switchDirection + angleCorrection;
            rightPower = (power*switchDirection - angleCorrection) * driveLeftCorrection;

            //drive.setDrivePowerAll(leftPower, rightPower , leftPower , rightPower);
            drive.setDrivePowerAll(leftPower,-rightPower,(-leftPower)*0.91,rightPower);

            //distanceTravelled = (double) (drive.getBackRightPosition()) / STRAIGHT_COUNTS_PER_INCH;
            distanceTravelled = (double) (drive.getBackLeftPosition()) / STRAIGHT_COUNTS_PER_INCH;
            distanceTravelled = distanceTravelled * switchDirection;
            distanceError = (destination*switchDirection) - distanceTravelled;
            telemetry.getSmartdashboard().putGraph("Turbo", "Power",distanceTravelled,leftPower);

            loop++;
            // telemetry.addData(INFO, "Angle heading   " +  angleError);
            // telemetry.update();

        } //end of while loop
        delay(200);

        power = 0.1;

        while (getHeading()>0.2 || getHeading()<-0.2) {

            angleError = getHeading();
            if (angleError>0)
                leftPower = power;
            else
                leftPower = -power;
            rightPower = -leftPower;
            drive.setDrivePowerAll(leftPower, rightPower, leftPower, rightPower);
        }

        drive.stop();

        telemetry.addData(INFO, "Time for drive: " + runtime.milliseconds());
        telemetry.addData(INFO, "Average loop time for drive: " + runtime.milliseconds() / loop);
        telemetry.addData(INFO, "Loop count " + loop);
        telemetry.addData(INFO, "Straight encoder goal: " + distance );
        telemetry.addData(INFO, "Straight encoder position: " + drive.getBackRightPosition()/STRAIGHT_COUNTS_PER_INCH);
        telemetry.addData(INFO, "Side encoder position: " + drive.getBackLeftPosition()/STRAIGHT_COUNTS_PER_INCH);
        telemetry.addData(INFO, "Angle heading   " +  getHeading());
        telemetry.update();

        //delay(9000);
        //drive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public synchronized void strafeToSegmentNew(StrafeSegment segment) {
        telemetry.addData(INFO, "Strafe Segment is starting");
        telemetry.addData(INFO, "");
        telemetry.addData(INFO, "");
        telemetry.addData(INFO, "");

        //AbstractOpMode.delay(100);

        double distance = segment.getDistance(), speed = segment.getSpeed(), angle = baseHeading;
        if (segment.getAngle() != null) angle = segment.getAngle();
        int error = segment.getError();

        baseHeading = angle;

        straightPID.reset(); //Resets the PID values in the PID class to make sure we do not have any left over values from the last segment
        distancePID.reset();
        strafePID.reset();
        straightPID.setMinimumOutput(0);
        int position = (int) ((distance) * STRAFE_COUNTS_PER_INCH); //
        double turn;
        speed = range(speed);
        drive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //drive.setPositionP(5);
        telemetry.update();
        double leftPower, rightPower;
        double power;
        double turnPower=0.0;
        int initialLocation=0;
        int finishPosition = (int)(10*DRIVE_COUNTS_PER_INCH);
        double currentHeading;

        int loop = 0;
        runtime.reset();

        RecTelemTime.reset();

        resetAngleToZero();
        while(getHeading()<30){

            setPower(0.1,0.4);
        }
        initialLocation = drive.getBackRightPosition();
        while (((drive.getBackRightPosition()-initialLocation)<(position-1.5*DRIVE_COUNTS_PER_INCH)) && opModeIsActive()) {

            leftPower = 0.3;
            rightPower = leftPower*1.25;
            setPower(leftPower,rightPower);

        } //end of while loop
        while(getHeading()>0){

            setPower(0.4,0.1);
        }
        initialLocation = drive.getBackRightPosition();
        while (((drive.getBackRightPosition()-initialLocation)<(finishPosition-1.5*DRIVE_COUNTS_PER_INCH)) && opModeIsActive()) {

            leftPower = 0.3;
            rightPower = leftPower*1.25;
            setPower(leftPower,rightPower);

        }
        drive.stop();
        telemetry.addData(INFO, "Average loop time for drive: " + runtime.milliseconds() / loop);
        telemetry.addData(INFO, "Loop count " + loop);
        telemetry.addData(INFO, "Stafe encoder goal: " + position);
        telemetry.addData(INFO, "Stafe encoder position: " + drive.getBackLeftPosition());
        telemetry.addData(INFO, "Straight encoder position: " + drive.getBackRightPosition());
        telemetry.update();
        telemetry.addData(INFO,"Loop :" + loop);
        telemetry.update();

        //drive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public synchronized void strafeToSegment(StrafeSegment segment) {
        telemetry.addData(INFO, "Strafe Segment is starting");
        telemetry.addData(INFO, "");
        telemetry.addData(INFO, "");
        telemetry.addData(INFO, "");

        //AbstractOpMode.delay(100);

        double distance = segment.getDistance(), speed = segment.getSpeed(), angle = baseHeading;
        if (segment.getAngle() != null) angle = segment.getAngle();
        int error = segment.getError();

        baseHeading = angle;
        double REAR_LEFT_WHEEL_CORRECTION = 0.91;

        straightPID.reset(); //Resets the PID values in the PID class to make sure we do not have any left over values from the last segment
        distancePID.reset();
        strafePID.reset();
        straightPID.setMinimumOutput(0);
        int position = (int) ((distance+1.0) * STRAFE_COUNTS_PER_INCH); //
        double turn=0.0;
        double factor = REAR_LEFT_WHEEL_CORRECTION;
        double straightPower=0.0;
        speed = range(speed);
        drive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //drive.setPositionP(5);
        telemetry.update();
        double leftPower, rightPower;
        double power;
        double turnPower=0.0;

        double currentHeading;

        int loop = 0;
        runtime.reset();

        RecTelemTime.reset();
        while ((drive.getBackLeftPosition()<(position-0.0*STRAFE_COUNTS_PER_INCH)) && opModeIsActive()) {

            //   telemetry.addData(INFO,"Entered driveSegement Loop");
            //   telemetry.update();
            if (segment.isDone()) {
                setPower(0, 0);
                return;
            }
            if (!segment.isRunning()) {
                setPower(0, 0);
                continue;
            }

            currentHeading = getHeading();


            if (drive.getBackLeftPosition()<(10*STRAFE_COUNTS_PER_INCH)) {
                rightPower = 0.15+drive.getBackLeftPosition()*(speed-0.15)/(10*STRAFE_COUNTS_PER_INCH);
                leftPower = rightPower;
            }
            else if ((position-drive.getBackLeftPosition())<(20*STRAFE_COUNTS_PER_INCH)){
                rightPower = 0.+(position-drive.getBackLeftPosition())*(speed)/(20*STRAFE_COUNTS_PER_INCH);
                leftPower = rightPower;
            }
            else {
                rightPower = speed;
                leftPower = speed;
            }
            //turnPower = rightPower*0.09-(drive.getBackRightPosition()/10000);
            // drive.setDrivePowerAll(leftPower-1.2*turnPower,-rightPower,-leftPower-1.2*turnPower,rightPower);
       ;    straightPower = 0.0;
           /* if ((position < 10*STRAFE_COUNTS_PER_INCH) && (position > -20*STRAFE_COUNTS_PER_INCH)) {

                if (distance > 0)
                    leftPower = 0.20;
                else
                    leftPower = -0.20;

                rightPower = leftPower;
                straightPower = 0.20;
            }*/

            factor = 0.91;
            drive.setDrivePowerAll(leftPower+straightPower,-rightPower+straightPower,(-leftPower+straightPower)*factor,rightPower+straightPower);

            loop++;

        } //end of while loop

        drive.stop();
        telemetry.addData(INFO, "Average loop time for drive: " + runtime.milliseconds() / loop);
        telemetry.addData(INFO, "Loop count " + loop);
        telemetry.addData(INFO, "Stafe encoder goal: " + position);
        telemetry.addData(INFO, "Stafe encoder position: " + drive.getBackLeftPosition());
        telemetry.addData(INFO, "Straight encoder position: " + drive.getBackRightPosition());
        telemetry.addData(INFO,"Angle heading  " + getHeading());
        telemetry.update();

        /*for ( int n = 0; n < loop; n++ )
        {
            telemetry.addData(INFO, "index = " + DFpwr.format( RecTelem[n].Index)
                    + " deltatime = " + DFpwr.format( RecTelem[n].Time)
                    + " angle = " + DFpwr.format(RecTelem[n].Angle)
                    + " error = " + DFpwr.format(RecTelem[n].Error)
                    + "   left = " + DFpwr.format(RecTelem[n].LeftPower)
                    + " right = " + DFpwr.format(RecTelem[n].RightPower)
                    + "    left enc = " +DFenc.format(RecTelem[n].BackLeftCount)
                    + "    right enc = " +DFenc.format(RecTelem[n].BackRightCount)
            );
        }*/

        //drive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void strafe(double power) {


        drive.setDrivePowerAll(-power, power,power,-power);

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
        currentPath.pause();
        telemetry.addData(INFO, "Pause path");
        drive.setMarkerServo(DRIVE_TEAM_MARKER_EXTENDED);
        delay(DRIVE_DUMP_TEAM_MARKER_DELAY);
        drive.setMarkerServo(DRIVE_TEAM_MARKER_RETRACTED);
        currentPath.resume();
        telemetry.addData(INFO, "Marker dumped");
    }*/

    public void resetPosition() {
        drive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
}