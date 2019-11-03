package org.firstinspires.ftc.teamcode.mecanum.opmodes.test;

import org.firstinspires.ftc.teamcode.framework.userhardware.paths.DriveSegment;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.Path;
import org.firstinspires.ftc.teamcode.framework.util.RobotCallable;
import org.firstinspires.ftc.teamcode.mecanum.hardware.devices.RobotState;

public class Robotcwo extends Robot {

    String skyStonePosition;
    DriveSegment segment1, segmentLeft, segmentRight, segmentCenter;
    Path path, pathLeft, pathRight, pathCenter;

    public Robotcwo() {
        segment1 = new DriveSegment("test", 20, 0.5, 100);
        segmentLeft = new DriveSegment("left", 20, 0.5, 100);
        segmentRight= new DriveSegment("right", 20, 0.5, 100);
        segmentCenter = new DriveSegment("center", 20, 0.5, 100);

        path = new Path("test");
        pathLeft = new Path("LeftPath");
        pathCenter = new Path("CenterPath");
        pathRight = new Path("RightPath");
        path.addSegment(segment1);
        pathLeft.addSegment(segmentLeft);
        pathRight.addSegment(segmentRight);
        pathCenter.addSegment(segmentCenter);
    }

    public void strafe(int distance){}

    public void getSkyStonePosition(){

        skyStonePosition = getSkyStonePositionThreeStones();

    }

    public RobotCallable armDownCallable() {
        return () -> armDown();
    }

    public void armDown(){}

    public RobotCallable armUpCallable() {
        return () -> armDown();
    }

    public void armUp(){}

    public RobotCallable armCloseCallable() {
        return () -> armClose();
    }

    public void armClose(){}

    public RobotCallable dropStoneCallable() {
        return () -> dropStone();
    }

    public void dropStone(){}

    public RobotCallable trayArmDownCallable() {
        return () -> trayArmDown();
    }

    public void trayArmDown(){}

    public RobotCallable trayArmUpCallable() {
        return () -> trayArmUp();
    }

    public void trayArmUp(){}

    public RobotCallable releaseStoneCallable() {
        return () -> releaseStone();
    }

    public void releaseStone(){}

}
