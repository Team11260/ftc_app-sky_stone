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

    public static double RED_BLOCK_LOCATION_Y = 26;
    public static double RED_FOUNDATION_Y = 27.5;
    public static double RED_FOUNDATION_NEAR_X = 72;  //  Play with this parameter
    public static double RED_FOUNDATION_MIDDLE_NEAR_X = 76;  //  Play with this parameter
    public static double RED_FOUNDATION_MIDDLE_X = 84;  //Measure this paramater
    public static double RED_RUNWAY_Y = 24;  //  Play with this parameter

    public static double RED_BLOCK1_X = -31, RED_BLOCK1_Y = RED_BLOCK_LOCATION_Y;
    public static double RED_BLOCK2_X = -24, RED_BLOCK2_Y = RED_BLOCK_LOCATION_Y;
    public static double RED_BLOCK3_X = -16, RED_BLOCK3_Y = RED_BLOCK_LOCATION_Y;
    public static double RED_BLOCK4_X = 8, RED_BLOCK4_Y = RED_BLOCK_LOCATION_Y;
    public static double RED_BLOCK5_X = 0, RED_BLOCK5_Y = RED_BLOCK_LOCATION_Y;
    public static double RED_BLOCK6_X = 8, RED_BLOCK6_Y = RED_BLOCK_LOCATION_Y;

/*  Paths for red side left sky stone*/

    public final static Path RedPurePursuitLeft = new Path("collect left sky stones");

    static{
        RedPurePursuitLeft.addSegment(new PurePursuitSegment("drive to first sky stone",
                new PursuitPath(new Point(0, 0), new Point(RED_BLOCK4_X, RED_BLOCK4_Y)).setMinSpeed(0.17),0));



    }

   public final static Path RedPurePursuitCenter = new Path("collect center sky stones");

    static {
        RedPurePursuitCenter.addSegment(new PurePursuitSegment("drive to first sky stone",
                new PursuitPath(new Point(0, 0), new Point(RED_BLOCK5_X, RED_BLOCK5_Y)).setMinSpeed(0.18), 0));
    }
