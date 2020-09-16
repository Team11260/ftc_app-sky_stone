package org.firstinspires.ftc.teamcode.mecanum.opmodes.test;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractTeleop;
import org.firstinspires.ftc.teamcode.framework.userhardware.DoubleTelemetry;
import org.firstinspires.ftc.teamcode.framework.util.RobotCallable;
import org.firstinspires.ftc.teamcode.mecanum.hardware.devices.drive.Drive;
import org.firstinspires.ftc.teamcode.mecanum.hardware.devices.intake.Intake;
import org.upacreekrobotics.classscanner.Config;

import java.util.Locale;

@TeleOp(name = "Test Teleop Sensor", group = "New")
@Disabled

@Config
public class TestTeleopDistanceSensor extends AbstractTeleop {

    public static double ARM_DOWN_POSITION = 0.855;
    public static double ARM_UP_POSITION = 0.5;
    public static double GRIPPER_GRIP_POSITION = 0.8;
    public static double GRIPPER_RELEASE_POSITION = 0.35;

    DigitalChannel digitalTouch;
    boolean boolDigitalPressed = false;

    private Drive drive;
    Intake intake;

    private Servo arm, gripper;
    boolean up = true, gripped = false;
    boolean isRotating = false;
    boolean isConveying= false;
    boolean boolGamepadKeyPressed = false;
    DistanceSensor sensorDistance;

    @Override
    public void RegisterEvents() {
      //  addEventHandler("1_x_down", () -> toggleIntake());
      //  addEventHandler("l_y_down", () -> toggleConveyor());
    }

    @Override
    public void UpdateEvents() {
        double k = 0.5;
        double left_stick_x=gamepad1.left_stick_x,left_stick_y = -gamepad1.left_stick_y, right_stick_x = gamepad1.right_stick_x;
        drive.setDrivePowerAll(k*(left_stick_y+left_stick_x+right_stick_x),k*(left_stick_y-left_stick_x-right_stick_x),
                               k*(left_stick_y-left_stick_x+right_stick_x),k*(left_stick_y+left_stick_x-right_stick_x));
    }


    @Override
    public void Init() {

//        arm = hardwareMap.servo.get("arm_servo");
//        arm.setDirection(Servo.Direction.FORWARD);
//        arm.setPosition(ARM_AUTON_POSITION);
//
//        gripper = hardwareMap.servo.get("gripper_servo");
//        gripper.setDirection(Servo.Direction.FORWARD);
//        gripper.setPosition(GRIPPER_RELEASE_POSITION);

        drive = new Drive(hardwareMap,telemetry);
        intake = new Intake(hardwareMap);

        digitalTouch = hardwareMap.get(DigitalChannel.class, "digital_touch");

         // get a reference to the distance sensor that shares the same name.
        sensorDistance = hardwareMap.get(DistanceSensor.class, "color_distance");


    }

    public void setGripperPosition(double position) {
        gripper.setPosition(position);
    }

    public void setArmPosition(double position) {
        arm.setPosition(position);
    }

    public void toggleConveyor(){
        if (isConveying)
            intake.stopConveyor();
        else
            intake.startConveyor();
        isConveying = !isConveying;
    }

    public void startRotating(){
        intake.startRotatingRight();
        intake.startRotatingLeft();
    }

    public void stopRotating(){
        intake.stopRotatingLeft();
        intake.stopRotatingRight();
    }

    public void toggleRotation() {
        if (isRotating)
            stopRotating();
        else
            startRotating();
        isRotating = !isRotating;

    }

    public void setArmUp() {
        arm.setPosition(ARM_UP_POSITION);
    }

    public void setArmDown() {
        arm.setPosition(ARM_DOWN_POSITION);
    }

    public void setGripperGrip() {
        gripper.setPosition(GRIPPER_GRIP_POSITION);
    }

    public void setGripperRelease() {
        gripper.setPosition(GRIPPER_RELEASE_POSITION);
    }


    public RobotCallable setGripperGripCallable() {
        return () -> setGripperGrip();
    }


    public RobotCallable setGripperReleaseCallable() {
        return () -> setGripperRelease();
    }

    public boolean buttonRevPressed()
    {
        if (digitalTouch.getState() == false) {
           return true;
        }
        else
        {
           return false;
        }
    }

    @Override
    public void Loop() {

        // Forgot to bring the joystick to check, so have to use RevButton

        if ( gamepad1.x && ( boolGamepadKeyPressed == false ))
        {
            toggleRotation();
        }

        if ( gamepad1.y && ( boolGamepadKeyPressed == false))
        {
            toggleConveyor();
        }

        boolGamepadKeyPressed = gamepad1.x || gamepad1.y;

        if ( buttonRevPressed() && ( boolDigitalPressed == false ) )
        {
           toggleRotation();
           toggleConveyor();
        }
        boolDigitalPressed = buttonRevPressed();

        telemetry.addDataPhone(DoubleTelemetry.LogMode.INFO,"Distance (in)",
                String.format(Locale.US, "%.02f", sensorDistance.getDistance(DistanceUnit.INCH)));
         telemetry.update();
      }

    @Override
    public void Stop() {

    }
}
