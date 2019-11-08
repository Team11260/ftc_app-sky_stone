package org.firstinspires.ftc.teamcode.mecanum.hardware.devices.drive;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractOpMode;
import org.firstinspires.ftc.teamcode.framework.userhardware.inputs.sensors.IMU;
import org.firstinspires.ftc.teamcode.framework.userhardware.outputs.SlewDcMotor;
import org.firstinspires.ftc.teamcode.mecanum.hardware.Constants;

public class Drive {

    private IMU imu;

    private SlewDcMotor frontLeft;
    private SlewDcMotor frontRight;
    private SlewDcMotor backLeft;
    private SlewDcMotor backRight;

    private double lastPosition = 0.0;
    private long lastVelocityTime = 0;

    private double lastVelocity = 0.0;
    private long lastAccelerationTime = 0;

    public Drive(HardwareMap hardwareMap){
        AbstractOpMode.getOpModeInstance();
        imu = new IMU(hardwareMap);

        frontLeft = new SlewDcMotor((hardwareMap.dcMotor.get("front_left")));
        frontRight = new SlewDcMotor((hardwareMap.dcMotor.get("front_right")));
        backLeft = new SlewDcMotor((hardwareMap.dcMotor.get("back_left")));
        backRight = new SlewDcMotor((hardwareMap.dcMotor.get("back_right")));

        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);

        frontRight.setDirection(DcMotor.Direction.FORWARD);
        backRight.setDirection(DcMotor.Direction.FORWARD);

        resetPosition();

        setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public double getStraightPosition() {
        return backRight.getCurrentPosition() / Constants.DRIVE_STRAIGHT_COUNTS_PER_INCH;
    }

    public double getStraightVelocity() {
        double position = getStraightPosition();
        long time = System.currentTimeMillis();

        double velocity = (position - lastPosition) / ((time - lastVelocityTime) / 1000.0);

        lastPosition = position;
        lastVelocityTime = time;

        return velocity / 70;
    }

    public double getStrafePosition() {
        return backLeft.getCurrentPosition() / Constants.DRIVE_STRAFE_COUNTS_PER_INCH;
    }

    public double getHeading() {
        return imu.getHeading();
    }

    public void setPower(double fl, double fr, double bl, double br) {
        frontLeft.setPower(fl);
        frontRight.setPower(fr);
        backLeft.setPower(bl);
        backRight.setPower(br);
    }

    public void setPower(double l, double r) {
        setPower(l, r, l, r);
    }

    public void setPower(double p) {
        setPower(p, p);
    }

    public void setMode(DcMotor.RunMode mode) {
        frontLeft.setMode(mode);
        frontRight.setMode(mode);
        backLeft.setMode(mode);
        backRight.setMode(mode);
    }

    public void resetPosition() {
        setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void resetHeading() {
        imu.resetAngleToZero();
    }
}
