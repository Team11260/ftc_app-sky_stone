package org.firstinspires.ftc.teamcode.mecanum.opmodes.auton;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractAuton;

import static org.firstinspires.ftc.teamcode.mecanum.hardware.AutonPursuitPaths.BlueDragFoundation;
import static org.firstinspires.ftc.teamcode.mecanum.hardware.AutonPursuitPaths.BluePurePursuitCenter;
import static org.firstinspires.ftc.teamcode.mecanum.hardware.AutonPursuitPaths.BluePurePursuitLeft;
import static org.firstinspires.ftc.teamcode.mecanum.hardware.AutonPursuitPaths.BluePurePursuitRight;

@Autonomous(name = "Blue Two Stone", group = "New")


public class BlueTwoStone extends BaseTwoStone {

    public BlueTwoStone (){
        super();
        isRed = false;
    }

    @Override
    public void Run() {

        robot.arm.setArmDownPosition();
        robot.setGripperRelease();

        switch (place) {
            case "Right":
                robot.runDrivePath(BluePurePursuitRight);
                break;

            case "Left":
                robot.runDrivePath(BluePurePursuitLeft);
                break;

            case "Center":
                robot.runDrivePath(BluePurePursuitCenter);
                break;

            default:
                robot.runDrivePath(BluePurePursuitCenter);
                break;
        }

       robot.runDrivePath(BlueDragFoundation);
    }
}