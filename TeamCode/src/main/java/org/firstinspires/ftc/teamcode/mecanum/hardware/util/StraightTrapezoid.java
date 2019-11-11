package org.firstinspires.ftc.teamcode.mecanum.hardware.util;

public class StraightTrapezoid {

    double RAMP_UP_DISTANCE =  3.0;
    double RAMP_DOWN_DISTANCE = 12.0;
    double ZOOM_IN_DISTANCE =  12.0;
    double ZERO_STOP = 2.0;
    double ZOOM_IN_POWER = 0.12;
    double MAX_POWER = 0.3;
    double INITIAL_POWER = 0.15;
    double STOP_POWER = 0.0;

    public double getPower(String name, double distanceError, double distanceTravelled) {

    double power = 0.2;

    switch (name){

        case "collect block":{

            if (distanceError < 5)
                power = STOP_POWER;
            else
                power = 0.2;
            break;
            }

        case "back up":{

            if (distanceError < 4)
                power = STOP_POWER;
            else
                power = 0.2;
            break;
        }
            case "drive straight a distance":{

                if (distanceError < ZERO_STOP)
                    power = STOP_POWER;
                else if (distanceError < (ZOOM_IN_DISTANCE + ZERO_STOP))
                    power = ZOOM_IN_POWER;
                else if (distanceError < (ZOOM_IN_DISTANCE + RAMP_DOWN_DISTANCE + ZERO_STOP))
                    power = ZOOM_IN_POWER + (((MAX_POWER - ZOOM_IN_POWER) * (distanceError - ZOOM_IN_DISTANCE - ZERO_STOP)) / RAMP_DOWN_DISTANCE);
                else if (distanceTravelled < RAMP_UP_DISTANCE)
                    power = INITIAL_POWER;
                else if (distanceTravelled < 3 * RAMP_UP_DISTANCE)
                    power = INITIAL_POWER + ((MAX_POWER - INITIAL_POWER) * (distanceTravelled - RAMP_UP_DISTANCE) / (2 * RAMP_UP_DISTANCE));
                else
                    power = MAX_POWER;
                break;
            }
    }
    return power;
}


}
