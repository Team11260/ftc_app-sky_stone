package org.firstinspires.ftc.teamcode.mecanum.opmodes.test;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractTeleop;
import org.firstinspires.ftc.teamcode.framework.userhardware.DoubleTelemetry;
import org.firstinspires.ftc.teamcode.mecanum.hardware.devices.Drive;
import org.firstinspires.ftc.teamcode.mecanum.hardware.devices.Launcher;


@TeleOp(name = "Tank Drive",group = "new")
public class TankDrive extends AbstractTeleop {


    Drive drive;
    Launcher launcher;



    @Override
    public void RegisterEvents() {
        addEventHandler("1_x_down",()->launcher.toggleLauncher(-0.9));

    }

    @Override
    public void UpdateEvents() {
        drive.setDrivePower(-gamepad1.left_stick_y,-gamepad1.right_stick_y);
    }

    @Override
    public void Init() {
        drive = new Drive(hardwareMap,telemetry);
        launcher = new Launcher(hardwareMap, telemetry);
        telemetry.addData(DoubleTelemetry.LogMode.INFO,"test");
        telemetry.update();




    }

    @Override
    public void Start() {
        super.Start();
    }

    @Override
    public void Loop() {
        telemetry.addData(DoubleTelemetry.LogMode.INFO,"Velocity: "+launcher.getVelocity());
        telemetry.update();

    }

    @Override
    public void Stop() {

    }
}
