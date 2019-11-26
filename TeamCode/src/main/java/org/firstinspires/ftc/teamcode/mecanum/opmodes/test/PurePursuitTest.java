package org.firstinspires.ftc.teamcode.mecanum.opmodes.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractAuton;
import org.firstinspires.ftc.teamcode.framework.userhardware.DoubleTelemetry;
import org.firstinspires.ftc.teamcode.framework.userhardware.purepursuit.PursuitPath;
import org.firstinspires.ftc.teamcode.framework.util.PathState;
import org.firstinspires.ftc.teamcode.mecanum.hardware.Robot;

import static org.firstinspires.ftc.teamcode.mecanum.hardware.Constants.goBackToFoundation;
import static org.firstinspires.ftc.teamcode.mecanum.hardware.Constants.goBackToFoundationAgain;
import static org.firstinspires.ftc.teamcode.mecanum.hardware.Constants.goBackToFoundationAgainLeft;
import static org.firstinspires.ftc.teamcode.mecanum.hardware.Constants.goBackToFoundationAgainRight;
import static org.firstinspires.ftc.teamcode.mecanum.hardware.Constants.goBackToFoundationLeft;
import static org.firstinspires.ftc.teamcode.mecanum.hardware.Constants.goBackToFoundationRight;
import static org.firstinspires.ftc.teamcode.mecanum.hardware.Constants.goToFirstBlock;
import static org.firstinspires.ftc.teamcode.mecanum.hardware.Constants.goToFirstBlockLeft;
import static org.firstinspires.ftc.teamcode.mecanum.hardware.Constants.goToFirstBlockRight;
import static org.firstinspires.ftc.teamcode.mecanum.hardware.Constants.goToFoundation;
import static org.firstinspires.ftc.teamcode.mecanum.hardware.Constants.goToFoundationLeft;
import static org.firstinspires.ftc.teamcode.mecanum.hardware.Constants.goToFoundationRight;
import static org.firstinspires.ftc.teamcode.mecanum.hardware.Constants.goToFourthBlockRight;
import static org.firstinspires.ftc.teamcode.mecanum.hardware.Constants.goToSecondBlock;
import static org.firstinspires.ftc.teamcode.mecanum.hardware.Constants.goToSecondBlockLeft;
import static org.firstinspires.ftc.teamcode.mecanum.hardware.Constants.goToSecondBlockRight;
import static org.firstinspires.ftc.teamcode.mecanum.hardware.Constants.goToThirdBlock;
import static org.firstinspires.ftc.teamcode.mecanum.hardware.Constants.goToThirdBlockLeft;
import static org.firstinspires.ftc.teamcode.mecanum.hardware.Constants.goToThirdBlockRight;

@Autonomous(name = "PurePursuitTest", group = "Test")
//@Disabled

public class PurePursuitTest extends AbstractAuton {

    private Robot robot;

    @Override
    public void RegisterStates() {
        addState(new PathState("Delayed Arm Down", "start", robot.armDownCallable()));
        addState(new PathState("Delayed Arm Down", "test", robot.delayedArmDownCallable()));
        addState(new PathState("Delayed Arm Down", "test2", robot.delayedArmDownSecondCallable()));

    }

    @Override
    public void Init() {
        robot = new Robot();
        //robot.setArmDown();
        robot.setGripperRelease();
    }

    @Override
    public void Run() {

        leftBlockAuton();

    }

    public void centerBlockAuton(){

        robot.runTestPurePursuit(configurePath(goToFirstBlock));
        robot.grabBlock();
        robot.runTestPurePursuit(configurePath(goToFoundation));
        robot.deliverBlock();
        AbstractAuton.addFinishedState("test");
        robot.runTestPurePursuit(configurePath(goToSecondBlock));
        robot.grabBlock();
        robot.runTestPurePursuit(configurePath(goBackToFoundation));
        robot.deliverBlock();
        AbstractAuton.addFinishedState("test2");
        robot.runTestPurePursuit(configurePath(goToThirdBlock));
        robot.grabBlock();
        robot.runTestPurePursuit(configurePath(goBackToFoundationAgain));
        robot.deliverBlock();
        telemetry.addData(DoubleTelemetry.LogMode.INFO,"All done " );
        telemetry.update();
        delay(9000);
    }

    public void leftBlockAuton(){

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
    }
    public void rightBlockAuton(){

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
        telemetry.addData(DoubleTelemetry.LogMode.INFO,"All done " );
        telemetry.update();
        delay(9000);
    }

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
