package org.firstinspires.ftc.teamcode.mecanum.opmodes.test;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractOpMode;
import org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractTeleop;
import org.firstinspires.ftc.teamcode.mecanum.hardware.Robot;
import org.firstinspires.ftc.teamcode.mecanum.hardware.devices.clamp.ClampController;

@TeleOp(name = "Test Clamp Mode", group = "New")

public class ClampTest extends AbstractTeleop {

    Robot robot;

    @Override
    public void RegisterEvents() {

        //addEventHandler("1_x_down",()->robot.toggleLeftArm());

        //addEventHandler("1_y_down", ()->robot.toggleRightArm());

        //addEventHandler("1_a_down",()->robot.toggleLeftGripper());

        //addEventHandler("1_b_down", ()->robot.toggleRightGripper());

    }

    @Override
    public void UpdateEvents() {

        double k = 0.5;

        double left_stick_x=-gamepad1.left_stick_x,left_stick_y = -gamepad1.left_stick_y, right_stick_x = gamepad1.right_stick_x;

        robot.setDrivePowerAll(k*(left_stick_y+left_stick_x+right_stick_x),k*(left_stick_y-left_stick_x-right_stick_x),
                k*(left_stick_y-left_stick_x+right_stick_x),k*(left_stick_y+left_stick_x-right_stick_x));
    }

    @Override
    public void Init() {
        robot = new Robot();
    }

    @Override
    public void Loop() {

    }

    @Override
    public void Stop() {

    }

}
