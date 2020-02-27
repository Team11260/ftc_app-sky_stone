package org.firstinspires.ftc.teamcode.mecanum.opmodes.auton;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractAuton;
import org.firstinspires.ftc.teamcode.framework.userhardware.DoubleTelemetry;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.Path;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.PurePursuitSegment;
import org.firstinspires.ftc.teamcode.framework.userhardware.purepursuit.Point;
import org.firstinspires.ftc.teamcode.framework.userhardware.purepursuit.PursuitPath;
import org.firstinspires.ftc.teamcode.mecanum.hardware.Robot;

@Autonomous(group = "new", name = "Sixth Stone")

public class SixthStone extends AbstractAuton {

    Robot robot;

    private int depotPathPeriod = 0;


    @Override
    public void RegisterStates() {

        addState("arm down","drive to the blue depot",robot.armDownCallable());
        addState("arm down","drive to the blue depot",robot.setGripperReleaseCallable());

        addState("Pick up stone","drive to first stone",robot.grabStoneCallable());



    }

    @Override
    public void Init() {

        robot = new Robot();
        robot.arm.setArmUpPosition();
        robot.arm.setGripperGripPosition();
        robot.lift.setTiltUp();
        robot.dragger.setDraggerUp();


    }

    @Override
    public void Run() {

        robot.runDrivePath(driveToDepot());


        boolean isSixth = robot.isSixthStone();

        telemetry.addData(DoubleTelemetry.LogMode.INFO, "Is sixth stone: " + isSixth);

        telemetry.update();

        delay(1000);


        robot.imageShutDown();

        robot.runDrivePath(getLeftStone());


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


    protected Path getLeftStone(){
        Path getStone = new Path("get stone");

        getStone.addSegment(new PurePursuitSegment("drive to first stone",
                new PursuitPath(
                        new Point(68,-4),
                        new Point(66,-25.5)


                ).setMaxDeceleration(0.01).setMaxAcceleration(0.04).setMaxSpeed(0.6)

                ));

        getStone.addSegment(new PurePursuitSegment("drive back to depot",
                new PursuitPath(
                        new Point(66,-25.5),
                        new Point(68,-5)


                ).setTurnGain(0.5),1000,-90

                ));


        return getStone;

    }


}
