package org.firstinspires.ftc.teamcode.mecanum.hardware;

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

    public static double RED_BLOCK_LOCATION_Y = -26.5;
    public static double RED_FOUNDATION_Y = -27;
    public static double RED_FOUNDATION_Y_2ND = -30;
    public static double RED_FOUNDATION_NEAR_X = -72;  //  Play with this parameter
    public static double RED_FOUNDATION_MIDDLE_NEAR_X = -76;  //  Play with this parameter
    public static double RED_FOUNDATION_MIDDLE_X = -82;  //Measure this paramater
   // public static double RED_FOUNDATION_DRAGGER_X = -75;
    public static double RED_RUNWAY_Y = -18;  //  Play with this parameter
    public static double RED_DRAGGER_BACKUP_Y = -23;
    public static double RED_DRAGGER_FORWARD_HALFWAY_Y = -26;
    public static double RED_DRAGGER_FORWARD_FULL_Y = -29;
    public static double OFF_SET = -2;

    public static double RED_BLOCK1_X = 31;
    public static double RED_BLOCK2_X = 24;
    public static double RED_BLOCK3_X = 16;
    public static double RED_BLOCK4_X = 8;
    public static double RED_BLOCK5_X = 0;
    public static double RED_BLOCK6_X = -8;

    public static double BLUE_RUNWAY_Y = -18;
    public static double BLUE_FOUNDATION_Y = -27;
    public static double BLUE_FOUNDATION_NEAR_X = 72;
    public static double BLUE_BLOCK_LOCATION_Y = -26.5;

    public static double BLUE_BLOCK1_X = -31;
    public static double BLUE_BLOCK2_X = -24;
    public static double BLUE_BLOCK3_X = -16;
    public static double BLUE_BLOCK4_X = -8;
    public static double BLUE_BLOCK5_X = 0;
    public static double BLUE_BLOCK6_X = 8;


    /*  Paths for red side left sky stone*/

    public final static Path RedPurePursuitLeft = new Path("collect left sky stones");

    static {
        RedPurePursuitLeft.addSegment(new PurePursuitSegment("drive to first sky stone",
                new PursuitPath(new Point(0, 0), new Point(RED_BLOCK4_X, RED_BLOCK_LOCATION_Y)).setMinSpeed(0.17), 0));
        RedPurePursuitLeft.addSegment(new PurePursuitSegment("first trip to foundation",
                new PursuitPath(new Point(RED_BLOCK4_X, RED_BLOCK_LOCATION_Y), new Point(RED_BLOCK4_X, RED_RUNWAY_Y), new Point(RED_FOUNDATION_NEAR_X, RED_RUNWAY_Y), new Point(RED_FOUNDATION_NEAR_X, RED_FOUNDATION_Y)), 300));
        RedPurePursuitLeft.addSegment(new PurePursuitSegment("drive to second sky stone",
                new PursuitPath(new Point(RED_FOUNDATION_NEAR_X, RED_FOUNDATION_Y), new Point(RED_FOUNDATION_NEAR_X, RED_RUNWAY_Y), new Point(RED_BLOCK1_X, RED_RUNWAY_Y + OFF_SET), new Point(RED_BLOCK1_X, RED_BLOCK_LOCATION_Y)), 0));
        RedPurePursuitLeft.addSegment(new PurePursuitSegment("second trip to foundation",
                new PursuitPath(new Point(RED_BLOCK1_X, RED_BLOCK_LOCATION_Y), new Point(RED_BLOCK1_X, RED_RUNWAY_Y), new Point(RED_FOUNDATION_MIDDLE_X, RED_RUNWAY_Y), new Point(RED_FOUNDATION_MIDDLE_X, RED_FOUNDATION_Y_2ND)), 300));
        RedPurePursuitLeft.addSegment(new PurePursuitSegment("move foundation",new PursuitPath(new Point(RED_FOUNDATION_MIDDLE_X,RED_BLOCK_LOCATION_Y))));
//        RedPurePursuitLeft.addSegment(new PurePursuitSegment("drive to third stone",
//                new PursuitPath(new Point(RED_FOUNDATION_MIDDLE_X, RED_FOUNDATION_Y), new Point(RED_FOUNDATION_MIDDLE_X, RED_RUNWAY_Y), new Point(RED_BLOCK6_X, RED_RUNWAY_Y), new Point(RED_BLOCK6_X, RED_BLOCK_LOCATION_Y)), 0));
//        RedPurePursuitLeft.addSegment(new PurePursuitSegment("third trip to foundation",
//                new PursuitPath(new Point(RED_BLOCK6_X, RED_BLOCK_LOCATION_Y), new Point(RED_BLOCK5_X, RED_RUNWAY_Y), new Point(RED_FOUNDATION_NEAR_X, RED_RUNWAY_Y), new Point(RED_FOUNDATION_NEAR_X, RED_FOUNDATION_Y)), 300));
    }

    public final static Path RedPurePursuitCenter = new Path("collect center sky stones");

    static {
        RedPurePursuitCenter.addSegment(new PurePursuitSegment("drive to first sky stone",
                new PursuitPath(new Point(0, 0), new Point(RED_BLOCK5_X, RED_BLOCK_LOCATION_Y)).setMinSpeed(0.17), 0));
        RedPurePursuitCenter.addSegment(new PurePursuitSegment("first trip to foundation",
                new PursuitPath(new Point(RED_BLOCK5_X, RED_BLOCK_LOCATION_Y), new Point(RED_BLOCK5_X, RED_RUNWAY_Y), new Point(RED_FOUNDATION_MIDDLE_X, RED_RUNWAY_Y), new Point(RED_FOUNDATION_MIDDLE_X, RED_FOUNDATION_Y)), 300));
        RedPurePursuitCenter.addSegment(new PurePursuitSegment("drive to second sky stone",
                new PursuitPath(new Point(RED_FOUNDATION_NEAR_X, RED_FOUNDATION_Y), new Point(RED_FOUNDATION_NEAR_X, RED_RUNWAY_Y), new Point(RED_BLOCK2_X, RED_RUNWAY_Y), new Point(RED_BLOCK2_X, RED_BLOCK_LOCATION_Y + OFF_SET)), 0));
        RedPurePursuitCenter.addSegment(new PurePursuitSegment("second trip to foundation",
                new PursuitPath(new Point(RED_BLOCK2_X, RED_BLOCK_LOCATION_Y + OFF_SET), new Point(RED_BLOCK2_X, RED_RUNWAY_Y), new Point(RED_FOUNDATION_NEAR_X, RED_RUNWAY_Y), new Point(RED_FOUNDATION_NEAR_X, RED_FOUNDATION_Y)),300));
        RedPurePursuitCenter.addSegment(new PurePursuitSegment("dragger backup",
                new PursuitPath(new Point(RED_FOUNDATION_NEAR_X, RED_FOUNDATION_Y_2ND),new Point (RED_FOUNDATION_NEAR_X,RED_DRAGGER_BACKUP_Y))));
        //RedPurePursuitCenter.addSegment(new PurePursuitSegment("dragger forward half",
        //        new PursuitPath(new Point(RED_FOUNDATION_NEAR_X,RED_DRAGGER_BACKUP_Y), new Point(RED_FOUNDATION_NEAR_X,RED_DRAGGER_FORWARD_HALFWAY_Y))));
        RedPurePursuitCenter.addSegment(new PurePursuitSegment("dragger forward full",
                new PursuitPath(new Point(RED_FOUNDATION_NEAR_X,RED_DRAGGER_BACKUP_Y),new Point(RED_FOUNDATION_NEAR_X,RED_DRAGGER_FORWARD_FULL_Y))));
        RedPurePursuitCenter.addSegment(new PurePursuitSegment("back up with foundation",
                new PursuitPath(new Point(RED_FOUNDATION_NEAR_X,RED_DRAGGER_FORWARD_FULL_Y), new Point(RED_FOUNDATION_NEAR_X + 6,RED_DRAGGER_FORWARD_FULL_Y + 18)).setMinSpeed(0.4),0,-20));
        RedPurePursuitCenter.addSegment(new PurePursuitSegment("back up with foundation and turn",
                new PursuitPath(new Point(RED_FOUNDATION_NEAR_X,RED_DRAGGER_FORWARD_FULL_Y), new Point(RED_FOUNDATION_NEAR_X + 8,RED_DRAGGER_FORWARD_FULL_Y + 16)).setMinSpeed(0.4),0,-90));

        //        RedPurePursuitCenter.addSegment(new PurePursuitSegment("dragger safety drive",
//                new PursuitPath(new Point(RED_FOUNDATION_NEAR_X, RED_FOUNDATION_Y_2ND), new Point(RED_FOUNDATION_DRAGGER_X,RED_FOUNDATION_Y_2ND))));
//        RedPurePursuitCenter.addSegment(new PurePursuitSegment("drive to third stone",
//                new PursuitPath(new Point(RED_FOUNDATION_MIDDLE_X, RED_FOUNDATION_Y), new Point(RED_FOUNDATION_MIDDLE_X, RED_RUNWAY_Y), new Point(RED_BLOCK5_X, RED_RUNWAY_Y), new Point(RED_BLOCK6_X, RED_BLOCK_LOCATION_Y)),0));
//        RedPurePursuitCenter.addSegment(new PurePursuitSegment("third trip to foundation",
//                new PursuitPath(new Point(RED_BLOCK6_X, RED_BLOCK_LOCATION_Y), new Point(RED_BLOCK6_X, RED_RUNWAY_Y), new Point(RED_FOUNDATION_NEAR_X, RED_RUNWAY_Y), new Point(RED_FOUNDATION_NEAR_X, RED_FOUNDATION_Y)),300));
    }

    public final static Path RunwayDrive = new Path("Drive along runway");

    static {
        RunwayDrive.addSegment(new PurePursuitSegment("Run along runway", new PursuitPath(new Point(0, 0), new Point(-50, 0))));
        RunwayDrive.addSegment(new PurePursuitSegment("Run along runway 2", new PursuitPath(new Point(-50, 0), new Point(-100, 0)), 1000));


    }

    public final static Path RedPurePursuitRight = new Path("collect right sky stones");

    static {
        RedPurePursuitRight.addSegment(new PurePursuitSegment("drive to first sky stone",
                new PursuitPath(new Point(0, 0), new Point(RED_BLOCK6_X, RED_BLOCK_LOCATION_Y)), 0));
        RedPurePursuitRight.addSegment(new PurePursuitSegment("first trip to foundation",
                new PursuitPath(new Point(RED_BLOCK6_X, RED_BLOCK_LOCATION_Y), new Point(RED_BLOCK6_X, RED_RUNWAY_Y), new Point(RED_FOUNDATION_NEAR_X, RED_RUNWAY_Y), new Point(RED_FOUNDATION_NEAR_X, RED_FOUNDATION_Y)), 300));
        RedPurePursuitRight.addSegment(new PurePursuitSegment("drive to second sky stone",
                new PursuitPath(new Point(RED_FOUNDATION_NEAR_X, RED_FOUNDATION_Y), new Point(RED_FOUNDATION_NEAR_X, RED_RUNWAY_Y), new Point(RED_BLOCK3_X, RED_RUNWAY_Y), new Point(RED_BLOCK3_X, RED_BLOCK_LOCATION_Y + OFF_SET)), 0));
        RedPurePursuitRight.addSegment(new PurePursuitSegment("second trip to foundation",
                new PursuitPath(new Point(RED_BLOCK3_X, RED_BLOCK_LOCATION_Y), new Point(RED_BLOCK3_X, RED_RUNWAY_Y), new Point(RED_FOUNDATION_MIDDLE_X, RED_RUNWAY_Y), new Point(RED_FOUNDATION_MIDDLE_X, RED_FOUNDATION_Y_2ND)), 300));
//        RedPurePursuitRight.addSegment(new PurePursuitSegment("drive to third stone",
//                new PursuitPath(new Point(RED_FOUNDATION_MIDDLE_X, RED_FOUNDATION_Y), new Point(RED_FOUNDATION_MIDDLE_X, RED_RUNWAY_Y), new Point(RED_BLOCK5_X, RED_RUNWAY_Y), new Point(RED_BLOCK5_X, RED_BLOCK_LOCATION_Y)),0));
//        RedPurePursuitRight.addSegment(new PurePursuitSegment("third trip to foundation",
//                new PursuitPath(new Point(RED_BLOCK5_X, RED_BLOCK_LOCATION_Y), new Point(RED_BLOCK5_X, RED_RUNWAY_Y), new Point(RED_FOUNDATION_NEAR_X, RED_RUNWAY_Y), new Point(RED_FOUNDATION_NEAR_X, RED_FOUNDATION_Y)),300));
    }

    public final static Path RedPurePursuitParkFoundation= new Path("drag and park the foundation");

    static{
        RedPurePursuitParkFoundation.addSegment(new PurePursuitSegment("turn and drag",
                new PursuitPath(new Point(0, 0), new Point(-9, -17)),0,-27));
        RedPurePursuitParkFoundation.addSegment(new PurePursuitSegment("turn -90 degrees and push ",
                new PursuitPath(new Point(-9, -17), new Point(4, -17)),0,-90));
        RedPurePursuitParkFoundation.addSegment(new PurePursuitSegment("park the robot",
                new PursuitPath(new Point(4, -17), new Point(-40, -11)),0,-90));

    }

    public final static Path BluePurePursuitLeft = new Path("collect left sky stones on blue side");

    static {
        BluePurePursuitLeft.addSegment(new PurePursuitSegment("drive to first sky stone on blue side",
                new PursuitPath(new Point(0, 0), new Point(BLUE_BLOCK6_X, BLUE_BLOCK_LOCATION_Y)).setMinSpeed(0.17), 0));
        BluePurePursuitLeft.addSegment(new PurePursuitSegment("first trip to foundation",
                new PursuitPath(new Point(BLUE_BLOCK6_X, BLUE_BLOCK_LOCATION_Y),
                        new Point(BLUE_BLOCK6_X, BLUE_RUNWAY_Y),
                        new Point(BLUE_FOUNDATION_NEAR_X, BLUE_RUNWAY_Y),
                        new Point(BLUE_FOUNDATION_NEAR_X, BLUE_FOUNDATION_Y)), 300));
//        RedPurePursuitLeft.addSegment(new PurePursuitSegment("drive to second sky stone",
//                new PursuitPath(new Point(RED_FOUNDATION_NEAR_X, RED_FOUNDATION_Y), new Point(RED_FOUNDATION_NEAR_X, RED_RUNWAY_Y), new Point(RED_BLOCK1_X, RED_RUNWAY_Y + OFF_SET), new Point(RED_BLOCK1_X, RED_BLOCK_LOCATION_Y)), 0));
//        RedPurePursuitLeft.addSegment(new PurePursuitSegment("second trip to foundation",
//                new PursuitPath(new Point(RED_BLOCK1_X, RED_BLOCK_LOCATION_Y), new Point(RED_BLOCK1_X, RED_RUNWAY_Y), new Point(RED_FOUNDATION_MIDDLE_X, RED_RUNWAY_Y), new Point(RED_FOUNDATION_MIDDLE_X, RED_FOUNDATION_Y_2ND)), 300));
//        RedPurePursuitLeft.addSegment(new PurePursuitSegment("move foundation",new PursuitPath(new Point(RED_FOUNDATION_MIDDLE_X,RED_BLOCK_LOCATION_Y))));

    }


    public final static Path BluePurePursuitCenter = new Path("collect center sky stones on blue side");

    static {
        BluePurePursuitCenter.addSegment(new PurePursuitSegment("drive to first sky stone on blue side",
                new PursuitPath(new Point(0, 0), new Point(BLUE_BLOCK5_X, BLUE_BLOCK_LOCATION_Y)).setMinSpeed(0.17), 0));
    }

    public final static Path BluePurePursuitRight = new Path("collect right sky stones on blue side");

    static {
        BluePurePursuitRight.addSegment(new PurePursuitSegment("drive to first sky stone on blue side",
                new PursuitPath(new Point(0, 0), new Point(BLUE_BLOCK4_X, BLUE_BLOCK_LOCATION_Y)), 0));
    }
}
