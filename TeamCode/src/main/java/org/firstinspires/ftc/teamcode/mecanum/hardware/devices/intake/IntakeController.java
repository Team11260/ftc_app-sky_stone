package org.firstinspires.ftc.teamcode.mecanum.hardware.devices.intake;

import org.firstinspires.ftc.teamcode.framework.util.SubsystemController;
import org.firstinspires.ftc.teamcode.mecanum.hardware.devices.intake.Intake;

public class IntakeController extends SubsystemController {
    Intake intake;
    private boolean isRotating = false;
    private boolean isConveying = false;

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
    }
    public void stopIntake(){
        intake.stopRotatingLeft();
        intake.stopRotatingRight();
    }

    public void toggleRotation(){
        if (isRotating)
            stopIntake();
        else
            startIntake();
        isRotating = !isRotating;
    }

    public void toggleConveyor(){
        if (isConveying)
            intake.stopConveyor();
        else
            intake.startConveyor();
        isConveying = !isConveying;
    }


    @Override
    public void stop() {


    }
}
