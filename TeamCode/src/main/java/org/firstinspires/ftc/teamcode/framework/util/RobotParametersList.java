package org.firstinspires.ftc.teamcode.framework.util;


import android.util.Log;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

// A class to read a list of parameters from a file.  The parameter file can be updated thru
//
//        adb push robot_parameters.txt /sdcard/FIRST
//
//    to save time instead of rebuilding the apk when the parameters are adjusted.
//
public class RobotParametersList {

    String result = "";
    InputStream inputStream;
    Properties prop;

    public RobotParametersList() {

        try {
            prop = new Properties();

            String propFileName = "/sdcard/FIRST/robot_parameters.txt";

            File file = new File(propFileName);

            if (file.exists()) {
                Log.e("Exception", "ABCD file exists, propFileName = " + propFileName);
            } else {
                Log.e("Exception", "ABCD file does not exists, propFileName =  " + propFileName);
                prop = null;
                inputStream = null;
                return;
            }

            inputStream = new FileInputStream(propFileName);


            if (inputStream != null) {
                prop.load(inputStream);
                Log.e("Exception", "ABCD inputStream valid = " + inputStream);
            } else {
                Log.e("Exception", "ABCD inputStream INVALID = " + inputStream);
            }


        } catch (Exception e) {
            Log.e("Exception", "ABCD exception e value = " + e);
            System.out.println("Exception: " + e);

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

        if (prop != null) {


            try {
                parameterInt = Integer.valueOf(prop.getProperty(getParamString, "8888888"));
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

        if (prop != null) {


            try {
                parameterInt = Integer.valueOf(prop.getProperty(getParamString, defaultValue));
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
        if (prop != null) {

            // get the property value and return it
            return (Double.valueOf(prop.getProperty(getParamString, "9.888888")));
        } else {
            return (9.999999);
        }
    }

    public double getParamValueDouble(String getParamString, String DefaultString) {
        if (prop != null) {

            // get the property value and return it
            return (Double.valueOf(prop.getProperty(getParamString, DefaultString)));
        } else {
            return (9.999999);
        }
    }

    public String getParamValueString(String getParamString) {
        if (prop != null) {

            // get the property value and return it
            return ( prop.getProperty(getParamString, "NoString") );
        } else {
            return ( "FileMissing" );
        }
    }

    public String getParamValueString(String getParamString, String DefaultString) {
        if (prop != null) {

            // get the property value and return it
            return ( prop.getProperty(getParamString, DefaultString) );
        } else {
            return ( "FileMissing" );
        }
    }


}
