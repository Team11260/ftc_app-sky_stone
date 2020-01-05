package org.firstinspires.ftc.teamcode.mecanum.hardware.devices.dragger;

import org.firstinspires.ftc.teamcode.framework.util.SubsystemController;

import static org.firstinspires.ftc.teamcode.mecanum.hardware.Constants.BACK_DRAGGER_DOWN_POSITION;
import static org.firstinspires.ftc.teamcode.mecanum.hardware.Constants.BACK_DRAGGER_HALFWAY_POSITION;
import static org.firstinspires.ftc.teamcode.mecanum.hardware.Constants.BACK_DRAGGER_UP_POSITION;
import static org.firstinspires.ftc.teamcode.mecanum.hardware.Constants.FRONT_DRAGGER_DOWN_POSITION;
import static org.firstinspires.ftc.teamcode.mecanum.hardware.Constants.FRONT_DRAGGER_HALFWAY_POSITION;
import static org.firstinspires.ftc.teamcode.mecanum.hardware.Constants.FRONT_DRAGGER_UP_POSITION;

public class DraggerController extends SubsystemController {

    Dragger dragger;

    boolean draggerUp = true;


    public DraggerController() {
        dragger = new Dragger(hardwareMap);
        setBackUp();
        setFrontUp();
    }

    public void setBackUp() {
        dragger.setBackDraggerPosition(BACK_DRAGGER_UP_POSITION);

    }

    public void setFrontUp() {
        dragger.setFrontDraggerPosition(FRONT_DRAGGER_UP_POSITION);
    }

    public void setBackDown() {
        dragger.setBackDraggerPosition(BACK_DRAGGER_DOWN_POSITION);

    }

    public void setFrontDown() {
        dragger.setFrontDraggerPosition(FRONT_DRAGGER_DOWN_POSITION);
    }

    public void setFrontHalfway() {
        dragger.setFrontDraggerPosition(FRONT_DRAGGER_HALFWAY_POSITION);
    }

    public void setBackHalfway() {
        dragger.setBackDraggerPosition(BACK_DRAGGER_HALFWAY_POSITION);
    }


    public void setDraggerDown() {
        setFrontDown();
        setBackDown();
    }

    public void setDraggerUp() {
        setFrontUp();
        setBackUp();
    }

    public void setDraggerHalf(){
        setFrontHalfway();
        setBackHalfway();

    }

    public void toggleDragger() {
        if (draggerUp)
            setDraggerDown();
        else
            setDraggerUp();
        draggerUp = !draggerUp;
    }

    public void toggleDraggerHalf(){
        if(draggerUp)
            setDraggerDown();
        else
            setDraggerHalf();
        draggerUp = !draggerUp;


    }

    @Override
    public void update() throws Exception {

    }

    @Override
    public void stop() {

    }
}
