package org.firstinspires.ftc.teamcode.mecanum.opmodes.auton;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractAuton;
import org.firstinspires.ftc.teamcode.framework.userhardware.DoubleTelemetry;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.Path;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.PurePursuitSegment;
import org.firstinspires.ftc.teamcode.framework.userhardware.purepursuit.Point;
import org.firstinspires.ftc.teamcode.framework.userhardware.purepursuit.PursuitPath;
import org.firstinspires.ftc.teamcode.mecanum.hardware.Robot;
import org.firstinspires.ftc.teamcode.mecanum.hardware.util.ParameterFileConfiguration;

import static org.firstinspires.ftc.teamcode.framework.userhardware.DoubleTelemetry.LogMode.INFO;

@Autonomous(group = "new", name = "Blue Sixth Stone")

public class BlueSixthStone extends AbstractAuton {

    Robot robot;

    private int depotPathPeriod = 0;
    private int foundationPathPeriod = 0;
    String pos = "";


    private ParameterFileConfiguration ParameterFile;
    private int SixStoneDelay;



    @Override
    public void RegisterStates() {


        addState("sense stone", "drive to the blue depot", robot.SixthStoneCallable());
        addState("arm down", "sense stone", robot.armDownCallable());
        addState("open gripper", "sense stone", robot.setGripperReleaseCallable());
        addState("Pick up stone", "drive to stone", robot.grabStoneFullCallable());
        addState("drop off stone", "drive to foundation", robot.deliverStoneCallable());


    }

    @Override
    public void Init() {

        robot = new Robot();
        robot.arm.setArmUpPosition();
        robot.arm.setGripperGripPosition();
        robot.lift.setTiltUp();
        robot.dragger.setDraggerUp();
        robot.imageOn();
        ParameterFile = new ParameterFileConfiguration();
        SixStoneDelay = ParameterFile.getParamValueInt("CameraDelayTimeMsec", "700");

    }

    @Override
    public void Run() {

        robot.runDrivePath(driveToDepot());

        delay(SixStoneDelay);


        pos = robot.SixthStoneVis();

        if (pos.equals("Left")) {

            robot.runDrivePath(getRightStone());


        } else {

            robot.runDrivePath(getLeftStone());

        }


        telemetry.addData(INFO,pos);

        telemetry.update();


//        telemetry.addData(DoubleTelemetry.LogMode.INFO, "Is sixth stone: " + isSixth);
//
//        telemetry.update();


        robot.imageShutDown();

        robot.runDrivePath(goToBridge());

        robot.runDrivePath(goToFoundation());

        telemetry.addData(DoubleTelemetry.LogMode.INFO, "delay value = " + ParameterFile.getParamValueInt("CameraDelayTimeMsec"));
        telemetry.addData(DoubleTelemetry.LogMode.INFO, "fake value = " + ParameterFile.getParamValueInt("FakeName"));

    }


    @Override
    public void Stop() {
        super.Stop();
    }


    protected Path driveToDepot() {
        Path drive = new Path("drive to depot");


        double  DRIVETODEPOT_X = ParameterFile.getParamValueDouble("DRIVETODEPOT_X", "-68");
        double  DRIVETODEPOT_Y = ParameterFile.getParamValueDouble("DRIVETODEPOT_Y", "-4");

        double  DRIVETODEPOT_MAXDECEL = ParameterFile.getParamValueDouble("DRIVETODEPOT_MAXDECEL", "0.005");
        double  DRIVETODEPOT_MAXACEL = ParameterFile.getParamValueDouble("DRIVETODEPOT_MAXACEL", "0.04");
        double  DRIVETODEPOT_MAXSPEED = ParameterFile.getParamValueDouble("DRIVETODEPOT_MAXSPEED", "0.6");

        drive.addSegment(new PurePursuitSegment("drive to the blue depot",

                new PursuitPath(
                        new Point(0, 0),
                        new Point(DRIVETODEPOT_X, DRIVETODEPOT_Y)).setMaxDeceleration(DRIVETODEPOT_MAXDECEL).setMaxAcceleration(DRIVETODEPOT_MAXACEL).setMaxSpeed(DRIVETODEPOT_MAXSPEED), depotPathPeriod

        ));


        return drive;
    }


