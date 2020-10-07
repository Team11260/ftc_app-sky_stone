package org.firstinspires.ftc.teamcode.mecanum.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractTeleop;

import org.firstinspires.ftc.teamcode.mecanum.hardware.BaseRobot;
import org.upacreekrobotics.dashboard.Config;

@TeleOp(name = "TestTeleop Mode", group = "New")
@Config

public class TestTeleop extends AbstractTeleop {

    BaseRobot robotBase;


    public TestTeleop(){

        robotBase = new BaseRobot();
    }

    @Override
    public void RegisterEvents() {

        addEventHandler("1_y_down", ()->robotBase.testDrive(0.7));
        addEventHandler("1_b_down", ()->robotBase.testDrive(-0.7));

    }

    @Override
    public void UpdateEvents() {

    }

    @Override
    public void Init() {

    }

    @Override
    public void Loop() {

    }

    @Override
    public void Stop() {

    }


}
