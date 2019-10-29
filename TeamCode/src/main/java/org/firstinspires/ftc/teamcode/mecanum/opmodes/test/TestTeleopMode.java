package org.firstinspires.ftc.teamcode.mecanum.opmodes.test;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractTeleop;
import org.firstinspires.ftc.teamcode.framework.userhardware.DoubleTelemetry;
import org.firstinspires.ftc.teamcode.framework.userhardware.purepursuit.Path;
import org.firstinspires.ftc.teamcode.framework.userhardware.purepursuit.Point;
import org.firstinspires.ftc.teamcode.framework.util.RobotCallable;
import org.firstinspires.ftc.teamcode.mecanum.hardware.devices.Drive;
import org.firstinspires.ftc.teamcode.mecanum.hardware.devices.Intake;
import org.firstinspires.ftc.teamcode.mecanum.hardware.devices.IntakeController;
import org.upacreekrobotics.dashboard.Config;

import static org.firstinspires.ftc.teamcode.framework.userhardware.purepursuit.Path.k;

@TeleOp(name = "Test Teleop Mode", group = "New")

@Config
public class TestTeleopMode extends AbstractTeleop {

    public static double ARM_DOWN_POSITION = 0.855;
    public static double ARM_UP_POSITION = 0.5;
    public static double GRIPPER_GRIP_POSITION = 0.8;
    public static double GRIPPER_RELEASE_POSITION = 0.35;

    private Drive drive;
    Intake intake;

    private Servo arm, gripper;
    boolean up = true, gripped = false;
    boolean isRotating = false;
    boolean isConveying= false;

    @Override
    public void RegisterEvents() {
        addEventHandler("1_x_down", () -> toggleRotation());
        addEventHandler("l_y_down", () -> toggleConveyor());
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
//        arm.setPosition(ARM_UP_POSITION);
//
//        gripper = hardwareMap.servo.get("gripper_servo");
//        gripper.setDirection(Servo.Direction.FORWARD);
//        gripper.setPosition(GRIPPER_RELEASE_POSITION);

        drive = new Drive(hardwareMap,telemetry);
        intake = new Intake(hardwareMap);
    }

    public void setGripperPosition(double position) {
        gripper.setPosition(position);
    }

    public void setArmPosition(double position) {
        arm.setPosition(position);
    }

    public void toggleArmPosition() {
        setArmPosition(up ? ARM_DOWN_POSITION : ARM_UP_POSITION);
        up = !up;
    }

    public void toggleGripperPosition() {
        setGripperPosition(gripped ? GRIPPER_GRIP_POSITION : GRIPPER_RELEASE_POSITION);
        gripped = !gripped;
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

    @Override
    public void Loop() {
      }

    @Override
    public void Stop() {

    }
}
