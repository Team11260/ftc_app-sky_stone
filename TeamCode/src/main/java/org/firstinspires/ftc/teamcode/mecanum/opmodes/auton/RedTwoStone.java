package org.firstinspires.ftc.teamcode.mecanum.opmodes.auton;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.framework.userhardware.purepursuit.MecanumPurePursuitController;

import static org.firstinspires.ftc.teamcode.mecanum.hardware.AutonPursuitPaths.*;

@Autonomous(name = "Red Two Stone", group = "New")

public class RedTwoStone extends BaseTwoStone {


    @Override
    public void Run() {

//        robot.arm.setArmDownPosition();
//        robot.setGripperRelease();
        robot.setDraggerDown();

        /*
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

         */

//        robot.setDrivePowerAll(0.5,-0.5,-0.5,0.5);
//        delay(1000);
        robot.setDrivePowerAll(0,0,0.6,-0.6);
        delay(3000);
        robot.setDrivePowerAll(0,0,0,0);
    }
}
