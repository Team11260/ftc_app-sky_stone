package org.firstinspires.ftc.teamcode.mecanum.hardware;

import org.firstinspires.ftc.teamcode.framework.userhardware.paths.AngleDriveSegment;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.DriveSegment;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.Path;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.PurePursuitSegment;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.StrafeSegment;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.TurnSegment;
import org.firstinspires.ftc.teamcode.framework.userhardware.purepursuit.Point;
import org.firstinspires.ftc.teamcode.framework.userhardware.purepursuit.PursuitPath;

public final class Constants {

    ////////Drive////////
    public static int TRACK_WIDTH = 20;
    // static double STRAIGHT_COUNTS_PER_INCH = 189;
    public static double STRAIGHT_COUNTS_PER_INCH = 189.0;

    ////////Arm////////
    //For Old
    public static double ARM_DOWN_POSITION = 0.63;
    public static double ARM_PIN_POSITION = 1.0;
    public static double ARM_UP_POSITION = 1.0;
    public static double ARM_BACK_POSITION = 0.3;
    public static double GRIPPER_GRIP_POSITION = 0.27;
    public static double GRIPPER_RELEASE_POSITION = 0.85;


    ////////Vision////////
    //For Old
    public static int BLOCKHEIGHT = 66;
    public static int XORIGIN = 200;
    public static int YORIGIN = 72;
    public static int BLOCKWIDTH = 123;

    public static int LINE_WIDTH = 7;
    public static int HEIGHT = 45;
    public static int THRESHOLD = 120;

    ////////Lift////////
    public static int LIFT_UP = 1234;
    public static int LIFT_DOWN = 0;
    public static double TILT_DOWN = 0;
    public static double TILT_UP = 0.5;
    public static double GRABBER_OPEN = 0.5;
    public static double GRABBER_CLOSE = 0;
    public static double SLIDE_OUT = 0.5;
    public static double PAN_WIDE = 0;
    public static double PAN_SHORT = 0.5;



    public final static Path collectCenterSkyStone = new Path("collect center sky stone");

    static {
        collectCenterSkyStone.addSegment((new StrafeSegment("strafe to sky stone", -29, 0.3, 1)));
        collectCenterSkyStone.addSegment((new StrafeSegment("back up from block",6,0.25,1,true)));
        collectCenterSkyStone.addSegment((new DriveSegment("drive to foundation",76,0.5,1)));
        collectCenterSkyStone.addSegment((new StrafeSegment("strafe to foundation",-10,0.3,1,800,false)));
        //collectCenterSkyStone.addSegment(new StrafeSegment("strafe from foundation", 2,0.25,1,false));
        collectCenterSkyStone.addSegment((new TurnSegment("turn 90", -80.0, 0.25, 2.0,50000)));
        collectCenterSkyStone.addSegment((new StrafeSegment("park the foundation", -10,0.25, 1, 5000, false)));
        collectCenterSkyStone.addSegment((new StrafeSegment("park the robot", 43, 0.4, 1, 5000, false )));
    }
    public final static Path collectLeftSkyStone = new Path("collect  left sky stone");

    static {
        collectLeftSkyStone.addSegment(new AngleDriveSegment("angle to block", -7,0.4,0.5));
        collectLeftSkyStone.addSegment((new StrafeSegment("strafe to sky stone", -22, 0.3, 1)));
        collectLeftSkyStone.addSegment((new StrafeSegment("back up from block",6,0.25,1,true)));
        collectLeftSkyStone.addSegment((new DriveSegment("drive to foundation",84,0.5,1)));
        collectLeftSkyStone.addSegment((new StrafeSegment("strafe to foundation",-10,0.3,1,800,false)));
        //collectCenterSkyStone.addSegment(new StrafeSegment("strafe from foundation", 2,0.25,1,false));
        collectLeftSkyStone.addSegment((new TurnSegment("turn 90", -80.0, 0.25, 2.0,50000)));
        collectLeftSkyStone.addSegment((new StrafeSegment("park the foundation", -10,0.25, 1, 5000, false)));
        collectLeftSkyStone.addSegment((new StrafeSegment("park the robot", 43, 0.4, 1, 5000, false )));
        //collectLeftSkyStone.addSegment(new StrafeSegment("strafe to block", 4,0.5,100));
        //collectLeftSkyStone.addSegment((new DriveSegment("collect block", 9, 0.3, 100)));
        //collectLeftSkyStone.addSegment((new DriveSegment("drive straight a distance", -86, 0.25, 100)));
        //collectLeftSkyStone.addSegment((new DriveSegment("back up", 8, 0.25, 100)));
        //collectLeftSkyStone.addSegment(new StrafeSegment("strafe from foundation", 2.5,0.5,1));
        //collectLeftSkyStone.addSegment((new DriveSegment("drive straight a distance", 14, 0.25, 1)));
    }

