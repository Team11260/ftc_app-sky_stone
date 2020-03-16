package org.firstinspires.ftc.teamcode.framework.userhardware.purepursuit;

import org.firstinspires.ftc.teamcode.framework.userhardware.DoubleTelemetry;
import org.firstinspires.ftc.teamcode.framework.userhardware.PIDController;

import static org.firstinspires.ftc.teamcode.framework.userhardware.DoubleTelemetry.LogMode.INFO;

public abstract class MecanumPurePursuitController extends PurePursuitController {

    protected double lastXPosition = 0, lastYPosition = 0, lastHeading = 0;

    protected final double yScale;

    protected double targetHeading = 0.0;

    protected double nextDistance = 0.0;

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

        double xDistance = xPosition - lastXPosition + ((heading - lastHeading)*0.11);
        double yDistance = yPosition - lastYPosition + ((heading - lastHeading)*0.04);;

        double x = Math.cos(Math.toRadians(heading));
        double y = Math.sin(Math.toRadians(heading));


        currentPosition = new Pose(currentPosition.add(new Vector(xDistance * x, xDistance * y)).add(new Vector(-yDistance * y, yDistance * x)), heading);

        telemetry.getSmartdashboard().putGraph("position", "Heading", xPosition, heading);
        telemetry.getSmartdashboard().putGraph("position", "X Y Plane", currentPosition.getX(), currentPosition.getY());


        /*telemetry.addData(INFO," " );
        telemetry.addData(INFO,"heading " + heading);
        telemetry.addData(INFO,"x encoder " + xPosition);
        telemetry.addData(INFO,"y encoder " + yPosition);
        telemetry.addData(INFO,"x distance " + xDistance);
        telemetry.addData(INFO,"y distance: " + yDistance);
        telemetry.addData(INFO,"x position " + currentPosition.getX());
        telemetry.addData(INFO,"y position " + currentPosition.getY());
        telemetry.addData(INFO," " );
        telemetry.update();*/

        lastXPosition = xPosition;
        lastYPosition = yPosition;
        lastHeading = heading;

        nextDistance = new Vector(xDistance,yDistance).magnitude();
    }

    @Override
    public void updateFollower(boolean rotate) {
        if(headingMode == HeadingMode.DYNAMIC) {
            super.updateFollower(rotate);
            return;
        }

        if(!isFollowing()) return;

        int lookahead = currentPursuitPath.getLookAheadPointIndex(currentPosition);
        int closest = currentPursuitPath.getClosestPointIndex(currentPosition);

        if(lookahead == -1) {
            telemetry.addData(INFO,"Exited Path by lookahead");
            telemetry.update();

            isFollowing = false;
            lookahead = currentPursuitPath.getPoints().size() - 1;
        }
        Vector delta = new Vector(currentPursuitPath.getPoint(lookahead).subtract(currentPosition));

        telemetry.addData(INFO,"Y value  "  + currentPosition.getY());
        telemetry.addData(INFO,"X value  "  + currentPosition.getX());
        telemetry.update();

        if(rotate){
           if(currentPosition.getY() > 3) targetHeading = -45;
            if(currentPosition.getY() > 5) targetHeading = -60;
            if(currentPosition.getY() > 7 && targetHeading > -75) targetHeading = -75;
            if(currentPosition.getY() > 9 && targetHeading > -90) targetHeading = -90;
        }
        //telemetry.addData(INFO,"Y value  "  + currentPosition.getY());
        //telemetry.addData(INFO,"X value  "  + currentPosition.getX());
        //telemetry.addData(INFO,"Heading  "  + targetHeading);
        //telemetry.update();

        double velocity = currentPursuitPath.getPathPointVelocity(closest, currentPosition);
        double angle = currentPursuitPath.getAngleFromPathPoint(lookahead, currentPosition) - currentPosition.getHeading();

        double x = Math.cos(Math.toRadians(angle));
        double y = Math.sin(Math.toRadians(angle)) * yScale;
        double z = currentPursuitPath.getTurnGain() * headingController.output(targetHeading, currentPosition.getHeading());


        //telemetry.getSmartdashboard().putGraph("Position","Target Heading", closest ,angle);

        double frontLeft = velocity * (x - y) - z;
        double frontRight = velocity * (x + y) + z;
        double backLeft = velocity * (x + y) - z;
        double backRight = velocity * (x - y) + z;

//        telemetry.getSmartdashboard().putGraph("outputs","x", closest ,x);
//        telemetry.getSmartdashboard().putGraph("outputs","y", closest ,y);
//        telemetry.getSmartdashboard().putGraph("outputs","z", closest ,z);



//        telemetry.addData(INFO,"heading: " + currentPosition.getHeading());
//        telemetry.addData(INFO,"x position: " + currentPosition.getX());
//        telemetry.addData(INFO,"y position: " + currentPosition.getY());
//        telemetry.addData(INFO," " );
//        telemetry.update();

        setMecanumPower(frontLeft, frontRight, backLeft, backRight);
    }

    public double getDistance(){
        return nextDistance;
    }

    @Override
    public boolean isFollowing() {
        return currentPursuitPath != null && (isFollowing || Math.abs(currentPosition.getHeading() - targetHeading) > currentPursuitPath.getHeadingError() || currentPosition.distance(currentPursuitPath.getPoint(currentPursuitPath.getPoints().size() - 1)) > currentPursuitPath.getPositionError());
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
