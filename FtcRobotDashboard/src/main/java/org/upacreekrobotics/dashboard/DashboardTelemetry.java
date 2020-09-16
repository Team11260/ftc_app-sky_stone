package org.upacreekrobotics.dashboard;

public class DashboardTelemetry {

    private static final Date date = new Date();

    private String oldTelemetry;
    private String infoText;

    public DashboardTelemetry() {
        oldTelemetry = "";
        infoText = "";
    }

    void reconnected() {
        String[] telemetryParts = oldTelemetry.split("&#%#&");
        for (String part : telemetryParts) {
            if (!part.equals(" "))
                write(part);
        }
        oldTelemetry = "";
    }

    public void write(String text) {
        String packet = getLogPreMessage() + text;

        if(Dashboard.getConnectionHandler().isConnected()) {
            Dashboard.getConnectionHandler().write(new Message(MessageType.TELEMETRY, packet));
        } else {
            oldTelemetry += "&#%#&" + packet;
        }
    }

    public void updateInfo() {
        Dashboard.getConnectionHandler().write(new Message(MessageType.INFO, infoText));
        infoText = "";
    }

    public void info(String text) {
        infoText = text + "!#@$";
    }

    public void info(int text) {
        info(String.valueOf(text));
    }

    public void info(double text) {
        info(String.valueOf(text));
    }

    public static String getLogPreMessage() {
        String packet = "";

        if (Dashboard.getOpModeHandler().isOpModeInInit()) {
            packet = date.getDateTime().replace(": ", "") + "%&&%&&%Time since init: " +
                    Dashboard.getOpModeHandler().getTimeSinceInit() / 1000.0 + "%&&%&&%";
        } else if (Dashboard.getOpModeHandler().isOpModeRunning()) {
            packet = date.getDateTime().replace(": ", "") + "%&&%&&%Time since start: " +
                    Dashboard.getOpModeHandler().getTimeSinceStart() / 1000. + "%&&%&&%";
        } else {
            packet = date.getDateTime().replace(": ", "") + "%&&%&&%Stopped%&&%&&%";
        }

        return packet;
    }

    public static String getTelemetryPreMessage() {
        String packet = "";

        if (Dashboard.getOpModeHandler().isOpModeInInit()) {
            double currentTime = Dashboard.getOpModeHandler().getTimeSinceInit() / 1000.0;
            packet = date.getDateTime() + "`Time since init: " + currentTime + "`";
        } else if (Dashboard.getOpModeHandler().isOpModeRunning()) {
            packet = date.getDateTime() + "`Time since start: " +
                    Dashboard.getOpModeHandler().getTimeSinceStart() / 1000.0 + "`";
        } else {
            packet = date.getDateTime() + "`Stopped`";
        }

        return packet;
    }
}