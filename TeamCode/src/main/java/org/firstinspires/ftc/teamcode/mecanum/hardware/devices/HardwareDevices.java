package org.firstinspires.ftc.teamcode.mecanum.hardware.devices;

import org.firstinspires.ftc.teamcode.mecanum.hardware.devices.arm.ArmController;
import org.firstinspires.ftc.teamcode.mecanum.hardware.devices.drive.DriveController;
import org.firstinspires.ftc.teamcode.mecanum.hardware.devices.intake.IntakeController;

public class HardwareDevices {

    public DriveController drive = null;
    public IntakeController intake = null;
    public ArmController arm = null;

    public HardwareDevices() {
        drive = new DriveController();
        intake = new IntakeController();
        arm = new ArmController();
    }

    public void stop() {
        if (drive != null) drive.stop();
        if (intake != null) intake.stop();
        if (arm != null) arm.stop();
    }
}