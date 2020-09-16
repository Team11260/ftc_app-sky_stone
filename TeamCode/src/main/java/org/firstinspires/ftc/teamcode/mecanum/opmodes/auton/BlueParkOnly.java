package org.firstinspires.ftc.teamcode.mecanum.opmodes.auton;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractAuton;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.Path;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.PurePursuitSegment;
import org.firstinspires.ftc.teamcode.framework.userhardware.purepursuit.Point;
import org.firstinspires.ftc.teamcode.framework.userhardware.purepursuit.PursuitPath;
import org.firstinspires.ftc.teamcode.mecanum.hardware.Constants;
import org.firstinspires.ftc.teamcode.mecanum.hardware.Robot;
import org.upacreekrobotics.dashboard.Dashboard;

import static org.firstinspires.ftc.teamcode.mecanum.hardware.AutonPursuitPaths.BLUE_PARK_X;
import static org.firstinspires.ftc.teamcode.mecanum.hardware.AutonPursuitPaths.BLUE_PARK_Y;
@Autonomous(group = "new",name = "Blue Park Only")
public class BlueParkOnly extends AbstractAuton {

    Robot robot;

    public BlueParkOnly(){
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

    @Override
    public void Run() {


        delay(3000);
        robot.runDrivePath(parkPath());

    }

    public void Stop(){

        Dashboard.getOpModeHandler().startOpMode(Constants.OPMODE_TO_START_AFTER_AUTON);
    }

    protected Path parkPath(){
        Path park = new Path("park");

        park.addSegment(new PurePursuitSegment("Park",
                new PursuitPath(new Point(0, 0), new Point(BLUE_PARK_X, BLUE_PARK_Y))));


        return park;
    }

}
