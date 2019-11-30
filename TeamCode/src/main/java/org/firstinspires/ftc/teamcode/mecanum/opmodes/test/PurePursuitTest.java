package org.firstinspires.ftc.teamcode.mecanum.opmodes.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractAuton;
import org.firstinspires.ftc.teamcode.framework.userhardware.DoubleTelemetry;
import org.firstinspires.ftc.teamcode.framework.util.PathState;
import org.firstinspires.ftc.teamcode.mecanum.hardware.Robot;
import static org.firstinspires.ftc.teamcode.mecanum.hardware.AutonPursuitPaths.*;
import static org.firstinspires.ftc.teamcode.mecanum.hardware.Constants.dragFoundation;

@Autonomous(name = "PurePursuitTest", group = "Test")
//@Disabled

public class PurePursuitTest extends AbstractAuton {

    private Robot robot;

    @Override
    public void RegisterStates() {

        addState(new PathState("Delayed Arm Down", "start", robot.armDownCallable()));

        addState(new PathState("Grab first stone", "drive to first sky stone", robot.grabStoneCallable()));
        addState(new PathState("Grab second stone", "drive to second sky stone", robot.grabStoneCallable()));
        //addState(new PathState("Grab third stone", "drive to third stone", robot.grabStoneCallable()));
        //addState(new PathState("Grab fourth stone", "drive to fourth stone", robot.grabStoneCallable()));

        addState(new PathState("Deliver first stone", "first trip to foundation", robot.deliverStoneCallable()));
        addState(new PathState("Deliver second stone", "second trip to foundation", robot.deliverStoneCallable()));
        //addState(new PathState("Deliver third stone", "third trip to foundation", robot.deliverStoneCallable()));
        //addState(new PathState("Deliver fourth stone", "fourth trip to foundation", robot.deliverStoneCallable()));

        addState(new PathState("Delayed Arm Down", "Deliver first stone", robot.delayedArmDownCallable()));
        addState(new PathState("Delayed Arm Down", "Deliver second stone", robot.delayedArmDownCallable()));
        //addState(new PathState("Delayed Arm Down", "Deliver third stone", robot.delayedArmDownCallable()));
        //addState(new PathState("Delayed Arm Down", "Deliver fourth stone", robot.delayedArmDownCallable()));

    }

    @Override
    public void Init() {
        robot = new Robot();
        robot.setArmUp();
        robot.setGripperRelease();
    }


    @Override
    public void Run() {

        robot.runDrivePath(RedPurePursuitRight);
        //robot.runDrivePath(RedPurePursuitParkFoundation);

        telemetry.addData(DoubleTelemetry.LogMode.INFO, "All done ");
        telemetry.update();
        delay(9000);
    }
}
