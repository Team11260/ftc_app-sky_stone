package org.firstinspires.ftc.teamcode.mecanum.opmodes.auton;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractAuton;
import org.firstinspires.ftc.teamcode.mecanum.hardware.Robot;

import static org.firstinspires.ftc.teamcode.mecanum.hardware.AutonPursuitPaths.*;

@Autonomous(name = "Auton Drive with DistSensor", group = "Test")

public class AutonDriveWithDistanceSensor extends AbstractAuton {

    Robot robot;

    @Override
    public void RegisterStates() {

        addState("Pick up first Stone", "drive to first sky stone", robot.grabStoneCallable());

        addState("Drop the block", "first trip to foundation", robot.deliverStoneCallable());
        addState("Drop the arm", "first trip to foundation", robot.redDelayedArmDownCallable());


        addState("pick up second sky stone", "drive to second sky stone", robot.grabStoneCallable());

        addState("Drop the block", "second trip to foundation", robot.deliverStoneCallable());
        addState("Drop the arm", "second trip to foundation", robot.redDelayedArmDownCallable());
        addState("Draggers down", "second trip to foundation", robot.delayedDraggerDownCallable());


        addState("pick up third sky stone", "drive to third sky stone", robot.grabStoneCallable());
        addState("Draggers halfway up", "drive to third sky stone", robot.delayedRedDraggerHalfwayCallable());


        addState("Draggers down", "last trip to foundation", robot.setDraggerDownCallable());
        addState("Drop the block", "last trip to foundation", robot.deliverStoneCallable());
        addState("release dragger","park the foundation",robot.setDraggerUpCallable());

    }

    @Override
    public void Init() {
        robot = new Robot();

        //robot.setArmDown();
        //robot.setGripperRelease();

    }

    public void InitLoop(int loop) {

        robot.driver.updatePose();
        //telemetry.addData(DoubleTelemetry.LogMode.INFO, "Encoder x position: " + robot.driver.getStrafePosition());

      //  telemetry.addData(DoubleTelemetry.LogMode.INFO, "Encoder y position: " + robot.driver.getStraightPosition());
      //  telemetry.addData(DoubleTelemetry.LogMode.INFO, "Encoder heading: " + robot.driver.getCurrentPosition().getHeading());
      //  telemetry.update();
        //delay(2000);

        //telemetry.addData(DoubleTelemetry.LogMode.INFO, "Distance from distance sensor: " + robot.driver.distanceSensor.getDistance());
        //telemetry.update();
    }

    @Override
    public void Run() {

        robot.runDrivePath(RedDistanceCenterPath);
        robot.runDrivePath(RedDragFoundation);
        delay(10000);


//        double distanceRemaining = 10.0;
//        robot.driver.strafe(0.35);
//        ElapsedTime MeasureTime = new ElapsedTime();
//        MeasureTime.reset();
//        int i = 0;
//        double totalTime = 0;
//        while ((distanceRemaining > 2.2) || (distanceRemaining < 0.2) || Double.isNaN(distanceRemaining)) {
//            distanceRemaining = robot.driver.distanceSensor.getDistance();
//            telemetry.addData(DoubleTelemetry.LogMode.INFO, "Distance from distance sensor: " + distanceRemaining);
//            telemetry.update();
//            i++;
//
//        }
//        robot.driver.strafe(0);
//        robot.setGripperGrip();
//        delay(500);
//        robot.setArmUp();
//
//        totalTime = MeasureTime.milliseconds();
//
//
//        telemetry.addData(DoubleTelemetry.LogMode.INFO,"Average Time: "+totalTime/i);
//        telemetry.addData(DoubleTelemetry.LogMode.INFO,"Loop count: "+i);
//
//
//        telemetry.addData(DoubleTelemetry.LogMode.INFO, "Loop done distance: " + distanceRemaining);
//
//        telemetry.update();
//
//        stopRequested();

    }


}
