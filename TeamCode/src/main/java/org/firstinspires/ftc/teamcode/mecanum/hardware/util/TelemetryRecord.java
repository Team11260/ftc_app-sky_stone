package org.firstinspires.ftc.teamcode.mecanum.hardware.util;

public class TelemetryRecord {
    public String TelemetryString;
    public int Index;
    public double Time;
    public double Angle;
    public double BackLeftCount;
    public double BackRightCount;
    public double Back_Diff;
    public double Error;
    public double FrontLeftCount;
    public double FrontRightCount;
    public double Front_Diff;
    public double LeftPower;
    public double RightPower;

    public TelemetryRecord() {
    }

    String GetString() {
        return TelemetryString;
    }

}
