package org.firstinspires.ftc.teamcode.mecanum.hardware.devices;

public class StraightTrapezoid {

    double RAMP_UP_DISTANCE =  3;
    double RAMP_DOWN_DISTANCE = 12;
    double ZOOM_IN_DISTANCE =  12;
    double ZERO_STOP = 4;

    public double getPower(String namme, double distanceError, double distanceTravelled) {

    double power;

    if(distanceError<ZERO_STOP)
        power =0.0;
    else if(distanceError<(ZOOM_IN_DISTANCE +ZERO_STOP))
        power =0.12;
    else if(distanceError<(ZOOM_IN_DISTANCE +RAMP_DOWN_DISTANCE +ZERO_STOP))
        power =0.12+((0.18*(distanceError -ZOOM_IN_DISTANCE-ZERO_STOP))/RAMP_DOWN_DISTANCE);
    else if(distanceTravelled<RAMP_UP_DISTANCE)
        power =0.15;
    else if(distanceTravelled< 3*RAMP_UP_DISTANCE)
        power =0.15+(0.15*(distanceTravelled -RAMP_UP_DISTANCE)/(2*RAMP_UP_DISTANCE));
    else
        power =0.30;

    return power;
}


}
