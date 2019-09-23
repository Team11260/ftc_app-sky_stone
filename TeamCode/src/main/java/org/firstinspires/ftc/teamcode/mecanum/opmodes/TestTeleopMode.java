package org.firstinspires.ftc.teamcode.mecanum.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractTeleop;
import org.firstinspires.ftc.teamcode.framework.userhardware.DoubleTelemetry;
import org.firstinspires.ftc.teamcode.framework.userhardware.outputs.SlewDcMotor;
import org.firstinspires.ftc.teamcode.framework.userhardware.purepursuit.Path;
import org.firstinspires.ftc.teamcode.framework.userhardware.purepursuit.Point;
import org.firstinspires.ftc.teamcode.framework.util.RobotCallable;
import org.firstinspires.ftc.teamcode.mecanum.hardware.Drive;
import org.upacreekrobotics.dashboard.Config;

@TeleOp(name = "Test Teleop Mode", group = "New")

@Config
public class TestTeleopMode extends AbstractTeleop {

    public static double ARM_DOWN_POSITION = 0.83;
    public static double ARM_UP_POSITION = 0.66;
    public static double GRIPPER_GRIP_POSITION = 0.8;
    public static double GRIPPER_RELEASE_POSITION = 0.35;

    private Drive drive;

    private Servo arm, gripper;
    boolean up = true, gripped = false;

    @Override
    public void RegisterEvents() {
        addEventHandler("1_a_down", () -> toggleArmPosition());
        addEventHandler("1_b_down", () -> toggleGripperPosition());
        addEventHandler("1_x_down", () -> {
            telemetry.addData(DoubleTelemetry.LogMode.INFO, "X pressed");
            telemetry.update();
        });

    }

    @Override
    public void UpdateEvents() {
        double left_stick_x=gamepad1.left_stick_x,left_stick_y = -gamepad1.left_stick_y, right_stick_x = gamepad1.right_stick_x;
        //drive.setDrivePowerAll(left_stick_y-left_stick_x-right_stick_x,left_stick_y+left_stick_x+right_stick_x,left_stick_y+left_stick_x-right_stick_x,left_stick_y-left_stick_x+right_stick_x);
    }

    @Override
    public void Init() {

        arm = hardwareMap.servo.get("arm_servo");
        arm.setDirection(Servo.Direction.FORWARD);
        arm.setPosition(ARM_UP_POSITION);

        gripper = hardwareMap.servo.get("gripper_servo");
        gripper.setDirection(Servo.Direction.FORWARD);
        gripper.setPosition(GRIPPER_RELEASE_POSITION);

        drive = new Drive(hardwareMap);

        drive.follow(new Path(new Point(0, 0), new Point(48, 0)));
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
        if(drive.isFollowing()) {
            drive.update();
        } else {
            drive.setDrivePowerAll(0, 0, 0,0);
        }
        telemetry.addData(DoubleTelemetry.LogMode.INFO, "X: " + drive.getCurrentPosition().getX() + " Y: " + drive.getCurrentPosition().getY());
    }

    @Override
    public void Stop() {

    }
}
