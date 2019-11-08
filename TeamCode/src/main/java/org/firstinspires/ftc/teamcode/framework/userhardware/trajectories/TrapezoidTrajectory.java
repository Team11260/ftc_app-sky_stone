package org.firstinspires.ftc.teamcode.framework.userhardware.trajectories;

public class TrapezoidTrajectory extends Trajectory {

    private double distance;
    private double acceleration;
    private double deceleration;
    private double cruiseVelocity;
    private double minimumVelocity;

    private double accelerationDistance;
    private double cruisePosition;
    private double decelerationDistance;
    private double decelerationPosition;

    public TrapezoidTrajectory(double distance, double acceleration, double deceleration, double cruiseVelocity, double minimumVelocity) {
        this.distance = distance;
        this.acceleration = acceleration;
        this.deceleration = deceleration;
        this.cruiseVelocity = cruiseVelocity;
        this.minimumVelocity = minimumVelocity;

        accelerationDistance = 1 / acceleration;
        cruisePosition = (1 / acceleration);
        decelerationDistance = 1 / deceleration;
        decelerationPosition = distance - (1 / deceleration);
    }

    @Override
    public double velocity(double distance) {
        double power;

        if(distance < 0 || this.distance < distance) {
            power = 0.0;
        } else if(distance > decelerationPosition) {
            power = cruiseVelocity * (1 - ((distance - decelerationPosition) / decelerationDistance));
        } else if(distance > cruisePosition) {
            power = cruiseVelocity;
        } else {
            power = cruiseVelocity * (distance / accelerationDistance);
        }

        if(power < minimumVelocity) {
            power = minimumVelocity;
        }

        return power;
    }

    @Override
    public double acceleration(double distance) {
        return 0.0;
    }

    public boolean isDone(double distance) {
        return distance > this.distance;
    }
}