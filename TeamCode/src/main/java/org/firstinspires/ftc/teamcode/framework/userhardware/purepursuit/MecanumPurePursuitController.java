package org.firstinspires.ftc.teamcode.framework.userhardware.purepursuit;

import org.firstinspires.ftc.teamcode.framework.userhardware.DoubleTelemetry;
import org.firstinspires.ftc.teamcode.framework.userhardware.PIDController;

public abstract class MecanumPurePursuitController extends PurePursuitController {

    protected double lastXPosition = 0, lastYPosition = 0;

    private PIDController headingController;

    public MecanumPurePursuitController(double trackWidth, DoubleTelemetry telemetry) {
        super(trackWidth, telemetry);

        headingController = new PIDController(100, 0, 100);
    }

    @Override
    public void updatePose() {

        double xPosition = getXActualPositionInches();
        double yPosition = getYActualPositionInches();

        double heading = getActualHeadingDegrees();

        double xDistance = xPosition - lastXPosition;
        double yDistance = yPosition - lastYPosition;

        double x = Math.cos(Math.toRadians(heading));
        double y = Math.sin(Math.toRadians(heading));

        currentPosition = new Pose(currentPosition.add(new Vector(xDistance * x, xDistance * y)).add(new Vector(yDistance * y, yDistance * x)), heading);

        lastXPosition = xPosition;
        lastYPosition = yPosition;
    }

    @Override
    public void updateFollower() {

        if(!isFollowing || currentPath == null) return;

        int lookahead = currentPath.getLookAheadPointIndex(currentPosition);
        int closest = currentPath.getClosestPointIndex(currentPosition);

        if(lookahead == -1) {
            currentPath = null;
            isFollowing = false;
            return;
        }

        double velocity = currentPath.getPathPointVelocity(closest, currentPosition);
        double angle = currentPath.getAngleFromPathPoint(lookahead, currentPosition);

        double x = Math.cos(Math.toRadians(angle));
        double y = Math.sin(Math.toRadians(angle)) * 1.4;

        telemetry.getSmartdashboard().putGraph("powers", "x", closest, x);
        telemetry.getSmartdashboard().putGraph("powers", "y", closest, y);

        double z = headingController.output(0, currentPosition.getHeading());

        double frontLeft = velocity * (x - y - z);
        double frontRight = velocity * (x + y + z);
        double backLeft = velocity * (x + y - z);
        double backRight = velocity * (x - y + z);

        setMecanumPower(frontLeft, frontRight, backLeft, backRight);
    }

    @Override
    public boolean isFollowing() {
        return super.isFollowing || !(Math.abs(currentPosition.getHeading()) < 2);
    }

    @Override
    public void resetPosition() {
        super.resetPosition();

        lastXPosition = 0;
        lastYPosition = 0;
    }

    @Override
    public final double getLeftActualPositionInches() {
        return 0;
    }

    @Override
    public final double getRightActualPositionInches() {
        return 0;
    }

    public abstract double getXActualPositionInches();

    public abstract double getYActualPositionInches();

    @Override
    public final void setPower(double l, double r) {
        setMecanumPower(l, r, l, r);
    }

    public abstract void setMecanumPower(double fl, double fr, double bl, double br);
}
