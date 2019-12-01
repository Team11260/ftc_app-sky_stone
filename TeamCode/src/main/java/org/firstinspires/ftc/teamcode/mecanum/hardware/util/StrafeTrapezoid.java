package org.firstinspires.ftc.teamcode.mecanum.hardware.util;

public class StrafeTrapezoid {

    double RAMP_UP_DISTANCE =  5;
    double RAMP_DOWN_DISTANCE = 2.5;
    double ZOOM_IN_DISTANCE =  10;
    double ZERO_STOP = 1.0;

    public double getPower(String name, double distanceError, double distanceTravelled) {

        double power = 0.2;

        switch (name) {

            case "strafe from foundation": {

                power = 0.2;

                break;
            }

            case "strafe a distance": {

                if (distanceError < ZERO_STOP)
                    power = 0.0;
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
            }
        }
        return power;

    }
}