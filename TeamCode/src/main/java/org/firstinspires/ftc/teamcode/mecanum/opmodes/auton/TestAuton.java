package org.firstinspires.ftc.teamcode.mecanum.opmodes.auton;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractAuton;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.DriveSegment;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.Path;
import org.firstinspires.ftc.teamcode.mecanum.hardware.Robot;

@Autonomous(name = "Test Auton", group = "Auton")
//@Disabled

public class TestAuton extends AbstractAuton {

    private Robot robot;

    @Override
    public void RegisterStates() {

    }

    @Override
    public void Init() {
        robot = new Robot();
    }

    @Override
    public void Run() {
        Path testPath = new Path("test path");
        testPath.addSegment(new DriveSegment("test segment", 60.0, 0.0, 0, 0.0));

        robot.runDrivePath(testPath);
    }
}
