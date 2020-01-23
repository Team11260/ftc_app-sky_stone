package org.firstinspires.ftc.teamcode.mecanum.hardware.devices.intake;

import org.firstinspires.ftc.teamcode.framework.userhardware.DoubleTelemetry;
import org.firstinspires.ftc.teamcode.framework.util.SubsystemController;
import org.firstinspires.ftc.teamcode.mecanum.hardware.devices.drive.Drive;

public class IntakeController extends SubsystemController {
    Intake intake;
    private boolean isRotating = false;
    private boolean isConveying = false;
    private boolean isReverseRotating = false;
    private boolean isMotorIntaking = false;

    public IntakeController() {
        intake = new Intake(hardwareMap);
    }

    @Override
    public void update() throws Exception {

    }

    public void startRotatingRight() {
        intake.startRotatingRight();
    }

    public void startIntake() {
        intake.startRotatingLeft();
        intake.startRotatingRight();
    }

    public void startReverseIntake() {
        intake.startReverseRotationLeft();
        intake.startReverseRotationRight();
        isRotating = true;
    }

    public void stopIntake() {
        intake.stopRotatingLeft();
        intake.stopRotatingRight();
    }


    public void toggleIntake() {
        if (isRotating)
            stopIntake();
        else
            startIntake();
        isRotating = !isRotating;
    }

    public void toggleReverseRotation() {
        if (isReverseRotating)
            stopIntake();
        else
            startReverseIntake();
        isReverseRotating = !isReverseRotating;
    }

    public void toggleConveyor() {
        if (isConveying)
            intake.stopConveyor();
        else
            intake.startConveyor();
        isConveying = !isConveying;
    }

    public void startReverseConveyor() {
        intake.startReverseConveyor();
        isConveying = true;

    }

    public void toggleReverseConveyor() {
        intake.startReverseConveyor();
    }


    @Override
    public void stop() {


    }
}
