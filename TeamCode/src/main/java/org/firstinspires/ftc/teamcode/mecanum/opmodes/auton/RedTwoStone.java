package org.firstinspires.ftc.teamcode.mecanum.opmodes.auton;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.framework.userhardware.DoubleTelemetry;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.Path;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.PurePursuitSegment;
import org.firstinspires.ftc.teamcode.framework.userhardware.purepursuit.MecanumPurePursuitController;
import org.firstinspires.ftc.teamcode.framework.userhardware.purepursuit.Point;
import org.firstinspires.ftc.teamcode.framework.userhardware.purepursuit.PursuitPath;
import org.upacreekrobotics.dashboard.Config;

import static org.firstinspires.ftc.teamcode.mecanum.hardware.AutonPursuitPaths.*;

@Autonomous(name = "Red Two Stone", group = "New")

@Config
public class RedTwoStone extends BaseTwoStone {

    public static double x = 0.2,y=0.47,z=-0.3;


    @Override
    public void Run() {

//        robot.arm.setArmDownPosition();
//        robot.setGripperRelease();
        robot.setDraggerDown();

        //delay(100);

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


        double frontLeft = (x - y - z);
        double frontRight = (x + y + z);
        double backLeft = (x + y - z);
        double backRight = (x - y + z);
        telemetry.addData(DoubleTelemetry.LogMode.INFO, " heading: " + robot.driver.getHeading());
        telemetry.update();

        // robot.setDrivePowerAll(frontLeft,frontRight,backLeft,backRight);

        robot.setDrivePowerAll(frontLeft,frontRight,backLeft,backRight);
        while (robot.driver.getHeading()>-80);
        //delay(1700);
        robot.setDrivePowerAll(0,0,0,0);
        delay(200);
        robot.dragger.setDraggerUp();
        robot.setDrivePowerAll(0.5,-0.5,-0.5,0.5);
        delay(1000);
        robot.setDrivePowerAll(0,0,0,0);

        telemetry.addData(DoubleTelemetry.LogMode.INFO," Pose X: " + robot.driver.getCurrentPosition().getX());
        telemetry.addData(DoubleTelemetry.LogMode.INFO," Pose Y: " + robot.driver.getCurrentPosition().getY());

        //robot.driver.resetPosition();
        //robot.driver.resetAngleToZero();
        telemetry.update();

        //robot.driver.runDrivePath(DragFoundation);
    }
}
