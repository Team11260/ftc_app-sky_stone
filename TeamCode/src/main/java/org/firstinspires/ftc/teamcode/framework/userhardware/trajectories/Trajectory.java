package org.firstinspires.ftc.teamcode.framework.userhardware.trajectories;

public abstract class Trajectory {

    public abstract double velocity(double distance);

    public abstract double acceleration(double distance);

    public abstract boolean isDone(double time);
}