package org.firstinspires.ftc.teamcode.mecanum.hardware.devices.lift;

import org.firstinspires.ftc.teamcode.framework.util.SubsystemController;

import static org.firstinspires.ftc.teamcode.mecanum.hardware.Constants.*;


public class LiftController extends SubsystemController {

    Lift lift;

    public boolean isTilted = false;

    public LiftController() {
        lift = new Lift(hardwareMap);
    }

    @Override
    public void update() throws Exception {

    }

    public void setLiftUp() {
        lift.setLiftTargetPosition(LIFT_UP);
    }

    public void setLiftDown() {
        lift.setLiftTargetPosition(LIFT_DOWN);
    }

    public void setTiltDown() {
        lift.setTiltPosition(TILT_DOWN);
    }

    public void setTiltUp() {
        lift.setTiltPosition(TILT_UP);
    }


    public void setGrabberOpen() {
        lift.setGrabberPosition(GRABBER_OPEN);
    }

    public void setGrabberClose() {
        lift.setGrabberPosition(GRABBER_CLOSE);
    }

    public void setSlideout() {
        lift.setSlidePosition(SLIDE_OUT);
    }

    public void setPanWide() {
        lift.setPanPosition(PAN_WIDE);
    }

    public void setPanShort() {
        lift.setPanPosition(PAN_SHORT);
    }

    public int getStraightPosition() {
        return lift.getStraightPosition();
    }

    public int getStrafePosition() {
        return lift.getStrafePosition();
    }


    public void toggleTilt() {
        lift.setTiltPosition(isTilted ? 0.5 : 0);
        isTilted= !isTilted;
    }

    @Override
    public void stop() {

    }
}
