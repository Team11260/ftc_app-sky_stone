package org.firstinspires.ftc.teamcode.mecanum.hardware;

import android.provider.Telephony;

import org.firstinspires.ftc.teamcode.framework.userhardware.paths.Path;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.PurePursuitSegment;
import org.firstinspires.ftc.teamcode.framework.userhardware.purepursuit.Point;
import org.firstinspires.ftc.teamcode.framework.userhardware.purepursuit.PursuitPath;

import java.time.OffsetDateTime;

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

    public static double RED_BLOCK_LOCATION_Y = -25.5 + FIELD_OFFSET;
    public static double RED_FOUNDATION_Y = -27.5;
    public static double RED_FOUNDATION_Y_2ND = -29.5;

    public static double RED_FOUNDATION_NEAR_X = -74;  //  Play with this parameter
    public static double RED_FOUNDATION_MIDDLE_NEAR_X = -76;  //  Play with this parameter
    public static double RED_FOUNDATION_MIDDLE_X = -83;  //Measure this parameter

    // public static double RED_FOUNDATION_DRAGGER_X = -75;
    public static double RED_RUNWAY_Y = -17;  //  Play with this parameter
    public static double RED_RUNWAY_Y_2 = -17;
    public static double RED_DRAGGER_BACKUP_Y = -23;
    public static double RED_DRAGGER_FORWARD_HALFWAY_Y = -26;
    public static double RED_DRAGGER_FORWARD_FULL_Y = -29;
    public static double OFF_SET = -2;
    //count from wall
    public static double RED_BLOCK1_X = 30;
    public static double RED_BLOCK2_X = 23;
    public static double RED_BLOCK3_X = 14;   //Was 16
    public static double RED_BLOCK4_X = 6;
    public static double RED_BLOCK5_X = -1;
    public static double RED_BLOCK6_X = -9;

    public static double BLUE_RUNWAY_Y = -19;
    public static double BLUE_RUNWAY_Y_2 = -20;
    public static double BLUE_FOUNDATION_Y = -27.5;
    public static double BLUE_FOUNDATION_Y_2ND = -31;
    public static double BLUE_FOUNDATION_MIDDLE_X = 83.5;
    public static double BLUE_FOUNDATION_MIDDLE_NEAR_X = 76;
    public static double BLUE_FOUNDATION_NEAR_X = 72.5;
    public static double BLUE_BLOCK_LOCATION_Y = -26.5 + FIELD_OFFSET;
    //count from wall
    public static double BLUE_BLOCK1_X = -31;
    public static double BLUE_BLOCK2_X = -24;
    public static double BLUE_BLOCK3_X = -16;
    public static double BLUE_BLOCK4_X = -8;
    public static double BLUE_BLOCK5_X = 0;
    public static double BLUE_BLOCK6_X = 6;


    /*  Paths for red side left sky stone*/

    public final static Path RedPurePursuitLeft = new Path("collect left sky stones");

    static {
        RedPurePursuitLeft.addSegment(new PurePursuitSegment("drive to first sky stone",
                new PursuitPath(new Point(0, 0), new Point(RED_BLOCK4_X, RED_BLOCK_LOCATION_Y)), 300));
        RedPurePursuitLeft.addSegment(new PurePursuitSegment("first trip to foundation",
                new PursuitPath(new Point(RED_BLOCK4_X, RED_BLOCK_LOCATION_Y), new Point(RED_BLOCK4_X, RED_RUNWAY_Y), new Point(RED_FOUNDATION_MIDDLE_X, RED_RUNWAY_Y), new Point(RED_FOUNDATION_MIDDLE_X, RED_FOUNDATION_Y)), 200));
        RedPurePursuitLeft.addSegment(new PurePursuitSegment("drive to second sky stone",
                new PursuitPath(new Point(RED_FOUNDATION_MIDDLE_X, RED_FOUNDATION_Y), new Point(RED_FOUNDATION_MIDDLE_X, RED_RUNWAY_Y), new Point(RED_BLOCK1_X, RED_RUNWAY_Y + OFF_SET), new Point(RED_BLOCK1_X, RED_BLOCK_LOCATION_Y)), 200));
        RedPurePursuitLeft.addSegment(new PurePursuitSegment("second trip to foundation",
                new PursuitPath(new Point(RED_BLOCK1_X, RED_BLOCK_LOCATION_Y), new Point(RED_BLOCK1_X, RED_RUNWAY_Y_2), new Point(RED_FOUNDATION_MIDDLE_NEAR_X, RED_RUNWAY_Y_2), new Point(RED_FOUNDATION_MIDDLE_NEAR_X, RED_FOUNDATION_Y)), 200));
        RedPurePursuitLeft.addSegment(new PurePursuitSegment("drive to third sky stone",
                new PursuitPath(new Point(RED_FOUNDATION_MIDDLE_NEAR_X, RED_FOUNDATION_Y), new Point(RED_FOUNDATION_MIDDLE_NEAR_X, RED_RUNWAY_Y_2), new Point(RED_BLOCK6_X, RED_RUNWAY_Y_2), new Point(RED_BLOCK6_X, RED_BLOCK_LOCATION_Y)), 200));
        RedPurePursuitLeft.addSegment(new PurePursuitSegment("third trip to foundation",
                new PursuitPath(new Point(RED_BLOCK6_X, RED_BLOCK_LOCATION_Y), new Point(RED_BLOCK6_X, RED_RUNWAY_Y_2), new Point(RED_FOUNDATION_MIDDLE_NEAR_X, RED_RUNWAY_Y_2), new Point(RED_FOUNDATION_MIDDLE_NEAR_X, RED_FOUNDATION_Y_2ND)), 200));

    }

    public final static Path RedPurePursuitCenter = new Path("collect center sky stones");

    static {
        RedPurePursuitCenter.addSegment(new PurePursuitSegment("drive to first sky stone",
                new PursuitPath(new Point(0, 0), new Point(RED_BLOCK5_X, RED_BLOCK_LOCATION_Y)), 0));
        RedPurePursuitCenter.addSegment(new PurePursuitSegment("first trip to foundation",
                new PursuitPath(new Point(RED_BLOCK5_X, RED_BLOCK_LOCATION_Y), new Point(RED_BLOCK5_X, RED_RUNWAY_Y), new Point(RED_FOUNDATION_MIDDLE_X, RED_RUNWAY_Y), new Point(RED_FOUNDATION_MIDDLE_X, RED_FOUNDATION_Y)), 200));
        RedPurePursuitCenter.addSegment(new PurePursuitSegment("drive to second sky stone",
                new PursuitPath(new Point(RED_FOUNDATION_MIDDLE_X, RED_FOUNDATION_Y), new Point(RED_FOUNDATION_MIDDLE_X, RED_RUNWAY_Y), new Point(RED_BLOCK2_X, RED_RUNWAY_Y), new Point(RED_BLOCK2_X, RED_BLOCK_LOCATION_Y + OFF_SET)), 200));
        RedPurePursuitCenter.addSegment(new PurePursuitSegment("second trip to foundation",
                new PursuitPath(new Point(RED_BLOCK2_X, RED_BLOCK_LOCATION_Y + OFF_SET), new Point(RED_BLOCK2_X, RED_RUNWAY_Y_2), new Point(RED_FOUNDATION_MIDDLE_NEAR_X, RED_RUNWAY_Y_2), new Point(RED_FOUNDATION_MIDDLE_NEAR_X, RED_FOUNDATION_Y)), 200));
        RedPurePursuitCenter.addSegment(new PurePursuitSegment("drive to third sky stone",
                new PursuitPath(new Point(RED_FOUNDATION_MIDDLE_NEAR_X, RED_FOUNDATION_Y), new Point(RED_FOUNDATION_MIDDLE_NEAR_X, RED_RUNWAY_Y_2), new Point(RED_BLOCK6_X, RED_RUNWAY_Y_2), new Point(RED_BLOCK6_X, RED_BLOCK_LOCATION_Y)), 200));
        RedPurePursuitCenter.addSegment(new PurePursuitSegment("third trip to foundation",
                new PursuitPath(new Point(RED_BLOCK6_X, RED_BLOCK_LOCATION_Y), new Point(RED_BLOCK6_X, RED_RUNWAY_Y_2), new Point(RED_FOUNDATION_MIDDLE_NEAR_X, RED_RUNWAY_Y_2), new Point(RED_FOUNDATION_MIDDLE_NEAR_X, RED_FOUNDATION_Y_2ND)), 200));


    }

    public final static Path RedPurePursuitRight = new Path("collect right sky stones");

    static {
        RedPurePursuitRight.addSegment(new PurePursuitSegment("drive to first sky stone",
                new PursuitPath(new Point(0, 0), new Point(RED_BLOCK6_X, RED_BLOCK_LOCATION_Y)), 0));
        RedPurePursuitRight.addSegment(new PurePursuitSegment("first trip to foundation",
                new PursuitPath(new Point(RED_BLOCK6_X, RED_BLOCK_LOCATION_Y), new Point(RED_BLOCK6_X, RED_RUNWAY_Y), new Point(RED_FOUNDATION_MIDDLE_X, RED_RUNWAY_Y), new Point(RED_FOUNDATION_MIDDLE_X, RED_FOUNDATION_Y)), 200));
        RedPurePursuitRight.addSegment(new PurePursuitSegment("drive to second sky stone",
                new PursuitPath(new Point(RED_FOUNDATION_MIDDLE_X, RED_FOUNDATION_Y), new Point(RED_FOUNDATION_MIDDLE_X, RED_RUNWAY_Y), new Point(RED_BLOCK3_X, RED_RUNWAY_Y), new Point(RED_BLOCK3_X, RED_BLOCK_LOCATION_Y + OFF_SET)), 200));
        RedPurePursuitRight.addSegment(new PurePursuitSegment("second trip to foundation",
                new PursuitPath(new Point(RED_BLOCK3_X, RED_BLOCK_LOCATION_Y), new Point(RED_BLOCK3_X, RED_RUNWAY_Y_2), new Point(RED_FOUNDATION_MIDDLE_NEAR_X, RED_RUNWAY_Y_2), new Point(RED_FOUNDATION_MIDDLE_NEAR_X, RED_FOUNDATION_Y)), 200));
        RedPurePursuitRight.addSegment(new PurePursuitSegment("drive to third sky stone",
                new PursuitPath(new Point(RED_FOUNDATION_MIDDLE_NEAR_X, RED_FOUNDATION_Y), new Point(RED_FOUNDATION_MIDDLE_NEAR_X, RED_RUNWAY_Y_2), new Point(RED_BLOCK5_X, RED_RUNWAY_Y_2), new Point(RED_BLOCK5_X, RED_BLOCK_LOCATION_Y)), 200));
        RedPurePursuitRight.addSegment(new PurePursuitSegment("third trip to foundation",
                new PursuitPath(new Point(RED_BLOCK5_X, RED_BLOCK_LOCATION_Y), new Point(RED_BLOCK5_X, RED_RUNWAY_Y_2), new Point(RED_FOUNDATION_MIDDLE_NEAR_X, RED_RUNWAY_Y_2), new Point(RED_FOUNDATION_MIDDLE_NEAR_X, RED_FOUNDATION_Y_2ND)), 200));

    }

    public final static Path RedDragFoundation = new Path("Red Drag Foundation");

    static {
        RedDragFoundation.addSegment(new PurePursuitSegment("Pull the foundation", new PursuitPath(new Point(RED_FOUNDATION_MIDDLE_NEAR_X, RED_FOUNDATION_Y_2ND+2), new Point(RED_FOUNDATION_MIDDLE_NEAR_X + 20, RED_FOUNDATION_Y_2ND + 12)).setMinSpeed(0.5).setPositionError(4.0), 400, -35));
        RedDragFoundation.addSegment(new PurePursuitSegment("park the foundation", new PursuitPath(new Point(RED_FOUNDATION_MIDDLE_NEAR_X + 20, RED_FOUNDATION_Y_2ND + 12), new Point(RED_FOUNDATION_MIDDLE_NEAR_X + 22, RED_FOUNDATION_Y_2ND + 12)).setMinSpeed(0.4).setPositionError(4.0), 0, -90));
        RedDragFoundation.addSegment(new PurePursuitSegment("park the robot", new PursuitPath(new Point(RED_FOUNDATION_MIDDLE_NEAR_X + 22, RED_FOUNDATION_Y_2ND + 12), new Point(RED_FOUNDATION_MIDDLE_NEAR_X + 26, RED_FOUNDATION_Y_2ND + 5), new Point(RED_FOUNDATION_MIDDLE_NEAR_X + 52, RED_FOUNDATION_Y_2ND + 2)).setMinSpeed(0.3).setPositionError(4.0), 0, -90));
    }

    public final static Path RedDragFoundationTest = new Path("Red Drag Foundation Test");

    static {
        RedDragFoundationTest.addSegment(new PurePursuitSegment("Pull the foundation", new PursuitPath(new Point(0, 0), new Point(20, 12)).setMinSpeed(0.6).setPositionError(4.0), 0, -35));
        RedDragFoundationTest.addSegment(new PurePursuitSegment("park the foundation", new PursuitPath(new Point(20, 12), new Point(22, 12)).setMinSpeed(0.4).setPositionError(4.0), 0, -90));
        RedDragFoundationTest.addSegment(new PurePursuitSegment("park the robot", new PursuitPath(new Point(22, 12), new Point(26, 5), new Point(52, 2)).setMinSpeed(0.3).setPositionError(4.0), 0, -90));
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

    public final static Path RunwayDrive = new Path("Drive along runway");

    static {
        RunwayDrive.addSegment(new PurePursuitSegment("Run along runway", new PursuitPath(new Point(0, 0), new Point(-50, 0))));
        RunwayDrive.addSegment(new PurePursuitSegment("Run along runway 2", new PursuitPath(new Point(-50, 0), new Point(-100, 0)), 1000));
    }

    public final static Path BluePurePursuitLeft = new Path("collect left sky stones on blue side");

    static {
        BluePurePursuitLeft.addSegment(new PurePursuitSegment("drive to first sky stone",
                new PursuitPath(new Point(0, 0), new Point(BLUE_BLOCK6_X, BLUE_BLOCK_LOCATION_Y)), 300));
        BluePurePursuitLeft.addSegment(new PurePursuitSegment("first trip to foundation",
                new PursuitPath(new Point(BLUE_BLOCK6_X, BLUE_BLOCK_LOCATION_Y),
                        new Point(BLUE_BLOCK6_X, BLUE_RUNWAY_Y),
                        new Point(BLUE_FOUNDATION_NEAR_X, BLUE_RUNWAY_Y),
                        new Point(BLUE_FOUNDATION_MIDDLE_X, BLUE_FOUNDATION_Y)), 300));

        BluePurePursuitLeft.addSegment(new PurePursuitSegment("drive to second sky stone",
                new PursuitPath(new Point(BLUE_FOUNDATION_MIDDLE_X, BLUE_FOUNDATION_Y),
                        new Point(BLUE_FOUNDATION_MIDDLE_X, BLUE_RUNWAY_Y),
                        new Point(BLUE_BLOCK3_X, BLUE_RUNWAY_Y + OFF_SET),
                        new Point(BLUE_BLOCK3_X, BLUE_BLOCK_LOCATION_Y)), 300));
        BluePurePursuitLeft.addSegment(new PurePursuitSegment("second trip to foundation",
                new PursuitPath(new Point(BLUE_BLOCK3_X, BLUE_BLOCK_LOCATION_Y),
                        new Point(BLUE_BLOCK3_X, BLUE_RUNWAY_Y_2),
                        new Point(BLUE_FOUNDATION_MIDDLE_NEAR_X, BLUE_RUNWAY_Y_2),
                        new Point(BLUE_FOUNDATION_MIDDLE_NEAR_X, BLUE_FOUNDATION_Y_2ND)), 300));
    }


    public final static Path BluePurePursuitCenter = new Path("collect center sky stones on blue side");

    static {
        BluePurePursuitCenter.addSegment(new PurePursuitSegment("drive to first sky stone",
                new PursuitPath(new Point(0, 0), new Point(BLUE_BLOCK5_X, BLUE_BLOCK_LOCATION_Y)), 300));
        BluePurePursuitCenter.addSegment(new PurePursuitSegment("first trip to foundation",
                new PursuitPath(new Point(BLUE_BLOCK5_X, BLUE_BLOCK_LOCATION_Y),
                        new Point(BLUE_BLOCK5_X, BLUE_RUNWAY_Y),
                        new Point(BLUE_FOUNDATION_NEAR_X, BLUE_RUNWAY_Y),
                        new Point(BLUE_FOUNDATION_MIDDLE_X, BLUE_FOUNDATION_Y)), 300));

        BluePurePursuitCenter.addSegment(new PurePursuitSegment("drive to second sky stone",
                new PursuitPath(new Point(BLUE_FOUNDATION_MIDDLE_X, BLUE_FOUNDATION_Y),
                        new Point(BLUE_FOUNDATION_MIDDLE_X, BLUE_RUNWAY_Y),
                        new Point(BLUE_BLOCK2_X, BLUE_RUNWAY_Y + OFF_SET),
                        new Point(BLUE_BLOCK2_X, BLUE_BLOCK_LOCATION_Y)), 300));
        BluePurePursuitCenter.addSegment(new PurePursuitSegment("second trip to foundation",
                new PursuitPath(new Point(BLUE_BLOCK2_X, BLUE_BLOCK_LOCATION_Y),
                        new Point(BLUE_BLOCK2_X, BLUE_RUNWAY_Y_2),
                        new Point(BLUE_FOUNDATION_MIDDLE_NEAR_X, BLUE_RUNWAY_Y_2),
                        new Point(BLUE_FOUNDATION_MIDDLE_NEAR_X, BLUE_FOUNDATION_Y_2ND - 2)), 300));
    }

    public final static Path BluePurePursuitRight = new Path("collect right sky stones on blue side");

    static {
        BluePurePursuitRight.addSegment(new PurePursuitSegment("drive to first sky stone",
                new PursuitPath(new Point(0, 0), new Point(BLUE_BLOCK4_X, BLUE_BLOCK_LOCATION_Y)), 300));
        BluePurePursuitRight.addSegment(new PurePursuitSegment("first trip to foundation",
                new PursuitPath(new Point(BLUE_BLOCK4_X, BLUE_BLOCK_LOCATION_Y),
                        new Point(BLUE_BLOCK4_X, BLUE_RUNWAY_Y),
                        new Point(BLUE_FOUNDATION_NEAR_X, BLUE_RUNWAY_Y),
                        new Point(BLUE_FOUNDATION_MIDDLE_X, BLUE_FOUNDATION_Y)), 300));

        BluePurePursuitRight.addSegment(new PurePursuitSegment("drive to second sky stone",
                new PursuitPath(new Point(BLUE_FOUNDATION_MIDDLE_X, BLUE_FOUNDATION_Y),
                        new Point(BLUE_FOUNDATION_MIDDLE_X, BLUE_RUNWAY_Y),
                        new Point(BLUE_BLOCK1_X, BLUE_RUNWAY_Y + OFF_SET),
                        new Point(BLUE_BLOCK1_X, BLUE_BLOCK_LOCATION_Y)), 300));
        BluePurePursuitRight.addSegment(new PurePursuitSegment("second trip to foundation",
                new PursuitPath(new Point(BLUE_BLOCK1_X, BLUE_BLOCK_LOCATION_Y),
                        new Point(BLUE_BLOCK1_X, BLUE_RUNWAY_Y_2),
                        new Point(BLUE_FOUNDATION_MIDDLE_NEAR_X, BLUE_RUNWAY_Y_2),
                        new Point(BLUE_FOUNDATION_MIDDLE_NEAR_X, BLUE_FOUNDATION_Y_2ND)), 300));
    }

    public final static Path BlueDragFoundation = new Path("Blue Drag Foundation");

    static {
        BlueDragFoundation.addSegment(new PurePursuitSegment("Pull the foundation", new PursuitPath(new Point(BLUE_FOUNDATION_MIDDLE_NEAR_X, BLUE_FOUNDATION_Y_2ND), new Point(BLUE_FOUNDATION_MIDDLE_NEAR_X - 20, BLUE_FOUNDATION_Y_2ND + 12)).setMinSpeed(0.6).setPositionError(4.0), 0, 35));
        BlueDragFoundation.addSegment(new PurePursuitSegment("park the foundation", new PursuitPath(new Point(BLUE_FOUNDATION_MIDDLE_NEAR_X - 20, BLUE_FOUNDATION_Y_2ND + 12), new Point(BLUE_FOUNDATION_MIDDLE_NEAR_X - 17, BLUE_FOUNDATION_Y_2ND + 4)).setMinSpeed(0.4).setPositionError(4.0), 0, 90));
        BlueDragFoundation.addSegment(new PurePursuitSegment("park the robot", new PursuitPath(new Point(BLUE_FOUNDATION_MIDDLE_NEAR_X - 17, BLUE_FOUNDATION_Y_2ND + 4), new Point(BLUE_FOUNDATION_MIDDLE_NEAR_X - 26, BLUE_FOUNDATION_Y_2ND + 1), new Point(BLUE_FOUNDATION_MIDDLE_NEAR_X - 48, BLUE_FOUNDATION_Y_2ND + -2)).setMinSpeed(0.3).setPositionError(4.0), 0, 90));
    }

    public final static Path BlueDragFoundationTest = new Path("Blue Drag Foundation");

    static {
        BlueDragFoundationTest.addSegment(new PurePursuitSegment("Pull the foundation", new PursuitPath(new Point(0, 0), new Point(-20, +12)).setMinSpeed(0.3).setPositionError(4.0), 0, 35));
        BlueDragFoundationTest.addSegment(new PurePursuitSegment("park the foundation", new PursuitPath(new Point(-20, +12), new Point(-17, +4)).setMinSpeed(0.3).setPositionError(4.0), 0, 90));
        BlueDragFoundationTest.addSegment(new PurePursuitSegment("park the robot", new PursuitPath(new Point(- -17, +4), new Point(-26, +1), new Point(-48, +-2)).setMinSpeed(0.3).setPositionError(4.0), 0, 90));
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


}
