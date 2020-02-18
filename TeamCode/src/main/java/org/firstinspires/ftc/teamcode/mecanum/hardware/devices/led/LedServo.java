package org.firstinspires.ftc.teamcode.mecanum.hardware.devices.led;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class LedServo {
    private Servo led1;


    public LedServo(HardwareMap hardwareMap){



        led1 = hardwareMap.servo.get("led_one");



    }


    public void setLed1(double position){


        led1.setPosition(position);


    }





}
