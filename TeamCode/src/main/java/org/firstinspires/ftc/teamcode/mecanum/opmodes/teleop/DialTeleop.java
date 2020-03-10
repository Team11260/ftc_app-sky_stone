package org.firstinspires.ftc.teamcode.mecanum.opmodes.teleop;

import android.icu.text.IDNA;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.DigitalChannel;

import org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractTeleop;
import org.firstinspires.ftc.teamcode.framework.userhardware.DoubleTelemetry;
import org.firstinspires.ftc.teamcode.mecanum.hardware.Robot;


@TeleOp(name = "Dial Teleop", group = "new")
public class DialTeleop extends AbstractTeleop {


    AnalogInput dial;


    @Override
    public void RegisterEvents() {

    }

    @Override
    public void UpdateEvents() {


    }


    @Override
    public void Init() {

        dial = hardwareMap.get(AnalogInput.class, "sensor_digital");

        //max voltage = 3.3


    }


    @Override
    public void Loop() {

        telemetry.addData(DoubleTelemetry.LogMode.INFO, dial.getVoltage());

        telemetry.addData(DoubleTelemetry.LogMode.INFO, getDialDelay(dial.getVoltage()));


        telemetry.update();

    }


    public int getDialDelay(double volt) {
        double ret = volt / 0.33;

        return (int) ret;


    }


    @Override
    public void Stop() {

    }
}
