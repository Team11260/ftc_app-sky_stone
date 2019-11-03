package org.firstinspires.ftc.teamcode.mecanum.hardware;

import org.firstinspires.ftc.teamcode.framework.userhardware.paths.DriveSegment;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.Path;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.TurnSegment;

public class Constantscwo {

    public static int GRIPPING_DELAY = 500;

    public final static Path approachTheStones = new Path("approach the stones");

    static{
        approachTheStones.addSegment(new DriveSegment("first approach", 12, 0.5, 30));
    }

    public final static Path collectRightSkyStone = new Path("collect right sky stone");

    static {
        collectRightSkyStone.addSegment(new DriveSegment("drive to sky stone", 7, 0.5, 20));
        collectRightSkyStone.addSegment(new TurnSegment("turn back from stones", 90, 0.5, 1, 300));
        collectRightSkyStone.addSegment(new DriveSegment("drive towards tray", 96, 0.5, 20));
    }

    public final static Path collectLeftSkyStone = new Path("collect left sky stone");

    static {
        collectLeftSkyStone.addSegment(new DriveSegment("drive to sky stone", 7, 0.5, 20));
        collectLeftSkyStone.addSegment(new TurnSegment("turn back from stones", 90, 0.5, 1, 300));
        collectLeftSkyStone.addSegment(new DriveSegment("drive towards tray", 0, 0.5, 1, 300));
    }

    public final static Path collectCenterSkyStone = new Path("collect center sky stone");

    static {
       // collectCenterSkyStone.addSegment(new TurnSegment("turn to sky stone", 153, 0.5, 1, 300));
        collectCenterSkyStone.addSegment(new DriveSegment("drive to sky stone", 7, 0.5, 20));
        collectCenterSkyStone.addSegment(new TurnSegment("turn back from stones", 90, 0.5, 1, 300));
        collectCenterSkyStone.addSegment(new DriveSegment("drive towards tray", 104, 0.5, 20));
    }

    public final static Path dumpAndDrag = new Path("dump the stone and drag the tray");

    static{
        dumpAndDrag.addSegment(new TurnSegment("turn to tray", 0, 0.5, 1, 300));
        dumpAndDrag.addSegment(new DriveSegment("drive to tray", 10, 0.5, 20));
        dumpAndDrag.addSegment(new DriveSegment("pull tray to wall", -30, 0.5, 20));
    }

    public final static Path park = new Path("park the cart");

    static {
        park.addSegment(new DriveSegment("first park leg", 18, 0.5, 20));
        park.addSegment(new TurnSegment("turn to park site", 90, 0.5, 1, 300));
        park.addSegment(new DriveSegment("drive to park site", 24, 0.5, 20));
    }
}
