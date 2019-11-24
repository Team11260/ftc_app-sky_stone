package org.firstinspires.ftc.teamcode.mecanum.hardware;

import org.firstinspires.ftc.teamcode.framework.userhardware.paths.Path;
import org.firstinspires.ftc.teamcode.mecanum.hardware.devices.HardwareDevices;

public class Robot {

    private HardwareDevices hardware;

    public Robot() {
        hardware = new HardwareDevices();
        hardware.init();
    }

    public void runDrivePath(Path path) {
        hardware.drive.runPath(path);
    }
}
