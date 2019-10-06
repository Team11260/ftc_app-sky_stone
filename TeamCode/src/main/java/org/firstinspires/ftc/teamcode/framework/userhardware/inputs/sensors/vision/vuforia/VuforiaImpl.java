package org.firstinspires.ftc.teamcode.framework.userhardware.inputs.sensors.vision.vuforia;

import android.graphics.Bitmap;

import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;

public class VuforiaImpl {

    public Vuforia vuforia;

    public VuforiaImpl(boolean viewer, boolean led) {
        vuforia = new Vuforia(viewer, led);
    }

    public VuforiaImpl(String camera, boolean viewer, boolean led) {
        vuforia = new Vuforia(camera, viewer);
    }

    public VuforiaImpl(boolean viewer) {
        this(viewer, false);
    }

    public VuforiaImpl(String camera, boolean viewer) {
        this(camera, viewer, true);
    }

    public VuforiaLocalizer getVuforia() {
        return vuforia.getVuforia();
    }

    public void setLED(boolean on) {
        vuforia.setLED(on);
    }

    public Bitmap getImage() {
        return vuforia.getImage();
    }

    public int getRotation() {
        return vuforia.getRotation();
    }
}
