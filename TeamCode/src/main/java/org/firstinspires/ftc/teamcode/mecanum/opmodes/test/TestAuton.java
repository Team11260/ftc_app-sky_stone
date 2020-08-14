package org.firstinspires.ftc.teamcode.mecanum.opmodes.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractAuton;
import org.firstinspires.ftc.teamcode.mecanum.hardware.devices.Drive;

@Autonomous(name = "Test Auton",group = "new")
public class TestAuton extends AbstractAuton {

    Drive drive;

    @Override
    public void RegisterStates() {

    }

    @Override
    public void Init() {
        drive = new Drive(hardwareMap,telemetry);
    }

    @Override
    public void Run() {
        drive.setDrivePowerAll(1,1);
        delay(1000);

    }
}
