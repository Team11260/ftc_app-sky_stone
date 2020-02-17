package org.firstinspires.ftc.teamcode.mecanum.hardware;

import org.firstinspires.ftc.teamcode.framework.util.AbstractRobot;
import org.firstinspires.ftc.teamcode.mecanum.hardware.devices.drive.DriveController;

public class BigRobot extends AbstractRobot {


    public DriveController driver;

    public BigRobot(){

        driver = new DriveController();



    }

    public double getBigX(){
        return driver.getCurrentPosition().getX();


    }

    public double getBigY(){
        return driver.getCurrentPosition().getY();


    }

    public double getBigHeading(){
        return driver.getHeading();


    }













    public void stop(){

        driver.stop();



    }
}
