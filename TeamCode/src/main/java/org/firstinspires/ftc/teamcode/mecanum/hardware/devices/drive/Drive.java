package org.firstinspires.ftc.teamcode.mecanum.hardware.devices.drive;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.MotorControlAlgorithm;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import org.firstinspires.ftc.teamcode.framework.userhardware.DoubleTelemetry;
import org.firstinspires.ftc.teamcode.framework.userhardware.PIDController;
import org.firstinspires.ftc.teamcode.framework.userhardware.inputs.sensors.IMU;
import org.firstinspires.ftc.teamcode.framework.userhardware.outputs.SlewDcMotor;
import org.firstinspires.ftc.teamcode.framework.userhardware.purepursuit.MecanumPurePursuitController;
import org.firstinspires.ftc.teamcode.mecanum.hardware.Robot;
import org.firstinspires.ftc.teamcode.mecanum.hardware.RobotState;
import org.upacreekrobotics.dashboard.Config;

import static org.firstinspires.ftc.teamcode.mecanum.hardware.Constants.*;

@Config
public class Drive extends MecanumPurePursuitController {

    private IMU imu;
    private double lastLeftPosition = 0, lastRightPosition = 0;
    private double strafeOffset = 0.0, straightOffset = 0.0;
    public static double p=16,i=0,d=0,f=16;

    private SlewDcMotor dcMotorFrontLeft;
    private SlewDcMotor dcMotorFrontRight;
    private SlewDcMotor dcMotorBackLeft;
    private SlewDcMotor dcMotorBackRight;
    private SlewDcMotor straightEncoder,strafeEncoder;

    public Drive(HardwareMap hardwareMap, DoubleTelemetry telemetry) {
        //super(20, 1.4, new PIDController(50, 0, 100), telemetry);
        super(20, 1.4, new PIDController(25, 0, 50), telemetry);

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
        dcMotorFrontRight.setDirection(DcMotorSimple.Direction.FORWARD);
        dcMotorBackRight.setDirection(DcMotorSimple.Direction.FORWARD);

        dcMotorFrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        dcMotorFrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        dcMotorBackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        dcMotorBackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        setSlewRate(2.0);  //Increase slew rate from default 0.1 to 2.0 speed change in 15 ms

        setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        resetPosition();

        setMode(RobotState.isAutonomous() ? DcMotor.RunMode.RUN_USING_ENCODER : DcMotor.RunMode.RUN_USING_ENCODER);

        setVelocityPIDCoefficients(new PIDFCoefficients(p,i,d,f));
    }

    public void setDrivePowerAll(double FL, double FR, double BL, double BR) {
        dcMotorFrontLeft.setPower(FL);
        dcMotorFrontRight.setPower(FR);
        dcMotorBackLeft.setPower(BL);
        dcMotorBackRight.setPower(BR);
        //telemetry.addData(DoubleTelemetry.LogMode.INFO, dcMotorFrontLeft.getCurrentPosition(), dcMotorFrontRight.getCurrentPosition());
    }

    public void resetAngleToZero() {
        imu.resetAngleToZero();

    }

    @Override
    public double getActualHeadingDegrees() {
        return imu.getHeading();
    }

    @Override
    public double getXActualPositionInches() {
        return getStraightPosition();
    }

    @Override
    public double getYActualPositionInches() {
        return getStrafePosition();
    }

    public int getFrontLeftPosition() {
        return dcMotorFrontLeft.getCurrentPosition();
    }

    public int getFrontRightPosition() {
        //Negative for the odometry wheels
        return dcMotorFrontRight.getCurrentPosition();
    }

    public  int getBackLeftPosition(){
        return dcMotorBackLeft.getCurrentPosition();
    }

    public  int getBackRightPosition() {
        return dcMotorBackRight.getCurrentPosition();
    }

    public  double getStraightPosition(){

        return (((double)(-straightEncoder.getCurrentPosition())/STRAIGHT_ENCODER_COUNTS_INCH));
    }

    public  double getStrafePosition(){
        return (((double)(strafeEncoder.getCurrentPosition()))/STRAFE_ENCODER_COUNTS_INCH);
    }

    @Override
    public void setMecanumPower(double fl, double fr, double bl, double br) {
        setDrivePowerAll(fl, fr, bl, br);
    }

    public void setMode(DcMotor.RunMode mode) {
        if (mode == DcMotor.RunMode.STOP_AND_RESET_ENCODER) encodersZero();
        dcMotorFrontLeft.setMode(mode);
        dcMotorBackLeft.setMode(mode);
        dcMotorFrontRight.setMode(mode);
        dcMotorBackRight.setMode(mode);
        strafeEncoder.setMode(mode);
        straightEncoder.setMode(mode);
    }

    public void setVelocityPIDCoefficients(PIDFCoefficients pidfCoefficients){
        dcMotorFrontLeft.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER,pidfCoefficients);
        dcMotorBackLeft.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER,pidfCoefficients);
        dcMotorFrontRight.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER,pidfCoefficients);
        dcMotorBackRight.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER,pidfCoefficients);
        }

    public void resetEncoders(){
        setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void encodersZero() {
        lastLeftPosition = 0;
        lastRightPosition = 0;
        straightOffset = 0;
        straightOffset = getStraightPosition();
        strafeOffset = 0;
        strafeOffset = getStrafePosition();
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
