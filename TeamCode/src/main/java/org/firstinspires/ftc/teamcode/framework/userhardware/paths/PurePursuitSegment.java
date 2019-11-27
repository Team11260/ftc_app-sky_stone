package org.firstinspires.ftc.teamcode.framework.userhardware.paths;

import org.firstinspires.ftc.teamcode.framework.userhardware.purepursuit.PursuitPath;

public class PurePursuitSegment extends Segment {

    PursuitPath path;
    int period=0;

    public PurePursuitSegment(String name, PursuitPath path) {

        super(name,SegmentType.PUREPURSUIT);
        this.path = path;
    }

    public PurePursuitSegment(String name, PursuitPath path, int period) {

        super(name,SegmentType.PUREPURSUIT);
        this.period = period;
        this.path = path;
    }

    public PursuitPath getPursuitPath() {
        return path;
    }

    public int getPeriod() {
        return period;
    }
}
