package org.firstinspires.ftc.teamcode.mecanum.opmodes.auton;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.Servo;

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

        addState(new State("arm down to collect", "start", robot.armDownCallable()));
        addState(new State("gripper release", "start", robot.setGripperReleaseCallable()));
        addState(new State("get distance", "start", robot.getDistanceLoop()));
        addState(new State("gripper grip", "start", robot.distanceGrip()));
    }

    @Override
    public void Init() {
        robot = new Robot();

    }

    public void InitLoop(int loop) {

        telemetry.addData(DoubleTelemetry.LogMode.INFO,"Distance from distance sensor: "+robot.driver.distanceSensor.getDistance());
        telemetry.update();
    }

    @Override
    public void Run() {
        robot.driver.strafe(0.2);
        while (robot.driver.distanceSensor.getDistance()>2.2) ;
        telemetry.addData(DoubleTelemetry.LogMode.INFO,"Loop done distance: "+robot.driver.distanceSensor.getDistance());
        robot.driver.strafe(0);
        stopRequested();

    }


}