    protected Path getLeftStone() {
        Path getStone = new Path("get stone");

        getStone.addSegment(new PurePursuitSegment("drive to stone",
                new PursuitPath(
                        new Point(68, -4),
                        new Point(68, -25.5)


                ).setMaxDeceleration(0.005).setMaxAcceleration(0.04)

        ));

        getStone.addSegment(new PurePursuitSegment("drive back to depot",
                new PursuitPath(
                        new Point(66, -25.5),
                        new Point(68, -0.5)


                ).setMaxDeceleration(0.003).setMaxAcceleration(0.04).setMinSpeed(0.14), 1000, 0

        ));


        return getStone;


    }

    protected Path getRightStone() {

        Path getStone = new Path("get second stone");

        getStone.addSegment(new PurePursuitSegment("drive to stone",
                new PursuitPath(
                        new Point(68, -4),
                        new Point(61, -25.5)


                ).setMaxDeceleration(0.005).setMaxAcceleration(0.04)

        ));

        getStone.addSegment(new PurePursuitSegment("drive back to depot",
                new PursuitPath(
                        new Point(63, -25.5),
                        new Point(68, -0.5)


                ).setMaxDeceleration(0.003).setMaxAcceleration(0.04).setMinSpeed(0.14), 1000, 0));

        return getStone;


    }


    protected Path goToBridge() {
        Path driveToLine = new Path("drive to line");

        driveToLine.addSegment(new PurePursuitSegment("drive to red line",
                new PursuitPath(
                        new Point(68, -1),
                        new Point(10, -0.3)

                ).setMaxDeceleration(0.01).setMaxAcceleration(0.04).setMaxSpeed(0.45), 0


        ));


        return driveToLine;
    }


    protected Path goToFoundation() {
        Path driveToFoundation = new Path("foundation drive");

        driveToFoundation.addSegment(new PurePursuitSegment("drive to foundation",
                new PursuitPath(
                        new Point(10, -0.3),
                        new Point(-45, -0.3),
                        new Point(-47, -26)


                ).setPointSpacing(3).setMaxDeceleration(0.015).setMaxAcceleration(0.05).setMaxSpeed(0.5), foundationPathPeriod


        ));
        driveToFoundation.addSegment(new PurePursuitSegment("drive to wall",
                new PursuitPath(
                        new Point(-47, -26),
                        new Point(-47, -0.3)


                ).setMaxDeceleration(0.01).setMaxAcceleration(0.05).setMaxSpeed(0.6), 0

        ));

        return driveToFoundation;

    }


    protected Path Park() {
        Path driveToLine = new Path("park");

        driveToLine.addSegment(new PurePursuitSegment("drive to line",
                new PursuitPath(
                        new Point(-47, -0.3),
                        new Point(10, -1)


                ).setMaxSpeed(0.5).setMaxAcceleration(0.05).setMaxDeceleration(0.01), 0

        ));


        return driveToLine;


    }





    protected Path getLeftStoneTurn() {
        Path getStone = new Path("get stone");

        getStone.addSegment(new PurePursuitSegment("drive to stone",
                new PursuitPath(
                        new Point(68, -4),
                        new Point(68, -25.5)


                ).setMaxDeceleration(0.005).setMaxAcceleration(0.04)

        ));

        getStone.addSegment(new PurePursuitSegment("drive back to depot",
                new PursuitPath(
                        new Point(66, -25.5),
                        new Point(68, -5)


                ).setTurnGain(0.7).setMaxDeceleration(0.003).setMaxAcceleration(0.04).setHeadingError(5).setMinSpeed(0.14).setPositionError(4.0), 1000, -90

        ));


        return getStone;

    }


    protected Path getRightStoneTurn() {

        Path getStone = new Path("get second stone");

        getStone.addSegment(new PurePursuitSegment("drive to stone",
                new PursuitPath(
                        new Point(68, -4),
                        new Point(61, -25.5)


                ).setMaxDeceleration(0.005).setMaxAcceleration(0.04)

        ));

        getStone.addSegment(new PurePursuitSegment("drive back to depot",
                new PursuitPath(
                        new Point(63, -25.5),
                        new Point(68, -5)


                ).setTurnGain(0.7).setMaxDeceleration(0.003).setMaxAcceleration(0.04).setHeadingError(5).setMinSpeed(0.14).setPositionError(4.0), 1000, -90));

        return getStone;


    }


    protected Path goToBridgeTurn() {
        Path driveToLine = new Path("drive to line");

        driveToLine.addSegment(new PurePursuitSegment("drive to red line",
                new PursuitPath(
                        new Point(68, -5),
                        new Point(7, -4)

                ).setMaxDeceleration(0.01).setMaxAcceleration(0.04).setMaxSpeed(0.45), 0, -90


        ));


        return driveToLine;
    }


}
