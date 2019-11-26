package org.firstinspires.ftc.teamcode.mecanum.opmodes.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractAuton;
import org.firstinspires.ftc.teamcode.framework.util.PathState;
import org.firstinspires.ftc.teamcode.mecanum.hardware.Robot;

import static org.firstinspires.ftc.teamcode.mecanum.hardware.Constants.goBackToFoundation;
import static org.firstinspires.ftc.teamcode.mecanum.hardware.Constants.goBackToFoundationAgain;
import static org.firstinspires.ftc.teamcode.mecanum.hardware.Constants.goToFirstBlock;
import static org.firstinspires.ftc.teamcode.mecanum.hardware.Constants.goToFoundation;
import static org.firstinspires.ftc.teamcode.mecanum.hardware.Constants.goToSecondBlock;
import static org.firstinspires.ftc.teamcode.mecanum.hardware.Constants.goToThirdBlock;

@Autonomous(name = "PurePursuitTest", group = "Test")
//@Disabled

public class PurePursuitTest extends AbstractAuton {

    private Robot robot;

    @Override
    public void RegisterStates() {

        addState(new PathState("Delayed Arm Down", "test", robot.delayedArmDownCallable()));

    }

    @Override
    public void Init() {
        robot = new Robot();
        //robot.setArmDown();
        robot.setGripperRelease();
    }

    @Override
    public void Run() {

            robot.setArmDown();
            robot.runTestPurePursuit(goToFirstBlock);
            robot.setGripperGrip();
            delay(500);
            robot.setArmUp();
            delay(500);
            robot.runTestPurePursuit(goToFoundation);
            robot.setArmDown();
            delay(500);
            robot.setGripperRelease();
            delay(400);
            robot.setArmUp();
            delay(500);
            AbstractAuton.addFinishedState("test");
            robot.runTestPurePursuit(goToSecondBlock);
            robot.setGripperGrip();
            delay(500);
            robot.setArmUp();
            delay(500);
            robot.runTestPurePursuit(goBackToFoundation);
            robot.setArmDown();
            delay(500);
            robot.setGripperRelease();
            delay(400);
            robot.setArmUp();
            delay(500);
            AbstractAuton.addFinishedState("test");
            robot.runTestPurePursuit(goToThirdBlock);
            robot.setGripperGrip();
            delay(500);
            robot.setArmUp();
            delay(500);
            robot.runTestPurePursuit(goBackToFoundationAgain);
            robot.setArmDown();
            delay(500);
            robot.setGripperRelease();
            delay(400);
            robot.setArmUp();


    }
}
