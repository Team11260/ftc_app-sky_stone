package org.firstinspires.ftc.teamcode.mecanum.opmodes.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractAuton;
import org.firstinspires.ftc.teamcode.mecanum.hardware.devices.ArmServo;


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
    public void Run() {


        arm.setPosition(1);
        delay(500);

        arm.setPosition(1);

        delay(1000);

    }
}
