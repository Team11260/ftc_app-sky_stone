package org.upacreekrobotics.GUI;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Date {

    private DateTimeFormatter df;
    private DateTimeFormatter dtf;

    public Date(){
        df = DateTimeFormatter.ofPattern("MM:dd:yyyy");
        dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss.SSS");
    }

    public String getDate(){
        LocalDateTime now = LocalDateTime.now();
        return (df.format(now));
    }

    public String getDateTime(){
        LocalDateTime now = LocalDateTime.now();
        return (dtf.format(now));
    }
}