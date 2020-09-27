package org.firstinspires.ftc.teamcode.mecanum.hardware.devices;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.framework.userhardware.DoubleTelemetry;
import org.firstinspires.ftc.teamcode.framework.userhardware.outputs.SlewDcMotor;

import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.RUN_USING_ENCODER;

public class Launcher {

    private boolean isLaunching = false;

    private SlewDcMotor motor1;

    private SlewDcMotor motor2;


    public Launcher(HardwareMap hardwareMap, DoubleTelemetry telemetry) {


        motor1 = new SlewDcMotor(hardwareMap.dcMotor.get("launch1"));
        //motor2 = new SlewDcMotor(hardwareMap.dcMotor.get("launch2"));

        motor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


    }

    public void setMotor1(double power) {
        motor1.setPower(power);
    }

    public void setMotor2(double power) {
        motor2.setPower(power);


    }

    public void setPowerAll(double one, double two) {
        motor1.setPower(one);
        motor2.setPower(two);


    }

    public void toggleLauncher(double power) {
        if (!isLaunching) {
            setMotor1(power);
            isLaunching = true;
        } else {
            setMotor1(0);
            isLaunching = false;
        }


    }

    public double getTicksPerSecond(){
        return motor1.getVelocity();

    }

    public double getRevolutionsPerSecond(){
        return motor1.getVelocity()/28;
    }

    public double getRevolutionsPerMinute(){
        return (motor1.getVelocity()/28)*60;
    }



}
