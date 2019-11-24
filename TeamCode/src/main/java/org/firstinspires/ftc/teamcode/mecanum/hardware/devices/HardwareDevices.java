package org.firstinspires.ftc.teamcode.mecanum.hardware.devices;

import org.firstinspires.ftc.teamcode.mecanum.hardware.devices.drive.DriveController;

public class HardwareDevices {

    public DriveController drive;

    public HardwareDevices() {
        drive = new DriveController();
    }

    public void init() {
        if(drive != null) drive.init();
    }

    public void stop() {
        if(drive != null) drive.stop();
    }
}
