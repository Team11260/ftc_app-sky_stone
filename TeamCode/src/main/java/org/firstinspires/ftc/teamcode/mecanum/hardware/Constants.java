package org.firstinspires.ftc.teamcode.mecanum.hardware;

import org.firstinspires.ftc.teamcode.framework.userhardware.paths.DriveSegment;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.Path;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.StrafeSegment;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.TurnSegment;

public final class Constants {

    ////////Drive////////
    public static int TRACK_WIDTH = 20;
    public static double STRAIGHT_COUNTS_PER_INCH = 189.0;


    public final static Path collectCenterSkyStone = new Path("collect center sky stone");

    static {
        collectCenterSkyStone.addSegment(new DriveSegment("drive to sky stone", 16, 0.5, 100));
        collectCenterSkyStone.addSegment(new StrafeSegment("strafe to block", 8,0.4,100));

    }

    public final static Path collectRightSkyStone = new Path("collect right sky stone");

    static {
        collectRightSkyStone.addSegment(new DriveSegment("drive to skystone", 32, 0.5, 100));
        //collectRightSkyStone.addSegment(new StrafeSegment("strafe to block", 5, 0.4, 100));
    }

    public final static Path collectLeftSkyStone = new Path("collect left sky stone");

    static {
        collectLeftSkyStone.addSegment(new DriveSegment("drive to skystone", 40, 0.5, 100));
        //collectLeftSkyStone.addSegment(new StrafeSegment("strafe to block", 5, 0.4, 100));
    }


    public final static Path collectBlock = new Path("collect block");

    static {
        collectBlock.addSegment((new DriveSegment("collect block", 16, 0.5, 100)));
    }

    public static final Path backUp = new Path("back up");

    static {
        backUp.addSegment(new DriveSegment("back up segment", -12, 0.5, 100));
    }

    public static final Path forwardDrive = new Path("forward drive");


    static {
        forwardDrive.addSegment(new DriveSegment("forward drive", 6, 0.5, 100));
    }

    public static final Path dragFoundation = new Path("last drive");

    static {
        dragFoundation.addSegment(new DriveSegment("drag foundation", -17, 0.3, 100));
    }

    public static final Path avoidRobot = new Path("avoid robot");
    static {

        avoidRobot.addSegment(new DriveSegment("avoid robot", 14, 0.5, 100));
    }

}
