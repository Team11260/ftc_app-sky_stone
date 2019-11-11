package org.firstinspires.ftc.teamcode.mecanum.hardware.devices;

import org.firstinspires.ftc.robotcore.internal.android.dx.rop.cst.Zeroes;

public class StraightTrapezoid {

    double RAMP_UP_DISTANCE = 5.0;
    double RAMP_DOWN_DISTANCE = 12.0;
    double ZOOM_IN_DISTANCE = 12.0;
    double ZERO_STOP = 4.0;
    double MAX_POWER = 0.30;
    double START_POWER = 0.15;
    double END_POWER = 0.12;
    public double



     getPower (String name, double distanceError, double distanceTravelled) {

        double power;


        if(distanceError<ZERO_STOP)
            power = 0.0;
        else if(distanceError<(ZOOM_IN_DISTANCE+ ZERO_STOP))
            power = END_POWER;
        else if (distanceError<(ZOOM_IN_DISTANCE+RAMP_DOWN_DISTANCE+ZERO_STOP))
            power = END_POWER+(((MAX_POWER-END_POWER)*(distanceError - ZOOM_IN_DISTANCE-ZERO_STOP))/RAMP_DOWN_DISTANCE);
        else if (distanceTravelled<RAMP_UP_DISTANCE)
            power = START_POWER;
        else if (distanceTravelled<2*RAMP_UP_DISTANCE)
            power = START_POWER+((MAX_POWER-START_POWER)*(distanceTravelled-RAMP_UP_DISTANCE)/RAMP_UP_DISTANCE);
        else
            power = MAX_POWER;

        return power;

    }


}
