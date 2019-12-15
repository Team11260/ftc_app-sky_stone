package org.firstinspires.ftc.teamcode.mecanum.hardware.devices.dragger;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Dragger {

    private Servo leftDragger, rightDragger;

    public Dragger(HardwareMap hardwareMap){

        leftDragger = hardwareMap.servo.get("left_dragger_servo");
        rightDragger = hardwareMap.servo.get("right_dragger_servo");
        leftDragger.setDirection(Servo.Direction.FORWARD);
        rightDragger.setDirection(Servo.Direction.FORWARD);
    }

    public void setLeftDraggerPosition(double position) { leftDragger.setPosition(position);}
    public void setRightDraggerPosition(double position) { rightDragger.setPosition(position);}
}
