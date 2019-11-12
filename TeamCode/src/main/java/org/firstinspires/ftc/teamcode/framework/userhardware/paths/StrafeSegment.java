package org.firstinspires.ftc.teamcode.framework.userhardware.paths;

public class StrafeSegment extends Segment {

    private final double distance, speed, angle;
    private final int error;
    boolean alignHeading;


    public StrafeSegment(String name, double distance, double speed, int error) {
        this(name, distance, speed, error, -210000, false);
    }

    public StrafeSegment(String name, double distance, double speed, int error, boolean alignHeading){
        this(name, distance, speed, error, -210000, alignHeading);

    }

    public StrafeSegment(String name, double distance, double speed, int error, double angle, boolean alignHeading) {
        super(name, SegmentType.STRAFE);
        this.distance = distance;
        this.alignHeading = alignHeading;
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

    public int getError() {
        return error;
    }

    public boolean getAlignHeading() {
        return alignHeading;
    }

    public Double getAngle() {
        if (angle == -210000) return null;
        return angle;
    }
}
