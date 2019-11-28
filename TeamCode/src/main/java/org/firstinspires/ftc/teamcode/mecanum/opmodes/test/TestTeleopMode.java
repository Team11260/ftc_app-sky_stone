package org.firstinspires.ftc.teamcode.mecanum.opmodes.test;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractTeleop;
import org.firstinspires.ftc.teamcode.mecanum.hardware.Robot;
import org.firstinspires.ftc.teamcode.mecanum.hardware.devices.intake.Intake;
import org.firstinspires.ftc.teamcode.mecanum.hardware.devices.intake.IntakeController;
import org.firstinspires.ftc.teamcode.mecanum.hardware.devices.lift.LiftController;
import org.upacreekrobotics.dashboard.Config;

@TeleOp(name = "Test Teleop Mode", group = "New")

@Config
public class TestTeleopMode extends AbstractTeleop {

    Robot robot;
    IntakeController intake;
    LiftController lift;

    @Override
    public void RegisterEvents() {
        addEventHandler("2_y_down", ()->lift.toggleTilt());
        addEventHandler("2_x_down", ()->lift.toggleGrabber());

        addEventHandler("2_a_down", ()->lift.toggleSlide());
        addEventHandler("2_b_down", ()->lift.cyclePan());

        addEventHandler("1_x_down",()->intake.toggleRotation());
        addEventHandler("1_y_down", ()->intake.toggleConveyor());
    }


    @Override
    public void UpdateEvents() {
        double k = 0.5;
        double liftMultiplier = 0.8;
        double left_stick_x=-gamepad1.left_stick_x,left_stick_y = -gamepad1.left_stick_y, right_stick_x = gamepad1.right_stick_x;
        robot.setDrivePowerAll(k*(left_stick_y+left_stick_x+right_stick_x),k*(left_stick_y-left_stick_x-right_stick_x),
                               k*(left_stick_y-left_stick_x+right_stick_x),k*(left_stick_y+left_stick_x-right_stick_x));
        lift.lift.setLiftPower(gamepad2.left_stick_y*liftMultiplier);
    }


    @Override
    public void Init() {
        robot = new Robot();
        lift = new LiftController();
        intake = new IntakeController();
        lift.setTiltUp();
        lift.setGrabberClose();
        lift.setPanMiddle();
        lift.setSlideIn();
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
