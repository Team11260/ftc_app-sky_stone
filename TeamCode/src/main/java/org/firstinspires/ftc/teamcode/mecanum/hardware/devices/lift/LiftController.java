package org.firstinspires.ftc.teamcode.mecanum.hardware.devices.lift;

import org.firstinspires.ftc.teamcode.framework.util.SubsystemController;

import static org.firstinspires.ftc.teamcode.mecanum.hardware.Constants.*;


public class LiftController extends SubsystemController {

    public Lift lift;

    boolean isGrabbed = true;
    boolean isTilted = false;
    boolean isSlideOut = false;
    int panCyclePosition = 0;

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

    public void toggleTilt() {
        if (isTilted)
            setTiltUp();
        else
            setTiltDown();
        isTilted = !isTilted;
    }

    public void setGrabberOpen() {
        lift.setGrabberPosition(GRABBER_OPEN);
    }

    public void setGrabberClose() {
        lift.setGrabberPosition(GRABBER_CLOSE);
    }

    public void toggleGrabber() {
        if (isGrabbed)
            setGrabberOpen();
        else
            setGrabberClose();
        isGrabbed = !isGrabbed;
    }

    public void setSlideIn() {
        lift.setSlidePosition(SLIDE_IN);
    }

    public void setSlideOutHalf() {
        lift.setSlidePosition(SLIDE_OUT_HALF);
    }

    public void setSlideOut() {
        lift.setSlidePosition(SLIDE_OUT);
    }

    public void toggleSlide(){
        if(isSlideOut)
            setSlideIn();
        else
            setSlideOut();
        isSlideOut= !isSlideOut;
    }

    public void setPanRight() {
        lift.setPanPosition(PAN_RIGHT);
    }

    public void setPanMiddle() {
        lift.setPanPosition(PAN_MIDDLE);
    }

    public void setPanLeft() {
        lift.setPanPosition(PAN_LEFT);
    }

    public void cyclePan(){
        if (panCyclePosition==1){
            setPanRight();
            panCyclePosition=2;
        }
        else if(panCyclePosition==2){
            setPanMiddle();
            panCyclePosition=0;
        }
        else if (panCyclePosition==0){
            setPanLeft();
            panCyclePosition=1;
        }
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
