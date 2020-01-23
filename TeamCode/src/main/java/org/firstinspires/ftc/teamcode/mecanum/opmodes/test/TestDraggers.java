package org.firstinspires.ftc.teamcode.mecanum.opmodes.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.R;
import org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractAuton;
import org.firstinspires.ftc.teamcode.framework.userhardware.purepursuit.MecanumPurePursuitController;
import org.firstinspires.ftc.teamcode.mecanum.hardware.Robot;

import static org.firstinspires.ftc.teamcode.mecanum.hardware.AutonPursuitPaths.BlueDragFoundationTest;
import static org.firstinspires.ftc.teamcode.mecanum.hardware.AutonPursuitPaths.RedDragFoundation;
import static org.firstinspires.ftc.teamcode.mecanum.hardware.AutonPursuitPaths.RedDragFoundationTest;


@Autonomous (name = "Test Draggers", group = "New")
public class TestDraggers extends AbstractAuton {

    Robot robot;


    @Override
    public void RegisterStates() {

        addState("release dragger","Pull the foundation",robot.delayedDraggerUpCallable());
    }

    @Override
    public void Init() {
        robot = new Robot();
        robot.setDraggerDown();
    }

    @Override
    public void Run() {
        robot.runDrivePath(BlueDragFoundationTest);

        robot.dragger.setDraggerUp();
    }
}
