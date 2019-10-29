package org.firstinspires.ftc.teamcode.mecanum.hardware.devices;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.upacreekrobotics.dashboard.Config;

@Config
public class Intake {
     Servo rightIntake;
     Servo conveyorBelt;
     Servo leftIntake;



     public static double rightPosition = 0.8, leftPosition = 0.8;
     public static double beltPosition = 1.0;


    public Intake(HardwareMap hardwareMap){
        rightIntake = hardwareMap.servo.get("right_intake");
        rightIntake.setDirection(Servo.Direction.FORWARD);
        rightIntake.scaleRange(0.0,1.0);

        conveyorBelt = hardwareMap.servo.get("conveyor_belt");
        conveyorBelt.setDirection(Servo.Direction.REVERSE);
        conveyorBelt.scaleRange(0.0,1.0);

        leftIntake = hardwareMap.servo.get("left_intake");
        leftIntake.setDirection(Servo.Direction.REVERSE);
        leftIntake.scaleRange(0.0,1.0);

    }

    public void startRotatingLeft(){
        leftIntake.setPosition(leftPosition);

    }
    public void startRotatingRight() {

        rightIntake.setPosition(rightPosition);
    }

    public void stopRotatingLeft(){
        leftIntake.setPosition(0.5);
    }

    public void stopRotatingRight(){
        rightIntake.setPosition(0.5);

    }

    public void startConveyor(){
        conveyorBelt.setPosition(beltPosition);
    }

    public void stopConveyor() {
        conveyorBelt.setPosition(0.5);
    }



}
