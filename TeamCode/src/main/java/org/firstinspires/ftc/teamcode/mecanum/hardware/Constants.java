package org.firstinspires.ftc.teamcode.mecanum.hardware;

import org.upacreekrobotics.dashboard.Config;

@Config
public final class Constants {

    ////////OpMode////////
    public static final String OPMODE_TO_START_AFTER_AUTON = "Teleop Mode";

    public static int GRIPPING_DELAY = 500;


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
    public static int RED_XORIGIN = 85;
    public static int RED_YORIGIN = 60;
    //    public static int BLUE_XORIGIN = 512;
//    public static int BLUE_YORIGIN = 100;
    public static int BLUE_XORIGIN = 515;
    public static int BLUE_YORIGIN = 45;
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
    public static double SLIDE_OUT = 0.78;
    public static double SLIDE_OUT_PARK = 0.98;
    public static double SLIDE_OUT_HALF = 0.35;
    public static double PAN_RIGHT = 0;
    public static double PAN_MIDDLE = 0.5;
    public static double PAN_LEFT = 1.00;
    public static double CLAMP_OPEN = 0.45;
    public static double CLAMP_CLOSE = 0.73;

}
