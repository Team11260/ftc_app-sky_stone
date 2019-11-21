package org.firstinspires.ftc.teamcode.mecanum.hardware.devices.arm;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Arm {


    public static double ARM_DOWN_POSITION = 0.6;
    public static double ARM_UP_POSITION = 0.5;
    public static double GRIPPER_GRIP_POSITION = 0.8;
    public static double GRIPPER_RELEASE_POSITION = 0.35;

    private Servo arm, gripper;

    public Arm(HardwareMap hardwareMap) {

        arm = hardwareMap.servo.get("arm_servo");
        arm.setDirection(Servo.Direction.REVERSE);
        //arm.setPosition(ARM_UP_POSITION);


        gripper = hardwareMap.servo.get("gripper_servo");
        gripper.setDirection(Servo.Direction.FORWARD);
        //gripper.setPosition(GRIPPER_RELEASE_POSITION);


    }


    public void setGripperPosition(double position) {
        gripper.setPosition(position);
    }

    public void setArmPosition(double position) {
        arm.setPosition(position);
    }

    public void setArmUp() {
        arm.setPosition(ARM_UP_POSITION);
    }

    public void setArmDown() {
        arm.setPosition(ARM_DOWN_POSITION);
    }

    public void setGripperGrip() {
        gripper.setPosition(GRIPPER_GRIP_POSITION);
    }

    public void setGripperRelease() {
        gripper.setPosition(GRIPPER_RELEASE_POSITION);
    }

}
