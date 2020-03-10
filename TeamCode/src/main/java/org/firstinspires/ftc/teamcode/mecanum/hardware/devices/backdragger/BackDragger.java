package org.firstinspires.ftc.teamcode.mecanum.hardware.devices.backdragger;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.framework.util.SubsystemController;

public class BackDragger {

    private Servo dragger;

    public BackDragger(HardwareMap hardwareMap){

        dragger = hardwareMap.servo.get("led_one");
        dragger.setDirection(Servo.Direction.FORWARD);


    }


    public void setBackDragger(double pos){
        dragger.setPosition(pos);


    }







}
