
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
import org.firstinspires.ftc.teamcode.framework.userhardware.purepursuit.Pose;
import org.firstinspires.ftc.teamcode.mecanum.hardware.Robot;
import org.firstinspires.ftc.teamcode.mecanum.hardware.RobotState;
import org.upacreekrobotics.dashboard.Config;

import static org.firstinspires.ftc.teamcode.mecanum.hardware.Constants.*;

@Config
public class Drive extends MecanumPurePursuitController {

    private IMU imu;
    private double lastLeftPosition = 0, lastRightPosition = 0;
    private double strafeOffset = 0.0, straightOffset = 0.0;
    public static double p=16,i=0,d=0,f=12;
    private double ENCODER_COUNTS_INCH = 19.0;

    private SlewDcMotor dcMotorFrontLeft;
    private SlewDcMotor dcMotorFrontRight;
    private SlewDcMotor dcMotorBackLeft;
    private SlewDcMotor dcMotorBackRight;
    private SlewDcMotor straightEncoder,strafeEncoder;

    public Drive(HardwareMap hardwareMap, DoubleTelemetry telemetry) {
        //super(20, 1.4, new PIDController(50, 0, 100), telemetry);
        super(20, 1.4, new PIDController(10, 0, 5), telemetry);

        imu = new IMU(hardwareMap);
        //imu = hardwareMap.getImu("imu");

        dcMotorBackLeft = new SlewDcMotor((hardwareMap.dcMotor.get("B-L")));
        dcMotorBackRight = new SlewDcMotor((hardwareMap.dcMotor.get("B-R")));

        dcMotorBackLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        dcMotorBackRight.setDirection(DcMotorSimple.Direction.REVERSE);

        dcMotorBackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        dcMotorBackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        setSlewRate(2.0);  //Increase slew rate from default 0.1 to 2.0 speed change in 15 ms

        setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        resetPosition();

        setMode(RobotState.MatchState.AUTONOMOUS == RobotState.currentMatchState ? DcMotor.RunMode.RUN_USING_ENCODER : DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        //setVelocityPIDCoefficients(new PIDFCoefficients(p,i,d,f));
    }

    public void setLeftMotorIntakePower(double power){
        //straightEncoder.setPower(power);
    }


    public void setRightMotorIntakePower(double power){

        //strafeEncoder.setPower(-power);
    }

    public void setDrivePowerTwo(double BL, double BR){
        dcMotorBackLeft.setPower(BL);
        dcMotorBackRight.setPower(BR);

    }

    public void setDrivePowerAll(double FL, double FR, double BL, double BR) {
       // dcMotorFrontLeft.setPower(FL);
        //dcMotorFrontRight.setPower(FR);
        dcMotorBackLeft.setPower(BL);
        dcMotorBackRight.setPower(BR);
        //telemetry.addData(DoubleTelemetry.LogMode.INFO, dcMotorFrontLeft.getCurrentPosition(), dcMotorFrontRight.getCurrentPosition());
    }

    public void resetAngleToZero() {
        imu.resetAngleToZero();
    }

    public Pose getCurrentPosition(){
        return currentPosition;
    }


    @Override
    public double getActualHeadingDegrees() {
        return imu.getHeading();
    }

    @Override
    public double getXActualPositionInches() {
        return getBackLeftPosition();
    }

    @Override
    public double getYActualPositionInches() {
        return getBackLeftPosition();
    }

    public void setHeadingMode(HeadingMode headingMode){
        super.setHeadingMode(headingMode);
    }

    public int getFrontLeftPosition() {
        return dcMotorBackLeft.getCurrentPosition();
    }

    public int getFrontRightPosition() {
        //Negative for the odometry wheels
       return dcMotorBackRight.getCurrentPosition();
    }

    public  int getBackLeftPosition(){
        return dcMotorBackLeft.getCurrentPosition();
    }

    public  int getBackRightPosition() {
        return dcMotorBackRight.getCurrentPosition();
    }

    public  double getStraightPosition(){
        return (((double)(getBackLeftPosition())/ENCODER_COUNTS_INCH));
    }

    public  double getStrafePosition(){
        return ((double)(getBackRightPosition()));
    }

    @Override
    public void setMecanumPower(double fl, double fr, double bl, double br) {
      //  setDrivePowerAll(fl, fr, bl, br);
    }//

    public void setMode(DcMotor.RunMode mode) {
        if (mode == DcMotor.RunMode.STOP_AND_RESET_ENCODER) encodersZero();
        //dcMotorFrontLeft.setMode(mode);
        dcMotorBackLeft.setMode(mode);
        //dcMotorFrontRight.setMode(mode);
        dcMotorBackRight.setMode(mode);
        //strafeEncoder.setMode(mode);
      //  straightEncoder.setMode(mode);
    }

    public void setVelocityPIDCoefficients(PIDFCoefficients pidfCoefficients){
        //dcMotorFrontLeft.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER,pidfCoefficients);
        dcMotorBackLeft.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER,pidfCoefficients);
        //dcMotorFrontRight.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER,pidfCoefficients);
        dcMotorBackRight.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER,pidfCoefficients);
        }

    public void resetEncoders(){
        setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void encodersZero() {
        lastLeftPosition = 0;
        lastRightPosition = 0;
        //straightOffset = 0;
        //straightOffset = getStraightPosition();
        //strafeOffset = 0;
        //strafeOffset = getStrafePosition();
    }

    public void setSlewRate(double slewSpeed){
        dcMotorBackLeft.setSlewSpeed(slewSpeed);
        dcMotorBackRight.setSlewSpeed(slewSpeed);
        //dcMotorFrontLeft.setSlewSpeed(slewSpeed);
        //dcMotorFrontRight.setSlewSpeed(slewSpeed);
    }

    public double getHeading() {
        return imu.getHeading();
    }

    public void stop() {
        setDrivePowerTwo(0, 0);
    }
}
