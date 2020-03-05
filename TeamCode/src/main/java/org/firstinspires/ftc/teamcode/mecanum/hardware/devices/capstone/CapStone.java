package org.firstinspires.ftc.teamcode.mecanum.hardware.devices.capstone;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class CapStone {

    private Servo capstone;

    public CapStone(HardwareMap hardwareMap){

        capstone = hardwareMap.servo.get("capstone");



        capstone.setDirection(Servo.Direction.FORWARD);


    }



    public void setOpen(){

        capstone.setPosition(0.75);



    }




    public void setClosed(){

        capstone.setPosition(0.5);


    }





}
