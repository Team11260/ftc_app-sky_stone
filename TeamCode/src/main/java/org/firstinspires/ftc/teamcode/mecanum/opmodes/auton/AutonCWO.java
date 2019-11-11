package org.firstinspires.ftc.teamcode.mecanum.opmodes.auton;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractAuton;
import org.firstinspires.ftc.teamcode.framework.util.PathState;
import org.firstinspires.ftc.teamcode.framework.util.State;
import org.firstinspires.ftc.teamcode.mecanum.hardware.Constantscwo;
import org.firstinspires.ftc.teamcode.mecanum.hardware.RobotState;
import org.firstinspires.ftc.teamcode.mecanum.hardware.Robotcwo;

//@Autonomous(name = "Auton CWO", group = "New")

public class AutonCWO extends AbstractAuton {

    Robotcwo robot;
    private Servo arm, gripper;

    @Override
    public void RegisterStates() {

        addState(new State("arm down to collect", "start", robot.armDownCallable()));
        addState(new PathState("arm grips the stone", "drive to sky stone", robot.gripperGripCallable()));
        addState(new State("arm up", "arm grips the stone", robot.armUpCallable()));
        addState(new PathState("gripping pause", "drive to sky stone", () -> {
            RobotState.currentPath.pause();
            delay(Constantscwo.GRIPPING_DELAY);
            RobotState.currentPath.resume();
        }));
        addState(new PathState("arm down with block", "drive to tray", robot.armDownCallable()));
        addState(new State("release stone", "arm down with block", robot.releaseStoneCallable()));
        addState(new State("tray latch down", "arm down with block", robot.trayArmDownCallable()));
        addState(new PathState("tray latch up", "pull tray", robot.trayArmUpCallable()));
    }

    @Override
    public void Init() {

        robot = new Robotcwo();

    }

    public void InitLoop(int loop) {
        if (loop%5 == 1)
        robot.getSkyStonePosition();
    }

    @Override
    public void Run() {




        /*robot.runDrivePath(Constantscwo.approachTheStones);
        switch(robot.skyStonePosition){
            case "Right":
                robot.strafe(6);
                robot.runDrivePath(Constantscwo.collectRightSkyStone);
                break;
            case "Left":
                robot.strafe(-6);
                robot.runDrivePath(Constantscwo.collectLeftSkyStone);
                break;
            case "Center":
                robot.runDrivePath(Constantscwo.collectCenterSkyStone);
                break;
            default:
                robot.runDrivePath(Constantscwo.collectCenterSkyStone);
                break;
        }
        robot.runDrivePath(Constantscwo.dumpAndDrag);
        robot.strafe(-30);
        robot.runDrivePath(Constantscwo.park);*/

    }


}
