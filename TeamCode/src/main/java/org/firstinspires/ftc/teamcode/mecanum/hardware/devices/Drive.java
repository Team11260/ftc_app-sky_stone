package org.firstinspires.ftc.teamcode.mecanum.hardware.devices;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractAuton;
import org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractOpMode;
import org.firstinspires.ftc.teamcode.framework.userhardware.DoubleTelemetry;
import org.firstinspires.ftc.teamcode.framework.userhardware.inputs.sensors.IMU;
import org.firstinspires.ftc.teamcode.framework.userhardware.outputs.SlewDcMotor;
import org.firstinspires.ftc.teamcode.framework.userhardware.purepursuit.PurePursuitController;

public class Drive extends PurePursuitController {

    private IMU imu;
    private double lastLeftPosition = 0, lastRightPosition = 0;

    private SlewDcMotor dcMotorFrontLeft;
    private SlewDcMotor dcMotorFrontRight;
    private SlewDcMotor dcMotorBackLeft;
    private SlewDcMotor dcMotorBackRight;


    public Drive(HardwareMap hardwareMap, DoubleTelemetry telemetry){
        super(20,telemetry);

        AbstractOpMode.getOpModeInstance();
        imu = new IMU(hardwareMap);
        //imu = hardwareMap.getImu("imu");

        dcMotorFrontLeft = new SlewDcMotor((hardwareMap.dcMotor.get("front_left")));
        dcMotorFrontRight = new SlewDcMotor((hardwareMap.dcMotor.get("front_right")));
        dcMotorBackLeft = new SlewDcMotor((hardwareMap.dcMotor.get("back_left")));
        dcMotorBackRight = new SlewDcMotor((hardwareMap.dcMotor.get("back_right")));

        dcMotorFrontLeft.setDirection(DcMotor.Direction.FORWARD);
        dcMotorBackLeft.setDirection(DcMotor.Direction.FORWARD);

        dcMotorFrontRight.setDirection(DcMotor.Direction.REVERSE);
        dcMotorBackRight.setDirection(DcMotor.Direction.REVERSE);

        dcMotorFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcMotorBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcMotorFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcMotorBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        dcMotorFrontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        dcMotorBackRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        dcMotorFrontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        dcMotorBackLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        resetPosition();
    }

    public void setDrivePowerAll(double FL, double FR, double BL, double BR) {
        dcMotorFrontLeft.setPower(FL*0.8);
        dcMotorFrontRight.setPower(FR*0.8);
        dcMotorBackLeft.setPower(BL*0.8);
        dcMotorBackRight.setPower(BR*0.8);
        telemetry.addData(DoubleTelemetry.LogMode.INFO,dcMotorFrontLeft.getCurrentPosition(),dcMotorFrontRight.getCurrentPosition());
    }

    @Override
    public double getActualHeadingDegrees() {
        return -imu.getHeading();
    }

    public void resetAngleToZero() {
        imu.resetAngleToZero();

    }

    @Override
    public double getLeftActualPositionInches() {
        return dcMotorFrontLeft.getCurrentPosition()/163.0;
    }

    @Override
    public double getRightActualPositionInches() {
        return dcMotorFrontRight.getCurrentPosition()/163.0;
    }

    public int getLeftPosition() {
        return dcMotorFrontLeft.getCurrentPosition();
    }

    public int getRightPosition() {
        return dcMotorFrontRight.getCurrentPosition();
    }

    @Override
    public void setPowers(double l, double r) {
        dcMotorFrontLeft.setPower(l);
        dcMotorBackLeft.setPower(l);
        dcMotorFrontRight.setPower(r);
        dcMotorBackRight.setPower(r);
    }

    public void setMode(DcMotor.RunMode mode) {
        if(mode == DcMotor.RunMode.STOP_AND_RESET_ENCODER) encodersZero();
        dcMotorFrontLeft.setMode(mode);
        dcMotorBackLeft.setMode(mode);
        dcMotorFrontRight.setMode(mode);
        dcMotorBackRight.setMode(mode);
    }
    public void encodersZero() {
       lastLeftPosition = 0;
       lastRightPosition = 0;
    }
}
