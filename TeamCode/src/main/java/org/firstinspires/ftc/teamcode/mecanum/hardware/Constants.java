package org.firstinspires.ftc.teamcode.mecanum.hardware;

import org.firstinspires.ftc.teamcode.framework.userhardware.paths.DriveSegment;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.Path;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.StrafeSegment;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.TurnSegment;

public final class Constants {

    ////////Drive////////
    public static int TRACK_WIDTH = 20;


    public final static Path collectCenterSkyStone = new Path("collect center sky stone");

    static {
        collectCenterSkyStone.addSegment(new DriveSegment("drive to sky stone", 16, 0.5, 100));
        collectCenterSkyStone.addSegment(new StrafeSegment("strafe to block", 5,0.4,100));

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
