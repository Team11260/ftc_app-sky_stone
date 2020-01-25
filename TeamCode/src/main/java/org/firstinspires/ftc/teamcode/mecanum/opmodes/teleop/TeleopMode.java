package org.firstinspires.ftc.teamcode.mecanum.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractTeleop;
import org.firstinspires.ftc.teamcode.framework.userhardware.DoubleTelemetry;
import org.firstinspires.ftc.teamcode.mecanum.hardware.Robot;
import org.upacreekrobotics.dashboard.Config;

@TeleOp(name = "Teleop Mode", group = "New")

@Config
public class TeleopMode extends AbstractTeleop {

    Robot robot;

    double k = 0.4;

    @Override
    public void RegisterEvents() {
        addEventHandler("2_y_down", ()->robot.lift().toggleTilt());
        addEventHandler("2_x_down", ()->robot.lift().toggleGrabber());

        addEventHandler("2_a_down", ()->robot.lift().toggleSlide());
        addEventHandler("2_b_down", ()->robot.lift().cyclePan());
        addEventHandler("2_rb_down",()-> robot.lift().setSlideOutHalf());
        addEventHandler("1_a_down", ()-> robot.toggleBothDraggersHalf());


        addEventHandler("1_x_down",()->robot.driver.toggleMotorIntake());

        addEventHandler("1_x_down",()->robot.lift.setTiltUpOnce());

        addEventHandler("1_rb_down",()->robot.driver.reverseMotorIntake());
        addEventHandler("1_y_down", ()->robot.intake.toggleConveyor());
        addEventHandler("1_lb_down",()->robot.intake.startReverseConveyor());


        addEventHandler("1_b_down", ()-> toggleDriveSpeed());

        addEventHandler("1_dpd_down",()->robot.arm.toggleArmPosition());
        addEventHandler("1_dpl_down",()->robot.arm.toggleGripperPosition());

        addEventHandler("2_dpu_down",()->robot.tapeMeasure.extend());
        addEventHandler("2_dpr_down",()->robot.tapeMeasure.retract());

        addEventHandler("2_dpu_up",()->robot.tapeMeasure.stop());
        addEventHandler("2_dpr_up",()->robot.tapeMeasure.stop());

        addEventHandler("2_dpl_down",()->robot.lift.pushSlideOut());
        addEventHandler("2_dpd_down",()->robot.lift.pullSlideIn());

    }

    @Override
    public void UpdateEvents() {
        double liftMultiplier = 0.8;
        double left_stick_x=gamepad1.left_stick_x,left_stick_y = -gamepad1.left_stick_y, right_stick_x = gamepad1.right_stick_x;
        double right_trigger=gamepad1.right_trigger*0.32,left_trigger = gamepad1.left_trigger*0.32;
        robot.setDrivePowerAll(k*(left_stick_y+left_stick_x+right_stick_x)-right_trigger+left_trigger,k*(left_stick_y-left_stick_x-right_stick_x)+right_trigger-left_trigger,
                               k*(left_stick_y-left_stick_x+right_stick_x)+right_trigger-left_trigger,k*(left_stick_y+left_stick_x-right_stick_x)-right_trigger+left_trigger);
        robot.lift.setLiftPower(-gamepad2.left_stick_y*liftMultiplier);

    }

    public void toggleDriveSpeed(){
       k = k==0.4? 0.9: 0.4;
    }

    @Override
    public void Init() {
        robot = new Robot();
        //robot.lift.setTiltUp();
        robot.lift.setGrabberClose();
        robot.lift.setPanMiddle();
        robot.lift.setSlideIn();
        robot.arm.setGripperGripPosition();
        robot.arm.setArmUpPosition();
        robot.dragger.setFrontUp();
        robot.dragger.setBackUp();

        telemetry.addData(DoubleTelemetry.LogMode.INFO,"Front left position: "+robot.driver.getFrontLeftPosition());
        telemetry.addData(DoubleTelemetry.LogMode.INFO,"Back left position: "+ robot.driver.getBackLeftPosition());

        telemetry.update();
    }

    @Override
    public void Loop() {

    }

    @Override
    public void Stop() {

    }
}
