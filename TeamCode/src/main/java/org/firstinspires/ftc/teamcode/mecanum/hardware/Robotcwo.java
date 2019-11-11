package org.firstinspires.ftc.teamcode.mecanum.hardware;

import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.framework.util.RobotCallable;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.mecanum.hardware.Robot;

public class Robotcwo extends Robot {

    public static double ARM_DOWN_POSITION = 0.855;
    public static double ARM_UP_POSITION = 0.5;
    public static double GRIPPER_GRIP_POSITION = 0.8;
    public static double GRIPPER_RELEASE_POSITION = 0.35;

    HardwareMap hardwareMap;

    public Servo arm, gripper;

    String skyStonePosition;

    public Robotcwo() {

        arm = hardwareMap.servo.get("arm_servo");
        arm.setDirection(Servo.Direction.FORWARD);
        arm.setPosition(ARM_UP_POSITION);

        gripper = hardwareMap.servo.get("gripper_servo");
        gripper.setDirection(Servo.Direction.FORWARD);
        gripper.setPosition(GRIPPER_RELEASE_POSITION);

    }

    public void strafe(int distance){}

    public void getSkyStonePosition(){
        skyStonePosition = getSkyStonePositionThreeStones();
    }

    public RobotCallable armDownCallable() {
        return () -> armDown();
    }

    public void armDown(){
        arm.setPosition(ARM_DOWN_POSITION);
    }

    public RobotCallable armUpCallable() {
        return () -> armUp();
    }

    public void armUp(){
        arm.setPosition(ARM_UP_POSITION);
    }

    public RobotCallable gripperGripCallable() {
        return () -> gripperGrip();
    }

    public void gripperGrip(){
        gripper.setPosition(GRIPPER_GRIP_POSITION);
    }

    public RobotCallable gripperReleaseCallable() {
        return () -> gripperRelease();
    }

    public void gripperRelease(){
        gripper.setPosition(GRIPPER_RELEASE_POSITION);
    }

    public RobotCallable trayArmDownCallable() {
        return () -> trayArmDown();
    }

    public void trayArmDown(){}

    public RobotCallable trayArmUpCallable() {
        return () -> trayArmUp();
    }

    public void trayArmUp(){}

    public RobotCallable releaseStoneCallable() {
        return () -> releaseStone();
    }

    public void releaseStone(){}

}
