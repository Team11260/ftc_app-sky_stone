package org.firstinspires.ftc.teamcode.mecanum.opmodes.auton;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractAuton;
import org.firstinspires.ftc.teamcode.mecanum.hardware.devices.drive.DriveController;
import org.firstinspires.ftc.teamcode.mecanum.opmodes.auton.vector.Vector;
import org.upacreekrobotics.dashboard.Config;

@Autonomous (name = "Vector Test",group = "New")
@Disabled
@Config
public class VectorTest extends AbstractAuton {

    DriveController drive;
    public static double x = 0,y = 0;

    @Override
    public void RegisterStates() {

    }

    @Override
    public void Init() {
        drive = new DriveController();
    }

    @Override
    public void Run() {
    drive.driveToVector(new Vector(x,y));
    delay (1000);
    drive.stop();
    }
}
