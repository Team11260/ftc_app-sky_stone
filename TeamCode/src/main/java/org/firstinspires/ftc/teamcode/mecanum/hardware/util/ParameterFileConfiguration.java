package org.firstinspires.ftc.teamcode.mecanum.hardware.util;
import java.util.*;
import java.util.Properties;


public class ParameterFileConfiguration {


        Properties configFile;
        public ParameterFileConfiguration()
        {
            configFile = new java.util.Properties();
            try {
                configFile.load(this.getClass().getClassLoader().
                        getResourceAsStream("RobotParameters.txt"));
            }catch(Exception eta){
                eta.printStackTrace();
            }
        }

        public String getProperty(String key)
        {
            String value = configFile.getProperty(key);
            return value;
        }




}
