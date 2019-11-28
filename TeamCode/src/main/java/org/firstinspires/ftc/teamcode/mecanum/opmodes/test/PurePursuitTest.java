package org.firstinspires.ftc.teamcode.mecanum.opmodes.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractAuton;
import org.firstinspires.ftc.teamcode.framework.userhardware.DoubleTelemetry;
import org.firstinspires.ftc.teamcode.framework.userhardware.purepursuit.PursuitPath;
import org.firstinspires.ftc.teamcode.framework.util.PathState;
import org.firstinspires.ftc.teamcode.mecanum.hardware.Robot;






import static org.firstinspires.ftc.teamcode.mecanum.hardware.Constants.*;


@Autonomous(name = "PurePursuitTest", group = "Test")
//@Disabled

public class PurePursuitTest extends AbstractAuton {

    private Robot robot;

    @Override
    public void RegisterStates() {
        addState(new PathState("Delayed Arm Down", "start", robot.armDownCallable()));
        addState(new PathState("Grab first stone", "drive to center sky stone", robot.grabStoneCallable()));
        addState(new PathState("Deliver first stone", "first trip to foundation", robot.deliverStoneCallable()));
        addState(new PathState("Grab first stone", "drive to left sky stone", robot.grabStoneCallable()));
        addState(new PathState("Grab first stone", "drive to right sky stone", robot.grabStoneCallable()));

        addState(new PathState("Deliver first stone", "first trip to foundation", robot.deliverStoneCallable()));

        addState(new PathState("Delayed Arm Down", "Deliver first stone", robot.delayedArmDownCallable()));
        addState(new PathState("Grab second stone", "drive to second center sky stone", robot.grabStoneCallable()));
        addState(new PathState("Grab second stone", "drive to second right sky stone", robot.grabStoneCallable()));
        addState(new PathState("Grab second stone", "drive to second left sky stone", robot.grabStoneCallable()));



        addState(new PathState("Deliver second stone", "second trip to foundation", robot.deliverStoneCallable()));
        addState(new PathState("Delayed Arm Down", "Deliver second stone", robot.delayedArmDownCallable()));
        addState(new PathState("Grab third stone", "drive to third center sky stone", robot.grabStoneCallable()));
        addState(new PathState("Deliver third stone", "third trip to foundation", robot.deliverStoneCallable()));


        //addState(new PathState("Delayed Arm Down", "Deliver second stone", robot.delayedArmDownCallable()));


        //addState(new PathState("Delayed Arm Down", "test2", robot.delayedArmDownSecondCallable()));

    }

    @Override
    public void Init() {
        robot = new Robot();
        //robot.setArmDown();
        robot.setGripperRelease();
    }


    @Override
    public void Run() {

        robot.runDrivePath(testPurePursuitLeft);
        //centerBlockAuton();
        telemetry.addData(DoubleTelemetry.LogMode.INFO,"All done " );
        telemetry.update();
        delay(9000);
    }

    public void centerBlockAuton(){

        robot.runTestPurePursuit(configurePath(goToFirstBlockCenter));
        robot.grabStone();
        robot.runTestPurePursuit(configurePath(goToFoundationCenter));
        robot.deliverStone();
        /*AbstractAuton.addFinishedState("test");
        robot.runTestPurePursuit(configurePath(goToSecondBlockCenter));
        robot.grabBlock();
        robot.runTestPurePursuit(configurePath(goBackToFoundationCenter));
        robot.deliverBlock();
        AbstractAuton.addFinishedState("test2");
        robot.runTestPurePursuit(configurePath(goToThirdBlockCenter));
        robot.grabBlock();
        robot.runTestPurePursuit(configurePath(goBackToFoundationAgainCenter));
        robot.deliverBlock();*/
        telemetry.addData(DoubleTelemetry.LogMode.INFO,"All done " );
        telemetry.update();
        delay(9000);
    }

    /*public void leftBlockAuton(){

        robot.runTestPurePursuit(configurePath(goToFirstBlockLeft));
        robot.grabBlock();
        robot.runTestPurePursuit(configurePath(goToFoundationLeft));
        robot.deliverBlock();
        AbstractAuton.addFinishedState("test");
        robot.runTestPurePursuit(configurePath(goToSecondBlockLeft));
        robot.grabBlock();
        robot.runTestPurePursuit(configurePath(goBackToFoundationLeft));
        robot.deliverBlock();
        AbstractAuton.addFinishedState("test2");
        robot.runTestPurePursuit(configurePath(goToThirdBlockLeft));
        robot.grabBlock();
        robot.runTestPurePursuit(configurePath(goBackToFoundationAgainLeft));
        robot.deliverBlock();
        telemetry.addData(DoubleTelemetry.LogMode.INFO,"All done " );
        telemetry.update();
        delay(9000);
    }*/
   /* public void rightBlockAuton(){

        robot.runTestPurePursuit(configurePath(goToFirstBlockRight));
        robot.grabBlock();
        robot.runTestPurePursuit(configurePath(goToFoundationRight));
        robot.deliverBlock();
        AbstractAuton.addFinishedState("test");
        robot.runTestPurePursuit(configurePath(goToSecondBlockRight));
        robot.grabBlock();
        robot.runTestPurePursuit(configurePath(goBackToFoundationRight));
        robot.deliverBlock();
        AbstractAuton.addFinishedState("test2");
        robot.runTestPurePursuit(configurePath(goToThirdBlockRight));
        robot.grabBlock();
        robot.runTestPurePursuit(configurePath(goBackToFoundationAgainRight));
        robot.deliverBlock();
        /*AbstractAuton.addFinishedState("test2");
        robot.runTestPurePursuit(configurePath(goToFourthBlockRight));
        robot.grabBlock();
        robot.runTestPurePursuit(configurePath(goToFoundationLeft));*/
        /*telemetry.addData(DoubleTelemetry.LogMode.INFO,"All done " );
        telemetry.update();
        delay(9000);
    }*/

    public PursuitPath configurePath(PursuitPath path) {
        path.setMaxSpeed(1.4);
        path.setTurnSpeed(1);
        path.setTrackingErrorSpeed(5.0);
        path.setLookAheadDistance(5);
        path.setVelocityLookAheadPoints(8);
        path.setMaxAcceleration(0.015);
        path.setTurnErrorScalar(0);

        return path;
    }
}
