package org.firstinspires.ftc.teamcode.mecanum .hardware.devices.arm;

import org.firstinspires.ftc.teamcode.framework.util.SubsystemController;
import static org.firstinspires.ftc.teamcode.mecanum.hardware.Constants.*;

public class ArmController extends SubsystemController {

    Arm leftArm;
    Arm rightArm;

    boolean leftUp = true, rightUp = true, leftGripped = true, rightGripped = true;


    public ArmController() {
        leftArm = new Arm(hardwareMap);
        rightArm = new Arm(hardwareMap);
    }

    public void setRightArmUpPosition() {
        rightArm.setRightArmPosition(RIGHT_ARM_UP_POSITION);
        rightUp = true;
    }

    public void setLeftArmUpPosition() {
        leftArm.setLeftArmPosition(LEFT_ARM_UP_POSITION);
        leftUp = true;
    }

    public void setRightArmDownPosition() {
        rightArm.setRightArmPosition(RIGHT_ARM_DOWN_POSITION);
        rightUp = true;
    }

    public void setLeftArmDownPosition() {
        leftArm.setLeftArmPosition(LEFT_ARM_DOWN_POSITION);
        leftUp = true;
    }

    public void setArmBackPosition() {
        //leftArm.setLeftArmPosition(ARM_BACK_POSITION);
        leftUp = true;
    }


    public void setArmPinPosition() {
        leftArm.setLeftArmPosition(ARM_PIN_POSITION);
        leftUp = false;
    }


    public void setLeftGripperGripPosition() {
        leftArm.setLeftGripperPosition(GRIPPER_GRIP_POSITION);
        leftGripped = true;
    }

    public void setRightGripperGripPosition() {
        rightArm.setRightGripperPosition(GRIPPER_GRIP_POSITION);
        rightGripped = true;
    }


    public void setLeftGripperReleasePosition() {
        leftArm.setLeftGripperPosition(GRIPPER_RELEASE_POSITION);
        leftGripped = false;
    }

    public void setRightGripperReleasePostion() {
        rightArm.setRightGripperPosition(GRIPPER_RELEASE_POSITION);
        rightGripped = false;
    }

    public void toggleLeftArmPosition() {
        leftArm.setLeftArmPosition(leftUp ? LEFT_ARM_DOWN_POSITION : LEFT_ARM_UP_POSITION);
        leftUp = !leftUp;
    }

    public void toggleRightArmPosition() {
        rightArm.setRightArmPosition(rightUp ? RIGHT_ARM_DOWN_POSITION : RIGHT_ARM_UP_POSITION);
        rightUp = !rightUp;
    }

    public void toggleLeftGripperPosition() {
        leftArm.setLeftGripperPosition(leftGripped ? GRIPPER_GRIP_POSITION : GRIPPER_RELEASE_POSITION);
        leftGripped = !leftGripped;
    }

    public void toggleRightGripperPosition() {
        rightArm.setRightGripperPosition(rightGripped ? GRIPPER_GRIP_POSITION : GRIPPER_RELEASE_POSITION);
        rightGripped = !rightGripped;
    }

    @Override
    public void update() throws Exception {

    }

    @Override
    public void stop() {

    }
}
