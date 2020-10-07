package org.firstinspires.ftc.teamcode.mecanum.hardware.devices.TestMotor;
import org.firstinspires.ftc.teamcode.framework.userhardware.outputs.SlewDcMotor;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
public class TestMotor {

    private SlewDcMotor testMotor;

    public TestMotor(HardwareMap hardwareMap){

        testMotor = new SlewDcMotor(hardwareMap.dcMotor.get("B-L"));

        testMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void setTestMotorPower(double power) {
        testMotor.setPower(power);
    }


}
