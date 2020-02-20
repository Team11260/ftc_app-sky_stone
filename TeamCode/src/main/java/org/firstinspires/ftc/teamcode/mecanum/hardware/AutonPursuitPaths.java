package org.firstinspires.ftc.teamcode.mecanum.hardware;

import org.firstinspires.ftc.teamcode.framework.userhardware.paths.Path;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.PurePursuitSegment;
import org.firstinspires.ftc.teamcode.framework.userhardware.purepursuit.Point;
import org.firstinspires.ftc.teamcode.framework.userhardware.purepursuit.PursuitPath;

public class AutonPursuitPaths {

    /*  This class countains all the paths for the pure pursuit-based stone collections */
    /*  The stones are numbered from the wall from 1 to 6 */
    /*  The origin point (0,0) is the robot's starting position */
    /*  Every point is referenced absolutely to this reference frame - no relative distances */
    public static double RED_PARK_X = 8.0;
    public static double RED_PARK_Y = 0.5;

    public static double BLUE_PARK_X = 8.0;
    public static double BLUE_PARK_Y = -0.5;

    public static double FIELD_OFFSET = -0.75;
    public static int PERIOD = 200;


    public static double RED_BLOCK_LOCATION_Y = -25.5 + FIELD_OFFSET;
    public static double RED_FOUNDATION_Y = -29.0;
    public static double RED_FOUNDATION_Y_2ND = -32.5;

    public static double RED_FOUNDATION_NEAR_X = -74;  //  Play with this parameter
    public static double RED_FOUNDATION_MIDDLE_NEAR_X = -77;  //  Play with this parameter
    public static double RED_FOUNDATION_MIDDLE_X = -86;  //Measure this parameter

    // public static double RED_FOUNDATION_DRAGGER_X = -75;
    public static double RED_RUNWAY_Y = -19;  //  Play with this parameter
    public static double RED_RUNWAY_Y_2 = -19.5;
    public static double RED_DRAGGER_BACKUP_Y = -23;
    public static double RED_DRAGGER_FORWARD_HALFWAY_Y = -26;
    public static double RED_DRAGGER_FORWARD_FULL_Y = -29;
    public static double OFF_SET = 0;
    //count from wall
    public static double RED_BLOCK1_X = 30;
    public static double RED_BLOCK2_X = 23;
    public static double RED_BLOCK3_X = 14;//Was 16
    public static double RED_BLOCK4_X = 6;
    public static double RED_BLOCK5_X = -2;
    public static double RED_BLOCK6_X = -10;

    public static double BLUE_RUNWAY_Y = -19;
    public static double BLUE_RUNWAY_Y_2 = -19.5;

    public static double BLUE_FOUNDATION_Y = -31;
    public static double BLUE_FOUNDATION_Y_2ND = -34.5;
    public static double BLUE_FOUNDATION_MIDDLE_X = 88;
    public static double BLUE_FOUNDATION_MIDDLE_NEAR_X = 78;
    public static double BLUE_FOUNDATION_NEAR_X = 72.5;
    public static double BLUE_BLOCK_LOCATION_Y = -26.5 + FIELD_OFFSET;
    //count from wall
    public static double BLUE_BLOCK1_X = -30;
    public static double BLUE_BLOCK2_X = -23;
    public static double BLUE_BLOCK3_X = -15;
    public static double BLUE_BLOCK4_X = -7;
    public static double BLUE_BLOCK5_X = 1;
    public static double BLUE_BLOCK6_X = 7;


    /*  Paths for red side left sky stone*/

    public final static Path RedPurePursuitLeft = new Path("collect left sky stones");

    static {
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

    }

    public final static Path RedPurePursuitCenterTest = new Path("collect center sky stones");

    static {
        RedPurePursuitCenterTest.addSegment(new PurePursuitSegment("drive to first sky stone",
                new PursuitPath(
                        new Point(0, 0),
                        new Point(RED_BLOCK5_X, RED_BLOCK_LOCATION_Y))
                        .setMaxAcceleration(0.01), 0));
    }

