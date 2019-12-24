package org.firstinspires.ftc.teamcode.mecanum.opmodes.auton;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractAuton;
import org.firstinspires.ftc.teamcode.framework.userhardware.DoubleTelemetry;
import org.firstinspires.ftc.teamcode.mecanum.hardware.Robot;

import static org.firstinspires.ftc.teamcode.mecanum.hardware.AutonPursuitPaths.*;

@Autonomous (name="Red Two Stone",group="New")

public class RedTwoStone extends AbstractAuton {
    Robot robot;

    @Override
    public void RegisterStates() {

        addState("Gripper Grip", "drive to first sky stone", robot.setGripperGripCallable());

    }

    @Override
    public void Init() {
        robot = new Robot();
        robot.setArmDown();
        robot.setGripperRelease();
        robot.dragger.setBackUp();
        robot.dragger.setFrontUp();
    }

    public void InitLoop(){

        telemetry.addData(DoubleTelemetry.LogMode.INFO, robot.getSkyStonePositionThreeStones(0));

        telemetry.update();
    }

    @Override
    public void Run() {

        robot.driver.setDrivePowerAll(0.5,0.5,0.5,0.5);



        switch (robot.getSkyStonePositionThreeStones(0)) {
            case "Right":
                //robot.runDrivePath(RedPurePursuitRight);
                break;

            case "Left":
                robot.runDrivePath(RedPurePursuitLeft);
                break;

            case "Center":
                robot.runDrivePath(RedPurePursuitCenter);
                break;

            default:
                //robot.runDrivePath(collectCenterSkyStone);
                break;


        }
    }
}
