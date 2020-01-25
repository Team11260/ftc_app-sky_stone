package org.firstinspires.ftc.teamcode.mecanum.opmodes.auton;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractAuton;
import org.firstinspires.ftc.teamcode.mecanum.hardware.Robot;

import static org.firstinspires.ftc.teamcode.mecanum.hardware.AutonPursuitPaths.RedDragFoundationParking;
import static org.firstinspires.ftc.teamcode.mecanum.hardware.AutonPursuitPaths.RedDragFoundationParking2;
import static org.firstinspires.ftc.teamcode.mecanum.hardware.AutonPursuitPaths.lastStoneDrive;

@Autonomous (name = "Last Stone Delivery", group = "New")
public class LastStoneDelivery extends AbstractAuton {

    Robot robot;

    @Override
    public void RegisterStates() {
        addState("detect stone","trip to last stone",robot.findLastStone());
        addState("set Dragger halfway","drive down the wall",robot.setDraggerHalfwayCallable());
        addState("set Dragger down","drive up to the foundation",robot.setDraggerDownCallable());
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

    @Override
    public void Run() {
        delay(19000);
        robot.runDrivePath(RedDragFoundationParking2);

        /*
        switch (robot.place){
            case "Middle":
                robot.runDrivePath(leftLastStone);
                break;
            case "Left":
                robot.runDrivePath(middleLastStone);
                break;
            default:
                robot.runDrivePath(leftLastStone);
        }

        robot.runDrivePath(redDragFoundation);
        */
    }
}
