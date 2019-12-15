package org.firstinspires.ftc.teamcode.mecanum.hardware.devices.dragger;

import org.firstinspires.ftc.teamcode.framework.util.SubsystemController;

import static org.firstinspires.ftc.teamcode.mecanum.hardware.Constants.LEFT_DRAGGER_DOWN_POSITION;
import static org.firstinspires.ftc.teamcode.mecanum.hardware.Constants.LEFT_DRAGGER_UP_POSITION;
import static org.firstinspires.ftc.teamcode.mecanum.hardware.Constants.RIGHT_DRAGGER_DOWN_POSITION;
import static org.firstinspires.ftc.teamcode.mecanum.hardware.Constants.RIGHT_DRAGGER_UP_POSITION;

public class DraggerController extends SubsystemController {

    Dragger leftDragger;
    Dragger rightDragger;

    boolean leftUp = true, rightUp = true;

    public DraggerController(){
        leftDragger = new Dragger(hardwareMap);
        rightDragger = new Dragger(hardwareMap);
    }

    public void setLeftUp(){
        leftDragger.setLeftDraggerPosition(LEFT_DRAGGER_UP_POSITION);
        leftUp = true;
    }

    public void setRightUp(){
        rightDragger.setRightDraggerPosition(RIGHT_DRAGGER_UP_POSITION);
        rightUp = true;
    }
    public void setLeftDown(){
        leftDragger.setLeftDraggerPosition(LEFT_DRAGGER_DOWN_POSITION);
        leftUp = false;
    }
    public void setRightDown(){
        rightDragger.setRightDraggerPosition(RIGHT_DRAGGER_DOWN_POSITION);
        rightUp = false;
    }
    public void toggleBoth(){
        if (rightUp && leftUp){
          setRightDown();
          setLeftDown();
        }
        else if (!rightUp && !leftUp){
            setLeftUp();
            setRightUp();
        }
        else{
            setRightDown();
            setLeftDown();
        }
    }

    @Override
    public void update() throws Exception {

    }

    @Override
    public void stop() {

    }
}
