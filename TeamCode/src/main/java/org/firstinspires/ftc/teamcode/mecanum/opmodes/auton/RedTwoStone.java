package org.firstinspires.ftc.teamcode.mecanum.opmodes.auton;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractAuton;
import org.firstinspires.ftc.teamcode.framework.userhardware.DoubleTelemetry;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.Path;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.PurePursuitSegment;
import org.firstinspires.ftc.teamcode.framework.userhardware.purepursuit.MecanumPurePursuitController;
import org.firstinspires.ftc.teamcode.framework.userhardware.purepursuit.Point;
import org.firstinspires.ftc.teamcode.framework.userhardware.purepursuit.PursuitPath;
import org.upacreekrobotics.dashboard.Config;

import static org.firstinspires.ftc.teamcode.mecanum.hardware.AutonPursuitPaths.*;

@Autonomous(name = "Red Two Stone", group = "New")

@Config
public class RedTwoStone extends BaseTwoStone {

    @Override
    public void Run() {

        super.Run();


        switch (place) {
            case "Right":
                robot.runDrivePath(rightPath());
                break;

            case "Left":
                robot.runDrivePath(leftPath());
                break;

            case "Center":
                robot.runDrivePath(centerPath());
                break;

            default:
                robot.runDrivePath(centerPath());
                break;
        }
        robot.runDrivePath(dragPath());
    }


    protected Path leftPath() {

        Path RedPurePursuitLeft = new Path("left stone");

        RedPurePursuitLeft.addSegment(new PurePursuitSegment("drive to first sky stone",
                new PursuitPath(
                        new Point(0, 0),
                        new Point(RED_BLOCK4_X, RED_BLOCK_LOCATION_Y))
                        .setMaxAcceleration(0.01), 0));
        RedPurePursuitLeft.addSegment(new PurePursuitSegment("first trip to foundation",
                new PursuitPath(
                        new Point(RED_BLOCK4_X, RED_BLOCK_LOCATION_Y),
                        new Point(RED_BLOCK4_X, RED_RUNWAY_Y),
                        new Point(RED_FOUNDATION_MIDDLE_X, RED_RUNWAY_Y),
                        new Point(RED_FOUNDATION_MIDDLE_X, RED_FOUNDATION_Y)), PERIOD));
        RedPurePursuitLeft.addSegment(new PurePursuitSegment("drive to second sky stone",
                new PursuitPath(
                        new Point(RED_FOUNDATION_MIDDLE_X, RED_FOUNDATION_Y),
                        new Point(RED_FOUNDATION_MIDDLE_X, RED_RUNWAY_Y),
                        new Point(RED_BLOCK1_X + 1, RED_RUNWAY_Y + OFF_SET),
                        new Point(RED_BLOCK1_X + 1, RED_BLOCK_LOCATION_Y - 2)).setMaxDeceleration(0.01), PERIOD));
        RedPurePursuitLeft.addSegment(new PurePursuitSegment("second trip to foundation",
                new PursuitPath(
                        new Point(RED_BLOCK1_X + 1, RED_BLOCK_LOCATION_Y - 2),
                        new Point(RED_BLOCK1_X + 1, RED_RUNWAY_Y_2),
                        new Point(RED_FOUNDATION_NEAR_X, RED_RUNWAY_Y_2),
                        new Point(RED_FOUNDATION_NEAR_X, RED_FOUNDATION_Y)), PERIOD));
        RedPurePursuitLeft.addSegment(new PurePursuitSegment("drive to third sky stone",
                new PursuitPath(
                        new Point(RED_FOUNDATION_NEAR_X, RED_FOUNDATION_Y),
                        new Point(RED_FOUNDATION_NEAR_X, RED_RUNWAY_Y_2),
                        new Point(RED_BLOCK6_X + 2, RED_RUNWAY_Y_2),
                        new Point(RED_BLOCK6_X + 2, RED_BLOCK_LOCATION_Y - 2)).setMaxDeceleration(0.013), PERIOD));
        RedPurePursuitLeft.addSegment(new PurePursuitSegment("last trip to foundation",
                new PursuitPath(
                        new Point(RED_BLOCK6_X + 2, RED_BLOCK_LOCATION_Y - 2),
                        new Point(RED_BLOCK6_X + 2, RED_RUNWAY_Y_2),
                        new Point(RED_FOUNDATION_MIDDLE_NEAR_X, RED_RUNWAY_Y_2),
                        new Point(RED_FOUNDATION_MIDDLE_NEAR_X, RED_FOUNDATION_Y_2ND))
                        .setMaxDeceleration(0.01)
                        .setPointSpacing(1.0)
                        .setPathSmoothing(0.5), PERIOD));


        return RedPurePursuitLeft;

    }


