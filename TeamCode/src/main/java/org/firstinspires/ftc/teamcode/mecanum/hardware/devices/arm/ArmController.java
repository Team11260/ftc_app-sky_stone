package org.firstinspires.ftc.teamcode.mecanum .hardware.devices.arm;

import org.firstinspires.ftc.teamcode.framework.util.SubsystemController;
import static org.firstinspires.ftc.teamcode.mecanum.hardware.Constants.*;

public class ArmController extends SubsystemController {

    Arm arm;

    boolean up = true, gripped = true;


    public ArmController() {
        arm = new Arm(hardwareMap);

    }

    public void setArmAutonPosition() {
        arm.setArmPosition(ARM_AUTON_POSITION);
        up = true;
    }

    public void setArmUpPosition(){
        arm.setArmPosition(ARM_UP_POSITION);
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

    public void setGripperReleasePosition() {
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
