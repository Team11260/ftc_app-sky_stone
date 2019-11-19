package org.firstinspires.ftc.teamcode.mecanum.hardware.devices.drive;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractOpMode;
import org.firstinspires.ftc.teamcode.framework.userhardware.DoubleTelemetry;
import org.firstinspires.ftc.teamcode.framework.userhardware.inputs.sensors.IMU;
import org.firstinspires.ftc.teamcode.framework.userhardware.outputs.SlewDcMotor;
import org.firstinspires.ftc.teamcode.framework.userhardware.purepursuit.PurePursuitController;

import static org.firstinspires.ftc.teamcode.framework.userhardware.DoubleTelemetry.LogMode.INFO;

public class Drive extends PurePursuitController {

    private IMU imu;
    private double lastLeftPosition = 0, lastRightPosition = 0;
    private double strafeOffset = 0.0, straightOffset = 0.0;

    private SlewDcMotor dcMotorFrontLeft;
    private SlewDcMotor dcMotorFrontRight;
    private SlewDcMotor dcMotorBackLeft;
    private SlewDcMotor dcMotorBackRight;
    private SlewDcMotor straightEncoder,strafeEncoder;

    //private double STRAIGHT_ENCODER_COUNTS_INCH = 189.0;
    private double STRAIGHT_ENCODER_COUNTS_INCH = 189.0;

    public Drive(HardwareMap hardwareMap, DoubleTelemetry telemetry) {
        super(20, telemetry);

        imu = new IMU(hardwareMap);
        //imu = hardwareMap.getImu("imu");

        dcMotorFrontLeft = new SlewDcMotor((hardwareMap.dcMotor.get("front_left")));
        dcMotorFrontRight = new SlewDcMotor((hardwareMap.dcMotor.get("front_right")));
        dcMotorBackLeft = new SlewDcMotor((hardwareMap.dcMotor.get("back_left")));
        dcMotorBackRight = new SlewDcMotor((hardwareMap.dcMotor.get("back_right")));

        straightEncoder = new SlewDcMotor(hardwareMap.dcMotor.get("straight_encoder"));
        strafeEncoder = new SlewDcMotor(hardwareMap.dcMotor.get("strafe_encoder"));

        dcMotorFrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        dcMotorBackLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        dcMotorFrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        dcMotorFrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        dcMotorBackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        dcMotorBackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        setSlewRate(2.0);  //Increase slew rate from default 0.1 to 2.0 speed change in 15 ms

        setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        resetPosition();
    }

    public void setDrivePowerAll(double FL, double FR, double BL, double BR) {
        dcMotorFrontLeft.setPower(FL);
        dcMotorFrontRight.setPower(FR);
        dcMotorBackLeft.setPower(BL);
        dcMotorBackRight.setPower(BR);
        //telemetry.addData(DoubleTelemetry.LogMode.INFO, dcMotorFrontLeft.getCurrentPosition(), dcMotorFrontRight.getCurrentPosition());
    }

    @Override
    public double getActualHeadingDegrees() {
        return imu.getHeading();
    }

    public void resetAngleToZero() {
        imu.resetAngleToZero();

    }

    @Override
    public double getLeftActualPositionInches() {
        //return dcMotorFrontLeft.getCurrentPosition() / 163.0;
        return getStraightPosition()/STRAIGHT_ENCODER_COUNTS_INCH;
    }

    @Override
    public double getRightActualPositionInches() {
        return getStraightPosition()/STRAIGHT_ENCODER_COUNTS_INCH;
    }

    public int getFrontLeftPosition() {
        return -dcMotorFrontLeft.getCurrentPosition();
    }

    public int getFrontRightPosition() {
        //Negative for the odometry wheels
        return -dcMotorFrontRight.getCurrentPosition();
    }

    public  int getBackLeftPosition(){
        return dcMotorBackLeft.getCurrentPosition();
    }

    public  int getBackRightPosition() {
        return dcMotorBackRight.getCurrentPosition();
    }

    public  double getStraightPosition(){

        return (((double)(straightEncoder.getCurrentPosition())/STRAIGHT_ENCODER_COUNTS_INCH));
    }

    public  double getStrafePosition(){
        return (((double)(-strafeEncoder.getCurrentPosition()))/STRAIGHT_ENCODER_COUNTS_INCH);
    }

    @Override
    public void setPower(double l, double r) {
        dcMotorFrontLeft.setPower(l);
        dcMotorBackLeft.setPower(l);
        dcMotorFrontRight.setPower(r);
        dcMotorBackRight.setPower(r);
    }

    public void setMode(DcMotor.RunMode mode) {
        if (mode == DcMotor.RunMode.STOP_AND_RESET_ENCODER) encodersZero();
        dcMotorFrontLeft.setMode(mode);
        dcMotorBackLeft.setMode(mode);
        dcMotorFrontRight.setMode(mode);
        dcMotorBackRight.setMode(mode);
    }

    public void resetEncoders(){
        setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void encodersZero() {
        lastLeftPosition = 0;
        lastRightPosition = 0;
        straightOffset = 0;
        straightOffset = getStraightPosition()/STRAIGHT_ENCODER_COUNTS_INCH;
        strafeOffset = 0;
        strafeOffset = getStrafePosition()/STRAIGHT_ENCODER_COUNTS_INCH;
    }

    public void setSlewRate(double slewSpeed){
        dcMotorBackLeft.setSlewSpeed(slewSpeed);
        dcMotorBackRight.setSlewSpeed(slewSpeed);
        dcMotorFrontLeft.setSlewSpeed(slewSpeed);
        dcMotorFrontRight.setSlewSpeed(slewSpeed);
    }

    public double getHeading() {
        return imu.getHeading();
    }

    public void stop() {
        setDrivePowerAll(0, 0, 0, 0);
    }
}