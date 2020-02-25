package org.firstinspires.ftc.teamcode.mecanum.opmodes.auton;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractAuton;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.Path;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.PurePursuitSegment;
import org.firstinspires.ftc.teamcode.framework.userhardware.purepursuit.Point;
import org.firstinspires.ftc.teamcode.framework.userhardware.purepursuit.PursuitPath;
import org.firstinspires.ftc.teamcode.mecanum.hardware.Robot;

import static org.firstinspires.ftc.teamcode.mecanum.hardware.AutonPursuitPaths.RED_FOUNDATION_Y_2ND;

@Autonomous (name = "Last Stone Delivery", group = "New")
public class LastStoneDelivery extends AbstractAuton {

    Robot robot;

    @Override
    public void RegisterStates() {
        addState("detect stone","trip to last stone",robot.findLastStone());
        addState("set Dragger halfway","drive down the wall",robot.setDraggerHalfwayCallable());
        addState("set Dragger down","drive up to the foundation",robot.setDraggerDownCallable());
        addState("release dragger","Pull the foundation",robot.delayedDraggerUpCallable());




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
        delay(19000);
        robot.runDrivePath(drag2());

        /*
        switch (robot.place){
            case "Middle":
                robot.runDrivePath(leftLastStone);
                break;
            case "Left":
                robot.runDrivePath(middleLastStone);
                break;
            default:
                robot.runDrivePath(leftLastStone);
        }

        robot.runDrivePath(redDragFoundation);
        */
    }



    protected Path drag2(){
        Path drag = new Path("drag");

        drag.addSegment(new PurePursuitSegment("drive down the wall", new PursuitPath(
                new Point(0, 0),
                new Point(-22, 0))
                .setPathSmoothing(0.5)
                .setPointSpacing(1.0), 0));
        drag.addSegment(new PurePursuitSegment("drive up to the foundation", new PursuitPath(
                new Point(-22, 0),
                new Point(-22, RED_FOUNDATION_Y_2ND)).setPathSmoothing(0.5).setPointSpacing(1.0), 0));
        drag.addSegment(new PurePursuitSegment("Pull the foundation", new PursuitPath(
                new Point(-22, RED_FOUNDATION_Y_2ND),
                new Point(-3, -10))
                .setMinSpeed(0.6)
                .setPositionError(5.0)
                .setTurnSpeed(0.6)
                .setPathSmoothing(0.5)
                .setPointSpacing(1.0), 1000, -35));
        drag.addSegment(new PurePursuitSegment("park the foundation", new PursuitPath(
                new Point(-3, -10),
                new Point(2, -4))
                .setMinSpeed(0.5)
                .setPositionError(8.0)
                .setTurnSpeed(0.7)
                .setPathSmoothing(0.5)
                .setPointSpacing(1.0), 0, -90));
        drag.addSegment(new PurePursuitSegment("park the robot", new PursuitPath(
                new Point(2, -4),
                new Point(1, -3),
                new Point(55 - 12, -1))
                .setMinSpeed(0.3)
                .setPositionError(4.0)
                .setPathSmoothing(0.5)
                .setPointSpacing(1.0), 0, -90));


        return drag;

    }
}