    protected Path centerPath() {

        Path RedPurePursuitCenter = new Path("center stone");

        RedPurePursuitCenter.addSegment(new PurePursuitSegment("drive to first sky stone",
                new PursuitPath(
                        new Point(0, 0),
                        new Point(RED_BLOCK5_X, RED_BLOCK_LOCATION_Y))
                        .setMaxAcceleration(0.01), 0));
        RedPurePursuitCenter.addSegment(new PurePursuitSegment("first trip to foundation",
                new PursuitPath(
                        new Point(RED_BLOCK5_X, RED_BLOCK_LOCATION_Y),
                        new Point(RED_BLOCK5_X, RED_RUNWAY_Y),
                        new Point(RED_FOUNDATION_MIDDLE_X, RED_RUNWAY_Y),
                        new Point(RED_FOUNDATION_MIDDLE_X, RED_FOUNDATION_Y)), PERIOD + 50));
        RedPurePursuitCenter.addSegment(new PurePursuitSegment("drive to second sky stone",
                new PursuitPath(
                        new Point(RED_FOUNDATION_MIDDLE_X, RED_FOUNDATION_Y),
                        new Point(RED_FOUNDATION_MIDDLE_X, RED_RUNWAY_Y),
                        new Point(RED_BLOCK2_X, RED_RUNWAY_Y),
                        new Point(RED_BLOCK2_X, RED_BLOCK_LOCATION_Y + OFF_SET - 2)).setMaxDeceleration(0.013), PERIOD));
        RedPurePursuitCenter.addSegment(new PurePursuitSegment("second trip to foundation",
                new PursuitPath(
                        new Point(RED_BLOCK2_X, RED_BLOCK_LOCATION_Y + OFF_SET - 2),
                        new Point(RED_BLOCK2_X, RED_RUNWAY_Y_2),
                        new Point(RED_FOUNDATION_NEAR_X, RED_RUNWAY_Y_2),
                        new Point(RED_FOUNDATION_NEAR_X, RED_FOUNDATION_Y)), PERIOD));
        RedPurePursuitCenter.addSegment(new PurePursuitSegment("drive to third sky stone",
                new PursuitPath(
                        new Point(RED_FOUNDATION_NEAR_X, RED_FOUNDATION_Y),
                        new Point(RED_FOUNDATION_NEAR_X, RED_RUNWAY_Y_2),
                        new Point(RED_BLOCK6_X + 2, RED_RUNWAY_Y_2),
                        new Point(RED_BLOCK6_X + 2, RED_BLOCK_LOCATION_Y - 2))
                        .setMaxDeceleration(0.01), PERIOD));
        RedPurePursuitCenter.addSegment(new PurePursuitSegment("last trip to foundation",
                new PursuitPath(
                        new Point(RED_BLOCK6_X + 2, RED_BLOCK_LOCATION_Y - 2),
                        new Point(RED_BLOCK6_X + 2, RED_RUNWAY_Y_2),
                        new Point(RED_FOUNDATION_MIDDLE_NEAR_X, RED_RUNWAY_Y_2),
                        new Point(RED_FOUNDATION_MIDDLE_NEAR_X, RED_FOUNDATION_Y_2ND))
                        .setMaxDeceleration(0.01)
                        .setPointSpacing(1.0)
                        .setPathSmoothing(0.5), PERIOD));


        return RedPurePursuitCenter;
    }


