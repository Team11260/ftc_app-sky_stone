package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractTeleop;
import org.firstinspires.ftc.teamcode.framework.userhardware.DoubleTelemetry;

import java.util.ArrayList;

public class RobotTest extends AbstractTeleop {

    private String type = "motor";

    private ArrayList<DcMotor> allDcMotors;

    @Override
    public void RegisterEvents() {
        addEventHandler("1_dpu_down", () -> {
            switch (type) {
                case "motor":
                    type = "servo";
                    break;
                case "servo":
                    type = "motor";
                    break;
            }
        });

        addEventHandler("1_dpd_down", () -> {
            switch (type) {
                case "motor":
                    type = "servo";
                    break;
                case "servo":
                    type = "motor";
                    break;
            }
        });
    }

    @Override
    public void UpdateEvents() {

    }

    @Override
    public void Init() {
        allDcMotors = (ArrayList<DcMotor>) hardwareMap.getAll(DcMotor.class);
    }

    @Override
    public void Loop() {
        telemetry.addDataPhone(DoubleTelemetry.LogMode.INFO, "Type", type);
        telemetry.update();

        telemetry.getSmartdashboard().putValue("Type", type);
    }

    @Override
    public void Stop() {

    }
}
