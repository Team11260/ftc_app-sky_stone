package org.firstinspires.ftc.teamcode.mecanum.hardware.devices.tapemeasure;

import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class TapeMeasure {
    Servo tapeMeasure;

    public TapeMeasure(HardwareMap hardwareMap){
        tapeMeasure = hardwareMap.servo.get("clamp_servo");
        tapeMeasure.setDirection(Servo.Direction.FORWARD);
    }

    public void extend(){
        tapeMeasure.setPosition(1);

    }

    public void retract(){
        tapeMeasure.setPosition(0);

    }

    public void stop(){
        tapeMeasure.setPosition(0.5);

    }


}
