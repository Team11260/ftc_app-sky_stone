package org.firstinspires.ftc.teamcode.mecanum.opmodes.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractAuton;
import org.firstinspires.ftc.teamcode.framework.userhardware.DoubleTelemetry;
import org.firstinspires.ftc.teamcode.framework.userhardware.purepursuit.Path;
import org.firstinspires.ftc.teamcode.framework.userhardware.purepursuit.Point;
import org.firstinspires.ftc.teamcode.framework.userhardware.purepursuit.PurePursuitController;
import org.firstinspires.ftc.teamcode.mecanum.hardware.devices.Drive;

import static org.firstinspires.ftc.teamcode.mecanum.hardware.Constants.TRACK_WIDTH;

@Autonomous(name = "Mecanum LoadingZone Autonomus", group = "New")
public class MecanumLoadingZoneAuton extends AbstractAuton {

    private Drive drive;

    @Override
    public void RegisterStates() {

    }

    @Override
    public void Init() {

        drive = new Drive(hardwareMap,telemetry);

        drive.follow(new Path(new Point(0, 0), new Point(48, -10)));
    }

    @Override
    public void Run() {
        drive.updateLoop();
        drive.setDrivePowerAll(0, 0, 0,0);
        telemetry.addData(DoubleTelemetry.LogMode.INFO, "X: " + drive.getCurrentPosition().getX() + " Y: " + drive.getCurrentPosition().getY());
        telemetry.addData(DoubleTelemetry.LogMode.INFO);
    }
}