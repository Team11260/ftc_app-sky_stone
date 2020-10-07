package org.firstinspires.ftc.teamcode.mecanum.opmodes.auton;

import org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractAuton;
import org.firstinspires.ftc.teamcode.framework.userhardware.DoubleTelemetry;
import org.firstinspires.ftc.teamcode.mecanum.hardware.Constants;
import org.firstinspires.ftc.teamcode.mecanum.hardware.Robot;
import org.upacreekrobotics.dashboard.Dashboard;

abstract public class BaseUltimate extends AbstractAuton {
    Robot robot;
    String place = "place";
    boolean isRed = true;

    @Override
    public void RegisterStates() {
        addState("Prepare pick up","start",robot.preparePickUpCallable());
        addState("update position check","start",robot.checkPositionCallable());
        addState("Pick up first Stone", "drive to first sky stone", robot.grabStoneCallable());
        addState("Place first skystone", "first trip to foundation", robot.deliverStoneCallable());
        addState("delayed arm down", "Place first skystone", isRed ? robot.redDelayedArmDownLongCallable() : robot.blueDelayedArmDownLongCallable());
        addState("Pick up second Stone", "drive to second sky stone", robot.grabStoneCallable());
        addState("Place second skystone", "second trip to foundation", robot.deliverStoneCallable());
        addState("Draggers down", "second trip to foundation", robot.delayedDraggerDownCallable());
        addState("delayed arm down after 2nd skystone", "Place second skystone", isRed ? robot.redDelayedArmDownCallable(): robot.blueDelayedArmDownCallable() );
        addState("Pick up third Stone", "drive to third sky stone", robot.grabStoneCallable());
        addState("Draggers halfway up", "drive to third sky stone", isRed ? robot.delayedRedDraggerHalfwayCallable() :robot.delayedBlueDraggerHalfwayCallable());
        addState("Place third skystone", "last trip to foundation", robot.deliverStoneCallable());
//        addState("delayed arm down after 3rd skystone", "Place third skystone", robot.redDelayedArmDownCallable());
//        addState("Delayed draggers down", "third trip to foundation", robot.delayedDraggerDownCallable());
//        addState("Pick up fourth Stone", "drive to fourth sky stone", robot.grabStoneCallable());
//        addState("Draggers halfway up", "drive to fourth sky stone", robot.delayedBlueDraggerHalfwayCallable());
//        addState("Place fourth skystone", "fourth trip to foundation", robot.deliverStoneCacxcxllable());
        addState("Draggers down", "last trip to foundation", robot.setDraggerDownCallable());
        addState("release dragger","Pull the foundation",robot.delayedDraggerUpCallable());
        addState("release dragger 2","park the foundation",robot.setDraggerUpCallable());

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

    public void Run(){
        robot.imageShutDown();
    }

    @Override
    public void Stop(){
        Dashboard.startOpMode(Constants.OPMODE_TO_START_AFTER_AUTON);
    }

}