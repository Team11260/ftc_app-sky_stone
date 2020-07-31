package org.firstinspires.ftc.teamcode.mecanum.opmodes.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractAuton;
import org.firstinspires.ftc.teamcode.mecanum.hardware.devices.ArmServo;
import org.opencv.core.Mat;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvInternalCamera;


@Autonomous(name = "Arm Auton", group = "new")

public class ArmAuton extends AbstractAuton {


    ArmServo arm;

    @Override
    public void RegisterStates() {

    }

    @Override
    public void Init() {

        arm = new ArmServo(hardwareMap);

    }


    @Override
    public void Run(){

        arm.setPosition(0.7);

        delay(1000);

        arm.setPosition(0.3);

        delay(1000);

    }
}
