package org.firstinspires.ftc.teamcode.mecanum.hardware.devices.intake;

import org.firstinspires.ftc.teamcode.framework.util.SubsystemController;
import org.firstinspires.ftc.teamcode.mecanum.hardware.devices.intake.Intake;

public class IntakeController extends SubsystemController {
    Intake intake;
    private boolean isRotating = false;
    private boolean isConveying = false;
    private boolean isReverseRotating = false;
    private boolean isReverseConveying = false;

    public IntakeController() {
        intake = new Intake(hardwareMap);
    }

    @Override
    public void update() throws Exception {

    }

    public void startRotatingRight(){
        intake.startRotatingRight();
    }

    public void startIntake() {
        intake.startRotatingLeft();
        intake.startRotatingRight();
        isRotating = true;
    }
    public void startReverseIntake(){
        intake.startReverseRotationLeft();
        intake.startREverseRotationRight();
        isRotating = true;
    }
    public void stopIntake(){
        intake.stopRotatingLeft();
        intake.stopRotatingRight();
        isRotating = false;
    }


    public void toggleRotation(){
        if (isRotating)
            stopIntake();
        else
            startIntake();
        isRotating = !isRotating;
    }

    public void toggleReverseRotation(){
        if (isReverseRotating)
            stopIntake();
        else
            startReverseIntake();
        isReverseRotating = !isReverseRotating;
    }

    public void toggleConveyor(){
        if (isConveying)
            intake.stopConveyor();
        else
            intake.startConveyor();
        isConveying = !isConveying;
    }
    public void toggleReverseConveyor(){
        if (isReverseConveying)
            intake.stopConveyor();
        else
            intake.startReverseConveyor();
        isReverseConveying = !isReverseConveying;
    }





    @Override
    public void stop() {


    }
}
