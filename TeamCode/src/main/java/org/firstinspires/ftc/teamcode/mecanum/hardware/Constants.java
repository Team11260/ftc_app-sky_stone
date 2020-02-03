package org.firstinspires.ftc.teamcode.mecanum.hardware;

import org.firstinspires.ftc.teamcode.framework.userhardware.paths.AngleDriveSegment;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.DriveSegment;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.Path;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.PurePursuitSegment;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.Segment;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.StrafeSegment;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.TurnSegment;
import org.firstinspires.ftc.teamcode.framework.userhardware.purepursuit.Point;
import org.firstinspires.ftc.teamcode.framework.userhardware.purepursuit.PursuitPath;
import org.upacreekrobotics.dashboard.Config;

@Config
public final class Constants {

    ////////OpMode////////
    public static final String OPMODE_TO_START_AFTER_AUTON = "Teleop Mode";

    ////////Drive////////
    public static int TRACK_WIDTH = 20;
    public static double STRAFE_ENCODER_COUNTS_INCH = 219;
    public static double STRAIGHT_ENCODER_COUNTS_INCH = 208;


    ////////Arm////////
    //For Old
    public static double ARM_DOWN_POSITION = 0.49;
    public static double ARM_PIN_POSITION = 1.0;
    public static double ARM_AUTON_POSITION = 0.75;
    public static double ARM_UP_POSITION = 0.83;
    public static double ARM_HALFWAY_POSITION = 0.7;
    public static double GRIPPER_GRIP_POSITION = 0.4;
    public static double GRIPPER_GRIP_POSITION_FOR_LONG_ARM = 0.45;
    public static double GRIPPER_RELEASE_POSITION = 0.0;


    ////////Dragger////////
    public static double BACK_DRAGGER_UP_POSITION = 1.00;
    public static double FRONT_DRAGGER_UP_POSITION = 1.00;
    public static double BACK_DRAGGER_DOWN_POSITION = 0.39;
    public static double FRONT_DRAGGER_DOWN_POSITION = 0.37;
    public static double BACK_DRAGGER_HALFWAY_POSITION = 0.51;
    public static double FRONT_DRAGGER_HALFWAY_POSITION = 0.51;


    ////////Vision////////
    //For Old
    public static int BLOCKHEIGHT = 132;
    public static int RED_XORIGIN = 50;
    public static int RED_YORIGIN = 85;
//    public static int BLUE_XORIGIN = 512;
//    public static int BLUE_YORIGIN = 100;
    public static int BLUE_XORIGIN = 500 ;
    public static int BLUE_YORIGIN = 70;
    public static int BLOCKWIDTH = 246;


    public static int LINE_WIDTH = 7;
    public static int HEIGHT = 45;
    public static int THRESHOLD = 80;

    ////////Lift////////
    public static int LIFT_DOWN = 0;
    public static int LIFT_UP = 1234;
    public static double TILT_DOWN = 1.00;
    public static double TILT_UP = 0.59;
    public static double GRABBER_OPEN = 0.45;
    public static double GRABBER_CLOSE = 0.8;
    public static double GRABBER_OPEN_WIDE = 0.36;
    public static double SLIDE_IN = 0.05;
    public static double SLIDE_OUT = 0.77;
    public static double SLIDE_OUT_PARK = 0.98;
    public static double SLIDE_OUT_HALF = 0.35;
    public static double PAN_RIGHT = 0;
    public static double PAN_MIDDLE = 0.5;
    public static double PAN_LEFT = 1.00;
    public static double CLAMP_OPEN = 0.45;
    public static double CLAMP_CLOSE = 0.73;

    /**/public final static PursuitPath goToFirstBlockCenter = new PursuitPath(new Point(0, 0), new Point(0, 28));


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

        //collectRightSkyStone.addSegment(new StrafeSegment("strafe from foundation", 2.5,0.5,1));
       // collectRightSkyStone.addSegment((new DriveSegment("drive straight a distance", 50, 0.4, 2.0)));
       // collectRightSkyStone.addSegment((new DriveSegment("drive straight a distance", -50, 0.4,2.0)));
        //collectRightSkyStone.addSegment((new DriveSegment("drive straight a distance", -95, 0.25, 1)));

       // collectRightSkyStone.addSegment((new DriveSegment("drive straight a distance", -64, 0.25, 100)));
                //collectRightSkyStone.addSegment(new AngleDriveSegment("angle to block", -4,0.4,0));
        //collectRightSkyStone.addSegment((new DriveSegment("drive straight a distance", -86, 0.25, 100)));
    }

    public final static Path redEndgame = new Path("do red endgame");

static {



        // clamp draggers
       // robot.setDraggerDown();
        // drive forward (8-10)
        redEndgame.addSegment(new DriveSegment("drive to end of foundation",8,0.3,1));
        // rotate 70-80 degrees
        redEndgame.addSegment(new TurnSegment("rotate foundation towards wall",70,0.3,2,5000));
        // strafe 12 inches
        redEndgame.addSegment(new StrafeSegment("push into wall",12,0.3,1));
        //unclamp draggers
//        robot.setDraggerUp();
        //back up
        redEndgame.addSegment(new StrafeSegment("back up",-2,0.3,1));

        //turn -90 degrees
        redEndgame.addSegment(new TurnSegment("rotate to tape measure position", -90, 0.3, 0, 0));
        // shoot tape measure
        // drive forward 6 inches
//        robot.setDrivePowerAll(0.3,0.3,0.3,0.3);
//        delay(500);



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
        //dragFoundation.addSegment(new DriveSegment("drag foundation", -17, 0.3, 100));
        dragFoundation.addSegment((new StrafeSegment("back up", 8, 0.25, 1)));
        dragFoundation.addSegment((new TurnSegment("turn 90", -83.0, 0.6, 2.0,50000)));

    }

    public static final Path avoidRobot = new Path("avoid robot");
    static {

        avoidRobot.addSegment(new DriveSegment("avoid robot", 14, 0.5, 100));
    }

}
