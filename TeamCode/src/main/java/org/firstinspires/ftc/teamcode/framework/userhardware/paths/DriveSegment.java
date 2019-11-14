package org.firstinspires.ftc.teamcode.framework.userhardware.paths;

public class DriveSegment extends Segment {

    private final double distance, speed, angle;
    private final int error;
    private final int period;
    private boolean alignHeading;


    public DriveSegment(String name, double distance, double speed, int error) {
        this(name, distance, speed, error, -210000, false, 300000000);
    }

    public DriveSegment(String name, double distance, double speed, int error, boolean alignHeading) {
        this(name, distance, speed, error, -210000, alignHeading, 300000000);
    }

    public DriveSegment(String name, double distance, double speed, int error, int period) {
        this(name, distance, speed, error, -210000, false, period);
    }


    public DriveSegment(String name, double distance, double speed, int error, double angle, boolean alignHeading, int period) {
        super(name, SegmentType.DRIVE);
        this.distance = distance;
        this.alignHeading = alignHeading;
        this.speed = speed;
        this.error = error;
        this.angle = angle;
        this.period = period;
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

    public Double getAngle() {
        if (angle == -210000) return null;
        return angle;
    }

    public boolean getAlignHeading() {
        return alignHeading;
    }

    public int getPeriod() {
        return period;
    }
}

