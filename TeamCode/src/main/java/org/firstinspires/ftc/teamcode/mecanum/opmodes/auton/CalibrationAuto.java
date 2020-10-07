package org.firstinspires.ftc.teamcode.mecanum.opmodes.auton;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractAuton;
import org.firstinspires.ftc.teamcode.framework.userhardware.DoubleTelemetry;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.Path;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.PurePursuitSegment;
import org.firstinspires.ftc.teamcode.framework.userhardware.purepursuit.Point;
import org.firstinspires.ftc.teamcode.framework.userhardware.purepursuit.PursuitPath;
import org.firstinspires.ftc.teamcode.mecanum.hardware.Robot;

//@Autonomous(name = "Calibration", group = "New")

public class CalibrationAuto extends AbstractAuton {

    Robot robot;


    @Override
    public void RegisterStates() {

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

        ElapsedTime time = new ElapsedTime();

        time.reset();


       robot.runDrivePath(Calibration());
//        robot.setDrivePowerAll(-0.8,-0.8,-0.8,-0.8);
//
//        delay(1000);
//
//        robot.setDrivePowerAll(0,0,0,0);
//
//        delay(100);
//
//        robot.setDrivePowerAll(-0.8,-0.8,-0.8,-0.8);
//
//        delay(800);
//
//        robot.setDrivePowerAll(0,0,0,0);






        telemetry.addData(DoubleTelemetry.LogMode.INFO, "x: " + robot.driver.getCurrentPosition().getX() +
                " y: " + robot.driver.getCurrentPosition().getY());

        telemetry.addData(DoubleTelemetry.LogMode.INFO, "total time: " + time.milliseconds() / 1000);

        telemetry.update();

        delay(10000);


    }


    protected Path Calibration() {
        Path CalibrationPath = new Path("Calibration");

        CalibrationPath.addSegment(new PurePursuitSegment("First Curve",
                new PursuitPath(

                        new Point(0, 0),
                        new Point(0, 6),
                        new Point(-86, 6),
                        new Point(-86, 0)
                ).setMaxDeceleration(0.013)


        ));

        CalibrationPath.addSegment(new PurePursuitSegment("Second Curve",
                new PursuitPath(
                        new Point(-86, 0),
                        new Point(-86, 6),
                        new Point(0, 6),
                        new Point(0, 0)


                ).setMaxDeceleration(0.013)

        ));

        return CalibrationPath;


    }


}
