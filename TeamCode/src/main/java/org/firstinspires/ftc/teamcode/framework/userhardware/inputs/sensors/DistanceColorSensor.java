package org.firstinspires.ftc.teamcode.framework.userhardware.inputs.sensors;

import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractOpMode;

public class DistanceColorSensor {

    private DistanceSensor distanceSensor;

    public DistanceColorSensor(String name){
        distanceSensor = AbstractOpMode.getHardwareMap().get(DistanceSensor.class, name);
    }

    public double getDistance(){
        return distanceSensor.getDistance(DistanceUnit.INCH);
    }

}
