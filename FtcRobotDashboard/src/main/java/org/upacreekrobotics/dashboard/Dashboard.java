package org.upacreekrobotics.dashboard;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Dashboard {

    private String lastInputValue;

    private ExecutorService executor;

    private ConnectionHandler connectionHandler;
    private OpModeHandler opModeHandler;
    private BatteryHandler batteryHandler;
    private ConfigVariableHandler configVariableHandler;
    private PreferencesHandler preferencesHandler;
    private DashboardTelemetry dashboardTelemetry;
    private SmartDashboard smartDashboard;
    private RobotRestartHandler robotRestartHandler;

    private static Dashboard dashboard;

    ////////////////Start the dashboard, calls Dashboard constructor, called by "onCreate()" in "FtcRobotControllerActivity"////////////////
    public static void start() {
        if (dashboard == null) {
            new Dashboard();
        }
    }

    ////////////////Stops the dashboard and socket connection////////////////
    ////////////////called by "onDestroy()" in "FtcRobotControllerActivity"////////////////
    public static void stop() {
        if(dashboard != null) {
            dashboard.executor.shutdownNow();
        }
    }

    ////////////////Constructor called by "start()"////////////////
    private Dashboard() {
        dashboard = this;

        executor = Executors.newCachedThreadPool();

        connectionHandler = new ConnectionHandler(this::onMessage);
        opModeHandler = new OpModeHandler();
        batteryHandler = new BatteryHandler();
        configVariableHandler = new ConfigVariableHandler();
        preferencesHandler = new PreferencesHandler();
        dashboardTelemetry = new DashboardTelemetry();
        smartDashboard = new SmartDashboard();
        robotRestartHandler = new RobotRestartHandler();
        robotRestartHandler.registerRestartCompleteListener(() -> {
            getConnectionHandler().write(new Message(MessageType.RESTART_ROBOT));
        });
    }

    public void onMessage(Message message) {
        switch (message.getType()) {
            case GET_OP_MODES: {
                for (String mode : opModeHandler.getOpModeNames()) {
                    connectionHandler.write(new Message(MessageType.OP_MODES, mode));
                }
                try {
                    if (opModeHandler.isOpModeActive()) {
                        if (opModeHandler.isOpModeInInit()) {
                            connectionHandler.write(new Message(MessageType.SELECT_OP_MODE, opModeHandler.getActiveOpModeName()));
                            connectionHandler.write(new Message(MessageType.ROBOT_STATUS, "INIT"));
                        } else if (opModeHandler.isOpModeRunning()) {
                            connectionHandler.write(new Message(MessageType.SELECT_OP_MODE, opModeHandler.getActiveOpModeName()));
                            connectionHandler.write(new Message(MessageType.ROBOT_STATUS, "RUNNING"));
                        }
                    }
                } catch (NullPointerException e) {

                }

                if(dashboardTelemetry != null) {
                    dashboardTelemetry.reconnected();
                }
                if(smartDashboard != null) {
                    smartDashboard.reconnected();
                }
                if(configVariableHandler != null) {
                    for (Map.Entry<String, Map<String, Field>> entry : configVariableHandler.getConfigVariables().entrySet()) {
                        String dataLine = entry.getKey();
                        for (HashMap.Entry<String, Field> e : entry.getValue().entrySet()) {
                            try {
                                dataLine += "<&#%#&>" + e.getKey() + ">#&%&#<" + e.getValue().getType() + ">#&%&#<" + e.getValue().get(null);
                            } catch (IllegalAccessException e1) {
                                e1.printStackTrace();
                            }
                        }
                        connectionHandler.write(new Message(MessageType.VARIABLE, dataLine));
                    }
                }
                break;
            }

            case VARIABLE: {
                String[] parts = message.getText().split("<&#%#&>");
                String[] dataParts = parts[1].split(">#&%&#<");
                configVariableHandler.setConfigVariable(parts[0], dataParts[0], dataParts[1]);
                break;
            }

            case RESTART_ROBOT: {
                robotRestartHandler.requestRestart();
                break;
            }

            case INIT_OP_MODE: {
                opModeHandler.initializeActiveOpMode(message.getText());
                break;
            }

            case RUN_OP_MODE: {
                opModeHandler.startActiveOpMode();
                break;
            }

            case STOP_OP_MODE: {
                opModeHandler.stopActiveOpMode();
                break;
            }

            case RETURN_VALUE: {
                lastInputValue = message.getText();
                break;
            }

            case SMARTDASHBOARD_GET: {
                smartDashboard.onResponse(message);
                break;
            }

            // TODO fix gamepads
            case GAMEPAD_1_SET: {
//                try {
//                    if (message.getText().equals("default")) {
//                        if (opModeManager.getActiveOpMode().gamepad1 instanceof DashboardGamepad)
//                            opModeManager.getActiveOpMode().gamepad1 = ((OurEventLoop) eventLoop).getGamepads()[0];
//                    } else if (opModeManager.getActiveOpMode().gamepad1 instanceof DashboardGamepad) {
//                        ((DashboardGamepad) opModeManager.getActiveOpMode().gamepad1).update(message.getText());
//                    } else {
//                        opModeManager.getActiveOpMode().gamepad1 = new DashboardGamepad();
//                        ((DashboardGamepad) opModeManager.getActiveOpMode().gamepad1).update(message.getText());
//                    }
//                } catch (ClassCastException e) {
//                } catch (NullPointerException e) {
//                }

                break;
            }

            case GAMEPAD_2_SET: {
//                try {
//                    if (opModeManager == null || opModeManager.getActiveOpMode() == null)
//                        return;
//                    if (message.getText().equals("default")) {
//                        if (opModeManager.getActiveOpMode().gamepad2 instanceof DashboardGamepad)
//                            opModeManager.getActiveOpMode().gamepad2 = ((OurEventLoop) eventLoop).getGamepads()[0];
//                    } else if (opModeManager.getActiveOpMode().gamepad2 instanceof DashboardGamepad) {
//                        ((DashboardGamepad) opModeManager.getActiveOpMode().gamepad2).update(message.getText());
//                    } else {
//                        opModeManager.getActiveOpMode().gamepad2 = new DashboardGamepad();
//                        ((DashboardGamepad) opModeManager.getActiveOpMode().gamepad2).update(message.getText());
//                    }
//                } catch (ClassCastException e) {
//                }

                break;
            }

            default: {
                break;
            }
        }
    }

    public static Dashboard getInstance() {
        if (dashboard == null) {
            start();
        }
        return dashboard;
    }

    public static ExecutorService getExecutor() {
        return getInstance().executor;
    }

    public static ConnectionHandler getConnectionHandler() {
        return getInstance().connectionHandler;
    }

    public static OpModeHandler getOpModeHandler() {
        return getInstance().opModeHandler;
    }

    public static PreferencesHandler getPreferencesHandler() {
        return getInstance().preferencesHandler;
    }

    public static DashboardTelemetry getTelemetry() {
        return getInstance().dashboardTelemetry;
    }

    public static SmartDashboard getSmartDashboard() {
        return getInstance().smartDashboard;
    }

    public static RobotRestartHandler getRobotRestartHandler() {
        return getInstance().robotRestartHandler;
    }

    ////////////////Called by the user code to request an input value from the dashboard, calls "internalGetInputValue()"////////////////
    public static double getInputValueDouble(String caption) {
        String value = dashboard.internalGetInputValue(caption);
        try {
            if (value.equals("")) return 0;
            return Double.valueOf(value);
        } catch (NumberFormatException e) {
            if (!caption.contains("Please enter a valid double for: "))
                caption = "Please enter a valid double for: " + caption;
            if (getOpModeHandler().isOpModeStopped()) return 0.0;
            return getInputValueDouble(caption);
        }
    }

    public static String getInputValueString(String caption) {
        return dashboard.internalGetInputValue(caption);
    }

    ////////////////The code to ask the dashboard to input a value and wait for a response////////////////
    ////////////////This can also be used to block the user opMode to do autonomous step by step////////////////
    private String internalGetInputValue(String caption) {
        if (connectionHandler != null) {
            connectionHandler.write(new Message(MessageType.GET_VALUE, caption));
        }
        lastInputValue = null;
        while (opModeHandler.isOpModeActive() && connectionHandler != null &&
                lastInputValue == null && !Thread.currentThread().isInterrupted());
        if (lastInputValue != null) {
            return lastInputValue;
        }
        return "";
    }
}