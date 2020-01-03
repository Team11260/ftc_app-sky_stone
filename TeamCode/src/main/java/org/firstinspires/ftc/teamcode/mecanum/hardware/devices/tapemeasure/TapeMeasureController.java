package org.firstinspires.ftc.teamcode.mecanum.hardware.devices.tapemeasure;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.framework.util.SubsystemController;

public class TapeMeasureController extends SubsystemController {

    TapeMeasure tapeMeasure;


    public TapeMeasureController(){
        tapeMeasure = new TapeMeasure(hardwareMap);
    }

    public void extend(){
        tapeMeasure.extend();

    }

    public void retract(){
        tapeMeasure.retract();

    }

    @Override
    public void update() throws Exception {


    }

    @Override
    public void stop() {
    tapeMeasure.stop();
    }


}
