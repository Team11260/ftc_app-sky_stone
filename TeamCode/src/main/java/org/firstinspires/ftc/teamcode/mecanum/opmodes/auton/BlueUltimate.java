package org.firstinspires.ftc.teamcode.mecanum.opmodes.auton;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.framework.userhardware.paths.Path;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.PurePursuitSegment;
import org.firstinspires.ftc.teamcode.framework.userhardware.purepursuit.Point;
import org.firstinspires.ftc.teamcode.framework.userhardware.purepursuit.PursuitPath;

import static org.firstinspires.ftc.teamcode.mecanum.hardware.AutonPursuitPaths.*;

@Autonomous(name = "Blue Two Stone", group = "New")


public class BlueUltimate extends BaseUltimate {

    public BlueUltimate() {
        super();
        isRed = false;
    }

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

    protected Path rightPath() {
        Path BluePurePursuitRight = new Path("right stone");

        BluePurePursuitRight.addSegment(new PurePursuitSegment("drive to first sky stone",
                new PursuitPath(
                        new Point(0, 0),
                        new Point(BLUE_BLOCK4_X, BLUE_BLOCK_LOCATION_Y))
                        .setMaxAcceleration(0.01)
                        .setMaxDeceleration(0.015), 0));
        BluePurePursuitRight.addSegment(new PurePursuitSegment("first trip to foundation",
                new PursuitPath(new Point(BLUE_BLOCK4_X, BLUE_BLOCK_LOCATION_Y),
                        new Point(BLUE_BLOCK4_X, BLUE_RUNWAY_Y),
                        new Point(BLUE_FOUNDATION_MIDDLE_X, BLUE_RUNWAY_Y),
                        new Point(BLUE_FOUNDATION_MIDDLE_X, BLUE_FOUNDATION_Y))
                        .setMaxDeceleration(0.015), PERIOD + 250));
        BluePurePursuitRight.addSegment(new PurePursuitSegment("drive to second sky stone",
                new PursuitPath(new Point(BLUE_FOUNDATION_MIDDLE_X, BLUE_FOUNDATION_Y),
                        new Point(BLUE_FOUNDATION_MIDDLE_X, BLUE_RUNWAY_Y),
                        new Point(BLUE_BLOCK1_X, BLUE_RUNWAY_Y + OFF_SET),
                        new Point(BLUE_BLOCK1_X, BLUE_BLOCK_LOCATION_Y)).setMaxDeceleration(0.017), PERIOD));
        BluePurePursuitRight.addSegment(new PurePursuitSegment("second trip to foundation",
                new PursuitPath(new Point(BLUE_BLOCK1_X, BLUE_BLOCK_LOCATION_Y),
                        new Point(BLUE_BLOCK1_X, BLUE_RUNWAY_Y_2),
                        new Point(BLUE_FOUNDATION_NEAR_X, BLUE_RUNWAY_Y_2),
                        new Point(BLUE_FOUNDATION_NEAR_X, BLUE_FOUNDATION_Y)).setMaxDeceleration(0.017), PERIOD));
        BluePurePursuitRight.addSegment(new PurePursuitSegment("drive to third sky stone",
                new PursuitPath(new Point(BLUE_FOUNDATION_NEAR_X, BLUE_FOUNDATION_Y),
                        new Point(BLUE_FOUNDATION_NEAR_X, BLUE_RUNWAY_Y),
                        new Point(BLUE_BLOCK6_X + 1.5, BLUE_RUNWAY_Y + OFF_SET),
                        new Point(BLUE_BLOCK6_X + 1.5, BLUE_BLOCK_LOCATION_Y)).setMaxDeceleration(0.017), PERIOD));
        BluePurePursuitRight.addSegment(new PurePursuitSegment("last trip to foundation",
                new PursuitPath(new Point(BLUE_BLOCK6_X, BLUE_BLOCK_LOCATION_Y),
                        new Point(BLUE_BLOCK6_X, BLUE_RUNWAY_Y_2),
                        new Point(BLUE_FOUNDATION_MIDDLE_NEAR_X, BLUE_RUNWAY_Y_2),
                        new Point(BLUE_FOUNDATION_MIDDLE_NEAR_X, BLUE_FOUNDATION_Y_2ND))
                        .setMaxDeceleration(0.005)
                        .setPointSpacing(1.0)
                        .setPathSmoothing(0.5), PERIOD));

        return BluePurePursuitRight;
    }