    protected Path rightPath() {
        Path RedPurePursuitRight = new Path("right stone");

        RedPurePursuitRight.addSegment(new PurePursuitSegment("drive to first sky stone",
                new PursuitPath(
                        new Point(0, 0),
                        new Point(RED_BLOCK6_X - 1, RED_BLOCK_LOCATION_Y))
                        .setMaxAcceleration(0.01), 0));
        RedPurePursuitRight.addSegment(new PurePursuitSegment("first trip to foundation",
                new PursuitPath(
                        new Point(RED_BLOCK6_X, RED_BLOCK_LOCATION_Y),
                        new Point(RED_BLOCK6_X, RED_RUNWAY_Y),
                        new Point(RED_FOUNDATION_MIDDLE_X, RED_RUNWAY_Y),
                        new Point(RED_FOUNDATION_MIDDLE_X, RED_FOUNDATION_Y)), PERIOD));
        RedPurePursuitRight.addSegment(new PurePursuitSegment("drive to second sky stone",
                new PursuitPath(
                        new Point(RED_FOUNDATION_MIDDLE_X, RED_FOUNDATION_Y),
                        new Point(RED_FOUNDATION_MIDDLE_X, RED_RUNWAY_Y),
                        new Point(RED_BLOCK3_X, RED_RUNWAY_Y),
                        new Point(RED_BLOCK3_X, RED_BLOCK_LOCATION_Y + OFF_SET - 2))
                        .setMaxDeceleration(0.013), PERIOD));
        RedPurePursuitRight.addSegment(new PurePursuitSegment("second trip to foundation",
                new PursuitPath(
                        new Point(RED_BLOCK3_X, RED_BLOCK_LOCATION_Y - 2),
                        new Point(RED_BLOCK3_X, RED_RUNWAY_Y_2),
                        new Point(RED_FOUNDATION_NEAR_X, RED_RUNWAY_Y_2),
                        new Point(RED_FOUNDATION_NEAR_X, RED_FOUNDATION_Y)), PERIOD));
        RedPurePursuitRight.addSegment(new PurePursuitSegment("drive to third sky stone",
                new PursuitPath(
                        new Point(RED_FOUNDATION_NEAR_X, RED_FOUNDATION_Y),
                        new Point(RED_FOUNDATION_NEAR_X, RED_RUNWAY_Y_2),
                        new Point(RED_BLOCK5_X + 2, RED_RUNWAY_Y_2),
                        new Point(RED_BLOCK5_X + 2, RED_BLOCK_LOCATION_Y - 2))
                        .setMaxDeceleration(0.01), PERIOD));
        RedPurePursuitRight.addSegment(new PurePursuitSegment("last trip to foundation",
                new PursuitPath(
                        new Point(RED_BLOCK5_X + 2, RED_BLOCK_LOCATION_Y - 2),
                        new Point(RED_BLOCK5_X + 2, RED_RUNWAY_Y_2),
                        new Point(RED_FOUNDATION_MIDDLE_NEAR_X, RED_RUNWAY_Y_2),
                        new Point(RED_FOUNDATION_MIDDLE_NEAR_X, RED_FOUNDATION_Y_2ND))
                        .setMaxDeceleration(0.01)
                        .setPointSpacing(1.0)
                        .setPathSmoothing(0.5), PERIOD));


        return RedPurePursuitRight;


    }


    protected Path dragPath(){
        Path dragPath = new Path("drag");

        dragPath.addSegment(new PurePursuitSegment("Pull the foundation", new PursuitPath(
                new Point(RED_FOUNDATION_MIDDLE_NEAR_X + 0, RED_FOUNDATION_Y_2ND + 0),
                new Point(RED_FOUNDATION_MIDDLE_NEAR_X + 20, RED_FOUNDATION_Y_2ND + 16))
                .setMinSpeed(0.8)
                .setTurnSpeed(1.0)
                .setTurnGain(1.0)
                .setPositionError(15.0)
                .setHeadingError(10.0)
                .setPathSmoothing(0.5)
                .setPointSpacing(1.0), 1000, -55, 1600));
        dragPath.addSegment(new PurePursuitSegment("park the foundation", new PursuitPath(
                new Point(RED_FOUNDATION_MIDDLE_NEAR_X + 20, RED_FOUNDATION_Y_2ND + 14),
                new Point(RED_FOUNDATION_MIDDLE_NEAR_X + 6, RED_FOUNDATION_Y_2ND + 8))
                .setMinSpeed(1.1)
                .setTurnSpeed(1.0)
                .setTurnGain(3.0)
                .setPositionError(15.0)
                .setHeadingError(10.0)
                .setPathSmoothing(0.5)
                .setPointSpacing(1.0), 0, -90, 1500));
        dragPath.addSegment(new PurePursuitSegment("park the robot", new PursuitPath(
                new Point(RED_FOUNDATION_MIDDLE_NEAR_X + 14, RED_FOUNDATION_Y_2ND + 5),
                new Point(RED_FOUNDATION_MIDDLE_NEAR_X + 18, RED_FOUNDATION_Y_2ND + 3),
                new Point(RED_FOUNDATION_MIDDLE_NEAR_X + 52, RED_FOUNDATION_Y_2ND + 1))
                .setMinSpeed(0.25)
                .setMaxSpeed(0.6)
                .setTurnSpeed(0.1)
                .setTurnGain(1.0)
                .setPositionError(6.0)
                .setHeadingError(8.0)
                .setPathSmoothing(0.5)
                .setPointSpacing(1.0), 0, -90, 3000));

        return dragPath;

    }




}
