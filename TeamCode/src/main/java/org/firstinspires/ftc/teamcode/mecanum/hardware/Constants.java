package org.firstinspires.ftc.teamcode.mecanum.hardware;

import org.firstinspires.ftc.teamcode.framework.userhardware.paths.DriveSegment;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.Path;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.TurnSegment;

public final class Constants {

    ////////Drive////////
    public static int TRACK_WIDTH = 20;


    public final static Path collectCenterSkyStone = new Path("collect center sky stone");

    static {
        collectCenterSkyStone.addSegment(new DriveSegment("drive to sky stone", 16, 0.5, 100));

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
        forwardDrive.addSegment(new DriveSegment("forward drive", 3, 0.5, 100));
    }

    public static final Path lastDrive = new Path("last drive");

    static {
        lastDrive.addSegment(new DriveSegment("last drive", -7, 1.0, 100));
    }


}
