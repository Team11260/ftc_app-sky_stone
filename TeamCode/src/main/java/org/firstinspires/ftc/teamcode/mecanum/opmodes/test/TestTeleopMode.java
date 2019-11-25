package org.firstinspires.ftc.teamcode.mecanum.opmodes.test;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractTeleop;
import org.firstinspires.ftc.teamcode.mecanum.hardware.Robot;
import org.firstinspires.ftc.teamcode.mecanum.hardware.devices.lift.Lift;
import org.firstinspires.ftc.teamcode.mecanum.hardware.devices.lift.LiftController;
import org.upacreekrobotics.dashboard.Config;

@TeleOp(name = "Test Teleop Mode", group = "New")

@Config
public class TestTeleopMode extends AbstractTeleop {

    Robot robot;
    LiftController lift;

    @Override
    public void RegisterEvents() {
        addEventHandler("1_y_down", robot.setArmDownCallable());
        addEventHandler("1_x_down", robot.setArmUpCallable());

        addEventHandler("1_a_down", ()->lift.setSlideout());
        addEventHandler("1_b_down", robot.toggleRotationCallable());
    }


    @Override
    public void UpdateEvents() {
        double k = 0.5;
        double left_stick_x=gamepad1.left_stick_x,left_stick_y = -gamepad1.left_stick_y, right_stick_x = 0;
        robot.setDrivePowerAll(k*(left_stick_y+left_stick_x+right_stick_x),k*(left_stick_y-left_stick_x-right_stick_x),
                               k*(left_stick_y-left_stick_x+right_stick_x),k*(left_stick_y+left_stick_x-right_stick_x));
    }


    @Override
    public void Init() {
        robot = new Robot();
        lift = new LiftController();
    }

    @Override
    public void Loop() {
        /*
        telemetry.addData(DoubleTelemetry.LogMode.INFO,"front left position: "+drive.getFrontLeftPosition());
        telemetry.addData(DoubleTelemetry.LogMode.INFO,"front right position: "+ drive.getFrontRightPosition());
        telemetry.addData(DoubleTelemetry.LogMode.INFO,"back left position: "+drive.getBackLeftPosition());
        telemetry.addData(DoubleTelemetry.LogMode.INFO,"back right position: "+drive.getBackRightPosition());
        telemetry.update();
        */
    }

    @Override
    public void Stop() {

    }
}