/*

        RedPurePursuitCenter.addSegment(new PurePursuitSegment("first trip to foundation",
                //new PursuitPath(new Point(RED_BLOCK5_X, RED_BLOCK5_Y), new Point(36, RED_RUNWAY_Y), new Point(RED_FOUNDATION_NEAR_X, RED_FOUNDATION_Y)),500));
                new PursuitPath(new Point(RED_BLOCK5_X, RED_BLOCK5_Y), new Point(RED_BLOCK5_X, RED_RUNWAY_Y), new Point(RED_FOUNDATION_NEAR_X, RED_RUNWAY_Y), new Point(RED_FOUNDATION_NEAR_X, RED_FOUNDATION_Y)),300));
        RedPurePursuitCenter.addSegment(new PurePursuitSegment("drive to second sky stone",
                new PursuitPath(new Point(RED_FOUNDATION_NEAR_X, RED_FOUNDATION_Y), new Point(RED_FOUNDATION_NEAR_X, RED_RUNWAY_Y), new Point(RED_BLOCK2_X, RED_RUNWAY_Y), new Point(RED_BLOCK2_X, RED_BLOCK2_Y)),0));
        RedPurePursuitCenter.addSegment(new PurePursuitSegment("second trip to foundation",
                new PursuitPath(new Point(RED_BLOCK2_X, RED_BLOCK2_Y), new Point(RED_BLOCK2_X,RED_RUNWAY_Y), new Point(RED_FOUNDATION_MIDDLE_X, RED_RUNWAY_Y), new Point(RED_FOUNDATION_MIDDLE_X,RED_FOUNDATION_Y)),300));
        RedPurePursuitCenter.addSegment(new PurePursuitSegment("drive to third stone",
                new PursuitPath(new Point(RED_FOUNDATION_MIDDLE_X, RED_FOUNDATION_Y), new Point(RED_FOUNDATION_MIDDLE_X, RED_RUNWAY_Y), new Point(RED_BLOCK6_X, RED_RUNWAY_Y), new Point(RED_BLOCK6_X, RED_BLOCK6_Y)),0));
        RedPurePursuitCenter.addSegment(new PurePursuitSegment("third trip to foundation",
                new PursuitPath(new Point(RED_BLOCK6_X, RED_BLOCK6_Y), new Point(RED_BLOCK6_X, RED_RUNWAY_Y), new Point(RED_FOUNDATION_NEAR_X, RED_RUNWAY_Y), new Point(RED_FOUNDATION_NEAR_X, RED_FOUNDATION_Y)),300));

    }

    public final static Path RedPurePursuitRight = new Path("collect right sky stones");

    static{
        RedPurePursuitRight.addSegment(new PurePursuitSegment("drive to first sky stone",
                new PursuitPath(new Point(0, 0), new Point(RED_BLOCK6_X, RED_BLOCK6_Y)).setMinSpeed(0.17),0));
        RedPurePursuitRight.addSegment(new PurePursuitSegment("first trip to foundation",
                new PursuitPath(new Point(RED_BLOCK6_X, RED_BLOCK6_Y), new Point(RED_BLOCK6_X, RED_RUNWAY_Y), new Point(RED_FOUNDATION_NEAR_X, RED_RUNWAY_Y), new Point(RED_FOUNDATION_NEAR_X, RED_FOUNDATION_Y)),300));
        RedPurePursuitRight.addSegment(new PurePursuitSegment("drive to second sky stone",
                new PursuitPath(new Point(RED_FOUNDATION_NEAR_X, RED_FOUNDATION_Y), new Point(RED_FOUNDATION_NEAR_X, RED_RUNWAY_Y), new Point(RED_BLOCK3_X, RED_RUNWAY_Y), new Point(RED_BLOCK3_X, RED_BLOCK3_Y)),0));
        RedPurePursuitRight.addSegment(new PurePursuitSegment("second trip to foundation",
                new PursuitPath(new Point(RED_BLOCK3_X, RED_BLOCK3_Y), new Point(RED_BLOCK3_X, RED_RUNWAY_Y), new Point(RED_FOUNDATION_MIDDLE_X, RED_RUNWAY_Y), new Point(RED_FOUNDATION_MIDDLE_X, RED_FOUNDATION_Y)),300));
        RedPurePursuitRight.addSegment(new PurePursuitSegment("drive to third stone",
                new PursuitPath(new Point(RED_FOUNDATION_MIDDLE_X, RED_FOUNDATION_Y), new Point(RED_FOUNDATION_MIDDLE_X, RED_RUNWAY_Y), new Point(RED_BLOCK5_X, RED_RUNWAY_Y), new Point(RED_BLOCK5_X, RED_BLOCK5_Y)),0));
        RedPurePursuitRight.addSegment(new PurePursuitSegment("third trip to foundation",
                new PursuitPath(new Point(RED_BLOCK5_X, RED_BLOCK5_Y), new Point(RED_BLOCK5_X, RED_RUNWAY_Y), new Point(RED_FOUNDATION_NEAR_X, RED_RUNWAY_Y), new Point(RED_FOUNDATION_NEAR_X, RED_FOUNDATION_Y)),300));

    }

    public final static Path RedPurePursuitParkFoundation= new Path("drag and park the foundation");

    static{
        RedPurePursuitParkFoundation.addSegment(new PurePursuitSegment("turn and drag",
                new PursuitPath(new Point(0, 0), new Point(-9, -17)),0,-27));
        RedPurePursuitParkFoundation.addSegment(new PurePursuitSegment("turn -90 degrees and push ",
                new PursuitPath(new Point(-9, -17), new Point(4, -17)),0,-90));
        RedPurePursuitParkFoundation.addSegment(new PurePursuitSegment("park the robot",
                new PursuitPath(new Point(4, -17), new Point(-40, -11)),0,-90));

    } */

}