    public final static Path collectRightSkyStone = new Path("collect right sky stone");

    static {
        collectRightSkyStone.addSegment(new AngleDriveSegment("angle to block", 6,0.4,0.5));
        collectRightSkyStone.addSegment((new StrafeSegment("strafe to sky stone", -22, 0.3, 1)));
        collectRightSkyStone.addSegment((new StrafeSegment("back up from block",6,0.25,1,true)));
        collectRightSkyStone.addSegment((new DriveSegment("drive to foundation",68,0.5,1)));
        collectRightSkyStone.addSegment((new StrafeSegment("strafe to foundation",-10,0.3,1,800,false)));
        //collectCenterSkyStone.addSegment(new StrafeSegment("strafe from foundation", 2,0.25,1,false));
        collectRightSkyStone.addSegment((new TurnSegment("turn 90", -80.0, 0.25, 2.0,50000)));
        collectRightSkyStone.addSegment((new StrafeSegment("park the foundation", -10,0.25, 1, 5000, false)));
        collectRightSkyStone.addSegment((new StrafeSegment("park the robot", 43, 0.4, 1, 5000, false )));
        //collectRightSkyStone.addSegment(new StrafeSegment("strafe to block", 3,0.5,100));
        //collectRightSkyStone.addSegment(new StrafeSegment("strafe to block", -15,0.3,1.0));
        //collectRightSkyStone.addSegment(new AngleDriveSegment("angle to block", 8,0.4,0.5));
        //collectRightSkyStone.addSegment(new AngleDriveSegment("angle to block", -8,0.4,0.5));
        //collectRightSkyStone.addSegment((new DriveSegment("collect block", 20, 0.25, 0)));
        //collectRightSkyStone.addSegment((new DriveSegment("back up", 40, 0.25, 0)));
        //collectRightSkyStone.addSegment(new StrafeSegment("strafe to foundation", 75,0.5,0,true));
        //collectRightSkyStone.addSegment(new DriveSegment("drive to foundation", 40, 0.1, 0,3000));
        //collectRightSkyStone.addSegment(new StrafeSegment("strafe from foundation", 8,0.6,1.0,false));
        //collectRightSkyStone.addSegment((new TurnSegment("turn 90", -80.0, 0.6, 2.0,50000)));
        //collectRightSkyStone.addSegment((new DriveSegment("back up", -8, 0.25, 100)));
        //collectRightSkyStone.addSegment(new StrafeSegment("strafe from foundation", 2.5,0.5,1));
       // collectRightSkyStone.addSegment((new DriveSegment("drive straight a distance", 50, 0.4, 2.0)));
       // collectRightSkyStone.addSegment((new DriveSegment("drive straight a distance", -50, 0.4,2.0)));
        //collectRightSkyStone.addSegment((new DriveSegment("drive straight a distance", -95, 0.25, 1)));

       // collectRightSkyStone.addSegment((new DriveSegment("drive straight a distance", -64, 0.25, 100)));
                //collectRightSkyStone.addSegment(new AngleDriveSegment("angle to block", -4,0.4,0));
        //collectRightSkyStone.addSegment((new DriveSegment("drive straight a distance", -86, 0.25, 100)));
    }

    public final static PursuitPath goToFirstBlockCenter = new PursuitPath(new Point(0, 0), new Point(0, 28));
    public final static PursuitPath goToFoundationCenter = new PursuitPath(new Point(0, 28), new Point(0, 26), new Point(72, 26), new Point(72, 28));
    public final static PursuitPath goToSecondBlockCenter = new PursuitPath(new Point(72, 28), new Point(72, 26), new Point(-24, 26), new Point(-24, 28));
    public final static PursuitPath goBackToFoundationCenter = new PursuitPath(new Point(-24, 28), new Point(-24, 26), new Point(72, 26), new Point(72, 28));

