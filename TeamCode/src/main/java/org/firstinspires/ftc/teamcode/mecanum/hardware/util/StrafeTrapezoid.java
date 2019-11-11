package org.firstinspires.ftc.teamcode.mecanum.hardware.util;

public class StrafeTrapezoid {

    double RAMP_UP_DISTANCE =  5;
    double RAMP_DOWN_DISTANCE = 12;
    double ZOOM_IN_DISTANCE =  10;
    double ZERO_STOP = 4;

    public double getPower(String namme, double distanceError, double distanceTravelled) {

        double power;

        if (distanceError < ZERO_STOP)
            power = 0.2;
        else if (distanceError < (ZOOM_IN_DISTANCE + ZERO_STOP))
            power = 0.3;
        else if (distanceError < (ZOOM_IN_DISTANCE + RAMP_DOWN_DISTANCE + ZERO_STOP))
            power = 0.2 + ((0.3 * (distanceError - ZOOM_IN_DISTANCE - ZERO_STOP)) / RAMP_DOWN_DISTANCE);
        else if (distanceTravelled < RAMP_UP_DISTANCE)
            power = 0.2;
        else if (distanceTravelled < 2 * RAMP_UP_DISTANCE)
            power = 0.2 + (0.3 * (distanceTravelled - RAMP_UP_DISTANCE) / RAMP_UP_DISTANCE);
        else
            power = 0.5;

        return power;
    }
}