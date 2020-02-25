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





    public final static Path RedDistancePath = new Path("pick up first stone");

    static {
        RedDistancePath.addSegment(new PurePursuitSegment("drive to first sky stone",
                new PursuitPath(new Point(0, 0),
                        new Point(RED_BLOCK5_X + 2, RED_BLOCK_LOCATION_Y + 4)).setMaxAcceleration(0.01), 0, 0,
                10000, true));
    }


}


