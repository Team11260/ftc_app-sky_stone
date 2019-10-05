package org.firstinspires.ftc.teamcode.framework.userhardware.inputs.sensors.vision.tensorflow;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractOpMode;
import org.firstinspires.ftc.teamcode.framework.userhardware.DoubleTelemetry;
import org.firstinspires.ftc.teamcode.framework.userhardware.inputs.sensors.vision.tensorflow.support.TensorFlow;
import org.firstinspires.ftc.teamcode.framework.userhardware.inputs.sensors.vision.vuforia.VuforiaImpl;

import java.util.List;

import static org.firstinspires.ftc.robotcore.external.tfod.TfodRoverRuckus.LABEL_GOLD_MINERAL;
import static org.firstinspires.ftc.robotcore.external.tfod.TfodRoverRuckus.LABEL_SILVER_MINERAL;
import static org.firstinspires.ftc.robotcore.external.tfod.TfodRoverRuckus.TFOD_MODEL_ASSET;

public class TensorFlowImpl {

    protected DoubleTelemetry telemetry = AbstractOpMode.getTelemetry();

    private VuforiaImpl vuforia;

    private TFObjectDetector tfod;

    private TensorFlowRoverRuckus.CameraOrientation orientation;

    public TensorFlowImpl() {
        this(TensorFlowRoverRuckus.CameraOrientation.VERTICAL);
    }

    public TensorFlowImpl(TensorFlowRoverRuckus.CameraOrientation cameraOrientation) {
        this(cameraOrientation, true);
    }

    public TensorFlowImpl(TensorFlowRoverRuckus.CameraOrientation cameraOrientation, String camera) {
        this(cameraOrientation, camera, true);
    }

    public TensorFlowImpl(TensorFlowRoverRuckus.CameraOrientation cameraOrientation, boolean led) {
        this(cameraOrientation, "BACK", led);
    }

    public TensorFlowImpl(TensorFlowRoverRuckus.CameraOrientation cameraOrientation, String camera, boolean led) {
        orientation = cameraOrientation;

        do {
            vuforia = new VuforiaImpl(camera, false);
        } while (vuforia.getVuforia().getCameraCalibration() == null);

        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTfodWithViewer();
        } else {
            telemetry.addData("Sorry!", "This device is not compatible with TFOD");
            telemetry.update();
        }
    }

    public TensorFlowImpl(TensorFlowRoverRuckus.CameraOrientation cameraOrientation, String camera, boolean led, boolean viewer) {
        orientation = cameraOrientation;

        do {
            vuforia = new VuforiaImpl(camera, false);
        } while (vuforia.getVuforia().getCameraCalibration() == null);

        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTfodWithoutViewer();
        } else {
            telemetry.addData("Sorry!", "This device is not compatible with TFOD");
            telemetry.update();
        }
    }

    private void initTfodWithViewer() {
        int tfodMonitorViewId = AbstractOpMode.getOpModeInstance().hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", AbstractOpMode.getOpModeInstance().hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minimumConfidence = 0.25;
        tfodParameters.useObjectTracker = false;
        tfod = new TensorFlow(tfodParameters, vuforia.getVuforia());
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
    }

    private void initTfodWithoutViewer() {
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters();
        tfodParameters.minimumConfidence = 0.25;
        tfodParameters.useObjectTracker = false;
        tfod = new TensorFlow(tfodParameters, vuforia.getVuforia());
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
    }

    public boolean isSetup() {
        return tfod != null;
    }

    public CameraOrientation getOrientation() {
        return orientation;
    }

    public void start() {
        tfod.activate();
    }

    public void restart() {
        tfod.deactivate();
        tfod.activate();
    }

    public void pause() {
        tfod.deactivate();
    }

    public void stop() {
        tfod.shutdown();
    }

    public void setLED(boolean on) {
        vuforia.setLED(on);
    }

    public List<Recognition> getUpdatedRecognitions() {
        return tfod.getUpdatedRecognitions();
    }

    public List<Recognition> getRecognitions() {
        return tfod.getRecognitions();
    }

    public enum CameraOrientation {
        VERTICAL,
        HORIZONTAL
    }
}
