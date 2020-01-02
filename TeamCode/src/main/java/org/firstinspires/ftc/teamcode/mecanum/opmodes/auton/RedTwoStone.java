package org.firstinspires.ftc.teamcode.mecanum.opmodes.auton;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractAuton;
import org.firstinspires.ftc.teamcode.framework.userhardware.DoubleTelemetry;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.Segment;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.TurnSegment;
import org.firstinspires.ftc.teamcode.mecanum.hardware.Robot;

import static org.firstinspires.ftc.teamcode.mecanum.hardware.AutonPursuitPaths.*;

@Autonomous(name = "Red Two Stone", group = "New")

public class RedTwoStone extends BaseTwoStone {

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

        addState("Pick up third Stone", "drive to third stone", robot.grabStoneCallable());
        addState("Place third skystone", "third trip to foundation", robot.deliverStoneCallable());


    }

    @Override
    public void Run() {

        robot.arm.setArmDownPosition();
        robot.setGripperRelease();

        switch (place) {
            case "Right":
                robot.runDrivePath(RedPurePursuitRight);
                break;

            case "Left":
                robot.runDrivePath(RedPurePursuitLeft);
                break;

            case "Center":
                robot.runDrivePath(RedPurePursuitCenter);
                break;

            default:
                robot.runDrivePath(RedPurePursuitCenter);
                break;
        }
        delay(10000);

    }


}
