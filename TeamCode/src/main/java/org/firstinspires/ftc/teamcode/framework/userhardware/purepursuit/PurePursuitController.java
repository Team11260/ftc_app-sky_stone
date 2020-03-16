package org.firstinspires.ftc.teamcode.framework.userhardware.purepursuit;
import org.firstinspires.ftc.teamcode.framework.userhardware.DoubleTelemetry;


public abstract class PurePursuitController {

    protected final double trackWidth;
    protected double lastLeftPosition = 0, lastRightPosition = 0;
    protected Pose currentPosition = new Pose();
    protected boolean isFollowing = false;
    protected PursuitPath currentPursuitPath = null;
    protected DoubleTelemetry telemetry;


    public PurePursuitController(double trackWidth, DoubleTelemetry telemetry) {
        this.trackWidth = trackWidth;
        this.telemetry = telemetry;
    }

    public void updateLoop(){
        while (isFollowing)
            update(false);

    }

    public void update(boolean rotate) {
        updatePose();

        updateFollower(rotate);
    }

    public void updatePose() {

        double leftPosition = getLeftActualPositionInches();
        double rightPosition = getRightActualPositionInches();

        double heading = getActualHeadingDegrees();

        double distance = ((leftPosition - lastLeftPosition) + (rightPosition - lastRightPosition)) / 2;

        currentPosition = new Pose(currentPosition.add(new Vector(distance * Math.cos(Math.toRadians(heading)), distance * Math.sin(Math.toRadians(heading)))), heading);

        lastLeftPosition = leftPosition;
        lastRightPosition = rightPosition;
    }

    public void updateFollower(boolean rotate) {

        if(!isFollowing || currentPursuitPath == null) return;

        int lookahead = currentPursuitPath.getLookAheadPointIndex(currentPosition);
        int closest = currentPursuitPath.getClosestPointIndex(currentPosition);

        if(lookahead == -1) {

            currentPursuitPath = null;
            isFollowing = false;
            return;
        }

        double velocity = currentPursuitPath.getPathPointVelocity(closest, currentPosition);
        double curvature = currentPursuitPath.getCurvatureFromPathPoint(lookahead, currentPosition);

        double left = velocity * ((2 + curvature * trackWidth)/2);
        double right = velocity * ((2 - curvature * trackWidth)/2);

        setPower(left, right);
    }

    public void follow(PursuitPath pursuitPath) {
        currentPursuitPath = pursuitPath;
        isFollowing = true;
    }

    public Pose getCurrentPosition() {
        return currentPosition;
    }

    public boolean isFollowing() {
        return isFollowing;
    }

    public void pause() {
        isFollowing = false;
    }

    public void resume() {
        isFollowing = true;
    }

    public void resetPosition() {
        currentPosition = new Pose();
        lastRightPosition = 0;
        lastLeftPosition = 0;
    }

    public abstract double getActualHeadingDegrees();

    public abstract double getLeftActualPositionInches();

    public abstract double getRightActualPositionInches();

    public abstract void setPower(double l, double r);
}
