package org.firstinspires.ftc.teamcode.mecanum.hardware.devices;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class ArmServo {




    private Servo servo;



    public ArmServo(HardwareMap hardwareMap){

       servo = hardwareMap.servo.get("Servo");

    }


    public void setPosition(double position){

        servo.setPosition(position);

    }



}
