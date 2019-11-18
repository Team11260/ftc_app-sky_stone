package org.firstinspires.ftc.teamcode.mecanum.hardware.devices.lift;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.framework.userhardware.outputs.SlewDcMotor;

public class Lift {
    private SlewDcMotor lift;
    private SlewDcMotor straightEncoder,strafeEncoder;

    private Servo tilt,pan,slide,grabber;

    public Lift(HardwareMap hardwareMap){
        lift = new SlewDcMotor(hardwareMap.dcMotor.get("lift"));

        tilt = hardwareMap.servo.get("tilt_servo");
        tilt.setDirection(Servo.Direction.FORWARD);

        pan = hardwareMap.servo.get("pan_servo");
        pan.setDirection(Servo.Direction.FORWARD);

        slide = hardwareMap.servo.get("slide_servo");
        slide.setDirection(Servo.Direction.FORWARD);

        grabber = hardwareMap.servo.get("grabber_servo");
        grabber.setDirection(Servo.Direction.FORWARD);

        straightEncoder = new SlewDcMotor(hardwareMap.dcMotor.get("straight_encoder"));
        strafeEncoder = new SlewDcMotor(hardwareMap.dcMotor.get("strafe_encoder"));
    }

    public void setLiftTargetPosition(int position){
        lift.setTargetPosition(position);
    }

    public int getLiftPosition(){
        return lift.getCurrentPosition();
    }

    public void setTiltPosition(double position){
        tilt.setPosition(position);
    }

    public void setPanPosition(double position){
        pan.setPosition(position);
    }
    public void setSlidePosition(double position){
        slide.setPosition(position);
    }
    public void setGrabberPosition(double position){
        grabber.setPosition(position);
    }

    public int getStraightPosition(){
        return straightEncoder.getCurrentPosition();
    }

    public int getStrafePosition(){
        return strafeEncoder.getCurrentPosition();
    }
}
