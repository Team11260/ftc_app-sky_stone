package org.firstinspires.ftc.teamcode.mecanum .hardware.devices.arm;

import org.firstinspires.ftc.teamcode.framework.util.SubsystemController;
import org.firstinspires.ftc.teamcode.mecanum.hardware.devices.arm.Arm;

public class ArmController extends SubsystemController {

    Arm arm;


    public static double ARM_DOWN_POSITION = 0.65;
    public static double ARM_PIN_POSITION = 1.0;
    public static double ARM_UP_POSITION = 0.95;
    public static double ARM_BACK_POSITION = 0.3;
    public static double GRIPPER_GRIP_POSITION = 0.3;
    public static double GRIPPER_RELEASE_POSITION = 0.8;

    boolean up = true, gripped = true;


    public ArmController() {
        arm = new Arm(hardwareMap);
    }

    public void setArmUpPosition() {
        arm.setArmPosition(ARM_UP_POSITION);
        up = true;
    }

    public void setArmBackPosition() {
        arm.setArmPosition(ARM_BACK_POSITION);
        up = true;
    }

    public void setArmDownPosition() {
        arm.setArmPosition(ARM_DOWN_POSITION);
        up = false;
    }

    public void setArmPinPosition() {
        arm.setArmPosition(ARM_PIN_POSITION);
        up = false;
    }


    public void setGripperGripPosition() {
        arm.setGripperPosition(GRIPPER_GRIP_POSITION);
        gripped = true;
    }

    public void setGripperReleasePostion() {
        arm.setGripperPosition(GRIPPER_RELEASE_POSITION);
        gripped = false;
    }

    public void toggleArmPosition() {
        arm.setArmPosition(up ? ARM_DOWN_POSITION : ARM_UP_POSITION);
        up = !up;
    }

    public void toggleGripperPosition() {
        arm.setGripperPosition(gripped ? GRIPPER_GRIP_POSITION : GRIPPER_RELEASE_POSITION);
        gripped = !gripped;
    }

    @Override
    public void update() throws Exception {

    }

    @Override
    public void stop() {

    }
}
