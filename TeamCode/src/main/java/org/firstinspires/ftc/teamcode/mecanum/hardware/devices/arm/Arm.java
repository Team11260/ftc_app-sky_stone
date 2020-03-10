package org.firstinspires.ftc.teamcode.mecanum.hardware.devices.arm;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Arm {

    private Servo arm, gripper;

    public Arm(HardwareMap hardwareMap) {

        arm = hardwareMap.servo.get("arm_servo");
        arm.setDirection(Servo.Direction.FORWARD);

        //arm.setPosition(ARM_AUTON_POSITION);


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
}
