package org.firstinspires.ftc.teamcode.mecanum.hardware.devices.led;

import org.firstinspires.ftc.teamcode.framework.util.SubsystemController;

public class LedController extends SubsystemController {


    LedServo led;

    static int cycle;


    public LedController(){
        led = new LedServo(hardwareMap);
        this.ledOff();
        cycle = 0;



    }

    public void ledOff(){
        led.setLed1(0.51);



    }

    public void setGreen(){
        led.setLed1(0.2);



    }

    public void setRed(){
        led.setLed1(0.8);

    }

    public void ledCycle(){
        if(cycle == 0) {
            this.setGreen();
            cycle++;

        }
        else if(cycle == 1){
            this.setRed();
            cycle++;


        }
        else{
            this.ledOff();
            cycle = 0;


        }






    }



    @Override
    public void update() throws Exception {

    }

    @Override
    public void stop() {

    }


}
