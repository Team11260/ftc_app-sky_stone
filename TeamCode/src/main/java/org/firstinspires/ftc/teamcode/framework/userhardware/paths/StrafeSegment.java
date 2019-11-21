package org.firstinspires.ftc.teamcode.framework.userhardware.paths;

public class StrafeSegment extends Segment {

    private final double distance, speed, angle=0.0;
    private final double error;
    final int period;
    boolean alignHeading;


    public StrafeSegment(String name, double distance, double speed, double error) {
        this(name, distance, speed, error, -210000, false);
    }

    public StrafeSegment(String name, double distance, double speed, double error, boolean alignHeading){
        this(name, distance, speed, error, -210000, alignHeading);

    }

    public StrafeSegment(String name, double distance, double speed, double error, int period, boolean alignHeading) {
        super(name, SegmentType.STRAFE);
        this.distance = distance;
        this.alignHeading = alignHeading;
        this.speed = speed;
        this.error = error;
        this.period = period;
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

    public boolean getAlignHeading() {
        return alignHeading;
    }

    public Double getAngle() {
        if (angle == -210000) return null;
        return angle;
    }
}
