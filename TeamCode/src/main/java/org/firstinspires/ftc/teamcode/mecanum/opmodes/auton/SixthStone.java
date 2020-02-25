package org.firstinspires.ftc.teamcode.mecanum.opmodes.auton;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractAuton;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.Path;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.PurePursuitSegment;
import org.firstinspires.ftc.teamcode.framework.userhardware.purepursuit.Point;
import org.firstinspires.ftc.teamcode.framework.userhardware.purepursuit.PursuitPath;
import org.firstinspires.ftc.teamcode.mecanum.hardware.Robot;

@Autonomous(group = "new",name = "Sixth Stone")

public class SixthStone extends AbstractAuton {

    Robot robot;

    private int depotPathPeriod = 0;




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

        robot.runDrivePath(driveToDepot());

        robot.isSixthStone();

        delay(100000);

        robot.imageShutDown();





    }




    @Override
    public void Stop() {
        super.Stop();
    }


    protected Path driveToDepot(){
        Path drive = new Path("drive to depot");

        drive.addSegment(new PurePursuitSegment("drive to the blue depot",

                new PursuitPath(
                new Point(0,0),
                new Point(68,0)).setMaxDeceleration(0.005).setMaxAcceleration(0.04).setMaxSpeed(0.6).setMinSpeed(0.1),depotPathPeriod

                ));


        return drive;
    }





}
