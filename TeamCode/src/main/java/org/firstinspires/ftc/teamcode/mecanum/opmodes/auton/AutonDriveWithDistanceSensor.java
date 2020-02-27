package org.firstinspires.ftc.teamcode.mecanum.opmodes.auton;

import android.widget.DatePicker;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractAuton;
import org.firstinspires.ftc.teamcode.framework.userhardware.DoubleTelemetry;
import org.firstinspires.ftc.teamcode.framework.util.PathState;
import org.firstinspires.ftc.teamcode.framework.util.State;
import org.firstinspires.ftc.teamcode.mecanum.hardware.Constants;
import org.firstinspires.ftc.teamcode.mecanum.hardware.Robot;
import org.firstinspires.ftc.teamcode.mecanum.hardware.RobotState;

import static org.firstinspires.ftc.teamcode.mecanum.hardware.AutonPursuitPaths.*;

@Autonomous(name = "Auton Drive with DistSensor", group = "Test")

public class AutonDriveWithDistanceSensor extends AbstractAuton {

    Robot robot;

    @Override
    public void RegisterStates() {

        //addState("Pick up first Stone", "drive to first sky stone", robot.blockFind());
        addState("Actually Pick up first Stone", "drive to first sky stone", robot.grabStoneCallable());

        addState("Drop the block", "first trip to foundation", robot.deliverStoneCallable());
        addState("Drop the arm", "first trip to foundation", robot.redDelayedArmDownCallable());


        //addState("Pick up second Stone", "drive to second sky stone", robot.blockFind());
        addState("pick up second sky stone", "drive to second sky stone", robot.grabStoneCallable());

        addState("Drop the block", "second trip to foundation", robot.deliverStoneCallable());
        addState("Drop the arm", "second trip to foundation", robot.redDelayedArmDownCallable());


        //addState("Pick up second Stone", "drive to third sky stone", robot.blockFind());
        addState("pick up second sky stone", "drive to third sky stone", robot.grabStoneCallable());

        // addState(new State("arm down to collect", "start", robot.armDownCallable()));
        // addState(new State("gripper release", "start", robot.setGripperReleaseCallable()));
        // addState(new State("get distance", "start", robot.getDistanceLoop()));
        // addState(new State("gripper grip", "start", robot.distanceGrip()));
    }

    @Override
    public void Init() {
        robot = new Robot();

        robot.setArmDown();
        robot.setGripperRelease();

    }

    public void InitLoop(int loop) {

        //telemetry.addData(DoubleTelemetry.LogMode.INFO, "Distance from distance sensor: " + robot.driver.distanceSensor.getDistance());
        //telemetry.update();
    }

    @Override
    public void Run() {

        robot.runDrivePath(RedDistancePath);
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
