package org.firstinspires.ftc.teamcode.mecanum.opmodes.auton;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.AnalogInput;

import org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractAuton;
import org.firstinspires.ftc.teamcode.mecanum.hardware.Robot;
import org.firstinspires.ftc.teamcode.mecanum.hardware.util.ParameterFileConfiguration;

@Autonomous(group = "new", name = "Test")

public class Test extends AbstractAuton {




    Robot robot;

    @Override
    public void RegisterStates() {

    }

    @Override
    public void Init() {
        robot = new Robot();

        robot.arm.setArmUpPosition();


        robot.arm.setGripperGripPosition();


        robot.lift.setTiltUp();


        robot.dragger.setDraggerUp();

    }

    @Override
    public void Run() {
        goStraight(1,500);
        goRight();
        goRight();
        goRight();
        goStraight(1,500);
    }



    public void goStraight(double power,int delay){
        robot.setDrivePowerAll(power,power,power,power);
        delay(delay);
        robot.setDrivePowerAll(0,0,0,0);
    }

    public void goRight(){
        robot.setDrivePowerAll(0.3,-0.3,0.3,-0.3);
        delay(1000);
        robot.setDrivePowerAll(0,0,0,0);
    }

    public void goLeft(){
         robot.setDrivePowerAll(0,0.3,0,0.3);
         delay(1000);
         robot.setDrivePowerAll(0,0,0,0);
    }







    @Override
    public void Stop() {
        super.Stop();
    }
}
