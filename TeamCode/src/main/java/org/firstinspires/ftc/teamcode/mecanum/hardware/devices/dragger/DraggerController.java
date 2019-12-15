package org.firstinspires.ftc.teamcode.mecanum.hardware.devices.dragger;

import org.firstinspires.ftc.teamcode.framework.util.SubsystemController;

import static org.firstinspires.ftc.teamcode.mecanum.hardware.Constants.BACK_DRAGGER_DOWN_POSITION;
import static org.firstinspires.ftc.teamcode.mecanum.hardware.Constants.BACK_DRAGGER_UP_POSITION;
import static org.firstinspires.ftc.teamcode.mecanum.hardware.Constants.FRONT_DRAGGER_DOWN_POSITION;
import static org.firstinspires.ftc.teamcode.mecanum.hardware.Constants.FRONT_DRAGGER_UP_POSITION;

public class DraggerController extends SubsystemController {

    Dragger backDragger;
    Dragger frontDragger;

    boolean backUp = true, FrontUp = true;

    public DraggerController(){
        backDragger = new Dragger(hardwareMap);
        frontDragger = new Dragger(hardwareMap);
    }

    public void setBackUp(){
        backDragger.setBackDraggerPosition(BACK_DRAGGER_UP_POSITION);
        backUp = true;
    }

    public void setFrontUp(){
        frontDragger.setFrontDraggerPosition(FRONT_DRAGGER_UP_POSITION);
        FrontUp = true;
    }
    public void setBackDown(){
        backDragger.setBackDraggerPosition(BACK_DRAGGER_DOWN_POSITION);
        backUp = false;
    }
    public void setFrontDown(){
        frontDragger.setFrontDraggerPosition(FRONT_DRAGGER_DOWN_POSITION);
        FrontUp = false;
    }
    public void toggleBoth(){
        if (FrontUp && backUp){
          setFrontDown();
          setBackDown();
        }
        else if (!FrontUp && !backUp){
            setBackUp();
            setFrontUp();
        }
        else{
            setFrontDown();
            setBackDown();
        }
    }

    @Override
    public void update() throws Exception {

    }

    @Override
    public void stop() {

    }
}
