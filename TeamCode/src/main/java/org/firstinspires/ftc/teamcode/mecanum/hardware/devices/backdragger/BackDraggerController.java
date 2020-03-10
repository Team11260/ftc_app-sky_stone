package org.firstinspires.ftc.teamcode.mecanum.hardware.devices.backdragger;

import org.firstinspires.ftc.teamcode.framework.util.SubsystemController;

public class BackDraggerController extends SubsystemController {

    BackDragger dragger;

    boolean isUp = true;

    public BackDraggerController(){

        dragger = new BackDragger(hardwareMap);

        setDraggerUp();


    }

    public void setDraggerUp(){
        dragger.setBackDragger(0.7);

        isUp = true;


    }


    public void setDraggerDown(){
        dragger.setBackDragger(0.1);
        isUp = false;


    }

    public void toggleBackDragger(){
        if(isUp)
            setDraggerDown();
        else{
            setDraggerUp();


        }


    }





    @Override
    public void update() throws Exception {

    }

    @Override
    public void stop() {

    }
}
