package org.firstinspires.ftc.teamcode.mecanum.opmodes.auton;

import org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractAuton;
import org.firstinspires.ftc.teamcode.framework.userhardware.DoubleTelemetry;
import org.firstinspires.ftc.teamcode.mecanum.hardware.Constants;
import org.firstinspires.ftc.teamcode.mecanum.hardware.Robot;
import org.upacreekrobotics.dashboard.Dashboard;

abstract public class BaseTwoStone extends AbstractAuton {
    Robot robot;
    String place = "place";
    boolean isRed = true;


    @Override
    public void RegisterStates() {
        addState("update position check","start",robot.checkPositionCallable());
        addState("Pick up first Stone", "drive to first sky stone", robot.grabStoneCallable());
        addState("Place first skystone", "first trip to foundation", robot.deliverStoneCallable());
        addState("delayed arm down", "Place first skystone", robot.delayedArmDownCallable());
        addState("Pick up second Stone", "drive to second sky stone", robot.grabStoneCallable());
        addState("Place second skystone", "second trip to foundation", robot.deliverStoneCallable());
        //addState("Draggers down", "second trip to foundation", robot.delayedDraggerDownCallable());
        addState("delayed arm down after 2nd skystone", "Place second skystone", robot.delayedArmDownCallable());
        addState("Pick up third Stone", "drive to third sky stone", robot.grabStoneCallable());
        //addState("Draggers halfway up", "drive to third sky stone", robot.delayedDraggerHalfwayCallable());
        addState("Place third skystone", "third trip to foundation", robot.deliverStoneCallable());
        addState("delayed arm down after 3rd skystone", "Place third skystone", robot.delayedArmDownCallable());
        addState("Delayed draggers down", "third trip to foundation", robot.delayedDraggerDownCallable());
        addState("Pick up fourth Stone", "drive to fourth sky stone", robot.grabStoneCallable());
        addState("Draggers halfway up", "drive to fourth sky stone", robot.delayedDraggerHalfwayCallable());
        addState("Place fourth skystone", "fourth trip to foundation", robot.deliverStoneCallable());
        addState("Draggers down", "fourth trip to foundation", robot.setDraggerDownCallable());
        addState("release dragger","Pull the foundation",robot.delayedDraggerUpCallable());
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
        String newPlace = robot.getSkyStonePositionThreeStones(0, isRed);

        if(newPlace != null)  place = newPlace;

        telemetry.addData(DoubleTelemetry.LogMode.INFO, place);
        telemetry.update();
    }

    @Override
    public void Stop(){

        Dashboard.startOpMode(Constants.OPMODE_TO_START_AFTER_AUTON);
    }

}
