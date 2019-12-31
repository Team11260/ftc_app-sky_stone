package org.firstinspires.ftc.teamcode.mecanum.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractTeleop;
import org.firstinspires.ftc.teamcode.framework.userhardware.DoubleTelemetry;
import org.firstinspires.ftc.teamcode.mecanum.hardware.Robot;
import org.firstinspires.ftc.teamcode.mecanum.hardware.devices.clamp.ClampController;
import org.firstinspires.ftc.teamcode.mecanum.hardware.devices.intake.IntakeController;
import org.firstinspires.ftc.teamcode.mecanum.hardware.devices.lift.LiftController;
import org.upacreekrobotics.dashboard.Config;

@TeleOp(name = "Teleop Mode", group = "New")

@Config
public class TeleopMode extends AbstractTeleop {

    Robot robot;

    double k = 0.5;

    @Override
    public void RegisterEvents() {
        addEventHandler("2_y_down", ()->robot.lift().toggleTilt());
        addEventHandler("2_x_down", ()->robot.lift().toggleGrabber());

        addEventHandler("2_a_down", ()->robot.lift().toggleSlide());
        addEventHandler("2_b_down", ()->robot.lift().cyclePan());
        addEventHandler("2_rb_down",()-> robot.lift().setSlideOutHalf());

        addEventHandler("1_x_down",()->robot.intake().toggleRotation());
        addEventHandler("1_y_down", ()->robot.intake().toggleConveyor());
        addEventHandler("1_a_down", robot.toggleBothCallable());
        addEventHandler("1_lb_down",()->robot.intake().toggleReverseConveyor());
        addEventHandler("1_rb_down",()->robot.intake().toggleReverseRotation());

        addEventHandler("1_b_down", ()-> toggleDriveSpeed());


    }

    @Override
    public void UpdateEvents() {
        double liftMultiplier = 0.8;
        double left_stick_x=-gamepad1.left_stick_x,left_stick_y = -gamepad1.left_stick_y, right_stick_x = gamepad1.right_stick_x;
        double right_trigger=gamepad1.right_trigger*0.4,left_trigger = gamepad1.left_trigger*0.4;
        robot.setDrivePowerAll(k*(left_stick_y+left_stick_x+right_stick_x-right_trigger+left_trigger),k*(left_stick_y-left_stick_x-right_stick_x+right_trigger-left_trigger),
                               k*(left_stick_y-left_stick_x+right_stick_x+right_trigger-left_trigger),k*(left_stick_y+left_stick_x-right_stick_x-right_trigger+left_trigger));
        robot.lift().lift.setLiftPower(gamepad2.left_stick_y*liftMultiplier);

    }

    public void toggleDriveSpeed(){
       k = k==0.5? 0.8: 0.5;
    }

    @Override
    public void Init() {
        robot = new Robot();
        LiftController lift = robot.lift();
        lift.setTiltUp();
        lift.setGrabberClose();
        lift.setPanMiddle();
        lift.setSlideIn();


    }

    @Override
    public void Loop() {
        telemetry.addData(DoubleTelemetry.LogMode.INFO,"Front left position: "+robot.driver.getFrontLeftPosition());
        telemetry.addData(DoubleTelemetry.LogMode.INFO,"Back left position: "+ robot.driver.getBackLeftPosition());

        telemetry.update();
    }

    @Override
    public void Stop() {

    }
}
