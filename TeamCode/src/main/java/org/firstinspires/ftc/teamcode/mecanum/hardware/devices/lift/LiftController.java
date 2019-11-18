package org.firstinspires.ftc.teamcode.mecanum.hardware.devices.lift;

import org.firstinspires.ftc.teamcode.framework.util.SubsystemController;

public class LiftController extends SubsystemController {

    Lift lift;

    public LiftController() {
        lift = new Lift(hardwareMap);
    }

    @Override
    public void update() throws Exception {

    }

    public void setLiftUp() {
        lift.setLiftTargetPosition(1234);
    }

    public void setLiftDown() {
        lift.setLiftTargetPosition(0);
    }

    public void setTiltDown() {
        lift.setTiltPosition(0);
    }

    public void setTiltUp() {
        lift.setTiltPosition(0.5);
    }

    public void setGrabberOpen() {
        lift.setTiltPosition(0.5);
    }

    public void setGrabberClose() {
        lift.setTiltPosition(0);
    }

    public void setSlideout() {
        lift.setSlidePosition(0.5);
    }

    public void setPanWide() {
        lift.setTiltPosition(0);
    }

    public void setPanShort() {
        lift.setPanPosition(0.5);
    }

    public int getStraightPosition() {
        return lift.getStraightPosition();
    }

    public int getStrafePosition() {
        return lift.getStrafePosition();
    }

    @Override
    public void stop() {

    }
}
