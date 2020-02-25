package org.firstinspires.ftc.teamcode.mecanum.opmodes.auton;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractAuton;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.Path;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.PurePursuitSegment;
import org.firstinspires.ftc.teamcode.framework.userhardware.purepursuit.Point;
import org.firstinspires.ftc.teamcode.framework.userhardware.purepursuit.PursuitPath;
import org.firstinspires.ftc.teamcode.mecanum.hardware.Constants;
import org.firstinspires.ftc.teamcode.mecanum.hardware.Robot;
import org.firstinspires.ftc.teamcode.mecanum.opmodes.auton.BaseTwoStone;
import org.upacreekrobotics.dashboard.Dashboard;

import static org.firstinspires.ftc.teamcode.mecanum.hardware.AutonPursuitPaths.RED_PARK_X;
import static org.firstinspires.ftc.teamcode.mecanum.hardware.AutonPursuitPaths.RED_PARK_Y;


@Autonomous(group = "new", name = "Red Park Only")
public class RedParkOnly extends AbstractAuton {


    Robot robot;

    public RedParkOnly() {
        super();
    }

    @Override
    public void RegisterStates() {

    }

    @Override
    public void Init() {
        robot = new Robot();
        robot.arm.setArmUpPosition();
        robot.arm.setGripperGripPosition();
        robot.lift.setTiltUp();
    }

    public void Run() {
        delay(3000);
        robot.runDrivePath(parkPath());
    }

    public void Stop() {

        Dashboard.startOpMode(Constants.OPMODE_TO_START_AFTER_AUTON);
    }


    protected Path parkPath() {

        Path park = new Path("park");

        park.addSegment(new PurePursuitSegment("Park",
                new PursuitPath(new Point(0, 0), new Point(RED_PARK_X, RED_PARK_Y))));


        return park;

    }


}