    public final static Path testPurePursuitCenter = new Path("collect center sky stones");

    static{
        testPurePursuitCenter.addSegment(new PurePursuitSegment("drive to center sky stone", goToFirstBlockCenter,0));
        testPurePursuitCenter.addSegment(new PurePursuitSegment("first trip to foundation", goToFoundationCenter,300));
        //testPurePursuitCenter.addSegment(new PurePursuitSegment("drive to second center sky stone", goToSecondBlockCenter,0));
        //testPurePursuitCenter.addSegment(new PurePursuitSegment("second trip to foundation", goBackToFoundationCenter,300));
    }


    public final static PursuitPath goToFirstBlockLeft = new PursuitPath(new Point(0, 0), new Point(-8, 28));
    public final static PursuitPath goToFoundationLeft = new PursuitPath(new Point(-8, 28), new Point(-8, 26), new Point(72, 26), new Point(72, 28));
    public final static PursuitPath goToSecondBlockLeft = new PursuitPath(new Point(72, 28), new Point(72, 26), new Point(-32, 26), new Point(-32, 28));
    public final static PursuitPath goBackToFoundationLeft = new PursuitPath(new Point(-32, 28), new Point(-32, 26), new Point(72, 26), new Point(72, 28));

    public final static Path testPurePursuitLeft = new Path("collect left sky stones");

    static{
        testPurePursuitLeft.addSegment(new PurePursuitSegment("drive to left sky stone", goToFirstBlockLeft,0));
        testPurePursuitLeft.addSegment(new PurePursuitSegment("first trip to foundation", goToFoundationLeft,300));
        //testPurePursuitLeft.addSegment(new PurePursuitSegment("drive to second center sky stone", goToSecondBlockLeft,0));
        //testPurePursuitLeft.addSegment(new PurePursuitSegment("second trip to foundation", goBackToFoundationLeft,300));
    }

    public final static PursuitPath goToFirstBlockRight = new PursuitPath(new Point(0, 0), new Point(8, 28));
    public final static PursuitPath goToFoundationRight= new PursuitPath(new Point(8, 28), new Point(8, 26), new Point(72, 26), new Point(72, 28));
    public final static PursuitPath goToSecondBlockRight = new PursuitPath(new Point(72, 28), new Point(72, 26), new Point(-16, 26), new Point(-16, 28));
    public final static PursuitPath goBackToFoundationRight = new PursuitPath(new Point(-16, 28), new Point(-16, 26), new Point(72, 26), new Point(72, 28));

    public final static Path testPurePursuitRight = new Path("collect right sky stones");

    static{
        testPurePursuitRight.addSegment(new PurePursuitSegment("drive to right sky stone", goToFirstBlockRight,0));
        testPurePursuitRight.addSegment(new PurePursuitSegment("first trip to foundation", goToFoundationRight,300));
        //testPurePursuitRight.addSegment(new PurePursuitSegment("drive to second right sky stone", goToSecondBlockRight,0));
        //testPurePursuitRight.addSegment(new PurePursuitSegment("second trip to foundation", goBackToFoundationRight,300));
    }




    public final static Path collectBlock = new Path("collect block");

    static {
        collectBlock.addSegment((new DriveSegment("collect block", 16, 0.5, 100)));
    }

    public static final Path backUp = new Path("back up");

    static {
        backUp.addSegment(new DriveSegment("back up segment", -12, 0.5, 100));
    }
    public static final Path strafeToTray = new Path("strafe to tray");

    static{
        strafeToTray.addSegment(new StrafeSegment("strafe to tray", 60, 0.5, 100));
    }

    public static final Path forwardDrive = new Path("forward drive");

    static {
        //forwardDrive.addSegment(new StrafeSegment("strafe test", 10,0.3,1,false));
        //forwardDrive.addSegment(new StrafeSegment("strafe test", 10,0.3,1,false));
        forwardDrive.addSegment(new DriveSegment("drive to foundation", 50, 0.6, 1));
        //forwardDrive.addSegment(new DriveSegment("drive to foundation", -84, 0.6, 1));

        //forwardDrive.addSegment(new DriveSegment("forward drive", -5, 0.25, 1));
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
