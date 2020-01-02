package org.firstinspires.ftc.teamcode.mecanum.opmodes.auton;

import org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractAuton;
import org.firstinspires.ftc.teamcode.framework.userhardware.DoubleTelemetry;
import org.firstinspires.ftc.teamcode.mecanum.hardware.Robot;

abstract public class BaseTwoStone extends AbstractAuton {
    Robot robot;
    String place;

    @Override
    public void Init() {
        robot = new Robot();
        robot.arm.setArmInitPosition();
        robot.arm.setGripperGripPosition();
        robot.lift.setTiltUp();
    }

    public void InitLoop() {

        telemetry.addData(DoubleTelemetry.LogMode.INFO, robot.getSkyStonePositionThreeStones(0));
        place = robot.getSkyStonePositionThreeStones(0);
        telemetry.update();
    }

}
