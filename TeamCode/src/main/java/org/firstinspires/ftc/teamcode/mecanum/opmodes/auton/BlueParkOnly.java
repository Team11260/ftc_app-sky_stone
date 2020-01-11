package org.firstinspires.ftc.teamcode.mecanum.opmodes.auton;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractAuton;
import org.firstinspires.ftc.teamcode.mecanum.hardware.Constants;
import org.firstinspires.ftc.teamcode.mecanum.hardware.Robot;
import org.upacreekrobotics.dashboard.Dashboard;

import static org.firstinspires.ftc.teamcode.mecanum.hardware.AutonPursuitPaths.BlueShortPath;
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
        robot.runDrivePath(BlueShortPath);

    }

    public void Stop(){

        Dashboard.startOpMode(Constants.OPMODE_TO_START_AFTER_AUTON);
    }
}
