package org.firstinspires.ftc.teamcode.mecanum.hardware.devices.arm;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Arm {

    private Servo leftArm, rightArm, leftGripper, rightGripper;

    public Arm(HardwareMap hardwareMap) {

        leftArm = hardwareMap.servo.get("left_arm_servo");
        rightArm = hardwareMap.servo.get("right_arm_servo");
        leftArm.setDirection(Servo.Direction.FORWARD);
        rightArm.setDirection(Servo.Direction.FORWARD);

        //arm.setPosition(ARM_UP_POSITION);


        leftGripper = hardwareMap.servo.get("left_gripper_servo");
        leftGripper.setDirection(Servo.Direction.FORWARD);
        rightGripper = hardwareMap.servo.get("right_gripper_servo");
        rightGripper.setDirection(Servo.Direction.FORWARD);
        //gripper.setPosition(GRIPPER_RELEASE_POSITION);


    }


    public void setLeftGripperPosition(double position) {
        leftGripper.setPosition(position);
    }
    public void setRightGripperPosition(double position) {
        rightGripper.setPosition(position);
    }

    public void setLeftArmPosition(double position) {
        leftArm.setPosition(position);
    }
    public void setRightArmPosition(double position) {
        rightArm.setPosition(position);
    }
}
