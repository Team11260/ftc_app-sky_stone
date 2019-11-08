package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractTeleop;

@TeleOp(name = "Dashboard Test", group = "test")
//@Disabled

public class DashboardTest extends AbstractTeleop {
    @Override
    public void RegisterEvents() {

    }

    @Override
    public void UpdateEvents() {

    }

    @Override
    public void Init() {
        telemetry.getSmartdashboard().putButton("test");
        telemetry.getSmartdashboard().putSlider("number", 0, 10);
    }

    @Override
    public void Loop() {
        telemetry.getSmartdashboard().putBoolean("button", telemetry.getSmartdashboard().getButton("test"));
    }

    @Override
    public void Stop() {

    }
}
