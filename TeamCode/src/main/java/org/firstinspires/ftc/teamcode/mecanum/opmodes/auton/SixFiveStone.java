package org.firstinspires.ftc.teamcode.mecanum.opmodes.auton;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.AnalogInput;

import org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractAuton;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.Path;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.PurePursuitSegment;
import org.firstinspires.ftc.teamcode.framework.userhardware.purepursuit.Point;
import org.firstinspires.ftc.teamcode.framework.userhardware.purepursuit.PursuitPath;
import org.firstinspires.ftc.teamcode.mecanum.hardware.Robot;
import org.firstinspires.ftc.teamcode.mecanum.hardware.util.ParameterFileConfiguration;

import static org.firstinspires.ftc.teamcode.framework.userhardware.DoubleTelemetry.LogMode.INFO;

@Autonomous(group = "new", name = "SixFive Stone")
public class SixFiveStone extends AbstractAuton {

    Robot robot;

    AnalogInput dial;

    private int depotPathPeriod = 0;
    private int foundationPathPeriod = 0;
    String pos = "";


    private ParameterFileConfiguration ParameterFile;


    @Override
    public void RegisterStates() {


        addState("sense stone", "drive to the blue depot", robot.SixthStoneCallable());
        addState("arm down", "sense stone", robot.armDownCallable());
        addState("open gripper", "sense stone", robot.setGripperReleaseCallable());
        addState("Pick up stone", "drive to stone", robot.grabStoneFullCallable());
        addState("drop off stone", "drive to foundation", robot.deliverStoneFullCallable());
        addState("arm down 2", "drive to the blue depot 2", robot.armDownCallable());
        addState("open gripper 2", "drive to the blue depot 2", robot.setGripperReleaseCallable());


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
//        dial = hardwareMap.get(AnalogInput.class, "sensor_digital");


    }

    @Override
    public void InitLoop() {

//        telemetry.addData(INFO, getDialDelay(dial.getVoltage()));
//        telemetry.addData(INFO, dial.getVoltage());
//        telemetry.update();
//        foundationPathPeriod = 1000 * getDialDelay(dial.getVoltage());


    }

    @Override
    public void Run() {

        robot.runDrivePath(driveToDepot());

        delay(700);

        pos = robot.SixthStoneVis();

        if (pos.equals("Left")) {

            robot.runDrivePath(getCenterStone());


        } else {

            robot.runDrivePath(getLeftStone());

        }


        telemetry.addData(INFO, pos);

        telemetry.update();


//        telemetry.addData(DoubleTelemetry.LogMode.INFO, "Is sixth stone: " + isSixth);
//
//        telemetry.update();


        robot.imageShutDown();

        robot.runDrivePath(goToBridge());

        robot.runDrivePath(goToFoundation());

//        robot.runDrivePath(Park());

        robot.runDrivePath(driveToDepot2());

        if (pos.equals("Right")) {
            robot.runDrivePath(getCenterStone());

        } else {
            robot.runDrivePath(getRightStone());

        }

        robot.runDrivePath(goToTriangle());

        robot.runDrivePath(goToFoundation2());

        robot.runDrivePath(Park());


        telemetry.addData(INFO, "delay value = " + ParameterFile.getProperty("CameraDelayTimeMsec"));
        telemetry.addData(INFO, "delay value = " + ParameterFile.getProperty("FakeName"));

    }

    public int getDialDelay(double volt) {
        double ret = volt / 0.33;

        return (int) ret;


    }


    @Override
    public void Stop() {
        super.Stop();
    }


    protected Path driveToDepot() {
        Path drive = new Path("drive to depot");

        drive.addSegment(new PurePursuitSegment("drive to the blue depot",

                new PursuitPath(
                        new Point(0, 0),
                        new Point(68, -4)).setMaxDeceleration(0.005).setMaxAcceleration(0.04).setMaxSpeed(0.6), depotPathPeriod

        ));


        return drive;
    }


    protected Path driveToDepot2() {

        Path drive = new Path("drive to depot 2");

        drive.addSegment(new PurePursuitSegment("drive to the blue depot 2",

                new PursuitPath(
                        new Point(-47, -1),
                        new Point(56, -4)).setMaxDeceleration(0.05).setMaxAcceleration(0.06).setMaxSpeed(0.8), depotPathPeriod

        ));


        return drive;


    }


