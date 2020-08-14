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

    private SlewDcMotor dcMotorBackLeft;
    private SlewDcMotor dcMotorBackRight;


    public Drive(HardwareMap hardwareMap, DoubleTelemetry telemetry){
        super(20,telemetry);

        AbstractOpMode.getOpModeInstance();
        imu = new IMU(hardwareMap);


        dcMotorBackLeft = new SlewDcMotor((hardwareMap.dcMotor.get("B-L")));
        dcMotorBackRight = new SlewDcMotor((hardwareMap.dcMotor.get("B-R")));



        dcMotorBackLeft.setDirection(DcMotor.Direction.FORWARD);


        dcMotorBackRight.setDirection(DcMotor.Direction.REVERSE);


        dcMotorBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        dcMotorBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        dcMotorBackRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        dcMotorBackLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        resetPosition();
    }

    public void setDrivePowerAll(double BL, double BR) {

        dcMotorBackLeft.setPower(BL*0.8);
        dcMotorBackRight.setPower(BR*0.8);

    }

    @Override
    public double getActualHeadingDegrees() {
        return -imu.getHeading();
    }

    @Override
    public double getLeftActualPositionInches() {
        return dcMotorBackLeft.getCurrentPosition()/163.0;
    }

    @Override
    public double getRightActualPositionInches() {
        return dcMotorBackLeft.getCurrentPosition()/163.0;
    }

    @Override
    public void setPowers(double l, double r) {

        dcMotorBackLeft.setPower(l);

        dcMotorBackRight.setPower(r);
    }
}
