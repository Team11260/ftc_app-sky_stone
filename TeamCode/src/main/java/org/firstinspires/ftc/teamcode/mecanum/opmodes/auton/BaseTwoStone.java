package org.firstinspires.ftc.teamcode.mecanum.opmodes.auton;

import org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractAuton;
import org.firstinspires.ftc.teamcode.framework.userhardware.DoubleTelemetry;
import org.firstinspires.ftc.teamcode.mecanum.hardware.Robot;

abstract public class BaseTwoStone extends AbstractAuton {
    Robot robot;
    String place;
    boolean isRed = true;

    @Override
    public void RegisterStates() {

        addState("Pick up first Stone", "drive to first sky stone", robot.grabStoneCallable());
        addState("Place first skystone", "first trip to foundation", robot.deliverStoneCallable());
        addState("delayed arm down", "Place first skystone", robot.delayedArmDownCallable());
        addState("Pick up second Stone", "drive to second sky stone", robot.grabStoneCallable());
        addState("Place second skystone", "second trip to foundation", robot.deliverStoneCallable());
        addState("put down dragger halfway", "dragger backup", robot.setDraggerHalfwayCallable());
        addState("put down dragger full", "dragger forward full", robot.setDraggerDownCallable());

        //addState("delayed arm down", "Place second skystone", robot.delayedArmDownCallable());

//        addState("Pick up third Stone", "drive to third stone", robot.grabStoneCallable());
//        addState("Place third skystone", "third trip to foundation", robot.deliverStoneCallable());
    }

    @Override
    public void Init() {
        robot = new Robot();
        robot.arm.setArmUpPosition();
        robot.arm.setGripperGripPosition();
        robot.lift.setTiltUp();
        robot.dragger.setDraggerUp();
    }

    public void InitLoop() {

        telemetry.addData(DoubleTelemetry.LogMode.INFO, robot.getSkyStonePositionThreeStones(0, isRed));
        place = robot.getSkyStonePositionThreeStones(0, isRed);
        telemetry.update();
    }

}
