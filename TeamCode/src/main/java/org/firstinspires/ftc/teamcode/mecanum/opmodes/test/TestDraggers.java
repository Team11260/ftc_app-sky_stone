package org.firstinspires.ftc.teamcode.mecanum.opmodes.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.R;
import org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractAuton;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.Path;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.PurePursuitSegment;
import org.firstinspires.ftc.teamcode.framework.userhardware.purepursuit.MecanumPurePursuitController;
import org.firstinspires.ftc.teamcode.framework.userhardware.purepursuit.Point;
import org.firstinspires.ftc.teamcode.framework.userhardware.purepursuit.PursuitPath;
import org.firstinspires.ftc.teamcode.mecanum.hardware.Robot;





@Autonomous (name = "Test Draggers", group = "New")
public class TestDraggers extends AbstractAuton {

    Robot robot;


    @Override
    public void RegisterStates() {

        addState("release dragger","Pull the foundation",robot.delayedDraggerUpCallable());
    }

    @Override
    public void Init() {
        robot = new Robot();
        robot.setDraggerDown();
    }

    @Override
    public void Run() {
        robot.runDrivePath(dragTest());

        robot.dragger.setDraggerUp();
    }

    protected Path dragTest(){
        Path drag = new Path("path");


       drag.addSegment(new PurePursuitSegment("Pull the foundation", new PursuitPath(
                new Point(0, 0),
                new Point(20, +16))
                .setMinSpeed(0.7)
                .setTurnSpeed(1.0)
                .setTurnGain(1.0)
                .setPositionError(15.0)
                .setHeadingError(10.0)
                .setPathSmoothing(0.5)
                .setPointSpacing(1.0), 500, -55, 1500));
        drag.addSegment(new PurePursuitSegment("park the foundation", new PursuitPath(
                new Point(20, +14),
                new Point(6, +8))
                .setMinSpeed(0.8)
                .setTurnSpeed(1.0)
                .setTurnGain(2.0)
                .setPositionError(15.0)
                .setHeadingError(10.0)
                .setPathSmoothing(0.5)
                .setPointSpacing(1.0), 0, -90, 1300));
        drag.addSegment(new PurePursuitSegment("park the robot", new PursuitPath(
                new Point(14, +5),
                new Point(18, 3),
                new Point(52, 3))
                .setMinSpeed(0.25)
                .setMaxSpeed(0.6)
                .setTurnSpeed(0.1)
                .setTurnGain(1.0)
                .setPositionError(6.0)
                .setHeadingError(8.0)
                .setPathSmoothing(0.5)
                .setPointSpacing(1.0), 0, -90, 3000));

        return drag;


    }


}
