package org.firstinspires.ftc.teamcode.mecanum.opmodes.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractAuton;
import org.firstinspires.ftc.teamcode.framework.userhardware.purepursuit.Path;
import org.firstinspires.ftc.teamcode.framework.userhardware.purepursuit.Point;
import org.firstinspires.ftc.teamcode.mecanum.hardware.Robot;

@Autonomous(name = "PurePursuitTest", group = "Test")
//@Disabled

public class PurePursuitTest extends AbstractAuton {

    private Robot robot;

    @Override
    public void RegisterStates() {

    }

    @Override
    public void Init() {
        robot = new Robot();
        robot.setGripperGrip();
    }

    @Override
    public void Run() {
        /*Path path = new Path(new Point(0,0), new Point (0,-6), new Point (72,-6), new Point(72, 0));

        path.setMaxSpeed(1);
        path.setTrackingErrorSpeed(4.0);
        path.setLookAheadDistance(5);

        robot.runTestPurePursuit(path);

        path = new Path(new Point(72,0), new Point (72,-6), new Point (0,-6), new Point(0, 0));

        path.setMaxSpeed(1);
        path.setTrackingErrorSpeed(4.0);
        path.setLookAheadDistance(5);*/

        Path path = new Path(new Point(0,0), new Point(50, 0));

        path.setMaxSpeed(1);
        path.setTrackingErrorSpeed(4.0);
        path.setLookAheadDistance(5);

        robot.runTestPurePursuit(path);

        robot.runTestPurePursuit(path);
    }
}
