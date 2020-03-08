package org.firstinspires.ftc.teamcode.mecanum.hardware.devices.capstone;


import org.firstinspires.ftc.teamcode.framework.util.SubsystemController;

public class CapStoneController extends SubsystemController {


    CapStone capstone;
    boolean isOpen;


    public CapStoneController() {
        capstone = new CapStone(hardwareMap);
        isOpen = false;


    }

    @Override
    public void update() throws Exception {

    }

    @Override
    public void stop() {

    }


    public void toggleCapStone() {

        if (isOpen) {
            capstone.setClosed();
            isOpen = false;

        } else {

            capstone.setOpen();
            isOpen = true;
        }


    }


    public void setCapstoneOpen(){
        capstone.setOpen();
        isOpen = true;


    }

    public void setCapstoneClosed(){
        capstone.setClosed();
        isOpen = false;



    }


}
