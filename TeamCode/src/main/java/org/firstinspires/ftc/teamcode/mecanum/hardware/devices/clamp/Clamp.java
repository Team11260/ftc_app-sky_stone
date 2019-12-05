package org.firstinspires.ftc.teamcode.mecanum.hardware.devices.clamp;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Clamp {

    private Servo clamp;

    public Clamp(HardwareMap hardwareMap){

        clamp = hardwareMap.servo.get("clamp_servo");
        clamp.setDirection(Servo.Direction.FORWARD);

    }

    public void setClampPosition(double position){clamp.setPosition(position);
    }

}
