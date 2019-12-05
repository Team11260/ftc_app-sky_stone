package org.firstinspires.ftc.teamcode.mecanum.opmodes.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractAuton;
import org.firstinspires.ftc.teamcode.framework.userhardware.DoubleTelemetry;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.Path;
import org.firstinspires.ftc.teamcode.framework.util.PathState;
import org.firstinspires.ftc.teamcode.mecanum.hardware.Robot;
import static org.firstinspires.ftc.teamcode.mecanum.hardware.AutonPursuitPaths.*;

@Autonomous(name = "PurePursuitTest", group = "Test")
//@Disabled

public class PurePursuitTest extends AbstractAuton {

    private Robot robot;
    int initloopscount = 0;
    String skyStonePosition = "Center";
    Path stonePath = null;

    @Override
    public void RegisterStates() {

        addState(new PathState("Arm Down", "start", robot.armDownCallable()));

        addState(new PathState("Grab first stone", "drive to first sky stone", robot.grabStoneCallable()));
        addState(new PathState("Grab second stone", "drive to second sky stone", robot.grabStoneCallable()));
        addState(new PathState("Grab third stone", "drive to third stone", robot.grabStoneCallable()));

        addState(new PathState("Deliver first stone", "first trip to foundation", robot.deliverStoneCallable()));
        addState(new PathState("Deliver second stone", "second trip to foundation", robot.deliverStoneCallable()));
        addState(new PathState("Deliver third stone", "third trip to foundation", robot.deliverStoneCallable()));

        addState(new PathState("Delayed Arm Down", "Deliver first stone", robot.delayedArmDownCallable()));
        addState(new PathState("Delayed Arm Down", "Deliver second stone", robot.delayedArmDownCallable()));
        //addState(new PathState("Delayed Arm Down", "Deliver third stone", robot.delayedArmDownCallable()));

    }
    public void InitLoop() {
        // arm.setArmUpPosition();
        ++initloopscount;

        skyStonePosition = robot.getSkyStonePositionThreeStones(initloopscount);
        switch (skyStonePosition) {
            case "Right":
                stonePath = RedPurePursuitRight;
                break;

            case "Left":
                stonePath = RedPurePursuitLeft;
                break;

            case "Center":
                stonePath = RedPurePursuitCenter;
                break;

            default:
                stonePath = RedPurePursuitCenter;
                break;
        }

        telemetry.addData(DoubleTelemetry.LogMode.INFO, skyStonePosition);

        telemetry.update();
        //delay(1000);

    }
    @Override
    public void Init() {
        robot = new Robot();
        robot.setArmUp();
        robot.setGripperRelease();
    }


    @Override
    public void Run() {

        /*switch (robot.getSkyStonePositionThreeStones(0)) {
            case "Right":
                robot.runDrivePath(RedPurePursuitRight);
                break;

            case "Left":
                // robot.runDrivePath(collectLeftSkyStone);
                break;

            case "Center":
                //robot.runDrivePath(collectCenterSkyStone);
                break;

            default:
                //robot.runDrivePath(collectCenterSkyStone);
                break;
        }*/
        robot.runDrivePath(stonePath);
        //robot.runDrivePath(RedPurePursuitParkFoundation);

        telemetry.addData(DoubleTelemetry.LogMode.INFO, "All done ");
        telemetry.update();
        delay(9000);
    }
}
