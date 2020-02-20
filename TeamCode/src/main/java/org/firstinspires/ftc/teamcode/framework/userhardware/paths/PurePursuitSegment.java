package org.firstinspires.ftc.teamcode.framework.userhardware.paths;

import org.firstinspires.ftc.teamcode.framework.userhardware.purepursuit.PursuitPath;

public class PurePursuitSegment extends Segment {

    PursuitPath path;
    int period = 0;
    double targetHeading = 0.0;
    double timeOut = 20000;
    boolean blockSense = false;

    public PurePursuitSegment(String name, PursuitPath path) {

        super(name, SegmentType.PUREPURSUIT);
        this.path = path;
    }

    public PurePursuitSegment(String name, PursuitPath path, int period) {

        super(name, SegmentType.PUREPURSUIT);
        this.period = period;
        this.path = path;
    }

    public PurePursuitSegment(String name, PursuitPath path, int period, double targetHeading) {

        super(name, SegmentType.PUREPURSUIT);
        this.period = period;
        this.path = path;
        this.targetHeading = targetHeading;
    }

    public PurePursuitSegment(String name, PursuitPath path, int period, double targetHeading, double timeOut) {

        super(name, SegmentType.PUREPURSUIT);
        this.period = period;
        this.path = path;
        this.targetHeading = targetHeading;
        this.timeOut = timeOut;
    }


    public PurePursuitSegment(String name, PursuitPath path, int period, double targetHeading, double timeOut, boolean blockSense) {
        super(name, SegmentType.PUREPURSUIT);
        this.period = period;
        this.path = path;
        this.targetHeading = targetHeading;
        this.timeOut = timeOut;
        this.blockSense = blockSense;


    }


    public double getTimeOut() {
        return timeOut;
    }

    public boolean getBlockSense(){
        return blockSense;


    }

    public PursuitPath getPursuitPath() {
        return path;
    }

    public int getPeriod() {
        return period;
    }

    public double getTargetHeading() {
        return targetHeading;
    }
}
