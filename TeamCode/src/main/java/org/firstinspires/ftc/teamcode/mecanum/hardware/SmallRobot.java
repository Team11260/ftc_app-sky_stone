package org.firstinspires.ftc.teamcode.mecanum.hardware;

import org.firstinspires.ftc.teamcode.framework.userhardware.paths.Path;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.PurePursuitSegment;
import org.firstinspires.ftc.teamcode.framework.userhardware.purepursuit.Point;
import org.firstinspires.ftc.teamcode.framework.userhardware.purepursuit.PursuitPath;
import org.firstinspires.ftc.teamcode.framework.util.AbstractRobot;
import org.firstinspires.ftc.teamcode.framework.util.RobotCallable;
import org.firstinspires.ftc.teamcode.mecanum.hardware.devices.drive.DriveController;

public class SmallRobot extends AbstractRobot {

    DriveController driver;


    public SmallRobot() {
        driver = new DriveController();


    }

    public void goHome() {


        driver.runDrivePath(goToBig());


        driver.resetAngleToZero();


    }

    protected Path goToBig() {
        Path smallGoHome = new Path("Go Home");


        smallGoHome.addSegment(new PurePursuitSegment("Go Close",
                new PursuitPath(
                        new Point(this.getSmallX(), this.getSmallY()),
                        new Point(Robots.bigRobot.getBigX(), Robots.bigRobot.getBigY() - 10)), 0

        ));

        smallGoHome.addSegment(new PurePursuitSegment("Go Inside",
                new PursuitPath(
                        new Point(Robots.bigRobot.getBigX(), Robots.bigRobot.getBigY() - 10),
                        new Point(Robots.bigRobot.getBigX(), Robots.bigRobot.getBigY())


                ), 0, Robots.bigRobot.getBigHeading()

        ));


        return smallGoHome;


    }


    public double getSmallX() {
        return driver.getCurrentPosition().getX();


    }

    public double getSmallY() {
        return driver.getCurrentPosition().getY();


    }

    public double getSmallHeading() {
        return driver.getHeading();


    }


    public void stop() {
    }


}
