package org.firstinspires.ftc.teamcode.mecanum.opmodes.test;


import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractTeleop;
import org.firstinspires.ftc.teamcode.framework.util.RobotCallable;
import org.firstinspires.ftc.teamcode.mecanum.hardware.devices.ArmServo;
import org.firstinspires.ftc.teamcode.mecanum.hardware.devices.Drive;

@TeleOp(name = "teleop",group = "new")
public class Teleop extends AbstractTeleop {


    Drive drive;
    ArmServo arm;



    @Override
    public void RegisterEvents() {
        addEventHandler("1_a_down",()->arm.setPosition(0));
        addEventHandler("1_b_down",()->arm.setPosition(1));
    }


    @Override
    public void UpdateEvents() {
        double k = 0.5;
        double left_stick_x=gamepad1.left_stick_x,left_stick_y = -gamepad1.left_stick_y, right_stick_x = gamepad1.right_stick_x;
        drive.setDrivePowerAll(k*(left_stick_y-left_stick_x-right_stick_x),k*(left_stick_y+left_stick_x+right_stick_x),k*(left_stick_y+left_stick_x-right_stick_x),k*(left_stick_y-left_stick_x+right_stick_x));
    }

    @Override
    public void Init() {
        drive = new Drive(hardwareMap,telemetry);
        arm = new ArmServo(hardwareMap);

        arm.setPosition(1);
        delay(500);
    }

    @Override
    public void Loop() {

    }

    @Override
    public void Stop() {


    }




}
