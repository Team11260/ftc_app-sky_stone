package org.firstinspires.ftc.teamcode.framework.userhardware;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.framework.userhardware.outputs.Logger;
import org.upacreekrobotics.dashboard.Dashboard;
import org.upacreekrobotics.dashboard.DashboardTelemetry;
import org.upacreekrobotics.dashboard.SmartDashboard;

public class DoubleTelemetry {

    private LogMode loggingMode = LogMode.INFO;
    private LogMode defaultLogMode = LogMode.TRACE;

    private Telemetry telemetry;
    private DashboardTelemetry dashtelem;
    private Logger logger;

    public DoubleTelemetry(Telemetry telemetry, DashboardTelemetry dashtelem, Logger logger) {
        this.telemetry = telemetry;
        this.dashtelem = dashtelem;
        this.logger = logger;
    }

    public void setLogMode(LogMode mode) {
        this.loggingMode = mode;
    }

    public void setDefaultLogMode(LogMode mode) {
        defaultLogMode = mode;
    }

    public void addData(Object caption, Object data) {
        addData(defaultLogMode, caption, data);
    }

    public void addData(Object data) {
        addData(defaultLogMode, data);
    }

    public void addDataDB(Object data) {
        addDataDB(defaultLogMode, data);
    }

    public void addDataPhone(Object data) {
        addDataPhone(defaultLogMode, data);
    }

    public void addData(LogMode mode, Object caption, Object data) {
        String message = "[" + mode.toString() + "] " + String.valueOf(caption) + ": " + String.valueOf(data);
        log(message);
        if (loggingMode.shouldLog(mode)) {
            telemetry.addData(String.valueOf(caption), String.valueOf(data));
            dashtelem.write(message);
            dashtelem.info(message);
        }
    }

    public void addData(LogMode mode, Object data) {
        String message = "[" + mode.toString() + "] " + String.valueOf(data);
        log(message);
        if (loggingMode.shouldLog(mode)) {
            telemetry.addLine(String.valueOf(data));
            dashtelem.write(message);
            dashtelem.info(message);
        }
    }

    public void addDataDB(LogMode mode, Object data) {
        if (loggingMode.shouldLog(mode)) {
            dashtelem.write("[" + mode.toString() + "] " + String.valueOf(data));
        }
    }

    public void addDataPhone(LogMode mode, Object data) {
        log("[" + mode.toString() + "] " + String.valueOf(data));
        // if (loggingMode.shouldLog(mode))
        //{
        telemetry.addLine(String.valueOf(data));
        //  dashtelem.info("[" + mode.toString() + "] " + String.valueOf(data));
        // }
    }

    public void addDataPhone(LogMode mode, Object caption, Object data) {
        String message = "[" + mode.toString() + "] " + String.valueOf(caption) + ": " + String.valueOf(data);
        log(message);
        if (loggingMode.shouldLog(mode)) {
            telemetry.addData(String.valueOf(caption), String.valueOf(data));
            dashtelem.info(message);
        }
    }

    public void update() {
        try {
            telemetry.update();
            dashtelem.updateInfo();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

    public void log(Object data) {
        logger.log(DashboardTelemetry.getLogPreMessage() + data);
    }

    public SmartDashboard getSmartdashboard() {
        return Dashboard.getSmartDashboard();
    }

    public void putString(String key, String value) {
        Dashboard.getPreferencesHandler().putString(key, value);
    }

    public void putInt(String key, int value) {
        Dashboard.getPreferencesHandler().putInt(key, value);
    }

    public void putFloat(String key, float value) {
        Dashboard.getPreferencesHandler().putFloat(key, value);
    }

    public void putBoolean(String key, boolean value) {
        Dashboard.getPreferencesHandler().putBoolean(key, value);
    }

    public String getString(String key, String defaultValue) {
        return Dashboard.getPreferencesHandler().getString(key, defaultValue);
    }

    public int getInt(String key, int defaultValue) {
        return Dashboard.getPreferencesHandler().getInt(key, defaultValue);
    }

    public float getFloat(String key, float defaultValue) {
        return Dashboard.getPreferencesHandler().getFloat(key, defaultValue);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return Dashboard.getPreferencesHandler().getBoolean(key, defaultValue);
    }

    public void stop() {
        logger.stop();
    }

    public enum LogMode {
        TRACE(0),
        DEBUG(1),
        INFO(1),
        ERROR(3);

        private int level;

        LogMode(int level) {
            this.level = level;
        }

        private boolean shouldLog(LogMode mode) {
            return (mode.getValue() >= this.getValue());
        }

        private int getValue() {
            return level;
        }
    }
}
