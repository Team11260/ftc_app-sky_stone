package org.firstinspires.ftc.teamcode.mecanum.opmodes.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractAuton;
import org.firstinspires.ftc.teamcode.framework.util.PathState;
import org.firstinspires.ftc.teamcode.framework.util.State;
import org.firstinspires.ftc.teamcode.mecanum.hardware.Constantscwo;

@Autonomous(name = "Auton CWO", group = "New")

public class AutonCWO extends AbstractAuton {

    Robotcwo robot;


    @Override
    public void RegisterStates() {

        addState(new State("auton arm down to collect", "start", robot.armDownCallable()));
        addState(new PathState("auton arm close to squeeze", "drive to sky stone", robot.armCloseCallable()));
        addState(new State("auton arm up", "arm close to squeeze", robot.armUpCallable()));
        addState(new PathState("arm down with block", "drive to tray", robot.dropStoneCallable()));
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

        switch(robot.skyStonePosition){
            case "Right":
                robot.runDrivePath(Constantscwo.collectRightSkyStone);
                break;
            case "Left":
                robot.runDrivePath(Constantscwo.collectLeftSkyStone);
                break;
            case "Center":
                robot.runDrivePath(Constantscwo.collectCenterSkyStone);
                break;
            default:
                robot.runDrivePath(Constantscwo.collectCenterSkyStone);
                break;
        }
        delay(400);
        robot.runDrivePath(Constantscwo.reverseTurn);

        switch(robot.skyStonePosition){
            case "Right":
                robot.runDrivePath(Constantscwo.driveRightSkyStone);
                break;
            case "Left":
                robot.runDrivePath(Constantscwo.driveLeftSkyStone);
                break;
            case "Center":
                robot.runDrivePath(Constantscwo.driveCenterSkyStone);
                break;
            default:
                robot.runDrivePath(Constantscwo.driveCenterSkyStone);
                break;
        }
        delay(500);
        robot.runDrivePath(Constantscwo.pullTray);
        delay(300);
        robot.strafe(-15);
        robot.runDrivePath(Constantscwo.park);


    }


}
