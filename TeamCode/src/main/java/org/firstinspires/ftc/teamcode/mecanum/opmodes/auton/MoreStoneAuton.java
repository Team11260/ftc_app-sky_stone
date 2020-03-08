package org.firstinspires.ftc.teamcode.mecanum.opmodes.auton;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractAuton;
import org.firstinspires.ftc.teamcode.mecanum.hardware.Robot;

import static org.firstinspires.ftc.teamcode.mecanum.hardware.AutonPursuitPaths.RedDistanceCenterPath;
import static org.firstinspires.ftc.teamcode.mecanum.hardware.AutonPursuitPaths.RedDistanceRightPath;
import static org.firstinspires.ftc.teamcode.mecanum.hardware.AutonPursuitPaths.RedDragFoundation;

@Autonomous(name = "Auton more stone", group = "Test")
public class MoreStoneAuton extends AbstractAuton {

    Robot robot;

    @Override
    public void RegisterStates() {

        addState("Pick up first Stone", "drive to first sky stone", robot.grabStoneCallable());

        addState("Drop the block", "first trip to foundation", robot.deliverStoneCallable());
        addState("Drop the arm", "first trip to foundation", robot.redDelayedArmDownCallable());

        addState("pick up second sky stone", "drive to second sky stone", robot.grabStoneCallable());

        addState("Drop the block", "second trip to foundation", robot.deliverStoneCallable());
        addState("Drop the arm", "second trip to foundation", robot.redDelayedArmDownCallable());

        addState("pick up third sky stone", "drive to third sky stone", robot.grabStoneCallable());

        addState("Drop the block", "third trip to foundation", robot.deliverStoneCallable());
        addState("Drop the arm", "third trip to foundation", robot.redDelayedArmDownCallable());

        addState("pick up fourth sky stone", "drive to fourth sky stone", robot.grabStoneCallable());

//        addState("Drop the block", "last trip to foundation", robot.deliverStoneCallable());
//        addState("Drop the arm", "last trip to foundation", robot.redDelayedArmDownCallable());

        addState("Drop the block", "park the foundation", robot.deliverLastStoneCallable());

        addState("delayed Draggers down", "third trip to foundation", robot.delayedDraggerDownCallable());
        addState("Draggers halfway up", "drive to fourth sky stone", robot.delayedRedDraggerHalfwayCallable());
        addState("Draggers down", "last trip to foundation", robot.setDraggerDownCallable());

        addState("release dragger","park the foundation",robot.setDraggerUpCallable());

    }

    @Override
    public void Init() {

        robot = new Robot();

        robot.setArmDown();
        robot.setGripperRelease();

    }

    @Override
    public void Run() {

        robot.runDrivePath(RedDistanceRightPath);
        robot.runDrivePath(RedDragFoundation);

    }
}
