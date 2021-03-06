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

    private SlewDcMotor dcMotorFrontLeft;
    private SlewDcMotor dcMotorFrontRight;
//    private SlewDcMotor dcMotorBackLeft;
//    private SlewDcMotor dcMotorBackRight;


    public Drive(HardwareMap hardwareMap, DoubleTelemetry telemetry){
        super(20,telemetry);

        AbstractOpMode.getOpModeInstance();
        imu = new IMU(hardwareMap);

        dcMotorFrontLeft = new SlewDcMotor((hardwareMap.dcMotor.get("front_left")));
        dcMotorFrontRight = new SlewDcMotor((hardwareMap.dcMotor.get("front_right")));
//        dcMotorBackLeft = new SlewDcMotor((hardwareMap.dcMotor.get("back_left")));
//        dcMotorBackRight = new SlewDcMotor((hardwareMap.dcMotor.get("back_right")));
        dcMotorFrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        dcMotorFrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        dcMotorFrontLeft.setDirection(DcMotor.Direction.FORWARD);
        //dcMotorBackLeft.setDirection(DcMotor.Direction.FORWARD);

        dcMotorFrontRight.setDirection(DcMotor.Direction.REVERSE);
        //dcMotorBackRight.setDirection(DcMotor.Direction.REVERSE);

        dcMotorFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //dcMotorBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcMotorFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //dcMotorBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        dcMotorFrontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        dcMotorBackRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        dcMotorFrontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        dcMotorBackLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        resetPosition();
    }

    public void setDrivePowerAll(double FL, double FR, double BL, double BR) {
        dcMotorFrontLeft.setPower(FL*0.8);
        dcMotorFrontRight.setPower(FR*0.8);
       // dcMotorBackLeft.setPower(BL*0.8);
       // dcMotorBackRight.setPower(BR*0.8);
        telemetry.addData(DoubleTelemetry.LogMode.INFO,dcMotorFrontLeft.getCurrentPosition(),dcMotorFrontRight.getCurrentPosition());
    }

    public void setDrivePower(double L, double R){
        dcMotorFrontLeft.setPower(L);
        dcMotorFrontRight.setPower(R);
    }

    @Override
    public double getActualHeadingDegrees() {
        return -imu.getHeading();
    }

    @Override
    public double getLeftActualPositionInches() {
        return dcMotorFrontLeft.getCurrentPosition()/163.0;
    }

    @Override
    public double getRightActualPositionInches() {
        return dcMotorFrontRight.getCurrentPosition()/163.0;
    }

    @Override
    public void setPowers(double l, double r) {
        dcMotorFrontLeft.setPower(l);
        //dcMotorBackLeft.setPower(l);
        dcMotorFrontRight.setPower(r);
        //dcMotorBackRight.setPower(r);
    }
}