    protected Path centerPath() {
        Path BluePurePursuitCenter = new Path("center code");

        BluePurePursuitCenter.addSegment(new PurePursuitSegment("drive to first sky stone",
                new PursuitPath(
                        new Point(0, 0),
                        new Point(BLUE_BLOCK5_X, BLUE_BLOCK_LOCATION_Y + 1))
                        .setMaxAcceleration(0.01).setMaxDeceleration(0.015), 0));
        BluePurePursuitCenter.addSegment(new PurePursuitSegment("first trip to foundation",
                new PursuitPath(
                        new Point(BLUE_BLOCK5_X, BLUE_BLOCK_LOCATION_Y),
                        new Point(BLUE_BLOCK5_X, BLUE_RUNWAY_Y),
                        new Point(BLUE_FOUNDATION_MIDDLE_X, BLUE_RUNWAY_Y),
                        new Point(BLUE_FOUNDATION_MIDDLE_X, BLUE_FOUNDATION_Y + 1)).setMaxDeceleration(0.015), PERIOD + 250));
        BluePurePursuitCenter.addSegment(new PurePursuitSegment("drive to second sky stone",
                new PursuitPath(new Point(BLUE_FOUNDATION_MIDDLE_X, BLUE_FOUNDATION_Y + 1),
                        new Point(BLUE_FOUNDATION_MIDDLE_X, BLUE_RUNWAY_Y),
                        new Point(BLUE_BLOCK2_X, BLUE_RUNWAY_Y + OFF_SET),
                        new Point(BLUE_BLOCK2_X, BLUE_BLOCK_LOCATION_Y - 1.5)).setMaxDeceleration(0.017), PERIOD));
        BluePurePursuitCenter.addSegment(new PurePursuitSegment("second trip to foundation",
                new PursuitPath(new Point(BLUE_BLOCK2_X, BLUE_BLOCK_LOCATION_Y - 1.5),
                        new Point(BLUE_BLOCK2_X, BLUE_RUNWAY_Y_2),
                        new Point(BLUE_FOUNDATION_NEAR_X, BLUE_RUNWAY_Y_2),
                        new Point(BLUE_FOUNDATION_NEAR_X + 5, BLUE_FOUNDATION_Y)).setMaxDeceleration(0.017), PERIOD));
        BluePurePursuitCenter.addSegment(new PurePursuitSegment("drive to third sky stone",
                new PursuitPath(new Point(BLUE_FOUNDATION_NEAR_X + 5, BLUE_FOUNDATION_Y),
                        new Point(BLUE_FOUNDATION_NEAR_X, BLUE_RUNWAY_Y),
                        new Point(BLUE_BLOCK6_X + 2, BLUE_RUNWAY_Y + OFF_SET),
                        new Point(BLUE_BLOCK6_X + 2, BLUE_BLOCK_LOCATION_Y - 1.5)).setMaxDeceleration(0.013), PERIOD));
        BluePurePursuitCenter.addSegment(new PurePursuitSegment("last trip to foundation",
                new PursuitPath(new Point(BLUE_BLOCK6_X + 2, BLUE_BLOCK_LOCATION_Y - 1.5),
                        new Point(BLUE_BLOCK6_X + 2, BLUE_RUNWAY_Y_2),
                        new Point(BLUE_FOUNDATION_MIDDLE_NEAR_X, BLUE_RUNWAY_Y_2),
                        new Point(BLUE_FOUNDATION_MIDDLE_NEAR_X, BLUE_FOUNDATION_Y_2ND))
                        .setMaxDeceleration(0.01)
                        .setPointSpacing(1.0)
                        .setPathSmoothing(0.5), PERIOD));


        return BluePurePursuitCenter;


    }

