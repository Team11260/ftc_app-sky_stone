package org.firstinspires.ftc.teamcode.mecanum.hardware.devices;

public class TelemetryObject {

    // static variable single_instance of type Singleton
    private static TelemetryObject single_instance = null;

    // variable of type String
    public String s;

    // private constructor restricted to this class itself
    private TelemetryObject()
    {
        s = "Hello I am a string part of Singleton class";
    }

    // static method to create instance of Singleton class
    public static TelemetryObject getInstance()
    {
        if (single_instance == null)
            single_instance = new TelemetryObject();

        return single_instance;
    }
}
