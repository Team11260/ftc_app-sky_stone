package org.firstinspires.ftc.teamcode.mecanum.hardware;

import org.firstinspires.ftc.teamcode.framework.userhardware.paths.DriveSegment;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.Path;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.TurnSegment;

public class Constantscwo {

    public final static Path collectRightSkyStone = new Path("collect right sky stone");

    static {
        collectRightSkyStone.addSegment(new TurnSegment("turn to sky stone", 153, 0.5, 1, 300));
        collectRightSkyStone.addSegment(new DriveSegment("drive to sky stone", 28, 0.5, 20));
    }

    public final static Path collectLeftSkyStone = new Path("collect left sky stone");

    static {
        collectLeftSkyStone.addSegment(new TurnSegment("turn to sky stone", 153, 0.5, 1, 300));
        collectLeftSkyStone.addSegment(new DriveSegment("drive to sky stone", 28, 0.5, 20));
    }

    public final static Path collectCenterSkyStone = new Path("collect center sky stone");

    static {
        collectCenterSkyStone.addSegment(new TurnSegment("turn to sky stone", 153, 0.5, 1, 300));
        collectCenterSkyStone.addSegment(new DriveSegment("drive to sky stone", 28, 0.5, 20));
    }

    public final static Path reverseTurn = new Path("reverse turn");

    static {
        reverseTurn.addSegment(new TurnSegment("turn back from stones", 153, 0.5, 1, 300));
    }

    public final static Path driveLeftSkyStone = new Path("straight from left stone");

    static {
        driveLeftSkyStone.addSegment(new DriveSegment("drive to tray", 28, 0.5, 20));
        driveLeftSkyStone.addSegment(new TurnSegment("turn to tray", 153, 0.5, 1, 300));
        driveLeftSkyStone.addSegment(new DriveSegment("drive to tray", 28, 0.5, 20));
    }

    public final static Path driveRightSkyStone = new Path("straight from right stone");

    static {
        driveRightSkyStone.addSegment(new DriveSegment("drive to tray", 28, 0.5, 20));
        driveRightSkyStone.addSegment(new TurnSegment("turn to tray", 153, 0.5, 1, 300));
        driveRightSkyStone.addSegment(new DriveSegment("drive to tray", 28, 0.5, 20));
    }

    public final static Path driveCenterSkyStone = new Path("straight from center stone");

    static {
        driveCenterSkyStone.addSegment(new DriveSegment("drive to tray", 28, 0.5, 20));
        driveCenterSkyStone.addSegment(new TurnSegment("turn to tray", 153, 0.5, 1, 300));
        driveCenterSkyStone.addSegment(new DriveSegment("drive to tray", 28, 0.5, 20));
    }

    public final static Path pullTray = new Path("pull the tray");

    static {
        driveCenterSkyStone.addSegment(new DriveSegment("pull tray", -10, 0.5, 20));
    }

    public final static Path park = new Path("park the cart");

    static {
        park.addSegment(new DriveSegment("straight", 10, 0.5, 20));
        park.addSegment(new TurnSegment("turn to tray", 90, 0.5, 1, 300));
        park.addSegment(new DriveSegment("drive to tray", 28, 0.5, 20));
    }

}
