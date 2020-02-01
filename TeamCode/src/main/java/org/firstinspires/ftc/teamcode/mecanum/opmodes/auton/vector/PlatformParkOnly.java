package org.firstinspires.ftc.teamcode.mecanum.opmodes.auton.vector;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractAuton;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.Path;
import org.firstinspires.ftc.teamcode.mecanum.hardware.Constants;
import org.firstinspires.ftc.teamcode.mecanum.hardware.Robot;
import org.upacreekrobotics.dashboard.Dashboard;

import static org.firstinspires.ftc.teamcode.mecanum.hardware.AutonPursuitPaths.PlatformParkOnlyPath;
import static org.firstinspires.ftc.teamcode.mecanum.hardware.AutonPursuitPaths.RedShortPath;

@Autonomous(name = "Platform Park Only", group = "New")

public class PlatformParkOnly extends AbstractAuton {
    Robot robot;
    @Override
    public void RegisterStates() {
        addState("dragger halfway","drive to building zone",robot.delayedDraggerHalfwayCallable());
        addState("put down dragger full","strafe to foundation",robot.setDraggerDownCallable());
        addState("dragger up","park the foundation",robot.setDraggerUpCallable());
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
        //delay(3000);
        robot.runDrivePath(PlatformParkOnlyPath);
    }

    public void Stop(){

        Dashboard.startOpMode(Constants.OPMODE_TO_START_AFTER_AUTON);
    }
}