    protected Path getLeftStone() {
        Path getStone = new Path("get stone");

        getStone.addSegment(new PurePursuitSegment("drive to stone",
                new PursuitPath(
                        new Point(68, -4),
                        new Point(68, -25.5)


                ).setMaxDeceleration(0.01).setMaxAcceleration(0.06)

        ));

        getStone.addSegment(new PurePursuitSegment("drive back to depot",
                new PursuitPath(
                        new Point(66, -25.5),
                        new Point(68, -0.5)


                ).setMaxDeceleration(0.01).setMaxAcceleration(0.06), 1000, 0

        ));


        return getStone;


    }

    protected Path getCenterStone() {

        Path getStone = new Path("get second stone");

        getStone.addSegment(new PurePursuitSegment("drive to stone",
                new PursuitPath(
                        new Point(68, -4),
                        new Point(61, -25.5)


                ).setMaxDeceleration(0.005).setMaxAcceleration(0.04)

        ));

        getStone.addSegment(new PurePursuitSegment("drive back to depot",
                new PursuitPath(
                        new Point(61, -25.5),
                        new Point(68, -0.5)


                ).setMaxDeceleration(0.01).setMaxAcceleration(0.06), 1000, 0));

        return getStone;


    }


    protected Path getRightStone() {
        Path getStone = new Path("get third stone");

        getStone.addSegment(new PurePursuitSegment("drive to stone",
                new PursuitPath(
                        new Point(68, -4),
                        new Point(53, -25.5)


                ).setMaxDeceleration(0.005).setMaxAcceleration(0.04)

        ));

        getStone.addSegment(new PurePursuitSegment("drive back to depot",
                new PursuitPath(
                        new Point(53, -25.5),
                        new Point(68, -0.5)


                ).setMaxDeceleration(0.01).setMaxAcceleration(0.06), 1000, 0));

        return getStone;


    }


    protected Path goToBridge() {
        Path driveToLine = new Path("drive to line");

        driveToLine.addSegment(new PurePursuitSegment("drive to red line",
                new PursuitPath(
                        new Point(68, -1),
                        new Point(10, -1)

                ).setMaxDeceleration(0.015).setMaxAcceleration(0.06), 0


        ));


        return driveToLine;
    }


    protected Path goToTriangle(){
        Path driveToTriangle = new Path("drive to triangle");

        driveToTriangle.addSegment(new PurePursuitSegment("drive",
                new PursuitPath(
                        new Point(68,-1),
                        new Point(-42,-1)


                ).setMaxDeceleration(0.015).setMaxAcceleration(0.06).setMinSpeed(0.3),0

                ));


        return driveToTriangle;


    }


    protected Path goToFoundation() {
        Path driveToFoundation = new Path("foundation drive");

        driveToFoundation.addSegment(new PurePursuitSegment("drive to foundation",
                new PursuitPath(
                        new Point(10, -0.3),
                        new Point(-45, -0.3),
                        new Point(-47, -26)


                ).setPointSpacing(3).setMaxDeceleration(0.013).setMaxAcceleration(0.06), foundationPathPeriod


        ));
        driveToFoundation.addSegment(new PurePursuitSegment("drive to wall",
                new PursuitPath(
                        new Point(-47, -26),
                        new Point(-47, -1)


                ).setMaxDeceleration(0.01).setMaxAcceleration(0.05).setMaxSpeed(0.6), 0

        ));

        return driveToFoundation;
    }


    protected Path goToFoundation2() {
        Path driveToFoundation = new Path("foundation drive");

        driveToFoundation.addSegment(new PurePursuitSegment("drive to foundation",
                new PursuitPath(
                        new Point(-42,-1),
                        new Point(-47,-26)


                ).setMaxDeceleration(0.015).setMaxAcceleration(0.05).setMaxSpeed(0.06),0

                ));


        driveToFoundation.addSegment(new PurePursuitSegment("drive to wall",
                new PursuitPath(
                        new Point(-47, -26),
                        new Point(-47, -1)


                ).setMaxDeceleration(0.01).setMaxAcceleration(0.05).setMaxSpeed(0.6), 0

        ));


        return driveToFoundation;


    }



    protected Path Park() {
        Path driveToLine = new Path("park");

        driveToLine.addSegment(new PurePursuitSegment("drive to line",
                new PursuitPath(
                        new Point(-47, -0.3),
                        new Point(10, -0.5)


                ).setMaxAcceleration(0.06).setMaxDeceleration(0.01), 0

        ));


        return driveToLine;


    }


}




