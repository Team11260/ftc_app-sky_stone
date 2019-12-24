package org.firstinspires.ftc.teamcode.mecanum.hardware.util;


public class StraightTrapezoid {

    double RAMP_UP_DISTANCE = 5.0;
    double RAMP_DOWN_DISTANCE = 15.0;
    double ZOOM_IN_DISTANCE = 20.0;
    double ZERO_STOP = 1.0;
    double ZOOM_IN_POWER = 0.12;
    double MAX_POWER = 1.0;
    double INITIAL_POWER = 0.25;
    double STOP_POWER = 0.0;

    public double getPower(String name, double distanceError, double distanceTravelled) {

        double power = 1.0;

        switch (name) {

            case "collect block": {

                if (distanceError < 5)
                    power = STOP_POWER;
                else
                    power = 0.2;
                break;
            }

            case "back up": {

                if (distanceError < 4)
                    power = STOP_POWER;
                else
                    power = 0.3;
                break;
            }
            case "drive to foundation": {

                if (distanceError < RAMP_DOWN_DISTANCE)
                    power = MAX_POWER * distanceError  / RAMP_DOWN_DISTANCE;
                //else if (distanceTravelled < RAMP_UP_DISTANCE)
                    //power = INITIAL_POWER;
                else if (distanceTravelled < RAMP_UP_DISTANCE)
                    power = INITIAL_POWER+ (MAX_POWER-INITIAL_POWER)*(distanceTravelled/ RAMP_UP_DISTANCE);
                else
                    power = MAX_POWER;

                if(power<0) power = 0.0;
                break;
            }

        }
        return power;
    }
    public double getVelocity(String name, double distanceError, double distanceTravelled) {

        double velocity = 0.2;
        double MAX_VELOCITY = 40.0;
        double INIT_VEL = 5.0;
        double RAMP_DOWN_VEL_DISTANCE =  25.0;
        double RAMP_UP_VEL_DISTANCE = 10.0;

        switch (name) {

            case "collect block": {

                if (distanceError < 5)
                    velocity = STOP_POWER;
                else
                    velocity = 0.2;
                break;
            }

            case "back up": {

                if (distanceError < 4)
                    velocity = STOP_POWER;
                else
                    velocity = 0.2;
                break;
            }
            case "drive straight a distance": {

                if (distanceError < RAMP_DOWN_VEL_DISTANCE)
                    velocity = MAX_VELOCITY*distanceError/ RAMP_DOWN_VEL_DISTANCE;
                else if (distanceTravelled < RAMP_UP_VEL_DISTANCE)
                    velocity = INIT_VEL + ((MAX_VELOCITY-INIT_VEL)*distanceTravelled/RAMP_UP_VEL_DISTANCE);
                else
                    velocity = MAX_VELOCITY;
                break;
            }
            case "drive to foundation": {
                velocity = 0.1;
            }
        }
        return velocity;
    }

}
