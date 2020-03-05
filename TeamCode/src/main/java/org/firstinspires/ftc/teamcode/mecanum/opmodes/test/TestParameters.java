package org.firstinspires.ftc.teamcode.mecanum.opmodes.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractAuton;
import org.firstinspires.ftc.teamcode.framework.userhardware.DoubleTelemetry;
import org.firstinspires.ftc.teamcode.mecanum.hardware.Robot;
import org.firstinspires.ftc.teamcode.mecanum.hardware.util.ParameterFileConfiguration;

@Autonomous(group = "new", name = "TestParameters")

public class TestParameters extends AbstractAuton {

    private ParameterFileConfiguration ParameterFile;

    @Override
    public void RegisterStates() {

    }

    @Override
    public void Init() {
        ParameterFile = new ParameterFileConfiguration();


    }

    @Override
    public void Run() {

        telemetry.addData(DoubleTelemetry.LogMode.INFO, "delay value = " + ParameterFile.getProperty("CameraDelayTimeMsec"));
        telemetry.update();
        delay(4000);
    }

    @Override
    public void Stop() {
        super.Stop();
    }

}
