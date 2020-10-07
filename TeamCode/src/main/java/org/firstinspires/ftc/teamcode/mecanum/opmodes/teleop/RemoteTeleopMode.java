package org.firstinspires.ftc.teamcode.mecanum.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractTeleop;
import org.firstinspires.ftc.teamcode.framework.userhardware.DoubleTelemetry;
import org.firstinspires.ftc.teamcode.mecanum.hardware.BaseRobot;

@TeleOp(name = "RemoteTeleopMode", group = "New")
public class RemoteTeleopMode extends AbstractTeleop {

    BaseRobot baseRobot;
    @Override
    public void RegisterEvents() {
        addEventHandler("1_a_down", ()-> baseRobot.testDrive(0.9));
    }

    @Override
    public void UpdateEvents() {

        double left_stick_y=gamepad1.left_stick_y;
        double right_stick_y=gamepad1.right_stick_y;

        baseRobot.straightPower(left_stick_y);

        telemetry.addData("left encoder:  ",  baseRobot.getLocation());
        telemetry.addData("");
        telemetry.update();

    }

    @Override
    public void Init() {

        baseRobot = new BaseRobot();
        telemetry.setDefaultLogMode(DoubleTelemetry.LogMode.INFO);

        telemetry.addData("left encoder:  ",  baseRobot.getLocation());
        telemetry.addData("");
        telemetry.update();


    }

    @Override
    public void Loop() {

    }

    @Override
    public void Stop() {

    }
}
