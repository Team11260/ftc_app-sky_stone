package org.firstinspires.ftc.teamcode.mecanum.hardware.util;
import android.util.Log;

import org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractOpMode;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;
import java.util.Properties;
import org.firstinspires.ftc.teamcode.framework.userhardware.DoubleTelemetry;
import org.firstinspires.ftc.teamcode.framework.util.SubsystemController;

import static org.firstinspires.ftc.teamcode.framework.userhardware.DoubleTelemetry.LogMode.INFO;




public class ParameterFileConfiguration extends SubsystemController {


    String propFileName;
    Properties configFile;
    File file;
    InputStream inputStream;


    public ParameterFileConfiguration() {
        propFileName = "/sdcard/FIRST/RobotParameters.txt";
        configFile = new java.util.Properties();
        try {
            //    configFile.load(this.getClass().getClassLoader().
            //            getResourceAsStream("/sdcard/FIRST/RobotParameters.txt"));


            inputStream = new FileInputStream(propFileName);

            if (inputStream != null) {
                configFile.load(inputStream);
                Log.e("Exception", "ABCD inputStream valid = " + inputStream);
            } else {
                Log.e("Exception", "ABCD inputStream INVALID = " + inputStream);
            }
            telemetry.addData(INFO, "file found ");

        } catch (Exception e) {

            Log.e("Exception", "ABCD exception e value = " + e);
            System.out.println("Exception: " + e);

            telemetry.addData(INFO, "file not found ");


            //   e.printStackTrace();
        } finally {
            try {
                Log.e("Exception", "ABCD inputstream close = " + inputStream);
                inputStream.close();
            } catch (Exception e) {
            }

        }

    }

    public int getParamValueInt(String getParamString) {
        int parameterInt;

        if (configFile != null) {


            try {
                parameterInt = Integer.valueOf(configFile.getProperty(getParamString, "8888888"));
            } catch (NumberFormatException e) {
                Log.e("Exception", "ABCD getParamString Wrong In File = "
                        + getParamString);
                // Return 7777777 back to user to tell user something is wrong
                parameterInt = 7777777;
            }

            // get the property value and return it
            return (parameterInt);
        } else {
            return (9999999);
        }
    }

    public int getParamValueInt(String getParamString, String defaultValue) {
        int parameterInt;

        if (configFile != null) {


            try {
                parameterInt = Integer.valueOf(configFile.getProperty(getParamString, defaultValue));
            } catch (NumberFormatException e) {
                Log.e("Exception", "ABCD getParamString Wrong In File = "
                        + getParamString);
                // Return 7777777 back to user to tell user something is wrong
                parameterInt = 7777777;
            }

            // get the property value and return it
            return (parameterInt);
        } else {
            return (9999999);
        }
    }

    public double getParamValueDouble(String getParamString) {
        if (configFile != null) {

            // get the property value and return it
            return (Double.valueOf(configFile.getProperty(getParamString, "9.888888")));
        } else {
            return (9.999999);
        }
    }

    public double getParamValueDouble(String getParamString, String DefaultString) {
        if (configFile != null) {

            // get the property value and return it
            return (Double.valueOf(configFile.getProperty(getParamString, DefaultString)));
        } else {
            return (9.999999);
        }
    }

    public String getParamValueString(String getParamString) {
        if (configFile != null) {

            // get the property value and return it
            return ( configFile.getProperty(getParamString, "NoString") );
        } else {
            return ( "FileMissing" );
        }
    }

    public String getParamValueString(String getParamString, String DefaultString) {
        if (configFile != null) {

            // get the property value and return it
            return ( configFile.getProperty(getParamString, DefaultString) );
        } else {
            return ( "FileMissing" );
        }
    }





    public String getProperty(String key) {
        String value = configFile.getProperty(key);
        return value;
    }


    @Override
    public void update() throws Exception {

    }

    @Override
    public void stop() {

    }
}