    public final static Path RedPurePursuitCenter = new Path("collect center sky stones");

    static {
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
    }

    public final static Path RedPurePursuitCenterMoreStone = new Path("collect center sky stones");

    static {
        RedPurePursuitCenterMoreStone.addSegment(new PurePursuitSegment("drive to first sky stone",
                new PursuitPath(
                        new Point(0, 0),
                        new Point(RED_BLOCK5_X, RED_BLOCK_LOCATION_Y))
                        .setMaxAcceleration(0.01), 0));
        RedPurePursuitCenterMoreStone.addSegment(new PurePursuitSegment("first trip to foundation",
                new PursuitPath(
                        new Point(RED_BLOCK5_X, RED_BLOCK_LOCATION_Y),
                        new Point(RED_BLOCK5_X, RED_RUNWAY_Y),
                        new Point(RED_FOUNDATION_MIDDLE_X, RED_RUNWAY_Y),
                        new Point(RED_FOUNDATION_MIDDLE_X, RED_FOUNDATION_Y)), 0));
        RedPurePursuitCenterMoreStone.addSegment(new PurePursuitSegment("drive to second sky stone",
                new PursuitPath(
                        new Point(RED_FOUNDATION_MIDDLE_X, RED_FOUNDATION_Y),
                        new Point(RED_FOUNDATION_MIDDLE_X, RED_RUNWAY_Y),
                        new Point(RED_BLOCK2_X, RED_RUNWAY_Y),
                        new Point(RED_BLOCK2_X, RED_BLOCK_LOCATION_Y + OFF_SET)), 0));
        RedPurePursuitCenterMoreStone.addSegment(new PurePursuitSegment("second trip to foundation",
                new PursuitPath(
                        new Point(RED_BLOCK2_X, RED_BLOCK_LOCATION_Y + OFF_SET),
                        new Point(RED_BLOCK2_X, RED_RUNWAY_Y_2),
                        new Point(RED_FOUNDATION_MIDDLE_NEAR_X, RED_RUNWAY_Y_2),
                        new Point(RED_FOUNDATION_MIDDLE_NEAR_X, RED_FOUNDATION_Y)), 50));
        RedPurePursuitCenterMoreStone.addSegment(new PurePursuitSegment("drive to third sky stone",
                new PursuitPath(
                        new Point(RED_FOUNDATION_MIDDLE_NEAR_X, RED_FOUNDATION_Y),
                        new Point(RED_FOUNDATION_MIDDLE_NEAR_X, RED_RUNWAY_Y_2),
                        new Point(RED_BLOCK6_X, RED_RUNWAY_Y_2),
                        new Point(RED_BLOCK6_X, RED_BLOCK_LOCATION_Y))
                        .setMaxDeceleration(0.01), 100));
        RedPurePursuitCenterMoreStone.addSegment(new PurePursuitSegment("last trip to foundation",
                new PursuitPath(
                        new Point(RED_BLOCK6_X, RED_BLOCK_LOCATION_Y),
                        new Point(RED_BLOCK6_X + 10, RED_RUNWAY_Y_2),
                        new Point(RED_FOUNDATION_MIDDLE_NEAR_X, RED_RUNWAY_Y_2),
                        new Point(RED_FOUNDATION_MIDDLE_NEAR_X, RED_FOUNDATION_Y_2ND)), 100));
//        RedPurePursuitCenterMoreStone.addSegment(new PurePursuitSegment("drive to fourth sky stone",
//                new PursuitPath(new Point(RED_FOUNDATION_MIDDLE_NEAR_X, RED_FOUNDATION_Y_2ND), new Point(RED_FOUNDATION_MIDDLE_NEAR_X, RED_RUNWAY_Y_2), new Point(RED_BLOCK4_X, RED_RUNWAY_Y_2), new Point(RED_BLOCK4_X, RED_BLOCK_LOCATION_Y)), 100));
//        RedPurePursuitCenterMoreStone.addSegment(new PurePursuitSegment("fourth trip to foundation",
//                new PursuitPath(new Point(RED_BLOCK4_X, RED_BLOCK_LOCATION_Y), new Point(RED_BLOCK4_X, RED_RUNWAY_Y_2), new Point(RED_FOUNDATION_MIDDLE_NEAR_X, RED_RUNWAY_Y_2), new Point(RED_FOUNDATION_MIDDLE_NEAR_X, RED_FOUNDATION_Y_2ND)), 100));
    }

