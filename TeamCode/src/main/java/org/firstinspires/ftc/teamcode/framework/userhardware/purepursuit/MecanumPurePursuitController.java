package org.firstinspires.ftc.teamcode.framework.userhardware.purepursuit;

import org.firstinspires.ftc.teamcode.framework.userhardware.DoubleTelemetry;
import org.firstinspires.ftc.teamcode.framework.userhardware.PIDController;

import static org.firstinspires.ftc.teamcode.framework.userhardware.DoubleTelemetry.LogMode.INFO;

public abstract class MecanumPurePursuitController extends PurePursuitController {

    protected double lastXPosition = 0, lastYPosition = 0;

    protected double positionError = 1.0, headingError = 3.0;

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
    public void follow(PursuitPath pursuitPath) {
        super.follow(pursuitPath);

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

        telemetry.getSmartdashboard().putGraph("position", "distance", xDistance, yDistance);
        telemetry.getSmartdashboard().putGraph("position", "scalars", x, y);

        currentPosition = new Pose(currentPosition.add(new Vector(xDistance * x, xDistance * y)).add(new Vector(-yDistance * y, yDistance * x)), heading);

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

        int lookahead = currentPursuitPath.getLookAheadPointIndex(currentPosition);
        int closest = currentPursuitPath.getClosestPointIndex(currentPosition);

        if(lookahead == -1) {
            isFollowing = false;
            lookahead = currentPursuitPath.getPoints().size() - 1;
        }

        double velocity = currentPursuitPath.getPathPointVelocity(closest, currentPosition);
        double angle = currentPursuitPath.getAngleFromPathPoint(lookahead, currentPosition) - currentPosition.getHeading();

        double x = Math.cos(Math.toRadians(angle));
        double y = Math.sin(Math.toRadians(angle)) * yScale;
        double z = headingController.output(targetHeading, currentPosition.getHeading());

        double frontLeft = velocity * (x + y - z);
        double frontRight = velocity * (x - y + z);
        double backLeft = velocity * (x - y - z);
        double backRight = velocity * (x + y + z);

        telemetry.addData(INFO,"Front left power: " + frontLeft);
        telemetry.update();

        setMecanumPower(frontLeft, frontRight, backLeft, backRight);
    }

    @Override
    public boolean isFollowing() {
        return currentPursuitPath != null && (isFollowing || Math.abs(currentPosition.getHeading() - targetHeading) > headingError || currentPosition.distance(currentPursuitPath.getPoint(currentPursuitPath.getPoints().size() - 1)) > positionError);
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
