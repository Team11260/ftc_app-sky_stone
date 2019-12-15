package org.firstinspires.ftc.teamcode.mecanum.hardware.devices.dragger;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Dragger {

    private Servo backDragger, frontDragger;

    public Dragger(HardwareMap hardwareMap){

        backDragger = hardwareMap.servo.get("back_dragger_servo");
        frontDragger = hardwareMap.servo.get("front_dragger_servo");
        backDragger.setDirection(Servo.Direction.FORWARD);
        frontDragger.setDirection(Servo.Direction.FORWARD);
    }

    public void setBackDraggerPosition(double position) { backDragger.setPosition(position);}
    public void setFrontDraggerPosition(double position) { frontDragger.setPosition(position);}
}

