package org.firstinspires.ftc.teamcode.mecanum.opmodes.auton;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractAuton;
import org.firstinspires.ftc.teamcode.framework.userhardware.DoubleTelemetry;
import org.firstinspires.ftc.teamcode.framework.userhardware.outputs.SlewDcMotor;
import org.firstinspires.ftc.teamcode.mecanum.hardware.BaseRobot;
import org.firstinspires.ftc.teamcode.mecanum.hardware.devices.drive.Drive;

//import static org.firstinspires.ftc.teamcode.framework.userhardware.DoubleTelemetry.LogMode.INFO;
@Autonomous(group = "new", name = "CWOTest")
public class CWOTest extends AbstractAuton {

    private BaseRobot baseRobot;

    @Override
    public void RegisterStates() {

    }

    @Override
    public void Init() {
        baseRobot = new BaseRobot();
        telemetry.setDefaultLogMode(DoubleTelemetry.LogMode.INFO);
        telemetry.addData("initial location  ", baseRobot.getLocation());
        telemetry.update();
    }

    @Override
    public void Run() {

        telemetry.addData("Starting robot actions");
        baseRobot.driveStraight(30.0,0.5);
        telemetry.addData("");
        //baseRobot.driveStraight(48, 0.5);
        //baseRobot.launch();
        //baseRobot.driveStraight(12.0, 0.4);
        //baseRobot.turnLeft();
        //baseRobot.driveStraight(20.0, 0.3);
        //baseRobot.dropWobble();
        //baseRobot.turnRight();
        telemetry.addData("location", baseRobot.getLocation());
        telemetry.update();
        //telemetry.addData(baseRobot.getSpeed());
    }

}