    protected Path leftPath() {

        Path BluePurePursuitLeft = new Path("left stone");
        BluePurePursuitLeft.addSegment(new PurePursuitSegment("drive to first sky stone",
                new PursuitPath(new Point(0, 0), new Point(BLUE_BLOCK6_X, BLUE_BLOCK_LOCATION_Y + 1)).setMaxAcceleration(0.01).setMaxDeceleration(0.015), 0));
        BluePurePursuitLeft.addSegment(new PurePursuitSegment("first trip to foundation",
                new PursuitPath(new Point(BLUE_BLOCK6_X, BLUE_BLOCK_LOCATION_Y),
                        new Point(BLUE_BLOCK6_X, BLUE_RUNWAY_Y),
                        new Point(BLUE_FOUNDATION_MIDDLE_X, BLUE_RUNWAY_Y),
                        new Point(BLUE_FOUNDATION_MIDDLE_X, BLUE_FOUNDATION_Y + 1))
                        .setMaxDeceleration(0.015), PERIOD));
        BluePurePursuitLeft.addSegment(new PurePursuitSegment("drive to second sky stone",
                new PursuitPath(new Point(BLUE_FOUNDATION_MIDDLE_X, BLUE_FOUNDATION_Y + 1),
                        new Point(BLUE_FOUNDATION_MIDDLE_X, BLUE_RUNWAY_Y),
                        new Point(BLUE_BLOCK3_X, BLUE_RUNWAY_Y + OFF_SET),
                        new Point(BLUE_BLOCK3_X, BLUE_BLOCK_LOCATION_Y - 1)).setMaxDeceleration(0.015), PERIOD));
        BluePurePursuitLeft.addSegment(new PurePursuitSegment("second trip to foundation",
                new PursuitPath(new Point(BLUE_BLOCK3_X, BLUE_BLOCK_LOCATION_Y - 1),
                        new Point(BLUE_BLOCK3_X, BLUE_RUNWAY_Y_2),
                        new Point(BLUE_FOUNDATION_NEAR_X, BLUE_RUNWAY_Y_2),
                        new Point(BLUE_FOUNDATION_NEAR_X + 5, BLUE_FOUNDATION_Y)), PERIOD));
        BluePurePursuitLeft.addSegment(new PurePursuitSegment("drive to third sky stone",
                new PursuitPath(new Point(BLUE_FOUNDATION_NEAR_X + 5, BLUE_FOUNDATION_Y),
                        new Point(BLUE_FOUNDATION_NEAR_X, BLUE_RUNWAY_Y),
                        new Point(BLUE_BLOCK5_X + 2, BLUE_RUNWAY_Y + OFF_SET),
                        new Point(BLUE_BLOCK5_X + 2, BLUE_BLOCK_LOCATION_Y - 1)).setMaxDeceleration(0.013), PERIOD));
        BluePurePursuitLeft.addSegment(new PurePursuitSegment("last trip to foundation",
                new PursuitPath(new Point(BLUE_BLOCK5_X + 2, BLUE_BLOCK_LOCATION_Y - 1),
                        new Point(BLUE_BLOCK5_X + 2, BLUE_RUNWAY_Y_2),
                        new Point(BLUE_FOUNDATION_MIDDLE_NEAR_X, BLUE_RUNWAY_Y_2),
                        new Point(BLUE_FOUNDATION_MIDDLE_NEAR_X, BLUE_FOUNDATION_Y_2ND))
                        .setMaxDeceleration(0.01)
                        .setPointSpacing(1.0)
                        .setPathSmoothing(0.5), PERIOD));


        return BluePurePursuitLeft;
    }


    protected Path dragPath() {
        Path BlueDragFoundation = new Path("drag");


        BlueDragFoundation.addSegment(new PurePursuitSegment("Pull the foundation", new PursuitPath(
                new Point(BLUE_FOUNDATION_MIDDLE_NEAR_X - 0, BLUE_FOUNDATION_Y_2ND + 0),
                new Point(BLUE_FOUNDATION_MIDDLE_NEAR_X - 20, BLUE_FOUNDATION_Y_2ND + 16))
                .setMinSpeed(0.7)
                .setTurnSpeed(1.0)
                .setTurnGain(1.0)
                .setPositionError(15.0)
                .setHeadingError(10.0)
                .setPathSmoothing(0.5)
                .setPointSpacing(1.0), 1000, 55, 1500));
        BlueDragFoundation.addSegment(new PurePursuitSegment("park the foundation", new PursuitPath(
                new Point(BLUE_FOUNDATION_MIDDLE_NEAR_X - 20, BLUE_FOUNDATION_Y_2ND + 14),
                new Point(BLUE_FOUNDATION_MIDDLE_NEAR_X - 6, BLUE_FOUNDATION_Y_2ND + 8))
                .setMinSpeed(1.1)
                .setTurnSpeed(1.0)
                .setTurnGain(3.0)
                .setPositionError(15.0)
                .setHeadingError(10.0)
                .setPathSmoothing(0.5)
                .setPointSpacing(1.0), 0, 90, 1700));
        BlueDragFoundation.addSegment(new PurePursuitSegment("park the robot", new PursuitPath(
                new Point(BLUE_FOUNDATION_MIDDLE_NEAR_X - 14, BLUE_FOUNDATION_Y_2ND + 5),
                new Point(BLUE_FOUNDATION_MIDDLE_NEAR_X - 18, +BLUE_FOUNDATION_Y_2ND + 1),
                new Point(BLUE_FOUNDATION_MIDDLE_NEAR_X - 50, BLUE_FOUNDATION_Y_2ND - 2))
                .setMinSpeed(0.25)
                .setMaxSpeed(0.6)
                .setTurnSpeed(0.1)
                .setTurnGain(1.0)
                .setPositionError(6.0)
                .setHeadingError(8.0)
                .setPathSmoothing(0.5)
                .setPointSpacing(1.0), 0, 90, 3000));


        return BlueDragFoundation;
    }


}