package org.firstinspires.ftc.teamcode.framework.userhardware.inputs.sensors.vision.vuforia;

import org.firstinspires.ftc.teamcode.framework.userhardware.inputs.sensors.vision.SamplePosition;

public class SampleVuforia {
    Vuforia vuforia;

    public SampleVuforia() {
        vuforia = new Vuforia(true);
        vuforia.startTracking("Calc_OT");
    }

    public SamplePosition getSamplePosition() {
        return SamplePosition.UNKNOWN;
    }

    public double[] getPose() {
        return vuforia.getPose();
    }
}
