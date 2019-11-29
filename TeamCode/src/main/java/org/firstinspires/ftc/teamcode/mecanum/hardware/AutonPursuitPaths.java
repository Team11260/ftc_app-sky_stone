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

    public static double RED_BLOCK_LOCATION_Y = 28;
    public static double RED_FOUNDATION_Y = 28;
    public static double RED_FOUNDATION_NEAR_X = 73;  //  Play with this parameter
    public static double RED_FOUNDATION_MIDDLE_NEAR_X = 76;  //  Play with this parameter
    public static double RED_RUNWAY_Y = 25;  //  Play with this parameter

    public static double RED_BLOCK1_X = -32, RED_BLOCK1_Y = RED_BLOCK_LOCATION_Y;
    public static double RED_BLOCK2_X = -24, RED_BLOCK2_Y = RED_BLOCK_LOCATION_Y;
    public static double RED_BLOCK3_X = -16, RED_BLOCK3_Y = RED_BLOCK_LOCATION_Y;
    public static double RED_BLOCK4_X = -8, RED_BLOCK4_Y = RED_BLOCK_LOCATION_Y;
    public static double RED_BLOCK5_X = 0, RED_BLOCK5_Y = RED_BLOCK_LOCATION_Y;
    public static double RED_BLOCK6_X = 8, RED_BLOCK6_Y = RED_BLOCK_LOCATION_Y;

/*  Paths for red side left sky stone*/

    public final static Path PurePursuitLeft = new Path("collect left sky stones");

    static{
        PurePursuitLeft.addSegment(new PurePursuitSegment("drive to first sky stone",
                new PursuitPath(new Point(0, 0), new Point(RED_BLOCK4_X, RED_BLOCK4_Y)),0));
        PurePursuitLeft.addSegment(new PurePursuitSegment("first trip to foundation",
                new PursuitPath(new Point(RED_BLOCK4_X, RED_BLOCK4_Y), new Point(RED_BLOCK4_X, RED_RUNWAY_Y), new Point(RED_FOUNDATION_NEAR_X, RED_RUNWAY_Y), new Point(RED_FOUNDATION_NEAR_X, RED_FOUNDATION_Y)),300));
        PurePursuitLeft.addSegment(new PurePursuitSegment("drive to second sky stone",
                new PursuitPath(new Point(RED_FOUNDATION_NEAR_X, RED_FOUNDATION_Y), new Point(RED_FOUNDATION_NEAR_X, RED_RUNWAY_Y), new Point(RED_BLOCK1_X, RED_RUNWAY_Y), new Point(RED_BLOCK1_X, RED_BLOCK1_Y)),0));
        PurePursuitLeft.addSegment(new PurePursuitSegment("second trip to foundation",
                new PursuitPath(new Point(RED_BLOCK1_X, RED_BLOCK1_Y), new Point(RED_BLOCK1_X, RED_RUNWAY_Y), new Point(RED_FOUNDATION_NEAR_X, RED_RUNWAY_Y), new Point(RED_FOUNDATION_NEAR_X, RED_FOUNDATION_Y)),300));
        PurePursuitLeft.addSegment(new PurePursuitSegment("drive to third stone",
                new PursuitPath(new Point(RED_FOUNDATION_NEAR_X, RED_FOUNDATION_Y), new Point(RED_FOUNDATION_NEAR_X, RED_RUNWAY_Y), new Point(RED_BLOCK6_X, RED_RUNWAY_Y), new Point(RED_BLOCK6_X, RED_BLOCK6_Y)),0));
        PurePursuitLeft.addSegment(new PurePursuitSegment("third trip to foundation",
                new PursuitPath(new Point(RED_BLOCK6_X, RED_BLOCK6_Y), new Point(RED_BLOCK6_X, RED_RUNWAY_Y), new Point(RED_FOUNDATION_MIDDLE_NEAR_X, RED_RUNWAY_Y), new Point(RED_FOUNDATION_MIDDLE_NEAR_X, RED_FOUNDATION_Y)),300));
    }

    public final static Path PurePursuitCenter = new Path("collect center sky stones");

    static{
        PurePursuitCenter.addSegment(new PurePursuitSegment("drive to first sky stone",
                new PursuitPath(new Point(0, 0), new Point(0, 28)),0));
        PurePursuitCenter.addSegment(new PurePursuitSegment("first trip to foundation",
                new PursuitPath(new Point(0, 28), new Point(0, 25), new Point(73, 25), new Point(73, 28)),300));
        PurePursuitCenter.addSegment(new PurePursuitSegment("drive to second sky stone",
                new PursuitPath(new Point(73, 28), new Point(73, 25), new Point(-24, 25), new Point(-24, 28)),0));
        PurePursuitCenter.addSegment(new PurePursuitSegment("second trip to foundation",
                new PursuitPath(new Point(-24, 28), new Point(-24, 25), new Point(73, 25), new Point(73, 28)),300));
        PurePursuitCenter.addSegment(new PurePursuitSegment("drive to third stone",
                new PursuitPath(new Point(76, 28), new Point(76, 25), new Point(8, 25), new Point(8, 28)),0));
        PurePursuitCenter.addSegment(new PurePursuitSegment("third trip to foundation",
                new PursuitPath(new Point(8, 28), new Point(8, 25), new Point(76, 25), new Point(76, 28)),300));
    }

    public final static Path RedPurePursuitRight = new Path("collect right sky stones");

    static{
        RedPurePursuitRight.addSegment(new PurePursuitSegment("drive to first sky stone",
                new PursuitPath(new Point(0, 0), new Point(8, 28)),0));
        RedPurePursuitRight.addSegment(new PurePursuitSegment("first trip to foundation",
                new PursuitPath(new Point(8, 28), new Point(8, 25), new Point(73, 25), new Point(73, 28)),300));
        RedPurePursuitRight.addSegment(new PurePursuitSegment("drive to second sky stone",
                new PursuitPath(new Point(73, 28), new Point(73, 25), new Point(-16, 25), new Point(-16, 28)),0));
        RedPurePursuitRight.addSegment(new PurePursuitSegment("second trip to foundation",
                new PursuitPath(new Point(-16, 28), new Point(-16, 25), new Point(73, 25), new Point(73, 28)),300));
        RedPurePursuitRight.addSegment(new PurePursuitSegment("drive to third stone",
                new PursuitPath(new Point(73, 28), new Point(73, 25), new Point(0, 25), new Point(0, 28)),0));
        RedPurePursuitRight.addSegment(new PurePursuitSegment("third trip to foundation",
                new PursuitPath(new Point(0, 28), new Point(0, 25), new Point(76, 25), new Point(76, 28)),300));
        RedPurePursuitRight.addSegment(new PurePursuitSegment("drive to fourth stone",
                new PursuitPath(new Point(76, 28), new Point(76, 25), new Point(-8, 25), new Point(-8, 28)),0));
        RedPurePursuitRight.addSegment(new PurePursuitSegment("fourth trip to foundation",
                new PursuitPath(new Point(-8, 28), new Point(-8, 25), new Point(76, 25), new Point(76, 28)),300));
    }
}
