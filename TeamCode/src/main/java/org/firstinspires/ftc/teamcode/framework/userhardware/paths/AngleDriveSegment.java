package org.firstinspires.ftc.teamcode.framework.userhardware.paths;

public class AngleDriveSegment extends Segment {

    private final double distance, speed, angle;
    private final double error;
    boolean alignHeading;


    public AngleDriveSegment(String name, double distance, double speed, double error) {

        this(name, distance, speed, error, -210000);
    }

    public AngleDriveSegment(String name, double distance, double speed, double error, double angle) {
        super(name, SegmentType.ANGLEDRIVE);
        this.distance = distance;
        this.speed = speed;
        this.error = error;
        this.angle = angle;
    }

    public double getDistance() {
        return distance;
    }

    public double getSpeed() {
        return speed;
    }

    public double getError() {
        return error;
    }

    public Double getAngle() {
        if(angle == -210000) return null;
        return angle;
    }


}
