package org.firstinspires.ftc.teamcode.framework.userhardware.purepursuit;

import org.firstinspires.ftc.teamcode.framework.userhardware.DoubleTelemetry;
import org.firstinspires.ftc.teamcode.framework.userhardware.PIDController;

public abstract class MecanumPurePursuitController extends PurePursuitController {

    protected double lastXPosition = 0, lastYPosition = 0;

    protected final double yScale;

    protected double targetHeading = 0.0;

    protected HeadingMode headingMode = HeadingMode.FIXED;

    private PIDController headingController;

    public MecanumPurePursuitController(double trackWidth, double yScale, PIDController headingController, DoubleTelemetry telemetry) {
        super(trackWidth, telemetry);

        this.yScale = yScale;

        this.headingController = headingController;
    }

    public void setTargetHeading(double targetHeading) {
        this.targetHeading = targetHeading;
    }

    public void setHeadingMode(HeadingMode headingMode) {
        this.headingMode = headingMode;
    }

    @Override
    public void follow(Path path) {
        super.follow(path);

        headingController.reset();
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
        if(headingMode == HeadingMode.DYNAMIC) {
            super.updateFollower();
            return;
        }

        if(!isFollowing()) return;

        int lookahead = currentPath.getLookAheadPointIndex(currentPosition);
        int closest = currentPath.getClosestPointIndex(currentPosition);

        if(lookahead == -1) {
            isFollowing = false;
            lookahead = currentPath.getPoints().size() - 1;
        }

        double velocity = currentPath.getPathPointVelocity(closest, currentPosition);
        double angle = currentPath.getAngleFromPathPoint(lookahead, currentPosition);

        double x = Math.cos(Math.toRadians(angle));
        double y = Math.sin(Math.toRadians(angle)) * yScale;
        double z = headingController.output(targetHeading, currentPosition.getHeading());

        /*telemetry.getSmartdashboard().putGraph("powers", "x", closest, x);
        telemetry.getSmartdashboard().putGraph("powers", "y", closest, y);
        telemetry.getSmartdashboard().putGraph("powers", "velocity", closest, velocity);
        telemetry.getSmartdashboard().putGraph("position", "lookahead", closest, lookahead);
        telemetry.getSmartdashboard().putGraph("position", "angle", closest, angle);*/
        telemetry.getSmartdashboard().putGraph("position", "error", closest, currentPath.getTrackingError(currentPosition));
        telemetry.getSmartdashboard().putGraph("position", "v", closest, velocity);

        double frontLeft = velocity * (x - y - z);
        double frontRight = velocity * (x + y + z);
        double backLeft = velocity * (x + y - z);
        double backRight = velocity * (x - y + z);

        setMecanumPower(frontLeft, frontRight, backLeft, backRight);
    }

    @Override
    public boolean isFollowing() {
        return currentPath != null && (isFollowing || Math.abs(currentPosition.getHeading()) > 1 || currentPosition.distance(currentPath.getPoint(currentPath.getPoints().size() - 1)) > 1);
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

    @Override
    public void setPower(double l, double r) {
        setMecanumPower(l, r, l, r);
    }

    public abstract double getXActualPositionInches();

    public abstract double getYActualPositionInches();

    public abstract void setMecanumPower(double fl, double fr, double bl, double br);

    public enum HeadingMode {
        FIXED,
        DYNAMIC
    }
}
