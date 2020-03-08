package org.firstinspires.ftc.teamcode.mecanum.hardware.devices.intake;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PwmControl;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoControllerEx;

import org.upacreekrobotics.dashboard.Config;

@Config
public class Intake {
     Servo rightIntake;
     Servo conveyorBelt;
     Servo leftIntake;

     public static double rightPosition = 1.0, leftPosition = 1.0;
     public static double beltPosition = 1.0;
     public static double rightReversePosition = 0, leftReversePosition = 0;
     public static double beltReversePosition = 0;


    public Intake(HardwareMap hardwareMap){
//        rightIntake = hardwareMap.servo.get("right_intake");
//        rightIntake.setDirection(Servo.Direction.FORWARD);
        //ServoControllerEx theController = (ServoControllerEx) (rightIntake.getController());
        //rightIntake.getController();
        //int thePort = rightIntake.getPortNumber();
        //PwmControl.PwmRange theRange = new PwmControl.PwmRange(553,2500);
        //theController.setServoPwmRange(thePort,theRange);


        conveyorBelt = hardwareMap.servo.get("conveyor_belt");
        conveyorBelt.setDirection(Servo.Direction.REVERSE);

//        leftIntake = hardwareMap.servo.get("left_intake");
//        leftIntake.setDirection(Servo.Direction.REVERSE);
    }

    public void startRotatingLeft(){
        leftIntake.setPosition(leftPosition);

    }
    public void startRotatingRight() {

        rightIntake.setPosition(rightPosition);
    }
    public void startReverseRotationLeft(){
        leftIntake.setPosition(leftReversePosition);
    }
    public void startReverseRotationRight(){
        rightIntake.setPosition(rightReversePosition);
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
    public void startReverseConveyor(){
        conveyorBelt.setPosition(beltReversePosition);
    }

    public void stopConveyor() {
        conveyorBelt.setPosition(0.5);
    }



}
