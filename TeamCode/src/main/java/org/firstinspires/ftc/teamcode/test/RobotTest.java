package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractTeleop;
import org.firstinspires.ftc.teamcode.framework.userhardware.DoubleTelemetry;
import org.firstinspires.ftc.teamcode.framework.userhardware.inputs.sensors.vision.tensorflow.TensorFlowImpl;
import org.firstinspires.ftc.teamcode.framework.userhardware.inputs.sensors.vision.tensorflow.support.TensorFlow;
import org.tensorflow.lite.TensorFlowLite;

import java.util.ArrayList;
import java.util.LinkedList;

@TeleOp(name = "RobotTest", group = "Teleop")

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
        allDcMotors = new ArrayList<>(hardwareMap.getAll(DcMotor.class));
        telemetry.addData(allDcMotors.toString());
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
