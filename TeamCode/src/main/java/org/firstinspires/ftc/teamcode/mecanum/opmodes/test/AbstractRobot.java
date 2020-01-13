package org.firstinspires.ftc.teamcode.mecanum.opmodes.test;

import org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractAuton;
import org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractOpMode;
import org.firstinspires.ftc.teamcode.framework.userhardware.DoubleTelemetry;

public abstract class AbstractRobot {

    public DoubleTelemetry telemetry;

    public AbstractRobot() {
        telemetry = AbstractOpMode.getTelemetry();
    }

    public abstract void stop();

    public void delay(int time) {
        AbstractOpMode.delay(time);
    }
}