    public final static Path RedPurePursuitRight = new Path("collect right sky stones");

    static {
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
    }

    public final static Path RedDragFoundation = new Path("Red Drag Foundation");

    static {
        RedDragFoundation.addSegment(new PurePursuitSegment("Pull the foundation", new PursuitPath(
                new Point(RED_FOUNDATION_MIDDLE_NEAR_X + 0, RED_FOUNDATION_Y_2ND + 0),
                new Point(RED_FOUNDATION_MIDDLE_NEAR_X + 20, RED_FOUNDATION_Y_2ND + 16))
                .setMinSpeed(0.8)
                .setTurnSpeed(1.0)
                .setTurnGain(1.0)
                .setPositionError(15.0)
                .setHeadingError(10.0)
                .setPathSmoothing(0.5)
                .setPointSpacing(1.0), 1000, -55, 1600));
        RedDragFoundation.addSegment(new PurePursuitSegment("park the foundation", new PursuitPath(
                new Point(RED_FOUNDATION_MIDDLE_NEAR_X + 20, RED_FOUNDATION_Y_2ND + 14),
                new Point(RED_FOUNDATION_MIDDLE_NEAR_X + 6, RED_FOUNDATION_Y_2ND + 8))
                .setMinSpeed(1.1)
                .setTurnSpeed(1.0)
                .setTurnGain(3.0)
                .setPositionError(15.0)
                .setHeadingError(10.0)
                .setPathSmoothing(0.5)
                .setPointSpacing(1.0), 0, -90, 1500));
        RedDragFoundation.addSegment(new PurePursuitSegment("park the robot", new PursuitPath(
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

    }

    public final static Path RedDragFoundationTest = new Path("Red Drag Foundation Test");

    static {
        RedDragFoundationTest.addSegment(new PurePursuitSegment("Pull the foundation", new PursuitPath(
                new Point(0, 0),
                new Point(20, +16))
                .setMinSpeed(0.7)
                .setTurnSpeed(1.0)
                .setTurnGain(1.0)
                .setPositionError(15.0)
                .setHeadingError(10.0)
                .setPathSmoothing(0.5)
                .setPointSpacing(1.0), 500, -55, 1500));
        RedDragFoundationTest.addSegment(new PurePursuitSegment("park the foundation", new PursuitPath(
                new Point(20, +14),
                new Point(6, +8))
                .setMinSpeed(0.8)
                .setTurnSpeed(1.0)
                .setTurnGain(2.0)
                .setPositionError(15.0)
                .setHeadingError(10.0)
                .setPathSmoothing(0.5)
                .setPointSpacing(1.0), 0, -90, 1300));
        RedDragFoundationTest.addSegment(new PurePursuitSegment("park the robot", new PursuitPath(
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

//        BlueDragFoundationTest.addSegment(new PurePursuitSegment("park the foundation", new PursuitPath(new Point(-20, +12), new Point(-17, +4)).setMinSpeed(0.3).setPositionError(4.0), 0, 90));
//        BlueDragFoundationTest.addSegment(new PurePursuitSegment("park the robot", new PursuitPath(new Point(- -17, +4), new Point(-26, +1), new Point(-48, +-2)).setMinSpeed(0.3).setPositionError(4.0), 0, 90));
    }

    public final static Path RedDragFoundationPark = new Path("Red Drag Foundation Park");

    static {
        RedDragFoundationPark.addSegment(new PurePursuitSegment("drive to the wall", new PursuitPath(
                new Point(RED_FOUNDATION_MIDDLE_X, RED_RUNWAY_Y_2),
                new Point(RED_FOUNDATION_MIDDLE_X + 6, RED_RUNWAY_Y_2 + 4))
                .setMinSpeed(0.8)
                .setPositionError(10.0), PERIOD));
//        RedDragFoundationPark.addSegment(new PurePursuitSegment("park the foundation", new PursuitPath(
//                new Point(RED_FOUNDATION_MIDDLE_X, RED_RUNWAY_Y_2 + 8),
//                new Point(RED_FOUNDATION_MIDDLE_X - 10, RED_RUNWAY_Y_2 + 8))
//                .setMinSpeed(0.5)
//                .setPositionError(4.0)
//                .setHeadingError(15.0)
//                .setTurnSpeed(1.0), PERIOD, -90));

//        RedDragFoundationPark.addSegment(new PurePursuitSegment("park the robot", new PursuitPath(
//                new Point(RED_FOUNDATION_MIDDLE_X - 16, RED_RUNWAY_Y_2 + 12),
//                new Point(RED_FOUNDATION_MIDDLE_X - 16, RED_RUNWAY_Y_2),
//                new Point(RED_FOUNDATION_MIDDLE_X + 16, RED_RUNWAY_Y_2))
//                .setMinSpeed(0.3)
//                .setPositionError(4.0), PERIOD, -90));

    }

    public final static Path RedRobotPark = new Path("Red Robot Park");

    static {

        RedRobotPark.addSegment(new PurePursuitSegment("park the robot", new PursuitPath(
                new Point(RED_FOUNDATION_MIDDLE_X + 10, -45),
                new Point(-49, -44))
                .setMinSpeed(0.3)
                .setHeadingError(10.0)
                .setPositionError(3.0), PERIOD, -90));
    }

    public final static Path CalibrationPath = new Path("Calibration Path");

    static {

        CalibrationPath.addSegment(new PurePursuitSegment("first trip to foundation", new PursuitPath(
                new Point(0, 0),
                new Point(0, 10),
                new Point(80, 10),
                new Point(80, 0))));
        CalibrationPath.addSegment(new PurePursuitSegment("drive back", new PursuitPath(
                new Point(80, 0),
                new Point(80, 10),
                new Point(0, 10),
                new Point(0, 0))));

    }

    public final static Path lastStoneDrive = new Path("last stone drive");

    static {
        lastStoneDrive.addSegment(new PurePursuitSegment("trip to last stone", new PursuitPath(
                new Point(0, 0),
                new Point(48, 0))));
    }

    public final static Path middleLastStone = new Path("middle last stone delivery");

    static {
        middleLastStone.addSegment(new PurePursuitSegment("drive to last stone", new PursuitPath(
                new Point(48, 0),
                new Point(48, RED_BLOCK_LOCATION_Y))));
        middleLastStone.addSegment(new PurePursuitSegment("drive to foundation", new PursuitPath(new Point(48, RED_BLOCK_LOCATION_Y), new Point(48, RED_RUNWAY_Y_2), new Point(-72, RED_RUNWAY_Y_2))));
    }

    public final static Path RedDragFoundationParking = new Path("Red Drag Foundation parking");

    static {
        RedDragFoundationParking.addSegment(new PurePursuitSegment("drive down the wall", new PursuitPath(new Point(0, 0), new Point(-12, 0)), 0));
        RedDragFoundationParking.addSegment(new PurePursuitSegment("drive up to the foundation", new PursuitPath(new Point(-12, 0), new Point(-12, RED_FOUNDATION_Y_2ND)), 0));
        RedDragFoundationParking.addSegment(new PurePursuitSegment("Pull the foundation", new PursuitPath(new Point(0 - 12, 0 + RED_FOUNDATION_Y_2ND), new Point(20 - 12, 12 + RED_FOUNDATION_Y_2ND)).setMinSpeed(0.6).setPositionError(5.0), 0, -35));
        RedDragFoundationParking.addSegment(new PurePursuitSegment("park the foundation", new PursuitPath(new Point(20 - 12, 12 + RED_FOUNDATION_Y_2ND), new Point(20 - 12, 6 + RED_FOUNDATION_Y_2ND)).setMinSpeed(0.4).setPositionError(5.0), 0, -90));
        RedDragFoundationParking.addSegment(new PurePursuitSegment("park the robot", new PursuitPath(new Point(24 - 12, 4 + RED_FOUNDATION_Y_2ND), new Point(26 - 12, 3 + RED_FOUNDATION_Y_2ND), new Point(55 - 12, 6)).setMinSpeed(0.3).setPositionError(4.0), 0, -90));
    }

    public final static Path RedDragFoundationParking2 = new Path("Red Drag Foundation parking 2");

    static {
        RedDragFoundationParking2.addSegment(new PurePursuitSegment("drive down the wall", new PursuitPath(
                new Point(0, 0),
                new Point(-22, 0))
                .setPathSmoothing(0.5)
                .setPointSpacing(1.0), 0));
        RedDragFoundationParking2.addSegment(new PurePursuitSegment("drive up to the foundation", new PursuitPath(
                new Point(-22, 0),
                new Point(-22, RED_FOUNDATION_Y_2ND)).setPathSmoothing(0.5).setPointSpacing(1.0), 0));
        RedDragFoundationParking2.addSegment(new PurePursuitSegment("Pull the foundation", new PursuitPath(
                new Point(-22, RED_FOUNDATION_Y_2ND),
                new Point(-3, -10))
                .setMinSpeed(0.6)
                .setPositionError(5.0)
                .setTurnSpeed(0.6)
                .setPathSmoothing(0.5)
                .setPointSpacing(1.0), 1000, -35));
        RedDragFoundationParking2.addSegment(new PurePursuitSegment("park the foundation", new PursuitPath(
                new Point(-3, -10),
                new Point(2, -4))
                .setMinSpeed(0.5)
                .setPositionError(8.0)
                .setTurnSpeed(0.7)
                .setPathSmoothing(0.5)
                .setPointSpacing(1.0), 0, -90));
        RedDragFoundationParking2.addSegment(new PurePursuitSegment("park the robot", new PursuitPath(
                new Point(2, -4),
                new Point(1, -3),
                new Point(55 - 12, -1))
                .setMinSpeed(0.3)
                .setPositionError(4.0)
                .setPathSmoothing(0.5)
                .setPointSpacing(1.0), 0, -90));
    }

    public final static Path RunwayDrive = new Path("Drive along runway");

    static {
        RunwayDrive.addSegment(new PurePursuitSegment("Run along runway", new PursuitPath(new Point(0, 0), new Point(-50, 0))));
        RunwayDrive.addSegment(new PurePursuitSegment("Run along runway 2", new PursuitPath(new Point(-50, 0), new Point(-100, 0)), 1000));
    }

    public final static Path BluePurePursuitLeft = new Path("collect left sky stones on blue side");

    static {
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

    }

    public final static Path BluePurePursuitCenter = new Path("collect center sky stones on blue side");

    static {
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
    }

    public final static Path BluePurePursuitRight = new Path("collect right sky stones on blue side");

    static {
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
                        new Point(BLUE_BLOCK1_X, BLUE_BLOCK_LOCATION_Y - 2)).setMaxDeceleration(0.017), PERIOD));
        BluePurePursuitRight.addSegment(new PurePursuitSegment("second trip to foundation",
                new PursuitPath(new Point(BLUE_BLOCK1_X, BLUE_BLOCK_LOCATION_Y - 2),
                        new Point(BLUE_BLOCK1_X, BLUE_RUNWAY_Y_2),
                        new Point(BLUE_FOUNDATION_NEAR_X, BLUE_RUNWAY_Y_2),
                        new Point(BLUE_FOUNDATION_NEAR_X + 5, BLUE_FOUNDATION_Y)).setMaxDeceleration(0.017), PERIOD));
        BluePurePursuitRight.addSegment(new PurePursuitSegment("drive to third sky stone",
                new PursuitPath(new Point(BLUE_FOUNDATION_NEAR_X + 5, BLUE_FOUNDATION_Y),
                        new Point(BLUE_FOUNDATION_NEAR_X, BLUE_RUNWAY_Y),
                        new Point(BLUE_BLOCK6_X + 2, BLUE_RUNWAY_Y + OFF_SET),
                        new Point(BLUE_BLOCK6_X + 2, BLUE_BLOCK_LOCATION_Y - 2)).setMaxDeceleration(0.013), PERIOD));
        BluePurePursuitRight.addSegment(new PurePursuitSegment("last trip to foundation",
                new PursuitPath(new Point(BLUE_BLOCK6_X + 2, BLUE_BLOCK_LOCATION_Y - 2),
                        new Point(BLUE_BLOCK6_X + 2, BLUE_RUNWAY_Y_2),
                        new Point(BLUE_FOUNDATION_MIDDLE_NEAR_X, BLUE_RUNWAY_Y_2),
                        new Point(BLUE_FOUNDATION_MIDDLE_NEAR_X, BLUE_FOUNDATION_Y_2ND))
                        .setMaxDeceleration(0.01)
                        .setPointSpacing(1.0)
                        .setPathSmoothing(0.5), PERIOD));

    }

    public final static Path BlueDragFoundation = new Path("Blue Drag Foundation");

    static {
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
    }

    public final static Path BlueDragFoundationTest = new Path("Blue Drag Foundation Test");

    static {
        BlueDragFoundationTest.addSegment(new PurePursuitSegment("Pull the foundation", new PursuitPath(
                new Point(0, 0),
                new Point(-20, +16))
                .setMinSpeed(0.8)
                .setTurnSpeed(1.0)
                .setTurnGain(1.0)
                .setPositionError(15.0)
                .setHeadingError(10.0)
                .setPathSmoothing(0.5)
                .setPointSpacing(1.0), 0, 55, 1600));
        BlueDragFoundationTest.addSegment(new PurePursuitSegment("park the foundation", new PursuitPath(
                new Point(-20, +14),
                new Point(-6, +8))
                .setMinSpeed(1.0)
                .setTurnSpeed(1.0)
                .setTurnGain(2.0)
                .setPositionError(15.0)
                .setHeadingError(10.0)
                .setPathSmoothing(0.5)
                .setPointSpacing(1.0), 0, 90, 1400));
        BlueDragFoundationTest.addSegment(new PurePursuitSegment("park the robot", new PursuitPath(
                new Point(-14, +5),
                new Point(-18, 1),
                new Point(-50, -2))
                .setMinSpeed(0.25)
                .setMaxSpeed(0.6)
                .setTurnSpeed(0.1)
                .setTurnGain(1.0)
                .setPositionError(6.0)
                .setHeadingError(8.0)
                .setPathSmoothing(0.5)
                .setPointSpacing(1.0), 0, 90, 3000));

//        BlueDragFoundationTest.addSegment(new PurePursuitSegment("park the foundation", new PursuitPath(new Point(-20, +12), new Point(-17, +4)).setMinSpeed(0.3).setPositionError(4.0), 0, 90));
//        BlueDragFoundationTest.addSegment(new PurePursuitSegment("park the robot", new PursuitPath(new Point(- -17, +4), new Point(-26, +1), new Point(-48, +-2)).setMinSpeed(0.3).setPositionError(4.0), 0, 90));
    }

    public final static Path RedShortPath = new Path("park robot");

    static {
        RedShortPath.addSegment(new PurePursuitSegment("Park",
                new PursuitPath(new Point(0, 0), new Point(RED_PARK_X, RED_PARK_Y))));
    }

    public final static Path BlueShortPath = new Path("park robot");

    static {
        BlueShortPath.addSegment(new PurePursuitSegment("Park",
                new PursuitPath(new Point(0, 0), new Point(BLUE_PARK_X, BLUE_PARK_Y))));


    }

    public final static Path RedDistancePath = new Path("pick up first stone");

    static {
        RedDistancePath.addSegment(new PurePursuitSegment("drive to first sky stone",
                new PursuitPath(new Point(0, 0),
                        new Point(RED_BLOCK5_X+2, RED_BLOCK_LOCATION_Y+4)) .setMaxAcceleration(0.01),0,0,
                                                                    10000,true));
    }


}


