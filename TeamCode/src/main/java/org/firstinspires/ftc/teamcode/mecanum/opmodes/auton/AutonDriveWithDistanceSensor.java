package org.firstinspires.ftc.teamcode.mecanum.opmodes.auton;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractAuton;
import org.firstinspires.ftc.teamcode.framework.util.PathState;
import org.firstinspires.ftc.teamcode.framework.util.State;
import org.firstinspires.ftc.teamcode.mecanum.hardware.Constants;
import org.firstinspires.ftc.teamcode.mecanum.hardware.Robot;
import org.firstinspires.ftc.teamcode.mecanum.hardware.RobotState;

@Autonomous(name = "Auton Drive with DistSensor", group = "Test")
@Disabled

public class AutonDriveWithDistanceSensor extends AbstractAuton {

    Robot robot;
    private Servo arm, gripper;

    @Override
    public void RegisterStates() {

        addState(new State("arm down to collect", "start", robot.armDownCallable()));
        addState(new PathState("arm grips the stone", "drive to sky stone", robot.setGripperGripCallable()));
        addState(new State("arm up", "arm grips the stone", robot.setArmUpCallable()));
        addState(new PathState("gripping pause", "drive to sky stone", () -> {
            RobotState.currentPath.pause();
            delay(Constants.GRIPPING_DELAY);
            RobotState.currentPath.resume();
        }));
        addState(new PathState("arm down with block", "drive to tray", robot.armDownCallable()));
        addState(new State("release stone", "arm down with block", robot.deliverStoneCallable()));
        addState(new State("tray latch down", "arm down with block", robot.setDraggerDownCallable()));
        addState(new PathState("tray latch up", "pull tray", robot.setDraggerUpCallable()));
    }

    @Override
    public void Init() {

        robot = new Robot();

    }

    public void InitLoop(int loop) {
        //if (loop%5 == 1)
        //robot.getSkyStonePosition();
    }

    @Override
    public void Run() {


    }


}
