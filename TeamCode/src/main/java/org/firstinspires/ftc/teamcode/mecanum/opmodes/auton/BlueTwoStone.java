package org.firstinspires.ftc.teamcode.mecanum.opmodes.auton;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractAuton;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.Path;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.PurePursuitSegment;
import org.firstinspires.ftc.teamcode.framework.userhardware.purepursuit.Point;
import org.firstinspires.ftc.teamcode.framework.userhardware.purepursuit.PursuitPath;

import static org.firstinspires.ftc.teamcode.mecanum.hardware.AutonPursuitPaths.*;

@Autonomous(name = "Blue Two Stone", group = "New")


public class BlueTwoStone extends BaseTwoStone {

    public BlueTwoStone (){
        super();
        isRed = false;
    }

    @Override
    public void Run() {

        super.Run();

        switch (place) {
            case "Right":
                robot.runDrivePath(BluePurePursuitRight);
                break;

            case "Left":
                robot.runDrivePath(BluePurePursuitLeft);
                break;

            case "Center":
                robot.runDrivePath(BluePurePursuitCenter);
                break;

            default:
                robot.runDrivePath(BluePurePursuitCenter);
                break;
        }
       robot.runDrivePath(BlueDragFoundation);
    }

    protected Path rightPath() {
        Path BluePurePursuitRight = new Path("collect right sky stones on blue side");

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
}