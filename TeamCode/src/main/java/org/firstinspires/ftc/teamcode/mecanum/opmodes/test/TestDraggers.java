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
                new Point(15, 14),
               new Point(10, 14))
                .setMinSpeed(1.0)
                .setMaxSpeed(1.0)
                .setTurnSpeed(1.0)
                .setTurnGain(3.0)
                .setPositionError(3.0)
                .setHeadingError(10.0)
                .setPathSmoothing(0.1)
                .setPointSpacing(1.0), 0, -30, 2200, true));
     /*   drag.addSegment(new PurePursuitSegment("park the foundation", new PursuitPath(
                new Point(20, +14),
                new Point(6, +8))
                .setMinSpeed(0.8)
                .setTurnSpeed(1.0)
                .setTurnGain(2.0)
                .setPositionError(15.0)
                .setHeadingError(10.0)
                .setPathSmoothing(0.5)
                .setPointSpacing(1.0), 0, -90, 1300));*/
        drag.addSegment(new PurePursuitSegment("park the robot", new PursuitPath(
                new Point(10, +14),
                new Point(18, 8),
                new Point(36, 5))
                .setMinSpeed(0.4)
                .setMaxSpeed(0.6)
                .setTurnSpeed(0.1)
                .setTurnGain(1.0)
                .setPositionError(6.0)
                .setHeadingError(8.0)
                .setPathSmoothing(0.5)
                .setPointSpacing(1.0), 0, -90, 2000));

        return drag;


    }


}
